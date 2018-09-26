package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{FullNameAccessors, IsExternalAccessor, NameAccessors}
import shapeless.HList

/**
  * Type declaration - possibly a template that requires instantiation
  * */
class TypeDecl[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.TypeDecl, Labels](raw)
    with NameAccessors[nodes.TypeDecl, Labels]
    with FullNameAccessors[nodes.TypeDecl, Labels]
    with IsExternalAccessor[nodes.TypeDecl, Labels] {
  override val converter = Converter.forDomainNode[nodes.TypeDecl]

  /**
    * Types referencing to this type declaration.
    * */
  def referencingType: Type[Labels] =
    new Type[Labels](raw.in(EdgeTypes.REF))

  /**
    * Namespace in which this type declaration is defined
    * */
  def namespace: Namespace[Labels] =
    new Namespace[Labels](
      raw
        .in(EdgeTypes.AST)
        .hasLabel(NodeTypes.NAMESPACE_BLOCK)
        .out(EdgeTypes.REF)
    )

  /**
    * Methods defined as part of this type
    * */
  def method: Method[Labels] =
    new Method[Labels](raw.out(EdgeTypes.AST).hasLabel(NodeTypes.METHOD))

  /**
    * Filter for type declarations contained in the analyzed code.
    * */
  def internal: TypeDecl[Labels] =
    new TypeDecl[Labels](raw.has(NodeKeys.IS_EXTERNAL -> false))

  /**
    * Filter for type declarations not contained in the analyzed code.
    * */
  def external: TypeDecl[Labels] =
    new TypeDecl[Labels](raw.has(NodeKeys.IS_EXTERNAL -> true))

  /**
    * Member variables
    * */
  def member: Member[Labels] =
    new Member[Labels](raw.out().hasLabel(NodeTypes.MEMBER))

  /**
    * Direct base types in the inheritance graph.
    * */
  def baseType: Type[Labels] =
    new Type(raw.out(EdgeTypes.INHERITS_FROM))

  /**
    * Direct base type declaration.
    * */
  def derivedTypeDecl: TypeDecl[Labels] =
    referencingType.derivedTypeDecl

  /**
    * Direct and transitive base type declaration.
    * */
  def derivedTypeDeclTransitive: TypeDecl[Labels] =
    repeat(_.derivedTypeDecl).emit()

  /**
    * Direct base type declaration.
    */
  def baseTypeDecl: TypeDecl[Labels] =
    baseType.referencedTypeDecl

  /**
    * Direct and transitive base type declaration.
    */
  def baseTypeDeclTransitive: TypeDecl[Labels] =
    repeat(_.baseTypeDecl).emit()

  /**
    * Traverse to the methods which are part of the VTables of this type declaration.
    */
  def vtableMethod: Method[Labels] = {
    new Method[Labels](
      raw.out(EdgeTypes.VTABLE)
    )
  }
}
