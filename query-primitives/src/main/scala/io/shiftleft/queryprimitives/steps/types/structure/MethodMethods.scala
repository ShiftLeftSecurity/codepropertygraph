package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala.Vertex
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.dsl.InternalImplicits._
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.steps.ICallResolver
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._
import scala.language.higherKinds

class MethodMethods[PipeType[+_]](val pipe: PipeType[nodes.Method]) extends AnyVal {

  /**
    * Traverse to concrete instances of method.
    */
  def methodInstance(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.MethodInst] = {
    pipe.flatMap(_.accept(MethodMethodsMethodInstanceVisitor))
    //ops.flatMapIterator(pipe,
      //_.vertices(Direction.IN, EdgeTypes.REF).asScala)
  }

  /**
    * Traverse to parameters of the method
    * */
  def parameter(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.MethodParameterIn] = {
    pipe.flatMap(_.accept(MethodMethodsParameterVisitor))
  }

  /**
    * Incoming call sites
    * */
  def callIn(implicit ops: PipeOperations[PipeType],
             callResolver: ICallResolver): RealPipe[nodes.Call] = {
    pipe.flatMap(_.accept(new MethodMethodsCallInVisitor(callResolver)))
  }

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  def definingTypeDecl(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.TypeDecl] = {
    pipe.flatRepeatUntil[nodes.StoredNode](
      _.vertices(Direction.IN, EdgeTypes.AST).asScala,
      _.label == NodeTypes.TYPE_DECL)
      .asInstanceOf
  }

  implicit def foo[T](x: Iterator[Vertex]): T = {
    x.asInstanceOf[T]
  }
}

private object MethodMethodsMethodInstanceVisitor extends NodeVisitor[Iterator[nodes.MethodInst]] {
  implicit def foo[T](x: Iterator[Vertex]): T = {
   x.asInstanceOf[T]
  }
  override def visit(node: nodes.Method): Iterator[nodes.MethodInst] = {
    node.vertices(Direction.IN, EdgeTypes.REF).asScala
  }
}

private object MethodMethodsParameterVisitor extends NodeVisitor[Iterator[nodes.MethodParameterIn]] {
  implicit def foo[T](x: Iterator[Vertex]): T = {
    x.asInstanceOf[T]
  }
  override def visit(node: nodes.Method): Iterator[nodes.MethodParameterIn] = {
    node.vertices(Direction.OUT, EdgeTypes.AST).asScala
      .filter(_.label() == NodeTypes.METHOD_PARAMETER_IN)
  }
}

private class MethodMethodsCallInVisitor(callResolver: ICallResolver) extends NodeVisitor[Iterator[nodes.Call]] {
  implicit def foo[T](x: Iterator[Vertex]): T = {
    x.asInstanceOf[T]
  }
  override def visit(node: nodes.Method): Iterator[nodes.Call] = {
    callResolver.resolveDynamicMethodCallSites(node)

    node.vertices(Direction.IN, EdgeTypes.REF).asScala
      .flatMap(_.vertices(Direction.IN, EdgeTypes.CALL).asScala)
  }
}
