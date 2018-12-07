package io.shiftleft.queryprimitives.steps.starters

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.structure._
import io.shiftleft.queryprimitives.steps.types.expressions._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import java.lang.{Long => JLong}
import java.util.{Iterator => JIterator}
import org.apache.tinkerpop.gremlin.structure.{Direction, VertexProperty}
import shapeless.HNil

trait NodeTypeStarters {

  /**
    * The underlying graph
    * */
  def scalaGraph: ScalaGraph

  /**
    Traverse to all nodes.
    */
  def all: CpgSteps[nodes.StoredNode, HNil] =
    new CpgSteps[nodes.StoredNode, HNil](scalaGraph.V)(NodeTypeStarters.storedNodeMarshaller)

  /**
    Traverse to all source files
    */
  def file: File[HNil] =
    new File[HNil](scalaGraph.V.hasLabel(NodeTypes.FILE))

  /**
    Traverse to all namespaces, e.g., packages in Java.
    */
  def namespace: Namespace[HNil] =
    new Namespace[HNil](scalaGraph.V.hasLabel(NodeTypes.NAMESPACE))

  /**
  Traverse to all namespace blocks, e.g., packages in Java.
    */
  def namespaceBlock: NamespaceBlock[HNil] =
    new NamespaceBlock[HNil](scalaGraph.V.hasLabel(NodeTypes.NAMESPACE_BLOCK))

  /**
    Traverse to all types, e.g., Set<String>
    */
  def types: Type[HNil] =
    new Type(scalaGraph.V.hasLabel(NodeTypes.TYPE))

  /**
    Traverse to all declarations, e.g., Set<T>
    */
  def typeDecl: TypeDecl[HNil] =
    new TypeDecl(scalaGraph.V.hasLabel(NodeTypes.TYPE_DECL))

  /**
    Traverse to all methods
    */
  def method: Method[HNil] =
    new Method(scalaGraph.V.hasLabel(NodeTypes.METHOD))

  /**
    Traverse to all method instances
    */
  def methodInstance: MethodInst[HNil] =
    new MethodInst(scalaGraph.V.hasLabel(NodeTypes.METHOD_INST))

  /**
    Traverse to all formal return parameters
    */
  def methodReturn: MethodReturn[HNil] =
    new MethodReturn(scalaGraph.V.hasLabel(NodeTypes.METHOD_RETURN))

  /**
    Traverse to all input parameters
    */
  def parameter: MethodParameter[HNil] =
    new MethodParameter(scalaGraph.V.hasLabel(NodeTypes.METHOD_PARAMETER_IN))

  /**
    Traverse to all class members
    */
  def member: Member[HNil] =
    new Member(scalaGraph.V.hasLabel(NodeTypes.MEMBER))

  /**
    Traverse to all call sites
    */
  def call: Call[HNil] =
    new Call(scalaGraph.V.hasLabel(NodeTypes.CALL))

  /**
    Traverse to all local variable declarations

    */
  def local: Local[HNil] =
    new Local(scalaGraph.V.hasLabel(NodeTypes.LOCAL))

  /**
    Traverse to all literals (constant strings and numbers provided directly in the code).
    */
  def literal: Literal[HNil] =
    new Literal(scalaGraph.V.hasLabel(NodeTypes.LITERAL))

  /**
    Traverse to all identifiers, e.g., occurrences of local variables or class members in method bodies.
    */
  def identifier: Identifier[HNil] =
    new Identifier(scalaGraph.V.hasLabel(NodeTypes.IDENTIFIER))

  /**
    Traverse to all arguments passed to methods
    */
  def argument: Expression[HNil] =
    call.argument

  /**
    * Traverse to all meta data entries
    */
  def metaData: MetaData[HNil] =
    new MetaData(scalaGraph.V.hasLabel(NodeTypes.META_DATA))
}

object NodeTypeStarters {
  val storedNodeMarshaller: Marshallable[nodes.StoredNode] = new Marshallable[nodes.StoredNode] {
    override def toCC(element: Element) =
      new nodes.StoredNode {
        override def underlying: Vertex = element.asInstanceOf[Vertex]

        // needed for specialised tinkergraph (separate codegen) - doesn't harm standard CC impl
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
          Map("_label" -> element.label, "_id" -> element.id)

        // not really needed AFAIK
        override def productArity: Int = ???
        override def productElement(n: Int): Any = ???
        override def canEqual(that: Any): Boolean = ???
      }

    // not really needed AFAIK
    override def fromCC(cc: nodes.StoredNode) = ???
  }
}
