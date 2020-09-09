package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.TrackingPoint
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.semanticcpg.accesspath.{Elements, MemberAccessToElement}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess

import scala.jdk.CollectionConverters._

// Utility methods for dealing with tracking points

object TrackingPointMethodsBase {
  def lastExpressionInBlock(block: nodes.Block): Option[nodes.Expression] =
    block._astOut.asScala
      .collect {
        case node: nodes.Expression if !node.isInstanceOf[nodes.Local] && !node.isInstanceOf[nodes.Method] => node
      }
      .toVector
      .sortBy(_.order)
      .lastOption
}

object TrackingPointToCfgNode {
  def apply(node: nodes.TrackingPointBase): nodes.CfgNode = {
    applyInternal(node, _.parentExpression.get)
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
        applyInternal(TrackingPointMethodsBase.lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }
}

object TrackingPointToElements {

  def apply(node: nodes.TrackingPoint): Elements = {
    convertRealNode(node)
  }

  private def convertRealNode(node: nodes.TrackingPoint): Elements = {
    node match {
      case call: nodes.Call => convertCall(call)
      case block: nodes.Block =>
        val lastInBlock = TrackingPointMethodsBase.lastExpressionInBlock(block).get
        TrackingPointToElements.apply(lastInBlock)
      case _: nodes.MethodParameterIn  => Elements()
      case _: nodes.MethodParameterOut => Elements()
      case _: nodes.MethodReturn       => Elements()
      case _: nodes.ImplicitCall       => Elements()
      case _: nodes.Expression         => Elements()
      case _: nodes.TrackingPoint      => Elements()
    }
  }

  private def firstArgument(call: nodes.Call): TrackingPoint = {
    call
      .out(EdgeTypes.ARGUMENT)
      .asScala
      .find(_.property(NodeKeys.ARGUMENT_INDEX) == 1)
      .get
      .asInstanceOf[nodes.TrackingPoint]
  }

  private def convertCall(call: nodes.Call): Elements = {
    if (MemberAccess.isGenericMemberAccessName(call.name)) {
      val baseElements = TrackingPointToElements.apply(firstArgument(call))
      baseElements ++ MemberAccessToElement.convert(call)
    } else {
      Elements()
    }
  }
}
