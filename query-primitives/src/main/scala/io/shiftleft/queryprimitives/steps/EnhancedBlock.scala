package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.AstNode
import shapeless.HNil

object EnhancedBlock {

  implicit class EnhancedBlock(block: nodes.Block) {
    def ast: AstNode[HNil] = block.start.ast
    def children: AstNode[HNil] = block.start.children
  }
}
