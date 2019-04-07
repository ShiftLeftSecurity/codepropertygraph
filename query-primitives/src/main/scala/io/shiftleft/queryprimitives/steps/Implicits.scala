package io.shiftleft.queryprimitives.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import shapeless.{HList, HNil}

// TODO move to `nodes` package object?
object Implicits {

  private def newAnonymousTraversalWithAssociatedGraph[NodeType <: nodes.StoredNode](seq: NodeType*) = {
    val anonymousTraversal = __[NodeType](seq: _*)
    if (seq.nonEmpty) {
      anonymousTraversal.traversal.asAdmin().setGraph(seq.head.graph())
    }
    anonymousTraversal
  }

  implicit class NodeTypeDeco[NodeType <: nodes.StoredNode](node: NodeType) {

    /**
      Start a new traversal from this node
      */
    def start: NodeSteps[NodeType, HNil] =
      new NodeSteps[NodeType, HNil](newAnonymousTraversalWithAssociatedGraph(node))
  }

  implicit class NodeTypeDecoForSeq[NodeType <: nodes.StoredNode](seq: Seq[NodeType]) {

    /**
      Start a new traversal from these nodes
      */
    def start: NodeSteps[NodeType, HNil] =
      new NodeSteps[NodeType, HNil](newAnonymousTraversalWithAssociatedGraph(seq: _*))
  }

  implicit class NewNodeTypeDeco[NodeType <: nodes.NewNode](node: NodeType) {

    /**
    Start a new traversal from this node
      */
    def start: NewNodeSteps[NodeType, HNil] =
      new NewNodeSteps[NodeType, HNil](__[NodeType](node))
  }

  implicit class NewNodeTypeDecoForSeq[NodeType <: nodes.NewNode](seq: Seq[NodeType]) {

    /**
    Start a new traversal from these nodes
      */
    def start: NewNodeSteps[NodeType, HNil] =
      new NewNodeSteps[NodeType, HNil](__[NodeType](seq: _*))
  }

  implicit class GremlinScalaDeco[End <: Vertex, Labels <: HList](raw: GremlinScala.Aux[End, Labels]) {
    /* in some cases we cannot statically determine the type of the node, e.g. when traversing
     * from a known nodeType via AST edges, so we have to cast */
    def cast[NodeType <: nodes.StoredNode]: GremlinScala.Aux[NodeType, Labels] =
      raw.asInstanceOf[GremlinScala.Aux[NodeType, Labels]]
  }

  /**
    * This wrapped is used to make sure to throw a proper NoSuchElementException.
    * Proper in this case means an exception with a stack trace.
    * This is intended to be used as a replacement for next() on the iterators
    * returned from Tinkerpop since those are missing stack traces.
    */
  implicit class JavaIteratorDeco[T](iterator: java.util.Iterator[T]) {
    def nextChecked: T = {
      try {
        iterator.next
      } catch {
        case _: NoSuchElementException =>
          throw new NoSuchElementException()
      }
    }

    def nextOption: Option[T] = {
      if (iterator.hasNext) {
        Some(iterator.next)
      } else {
        None
      }
    }
  }

}
