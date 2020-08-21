package io.shiftleft.dataflowengineoss.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
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

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Steps[NodeType]*): NodeSteps[NodeType] = {
    val reachedSources = reachableByInternal(sourceTravs).map(_.reachedSource)
    new NodeSteps[NodeType](__(reachedSources: _*).asInstanceOf[GremlinScala[NodeType]])
  }

  def reachableByFlows[A <: nodes.TrackingPoint](sourceTravs: NodeSteps[A]*): Steps[Path] = {
    val paths = reachableByInternal(sourceTravs).map { result =>
      Path(result.path.filter(_.visible == true).map(_.node))
    }
    new Steps(__(paths: _*))
  }

  private def reachableByInternal[NodeType <: nodes.TrackingPoint](
      sourceTravs: Seq[Steps[NodeType]]): List[ReachableByResult] = {
    val sourceSymbols = sourceTravs
      .flatMap(_.raw.clone.toList)
      .collect { case n: nodes.TrackingPoint => n }
      .toSet

    // Recursive part of this function
    def traverseDdgBack(path: List[PathElement]): List[ReachableByResult] = {
      val pathHead = path.head
      val node = pathHead.node

      val resultsForNode = Some(node)
        .filter(n => sourceSymbols.contains(n.asInstanceOf[NodeType]))
        .map { n =>
          new ReachableByResult(n, path)
        }
        .toList

      val ddgParents = ddgIn(node).filter(parent => !path.map(_.node).contains(parent))

      val dstParentCall = argToCall(node)
      val resultsForParents = ddgParents.flatMap { srcNode =>
        dstParentCall match {
          case Some(_) =>
            srcNode match {
              case _: nodes.Expression =>
                if (argToCall(srcNode) == dstParentCall) {
                  val used = isUsed(srcNode)
                  val defined = isDefined(node)

                  List(srcNode)
                    .filter(_ => used)
                    .filter(_ => defined)
                    .flatMap(x => traverseDdgBack(PathElement(x) :: pathHead :: path.tail))
                } else {
                  val defined = isDefined(srcNode)
                  val used = isUsed(node)
                  if (!used) {
                    List()
                  } else if (defined) {
                    List(srcNode).flatMap(x => traverseDdgBack(PathElement(x) :: pathHead :: path.tail))
                  } else {
                    traverseDdgBack(PathElement(srcNode, visible = false) :: pathHead :: path.tail)
                  }
                }
              case x =>
                traverseDdgBack(PathElement(x) :: pathHead :: path.tail)
            }
          case None =>
            traverseDdgBack(PathElement(srcNode) :: pathHead :: path.tail)
        }

      }

      (resultsForParents ++ resultsForNode).toList
    }

    val sinkSymbols = raw.clone.dedup.toList.sortBy { _.id.asInstanceOf[java.lang.Long] }
    sinkSymbols.flatMap(s => traverseDdgBack(List(PathElement(s))))
  }

  private def ddgIn(dstNode: nodes.TrackingPoint): Iterator[nodes.TrackingPoint] = {
    dstNode._reachingDefIn().asScala.collect { case n: nodes.TrackingPoint => n }
  }

  private def isUsed(srcNode: nodes.StoredNode) = srcNode match {
    case arg: nodes.Expression =>
      val methods = argToMethods(arg)
      if (methods.nonEmpty && !methods.exists { m =>
            hasAnnotation(m)
          }) {
        true
      } else {
        methods.exists { method =>
          method.parameter.order(arg.order).l.exists(p => p._propagateOut().hasNext)
        }
      }
    case _ => true
  }

  private def isDefined(srcNode: nodes.StoredNode) = srcNode match {
    case arg: nodes.Expression =>
      val methods = argToMethods(arg)
      if (methods.nonEmpty && !methods.exists { m =>
            hasAnnotation(m)
          }) {
        true
      } else {
        methods.exists { method =>
          method.parameter.asOutput.order(arg.order).l.exists(p => p._propagateIn().hasNext)
        }
      }
    case _ => true
  }

  private def hasAnnotation(method: nodes.Method): Boolean = {
    method.parameter.l.exists(x => x._propagateOut().hasNext)
  }

  private def argToMethods(arg: nodes.Expression) = {
    argToCall(arg).toList.flatMap { call =>
      methodsForCall(call)
    }
  }

  private def argToCall(n: nodes.TrackingPoint) =
    n._argumentIn.asScala.collectFirst { case c: nodes.Call => c }

  private def argToParamOut(arg: nodes.Expression) = {
    argToCall(arg).toList.flatMap(x => methodsForCall(x).start.parameter.asOutput.order(arg.order))
  }

  private def methodsForCall(call: nodes.Call): List[nodes.Method] = {
    NoResolve.getCalledMethods(call).toList
  }

}

private class ReachableByResult(val reachedSource: nodes.TrackingPoint, val path: List[PathElement]) {
  override def clone(): ReachableByResult = {
    new ReachableByResult(reachedSource, path)
  }
}
