package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

/**
  A call site
  */
class Call(val traversal: Traversal[nodes.Call]) extends AnyVal {

  /**
    Only statically dispatched calls
    */
  def isStatic: Traversal[nodes.Call] =
    traversal.dispatchType("STATIC_DISPATCH")

  /**
    Only dynamically dispatched calls
    */
  def isDynamic: Traversal[nodes.Call] =
    traversal.dispatchType("DYNAMIC_DISPATCH")

  /**
    The caller
    */
  def method: Traversal[nodes.Method] =
    traversal.in(EdgeTypes.CONTAINS).hasLabel(NodeTypes.METHOD).cast[nodes.Method]

  /**
    The receiver of a call if the call has a receiver associated.
    */
  def receiver: Traversal[nodes.Expression] =
    traversal.out(EdgeTypes.RECEIVER).cast[nodes.Expression]

  /**
    Arguments of the call
    */
  def argument: Traversal[nodes.Expression] =
    traversal.flatMap(_.argument)

  /**
    `i'th` arguments of the call
    */
  def argument(i: Integer): Traversal[nodes.Expression] =
    traversal.flatMap(_.arguments(i))

  /**
    To formal method return parameter
    */
  def toMethodReturn(implicit callResolver: ICallResolver): Traversal[nodes.MethodReturn] =
    traversal
      .flatMap(callResolver.getCalledMethodsAsTraversal)
      .out(EdgeTypes.AST)
      .hasLabel(NodeTypes.METHOD_RETURN)
      .cast[nodes.MethodReturn]

  /**
    * Traverse to referenced members
    * */
  def referencedMember: Traversal[nodes.Member] =
    traversal.out(EdgeTypes.REF).cast[nodes.Member]

}
