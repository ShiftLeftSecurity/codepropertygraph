package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import overflowdb.traversal.Traversal

/**
  An identifier, e.g., an instance of a local variable, or a temporary variable
  */
class IdentifierTrav(val traversal: Traversal[nodes.Identifier]) extends AnyVal {

  /**
    * Traverse to all declarations of this identifier
    * */
  def refsTo: Traversal[nodes.Declaration] =
    traversal.out(EdgeTypes.REF).cast[nodes.Declaration]

}
