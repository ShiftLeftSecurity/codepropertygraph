package io.shiftleft.dataflowengineoss.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

case class PathElement(node: nodes.TrackingPoint, visible: Boolean = true)

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
    val reachedSources = reachableByInternal(sourceTravs).map(_.reachedSource)
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

    val cache = new ResultCache

    val sourceSymbols = sourceTravs
      .flatMap(_.raw.clone.toList)
      .collect { case n: nodes.TrackingPoint => n }
      .toSet

    /**
      * Recursively expand the DDG backwards and return a list of all
      * results, given by at least a source node in `sourceSymbols` and the
      * path between the source symbol and the sink.
      *
      * @param path This is a path from a node to the sink. The first node
      *             of the path is expanded by this method
      * */
    def results(path: List[PathElement]): List[ReachableByResult] = {
      val curNode = path.head.node

      val resultsForParents: List[(nodes.StoredNode, List[ReachableByResult])] = {
        val ddgParents = ddgIn(curNode).filter(parent => !path.map(_.node).contains(parent)).toList
        val newPathElems = if (argToCall(curNode).isEmpty) {
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

        def fetchOrComputeResults(pathElement: PathElement): (nodes.TrackingPoint, List[ReachableByResult]) = {
          cache.createFromCache(pathElement, path).getOrElse {
            pathElement.node -> results(pathElement :: path)
          }
        }

        newPathElems.map(fetchOrComputeResults)
      }

      val resultsForCurNode = Some(curNode).collect {
        case n if sourceSymbols.contains(n.asInstanceOf[NodeType]) =>
          new ReachableByResult(n, path)
      }.toList

      val resultsForCache = resultsForParents ++ resultsForCurNode.map { r =>
        curNode -> List(r)
      }

      cache.addAll(resultsForCache)
      resultsForParents.flatMap(_._2) ++ resultsForCurNode
    }

    val sinkSymbols = raw.clone.dedup.toList.sortBy { _.id.asInstanceOf[java.lang.Long] }
    sinkSymbols.flatMap(s => results(List(PathElement(s))))
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
        // TODO There is no semantic and we can resolve the method, so, we'll have to
        // analyze it, that is, we compute the result for the output parameter that
        // corresponds to the argument.
        Some(PathElement(parentNode)).filter(_ => isUsed(parentNode) && isDefined(curNode))
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

private class ReachableByResult(val reachedSource: nodes.TrackingPoint, val path: List[PathElement]) {
  override def clone(): ReachableByResult = {
    new ReachableByResult(reachedSource, path)
  }
}

private class ResultCache {

  private val cache = new java.util.concurrent.ConcurrentHashMap[nodes.StoredNode, List[ReachableByResult]].asScala

  def addAll(resultsForCache: List[(nodes.StoredNode, List[ReachableByResult])]): Unit = {
    val resultsWithShortenedPaths = resultsForCache.map {
      case (n, res) =>
        n -> res.map { r =>
          val shortenedPath = r.path.slice(0, r.path.map(_.node).indexOf(n))
          new ReachableByResult(r.reachedSource, shortenedPath)
        }
    }
    cache.addAll(resultsWithShortenedPaths)
  }

  def createFromCache(pathElement: PathElement,
                      path: List[PathElement]): Option[(nodes.TrackingPoint, List[ReachableByResult])] = {
    cache.get(pathElement.node).map { res =>
      pathElement.node -> res.map { r =>
        val newPath = r.path ++ (pathElement :: path)
        new ReachableByResult(r.reachedSource, newPath)
      }
    }
  }

}
