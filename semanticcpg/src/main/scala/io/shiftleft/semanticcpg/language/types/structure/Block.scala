package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._

class Block[A <: nodes.Block](raw: GremlinScala[A]) extends NodeSteps[A](raw) {

  /**
    * Traverse to locals of this block.
    */
  def local: NodeSteps[nodes.Local] =
    new NodeSteps(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.LOCAL).cast[nodes.Local])
}
