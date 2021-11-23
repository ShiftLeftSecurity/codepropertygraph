package io.shiftleft.utils

import org.slf4j.{LoggerFactory, MDC}

import java.util.concurrent.{ForkJoinPool, ForkJoinWorkerThread, Semaphore, ThreadFactory}
import scala.concurrent.{BlockContext, CanAwait, ExecutionContext, ExecutionContextExecutorService}

object ExecutionContextSummoning {
  def summonExecutionContext: ExecutionContext = {
    Thread.currentThread() match {
      case fjt: ForkJoinWorkerThread =>
        fjt.getPool match {
          case ec: ExecutionContext => ec
          case _                    => ExecutionContext.global
        }
      case _ => ExecutionContext.global
    }
  }

  val logger = LoggerFactory.getLogger(getClass)

  //adapted from  scala/concurrent/impl/ExecutionContextImpl.scala

  def defaultReporter(thread: Thread, error: Throwable): Unit =
    logger.error(s"Uncaught exception on Thread ${thread}:", error)

  class MDCThreadFactory(val daemonic: Boolean,
                         val maxBlockers: Int,
                         val prefix: String,
                         val uncaught: Thread.UncaughtExceptionHandler,
                         val mdc: java.util.Map[String, String])
      extends ThreadFactory
      with ForkJoinPool.ForkJoinWorkerThreadFactory {

    require(prefix ne null, "DefaultThreadFactory.prefix must be non null")
    require(maxBlockers >= 0, "DefaultThreadFactory.maxBlockers must be greater-or-equal-to 0")

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
}
