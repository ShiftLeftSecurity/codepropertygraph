package io.shiftleft.queryprimitives.steps.nodemethods.generalizations

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor

trait ExpressionGeneralization[T] extends NodeVisitor[T] {
  override def visit(node: nodes.Literal): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.Call): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.Identifier): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.Return): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.Block): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.MethodRef): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.Unknown): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }
}
