package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.NodeSteps
import shapeless.HList
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.{AstNode, ExpressionBase}

class Block[Labels <: HList](raw: GremlinScala.Aux[nodes.Block, Labels])
    extends NodeSteps[nodes.Block, Labels](raw)
    with ExpressionBase[nodes.Block, Labels] {

  /**
    * All abstract syntax tree nodes in this block, including the root
    * */
  def ast : AstNode[Labels] = new AstNode[Labels](raw.emit.repeat(_.out(EdgeTypes.AST)).cast[nodes.AstNode])

  /**
    * All abstract syntax tree nodes in this block, excluding the root
    * */
  def children : AstNode[Labels] = new AstNode[Labels](raw.repeat(_.out(EdgeTypes.AST)).emit.cast[nodes.AstNode])

  /**
    * Traverse to locals of this block.
    */
  def local: Local[Labels] =
    new Local[Labels](raw.out(EdgeTypes.AST).hasLabel(NodeTypes.LOCAL).cast[nodes.Local])
}

