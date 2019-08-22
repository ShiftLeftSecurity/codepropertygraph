package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.{AstNode, Expression, ExpressionBase}
import io.shiftleft.semanticcpg.language.types.propertyaccessors.ParserTypeNameAccessors
import io.shiftleft.semanticcpg.language._
import shapeless.HList

object ControlStructure {

  val secondChildIndex = new Integer(2)
  val thirdChildIndex = new Integer(3)

}

class ControlStructure[Labels <: HList](raw: GremlinScala.Aux[nodes.ControlStructure, Labels])
    extends NodeSteps[nodes.ControlStructure, Labels](raw)
    with ParserTypeNameAccessors[nodes.ControlStructure, Labels]
    with ExpressionBase[nodes.ControlStructure, Labels] {

  import ControlStructure._

  /**
    * The expression introduced by this control structure, if any
    * */
  def condition: Expression[Labels] =
    new Expression(raw.out(EdgeTypes.CONDITION).cast[nodes.Expression])

  /**
    * Only those control structures where condition matched `regex`
    * */
  def condition(regex: String): ControlStructure[Labels] =
    new ControlStructure(this.filterOnEnd(_.code.matches(regex)).raw)

  def whenTrue: AstNode[Labels] = new AstNode(
    raw.out.has(NodeKeys.ORDER, secondChildIndex).cast[nodes.AstNode]
  )

  def whenFalse: AstNode[Labels] = new AstNode(
    raw.out.has(NodeKeys.ORDER, thirdChildIndex).cast[nodes.AstNode]
  )

}
