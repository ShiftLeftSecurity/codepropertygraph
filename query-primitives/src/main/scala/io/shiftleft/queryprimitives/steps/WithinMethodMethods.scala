package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.queryprimitives.steps.visitormixins.ExpressionGeneralization
import io.shiftleft.queryprimitives.steps.Implicits.JavaIteratorDeco
import org.apache.tinkerpop.gremlin.structure.Direction

class WithinMethodMethods(val node: nodes.WithinMethod) extends AnyVal {
  def method: nodes.MethodRef = {
    node.accept(WithinMethodToMethod)
  }
}

private object WithinMethodToMethod extends NodeVisitor[nodes.MethodRef] with ExpressionGeneralization[nodes.MethodRef] {
  override def visit(node: nodes.MethodRef): nodes.MethodRef = {
    node.asInstanceOf[nodes.MethodRef]
  }

  override def visit(node: nodes.MethodParameterInRef): nodes.MethodRef = {
    node.vertices(Direction.IN, EdgeTypes.AST).nextChecked.asInstanceOf[nodes.MethodRef]
  }

  override def visit(node: nodes.MethodParameterOutRef): nodes.MethodRef = {
    node.vertices(Direction.IN, EdgeTypes.AST).nextChecked.asInstanceOf[nodes.MethodRef]
  }

  override def visit(node: nodes.MethodReturnRef): nodes.MethodRef = {
    node.vertices(Direction.IN, EdgeTypes.AST).nextChecked.asInstanceOf[nodes.MethodRef]
  }

  override def visit(node: nodes.Expression): nodes.MethodRef = {
    node.vertices(Direction.IN, EdgeTypes.CONTAINS).nextChecked.asInstanceOf[nodes.MethodRef]
  }
}
