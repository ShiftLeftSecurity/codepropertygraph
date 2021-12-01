package io.shiftleft.utils

import java.util.concurrent.ForkJoinWorkerThread
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

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
}
