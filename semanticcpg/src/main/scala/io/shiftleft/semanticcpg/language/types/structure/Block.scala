package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._

class Block(val wrapped: NodeSteps[nodes.Block]) extends AnyVal {

  /**
    * Traverse to locals of this block.
    */
  def local: NodeSteps[nodes.Local] =
    new NodeSteps(wrapped.raw.out(EdgeTypes.AST).hasLabel(NodeTypes.LOCAL).cast[nodes.Local])
}
