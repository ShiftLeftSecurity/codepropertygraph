package io.shiftleft.queryprimitives.steps.visitormixins

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor

trait ExpressionGeneralization[T] extends NodeVisitor[T] {
  override def visit(node: nodes.LiteralRef): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.CallRef): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.IdentifierRef): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.ReturnRef): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.BlockRef): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.MethodReferenceRef): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }

  override def visit(node: nodes.UnknownRef): T = {
    visit(node.asInstanceOf[nodes.Expression])
  }
}
