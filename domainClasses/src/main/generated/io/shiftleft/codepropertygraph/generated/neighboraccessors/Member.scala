package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForMember(val node: nodes.Member) extends AnyVal {

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def annotationViaAstOut: Iterator[nodes.Annotation] = astOut.collectAll[nodes.Annotation]

  /** Traverse to CALL via REF IN edge.
    */
  def callViaRefIn: Iterator[nodes.Call] = refIn.collectAll[nodes.Call]

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def modifierViaAstOut: Iterator[nodes.Modifier] = astOut.collectAll[nodes.Modifier]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def unknownViaAstIn: Iterator[nodes.Unknown] = astIn.collectAll[nodes.Unknown]

  /** The type declaration this member is defined in Traverse to TYPE_DECL via AST IN edge.
    */
  @deprecated("please use typeDecl instead")
  def typeDeclViaAstIn: nodes.TypeDecl = typeDecl

  /** The type declaration this member is defined in Traverse to TYPE_DECL via AST IN edge.
    */
  def typeDecl: nodes.TypeDecl = {
    try { astIn.collectAll[nodes.TypeDecl].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        throw new flatgraph.SchemaViolationException(
          "IN edge with label AST to an adjacent TYPE_DECL is mandatory, but not defined for this MEMBER node with seq=" + node.seq,
          e
        )
    }
  }

  /** Traverse to member type Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  @deprecated("please use typ instead")
  def typeViaEvalTypeOut: Iterator[nodes.Type] = typ

  /** Traverse to member type Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def typ: Iterator[nodes.Type] = evalTypeOut.collectAll[nodes.Type]

  def astIn: Iterator[nodes.AstNode] = node._astIn.cast[nodes.AstNode]

  def astOut: Iterator[nodes.AstNode] = node._astOut.cast[nodes.AstNode]

  def evalTypeOut: Iterator[nodes.Type] = node._evalTypeOut.cast[nodes.Type]

  def refIn: Iterator[nodes.Call] = node._refIn.cast[nodes.Call]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]
}

final class AccessNeighborsForMemberTraversal(val traversal: Iterator[nodes.Member]) extends AnyVal {

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def annotationViaAstOut: Iterator[nodes.Annotation] = traversal.flatMap(_.annotationViaAstOut)

  /** Traverse to CALL via REF IN edge.
    */
  def callViaRefIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaRefIn)

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def modifierViaAstOut: Iterator[nodes.Modifier] = traversal.flatMap(_.modifierViaAstOut)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.tagViaTaggedByOut)

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def unknownViaAstIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaAstIn)

  /** The type declaration this member is defined in Traverse to TYPE_DECL via AST IN edge.
    */
  def typeDecl: Iterator[nodes.TypeDecl] = traversal.map(_.typeDecl)

  /** The type declaration this member is defined in Traverse to TYPE_DECL via AST IN edge.
    */
  @deprecated("please use typeDecl instead")
  def typeDeclViaAstIn: Iterator[nodes.TypeDecl] = traversal.map(_.typeDeclViaAstIn)

  /** Traverse to member type Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def typ: Iterator[nodes.Type] = traversal.flatMap(_.typ)

  /** Traverse to member type Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  @deprecated("please use typ instead")
  def typeViaEvalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_.typeViaEvalTypeOut)

  def astIn: Iterator[nodes.AstNode] = traversal.flatMap(_.astIn)

  def astOut: Iterator[nodes.AstNode] = traversal.flatMap(_.astOut)

  def evalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_.evalTypeOut)

  def refIn: Iterator[nodes.Call] = traversal.flatMap(_.refIn)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)
}
