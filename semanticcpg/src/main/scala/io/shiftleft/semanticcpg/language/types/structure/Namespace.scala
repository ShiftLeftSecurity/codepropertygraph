package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

/**
  * A namespace, e.g., Java package or C# namespace
  * */
class Namespace(val wrapped: NodeSteps[nodes.Namespace]) extends AnyVal {
  private def raw: GremlinScala[nodes.Namespace] = wrapped.raw

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(
      raw
        .in(EdgeTypes.REF)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDecl])

  /**
    * Methods defined in this namespace
    * */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(
      raw
        .in(EdgeTypes.REF)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD)
        .cast[nodes.Method])

  /**
    * External namespaces - any namespaces
    * which contain one or more external type.
    * */
  def external: NodeSteps[nodes.Namespace] =
    wrapped.filter(_.typeDecl.external)

  /**
    * Internal namespaces - any namespaces
    * which contain one or more internal type
    * */
  def internal: NodeSteps[nodes.Namespace] =
    wrapped.filter(_.typeDecl.internal)

}
