package io.shiftleft.queryprimitives.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, Node, StoredNode}
import io.shiftleft.codepropertygraph.generated.{EdgeKeys, EdgeTypes}
import io.shiftleft.diffgraph.DiffGraph
import scala.collection.JavaConverters._
import shapeless.HList

class NewNodeSteps[A <: NewNode: Marshallable, Labels <: HList](val raw: GremlinScala[A]) {

  def store()(implicit graph: DiffGraph): Unit =
    raw.toList.foreach(storeRecursively)

  private def storeRecursively(newNode: NewNode)(implicit graph: DiffGraph): Unit = {
    graph.addNode(newNode)

    // add all `contained` nodes that are NewNodes to the DiffGraph
    newNode.allContainedNodes.collect {
      case containedNode: NewNode => storeRecursively(containedNode)
    }

    // create edges to `contained` nodes for this new node
    for {
      (localName, containedNodes) <- newNode.containedNodesByLocalName
      (containedNode, index)      <- containedNodes.zipWithIndex
    } {
      val properties = Seq(
        EdgeKeys.LOCAL_NAME -> localName,
        EdgeKeys.INDEX      -> index
      ).map { case KeyValue(key, value) => (key.name, value) }
      addEdge(graph, newNode, containedNode, EdgeTypes.CONTAINS, properties)
    }
  }

  private def addEdge(graph: DiffGraph, src: Node, dst: Node, label: String, properties: Seq[(String, AnyRef)]): Unit =
    (src, dst) match {
      case (src: NewNode, dst: NewNode) => graph.addEdge(src, dst, label, properties)
      case (src: NewNode, dst: StoredNode) =>
        graph.addEdgeToOriginal(src, dst.underlying, label, properties)
      case (src: StoredNode, dst: NewNode) =>
        graph.addEdgeFromOriginal(src.underlying, dst, label, properties)
      case (src: StoredNode, dst: StoredNode) =>
        graph.addEdgeInOriginal(src.underlying, dst.underlying, label, properties)
      case (_, _) => throw new NotImplementedError("this should never happen")
    }

  /**
    * Evaluate traversal and return list of results
    * TODO fabs/michael: remove - only `store` should be necessary
    * */
  def l: List[A] = raw.l

  /**
    * Evaluate traversal and return Java list of results
    * TODO fabs/michael: remove - only `store` should be necessary
    * */
  def jl: java.util.List[A] = l.asJava

  /**
    * Evaluate traversal and return first element
    * TODO fabs/michael: remove - only `store` should be necessary
    * */
  def head: A = l.head

  /**
    * Evaluate traversal and return first element as option
    * */
  def headOption: Option[A] = l.headOption

  /**
    * Evaluate traversal and return set of results
    * TODO fabs/michael: remove - only `store` should be necessary
    * */
  def toSet: Set[A] = raw.toSet

  /**
    * Evaluate traversals and return Java set of results
    * TODO fabs/michael: remove - only `store` should be necessary
    * */
  def toJSet: java.util.Set[A] = toSet.asJava

  /**
    Same as filter, but operates with a lambda (will only work with local databases)
    */
  def filterOnEnd(predicate: A => Boolean): NewNodeSteps[A, Labels] =
    new NewNodeSteps[A, Labels](raw.filterOnEnd(predicate))
}
