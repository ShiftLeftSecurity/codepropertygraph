package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeyNames, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import java.lang.{Long => JLong}
import java.util.{Iterator => JIterator}
import org.apache.tinkerpop.gremlin.structure.{Direction, VertexProperty}
import shapeless.HList

class Declaration[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Declaration, Labels](raw)(Declaration.marshaller)
    with DeclarationBase[nodes.Declaration, Labels]

object Declaration {
  val marshaller: Marshallable[nodes.Declaration] = new Marshallable[nodes.Declaration] {
    override def toCC(element: Element) =
      new nodes.Declaration {
        override def underlying: Vertex = element.asInstanceOf[Vertex]
        override def name: String = element.value[String](NodeKeyNames.NAME)

        // needed for specialised tinkergraph (separate codegen) - doesn't harm standard CC impl
        def _name_=(value: String): Unit = ???
        def _name: String = name
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
        def property[V](cardinality: VertexProperty.Cardinality, key: String, value: V, keyValues: Object*): VertexProperty[V] =
          underlying.property(cardinality, key, value, keyValues: _*)
        def vertices(direction: Direction, edgeLabels: String*): JIterator[Vertex] = 
          underlying.vertices(direction, edgeLabels: _*)
        def toMap: Map[String, Any] =
          Map(
            "_label" -> element.label,
            "_id" -> element.id,
            "NAME" -> _name
          )

        // not really needed AFAIK
        override def productArity: Int = ???
        override def productElement(n: Int): Any = ???
        override def canEqual(that: Any): Boolean = ???
      }

    // not really needed AFAIK
    override def fromCC(cc: nodes.Declaration) = ???
  }
}

trait DeclarationBase[NodeType <: nodes.Declaration, Labels <: HList] {
  this: CpgSteps[NodeType, Labels] =>
  // TODO: steps for Declarations go here
}
