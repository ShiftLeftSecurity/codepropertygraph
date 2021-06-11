package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import overflowdb.traversal.Traversal

class BindingTraversal(val traversal: Traversal[nodes.Binding]) extends AnyVal {

  /**
    * Traverse to the method bound by this method binding.
    */
  def boundMethod: Traversal[nodes.Method] =
    traversal.out(EdgeTypes.REF).cast[nodes.Method]

  /**
    * Traverse to the method bound by this method binding.
    */
  def bindingTypeDecl: Traversal[nodes.TypeDecl] =
    traversal.in(EdgeTypes.BINDS).cast[nodes.TypeDecl]
}
