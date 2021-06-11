package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class TypeTraversal(val traversal: Traversal[Type]) extends AnyVal {

  /**
    * Namespaces in which the corresponding type declaration is defined.
    * */
  def namespace: Traversal[Namespace] =
    referencedTypeDecl.namespace

  /**
    * Methods defined on the corresponding type declaration.
    * */
  def method: Traversal[Method] =
    referencedTypeDecl.method

  /**
    * Filter for types whos corresponding type declaration is in the analyzed jar.
    * */
  def internal: Traversal[Type] =
    traversal.where(_.referencedTypeDecl.internal)

  /**
    * Filter for types whos corresponding type declaration is not in the analyzed jar.
    * */
  def external: Traversal[Type] =
    traversal.where(_.referencedTypeDecl.external)

  /**
    * Member variables of the corresponding type declaration.
    * */
  def member: Traversal[Member] =
    referencedTypeDecl.member

  /**
    * Direct base types of the corresponding type declaration in the inheritance graph.
    * */
  def baseType: Traversal[Type] =
    referencedTypeDecl.baseType

  /**
    * Direct and transitive base types of the corresponding type declaration.
    * */
  def baseTypeTransitive: Traversal[Type] =
    traversal.repeat(_.baseType)(_.emitAllButFirst)

  /**
    * Direct derived types.
    * */
  def derivedType: Traversal[Type] =
    derivedTypeDecl.referencingType

  /**
    * Direct and transitive derived types.
    * */
  def derivedTypeTransitive: Traversal[Type] =
    traversal.repeat(_.derivedType)(_.emitAllButFirst)

  /**
    * Type declaration which is referenced by this type.
    */
  def referencedTypeDecl: Traversal[TypeDecl] =
    traversal.out(EdgeTypes.REF).cast[TypeDecl]

  /**
    * Type declarations which derive from this type.
    */
  def derivedTypeDecl: Traversal[TypeDecl] =
    traversal.in(EdgeTypes.INHERITS_FROM).cast[TypeDecl]

  /**
    * Direct alias type declarations.
    */
  def aliasTypeDecl: Traversal[TypeDecl] =
    traversal.in(EdgeTypes.ALIAS_OF).cast[TypeDecl]

  /**
    * Direct alias types.
    */
  def aliasType: Traversal[Type] =
    aliasTypeDecl.referencingType

  /**
    * Direct and transitive alias types.
    */
  def aliasTypeTransitive: Traversal[Type] =
    traversal.repeat(_.aliasType)(_.emitAllButFirst)

  def localOfType: Traversal[Local] =
    traversal.in(EdgeTypes.EVAL_TYPE).hasLabel(NodeTypes.LOCAL).cast[Local]

  def memberOfType: Traversal[Member] =
    traversal.in(EdgeTypes.EVAL_TYPE).hasLabel(NodeTypes.MEMBER).cast[Member]

  @deprecated("Please use `parameterOfType`")
  def parameter: Traversal[MethodParameterIn] = parameterOfType

  def parameterOfType: Traversal[MethodParameterIn] =
    traversal
      .in(EdgeTypes.EVAL_TYPE)
      .collectAll[MethodParameterIn]

  def methodReturnOfType: Traversal[MethodReturn] =
    traversal
      .in(EdgeTypes.EVAL_TYPE)
      .hasLabel(NodeTypes.METHOD_RETURN)
      .cast[MethodReturn]

  def expressionOfType: Traversal[Expression] = expression

  def expression: Traversal[Expression] =
    traversal
      .in(EdgeTypes.EVAL_TYPE)
      .collectAll[Expression]

}
