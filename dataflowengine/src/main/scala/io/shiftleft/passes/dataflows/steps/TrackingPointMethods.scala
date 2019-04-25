package io.shiftleft.passes.dataflows.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, Operators, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.queryprimitives.steps.visitormixins.ExpressionGeneralization
import io.shiftleft.queryprimitives.utils.ExpandTo
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class TrackingPointMethods(val node: nodes.TrackingPointBase) extends AnyVal {
  def cfgNode: nodes.CfgNode = {
    node.accept(TrackPointToCfgNode)
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
