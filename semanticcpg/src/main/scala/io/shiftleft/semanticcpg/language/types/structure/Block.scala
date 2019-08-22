package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.NodeSteps
import shapeless.HList
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.ExpressionBase

class Block[Labels <: HList](raw: GremlinScala.Aux[nodes.Block, Labels])
    extends NodeSteps[nodes.Block, Labels](raw)
    with ExpressionBase[nodes.Block, Labels] {

  /**
    * Traverse to locals of this block.
    */
  def local: Local[Labels] =
    new Local[Labels](raw.out(EdgeTypes.AST).hasLabel(NodeTypes.LOCAL).cast[nodes.Local])
}
