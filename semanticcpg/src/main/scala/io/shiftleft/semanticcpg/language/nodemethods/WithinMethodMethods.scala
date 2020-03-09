package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.Implicits.JavaIteratorDeco
import org.apache.tinkerpop.gremlin.structure.Direction

class WithinMethodMethods(val node: nodes.WithinMethod) extends AnyVal {
  def method: nodes.Method = node match {
    case node: nodes.Method             => node
    case node: nodes.MethodParameterIn  => walkUpAst(node)
    case node: nodes.MethodParameterOut => walkUpAst(node)
    case node: nodes.MethodReturn       => walkUpAst(node)
    case node: nodes.ImplicitCall       => walkUpAst(node)
    case node: nodes.Expression         => walkUpContainsEdges(node)
  }

  private def walkUpAst(node: nodes.WithinMethod): nodes.Method =
    node._astIn.onlyChecked.asInstanceOf[nodes.Method]

  private def walkUpContainsEdges(node: nodes.WithinMethod): nodes.Method =
    node._containsIn.onlyChecked.asInstanceOf[nodes.Method]

}
