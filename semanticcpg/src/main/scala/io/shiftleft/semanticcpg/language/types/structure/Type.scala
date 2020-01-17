package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression

class Type(raw: GremlinScala[nodes.Type]) extends NodeSteps[nodes.Type](raw) {

  /**
    * Namespaces in which the corresponding type declaration is defined.
    * */
  def namespace: Namespace =
    referencedTypeDecl.namespace

  /**
    * Methods defined on the corresponding type declaration.
    * */
  def method: Method =
    referencedTypeDecl.method

  /**
    * Filter for types whos corresponding type declaration is in the analyzed jar.
    * */
  def internal: Type =
    filter(_.referencedTypeDecl.internal)

  /**
    * Filter for types whos corresponding type declaration is not in the analyzed jar.
    * */
  def external: Type =
    filter(_.referencedTypeDecl.external)

  /**
    * Member variables of the corresponding type declaration.
    * */
  def member: Member =
    referencedTypeDecl.member

  /**
    * Direct base types of the corresponding type declaration in the inheritance graph.
    * */
  def baseType: Type =
    referencedTypeDecl.baseType

  /**
    * Direct and transitive base types of the corresponding type declaration.
    * */
  def baseTypeTransitive: Type = {
    repeat(_.baseType).emit()
  }

  /**
    * Direct derived types.
    * */
  def derivedType: Type =
    derivedTypeDecl.referencingType

  /**
    * Direct and transitive derived types.
    * */
  def derivedTypeTransitive: Type =
    repeat(_.derivedType).emit()

  /**
    * Type declaration which is referenced by this type.
    */
  def referencedTypeDecl: TypeDecl =
    new TypeDecl(raw.out(EdgeTypes.REF).cast[nodes.TypeDecl])

  /**
    * Type declarations which derive from this type.
    */
  def derivedTypeDecl: TypeDecl =
    new TypeDecl(raw.in(EdgeTypes.INHERITS_FROM).cast[nodes.TypeDecl])

  /**
    * Direct alias type declarations.
    */
  def aliasTypeDecl: TypeDecl = {
    new TypeDecl(raw.in(EdgeTypes.ALIAS_OF).cast[nodes.TypeDecl])
  }

  /**
    * Direct alias types.
    */
  def aliasType: Type = {
    aliasTypeDecl.referencingType
  }

  /**
    * Direct and transitive alias types.
    */
  def aliasTypeTransitive: Type = {
    repeat(_.aliasType).emit()
  }

  def localsOfType: Local =
    new Local(raw.in(EdgeTypes.EVAL_TYPE).hasLabel(NodeTypes.LOCAL).cast[nodes.Local])

  def expressionOfType: Expression =
    new Expression(
      raw
        .in(EdgeTypes.EVAL_TYPE)
        .hasLabel(NodeTypes.IDENTIFIER, NodeTypes.CALL, NodeTypes.LITERAL)
        .cast[nodes.Expression])
}
