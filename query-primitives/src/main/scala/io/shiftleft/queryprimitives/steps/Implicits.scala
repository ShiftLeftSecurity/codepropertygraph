package io.shiftleft.queryprimitives.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.starters.{Cpg, NodeTypeStarters}
import io.shiftleft.queryprimitives.steps.types.structure._
import io.shiftleft.queryprimitives.steps.types.expressions._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import shapeless.{HList, HNil}

import scala.language.implicitConversions

object Implicits extends Implicits // to allow for a hierarchy of implicits

trait Implicits {

  private def newAnonymousTraversalWithAssociatedGraph[NodeType <: nodes.StoredNode](
      seq: NodeType*) = {
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

  implicit class GremlinScalaDeco[End <: Vertex, Labels <: HList](
      raw: GremlinScala.Aux[End, Labels]) {
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

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters =
    new NodeTypeStarters(cpg)

  /* TODO: generate this boilerplate */
  implicit def toLiteral[Labels <: HList](steps: Steps[nodes.Literal, Labels]): Literal[Labels] =
    new Literal[Labels](steps.raw)

  implicit def toType[Labels <: HList](steps: Steps[nodes.Type, Labels]): Type[Labels] =
    new Type[Labels](steps.raw)

  implicit def toTypeDecl[Labels <: HList](steps: Steps[nodes.TypeDecl, Labels]): TypeDecl[Labels] =
    new TypeDecl[Labels](steps.raw)

  implicit def toCall[Labels <: HList](steps: Steps[nodes.Call, Labels]): Call[Labels] =
    new Call[Labels](steps.raw)

  implicit def toIdentifier[Labels <: HList](
      steps: Steps[nodes.Identifier, Labels]): Identifier[Labels] =
    new Identifier[Labels](steps.raw)

  implicit def toMember[Labels <: HList](steps: Steps[nodes.Member, Labels]): Member[Labels] =
    new Member[Labels](steps.raw)

  implicit def toLocal[Labels <: HList](steps: Steps[nodes.Local, Labels]): Local[Labels] =
    new Local[Labels](steps.raw)

  implicit def toMethodInst[Labels <: HList](
      steps: Steps[nodes.MethodInst, Labels]): MethodInst[Labels] =
    new MethodInst[Labels](steps.raw)

  implicit def toMethod[Labels <: HList](steps: Steps[nodes.Method, Labels]): Method[Labels] =
    new Method[Labels](steps.raw)

  implicit def toMethodParameter[Labels <: HList](
      steps: Steps[nodes.MethodParameterIn, Labels]): MethodParameter[Labels] =
    new MethodParameter[Labels](steps.raw)

  implicit def toMethodParameterOut[Labels <: HList](
      steps: Steps[nodes.MethodParameterOut, Labels]): MethodParameterOut[Labels] =
    new MethodParameterOut[Labels](steps.raw)

  implicit def toMethodReturn[Labels <: HList](
      steps: Steps[nodes.MethodReturn, Labels]): MethodReturn[Labels] =
    new MethodReturn[Labels](steps.raw)

  implicit def toNamespace[Labels <: HList](
      steps: Steps[nodes.Namespace, Labels]): Namespace[Labels] =
    new Namespace[Labels](steps.raw)

  implicit def toNamespaceBlock[Labels <: HList](
      steps: Steps[nodes.NamespaceBlock, Labels]): NamespaceBlock[Labels] =
    new NamespaceBlock[Labels](steps.raw)

  implicit def toModifier[Labels <: HList](steps: Steps[nodes.Modifier, Labels]): Modifier[Labels] =
    new Modifier[Labels](steps.raw)

  implicit def toExpression[Labels <: HList](
      steps: Steps[nodes.Expression, Labels]): Expression[Labels] =
    new Expression[Labels](steps.raw)

  implicit def toDeclaration[Labels <: HList](
      steps: Steps[nodes.Declaration, Labels]): Declaration[Labels] =
    new Declaration[Labels](steps.raw)

  implicit def toFile[Labels <: HList](steps: Steps[nodes.File, Labels]): File[Labels] =
    new File[Labels](steps.raw)

}
