package io.shiftleft.queryprimitives.steps.visitormixins

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor

/**
  * Methods for single Cfg nodes.
  * */
class CfgNodeMethods(val node: nodes.CfgNode) extends AnyVal {
  def code: String = node.accept(CfgNodeToCode)
}

/**
  * Lookup of `code` field for different node types.
  * */
private object CfgNodeToCode extends NodeVisitor[String] with ExpressionGeneralization[String] {

  override def visit(node: nodes.Expression): String = node.code

  override def visit(node: nodes.Method): String = node.name

  override def visit(node: nodes.MethodReturn): String = node.code
}
