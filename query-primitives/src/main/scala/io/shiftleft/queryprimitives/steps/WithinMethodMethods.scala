package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.queryprimitives.steps.visitormixins.ExpressionGeneralization
import io.shiftleft.queryprimitives.steps.Implicits._
import org.apache.tinkerpop.gremlin.structure.Direction


class WithinMethodMethods(val node: nodes.WithinMethod) extends AnyVal {
  def method: nodes.Method = {
    node.accept(WithinMethodToMethod)
  }
}

private object WithinMethodToMethod extends NodeVisitor[nodes.Method] with ExpressionGeneralization[nodes.Method] {
  override def visit(node: nodes.Method): nodes.Method = {
    node
  }

  override def visit(node: nodes.MethodParameterIn): nodes.Method = {
    node.vertices(Direction.IN, EdgeTypes.AST).nextChecked.asInstanceOf[nodes.Method]
  }

  override def visit(node: nodes.MethodParameterOut): nodes.Method = {
    node.vertices(Direction.IN, EdgeTypes.AST).nextChecked.asInstanceOf[nodes.Method]
  }

  override def visit(node: nodes.MethodReturn): nodes.Method = {
    node.vertices(Direction.IN, EdgeTypes.AST).nextChecked.asInstanceOf[nodes.Method]
  }

  override def visit(node: nodes.Expression): nodes.Method = {
    node.vertices(Direction.IN, EdgeTypes.CONTAINS).nextChecked.asInstanceOf[nodes.Method]
  }
}
