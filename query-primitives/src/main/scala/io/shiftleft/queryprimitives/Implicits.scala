package io.shiftleft.queryprimitives

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.{NewNodeSteps, NodeSteps, Steps}
import io.shiftleft.queryprimitives.steps.starters.NodeTypeStarters
import shapeless.{HList, HNil}

object Implicits {

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters =
    new NodeTypeStarters(cpg)

  implicit class GremlinScalaDeco[End, Labels <: HList](raw: GremlinScala.Aux[End, Labels]) {
    /* in some cases we cannot statically determine the type of the node, e.g. when traversing
     * from a known nodeType via AST edges, so we have to cast */
    def cast[NodeType]: GremlinScala.Aux[NodeType, Labels] =
      raw.asInstanceOf[GremlinScala.Aux[NodeType, Labels]]
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

  implicit class BaseNodeTypeDeco[NodeType <: nodes.Node](node: NodeType) {

    /**
    Start a new traversal from this node
      */
    def start: Steps[NodeType, HNil] =
      new Steps[NodeType, HNil](__[NodeType](node))
  }

  implicit class BaseNodeTypeDecoForSeq[NodeType <: nodes.Node](seq: Seq[NodeType]) {

    /**
    Start a new traversal from these nodes
      */
    def start: Steps[NodeType, HNil] =
      new Steps[NodeType, HNil](__[NodeType](seq: _*))
  }

  private def newAnonymousTraversalWithAssociatedGraph[NodeType <: nodes.StoredNode](
      seq: NodeType*): GremlinScala.Aux[NodeType, HNil] = {
    val anonymousTraversal = __[NodeType](seq: _*)
    if (seq.nonEmpty) {
      anonymousTraversal.traversal.asAdmin().setGraph(seq.head.graph)
    }
    anonymousTraversal
  }

}
