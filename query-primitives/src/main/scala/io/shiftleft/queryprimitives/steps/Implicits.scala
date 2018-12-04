package io.shiftleft.queryprimitives.steps

import gremlin.scala._
import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.types.structure._
import io.shiftleft.queryprimitives.steps.types.expressions._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import shapeless.{HList, HNil}

import scala.language.implicitConversions

object Implicits {

  implicit class NodeTypeDeco[NodeType <: nodes.StoredNode: Marshallable](node: NodeType) {

    /**
      Start a new traversal from this node
      */
    def start: CpgSteps[NodeType, HNil] =
      new CpgSteps[NodeType, HNil](node.underlying.start)
  }

  implicit class NodeTypeDecoForSeq[NodeType <: nodes.StoredNode: Marshallable](seq: Seq[NodeType]) {
    /**
      Start a new traversal from these nodes
      */
    def start: CpgSteps[NodeType, HNil] = {
      val raw: GremlinScala.Aux[Vertex, HNil] = __(seq.map(_.underlying): _*)
      new CpgSteps[NodeType, HNil](raw)
    }
  }

  implicit class NewNodeTypeDeco[NodeType <: nodes.NewNode : Marshallable](node: NodeType) {
    /**
    Start a new traversal from this node
      */
    def start: NewNodeSteps[NodeType, HNil] =
      new NewNodeSteps[NodeType, HNil](__[NodeType](node))
  }

  implicit class NewNodeTypeDecoForSeq[NodeType <: nodes.NewNode : Marshallable](seq: Seq[NodeType]) {
    /**
    Start a new traversal from these nodes
      */
    def start: NewNodeSteps[NodeType, HNil] =
      new NewNodeSteps[NodeType, HNil](__[NodeType](seq: _*))
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

  /* TODO: generate this boilerplate */
  implicit def toLiteral[Labels <: HList](steps: Steps[nodes.Literal, Vertex, Labels]): Literal[Labels] =
    new Literal[Labels](steps.raw)

  implicit def toType[Labels <: HList](steps: Steps[nodes.Type, Vertex, Labels]): Type[Labels] =
    new Type[Labels](steps.raw)

  implicit def toTypeDecl[Labels <: HList](steps: Steps[nodes.TypeDecl, Vertex, Labels]): TypeDecl[Labels] =
    new TypeDecl[Labels](steps.raw)

  implicit def toCall[Labels <: HList](steps: Steps[nodes.Call, Vertex, Labels]): Call[Labels] =
    new Call[Labels](steps.raw)

  implicit def toIdentifier[Labels <: HList](steps: Steps[nodes.Identifier, Vertex, Labels]): Identifier[Labels] =
    new Identifier[Labels](steps.raw)

  implicit def toMember[Labels <: HList](steps: Steps[nodes.Member, Vertex, Labels]): Member[Labels] =
    new Member[Labels](steps.raw)

  implicit def toLocal[Labels <: HList](steps: Steps[nodes.Local, Vertex, Labels]): Local[Labels] =
    new Local[Labels](steps.raw)

  implicit def toMethodInst[Labels <: HList](steps: Steps[nodes.MethodInst, Vertex, Labels]): MethodInst[Labels] =
    new MethodInst[Labels](steps.raw)

  implicit def toMethod[Labels <: HList](steps: Steps[nodes.Method, Vertex, Labels]): Method[Labels] =
    new Method[Labels](steps.raw)

  implicit def toMethodParameter[Labels <: HList](
      steps: Steps[nodes.MethodParameterIn, Vertex, Labels]): MethodParameter[Labels] =
    new MethodParameter[Labels](steps.raw)

  implicit def toMethodParameterOut[Labels <: HList](
      steps: Steps[nodes.MethodParameterOut, Vertex, Labels]): MethodParameterOut[Labels] =
    new MethodParameterOut[Labels](steps.raw)

  implicit def toMethodReturn[Labels <: HList](steps: Steps[nodes.MethodReturn, Vertex, Labels]): MethodReturn[Labels] =
    new MethodReturn[Labels](steps.raw)

  implicit def toNamespace[Labels <: HList](steps: Steps[nodes.Namespace, Vertex, Labels]): Namespace[Labels] =
    new Namespace[Labels](steps.raw)

  implicit def toModifier[Labels <: HList](steps: Steps[nodes.Modifier, Vertex, Labels]): Modifier[Labels] =
    new Modifier[Labels](steps.raw)

  implicit def toExpression[Labels <: HList](steps: Steps[nodes.Expression, Vertex, Labels]): Expression[Labels] =
    new Expression[Labels](steps.raw)

  implicit def toDeclaration[Labels <: HList](steps: Steps[nodes.Declaration, Vertex, Labels]): Declaration[Labels] =
    new Declaration[Labels](steps.raw)

  implicit def toFile[Labels <: HList](steps: Steps[nodes.File, Vertex, Labels]): File[Labels] =
    new File[Labels](steps.raw)
}
