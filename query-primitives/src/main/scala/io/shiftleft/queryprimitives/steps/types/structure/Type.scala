package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.{Declaration, Expression}
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{FullNameAccessors, NameAccessors}
import shapeless.{HList, HNil}

class Type[Labels <: HList](raw: GremlinScala.Aux[nodes.Type, Labels])
    extends NodeSteps[nodes.Type, Labels](raw)
    with NameAccessors[nodes.Type, Labels]
    with FullNameAccessors[nodes.Type, Labels] {

  /**
    * Namespaces in which the corresponding type declaration is defined.
    * */
  def namespace: Namespace[Labels] =
    referencedTypeDecl.namespace

  /**
    * Methods defined on the corresponding type declaration.
    * */
  def method: Method[Labels] =
    referencedTypeDecl.method

  /**
    * Filter for types whos corresponding type declaration is in the analyzed jar.
    * */
  def internal: Type[Labels] =
    filter(_.referencedTypeDecl.internal)

  /**
    * Filter for types whos corresponding type declaration is not in the analyzed jar.
    * */
  def external: Type[Labels] =
    filter(_.referencedTypeDecl.external)

  /**
    * Member variables of the corresponding type declaration.
    * */
  def member: Member[Labels] =
    referencedTypeDecl.member

  /**
    * Direct base types of the corresponding type declaration in the inheritance graph.
    * */
  def baseType: Type[Labels] =
    referencedTypeDecl.baseType

  /**
    * Direct and transitive base types of the corresponding type declaration.
    * */
  def baseTypeTransitive: Type[Labels] = {
    repeat(_.baseType).emit()
  }

  /**
    * Direct derived types.
    * */
  def derivedType: Type[Labels] =
    derivedTypeDecl.referencingType

  /**
    * Direct and transitive derived types.
    * */
  def derivedTypeTransitive: Type[Labels] =
    repeat(_.derivedType).emit()

  /**
    * Type declaration which is referenced by this type.
    */
  def referencedTypeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](raw.out(EdgeTypes.REF).cast[nodes.TypeDecl])

  /**
    * Type declarations which derive from this type.
    */
  def derivedTypeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](raw.in(EdgeTypes.INHERITS_FROM).cast[nodes.TypeDecl])

  /**
    * Direct alias type declarations.
    */
  def aliasTypeDecl: TypeDecl[Labels] = {
    new TypeDecl[Labels](raw.in(EdgeTypes.ALIAS_OF).cast[nodes.TypeDecl])
  }

  /**
    * Direct alias types.
    */
  def aliasType: Type[Labels] = {
    aliasTypeDecl.referencingType
  }

  /**
    * Direct and transitive alias types.
    */
  def aliasTypeTransitive: Type[Labels] = {
    repeat(_.aliasType).emit()
  }

  def localsOfType: Local[Labels] =
    new Local[Labels](raw.in(EdgeTypes.EVAL_TYPE).hasLabel(NodeTypes.LOCAL).cast[nodes.Local])

  def expressionOfType: Expression[Labels] =
    new Expression[Labels](
      raw
        .in(EdgeTypes.EVAL_TYPE)
        .hasLabel(NodeTypes.IDENTIFIER, NodeTypes.CALL, NodeTypes.LITERAL)
        .cast[nodes.Expression])
}
