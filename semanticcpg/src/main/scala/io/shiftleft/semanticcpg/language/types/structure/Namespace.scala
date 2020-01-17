package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

/**
  * A namespace, e.g., Java package or C# namespace
  * */
class Namespace(raw: GremlinScala[nodes.Namespace]) extends NodeSteps[nodes.Namespace](raw) {

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: TypeDecl =
    new TypeDecl(
      raw
        .in(EdgeTypes.REF)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDecl])

  /**
    * Methods defined in this namespace
    * */
  def method: Method =
    new Method(
      raw
        .in(EdgeTypes.REF)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD)
        .cast[nodes.Method])

  /**
    * External namespaces - any namespaces
    * which contain one or more external type.
    * */
  def external: Namespace =
    new Namespace(filter(_.typeDecl.external).raw)

  /**
    * Internal namespaces - any namespaces
    * which contain one or more internal type
    * */
  def internal: Namespace =
    new Namespace(filter(_.typeDecl.internal).raw)

}
