package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.help.Doc
import overflowdb.traversal.{Traversal, help}

@help.Traversal(elementType = classOf[nodes.Block])
class BlockTraversal(val traversal: Traversal[nodes.Block]) extends AnyVal {

  /** Traverse to locals of this block. */
  @Doc("Traverse to locals of this block.")
  def local: Traversal[nodes.Local] =
    traversal.out(EdgeTypes.AST).hasLabel(NodeTypes.LOCAL).cast[nodes.Local]
}
