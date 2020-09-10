package io.shiftleft.semanticcpg.accesspath

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointMethodsBase
import io.shiftleft.semanticcpg.utils.MemberAccess
import scala.jdk.CollectionConverters._

trait TrackedBase
case class TrackedNamedVariable(name: String) extends TrackedBase
case class TrackedReturnValue(call: nodes.CallRepr) extends TrackedBase {
  override def toString: String = {
    s"TrackedReturnValue(${call.code})"
  }
}
case class TrackedLiteral(literal: nodes.Literal) extends TrackedBase {
  override def toString: String = {
    s"TrackedLiteral(${literal.code})"
  }
}
case class TrackedMethodOrTypeRef(methodOrTypeRef: nodes.StoredNode with nodes.HasCode) extends TrackedBase {
  override def toString: String = {
    s"TrackedMethodOrTypeRef(${methodOrTypeRef.code})"
  }
}

object TrackedUnknown extends TrackedBase {
  override def toString: String = {
    "TrackedUnknown"
  }
}
object TrackedFormalReturn extends TrackedBase {
  override def toString: String = {
    "TrackedFormalReturn"
  }
}

object TrackingPointToTrackedBase {
  def apply(node: nodes.TrackingPoint): TrackedBase = node match {
    case node: nodes.MethodParameterIn  => TrackedNamedVariable(node.name)
    case node: nodes.MethodParameterOut => TrackedNamedVariable(node.name)
    case node: nodes.ImplicitCall       => TrackedReturnValue(node)
    case node: nodes.Identifier         => TrackedNamedVariable(node.name)
    case node: nodes.Literal            => TrackedLiteral(node)
    case node: nodes.MethodRef          => TrackedMethodOrTypeRef(node)
    case node: nodes.TypeRef            => TrackedMethodOrTypeRef(node)
    case _: nodes.Return                => TrackedFormalReturn
    case _: nodes.MethodReturn          => TrackedFormalReturn
    case _: nodes.Unknown               => TrackedUnknown
    case block: nodes.Block             => this(TrackingPointMethodsBase.lastExpressionInBlock(block).get)
    case call: nodes.Call               => handleCall(call)
    // FieldIdentifiers are only fake arguments, hence should not be tracked
    case _: nodes.FieldIdentifier => TrackedUnknown
  }

  private def handleCall(call: nodes.Call): TrackedBase = {
    if (MemberAccess.isGenericMemberAccessName(call.name)) {
      val trackingPoint = call
        .out(EdgeTypes.ARGUMENT)
        .asScala
        .find(_.property(NodeKeys.ARGUMENT_INDEX) == 1)
        .get
        .asInstanceOf[nodes.TrackingPoint]
      this(trackingPoint)
    } else {
      TrackedReturnValue(call)
    }
  }
}
