package io.shiftleft.dataflowengineoss.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import io.shiftleft.semanticcpg.language._

import scala.collection.parallel.CollectionConverters._
import scala.jdk.CollectionConverters._

case class PathElement(node: nodes.TrackingPoint, visible: Boolean = true, resolved: Boolean = true)

private case class ReachableByTask[NodeType <: nodes.TrackingPoint](sink: nodes.TrackingPoint,
                                                                    sources: Set[NodeType],
                                                                    table: ResultTable)

/**
  * Base class for nodes that can occur in data flows
  * */
class TrackingPoint(val wrapped: NodeSteps[nodes.TrackingPoint]) extends AnyVal {
  private def raw: GremlinScala[nodes.TrackingPoint] = wrapped.raw

  /**
    * The enclosing method of the tracking point
    * */
  def method: NodeSteps[nodes.Method] = wrapped.map(_.method)

  /**
    * Convert to nearest CFG node
    * */
  def cfgNode: NodeSteps[nodes.CfgNode] = wrapped.map(_.cfgNode)

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Steps[NodeType]*)(
      implicit semantics: Semantics): NodeSteps[NodeType] = {
    val reachedSources = reachableByInternal(sourceTravs).map(_.source)
    new NodeSteps[NodeType](__(reachedSources: _*).asInstanceOf[GremlinScala[NodeType]])
  }

  def reachableByFlows[A <: nodes.TrackingPoint](sourceTravs: NodeSteps[A]*)(
      implicit semantics: Semantics): Steps[Path] = {
    val paths = reachableByInternal(sourceTravs).map { result =>
      Path(result.path.filter(_.visible == true).map(_.node))
    }
    new Steps(__(paths: _*))
  }

  private def reachableByInternal[NodeType <: nodes.TrackingPoint](sourceTravs: Seq[Steps[NodeType]])(
      implicit semantics: Semantics): List[ReachableByResult] = {

    val sources = sourceTravs
      .flatMap(_.raw.clone.toList)
      .collect { case n: nodes.TrackingPoint => n }
      .toSet

    val sinks = raw.clone.dedup.toList.sortBy(_.id2)

    val tasks = sinks.map { sink =>
      ReachableByTask(sink, sources, new ResultTable)
    }

    tasks.par.flatMap { task =>
      results(List(PathElement(task.sink)), task.sources, task.table)
      task.table.get(task.sink).get
    }.toList
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
        table.createFromTable(parent :: path).map(_._2).getOrElse {
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

/**
  * A partial result, informing about a path that exists from a source to another
  * node in the graph.
  *
  * @param path this is the main result - a known path
  *
  * */
private case class ReachableByResult(path: List[PathElement]) {

  def source: nodes.TrackingPoint = path.head.node

  def unresolvedArgs: List[nodes.TrackingPoint] = path.collect {
    case elem if !elem.resolved =>
      elem.node
  }

}

private class ResultTable {

  private val table = new java.util.concurrent.ConcurrentHashMap[nodes.StoredNode, List[ReachableByResult]].asScala

  def add(key: nodes.StoredNode, value: List[ReachableByResult]): Unit = {
    table.asJava.compute(key, { (_, existingValue) =>
      Option(existingValue).toList.flatten ++ value
    })
  }

  /**
    * For a given path, determine whether the first element (`first`) is in the cache, and if so,
    * for each result stored in the cache, determine the path up to `first` and prepend it to
    * `path`, giving us a new result via table lookup.
    * */
  def createFromTable(path: List[PathElement]): Option[(nodes.TrackingPoint, List[ReachableByResult])] = {
    val first = path.head
    table.get(first.node).map { res =>
      first.node -> res.map { r =>
        val completePath = r.path.slice(0, r.path.map(_.node).indexOf(first.node)) ++ path
        r.copy(path = completePath)
      }
    }
  }

  def get(node: nodes.StoredNode): Option[List[ReachableByResult]] = {
    table.get(node)
  }

}
