package io.shiftleft.dataflowengineoss.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

case class PathElement(node: nodes.TrackingPoint, visible: Boolean = true, resolved: Boolean = true)

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
    val res = sinks.flatMap { sink =>
      val cache = new ResultCache
      results(List(PathElement(sink)), sources, cache)
    }

    res
  }

  /**
    * Recursively expand the DDG backwards and return a list of all
    * results, given by at least a source node in `sourceSymbols` and the
    * path between the source symbol and the sink.
    *
    * @param path This is a path from a node to the sink. The first node
    *             of the path is expanded by this method
    * */
  private def results[NodeType <: nodes.TrackingPoint](
      path: List[PathElement],
      sources: Set[NodeType],
      cache: ResultCache)(implicit semantics: Semantics): List[ReachableByResult] = {
    val curNode = path.head.node

    val resultsForParents: List[(nodes.StoredNode, List[ReachableByResult])] = {
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

      parentPathElements.map { parent =>
        cache.createFromCache(parent, path).getOrElse {
          parent.node -> results(parent :: path, sources, cache)
        }
      }
    }

    val resultsForCurNode = Some(curNode).collect {
      case n if sources.contains(n.asInstanceOf[NodeType]) =>
        ReachableByResult(path)
    }.toList

    val nodeToResults = resultsForParents ++ resultsForCurNode.map { r =>
      curNode -> List(r)
    }

    cache.addAll(nodeToResults)
    nodeToResults.flatMap(_._2)
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

private class ResultCache {

  private val cache = new java.util.concurrent.ConcurrentHashMap[nodes.StoredNode, List[ReachableByResult]].asScala

  /**
    * Add results to cache in a compressed form.
    * */
  def addAll(results: List[(nodes.StoredNode, List[ReachableByResult])]): Unit = {
    val resultsWithShortenedPaths = results.map {
      case (keyNode, res) =>
        keyNode -> res.map { r =>
          // This is the path to the key node, excluding the key node
          val pathToKeyNode = r.path.slice(0, r.path.map(_.node).indexOf(keyNode))
          r.copy(path = pathToKeyNode)
        }
    }
    cache.addAll(resultsWithShortenedPaths)
  }

  /**
    * For a parent, given in the form of a path element, and the path
    * to the current (child) node, check if a result exists for the parent,
    * and if so, calculate a result for parent :: path, that is, the path
    * from the parent all, via the current node, all the way back to the
    * sink.
    * */
  def createFromCache(parent: PathElement,
                      path: List[PathElement]): Option[(nodes.TrackingPoint, List[ReachableByResult])] = {
    cache.get(parent.node).map { res =>
      parent.node -> res.map { r =>
        val completePath = r.path ++ (parent :: path)
        r.copy(path = completePath)
      }
    }
  }

}
