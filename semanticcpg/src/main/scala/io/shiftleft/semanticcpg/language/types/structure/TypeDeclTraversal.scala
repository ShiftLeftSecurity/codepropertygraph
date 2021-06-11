package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, Properties, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb._
import overflowdb.traversal.Traversal

/**
  * Type declaration - possibly a template that requires instantiation
  * */
class TypeDeclTraversal(val traversal: Traversal[nodes.TypeDecl]) extends AnyVal {
  import TypeDeclTraversal._

  /**
    * Types referencing to this type declaration.
    * */
  def referencingType: Traversal[nodes.Type] =
    traversal.in(EdgeTypes.REF).cast[nodes.Type]

  /**
    * Namespace in which this type declaration is defined
    * */
  def namespace: Traversal[nodes.Namespace] =
    traversal
      .in(EdgeTypes.AST)
      .hasLabel(NodeTypes.NAMESPACE_BLOCK)
      .out(EdgeTypes.REF)
      .cast[nodes.Namespace]

  /**
    * Methods defined as part of this type
    * */
  def method: Traversal[nodes.Method] =
    canonicalType.out(EdgeTypes.AST).hasLabel(NodeTypes.METHOD).cast[nodes.Method]

  /**
    * Filter for type declarations contained in the analyzed code.
    * */
  def internal: Traversal[nodes.TypeDecl] =
    canonicalType.has(Properties.IS_EXTERNAL -> false)

  /**
    * Filter for type declarations not contained in the analyzed code.
    * */
  def external: Traversal[nodes.TypeDecl] =
    canonicalType.has(Properties.IS_EXTERNAL -> true)

  /**
    * Member variables
    * */
  def member: Traversal[nodes.Member] =
    canonicalType.out.hasLabel(NodeTypes.MEMBER).cast[nodes.Member]

  /**
    * Direct base types in the inheritance graph.
    * */
  def baseType: Traversal[nodes.Type] =
    canonicalType.out(EdgeTypes.INHERITS_FROM).cast[nodes.Type]

  /**
    * Direct base type declaration.
    * */
  def derivedTypeDecl: Traversal[nodes.TypeDecl] =
    referencingType.derivedTypeDecl

  /**
    * Direct and transitive base type declaration.
    * */
  def derivedTypeDeclTransitive: Traversal[nodes.TypeDecl] =
    traversal.repeat(_.derivedTypeDecl)(_.emitAllButFirst)

  /**
    * Direct base type declaration.
    */
  def baseTypeDecl: Traversal[nodes.TypeDecl] =
    traversal.baseType.referencedTypeDecl

  /**
    * Direct and transitive base type declaration.
    */
  def baseTypeDeclTransitive: Traversal[nodes.TypeDecl] =
    traversal.repeat(_.baseTypeDecl)(_.emitAllButFirst)

  /**
    * Traverse to methods bound to this type decl.
    */
  def boundMethod: Traversal[nodes.Method] =
    methodBinding.boundMethod

  /**
    * Traverse to the method bindings of this type declaration.
    */
  def methodBinding: Traversal[nodes.Binding] =
    canonicalType.out(EdgeTypes.BINDS).cast[nodes.Binding]

  /**
    * Traverse to alias type declarations.
    */
  def isAlias: Traversal[nodes.TypeDecl] =
    traversal.filter(_.aliasTypeFullName.isDefined)

  /**
    * Traverse to canonical type declarations.
    */
  def isCanonical: Traversal[nodes.TypeDecl] =
    traversal.filter(_.aliasTypeFullName.isEmpty)

  /**
    * If this is an alias type declaration, go to its underlying type declaration
    * else unchanged.
    */
  def unravelAlias: Traversal[nodes.TypeDecl] =
    traversal.map { typeDecl =>
      if (typeDecl.aliasTypeFullName.isDefined)
        typeDecl._typeViaAliasOfOut.next()._typeDeclViaRefOut.next()
      else
        typeDecl
    }

  /**
    * Traverse to canonical type which means unravel aliases until we find
    * a non alias type declaration.
    */
  def canonicalType: Traversal[nodes.TypeDecl] = {
    // We cannot use this compact form because the gremlin implementation at least
    // in some case seems to have problems with nested "repeat" steps. Since this
    // step is used in other repeat steps we do not use it here.
    //until(_.isCanonical).repeat(_.unravelAlias)

    traversal.map { typeDecl =>
      var currentTypeDecl = typeDecl
      var aliasExpansionCounter = 0
      while (currentTypeDecl.aliasTypeFullName.isDefined && aliasExpansionCounter < maxAliasExpansions) {
        currentTypeDecl = currentTypeDecl._typeViaAliasOfOut.next()._typeDeclViaRefOut.next()
        aliasExpansionCounter += 1
      }
      currentTypeDecl
    }
  }

  /**
    *  Direct alias type declarations.
    */
  def aliasTypeDecl: Traversal[nodes.TypeDecl] =
    referencingType.aliasTypeDecl

  /**
    *  Direct and transitive alias type declarations.
    */
  def aliasTypeDeclTransitive: Traversal[nodes.TypeDecl] =
    traversal.repeat(_.aliasTypeDecl)(_.emitAllButFirst)

}

object TypeDeclTraversal {
  private val maxAliasExpansions = 100
}
