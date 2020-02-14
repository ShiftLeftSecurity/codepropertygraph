package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression
import io.shiftleft.semanticcpg.language.types.propertyaccessors._
import io.shiftleft.semanticcpg.language.types.structure.{Member, Method, MethodReturn}

/**
  A call site
  */
class Call(raw: GremlinScala[nodes.Call]) extends NodeSteps[nodes.Call](raw) with EvalTypeAccessors[nodes.Call] {

  /**
    Only statically dispatched calls
    */
  def isStatic: NodeSteps[nodes.Call] =
    this.dispatchType("STATIC_DISPATCH")

  /**
    Only dynamically dispatched calls
    */
  def isDynamic: NodeSteps[nodes.Call] =
    this.dispatchType("DYNAMIC_DISPATCH")

  /**
    The caller
    */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(raw.in(EdgeTypes.CONTAINS).hasLabel(NodeTypes.METHOD).cast[nodes.Method])

  /**
    The receiver of a call if the call has a receiver associated.
    */
  def receiver: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.out(EdgeTypes.RECEIVER).cast[nodes.Expression])

  /**
    Arguments of the call
    */
  def argument: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.out(EdgeTypes.ARGUMENT).cast[nodes.Expression])

  /**
    `i'th` arguments of the call
    */
  def argument(i: Integer): NodeSteps[nodes.Expression] =
    argument.argIndex(i)

  /**
    To formal method return parameter
    */
  def toMethodReturn: NodeSteps[nodes.MethodReturn] =
    new NodeSteps(
      raw
        .out(EdgeTypes.CALL)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_RETURN)
        .cast[nodes.MethodReturn])

  /**
    * Traverse to referenced members
    * */
  def referencedMember: NodeSteps[nodes.Member] =
    new NodeSteps(raw.out(EdgeTypes.REF).cast[nodes.Member])

}
