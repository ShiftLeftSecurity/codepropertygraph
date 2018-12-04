package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.CpgSteps
import shapeless.HList
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.ExpressionBase

class Block[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Block, Labels](raw) with ExpressionBase[nodes.Block, Labels] {

  /**
    * Traverse to locals of this block.
    */
  def local: Local[Labels] =
    new Local[Labels](raw.out(EdgeTypes.AST).hasLabel(NodeTypes.LOCAL))
}
