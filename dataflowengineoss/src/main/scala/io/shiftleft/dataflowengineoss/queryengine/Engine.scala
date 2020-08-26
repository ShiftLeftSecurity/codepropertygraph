package io.shiftleft.dataflowengineoss.queryengine

import java.util.concurrent.{Callable, ExecutorCompletionService, Executors}

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import io.shiftleft.semanticcpg.language.NoResolve
import org.slf4j.{Logger, LoggerFactory}

import scala.util.{Failure, Success, Try}
import scala.jdk.CollectionConverters._

private case class ReachableByTask(sink: nodes.TrackingPoint, sources: Set[nodes.TrackingPoint], table: ResultTable)

class Engine {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  /**
    * Determine flows from sources to sinks by analyzing backwards from sinks.
    * */
  def determineFlowsBackwards(sinks: List[nodes.TrackingPoint], sources: List[nodes.TrackingPoint])(
      implicit semantics: Semantics): List[ReachableByResult] = {
    val sourcesSet = sources.toSet
    val tasks = sinks.map { sink =>
      ReachableByTask(sink, sourcesSet, new ResultTable)
    }

    val executorService = Executors.newWorkStealingPool()
    val completionService = new ExecutorCompletionService[List[ReachableByResult]](executorService)
    tasks.foreach { task =>
      completionService.submit(new ReachableByCallable(task, semantics))
    }

    var result = List[ReachableByResult]()
    for (_ <- 1 to tasks.size) {
      Try {
        completionService.take.get
      } match {
        case Success(list) =>
          result ++= list
        case Failure(exception) =>
          logger.warn(exception.getMessage)
      }
    }
    result
  }

}

private class ReachableByCallable(task: ReachableByTask, semantics: Semantics)
    extends Callable[List[ReachableByResult]] {

  override def call(): List[ReachableByResult] = {
    implicit val sem: Semantics = semantics
    results(List(PathElement(task.sink)), task.sources, task.table)
    task.table.get(task.sink).get
  }

  /**
    * Recursively expand the DDG backwards and return a list of all
    * results, given by at least a source node in `sourceSymbols` and the
    * path between the source symbol and the sink.
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
      val parentPathElements = if (argToCall(curNode).isEmpty) {
        ddgParents.flatMap { parentNode =>
          List(PathElement(parentNode))
        }
      } else {
        val (arguments, nonArguments) = ddgParents.partition(_.isInstanceOf[nodes.Expression])
        val elemsForArguments = arguments.flatMap { parentNode =>
          elemForArgument(parentNode.asInstanceOf[nodes.Expression], curNode)
        }
        elemsForArguments ++ nonArguments.map(parentNode => PathElement(parentNode))
      }

      parentPathElements.flatMap { parent =>
        table.createFromTable(parent :: path).getOrElse {
          results(parent :: path, sources, table)
          table.get(parent.node).get
        }
      }
    }

    val resultsForCurNode = Some(curNode).collect {
      case n if sources.contains(n.asInstanceOf[NodeType]) =>
        ReachableByResult(path)
    }.toList

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
      val callers = parentNodeCall.toList
        .flatMap(x => methodsForCall(x))
      if (semanticsForCallByArg(parentNode.asInstanceOf[nodes.Expression]).nonEmpty || callers.isEmpty) {
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

  private def methodsForCall(call: nodes.Call): List[nodes.Method] = {
    NoResolve.getCalledMethods(call).toList
  }

  private def argToMethods(arg: nodes.Expression) = {
    argToCall(arg).toList.flatMap { call =>
      methodsForCall(call)
    }
  }

  private def argToCall(n: nodes.TrackingPoint) =
    n._argumentIn.asScala.collectFirst { case c: nodes.Call => c }

}
