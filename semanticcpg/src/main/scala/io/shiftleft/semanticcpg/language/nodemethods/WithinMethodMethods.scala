package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes

class WithinMethodMethods(val node: nodes.WithinMethod) extends AnyVal {
  def method: nodes.Method = node match {
    case node: nodes.Method                        => node
    case _: nodes.Expression | _: nodes.JumpTarget => walkUpContains(node)
    case _: nodes.MethodParameterIn | _: nodes.MethodParameterOut | _: nodes.MethodReturn | _: nodes.CallRepr =>
      walkUpAst(node)
  }

  private def walkUpAst(node: nodes.WithinMethod): nodes.Method =
    node._astIn.onlyChecked.asInstanceOf[nodes.Method]

  private def walkUpContains(node: nodes.StoredNode): nodes.Method =
    node._containsIn.onlyChecked match {
      case method: nodes.Method => method
      case _: nodes.TypeDecl    =>
        // TODO - there are csharp CPGs that have typedecls here, which is invalid.
        null
    }

}
