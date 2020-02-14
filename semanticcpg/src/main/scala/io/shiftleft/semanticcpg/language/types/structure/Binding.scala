package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class Binding(val wrapped: NodeSteps[nodes.Binding]) extends AnyVal {

  /**
    * Traverse to the method bound by this method binding.
    */
  def boundMethod: Method =
    new Method(wrapped.raw.out(EdgeTypes.REF).cast[nodes.Method])

  /**
    * Traverse to the method bound by this method binding.
    */
  def bindingTypeDecl: TypeDecl =
    new TypeDecl(wrapped.raw.in(EdgeTypes.BINDS).cast[nodes.TypeDecl])
}
