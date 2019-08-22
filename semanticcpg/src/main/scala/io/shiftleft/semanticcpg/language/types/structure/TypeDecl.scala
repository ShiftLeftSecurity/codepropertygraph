package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Modifier
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{FullNameAccessors, IsExternalAccessor, NameAccessors}
import org.apache.tinkerpop.gremlin.structure.Direction
import shapeless.HList

/**
  * Type declaration - possibly a template that requires instantiation
  * */
class TypeDecl[Labels <: HList](raw: GremlinScala.Aux[nodes.TypeDecl, Labels])
    extends NodeSteps[nodes.TypeDecl, Labels](raw)
    with NameAccessors[nodes.TypeDecl, Labels]
    with FullNameAccessors[nodes.TypeDecl, Labels]
    with IsExternalAccessor[nodes.TypeDecl, Labels] {
  import TypeDecl._

  /**
    * Types referencing to this type declaration.
    * */
  def referencingType: Type[Labels] =
    new Type[Labels](raw.in(EdgeTypes.REF).cast[nodes.Type])

  /**
    * Namespace in which this type declaration is defined
    * */
  def namespace: Namespace[Labels] =
    new Namespace[Labels](
      raw
        .in(EdgeTypes.AST)
        .hasLabel(NodeTypes.NAMESPACE_BLOCK)
        .out(EdgeTypes.REF)
        .cast[nodes.Namespace])

  /**
    * Methods defined as part of this type
    * */
  def method: Method[Labels] =
    new Method[Labels](canonicalType.raw.out(EdgeTypes.AST).hasLabel(NodeTypes.METHOD).cast[nodes.Method])

  /**
    * Filter for type declarations contained in the analyzed code.
    * */
  def internal: TypeDecl[Labels] =
    new TypeDecl[Labels](canonicalType.raw.has(NodeKeys.IS_EXTERNAL -> false))

  /**
    * Filter for type declarations not contained in the analyzed code.
    * */
  def external: TypeDecl[Labels] =
    new TypeDecl[Labels](canonicalType.raw.has(NodeKeys.IS_EXTERNAL -> true))

  /**
    * Member variables
    * */
  def member: Member[Labels] =
    new Member[Labels](canonicalType.raw.out().hasLabel(NodeTypes.MEMBER).cast[nodes.Member])

  /**
    * Direct base types in the inheritance graph.
    * */
  def baseType: Type[Labels] =
    new Type(canonicalType.raw.out(EdgeTypes.INHERITS_FROM).cast[nodes.Type])

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
      canonicalType.raw.out(EdgeTypes.VTABLE).cast[nodes.Method]
    )
  }

  /**
    * Traverse to method modifiers, e.g., "static", "public".
    * */
  def modifier: Modifier[Labels] =
    new Modifier[Labels](
      canonicalType.raw.out
        .hasLabel(NodeTypes.MODIFIER)
        .cast[nodes.Modifier]
    )

  /**
    * Traverse to alias type declarations.
    */
  def isAlias: TypeDecl[Labels] = {
    new TypeDecl[Labels](raw.filterOnEnd(_.aliasTypeFullName.isDefined))
  }

  /**
    * Traverse to canonical type declarations.
    */
  def isCanonical: TypeDecl[Labels] = {
    new TypeDecl[Labels](raw.filterOnEnd(_.aliasTypeFullName.isEmpty))
  }

  /**
    * If this is an alias type declaration, go to its underlying type declaration
    * else unchanged.
    */
  def unravelAlias: TypeDecl[Labels] = {
    new TypeDecl[Labels](raw.map { typeDecl =>
      if (typeDecl.aliasTypeFullName.isDefined) {
        typeDecl
          .vertices(Direction.OUT, EdgeTypes.ALIAS_OF)
          .next
          .asInstanceOf[nodes.Type]
          .vertices(Direction.OUT, EdgeTypes.REF)
          .next
          .asInstanceOf[nodes.TypeDecl]
      } else {
        typeDecl
      }
    })
  }

  /**
    * Traverse to canonical type which means unravel aliases until we find
    * a non alias type declaration.
    */
  def canonicalType: TypeDecl[Labels] = {
    // We cannot use this compact form because the gremlin implementation at least
    // in some case seems to have problems with nested "repeat" steps. Since this
    // step is used in other repeat steps we do not use it here.
    //until(_.isCanonical).repeat(_.unravelAlias)

    new TypeDecl[Labels](raw.map { typeDecl =>
      var currentTypeDecl = typeDecl
      var aliasExpansionCounter = 0
      while (currentTypeDecl.aliasTypeFullName.isDefined && aliasExpansionCounter < maxAliasExpansions) {
        currentTypeDecl = currentTypeDecl
          .vertices(Direction.OUT, EdgeTypes.ALIAS_OF)
          .next
          .asInstanceOf[nodes.Type]
          .vertices(Direction.OUT, EdgeTypes.REF)
          .next
          .asInstanceOf[nodes.TypeDecl]
        aliasExpansionCounter += 1
      }
      currentTypeDecl
    })
  }

  /**
    *  Direct alias type declarations.
    */
  def aliasTypeDecl: TypeDecl[Labels] = {
    referencingType.aliasTypeDecl
  }

  /**
    *  Direct and transitive alias type declarations.
    */
  def alisTypeDeclTransitive: TypeDecl[Labels] = {
    repeat(_.aliasTypeDecl).emit()
  }
}

object TypeDecl {
  private val maxAliasExpansions = 100
}
