package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.accesspath.{AccessElement, AccessPath, Elements, TrackedBase, TrackedReturnValue}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._

/* The closed source tracker sometimes needs to opt out of the default handling of accessPaths.
 * Instead of having two versions of the toTrackingPoint / toAccessPath functions, we define an interface to opt out.
 *
 * The open-sourced parts of the code currently have nothing that implements FakeTrackingPoint.
 * */
trait FakeTrackingPoint {
  def trackedBaseAndAccessPathOverride: (TrackedBase, AccessPath)
  def trackedCfgNodeOverride: nodes.CfgNode
}

// Utility methods for dealing with tracking points
object TrackingPointMethodsBase {

  //we don't want to expose this API everywhere, only when explicitly imported
  implicit class ImplicitsAPI(val node: nodes.TrackingPoint) extends AnyVal {
    def trackedBaseAndAccessPath: (TrackedBase, AccessPath) = toTrackedBaseAndAccessPath(node)
    def trackedBaseAndElements: (TrackedBase, Elements) = {
      val (base, path) = trackedBaseAndAccessPath
      (base, path.elements)
    }
  }

  def lastExpressionInBlock(block: nodes.Block): Option[nodes.Expression] =
    block._astOut.asScala
      .collect {
        case node: nodes.Expression if !node.isInstanceOf[nodes.Local] && !node.isInstanceOf[nodes.Method] => node
      }
      .toVector
      .sortBy(_.order)
      .lastOption
  def toCfgNode(node: nodes.TrackingPointBase): nodes.CfgNode = {
    node match {
      case ftp: FakeTrackingPoint => ftp.trackedCfgNodeOverride
      case _                      => toCfgNodeInternal(node, _.parentExpression.get)
    }
  }
  @scala.annotation.tailrec
  private def toCfgNodeInternal(node: nodes.TrackingPointBase,
                                parentExpansion: nodes.Expression => nodes.Expression): nodes.CfgNode = {

    node match {
      case node: nodes.Identifier => parentExpansion(node)
      case node: nodes.MethodRef  => parentExpansion(node)
      case node: nodes.TypeRef    => parentExpansion(node)
      case node: nodes.Literal    => parentExpansion(node)

      case node: nodes.MethodParameterIn => node.method

      case node: nodes.MethodParameterOut =>
        node.method.methodReturn

      case node: nodes.Call if MemberAccess.isGenericMemberAccessName(node.name) =>
        parentExpansion(node)

      case node: nodes.Call         => node
      case node: nodes.ImplicitCall => node
      case node: nodes.MethodReturn => node
      case block: nodes.Block       =>
        // Just taking the lastExpressionInBlock is not quite correct because a BLOCK could have
        // different return expressions. So we would need to expand via CFG.
        // But currently the frontends do not even put the BLOCK into the CFG so this is the best
        // we can do.
        toCfgNodeInternal(TrackingPointMethodsBase.lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }

  def toTrackedBaseAndAccessPath(node: nodes.TrackingPoint): (TrackedBase, AccessPath) = {
    node match {
      case ftp: FakeTrackingPoint =>
        ftp.trackedBaseAndAccessPathOverride
      case _ =>
        val (base, revPath) = toTrackedBaseAndAccessPathInternal(node)
        (base, AccessPath.apply(Elements.normalized(revPath.reverse), Nil))
    }
  }

  def toTrackedBase(node: nodes.TrackingPoint): TrackedBase = toTrackedBaseAndAccessPath(node)._1
  def toTrackedAccessPath(node: nodes.TrackingPoint): AccessPath = toTrackedBaseAndAccessPath(node)._2

  private def toTrackedBaseAndAccessPathInternal(node: nodes.TrackingPoint): (TrackedBase, List[AccessElement]) = {

    node match {
      case node: nodes.ImplicitCall => (TrackedReturnValue(node), Nil)
      case node: nodes.PostExecutionCall =>
        toTrackedBaseAndAccessPathInternal(node._refOut.next().asInstanceOf[nodes.TrackingPoint])
      case cfgNode: nodes.CfgNode =>
        cfgNode.trackedBaseAccessPath({ x =>
          toTrackedBaseAndAccessPathInternal(x.asInstanceOf[nodes.TrackingPoint])
        })
    }
  }

}

object TrackingPointToCfgNode {
  private val logger = LoggerFactory.getLogger(getClass)
  def apply(node: nodes.TrackingPointBase): nodes.CfgNode = {
    node match {
      case ftp: FakeTrackingPoint => ftp.trackedCfgNodeOverride
      case cfgNode: nodes.CfgNode => cfgNode.statement
      case _                      =>
        // This case existed before, it just wasn't handled and would
        // crash immediately instead.
        logger.warn("Attempt to convert tracking point to CfgNode failed")
        null
    }
  }

}
