package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.{AstNode, Expression, ExpressionBase}
import io.shiftleft.semanticcpg.language.types.propertyaccessors.ParserTypeNameAccessors
import io.shiftleft.semanticcpg.language._

object ControlStructure {

  val secondChildIndex = new Integer(2)
  val thirdChildIndex = new Integer(3)

}

class ControlStructure(raw: GremlinScala[nodes.ControlStructure])
    extends NodeSteps[nodes.ControlStructure](raw)
    with ParserTypeNameAccessors[nodes.ControlStructure]
    with ExpressionBase[nodes.ControlStructure] {

  import ControlStructure._

  /**
    * The expression introduced by this control structure, if any
    * */
  def condition: Expression =
    new Expression(raw.out(EdgeTypes.CONDITION).cast[nodes.Expression])

  /**
    * Only those control structures where condition matched `regex`
    * */
  def condition(regex: String): ControlStructure =
    new ControlStructure(this.filterOnEnd(_.code.matches(regex)).raw)

  def whenTrue: AstNode = new AstNode(
    raw.out.has(NodeKeys.ORDER, secondChildIndex).cast[nodes.AstNode]
  )

  def whenFalse: AstNode = new AstNode(
    raw.out.has(NodeKeys.ORDER, thirdChildIndex).cast[nodes.AstNode]
  )

}
