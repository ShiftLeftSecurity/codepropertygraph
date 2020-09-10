package io.shiftleft.semanticcpg.accesspath

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.TrackingPoint
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointMethodsBase
import io.shiftleft.semanticcpg.utils.MemberAccess
import scala.jdk.CollectionConverters._

object TrackingPointToElements {

  def apply(node: nodes.TrackingPoint): Elements = {
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
      baseElements ++ MemberAccessToElement(call)
    } else {
      Elements()
    }
  }
}

object TrackingPointToAccessPath {

  def apply(node: nodes.TrackingPoint, exclusions: List[Elements] = List()): AccessPath = {
    new AccessPath(TrackingPointToElements(node), exclusions)
  }

}
