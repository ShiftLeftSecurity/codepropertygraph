package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForType(val node: nodes.Type) extends AnyVal {

  /** Traverse to ARRAY_INITIALIZER via EVAL_TYPE IN edge.
    */
  def arrayInitializerViaEvalTypeIn: Iterator[nodes.ArrayInitializer] = evalTypeIn.collectAll[nodes.ArrayInitializer]

  /** Traverse to BLOCK via EVAL_TYPE IN edge.
    */
  def blockViaEvalTypeIn: Iterator[nodes.Block] = evalTypeIn.collectAll[nodes.Block]

  /** Traverse to CALL via EVAL_TYPE IN edge.
    */
  def callViaEvalTypeIn: Iterator[nodes.Call] = evalTypeIn.collectAll[nodes.Call]

  /** Traverse to CONTROL_STRUCTURE via EVAL_TYPE IN edge.
    */
  def controlStructureViaEvalTypeIn: Iterator[nodes.ControlStructure] = evalTypeIn.collectAll[nodes.ControlStructure]

  /** Traverse to IDENTIFIER via EVAL_TYPE IN edge.
    */
  def identifierViaEvalTypeIn: Iterator[nodes.Identifier] = evalTypeIn.collectAll[nodes.Identifier]

  /** Traverse to LITERAL via EVAL_TYPE IN edge.
    */
  def literalViaEvalTypeIn: Iterator[nodes.Literal] = evalTypeIn.collectAll[nodes.Literal]

  /** Traverse to LOCAL via EVAL_TYPE IN edge.
    */
  def localViaEvalTypeIn: Iterator[nodes.Local] = evalTypeIn.collectAll[nodes.Local]

  /** Traverse to MEMBER via EVAL_TYPE IN edge.
    */
  def memberViaEvalTypeIn: Iterator[nodes.Member] = evalTypeIn.collectAll[nodes.Member]

  /** Traverse to METHOD_PARAMETER_IN via EVAL_TYPE IN edge.
    */
  def methodParameterInViaEvalTypeIn: Iterator[nodes.MethodParameterIn] = evalTypeIn.collectAll[nodes.MethodParameterIn]

  /** Traverse to METHOD_PARAMETER_OUT via EVAL_TYPE IN edge.
    */
  def methodParameterOutViaEvalTypeIn: Iterator[nodes.MethodParameterOut] =
    evalTypeIn.collectAll[nodes.MethodParameterOut]

  /** Traverse to METHOD_REF via EVAL_TYPE IN edge.
    */
  def methodRefViaEvalTypeIn: Iterator[nodes.MethodRef] = evalTypeIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_RETURN via EVAL_TYPE IN edge.
    */
  def methodReturnViaEvalTypeIn: Iterator[nodes.MethodReturn] = evalTypeIn.collectAll[nodes.MethodReturn]

  /** Traverse to TYPE_ARGUMENT via AST OUT edge.
    */
  def typeArgumentViaAstOut: Iterator[nodes.TypeArgument] = astOut.collectAll[nodes.TypeArgument]

  /** Traverse to TYPE_ARGUMENT via REF IN edge.
    */
  def typeArgumentViaRefIn: Iterator[nodes.TypeArgument] = refIn.collectAll[nodes.TypeArgument]

  /** Traverse to TYPE_DECL via INHERITS_FROM IN edge.
    */
  def typeDeclViaInheritsFromIn: Iterator[nodes.TypeDecl] = inheritsFromIn.collectAll[nodes.TypeDecl]

  /** Traverse to TYPE_REF via EVAL_TYPE IN edge.
    */
  def typeRefViaEvalTypeIn: Iterator[nodes.TypeRef] = evalTypeIn.collectAll[nodes.TypeRef]

  /** Traverse to UNKNOWN via EVAL_TYPE IN edge.
    */
  def unknownViaEvalTypeIn: Iterator[nodes.Unknown] = evalTypeIn.collectAll[nodes.Unknown]

  /** Direct alias type declarations. Traverse to TYPE_DECL via ALIAS_OF IN edge.
    */
  @deprecated("please use aliasTypeDecl instead")
  def typeDeclViaAliasOfIn: Iterator[nodes.TypeDecl] = aliasTypeDecl

  /** Direct alias type declarations. Traverse to TYPE_DECL via ALIAS_OF IN edge.
    */
  def aliasTypeDecl: Iterator[nodes.TypeDecl] = aliasOfIn.collectAll[nodes.TypeDecl]

  /** Type declaration which is referenced by this type. Traverse to TYPE_DECL via REF OUT edge.
    */
  @deprecated("please use referencedTypeDecl instead")
  def typeDeclViaRefOut: Iterator[nodes.TypeDecl] = referencedTypeDecl

  /** Type declaration which is referenced by this type. Traverse to TYPE_DECL via REF OUT edge.
    */
  def referencedTypeDecl: Iterator[nodes.TypeDecl] = refOut.collectAll[nodes.TypeDecl]

  def aliasOfIn: Iterator[nodes.TypeDecl] = node._aliasOfIn.cast[nodes.TypeDecl]

  def astOut: Iterator[nodes.TypeArgument] = node._astOut.cast[nodes.TypeArgument]

  def evalTypeIn: Iterator[nodes.AstNode] = node._evalTypeIn.cast[nodes.AstNode]

  def inheritsFromIn: Iterator[nodes.TypeDecl] = node._inheritsFromIn.cast[nodes.TypeDecl]

  def refIn: Iterator[nodes.TypeArgument] = node._refIn.cast[nodes.TypeArgument]

  def refOut: Iterator[nodes.TypeDecl] = node._refOut.cast[nodes.TypeDecl]
}

final class AccessNeighborsForTypeTraversal(val traversal: Iterator[nodes.Type]) extends AnyVal {

  /** Traverse to ARRAY_INITIALIZER via EVAL_TYPE IN edge.
    */
  def arrayInitializerViaEvalTypeIn: Iterator[nodes.ArrayInitializer] =
    traversal.flatMap(_.arrayInitializerViaEvalTypeIn)

  /** Traverse to BLOCK via EVAL_TYPE IN edge.
    */
  def blockViaEvalTypeIn: Iterator[nodes.Block] = traversal.flatMap(_.blockViaEvalTypeIn)

  /** Traverse to CALL via EVAL_TYPE IN edge.
    */
  def callViaEvalTypeIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaEvalTypeIn)

  /** Traverse to CONTROL_STRUCTURE via EVAL_TYPE IN edge.
    */
  def controlStructureViaEvalTypeIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaEvalTypeIn)

  /** Traverse to IDENTIFIER via EVAL_TYPE IN edge.
    */
  def identifierViaEvalTypeIn: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaEvalTypeIn)

  /** Traverse to LITERAL via EVAL_TYPE IN edge.
    */
  def literalViaEvalTypeIn: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaEvalTypeIn)

  /** Traverse to LOCAL via EVAL_TYPE IN edge.
    */
  def localViaEvalTypeIn: Iterator[nodes.Local] = traversal.flatMap(_.localViaEvalTypeIn)

  /** Traverse to MEMBER via EVAL_TYPE IN edge.
    */
  def memberViaEvalTypeIn: Iterator[nodes.Member] = traversal.flatMap(_.memberViaEvalTypeIn)

  /** Traverse to METHOD_PARAMETER_IN via EVAL_TYPE IN edge.
    */
  def methodParameterInViaEvalTypeIn: Iterator[nodes.MethodParameterIn] =
    traversal.flatMap(_.methodParameterInViaEvalTypeIn)

  /** Traverse to METHOD_PARAMETER_OUT via EVAL_TYPE IN edge.
    */
  def methodParameterOutViaEvalTypeIn: Iterator[nodes.MethodParameterOut] =
    traversal.flatMap(_.methodParameterOutViaEvalTypeIn)

  /** Traverse to METHOD_REF via EVAL_TYPE IN edge.
    */
  def methodRefViaEvalTypeIn: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaEvalTypeIn)

  /** Traverse to METHOD_RETURN via EVAL_TYPE IN edge.
    */
  def methodReturnViaEvalTypeIn: Iterator[nodes.MethodReturn] = traversal.flatMap(_.methodReturnViaEvalTypeIn)

  /** Traverse to TYPE_ARGUMENT via AST OUT edge.
    */
  def typeArgumentViaAstOut: Iterator[nodes.TypeArgument] = traversal.flatMap(_.typeArgumentViaAstOut)

  /** Traverse to TYPE_ARGUMENT via REF IN edge.
    */
  def typeArgumentViaRefIn: Iterator[nodes.TypeArgument] = traversal.flatMap(_.typeArgumentViaRefIn)

  /** Traverse to TYPE_DECL via INHERITS_FROM IN edge.
    */
  def typeDeclViaInheritsFromIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaInheritsFromIn)

  /** Traverse to TYPE_REF via EVAL_TYPE IN edge.
    */
  def typeRefViaEvalTypeIn: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaEvalTypeIn)

  /** Traverse to UNKNOWN via EVAL_TYPE IN edge.
    */
  def unknownViaEvalTypeIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaEvalTypeIn)

  /** Direct alias type declarations. Traverse to TYPE_DECL via ALIAS_OF IN edge.
    */
  def aliasTypeDecl: Iterator[nodes.TypeDecl] = traversal.flatMap(_.aliasTypeDecl)

  /** Direct alias type declarations. Traverse to TYPE_DECL via ALIAS_OF IN edge.
    */
  @deprecated("please use aliasTypeDecl instead")
  def typeDeclViaAliasOfIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaAliasOfIn)

  /** Type declaration which is referenced by this type. Traverse to TYPE_DECL via REF OUT edge.
    */
  def referencedTypeDecl: Iterator[nodes.TypeDecl] = traversal.flatMap(_.referencedTypeDecl)

  /** Type declaration which is referenced by this type. Traverse to TYPE_DECL via REF OUT edge.
    */
  @deprecated("please use referencedTypeDecl instead")
  def typeDeclViaRefOut: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaRefOut)

  def aliasOfIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_.aliasOfIn)

  def astOut: Iterator[nodes.TypeArgument] = traversal.flatMap(_.astOut)

  def evalTypeIn: Iterator[nodes.AstNode] = traversal.flatMap(_.evalTypeIn)

  def inheritsFromIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_.inheritsFromIn)

  def refIn: Iterator[nodes.TypeArgument] = traversal.flatMap(_.refIn)

  def refOut: Iterator[nodes.TypeDecl] = traversal.flatMap(_.refOut)
}
