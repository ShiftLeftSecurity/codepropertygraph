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
  override val converter = Converter.forDomainNode[nodes.Expression](Expression.marshaller, graph)
}

object Expression {
  implicit val marshaller: Marshallable[nodes.Expression] = new Marshallable[nodes.Expression] {
    override def toCC(element: Element) =
      new nodes.Expression {
        override def underlying: Vertex = element.asInstanceOf[Vertex]
        override def order: Integer = element.value[Integer](NodeKeyNames.ORDER)

        // needed for specialised tinkergraph (separate codegen) - doesn't harm standard CC impl
        def _code_=(value: String): Unit = ???
        def _order_=(value: Integer): Unit = ???
        def _order: Integer = order
        def graph() = underlying.graph
        def id(): Object = underlying.id
        def label(): String = underlying.label
        def remove(): Unit = underlying.remove
        def addEdge(label: String, inVertex: Vertex, keyValues: Object*) =
          underlying.addEdge(label, inVertex, keyValues: _*)
        def edges(direction: Direction, edgeLabels: String*) =
          underlying.edges(direction, edgeLabels: _*)
        def properties[V](propertyKeys: String*): JIterator[VertexProperty[V]] =
          underlying.properties(propertyKeys: _*)
        def property[V](cardinality: VertexProperty.Cardinality,
                        key: String,
                        value: V,
                        keyValues: Object*): VertexProperty[V] =
          underlying.property(cardinality, key, value, keyValues: _*)
        def vertices(direction: Direction, edgeLabels: String*): JIterator[Vertex] =
          underlying.vertices(direction, edgeLabels: _*)
        def toMap: Map[String, Any] =
          Map(
            "_label" -> element.label,
            "_id" -> element.id,
            "ORDER" -> order,
          ).filterNot {
              case (k, v) =>
                v == null || v == None
            }
            .map {
              case (k, Some(v)) => (k, v)
              case other        => other
            }

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

  /**
    * Traverse to expression evaluation type
    * */
  def typ: Type[Labels] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE))
}
