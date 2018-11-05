package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.{Declaration, Expression}
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{FullNameAccessors, NameAccessors}
import shapeless.{HList, HNil}

class Type2[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Type2, Labels](raw)
    with NameAccessors[nodes.Type2, Labels]
    with FullNameAccessors[nodes.Type2, Labels] {
  override val converter = Converter.forDomainNode[nodes.Type2]

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
  // def internal: Type[Labels] =
  //   filter(_.referencedTypeDecl.internal)

  /**
    * Filter for types whos corresponding type declaration is not in the analyzed jar.
    * */
  // def external: Type[Labels] =
  //   filter(_.referencedTypeDecl.external)

  /**
    * Member variables of the corresponding type declaration.
    * */
  def member: Member[Labels] =
    referencedTypeDecl.member

  /**
    * Direct base types of the corresponding type declarationi in the inheritance graph.
    * */
  def baseType: Type[Labels] =
    referencedTypeDecl.baseType

  /**
    * Direct and transitive base types of the corresponding type declarationi.
    * */
  // def baseTypeTransitive: Type[Labels] = {
  //   repeat(_.baseType).emit()
  // }

  /**
    * Direct derived types.
    * */
  def derivedType: Type[Labels] =
    derivedTypeDecl.referencingType

  /**
    * Direct and transitive derived types.
    * */
  // def derivedTypeTransitive: Type[Labels] =
  //   repeat(_.derivedType).emit()

  /**
    * Type declaration which is referenced by this type.
    */
  def referencedTypeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](raw.out(EdgeTypes.REF))

  /**
    * Type declarations which derive from this type.
    */
  def derivedTypeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](raw.in(EdgeTypes.INHERITS_FROM))

  def localsOfType: Local[Labels] =
    new Local[Labels](raw.in(EdgeTypes.EVAL_TYPE).hasLabel(NodeTypes.LOCAL))

  def expressionOfType: Expression[Labels] =
    new Expression[Labels](
      raw
        .in(EdgeTypes.EVAL_TYPE)
        .hasLabel(NodeTypes.IDENTIFIER, NodeTypes.CALL, NodeTypes.LITERAL)
    )
}
