package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.{ICallResolver, NodeSteps}
import io.shiftleft.queryprimitives.steps.types.structure.Block
import shapeless.HList
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.types.expressions.{Call, Identifier, Literal, Return}

class AstNode[Labels <: HList](raw: GremlinScala.Aux[nodes.AstNode, Labels])
    extends NodeSteps[nodes.AstNode, Labels](raw)
    with AstNodeBase[nodes.AstNode, Labels] {}

trait AstNodeBase[NodeType <: nodes.AstNode, Labels <: HList] { this: NodeSteps[NodeType, Labels] =>

  /**
    * Nodes of the AST rooted in this node, including the node itself.
    * */

  def ast: AstNode[Labels] = new AstNode[Labels](raw.emit.repeat(_.out(EdgeTypes.AST)).cast[nodes.AstNode])

  /**
    * Nodes of the AST rooted in this node, minus the node itself
    * */
  def children: AstNode[Labels] = new AstNode[Labels](raw.repeat(_.out(EdgeTypes.AST)).emit.cast[nodes.AstNode])

  /**
    * Traverse only to those AST nodes that are also control flow graph nodes
    * */
  def cfgNode: CfgNode[Labels] =
    new CfgNode[Labels](raw.filterOnEnd(_.isInstanceOf[nodes.CfgNode]).map(_.asInstanceOf[nodes.CfgNode]))

  /**
    * Traverse only to those AST nodes that are blocks
    * */
  def block: Block[Labels] = new Block[Labels](
    raw.hasLabel(NodeTypes.BLOCK).cast[nodes.Block]
  )

  /**
    * Traverse only to AST nodes that are expressions
    * */
  def expression: Expression[Labels] = new Expression[Labels](
    raw.filterOnEnd(_.isInstanceOf[nodes.Expression]).cast[nodes.Expression]
  )

  /**
    * Traverse only to AST nodes that are calls
    * */
  def call: Call[Labels] = new Call[Labels](
    raw.hasLabel(NodeTypes.CALL).cast[nodes.Call]
  )

  /**
  Cast to call if applicable and filter for callee fullName `calleeRegex`
    */
  def call(calleeRegex: String)(implicit callResolver: ICallResolver): Call[Labels] =
    call.filter(_.calledMethod.fullName(calleeRegex))

  /**
    * Traverse only to AST nodes that are literals
    * */
  def literal: Literal[Labels] = new Literal[Labels](
    raw.hasLabel(NodeTypes.LITERAL).cast[nodes.Literal]
  )

  /**
    * Traverse only to AST nodes that are identifier
    * */
  def identifier: Identifier[Labels] = new Identifier[Labels](
    raw.hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier]
  )

  /**
    * Traverse only to AST nodes that are return nodes
    * */
  def returnNode: Return[Labels] = new Return[Labels](
    raw.hasLabel(NodeTypes.RETURN).cast[nodes.Return]
  )
}
