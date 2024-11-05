package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForMethodReturn(val node: nodes.MethodReturn) extends AnyVal {

  /** Traverse to BLOCK via CDG IN edge.
    */
  def _blockViaCdgIn: Iterator[nodes.Block] = cdgIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def _blockViaDominateIn: Iterator[nodes.Block] = dominateIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def _blockViaPostDominateOut: Iterator[nodes.Block] = postDominateOut.collectAll[nodes.Block]

  /** Traverse to CALL via CDG IN edge.
    */
  def _callViaCdgIn: Iterator[nodes.Call] = cdgIn.collectAll[nodes.Call]

  /** Traverse to CALL via CFG IN edge.
    */
  def _callViaCfgIn: Iterator[nodes.Call] = cfgIn.collectAll[nodes.Call]

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def _callViaDominateIn: Iterator[nodes.Call] = dominateIn.collectAll[nodes.Call]

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def _callViaPostDominateOut: Iterator[nodes.Call] = postDominateOut.collectAll[nodes.Call]

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def _controlStructureViaCdgIn: Iterator[nodes.ControlStructure] = cdgIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def _controlStructureViaDominateIn: Iterator[nodes.ControlStructure] = dominateIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def _controlStructureViaPostDominateOut: Iterator[nodes.ControlStructure] =
    postDominateOut.collectAll[nodes.ControlStructure]

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def _fieldIdentifierViaCdgIn: Iterator[nodes.FieldIdentifier] = cdgIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def _fieldIdentifierViaDominateIn: Iterator[nodes.FieldIdentifier] = dominateIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _fieldIdentifierViaPostDominateOut: Iterator[nodes.FieldIdentifier] =
    postDominateOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def _identifierViaCdgIn: Iterator[nodes.Identifier] = cdgIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def _identifierViaDominateIn: Iterator[nodes.Identifier] = dominateIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _identifierViaPostDominateOut: Iterator[nodes.Identifier] = postDominateOut.collectAll[nodes.Identifier]

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def _jumpTargetViaCdgIn: Iterator[nodes.JumpTarget] = cdgIn.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def _jumpTargetViaPostDominateOut: Iterator[nodes.JumpTarget] = postDominateOut.collectAll[nodes.JumpTarget]

  /** Traverse to LITERAL via CDG IN edge.
    */
  def _literalViaCdgIn: Iterator[nodes.Literal] = cdgIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def _literalViaDominateIn: Iterator[nodes.Literal] = dominateIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def _literalViaPostDominateOut: Iterator[nodes.Literal] = postDominateOut.collectAll[nodes.Literal]

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: nodes.Method = {
    try { astIn.collectAll[nodes.Method].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        val nodeInfo = String.format("id=%d, (seq=%d)", node.id, node.seq)
        throw new flatgraph.SchemaViolationException(
          "IN edge with label AST to an adjacent METHOD is mandatory, but not defined for this METHOD_RETURN node with " + nodeInfo,
          e
        )
    }
  }

  /** Traverse to METHOD via CFG IN edge.
    */
  def _methodViaCfgIn: Option[nodes.Method] = cfgIn.collectAll[nodes.Method].nextOption()

  /** Traverse to METHOD via DOMINATE IN edge.
    */
  def _methodViaDominateIn: Iterator[nodes.Method] = dominateIn.collectAll[nodes.Method]

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def _methodViaPostDominateOut: Iterator[nodes.Method] = postDominateOut.collectAll[nodes.Method]

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def _methodRefViaCdgIn: Iterator[nodes.MethodRef] = cdgIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via CFG IN edge.
    */
  def _methodRefViaCfgIn: Iterator[nodes.MethodRef] = cfgIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def _methodRefViaDominateIn: Iterator[nodes.MethodRef] = dominateIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def _methodRefViaPostDominateOut: Iterator[nodes.MethodRef] = postDominateOut.collectAll[nodes.MethodRef]

  /** Traverse to RETURN via CFG IN edge.
    */
  @deprecated("please use toReturn instead")
  def _returnViaCfgIn: Iterator[nodes.Return] = toReturn

  /** Traverse to RETURN via CFG IN edge.
    */
  def toReturn: Iterator[nodes.Return] = cfgIn.collectAll[nodes.Return]

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def _returnViaDominateIn: Iterator[nodes.Return] = dominateIn.collectAll[nodes.Return]

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def _returnViaPostDominateOut: Iterator[nodes.Return] = postDominateOut.collectAll[nodes.Return]

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def _returnViaReachingDefIn: Iterator[nodes.Return] = reachingDefIn.collectAll[nodes.Return]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def _typeViaEvalTypeOut: Iterator[nodes.Type] = evalTypeOut.collectAll[nodes.Type]

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def _typeRefViaCdgIn: Iterator[nodes.TypeRef] = cdgIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via CFG IN edge.
    */
  def _typeRefViaCfgIn: Iterator[nodes.TypeRef] = cfgIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def _typeRefViaDominateIn: Iterator[nodes.TypeRef] = dominateIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def _typeRefViaPostDominateOut: Iterator[nodes.TypeRef] = postDominateOut.collectAll[nodes.TypeRef]

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def _unknownViaCdgIn: Iterator[nodes.Unknown] = cdgIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def _unknownViaDominateIn: Iterator[nodes.Unknown] = dominateIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def _unknownViaPostDominateOut: Iterator[nodes.Unknown] = postDominateOut.collectAll[nodes.Unknown]

  def astIn: Iterator[nodes.Method] = node._astIn.cast[nodes.Method]

  def cdgIn: Iterator[nodes.CfgNode] = node._cdgIn.cast[nodes.CfgNode]

  def cfgIn: Iterator[nodes.CfgNode] = node._cfgIn.cast[nodes.CfgNode]

  def dominateIn: Iterator[nodes.CfgNode] = node._dominateIn.cast[nodes.CfgNode]

  def evalTypeOut: Iterator[nodes.Type] = node._evalTypeOut.cast[nodes.Type]

  def postDominateOut: Iterator[nodes.CfgNode] = node._postDominateOut.cast[nodes.CfgNode]

  def reachingDefIn: Iterator[nodes.Return] = node._reachingDefIn.cast[nodes.Return]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]
}

final class AccessNeighborsForMethodReturnTraversal(val traversal: Iterator[nodes.MethodReturn]) extends AnyVal {

  /** Traverse to BLOCK via CDG IN edge.
    */
  def _blockViaCdgIn: Iterator[nodes.Block] = traversal.flatMap(_._blockViaCdgIn)

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def _blockViaDominateIn: Iterator[nodes.Block] = traversal.flatMap(_._blockViaDominateIn)

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def _blockViaPostDominateOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaPostDominateOut)

  /** Traverse to CALL via CDG IN edge.
    */
  def _callViaCdgIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaCdgIn)

  /** Traverse to CALL via CFG IN edge.
    */
  def _callViaCfgIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaCfgIn)

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def _callViaDominateIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaDominateIn)

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def _callViaPostDominateOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaPostDominateOut)

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def _controlStructureViaCdgIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaCdgIn)

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def _controlStructureViaDominateIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaDominateIn)

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def _controlStructureViaPostDominateOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaPostDominateOut)

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def _fieldIdentifierViaCdgIn: Iterator[nodes.FieldIdentifier] = traversal.flatMap(_._fieldIdentifierViaCdgIn)

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def _fieldIdentifierViaDominateIn: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_._fieldIdentifierViaDominateIn)

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _fieldIdentifierViaPostDominateOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_._fieldIdentifierViaPostDominateOut)

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def _identifierViaCdgIn: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaCdgIn)

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def _identifierViaDominateIn: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaDominateIn)

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _identifierViaPostDominateOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaPostDominateOut)

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def _jumpTargetViaCdgIn: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaCdgIn)

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def _jumpTargetViaPostDominateOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaPostDominateOut)

  /** Traverse to LITERAL via CDG IN edge.
    */
  def _literalViaCdgIn: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaCdgIn)

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def _literalViaDominateIn: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaDominateIn)

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def _literalViaPostDominateOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaPostDominateOut)

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Iterator[nodes.Method] = traversal.map(_._methodViaAstIn)

  /** Traverse to METHOD via CFG IN edge.
    */
  def _methodViaCfgIn: Iterator[nodes.Method] = traversal.flatMap(_._methodViaCfgIn)

  /** Traverse to METHOD via DOMINATE IN edge.
    */
  def _methodViaDominateIn: Iterator[nodes.Method] = traversal.flatMap(_._methodViaDominateIn)

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def _methodViaPostDominateOut: Iterator[nodes.Method] = traversal.flatMap(_._methodViaPostDominateOut)

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def _methodRefViaCdgIn: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaCdgIn)

  /** Traverse to METHOD_REF via CFG IN edge.
    */
  def _methodRefViaCfgIn: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaCfgIn)

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def _methodRefViaDominateIn: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaDominateIn)

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def _methodRefViaPostDominateOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaPostDominateOut)

  /** Traverse to RETURN via CFG IN edge.
    */
  def toReturn: Iterator[nodes.Return] = traversal.flatMap(_.toReturn)

  /** Traverse to RETURN via CFG IN edge.
    */
  @deprecated("please use toReturn instead")
  def _returnViaCfgIn: Iterator[nodes.Return] = traversal.flatMap(_._returnViaCfgIn)

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def _returnViaDominateIn: Iterator[nodes.Return] = traversal.flatMap(_._returnViaDominateIn)

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def _returnViaPostDominateOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaPostDominateOut)

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def _returnViaReachingDefIn: Iterator[nodes.Return] = traversal.flatMap(_._returnViaReachingDefIn)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_._tagViaTaggedByOut)

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def _typeViaEvalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_._typeViaEvalTypeOut)

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def _typeRefViaCdgIn: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaCdgIn)

  /** Traverse to TYPE_REF via CFG IN edge.
    */
  def _typeRefViaCfgIn: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaCfgIn)

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def _typeRefViaDominateIn: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaDominateIn)

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def _typeRefViaPostDominateOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaPostDominateOut)

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def _unknownViaCdgIn: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaCdgIn)

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def _unknownViaDominateIn: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaDominateIn)

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def _unknownViaPostDominateOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaPostDominateOut)

  def astIn: Iterator[nodes.Method] = traversal.flatMap(_.astIn)

  def cdgIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.cdgIn)

  def cfgIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.cfgIn)

  def dominateIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.dominateIn)

  def evalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_.evalTypeOut)

  def postDominateOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.postDominateOut)

  def reachingDefIn: Iterator[nodes.Return] = traversal.flatMap(_.reachingDefIn)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)
}
