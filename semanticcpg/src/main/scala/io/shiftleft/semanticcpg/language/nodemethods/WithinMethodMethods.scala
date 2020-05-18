package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes

import scala.annotation.tailrec

class WithinMethodMethods(val node: nodes.WithinMethod) extends AnyVal {
  def method: nodes.Method = node match {
    case node: nodes.Method             => node
    case node: nodes.MethodParameterIn  => walkUpAst(node)
    case node: nodes.MethodParameterOut => walkUpAst(node)
    case node: nodes.MethodReturn       => walkUpAst(node)
    case node: nodes.ImplicitCall       => walkUpAst(node)
    case node: nodes.Expression         => walkUpAstRecursively(node)
  }

  private def walkUpAst(node: nodes.WithinMethod): nodes.Method =
    node._astIn.onlyChecked.asInstanceOf[nodes.Method]

  @tailrec
  private def walkUpAstRecursively(node: nodes.WithinMethod): nodes.Method =
    node._astIn.onlyChecked match {
      case method: nodes.Method      => method
      case other: nodes.WithinMethod => walkUpAstRecursively(other)
    }

}
