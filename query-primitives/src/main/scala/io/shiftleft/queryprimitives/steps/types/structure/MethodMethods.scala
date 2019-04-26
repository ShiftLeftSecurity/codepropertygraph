package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala.Vertex
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.dsl.{PipeOperations, RealPipe}
import io.shiftleft.queryprimitives.steps.ICallResolver
import org.apache.tinkerpop.gremlin.structure.Direction


import scala.collection.JavaConverters._

class MethodMethods[PipeType[_], ElemType <: nodes.Method](val pipe: PipeType[ElemType]) extends AnyVal {

  /**
    * Traverse to concrete instances of method.
    */
  def methodInstance(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.MethodInst] = {
    ops.flatMap2(pipe, (_: ElemType).accept(MethodMethodsMethodInstanceVisitor))
    //ops.flatMapIterator(pipe,
      //_.vertices(Direction.IN, EdgeTypes.REF).asScala)
  }

  /**
    * Traverse to parameters of the method
    * */
  def parameter(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.MethodParameterIn] = {
    ops.flatMap2(pipe, (_: ElemType).accept(MethodMethodsParameterVisitor))
  }

  /**
    * Incoming call sites
    * */
  def callIn(implicit ops: PipeOperations[PipeType],
             callResolver: ICallResolver): RealPipe[nodes.Call] = {
    ops.flatMap2(pipe, (_: ElemType).accept(new MethodMethodsCallInVisitor(callResolver)))
  }

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  /*
  def definingTypeDecl(implicit ops: PipeOperations[PipeType, nodes.Method]): PipeType[nodes.TypeDecl] = {
  ops.map(pipe, _.vertices(Direction.IN, EdgeTypes.AST).asScala)


  new TypeDecl[Labels](
    raw
      .cast[nodes.StoredNode]
      .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
      .until(_.hasLabel(NodeTypes.TYPE_DECL))
      .cast[nodes.TypeDecl])
  }
      */
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
