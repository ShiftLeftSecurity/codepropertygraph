package io.shiftleft.codepropertygraph.generated

import gremlin.scala._
import io.shiftleft.queryprimitives.steps.{NewNodeSteps,NodeSteps}
import shapeless.{HList, HNil}

package object nodes {

  private def newAnonymousTraversalWithAssociatedGraph[NodeType <: StoredNode](seq: NodeType*): GremlinScala.Aux[NodeType, HNil] = {
    val anonymousTraversal = __[NodeType](seq: _*)
    if (seq.nonEmpty) {
      anonymousTraversal.traversal.asAdmin().setGraph(seq.head.graph)
    }
    anonymousTraversal
  }

  implicit class NodeTypeDeco[NodeType <: StoredNode](node: NodeType) {

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

}
