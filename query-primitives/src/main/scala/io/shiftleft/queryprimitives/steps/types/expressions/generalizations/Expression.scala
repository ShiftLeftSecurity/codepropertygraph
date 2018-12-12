package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeKeyNames, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.expressions.{Call, Identifier, Literal}
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{
  CodeAccessors,
  EvalTypeAccessors,
  LineNumberAccessors,
  OrderAccessors
}
import io.shiftleft.queryprimitives.steps.types.structure.{Method, MethodParameter, Type}
import java.lang.{Long => JLong}
import java.util.{Iterator => JIterator}
import org.apache.tinkerpop.gremlin.structure.{Direction, VertexProperty}
import shapeless.HList

/**
  An expression (base type)
  */
class Expression[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Expression, Labels](raw)(Expression.marshaller)
    with ExpressionBase[nodes.Expression, Labels] {
}

/* TODO MP: generate in DomainClassCreator */
object Expression {
  lazy val expressionLabels =
    Set(
      "BLOCK",
      "CALL",
      "IDENTIFIER",
      "LITERAL",
      "METHOD_REF",
      "RETURN",
      "UNKNOWN")

  def isExpression(label: String): Boolean =
    expressionLabels.contains(label)

  implicit val marshaller: Marshallable[nodes.Expression] = new Marshallable[nodes.Expression] {
    override def toCC(element: Element): nodes.Expression =
      element.label match {
        case "BLOCK" => implicitly[Marshallable[nodes.Block]].toCC(element)
        case "CALL" => implicitly[Marshallable[nodes.Call]].toCC(element)
        case "IDENTIFIER" => implicitly[Marshallable[nodes.Identifier]].toCC(element)
        case "LITERAL" => implicitly[Marshallable[nodes.Literal]].toCC(element)
        case "METHOD_REF" => implicitly[Marshallable[nodes.MethodRef]].toCC(element)
        case "RETURN" => implicitly[Marshallable[nodes.Return]].toCC(element)
        case "UNKNOWN" => implicitly[Marshallable[nodes.Unknown]].toCC(element)
      }

    // not really needed AFAIK
    override def fromCC(cc: nodes.Expression) = ???
  }
}

trait ExpressionBase[NodeType <: nodes.Expression, Labels <: HList]
    extends OrderAccessors[NodeType, Labels]
    with EvalTypeAccessors[NodeType, Labels]
    with CodeAccessors[NodeType, Labels]
    with LineNumberAccessors[NodeType, Labels] { this: CpgSteps[NodeType, Labels] =>

  /**
    Cast to literal if applicable
    */
  def literal: Literal[Labels] =
    new Literal[Labels](raw.hasLabel(NodeTypes.LITERAL))

  /**
    Cast to identifier, if applicable
    */
  def identifier: Identifier[Labels] =
    new Identifier[Labels](raw.hasLabel(NodeTypes.IDENTIFIER))

  /**
    Cast to call if applicable
    */
  def call: Call[Labels] =
    new Call[Labels](raw.hasLabel(NodeTypes.CALL))

  /**
    Traverse to enclosing expression
    */
  def expressionUp: Expression[Labels] =
    new Expression[Labels](raw.in(EdgeTypes.AST))

  /**
    Traverse to sub expressions
    */
  def expressionDown: Expression[Labels] =
    new Expression[Labels](raw.out(EdgeTypes.AST).not(_.hasLabel(NodeTypes.LOCAL)))

  /**
    If the expression is used as receiver for a call, this traverses to the call.
    */
  def receivedCall: Call[Labels] =
    new Call[Labels](raw.in(EdgeTypes.RECEIVER))

  /**
    Traverse to related parameter
    */
  def toParameter: MethodParameter[Labels] =
    new MethodParameter[Labels](raw.out(EdgeTypes.CALL_ARG))

  /**
    Traverse to enclosing method
    */
  def method: Method[Labels] =
    new Method[Labels](raw.in(EdgeTypes.CONTAINS))

  /**
    * Traverse to next expression in CFG.
    */
  def cfgNext: Expression[Labels] =
    new Expression[Labels](
      raw
        .out(EdgeTypes.CFG)
        .filterNot(_.hasLabel(NodeTypes.METHOD_RETURN))
    )

  /**
    * Traverse to previous expression in CFG.
    */
  def cfgPrev: Expression[Labels] =
    new Expression[Labels](
      raw
        .in(EdgeTypes.CFG)
        .filterNot(_.hasLabel(NodeTypes.METHOD)))

  /**
    * Traverse to expression evaluation type
    * */
  def typ: Type[Labels] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE))
}
