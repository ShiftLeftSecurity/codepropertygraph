package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{Binding, Method, TypeDecl}
import overflowdb.traversal.Traversal

class BindingTraversal(val traversal: Traversal[Binding]) extends AnyVal {

  /**
    * Traverse to the method bound by this method binding.
    */
  def boundMethod: Traversal[Method] =
    traversal.out(EdgeTypes.REF).cast[Method]

  /**
    * Traverse to the method bound by this method binding.
    */
  def bindingTypeDecl: Traversal[TypeDecl] =
    traversal.in(EdgeTypes.BINDS).cast[TypeDecl]
}
