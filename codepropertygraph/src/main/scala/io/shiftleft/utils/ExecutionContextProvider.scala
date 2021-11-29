package io.shiftleft.utils

import org.slf4j.{LoggerFactory, MDC}

import java.util.concurrent.{ForkJoinPool, ForkJoinWorkerThread, Semaphore, ThreadFactory}
import scala.concurrent.{
  BlockContext,
  CanAwait,
  ExecutionContext,
  ExecutionContextExecutor,
  ExecutionContextExecutorService
}

/** For the Shiftleft SAAS offering, we are using non-inherited thread-local MDC (message diagnostic context) in order
  * to relate log messages to artifacts. The same JVM process is concurrently processing multiple customer artifacts.
  *
  * In order to make this work, every long-running analysis gets its own MDCAwareExecutor, i.e. its own threadpool.
  *
  * So all multi-threading in codepropertygraph must make sure to either:
  *   1. Code that uses `new Thread` must set up MDC correctly. For example, consider the Writer background-threads
  *      spawned in ParallelCpgPass.scala, or consider the background-threads spawned in overflowdb.
  *   2. Code that directly (or scala-implicitly) uses threadpools / scala-style executors / java-style ExecutorService
  *      should call e.g. `implicit val ec:ExecutionContextExecutor = io.shiftleft.utils.ExecutionContextProvider.getExecutionContext`
  *      instead of `implicit val ec:ExecutionContextExecutor = scala.concurrent.ExecutionContext.global` or
  *      `import scala.concurrent.ExecutionContext.Implicits`
  *   3. Code that uses java parallel stream (e.g. ForkJoinParallelCpgPass) needs no changes -- the java stream standard
  *      library automatically picks up the right threadpool that sets up MDC correctly.
  *
  * When setting up MDC for a scan (i.e. shiftleft SAAS, or if one wants to make a competing offering based on codepropertygraph)
  * then one needs to:
  *   1. set up MDC
  *   2. create an MDCAwareExecutor
  *   3. submit a task that does the analysis to this MDCAwareExecutor
  *   4. await completion and shutdown the MDCAwareExecutor (e.g. via TryWithResources)
  *
  * It is essential to not leak MDCAwareExecutor instances -- the GC cannot shut them down, same as all threadpools.
  * An example is in codescience/mesh.
  *
  * The MDCAwareExecutor could theoretically live in closed-source codescience; it is open source in order to allow external
  * contributors to properly understand, test and honor the MDC / threadpool setup.
  *
  * NB: If the logging framework behind slf4j supports InheritableThreadLocal MDC, then one can alternatively use
  * ExecutionContext.fromExecutor(null) instead of MDCAwareExecutor.
  **/
object ExecutionContextProvider {

  def getExecutionContext: ExecutionContextExecutor = {
    //as of scala 2.13.5, this is always a subclass of ForkJoinPool and hence implements ExecutionContextExecutorService:
    //  1. If we extract the executionContext from the pool, then we must be ForkJoinPool
    //  2. The implemention of ExecutionContext.global happens to subclasses from ForkJoinPool, but doesn't expose this finer type in the API
    //So if you need an ExecutorService for some java stuff, feel free to cast this.

    Thread.currentThread() match {
      case fjt: ForkJoinWorkerThread =>
        fjt.getPool match {
          case ec: ExecutionContextExecutor => ec
          case _                            => ExecutionContext.global
        }
      case _ => ExecutionContext.global
    }
  }

  private val logger = LoggerFactory.getLogger(getClass)

  //adapted from  scala/concurrent/impl/ExecutionContextImpl.scala
  //cf https://github.com/scala/scala/blob/2131f5b7d96094df50726aa84291e6b0b173ae62/src/library/scala/concurrent/impl/ExecutionContextImpl.scala#L80
  def MDCAwareExecutor(prefix: String,
                       mdc: java.util.Map[String, String] = MDC.getCopyOfContextMap(),
                       uncaughtHandler: Thread.UncaughtExceptionHandler = defaultReporter)
    : ForkJoinPool with ExecutionContextExecutorService with AutoCloseable = {
    def getInt(name: String, default: String) =
      (try System.getProperty(name, default)
      catch {
        case _: SecurityException => default
      }) match {
        case s if s.charAt(0) == 'x' => (Runtime.getRuntime.availableProcessors * s.substring(1).toDouble).ceil.toInt
        case other                   => other.toInt
      }

    val desiredParallelism = // A range between min and max given num
      scala.math.min(
        scala.math.max(getInt("scala.concurrent.context.minThreads", "1"),
                       getInt("scala.concurrent.context.numThreads", "x1")),
        getInt("scala.concurrent.context.maxThreads", "x1")
      )
    val threadFactory = new MDCThreadFactory(
      daemonic = false,
      maxBlockers = getInt("scala.concurrent.context.maxExtraThreads", "256"),
      prefix = prefix,
      uncaught = uncaughtHandler,
      mdc = mdc
    )

    new ForkJoinPool(desiredParallelism, threadFactory, threadFactory.uncaught, true)
    with ExecutionContextExecutorService with AutoCloseable {
      final override def reportFailure(cause: Throwable): Unit =
        getUncaughtExceptionHandler() match {
          case null =>
          case some => some.uncaughtException(Thread.currentThread, cause)
        }

      override def close(): Unit = this.shutdown()
    }
  }

  //adapted from  scala/concurrent/impl/ExecutionContextImpl.scala
  //cf https://github.com/scala/scala/blob/2131f5b7d96094df50726aa84291e6b0b173ae62/src/library/scala/concurrent/impl/ExecutionContextImpl.scala#L27
  class MDCThreadFactory(val daemonic: Boolean,
                         val maxBlockers: Int,
                         val prefix: String,
                         val uncaught: Thread.UncaughtExceptionHandler,
                         val mdc: java.util.Map[String, String])
      extends ThreadFactory
      with ForkJoinPool.ForkJoinWorkerThreadFactory {

    require(prefix ne null, "MDCThreadFactory.prefix must be non null")
    require(maxBlockers >= 0, "MDCThreadFactory.maxBlockers must be greater-or-equal-to 0")

    private final val blockerPermits = new Semaphore(maxBlockers)

    def wire[T <: Thread](thread: T): T = {
      thread.setDaemon(daemonic)
      thread.setUncaughtExceptionHandler(uncaught)
      thread.setName(prefix + "-" + thread.getId())
      thread
    }

    def newThread(runnable: Runnable): Thread =
      wire(new Thread(runnable) {
        override def run(): Unit = {
          MDC.setContextMap(mdc); super.run()
        }
      })

    def newThread(fjp: ForkJoinPool): ForkJoinWorkerThread =
      wire(new ForkJoinWorkerThread(fjp) with BlockContext {

        override def run(): Unit = {
          MDC.setContextMap(mdc)
          super.run()
        }

        private[this] final var isBlocked
          : Boolean = false // This is only ever read & written if this thread is the current thread

        final override def blockOn[T](thunk: => T)(implicit permission: CanAwait): T =
          if ((Thread.currentThread eq this) && !isBlocked && blockerPermits.tryAcquire()) {
            try {
              val b: ForkJoinPool.ManagedBlocker with (() => T) =
                new ForkJoinPool.ManagedBlocker with (() => T) {
                  private[this] final var result: T = null.asInstanceOf[T]
                  private[this] final var done: Boolean = false

                  final override def block(): Boolean = {
                    if (!done) {
                      result = thunk // If this throws then it will stop blocking.
                      done = true
                    }

                    isReleasable
                  }

                  final override def isReleasable = done

                  final override def apply(): T = result
                }
              isBlocked = true
              ForkJoinPool.managedBlock(b)
              b()
            } finally {
              isBlocked = false
              blockerPermits.release()
            }
          } else thunk // Unmanaged blocking
      })
  }

  private def defaultReporter(thread: Thread, error: Throwable): Unit =
    logger.error(s"Uncaught exception on Thread ${thread}:", error)

}
