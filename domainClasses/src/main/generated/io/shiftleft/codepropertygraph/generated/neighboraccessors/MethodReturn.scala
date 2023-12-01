package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.Language.*

final class AccessNeighborsForMethodReturn(val node: nodes.MethodReturn) extends AnyVal {

  /** Traverse to BLOCK via CDG IN edge.
    */
  def blockViaCdgIn: Iterator[nodes.Block] = cdgIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def blockViaDominateIn: Iterator[nodes.Block] = dominateIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def blockViaPostDominateOut: Iterator[nodes.Block] = postDominateOut.collectAll[nodes.Block]

  /** Traverse to CALL via CDG IN edge.
    */
  def callViaCdgIn: Iterator[nodes.Call] = cdgIn.collectAll[nodes.Call]

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def callViaDominateIn: Iterator[nodes.Call] = dominateIn.collectAll[nodes.Call]

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def callViaPostDominateOut: Iterator[nodes.Call] = postDominateOut.collectAll[nodes.Call]

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def controlStructureViaCdgIn: Iterator[nodes.ControlStructure] = cdgIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def controlStructureViaDominateIn: Iterator[nodes.ControlStructure] = dominateIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def controlStructureViaPostDominateOut: Iterator[nodes.ControlStructure] =
    postDominateOut.collectAll[nodes.ControlStructure]

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def fieldIdentifierViaCdgIn: Iterator[nodes.FieldIdentifier] = cdgIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def fieldIdentifierViaDominateIn: Iterator[nodes.FieldIdentifier] = dominateIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def fieldIdentifierViaPostDominateOut: Iterator[nodes.FieldIdentifier] =
    postDominateOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def identifierViaCdgIn: Iterator[nodes.Identifier] = cdgIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def identifierViaDominateIn: Iterator[nodes.Identifier] = dominateIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def identifierViaPostDominateOut: Iterator[nodes.Identifier] = postDominateOut.collectAll[nodes.Identifier]

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def jumpTargetViaCdgIn: Iterator[nodes.JumpTarget] = cdgIn.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def jumpTargetViaPostDominateOut: Iterator[nodes.JumpTarget] = postDominateOut.collectAll[nodes.JumpTarget]

  /** Traverse to LITERAL via CDG IN edge.
    */
  def literalViaCdgIn: Iterator[nodes.Literal] = cdgIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def literalViaDominateIn: Iterator[nodes.Literal] = dominateIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def literalViaPostDominateOut: Iterator[nodes.Literal] = postDominateOut.collectAll[nodes.Literal]

  /** Traverse to METHOD via AST IN edge.
    */
  def methodViaAstIn: nodes.Method = {
    try { astIn.collectAll[nodes.Method].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        throw new flatgraph.SchemaViolationException(
          "IN edge with label AST to an adjacent METHOD is mandatory, but not defined for this METHOD_RETURN node with seq=" + node.seq,
          e
        )
    }
  }

  /** Traverse to METHOD via DOMINATE IN edge.
    */
  def methodViaDominateIn: Iterator[nodes.Method] = dominateIn.collectAll[nodes.Method]

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def methodViaPostDominateOut: Iterator[nodes.Method] = postDominateOut.collectAll[nodes.Method]

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def methodRefViaCdgIn: Iterator[nodes.MethodRef] = cdgIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def methodRefViaDominateIn: Iterator[nodes.MethodRef] = dominateIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def methodRefViaPostDominateOut: Iterator[nodes.MethodRef] = postDominateOut.collectAll[nodes.MethodRef]

  /** Traverse to RETURN via CFG IN edge.
    */
  @deprecated("please use toReturn instead")
  def returnViaCfgIn: Iterator[nodes.Return] = toReturn

  /** Traverse to RETURN via CFG IN edge.
    */
  def toReturn: Iterator[nodes.Return] = cfgIn.collectAll[nodes.Return]

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def returnViaDominateIn: Iterator[nodes.Return] = dominateIn.collectAll[nodes.Return]

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def returnViaPostDominateOut: Iterator[nodes.Return] = postDominateOut.collectAll[nodes.Return]

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def returnViaReachingDefIn: Iterator[nodes.Return] = reachingDefIn.collectAll[nodes.Return]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def typeViaEvalTypeOut: Iterator[nodes.Type] = evalTypeOut.collectAll[nodes.Type]

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def typeRefViaCdgIn: Iterator[nodes.TypeRef] = cdgIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def typeRefViaDominateIn: Iterator[nodes.TypeRef] = dominateIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def typeRefViaPostDominateOut: Iterator[nodes.TypeRef] = postDominateOut.collectAll[nodes.TypeRef]

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def unknownViaCdgIn: Iterator[nodes.Unknown] = cdgIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def unknownViaDominateIn: Iterator[nodes.Unknown] = dominateIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def unknownViaPostDominateOut: Iterator[nodes.Unknown] = postDominateOut.collectAll[nodes.Unknown]

  def astIn: Iterator[nodes.Method] = node._astIn.cast[nodes.Method]

  def cdgIn: Iterator[nodes.CfgNode] = node._cdgIn.cast[nodes.CfgNode]

  def cfgIn: Iterator[nodes.Return] = node._cfgIn.cast[nodes.Return]

  def dominateIn: Iterator[nodes.CfgNode] = node._dominateIn.cast[nodes.CfgNode]

  def evalTypeOut: Iterator[nodes.Type] = node._evalTypeOut.cast[nodes.Type]

  def postDominateOut: Iterator[nodes.CfgNode] = node._postDominateOut.cast[nodes.CfgNode]

  def reachingDefIn: Iterator[nodes.Return] = node._reachingDefIn.cast[nodes.Return]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]
}

final class AccessNeighborsForMethodReturnTraversal(val traversal: Iterator[nodes.MethodReturn]) extends AnyVal {

  /** Traverse to BLOCK via CDG IN edge.
    */
  def blockViaCdgIn: Iterator[nodes.Block] = traversal.flatMap(_.blockViaCdgIn)

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def blockViaDominateIn: Iterator[nodes.Block] = traversal.flatMap(_.blockViaDominateIn)

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def blockViaPostDominateOut: Iterator[nodes.Block] = traversal.flatMap(_.blockViaPostDominateOut)

  /** Traverse to CALL via CDG IN edge.
    */
  def callViaCdgIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaCdgIn)

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def callViaDominateIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaDominateIn)

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def callViaPostDominateOut: Iterator[nodes.Call] = traversal.flatMap(_.callViaPostDominateOut)

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def controlStructureViaCdgIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.controlStructureViaCdgIn)

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def controlStructureViaDominateIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaDominateIn)

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def controlStructureViaPostDominateOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaPostDominateOut)

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def fieldIdentifierViaCdgIn: Iterator[nodes.FieldIdentifier] = traversal.flatMap(_.fieldIdentifierViaCdgIn)

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def fieldIdentifierViaDominateIn: Iterator[nodes.FieldIdentifier] = traversal.flatMap(_.fieldIdentifierViaDominateIn)

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def fieldIdentifierViaPostDominateOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_.fieldIdentifierViaPostDominateOut)

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def identifierViaCdgIn: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaCdgIn)

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def identifierViaDominateIn: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaDominateIn)

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def identifierViaPostDominateOut: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaPostDominateOut)

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def jumpTargetViaCdgIn: Iterator[nodes.JumpTarget] = traversal.flatMap(_.jumpTargetViaCdgIn)

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def jumpTargetViaPostDominateOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_.jumpTargetViaPostDominateOut)

  /** Traverse to LITERAL via CDG IN edge.
    */
  def literalViaCdgIn: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaCdgIn)

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def literalViaDominateIn: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaDominateIn)

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def literalViaPostDominateOut: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaPostDominateOut)

  /** Traverse to METHOD via AST IN edge.
    */
  def methodViaAstIn: Iterator[nodes.Method] = traversal.map(_.methodViaAstIn)

  /** Traverse to METHOD via DOMINATE IN edge.
    */
  def methodViaDominateIn: Iterator[nodes.Method] = traversal.flatMap(_.methodViaDominateIn)

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def methodViaPostDominateOut: Iterator[nodes.Method] = traversal.flatMap(_.methodViaPostDominateOut)

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def methodRefViaCdgIn: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaCdgIn)

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def methodRefViaDominateIn: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaDominateIn)

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def methodRefViaPostDominateOut: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaPostDominateOut)

  /** Traverse to RETURN via CFG IN edge.
    */
  def toReturn: Iterator[nodes.Return] = traversal.flatMap(_.toReturn)

  /** Traverse to RETURN via CFG IN edge.
    */
  @deprecated("please use toReturn instead")
  def returnViaCfgIn: Iterator[nodes.Return] = traversal.flatMap(_.returnViaCfgIn)

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def returnViaDominateIn: Iterator[nodes.Return] = traversal.flatMap(_.returnViaDominateIn)

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def returnViaPostDominateOut: Iterator[nodes.Return] = traversal.flatMap(_.returnViaPostDominateOut)

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def returnViaReachingDefIn: Iterator[nodes.Return] = traversal.flatMap(_.returnViaReachingDefIn)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.tagViaTaggedByOut)

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def typeViaEvalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_.typeViaEvalTypeOut)

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def typeRefViaCdgIn: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaCdgIn)

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def typeRefViaDominateIn: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaDominateIn)

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def typeRefViaPostDominateOut: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaPostDominateOut)

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def unknownViaCdgIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaCdgIn)

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def unknownViaDominateIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaDominateIn)

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def unknownViaPostDominateOut: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaPostDominateOut)

  def astIn: Iterator[nodes.Method] = traversal.flatMap(_.astIn)

  def cdgIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.cdgIn)

  def cfgIn: Iterator[nodes.Return] = traversal.flatMap(_.cfgIn)

  def dominateIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.dominateIn)

  def evalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_.evalTypeOut)

  def postDominateOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.postDominateOut)

  def reachingDefIn: Iterator[nodes.Return] = traversal.flatMap(_.reachingDefIn)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)
}
