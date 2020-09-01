package io.shiftleft.dataflowengineoss.queryengine

import java.util.concurrent.{Callable, ExecutorCompletionService, ExecutorService, Executors}

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import org.slf4j.{Logger, LoggerFactory}
import io.shiftleft.semanticcpg.language._

import scala.util.{Failure, Success, Try}
import scala.jdk.CollectionConverters._

case class EngineContext(semantics: Semantics, config: EngineConfig = EngineConfig(), callDepth: Int = 0)
case class EngineConfig(var maxCallDepth: Int = 4)

/**
  * Backwards data flow task: from a `sink`, traverse the graph backwards to determine flows
  * from any of the nodes in `sources`, using the pre-calculated results in `ResultTable`.
  * */
private case class ReachableByTask(sink: nodes.TrackingPoint, sources: Set[nodes.TrackingPoint], table: ResultTable)

class Engine(context: EngineContext) {

  import Engine._

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)
  private var numberOfTasksRunning: Int = 0
  private val completionService = initializeWorkerPool()

  /**
    * Initialize a pool of workers and return a "completion service" that
    * we can query (in a blocking manner) for completed tasks.
    * */
  private def initializeWorkerPool(): ExecutorCompletionService[List[ReachableByResult]] = {
    val executorService: ExecutorService = Executors.newWorkStealingPool()
    new ExecutorCompletionService[List[ReachableByResult]](executorService)
  }

  /**
    * Determine flows from sources to sinks by analyzing backwards from sinks.
    * */
  def backwards(sinks: List[nodes.TrackingPoint], sources: List[nodes.TrackingPoint]): List[ReachableByResult] = {
    val sourcesSet = sources.toSet

    sinks
      .map(sink => ReachableByTask(sink, sourcesSet, new ResultTable))
      .foreach(task => submitTask(task, callDepth = 0))

    var result = List[ReachableByResult]()

    while (numberOfTasksRunning > 0) {
      Try {
        completionService.take.get
      } match {
        case Success(resultsOfTask) =>
          numberOfTasksRunning -= 1
          val (partial, complete) = resultsOfTask.partition(_.partial)
          result ++= complete
          submitTasksForPartialFlows(partial, sourcesSet)
          submitTasksForUnresolvedOutArgs(resultsOfTask, sourcesSet)
        case Failure(exception) =>
          numberOfTasksRunning -= 1
          logger.warn(exception.getMessage)
      }
    }
    result
  }

  /**
    * Partial results are flows originating from method input parameters.
    * */
  private def submitTasksForPartialFlows(partialResults: List[ReachableByResult],
                                         sourcesSet: Set[nodes.TrackingPoint]): Unit = {
    partialResults.map(result => (result, argsForPartial(result))).foreach {
      case (result, args) =>
        args.foreach { arg =>
          submitTask(ReachableByTask(arg, sourcesSet, new ResultTable), result.path, result.callDepth)
        }
    }
  }

  /**
    * Arguments associated with a partial flow, i.e., resolvable arguments corresponding
    * to the method parameter that the partial flow begins with.
    * */
  private def argsForPartial(partialResult: ReachableByResult): List[nodes.Expression] = {
    partialResult.path.headOption.toList
      .map(_.node)
      .collect {
        case p: nodes.MethodParameterIn =>
          paramToArgs(p)
      }
      .flatten
  }

  private def submitTasksForUnresolvedOutArgs(resultsOfTask: List[ReachableByResult],
                                              sourceSet: Set[nodes.TrackingPoint]): Unit = {

    val outArgsAndCalls = resultsOfTask
      .map(x => (x.unresolvedArgs, x.path, x.callDepth))
      .distinct

    val forCalls = outArgsAndCalls.flatMap {
      case (args, path, callDepth) =>
        val outCalls = args.collect { case n: nodes.Call => n }
        outCalls.flatMap(formalReturnsForCall).map { ret =>
          (ret, path, callDepth)
        }
    }

    val forArgs = outArgsAndCalls.flatMap {
      case (args, path, callDepth) =>
        args.flatMap(argToOutputParams).map { p =>
          (p, path, callDepth)
        }
    }

    (forCalls ++ forArgs).foreach {
      case (p: nodes.TrackingPoint, path: List[PathElement], callDepth) =>
        val task = ReachableByTask(p, sourceSet, new ResultTable)
        submitTask(task, path = path, callDepth = callDepth)
    }
  }

  private def submitTask(task: ReachableByTask, path: List[PathElement] = List(), callDepth: Int): Unit = {
    numberOfTasksRunning += 1
    completionService.submit(new ReachableByCallable(task, context.copy(callDepth = callDepth + 1), path))
  }

}

object Engine {

  def formalReturnsForCall(call: nodes.Call): List[nodes.MethodReturn] = {
    NoResolve.getCalledMethods(call).start.methodReturn.l
  }

  def argToOutputParams(arg: nodes.Expression): List[nodes.MethodParameterOut] = {
    argToMethods(arg).start.parameter.asOutput
      .order(arg.order)
      .l
  }

  def argToMethods(arg: nodes.Expression): List[nodes.Method] = {
    argToCall(arg).toList.flatMap { call =>
      methodsForCall(call)
    }
  }

  def argToCall(n: nodes.TrackingPoint): Option[nodes.Call] =
    n._argumentIn().asScala.collectFirst { case c: nodes.Call => c }

  def methodsForCall(call: nodes.Call): List[nodes.Method] = {
    NoResolve.getCalledMethods(call).toList
  }

  def paramToArgs(param: nodes.MethodParameterIn): List[nodes.Expression] =
    NoResolve
      .getMethodCallsites(param.method)
      .toList
      .collect { case call: nodes.Call => call }
      .start
      .argument(param.order)
      .l

}

private class ReachableByCallable(task: ReachableByTask,
                                  context: EngineContext,
                                  initialPath: List[PathElement] = List())
    extends Callable[List[ReachableByResult]] {

  import Engine._

  override def call(): List[ReachableByResult] = {
    if (context.callDepth > context.config.maxCallDepth) {
      List()
    } else {
      implicit val sem: Semantics = context.semantics
      results(List(PathElement(task.sink)) ++ initialPath, task.sources, task.table)
      task.table.get(task.sink).get.map { r =>
        r.copy(callDepth = context.callDepth)
      }
    }
  }

  /**
    * Recursively expand the DDG backwards and return a list of all
    * results, given by at least a source node in `sourceSymbols` and the
    * path between the source symbol and the sink.
    *
    * This method stays within the method (intra-procedural analysis) but
    * call sites which should be resolved are marked as such in the
    * ResultTable.
    *
    * @param path This is a path from a node to the sink. The first node
    *             of the path is expanded by this method
    * */
  private def results[NodeType <: nodes.TrackingPoint](path: List[PathElement],
                                                       sources: Set[NodeType],
                                                       table: ResultTable)(implicit semantics: Semantics): Unit = {
    val curNode = path.head.node

    val resultsForParents: List[ReachableByResult] = {
      val ddgParents = ddgIn(curNode).filter(parent => !path.map(_.node).contains(parent)).toList

      def lookupOrCalculate(parent: PathElement) = {
        table.createFromTable(parent :: path).getOrElse {
          results(parent :: path, sources, table)
          table.get(parent.node).get
        }
      }

      if (argToCall(curNode).isEmpty) {
        ddgParents.flatMap { parentNode =>
          lookupOrCalculate(PathElement(parentNode))
        }
      } else {
        val (arguments, nonArguments) = ddgParents.partition(_.isInstanceOf[nodes.Expression])
        val elemsForArguments = arguments.flatMap { parentNode =>
          elemForArgument(parentNode.asInstanceOf[nodes.Expression], curNode)
        }
        val elems = elemsForArguments ++ nonArguments.map(parentNode => PathElement(parentNode))
        elems.flatMap(lookupOrCalculate)
      }
    }

    val resultsForCurNode = {
      val endStates = if (sources.contains(curNode.asInstanceOf[NodeType])) {
        List(ReachableByResult(path))
      } else if (curNode.isInstanceOf[nodes.MethodParameterIn]) {
        List(ReachableByResult(path, partial = true))
      } else {
        List()
      }

      val retsToResolve = curNode match {
        case call: nodes.Call =>
          if (methodsForCall(call).start.internal.l.nonEmpty && semanticsForCall(call).isEmpty) {
            List(ReachableByResult(PathElement(path.head.node, resolved = false) :: path.tail, partial = true))
          } else {
            List()
          }
        case _ => List()
      }
      endStates ++ retsToResolve
    }

    table.add(curNode, resultsForParents ++ resultsForCurNode)
  }

  /**
    * For a given `(parentNode, curNode)` pair, determine whether to expand into
    * `parentNode`. If so, return a corresponding path element or None if
    * `parentNode` should not be followed.
    * */
  private def elemForArgument(parentNode: nodes.Expression, curNode: nodes.TrackingPoint)(
      implicit semantics: Semantics): Option[PathElement] = {
    val parentNodeCall = argToCall(parentNode)
    if (parentNodeCall == argToCall(curNode)) {
      val internalMethodsForCall = parentNodeCall.toList.flatMap(x => methodsForCall(x)).start.internal.l
      if (semanticsForCallByArg(parentNode.asInstanceOf[nodes.Expression]).nonEmpty || internalMethodsForCall.isEmpty) {
        Some(PathElement(parentNode)).filter(_ => isUsed(parentNode) && isDefined(curNode))
      } else {
        // There is no semantic and we can resolve the method, so, this is an
        // argument we would need to resolve. Report that, but don't take action for now
        Some(PathElement(parentNode, resolved = false)).filter(_ => isUsed(parentNode) && isDefined(curNode))
      }
    } else {
      Some(PathElement(parentNode, isDefined(parentNode))).filter(_ => isUsed(curNode))
    }
  }

  private def ddgIn(dstNode: nodes.TrackingPoint): Iterator[nodes.TrackingPoint] = {
    dstNode._reachingDefIn().asScala.collect { case n: nodes.TrackingPoint => n }
  }

  private def isUsed(srcNode: nodes.StoredNode)(implicit semantics: Semantics) = {
    Some(srcNode)
      .collect {
        case arg: nodes.Expression =>
          val s = semanticsForCallByArg(arg)
          s.isEmpty || s.exists(_.mappings.exists { case (srcIndex, _) => srcIndex == arg.order })
      }
      .getOrElse(true)
  }

  private def isDefined(srcNode: nodes.StoredNode)(implicit semantics: Semantics) = {
    Some(srcNode)
      .collect {
        case arg: nodes.Expression =>
          val s = semanticsForCallByArg(arg)
          s.isEmpty || s.exists(_.mappings.exists { case (_, dstIndex) => dstIndex == arg.order })
      }
      .getOrElse(true)
  }

  private def semanticsForCallByArg(arg: nodes.Expression)(implicit semantics: Semantics): List[FlowSemantic] = {
    argToMethods(arg).flatMap { method =>
      semantics.forMethod(method.fullName)
    }
  }

  private def semanticsForCall(call: nodes.Call)(implicit semantics: Semantics): List[FlowSemantic] = {
    Engine.methodsForCall(call).flatMap { method =>
      semantics.forMethod(method.fullName)
    }
  }

}
