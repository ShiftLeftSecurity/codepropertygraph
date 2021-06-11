package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes.{Block, Local}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import overflowdb.traversal.help.Doc
import overflowdb.traversal.{Traversal, help}

@help.Traversal(elementType = classOf[Block])
class BlockTraversal(val traversal: Traversal[Block]) extends AnyVal {

  /** Traverse to locals of this block. */
  @Doc("Traverse to locals of this block.")
  def local: Traversal[Local] =
    traversal.out(EdgeTypes.AST).hasLabel(NodeTypes.LOCAL).cast[Local]
}
