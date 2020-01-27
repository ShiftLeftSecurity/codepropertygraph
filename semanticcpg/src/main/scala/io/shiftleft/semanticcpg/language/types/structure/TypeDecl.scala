package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors.ModifierAccessors
import org.apache.tinkerpop.gremlin.structure.Direction

/**
  * Type declaration - possibly a template that requires instantiation
  * */
class TypeDecl[A <: nodes.TypeDecl](raw: GremlinScala[A]) extends NodeSteps[A](raw) with ModifierAccessors[A] {
  import TypeDecl._

  /**
    * Types referencing to this type declaration.
    * */
  def referencingType: NodeSteps[nodes.Type] =
    new NodeSteps(raw.in(EdgeTypes.REF).cast[nodes.Type])

  /**
    * Namespace in which this type declaration is defined
    * */
  def namespace: NodeSteps[nodes.Namespace] =
    new NodeSteps(
      raw
        .in(EdgeTypes.AST)
        .hasLabel(NodeTypes.NAMESPACE_BLOCK)
        .out(EdgeTypes.REF)
        .cast[nodes.Namespace])

  /**
    * Methods defined as part of this type
    * */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(canonicalType.raw.out(EdgeTypes.AST).hasLabel(NodeTypes.METHOD).cast[nodes.Method])

  /**
    * Filter for type declarations contained in the analyzed code.
    * */
  def internal: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(canonicalType.raw.has(NodeKeys.IS_EXTERNAL -> false))

  /**
    * Filter for type declarations not contained in the analyzed code.
    * */
  def external: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(canonicalType.raw.has(NodeKeys.IS_EXTERNAL -> true))

  /**
    * Member variables
    * */
  def member: NodeSteps[nodes.Member] =
    new NodeSteps(canonicalType.raw.out().hasLabel(NodeTypes.MEMBER).cast[nodes.Member])

  /**
    * Direct base types in the inheritance graph.
    * */
  def baseType: NodeSteps[nodes.Type] =
    new NodeSteps(canonicalType.raw.out(EdgeTypes.INHERITS_FROM).cast[nodes.Type])

  /**
    * Direct base type declaration.
    * */
  def derivedTypeDecl: NodeSteps[nodes.TypeDecl] =
    referencingType.derivedTypeDecl

  /**
    * Direct and transitive base type declaration.
    * */
  def derivedTypeDeclTransitive: NodeSteps[nodes.TypeDecl] =
    repeat(_.derivedTypeDecl).emit()

  /**
    * Direct base type declaration.
    */
  def baseTypeDecl: NodeSteps[nodes.TypeDecl] =
    baseType.referencedTypeDecl

  /**
    * Direct and transitive base type declaration.
    */
  def baseTypeDeclTransitive: NodeSteps[nodes.TypeDecl] =
    repeat(_.baseTypeDecl).emit()

  /**
    * Traverse to methods bound to this type decl.
    */
  def boundMethod: NodeSteps[nodes.Method] =
    methodBinding.boundMethod

  /**
    * Traverse to the method bindings of this type declaration.
    */
  def methodBinding: NodeSteps[nodes.Binding] =
    new NodeSteps(canonicalType.raw.out(EdgeTypes.BINDS).cast[nodes.Binding])

  /**
    * Traverse to alias type declarations.
    */
  def isAlias: NodeSteps[A] =
    new NodeSteps(raw.filterOnEnd(_.aliasTypeFullName.isDefined))

  /**
    * Traverse to canonical type declarations.
    */
  def isCanonical: NodeSteps[A] =
    new NodeSteps(raw.filterOnEnd(_.aliasTypeFullName.isEmpty))

  /**
    * If this is an alias type declaration, go to its underlying type declaration
    * else unchanged.
    */
  def unravelAlias: NodeSteps[nodes.TypeDecl] = {
    new NodeSteps(raw.map { typeDecl =>
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
  def canonicalType: NodeSteps[nodes.TypeDecl] = {
    // We cannot use this compact form because the gremlin implementation at least
    // in some case seems to have problems with nested "repeat" steps. Since this
    // step is used in other repeat steps we do not use it here.
    //until(_.isCanonical).repeat(_.unravelAlias)

    new NodeSteps(raw.map { typeDecl =>
      var currentTypeDecl: nodes.TypeDecl = typeDecl
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
  def aliasTypeDecl: NodeSteps[nodes.TypeDecl] =
    referencingType.aliasTypeDecl

  /**
    *  Direct and transitive alias type declarations.
    */
  def aliasTypeDeclTransitive: NodeSteps[nodes.TypeDecl] =
    repeat(_.aliasTypeDecl).emit()

}

object TypeDecl {
  private val maxAliasExpansions = 100
}
