package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.types.structure.Block
import shapeless.HList
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.types.expressions.{Call, Identifier, Literal, Return}

class AstNode [Labels <: HList](raw: GremlinScala.Aux[nodes.AstNode, Labels])
  extends NodeSteps[nodes.AstNode, Labels](raw) {

  /**
    * Traverse only to those AST nodes that are also control flow graph nodes
    * */
  def cfgNode : CfgNode[Labels] = new CfgNode[Labels](
    raw.filterOnEnd(_.isInstanceOf[nodes.CfgNode]).map(_.asInstanceOf[nodes.CfgNode]))


  /**
    * Traverse only to those AST nodes that are blocks
    * */

  def block : Block[Labels] = new Block[Labels](
    raw.hasLabel(NodeTypes.BLOCK).cast[nodes.Block]
  )

  def expression : Expression[Labels] = new Expression[Labels](
    raw.filterOnEnd(_.isInstanceOf[nodes.Expression]).cast[nodes.Expression]
  )

  def call : Call[Labels] = new Call[Labels](
    raw.hasLabel(NodeTypes.CALL).cast[nodes.Call]
  )

  def literal : Literal[Labels] = new Literal[Labels](
    raw.hasLabel(NodeTypes.LITERAL).cast[nodes.Literal]
  )

  def identifier : Identifier[Labels] = new Identifier[Labels](
    raw.hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier]
  )

  def returnNode : Return[Labels] = new Return[Labels](
    raw.hasLabel(NodeTypes.RETURN).cast[nodes.Return]
  )

}
