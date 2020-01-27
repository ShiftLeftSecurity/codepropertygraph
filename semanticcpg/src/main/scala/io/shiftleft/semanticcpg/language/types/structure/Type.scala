package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression

class Type[A <: nodes.Type](raw: GremlinScala[A]) extends NodeSteps[A](raw) {

  /**
    * Namespaces in which the corresponding type declaration is defined.
    * */
  def namespace: NodeSteps[nodes.Namespace] =
    referencedTypeDecl.namespace

  /**
    * Methods defined on the corresponding type declaration.
    * */
  def method: NodeSteps[nodes.Method] =
    referencedTypeDecl.method

  /**
    * Filter for types whos corresponding type declaration is in the analyzed jar.
    * */
  def internal: NodeSteps[A] =
    filter(_.referencedTypeDecl.internal)

  /**
    * Filter for types whos corresponding type declaration is not in the analyzed jar.
    * */
  def external: NodeSteps[A] =
    filter(_.referencedTypeDecl.external)

  /**
    * Member variables of the corresponding type declaration.
    * */
  def member: NodeSteps[nodes.Member] =
    referencedTypeDecl.member

  /**
    * Direct base types of the corresponding type declaration in the inheritance graph.
    * */
  def baseType: NodeSteps[nodes.Type] =
    referencedTypeDecl.baseType

  /**
    * Direct and transitive base types of the corresponding type declaration.
    * */
  def baseTypeTransitive: NodeSteps[nodes.Type] = {
    repeat(_.baseType).emit()
  }

  /**
    * Direct derived types.
    * */
  def derivedType: NodeSteps[nodes.Type] =
    derivedTypeDecl.referencingType

  /**
    * Direct and transitive derived types.
    * */
  def derivedTypeTransitive: NodeSteps[nodes.Type] =
    repeat(_.derivedType).emit()

  /**
    * Type declaration which is referenced by this type.
    */
  def referencedTypeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(raw.out(EdgeTypes.REF).cast[nodes.TypeDecl])

  /**
    * Type declarations which derive from this type.
    */
  def derivedTypeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(raw.in(EdgeTypes.INHERITS_FROM).cast[nodes.TypeDecl])

  /**
    * Direct alias type declarations.
    */
  def aliasTypeDecl: NodeSteps[nodes.TypeDecl] = {
    new NodeSteps(raw.in(EdgeTypes.ALIAS_OF).cast[nodes.TypeDecl])
  }

  /**
    * Direct alias types.
    */
  def aliasType: NodeSteps[nodes.Type] = {
    aliasTypeDecl.referencingType
  }

  /**
    * Direct and transitive alias types.
    */
  def aliasTypeTransitive: NodeSteps[nodes.Type] = {
    repeat(_.aliasType).emit()
  }

  def localsOfType: NodeSteps[nodes.Local] =
    new NodeSteps(raw.in(EdgeTypes.EVAL_TYPE).hasLabel(NodeTypes.LOCAL).cast[nodes.Local])

  def expressionOfType: NodeSteps[nodes.Expression] =
    new NodeSteps(
      raw
        .in(EdgeTypes.EVAL_TYPE)
        .hasLabel(NodeTypes.IDENTIFIER, NodeTypes.CALL, NodeTypes.LITERAL)
        .cast[nodes.Expression])
}
