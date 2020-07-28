package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class Type(val traversal: Traversal[nodes.Type]) extends AnyVal {

  /**
    * Namespaces in which the corresponding type declaration is defined.
    * */
  def namespace: Traversal[nodes.Namespace] =
    referencedTypeDecl.namespace

  /**
    * Methods defined on the corresponding type declaration.
    * */
  def method: Traversal[nodes.Method] =
    referencedTypeDecl.method

  /**
    * Filter for types whos corresponding type declaration is in the analyzed jar.
    * */
  def internal: Traversal[nodes.Type] =
    traversal.where(_.referencedTypeDecl.internal)

  /**
    * Filter for types whos corresponding type declaration is not in the analyzed jar.
    * */
  def external: Traversal[nodes.Type] =
    traversal.where(_.referencedTypeDecl.external)

  /**
    * Member variables of the corresponding type declaration.
    * */
  def member: Traversal[nodes.Member] =
    referencedTypeDecl.member

  /**
    * Direct base types of the corresponding type declaration in the inheritance graph.
    * */
  def baseType: Traversal[nodes.Type] =
    referencedTypeDecl.baseType

  /**
    * Direct and transitive base types of the corresponding type declaration.
    * */
  def baseTypeTransitive: Traversal[nodes.Type] =
    traversal.repeat(_.baseType)(_.emitAllButFirst)

  /**
    * Direct derived types.
    * */
  def derivedType: Traversal[nodes.Type] =
    derivedTypeDecl.referencingType

  /**
    * Direct and transitive derived types.
    * */
  def derivedTypeTransitive: Traversal[nodes.Type] =
    traversal.repeat(_.derivedType)(_.emitAllButFirst)

  /**
    * Type declaration which is referenced by this type.
    */
  def referencedTypeDecl: Traversal[nodes.TypeDecl] =
    traversal.out(EdgeTypes.REF).cast[nodes.TypeDecl]

  /**
    * Type declarations which derive from this type.
    */
  def derivedTypeDecl: Traversal[nodes.TypeDecl] =
    traversal.in(EdgeTypes.INHERITS_FROM).cast[nodes.TypeDecl]

  /**
    * Direct alias type declarations.
    */
  def aliasTypeDecl: Traversal[nodes.TypeDecl] =
    traversal.in(EdgeTypes.ALIAS_OF).cast[nodes.TypeDecl]

  /**
    * Direct alias types.
    */
  def aliasType: Traversal[nodes.Type] =
    aliasTypeDecl.referencingType

  /**
    * Direct and transitive alias types.
    */
  def aliasTypeTransitive: Traversal[nodes.Type] =
    traversal.repeat(_.aliasType)(_.emitAllButFirst)

  def localsOfType: Traversal[nodes.Local] =
    traversal.in(EdgeTypes.EVAL_TYPE).hasLabel(NodeTypes.LOCAL).cast[nodes.Local]

  @deprecated("Use expression step instead.")
  def expressionOfType: Traversal[nodes.Expression] = expression

  def expression: Traversal[nodes.Expression] =
      traversal
        .in(EdgeTypes.EVAL_TYPE)
        .collect { case node: nodes.Expression => node }

  def parameter: Traversal[nodes.MethodParameterIn] =
      traversal
        .in(EdgeTypes.EVAL_TYPE)
        .collect { case node: nodes.MethodParameterIn => node }
}
