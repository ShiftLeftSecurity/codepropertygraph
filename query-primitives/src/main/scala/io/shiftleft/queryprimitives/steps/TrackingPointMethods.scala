package io.shiftleft.queryprimitives.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, Operators, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.queryprimitives.steps.visitormixins.ExpressionGeneralization
import io.shiftleft.queryprimitives.utils.ExpandTo
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

sealed trait TrackedBase
case class TrackedNamedVariable(name: String) extends TrackedBase
case class TrackedReturnValue(call: nodes.Call) extends TrackedBase
case class TrackedLiteral(literal: nodes.Literal) extends TrackedBase
case class TrackedMethodRef(methodRef: nodes.MethodRef) extends TrackedBase
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

class TrackingPointMethods(val node: nodes.TrackingPoint) extends AnyVal {
  def cfgNode: nodes.CfgNode = {
    node.accept(TrackPointToCfgNode)
  }

  def trackedBase: TrackedBase = {
    node.accept(TrackingPointToTrackedBase)
  }
}

private object TrackPointToCfgNode extends NodeVisitor[nodes.CfgNode] with ExpressionGeneralization[nodes.CfgNode] {
  override def visit(node: nodes.MethodParameterIn): nodes.CfgNode = {
    ExpandTo.parameterToMethod(node).asInstanceOf[nodes.CfgNode]
  }

  override def visit(node: nodes.MethodParameterOut): nodes.CfgNode = {
    val method = ExpandTo.parameterToMethod(node)
    val methodReturn = ExpandTo.methodToFormalReturn(method)
    methodReturn.asInstanceOf[nodes.CfgNode]
  }

  override def visit(node: nodes.MethodReturn): nodes.CfgNode = {
    node
  }

  override def visit(node: nodes.Call): nodes.CfgNode = {
    val callName = node.name
    if (callName == Operators.memberAccess ||
        callName == Operators.indirectMemberAccess ||
        callName == Operators.computedMemberAccess ||
        callName == Operators.indirectComputedMemberAccess ||
        callName == Operators.indirection) {
      ExpandTo.argumentToCallOrReturn(node)
    } else {
      node
    }
  }

  override def visit(node: nodes.Identifier): nodes.CfgNode = {
    ExpandTo.argumentToCallOrReturn(node)
  }

  override def visit(node: nodes.Literal): nodes.CfgNode = {
    ExpandTo.argumentToCallOrReturn(node)
  }

  override def visit(node: nodes.Expression): nodes.CfgNode = {
    node
  }
}

private object TrackingPointToTrackedBase extends NodeVisitor[TrackedBase] {
  override def visit(node: nodes.MethodParameterIn): TrackedBase = {
    TrackedNamedVariable(node.name)
  }

  override def visit(node: nodes.MethodParameterOut): TrackedBase = {
    TrackedNamedVariable(node.name)
  }

  override def visit(node: nodes.MethodReturn): TrackedBase = {
    TrackedFormalReturn
  }

  override def visit(node: nodes.Call): TrackedBase = {
    val callName = node.name
    if (callName == Operators.memberAccess ||
        callName == Operators.indirectMemberAccess ||
        callName == Operators.computedMemberAccess ||
        callName == Operators.indirectComputedMemberAccess ||
        callName == Operators.indirection) {
      node
        .vertices(Direction.OUT, EdgeTypes.AST)
        .asScala
        .find(_.value2(NodeKeys.ARGUMENT_INDEX) == 1)
        .get
        .asInstanceOf[nodes.TrackingPoint]
        .accept(this)
    } else {
      TrackedReturnValue(node)
    }
  }

  override def visit(node: nodes.Identifier): TrackedBase = {
    TrackedNamedVariable(node.name)
  }

  override def visit(node: nodes.Literal): TrackedBase = {
    TrackedLiteral(node)
  }

  override def visit(node: nodes.Return): TrackedBase = {
    TrackedFormalReturn
  }

  override def visit(node: nodes.MethodRef): TrackedBase = {
    TrackedMethodRef(node)
  }

  override def visit(node: nodes.Unknown): TrackedBase = {
    TrackedUnknown
  }
}
