package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._

class Block(raw: GremlinScala[nodes.Block]) extends NodeSteps[nodes.Block](raw) {

  /**
    * Traverse to locals of this block.
    */
  def local: Local =
    new Local(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.LOCAL).cast[nodes.Local])
}
