package io.shiftleft.queryprimitives.steps.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.EdgeTypes.{AST, CONTAINS}
import io.shiftleft.Implicits.JavaIteratorDeco
import org.apache.tinkerpop.gremlin.structure.Direction.IN

class WithinMethodMethods(val node: nodes.WithinMethod) extends AnyVal {
  def method: nodes.Method =
    node match {
      case node: nodes.Method => node
      case node: nodes.MethodParameterIn => node.vertices(IN, AST).nextChecked.asInstanceOf[nodes.Method]
      case node: nodes.MethodParameterOut => node.vertices(IN, AST).nextChecked.asInstanceOf[nodes.Method]
      case node: nodes.MethodReturn => node.vertices(IN, AST).nextChecked.asInstanceOf[nodes.Method]
      case node: nodes.ImplicitCall => node.vertices(IN, AST).nextChecked.asInstanceOf[nodes.Method]
      case node: nodes.Expression => node.vertices(IN, CONTAINS).nextChecked.asInstanceOf[nodes.Method]
    }
}
