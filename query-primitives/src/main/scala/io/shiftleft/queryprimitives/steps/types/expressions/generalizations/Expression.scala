package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala._
import gremlin.scala.dsl.Converter
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
import io.shiftleft.queryprimitives.steps.types.structure.{Method, MethodParameter}
import java.lang.{Long => JLong}
import shapeless.HList

/**
  An expression (base type)
  */
class Expression[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Expression, Labels](raw)(Expression.marshaller)
    with ExpressionBase[nodes.Expression, Labels] {
  override val converter = ???
}

object Expression {
  implicit val marshaller: Marshallable[nodes.Expression] = new Marshallable[nodes.Expression] {
    override def toCC(element: Element) =
      new nodes.Expression {
        override def underlying: Vertex = element.asInstanceOf[Vertex]
        override def code: String = element.value[String](NodeKeyNames.CODE)
        override def order: Integer = element.value[Integer](NodeKeyNames.ORDER)
        override def columnNumber: Option[Integer] = element.property[Integer](NodeKeyNames.COLUMN_NUMBER).toOption
        override def columnNumberEnd: Option[Integer] =
          element.property[Integer](NodeKeyNames.COLUMN_NUMBER_END).toOption
        override def lineNumber: Option[Integer] = element.property[Integer](NodeKeyNames.LINE_NUMBER).toOption
        override def lineNumberEnd: Option[Integer] = element.property[Integer](NodeKeyNames.LINE_NUMBER_END).toOption

        // not really needed AFAIK
        override def productArity: Int = ???
        override def productElement(n: Int): Any = ???
        override def canEqual(that: Any): Boolean = ???
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
  def expression: Expression[Labels] =
    new Expression[Labels](raw.out(EdgeTypes.AST))

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
        .filterNot(_.hasLabel(NodeTypes.METHOD))
    )
}
