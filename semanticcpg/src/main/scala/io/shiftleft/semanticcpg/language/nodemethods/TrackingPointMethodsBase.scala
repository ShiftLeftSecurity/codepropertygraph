package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.accesspath.{
  AccessElement,
  AccessPath,
  Elements,
  TrackedBase,
  TrackedReturnValue,
  TrackedUnknown
}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
import org.slf4j.LoggerFactory

/* The closed source tracker sometimes needs to opt out of the default handling of accessPaths.
 * Instead of having two versions of the toTrackingPoint / toAccessPath functions, we define an interface to opt out.
 *
 * The open-sourced parts of the code currently have nothing that implements FakeTrackingPoint.
 * */
trait FakeTrackingPoint {
  def trackedBaseAndAccessPathOverride: (TrackedBase, AccessPath)
  def trackedCfgNodeOverride: nodes.CfgNode
}

object AccessPathUsageCommercial {

  private val logger = LoggerFactory.getLogger(getClass)

  //we don't want to expose this API everywhere, only when explicitly imported
  implicit class ImplicitsAPI(val node: nodes.TrackingPoint) extends AnyVal {
    def trackedBaseAndAccessPath: (TrackedBase, AccessPath) = toTrackedBaseAndAccessPath(node)
    def trackedBaseAndElements: (TrackedBase, Elements) = {
      val (base, path) = trackedBaseAndAccessPath
      (base, path.elements)
    }
  }

  def toTrackedBaseAndAccessPath(node: nodes.StoredNode): (TrackedBase, AccessPath) = {
    // assume: node isa nodes.TrackingPoint
    node match {
      case ftp: FakeTrackingPoint =>
        ftp.trackedBaseAndAccessPathOverride
      case _ =>
        toTrackedBaseAndAccessPathSimple(node)
    }
  }

  def toTrackedBaseAndAccessPathSimple(node: nodes.StoredNode): (TrackedBase, AccessPath) = {
    val (base, revPath) = toTrackedBaseAndAccessPathInternal(node)
    (base, AccessPath.apply(Elements.normalized(revPath.reverse), Nil))
  }

  def toTrackedBase(node: nodes.StoredNode): TrackedBase = toTrackedBaseAndAccessPath(node)._1
  def toTrackedAccessPath(node: nodes.StoredNode): AccessPath = toTrackedBaseAndAccessPath(node)._2

  private def toTrackedBaseAndAccessPathInternal(node: nodes.StoredNode): (TrackedBase, List[AccessElement]) = {
    val result = AccessPathHandling.leafToTrackedBaseAndAccessPathInternal(node)
    if (result.isDefined) {
      result.get
    } else {
      node match {
        case node: nodes.ImplicitCall => (TrackedReturnValue(node), Nil)
        case node: nodes.PostExecutionCall =>
          toTrackedBaseAndAccessPathInternal(node._refOut.next().asInstanceOf[nodes.TrackingPoint])

        case block: nodes.Block =>
          AccessPathHandling
            .lastExpressionInBlock(block)
            .map { toTrackedBaseAndAccessPathInternal }
            .getOrElse((TrackedUnknown, Nil))
        case call: nodes.Call if !MemberAccess.isGenericMemberAccessName(call.name) => (TrackedReturnValue(call), Nil)

        case memberAccess: nodes.Call =>
          //assume: MemberAccess.isGenericMemberAccessName(call.name)
          //FIXME: elevate debug to warn once csharp2cpg has managed to migrate.
          val argOne = memberAccess.argumentOption(1)
          if (argOne.isEmpty) {
            logger.warn(s"Missing first argument on call ${memberAccess}.")
            return (TrackedUnknown, Nil)
          }
          val (base, tail) = toTrackedBaseAndAccessPathInternal(argOne.get)
          val path = AccessPathHandling.memberAccessToPath(memberAccess, tail)
          (base, path)
      }
    }
  }
}

// Utility methods for dealing with tracking points
object TrackingPointMethodsBase {

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
        toCfgNodeInternal(AccessPathHandling.lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }

}

object TrackingPointToCfgNode {
  def apply(node: nodes.TrackingPointBase): nodes.CfgNode = {
    node match {
      case ftp: FakeTrackingPoint => ftp.trackedCfgNodeOverride
      case _                      => applyInternal(node, _.parentExpression.get)
    }

  }

  @scala.annotation.tailrec
  private def applyInternal(node: nodes.TrackingPointBase,
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
        applyInternal(AccessPathHandling.lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }
}
