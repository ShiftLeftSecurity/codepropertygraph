package io.shiftleft.queryprimitives.steps.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.queryprimitives.steps.nodemethods.generalizations.ExpressionGeneralization

class CfgNodeMethods(val node: nodes.CfgNode) extends AnyVal {
  def code: String = node.accept(CfgNodeToCode)
}

private object CfgNodeToCode extends NodeVisitor[String] with ExpressionGeneralization[String] {

  // This is confusing: it means that if we all `.code` on a CfgNode,
  // which is a Method, then we actually obtain `.name`
  // It seems that this behavior is only required in the pretty-printer of the OSS
  // data flow tracker. I would suggest we use a match instead.

  override def visit(node: nodes.Method): String = node.name

  override def visit(node: nodes.Expression): String = node.code

  override def visit(node: nodes.MethodReturn): String = node.code
}
