package io.shiftleft.dataflowengineoss.queryengine

import java.util.concurrent.{Callable, ExecutorCompletionService, ExecutorService, Executors}

import io.shiftleft.codepropertygraph.generated.{EdgeKeys, EdgeTypes, nodes}
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.language._
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.Edge
import overflowdb.traversal.{NodeOps, Traversal}

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

private case class ReachableByTask(sink: nodes.TrackingPoint,
                                   sources: Set[nodes.TrackingPoint],
                                   table: ResultTable,
                                   initialPath: List[PathElement] = List(),
                                   callDepth: Int = 0)

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
    val tasks = sinks.map(sink => ReachableByTask(sink, sourcesSet, new ResultTable))
    solveTasks(tasks, sourcesSet)
  }

  private def solveTasks(tasks: List[ReachableByTask], sources: Set[nodes.TrackingPoint]): List[ReachableByResult] = {

    tasks.foreach(submitTask)
    var result = List[ReachableByResult]()
    while (numberOfTasksRunning > 0) {
      Try {
        completionService.take.get
      } match {
        case Success(resultsOfTask) =>
          numberOfTasksRunning -= 1
          val complete = resultsOfTask.filterNot(_.partial)
          result ++= complete
          newTasksFromResults(resultsOfTask, sources)
            .foreach(submitTask)
        case Failure(exception) =>
          numberOfTasksRunning -= 1
          logger.warn(exception.getMessage)
      }
    }
    result
  }

  private def newTasksFromResults(resultsOfTask: List[ReachableByResult],
                                  sources: Set[nodes.TrackingPoint]): List[ReachableByTask] = {
    tasksForPartialResults(resultsOfTask, sources) ++ tasksForUnresolvedOutArgs(resultsOfTask, sources)
  }

  private def submitTask(task: ReachableByTask): Unit = {
    numberOfTasksRunning += 1
    completionService.submit(new ReachableByCallable(task, context))
  }

  private def tasksForPartialResults(resultsOfTask: List[ReachableByResult],
                                     sources: Set[nodes.TrackingPoint]): List[ReachableByTask] = {
    val partialResults = resultsOfTask.filter(_.partial)
    val pathsFromParams = partialResults.map(x => (x.path, x.callDepth))
    pathsFromParams.flatMap {
      case (path, callDepth) =>
        val param = path.head.node
        Some(param)
          .collect {
            case p: nodes.MethodParameterIn =>
              paramToArgs(p).map { arg =>
                ReachableByTask(arg, sources, new ResultTable, path, callDepth + 1)
              }
          }
          .getOrElse(List())
    }
  }

  private def tasksForUnresolvedOutArgs(resultsOfTask: List[ReachableByResult],
                                        sources: Set[nodes.TrackingPoint]): List[ReachableByTask] = {

    val outArgsAndCalls = resultsOfTask
      .map(x => (x.unresolvedArgs.collect { case e: nodes.Expression => e }, x.path, x.callDepth))
      .distinct

    val forCalls = outArgsAndCalls.flatMap {
      case (args, path, callDepth) =>
        val outCalls = args.collect { case n: nodes.Call => n }
        val methodReturns = outCalls
          .flatMap(NoResolve.getCalledMethods)
          .to(Traversal)
          .methodReturn
        methodReturns.map { ret =>
          ReachableByTask(ret, sources, new ResultTable, path, callDepth + 1)
        }
    }

    val forArgs = outArgsAndCalls.flatMap {
      case (args, path, callDepth) =>
        args.flatMap { arg =>
          argToOutputParams(arg)
            .map(p => ReachableByTask(p, sources, new ResultTable, path, callDepth + 1))
        }
    }

    forCalls ++ forArgs
  }

}

object Engine {

  def expandIn(curNode: nodes.TrackingPoint, path: List[PathElement])(
      implicit semantics: Semantics): List[PathElement] = {
    curNode match {
      case argument: nodes.Expression =>
        val (arguments, nonArguments) = ddgInE(curNode, path).partition(_.outNode().isInstanceOf[nodes.Expression])
        val elemsForArguments = arguments.flatMap { e =>
          val parentNode = e.outNode()
          elemForArgument(parentNode.asInstanceOf[nodes.Expression], argument).map(x =>
            x.copy(inEdgeLabel = Some(e.property(EdgeKeys.VARIABLE)).getOrElse("")))
        }
        val elems = elemsForArguments ++ nonArguments.map(edgeToPathElement)
        elems
      case _ =>
        ddgInE(curNode, path).map(edgeToPathElement)
    }
  }

  private def edgeToPathElement(e: Edge): PathElement = {
    PathElement(e.outNode().asInstanceOf[nodes.TrackingPoint],
                inEdgeLabel = Some(e.property(EdgeKeys.VARIABLE)).getOrElse(""))
  }

  private def ddgInE(dstNode: nodes.TrackingPoint, path: List[PathElement]): List[Edge] = {
    dstNode
      .inE(EdgeTypes.REACHING_DEF)
      .asScala
      .filter(e => e.outNode().isInstanceOf[nodes.TrackingPoint])
      .filter(e => !path.map(_.node).contains(e.outNode().asInstanceOf[nodes.TrackingPoint]))
      .toList
  }

  /**
    * For a given `(parentNode, curNode)` pair, determine whether to expand into
    * `parentNode`. If so, return a corresponding path element or None if
    * `parentNode` should not be followed. The Path element contains a Boolean
    * field to specify whether it should be visible in the flow or not, a decision
    * that can also only be made by looking at both the parent and the child.
    * */
  private def elemForArgument(parentNode: nodes.Expression, curNode: nodes.Expression)(
      implicit semantics: Semantics): Option[PathElement] = {
    val parentNodeCall = parentNode.start.inCall.l
    val sameCallSite = parentNode.start.inCall.l == curNode.start.inCall.l

    if (sameCallSite && parentNode.isUsed && curNode.isDefined ||
        !sameCallSite && curNode.isUsed) {

      val visible = if (sameCallSite) {
        val semanticExists = parentNode.asInstanceOf[nodes.Expression].semanticsForCallByArg.nonEmpty
        val internalMethodsForCall = parentNodeCall.flatMap(methodsForCall).to(Traversal).internal
        semanticExists || internalMethodsForCall.isEmpty
      } else {
        parentNode.isDefined
      }

      Some(PathElement(parentNode, visible))
    } else {
      None
    }
  }

  def argToMethods(arg: nodes.Expression): List[nodes.Method] = {
    arg.start.inCall.l.flatMap { call =>
      methodsForCall(call)
    }
  }

  def argToOutputParams(arg: nodes.Expression): Traversal[nodes.MethodParameterOut] = {
    argToMethods(arg)
      .to(Traversal)
      .parameter
      .asOutput
      .order(arg.order)
  }

  def methodsForCall(call: nodes.Call): List[nodes.Method] = {
    NoResolve.getCalledMethods(call).toList
  }

  def paramToArgs(param: nodes.MethodParameterIn): List[nodes.Expression] =
    NoResolve
      .getMethodCallsites(param.method)
      .to(Traversal)
      .collectAll[nodes.Call]
      .argument(param.order)
      .l

}

case class EngineContext(semantics: Semantics, config: EngineConfig = EngineConfig())
case class EngineConfig(var maxCallDepth: Int = 4)

/**
  * Callable for solving a ReachableByTask
  *
  * A Java Callable is "a task that returns a result and may throw an exception", and this
  * is the callable for calculating the result for `task`.
  *
  * @param task the data flow problem to solve
  * @param context state of the data flow engine
  * */
private class ReachableByCallable(task: ReachableByTask, context: EngineContext)
    extends Callable[List[ReachableByResult]] {

  import Engine._

  /**
    * Entry point of callable.
    * */
  override def call(): List[ReachableByResult] = {
    if (task.callDepth > context.config.maxCallDepth) {
      List()
    } else {
      implicit val sem: Semantics = context.semantics
      results(List(PathElement(task.sink)) ++ task.initialPath, task.sources, task.table)
      task.table.get(task.sink).get.map { r =>
        r.copy(callDepth = task.callDepth)
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
      expandIn(curNode, path).flatMap { parent =>
        table.createFromTable(parent :: path).getOrElse {
          results(parent :: path, sources, table)
          table.get(parent.node).get
        }
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
          if (methodsForCall(call).to(Traversal).internal.nonEmpty && semanticsForCall(call).isEmpty) {
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

  private def semanticsForCall(call: nodes.Call)(implicit semantics: Semantics): List[FlowSemantic] = {
    Engine.methodsForCall(call).flatMap { method =>
      semantics.forMethod(method.fullName)
    }
  }

}
