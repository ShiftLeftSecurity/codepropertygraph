package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes._

class WithinMethodMethods(val node: WithinMethod) extends AnyVal {
  def method: Method = node match {
    case node: Method => node
    case _: MethodParameterIn | _: MethodParameterOut | _: MethodReturn =>
      walkUpAst(node)
    case _: CallRepr if !node.isInstanceOf[Call] => walkUpAst(node)
    case _: Expression | _: JumpTarget           => walkUpContains(node)
  }

  private def walkUpAst(node: WithinMethod): Method =
    node._astIn.onlyChecked.asInstanceOf[Method]

  private def walkUpContains(node: StoredNode): Method =
    node._containsIn.onlyChecked match {
      case method: Method => method
      case _: TypeDecl    =>
        // TODO - there are csharp CPGs that have typedecls here, which is invalid.
        null
    }

}
