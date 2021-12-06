package io.shiftleft.semanticcpg.language.bindingextension

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{Binding, TypeDecl}
import overflowdb.traversal.Traversal

class BindingTraversal(val traversal: Traversal[Binding]) extends AnyVal {

  /**
    * Traverse to the method bound by this method binding.
    */
  def bindingTypeDecl: Traversal[TypeDecl] =
    traversal.in(EdgeTypes.BINDS).cast[TypeDecl]
}
