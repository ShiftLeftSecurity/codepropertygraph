package io.shiftleft.dataflowengineoss.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
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

    val sourceSymbols = sourceTravs
      .flatMap(_.raw.clone.toList)
      .collect { case n: nodes.TrackingPoint => n }
      .toSet

    // Recursive part of this function
    def traverseDdgBack(path: List[PathElement]): List[ReachableByResult] = {
      val curNode = path.head.node

      val resultsForCurNode = Some(curNode).collect {
        case n if sourceSymbols.contains(n.asInstanceOf[NodeType]) =>
          new ReachableByResult(n, path)
      }.toList

      val resultsForParents = {
        val ddgParents = ddgIn(curNode).filter(parent => !path.map(_.node).contains(parent)).toList
        val newPathElems = if (argToCall(curNode).isEmpty) {
          ddgParents.flatMap { srcNode =>
            List(PathElement(srcNode))
          }
        } else {
          val (expressions, nonExpressions) = ddgParents.partition(_.isInstanceOf[nodes.Expression])
          val elemsForExpressions = expressions.flatMap { srcNode: nodes.TrackingPoint =>
            if (argToCall(srcNode) == argToCall(curNode)) {
              List(PathElement(srcNode)).filter(_ => isUsed(srcNode) && isDefined(curNode))
            } else {
              val visible = isDefined(srcNode)
              List(PathElement(srcNode, visible)).filter(_ => isUsed(curNode))
            }
          }
          val elemsForNonExpressions = nonExpressions.map(x => PathElement(x))
          elemsForExpressions ++ elemsForNonExpressions
        }
        newPathElems.flatMap(e => traverseDdgBack(e :: path))
      }
      (resultsForParents ++ resultsForCurNode)
    }

    val sinkSymbols = raw.clone.dedup.toList.sortBy { _.id.asInstanceOf[java.lang.Long] }
    sinkSymbols.flatMap(s => traverseDdgBack(List(PathElement(s))))
  }

  private def ddgIn(dstNode: nodes.TrackingPoint): Iterator[nodes.TrackingPoint] = {
    dstNode._reachingDefIn().asScala.collect { case n: nodes.TrackingPoint => n }
  }

  private def isUsed(srcNode: nodes.StoredNode)(implicit semantics: Semantics) = {
    Some(srcNode)
      .collect {
        case arg: nodes.Expression =>
          val methods = argToMethods(arg)
          atLeastOneMethodHasAnnotation(methods) || {
            methods.exists { method =>
              semantics
                .forMethod(method.fullName)
                .exists(_.mappings.exists { case (srcIndex, _) => srcIndex == arg.order })
            }
          }
      }
      .getOrElse(true)
  }

  private def isDefined(srcNode: nodes.StoredNode)(implicit semantics: Semantics) = {
    Some(srcNode)
      .collect {
        case arg: nodes.Expression =>
          val methods = argToMethods(arg)
          atLeastOneMethodHasAnnotation(methods) || {
            methods.exists { method =>
              semantics
                .forMethod(method.fullName)
                .exists(_.mappings.exists { case (_, dstIndex) => dstIndex == arg.order })
            }
          }
      }
      .getOrElse(true)
  }

  private def atLeastOneMethodHasAnnotation(methods: List[nodes.Method])(implicit semantics: Semantics): Boolean = {
    methods.nonEmpty && !methods.exists { m =>
      hasAnnotation(m)
    }
  }

  private def hasAnnotation(method: nodes.Method)(implicit semantics: Semantics): Boolean = {
    semantics.forMethod(method.fullName).isDefined
  }

  private def argToMethods(arg: nodes.Expression) = {
    argToCall(arg).toList.flatMap { call =>
      methodsForCall(call)
    }
  }

  private def argToCall(n: nodes.TrackingPoint) =
    n._argumentIn.asScala.collectFirst { case c: nodes.Call => c }

  private def methodsForCall(call: nodes.Call): List[nodes.Method] = {
    NoResolve.getCalledMethods(call).toList
  }

}

private class ReachableByResult(val reachedSource: nodes.TrackingPoint, val path: List[PathElement]) {
  override def clone(): ReachableByResult = {
    new ReachableByResult(reachedSource, path)
  }
}
