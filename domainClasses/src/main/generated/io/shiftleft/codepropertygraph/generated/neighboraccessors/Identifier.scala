package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForIdentifier(val node: nodes.Identifier) extends AnyVal {

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def annotationViaAstOut: Iterator[nodes.Annotation] = astOut.collectAll[nodes.Annotation]

  /** Traverse to BLOCK via AST IN edge.
    */
  def blockViaAstIn: Iterator[nodes.Block] = astIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via CDG IN edge.
    */
  def blockViaCdgIn: Iterator[nodes.Block] = cdgIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via CDG OUT edge.
    */
  def blockViaCdgOut: Iterator[nodes.Block] = cdgOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def blockViaDominateIn: Iterator[nodes.Block] = dominateIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def blockViaDominateOut: Iterator[nodes.Block] = dominateOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def blockViaPostDominateIn: Iterator[nodes.Block] = postDominateIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def blockViaPostDominateOut: Iterator[nodes.Block] = postDominateOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via REACHING_DEF IN edge.
    */
  def blockViaReachingDefIn: Iterator[nodes.Block] = reachingDefIn.collectAll[nodes.Block]

  /** Traverse to CALL via ARGUMENT IN edge.
    */
  def callViaArgumentIn: Option[nodes.Call] = argumentIn.collectAll[nodes.Call].nextOption()

  /** Traverse to CALL via AST IN edge.
    */
  def callViaAstIn: Iterator[nodes.Call] = astIn.collectAll[nodes.Call]

  /** Traverse to CALL via CDG IN edge.
    */
  def callViaCdgIn: Iterator[nodes.Call] = cdgIn.collectAll[nodes.Call]

  /** Traverse to CALL via CDG OUT edge.
    */
  def callViaCdgOut: Iterator[nodes.Call] = cdgOut.collectAll[nodes.Call]

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def callViaDominateIn: Iterator[nodes.Call] = dominateIn.collectAll[nodes.Call]

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def callViaDominateOut: Iterator[nodes.Call] = dominateOut.collectAll[nodes.Call]

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def callViaPostDominateIn: Iterator[nodes.Call] = postDominateIn.collectAll[nodes.Call]

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def callViaPostDominateOut: Iterator[nodes.Call] = postDominateOut.collectAll[nodes.Call]

  /** Traverse to CALL via REACHING_DEF IN edge.
    */
  def callViaReachingDefIn: Iterator[nodes.Call] = reachingDefIn.collectAll[nodes.Call]

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def callViaReachingDefOut: Iterator[nodes.Call] = reachingDefOut.collectAll[nodes.Call]

  /** Traverse to CALL via RECEIVER IN edge.
    */
  def callViaReceiverIn: Option[nodes.Call] = receiverIn.collectAll[nodes.Call].nextOption()

  /** Traverse to CFG_NODE via CFG OUT edge.
    */
  def cfgNodeViaCfgOut: Iterator[nodes.CfgNode] = cfgOut.collectAll[nodes.CfgNode]

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def controlStructureViaAstIn: Option[nodes.ControlStructure] = astIn.collectAll[nodes.ControlStructure].nextOption()

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def controlStructureViaCdgIn: Iterator[nodes.ControlStructure] = cdgIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via CDG OUT edge.
    */
  def controlStructureViaCdgOut: Iterator[nodes.ControlStructure] = cdgOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via CONDITION IN edge.
    */
  def controlStructureViaConditionIn: Iterator[nodes.ControlStructure] = conditionIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def controlStructureViaDominateIn: Iterator[nodes.ControlStructure] = dominateIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via DOMINATE OUT edge.
    */
  def controlStructureViaDominateOut: Iterator[nodes.ControlStructure] = dominateOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def controlStructureViaPostDominateIn: Iterator[nodes.ControlStructure] =
    postDominateIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def controlStructureViaPostDominateOut: Iterator[nodes.ControlStructure] =
    postDominateOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via REACHING_DEF IN edge.
    */
  def controlStructureViaReachingDefIn: Iterator[nodes.ControlStructure] =
    reachingDefIn.collectAll[nodes.ControlStructure]

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def fieldIdentifierViaCdgIn: Iterator[nodes.FieldIdentifier] = cdgIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via CDG OUT edge.
    */
  def fieldIdentifierViaCdgOut: Iterator[nodes.FieldIdentifier] = cdgOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def fieldIdentifierViaDominateIn: Iterator[nodes.FieldIdentifier] = dominateIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def fieldIdentifierViaDominateOut: Iterator[nodes.FieldIdentifier] = dominateOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def fieldIdentifierViaPostDominateIn: Iterator[nodes.FieldIdentifier] =
    postDominateIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def fieldIdentifierViaPostDominateOut: Iterator[nodes.FieldIdentifier] =
    postDominateOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def identifierViaCdgIn: Iterator[nodes.Identifier] = cdgIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via CDG OUT edge.
    */
  def identifierViaCdgOut: Iterator[nodes.Identifier] = cdgOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def identifierViaDominateIn: Iterator[nodes.Identifier] = dominateIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def identifierViaDominateOut: Iterator[nodes.Identifier] = dominateOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def identifierViaPostDominateIn: Iterator[nodes.Identifier] = postDominateIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def identifierViaPostDominateOut: Iterator[nodes.Identifier] = postDominateOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via REACHING_DEF IN edge.
    */
  def identifierViaReachingDefIn: Iterator[nodes.Identifier] = reachingDefIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def identifierViaReachingDefOut: Iterator[nodes.Identifier] = reachingDefOut.collectAll[nodes.Identifier]

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def jumpTargetViaCdgIn: Iterator[nodes.JumpTarget] = cdgIn.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via CDG OUT edge.
    */
  def jumpTargetViaCdgOut: Iterator[nodes.JumpTarget] = cdgOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via DOMINATE IN edge.
    */
  def jumpTargetViaDominateIn: Iterator[nodes.JumpTarget] = dominateIn.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via DOMINATE OUT edge.
    */
  def jumpTargetViaDominateOut: Iterator[nodes.JumpTarget] = dominateOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via POST_DOMINATE IN edge.
    */
  def jumpTargetViaPostDominateIn: Iterator[nodes.JumpTarget] = postDominateIn.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def jumpTargetViaPostDominateOut: Iterator[nodes.JumpTarget] = postDominateOut.collectAll[nodes.JumpTarget]

  /** Traverse to LITERAL via CDG IN edge.
    */
  def literalViaCdgIn: Iterator[nodes.Literal] = cdgIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via CDG OUT edge.
    */
  def literalViaCdgOut: Iterator[nodes.Literal] = cdgOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def literalViaDominateIn: Iterator[nodes.Literal] = dominateIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def literalViaDominateOut: Iterator[nodes.Literal] = dominateOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def literalViaPostDominateIn: Iterator[nodes.Literal] = postDominateIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def literalViaPostDominateOut: Iterator[nodes.Literal] = postDominateOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via REACHING_DEF IN edge.
    */
  def literalViaReachingDefIn: Iterator[nodes.Literal] = reachingDefIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def literalViaReachingDefOut: Iterator[nodes.Literal] = reachingDefOut.collectAll[nodes.Literal]

  /** Traverse to LOCAL via REF OUT edge.
    */
  def localViaRefOut: Option[nodes.Local] = refOut.collectAll[nodes.Local].nextOption()

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def methodViaContainsIn: Iterator[nodes.Method] = containsIn.collectAll[nodes.Method]

  /** Traverse to METHOD via DOMINATE IN edge.
    */
  def methodViaDominateIn: Iterator[nodes.Method] = dominateIn.collectAll[nodes.Method]

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def methodViaPostDominateOut: Iterator[nodes.Method] = postDominateOut.collectAll[nodes.Method]

  /** Traverse to METHOD via REACHING_DEF IN edge.
    */
  def methodViaReachingDefIn: Iterator[nodes.Method] = reachingDefIn.collectAll[nodes.Method]

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF IN edge.
    */
  def methodParameterInViaReachingDefIn: Iterator[nodes.MethodParameterIn] =
    reachingDefIn.collectAll[nodes.MethodParameterIn]

  /** Traverse to METHOD_PARAMETER_IN via REF OUT edge.
    */
  def methodParameterInViaRefOut: Option[nodes.MethodParameterIn] =
    refOut.collectAll[nodes.MethodParameterIn].nextOption()

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF IN edge.
    */
  def methodParameterOutViaReachingDefIn: Iterator[nodes.MethodParameterOut] =
    reachingDefIn.collectAll[nodes.MethodParameterOut]

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def methodParameterOutViaReachingDefOut: Iterator[nodes.MethodParameterOut] =
    reachingDefOut.collectAll[nodes.MethodParameterOut]

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def methodRefViaCdgIn: Iterator[nodes.MethodRef] = cdgIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via CDG OUT edge.
    */
  def methodRefViaCdgOut: Iterator[nodes.MethodRef] = cdgOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def methodRefViaDominateIn: Iterator[nodes.MethodRef] = dominateIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def methodRefViaDominateOut: Iterator[nodes.MethodRef] = dominateOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def methodRefViaPostDominateIn: Iterator[nodes.MethodRef] = postDominateIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def methodRefViaPostDominateOut: Iterator[nodes.MethodRef] = postDominateOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via REACHING_DEF IN edge.
    */
  def methodRefViaReachingDefIn: Iterator[nodes.MethodRef] = reachingDefIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def methodRefViaReachingDefOut: Iterator[nodes.MethodRef] = reachingDefOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_RETURN via CDG OUT edge.
    */
  def methodReturnViaCdgOut: Iterator[nodes.MethodReturn] = cdgOut.collectAll[nodes.MethodReturn]

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def methodReturnViaDominateOut: Iterator[nodes.MethodReturn] = dominateOut.collectAll[nodes.MethodReturn]

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def methodReturnViaPostDominateIn: Iterator[nodes.MethodReturn] = postDominateIn.collectAll[nodes.MethodReturn]

  /** Traverse to RETURN via ARGUMENT IN edge.
    */
  def returnViaArgumentIn: Option[nodes.Return] = argumentIn.collectAll[nodes.Return].nextOption()

  /** Traverse to RETURN via AST IN edge.
    */
  def returnViaAstIn: Iterator[nodes.Return] = astIn.collectAll[nodes.Return]

  /** Traverse to RETURN via CDG OUT edge.
    */
  def returnViaCdgOut: Iterator[nodes.Return] = cdgOut.collectAll[nodes.Return]

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def returnViaDominateIn: Iterator[nodes.Return] = dominateIn.collectAll[nodes.Return]

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def returnViaDominateOut: Iterator[nodes.Return] = dominateOut.collectAll[nodes.Return]

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def returnViaPostDominateIn: Iterator[nodes.Return] = postDominateIn.collectAll[nodes.Return]

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def returnViaPostDominateOut: Iterator[nodes.Return] = postDominateOut.collectAll[nodes.Return]

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def returnViaReachingDefIn: Iterator[nodes.Return] = reachingDefIn.collectAll[nodes.Return]

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def returnViaReachingDefOut: Iterator[nodes.Return] = reachingDefOut.collectAll[nodes.Return]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def typeViaEvalTypeOut: Iterator[nodes.Type] = evalTypeOut.collectAll[nodes.Type]

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def typeRefViaCdgIn: Iterator[nodes.TypeRef] = cdgIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via CDG OUT edge.
    */
  def typeRefViaCdgOut: Iterator[nodes.TypeRef] = cdgOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def typeRefViaDominateIn: Iterator[nodes.TypeRef] = dominateIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def typeRefViaDominateOut: Iterator[nodes.TypeRef] = dominateOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def typeRefViaPostDominateIn: Iterator[nodes.TypeRef] = postDominateIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def typeRefViaPostDominateOut: Iterator[nodes.TypeRef] = postDominateOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via REACHING_DEF IN edge.
    */
  def typeRefViaReachingDefIn: Iterator[nodes.TypeRef] = reachingDefIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def typeRefViaReachingDefOut: Iterator[nodes.TypeRef] = reachingDefOut.collectAll[nodes.TypeRef]

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def unknownViaAstIn: Iterator[nodes.Unknown] = astIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def unknownViaCdgIn: Iterator[nodes.Unknown] = cdgIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via CDG OUT edge.
    */
  def unknownViaCdgOut: Iterator[nodes.Unknown] = cdgOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def unknownViaDominateIn: Iterator[nodes.Unknown] = dominateIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def unknownViaDominateOut: Iterator[nodes.Unknown] = dominateOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def unknownViaPostDominateIn: Iterator[nodes.Unknown] = postDominateIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def unknownViaPostDominateOut: Iterator[nodes.Unknown] = postDominateOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via REACHING_DEF IN edge.
    */
  def unknownViaReachingDefIn: Iterator[nodes.Unknown] = reachingDefIn.collectAll[nodes.Unknown]

  def argumentIn: Iterator[nodes.Expression] = node._argumentIn.cast[nodes.Expression]

  def astIn: Iterator[nodes.Expression] = node._astIn.cast[nodes.Expression]

  def astOut: Iterator[nodes.Annotation] = node._astOut.cast[nodes.Annotation]

  def cdgIn: Iterator[nodes.CfgNode] = node._cdgIn.cast[nodes.CfgNode]

  def cdgOut: Iterator[nodes.CfgNode] = node._cdgOut.cast[nodes.CfgNode]

  def cfgOut: Iterator[nodes.CfgNode] = node._cfgOut.cast[nodes.CfgNode]

  def conditionIn: Iterator[nodes.ControlStructure] = node._conditionIn.cast[nodes.ControlStructure]

  def containsIn: Iterator[nodes.Method] = node._containsIn.cast[nodes.Method]

  def dominateIn: Iterator[nodes.CfgNode] = node._dominateIn.cast[nodes.CfgNode]

  def dominateOut: Iterator[nodes.CfgNode] = node._dominateOut.cast[nodes.CfgNode]

  def evalTypeOut: Iterator[nodes.Type] = node._evalTypeOut.cast[nodes.Type]

  def postDominateIn: Iterator[nodes.CfgNode] = node._postDominateIn.cast[nodes.CfgNode]

  def postDominateOut: Iterator[nodes.CfgNode] = node._postDominateOut.cast[nodes.CfgNode]

  def reachingDefIn: Iterator[nodes.CfgNode] = node._reachingDefIn.cast[nodes.CfgNode]

  def reachingDefOut: Iterator[nodes.CfgNode] = node._reachingDefOut.cast[nodes.CfgNode]

  def receiverIn: Iterator[nodes.Call] = node._receiverIn.cast[nodes.Call]

  def refOut: Iterator[nodes.AstNode] = node._refOut.cast[nodes.AstNode]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]
}

final class AccessNeighborsForIdentifierTraversal(val traversal: Iterator[nodes.Identifier]) extends AnyVal {

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def annotationViaAstOut: Iterator[nodes.Annotation] = traversal.flatMap(_.annotationViaAstOut)

  /** Traverse to BLOCK via AST IN edge.
    */
  def blockViaAstIn: Iterator[nodes.Block] = traversal.flatMap(_.blockViaAstIn)

  /** Traverse to BLOCK via CDG IN edge.
    */
  def blockViaCdgIn: Iterator[nodes.Block] = traversal.flatMap(_.blockViaCdgIn)

  /** Traverse to BLOCK via CDG OUT edge.
    */
  def blockViaCdgOut: Iterator[nodes.Block] = traversal.flatMap(_.blockViaCdgOut)

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def blockViaDominateIn: Iterator[nodes.Block] = traversal.flatMap(_.blockViaDominateIn)

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def blockViaDominateOut: Iterator[nodes.Block] = traversal.flatMap(_.blockViaDominateOut)

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def blockViaPostDominateIn: Iterator[nodes.Block] = traversal.flatMap(_.blockViaPostDominateIn)

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def blockViaPostDominateOut: Iterator[nodes.Block] = traversal.flatMap(_.blockViaPostDominateOut)

  /** Traverse to BLOCK via REACHING_DEF IN edge.
    */
  def blockViaReachingDefIn: Iterator[nodes.Block] = traversal.flatMap(_.blockViaReachingDefIn)

  /** Traverse to CALL via ARGUMENT IN edge.
    */
  def callViaArgumentIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaArgumentIn)

  /** Traverse to CALL via AST IN edge.
    */
  def callViaAstIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaAstIn)

  /** Traverse to CALL via CDG IN edge.
    */
  def callViaCdgIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaCdgIn)

  /** Traverse to CALL via CDG OUT edge.
    */
  def callViaCdgOut: Iterator[nodes.Call] = traversal.flatMap(_.callViaCdgOut)

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def callViaDominateIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaDominateIn)

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def callViaDominateOut: Iterator[nodes.Call] = traversal.flatMap(_.callViaDominateOut)

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def callViaPostDominateIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaPostDominateIn)

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def callViaPostDominateOut: Iterator[nodes.Call] = traversal.flatMap(_.callViaPostDominateOut)

  /** Traverse to CALL via REACHING_DEF IN edge.
    */
  def callViaReachingDefIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaReachingDefIn)

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def callViaReachingDefOut: Iterator[nodes.Call] = traversal.flatMap(_.callViaReachingDefOut)

  /** Traverse to CALL via RECEIVER IN edge.
    */
  def callViaReceiverIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaReceiverIn)

  /** Traverse to CFG_NODE via CFG OUT edge.
    */
  def cfgNodeViaCfgOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.cfgNodeViaCfgOut)

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def controlStructureViaAstIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.controlStructureViaAstIn)

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def controlStructureViaCdgIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.controlStructureViaCdgIn)

  /** Traverse to CONTROL_STRUCTURE via CDG OUT edge.
    */
  def controlStructureViaCdgOut: Iterator[nodes.ControlStructure] = traversal.flatMap(_.controlStructureViaCdgOut)

  /** Traverse to CONTROL_STRUCTURE via CONDITION IN edge.
    */
  def controlStructureViaConditionIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaConditionIn)

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def controlStructureViaDominateIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaDominateIn)

  /** Traverse to CONTROL_STRUCTURE via DOMINATE OUT edge.
    */
  def controlStructureViaDominateOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaDominateOut)

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def controlStructureViaPostDominateIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaPostDominateIn)

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def controlStructureViaPostDominateOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaPostDominateOut)

  /** Traverse to CONTROL_STRUCTURE via REACHING_DEF IN edge.
    */
  def controlStructureViaReachingDefIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaReachingDefIn)

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def fieldIdentifierViaCdgIn: Iterator[nodes.FieldIdentifier] = traversal.flatMap(_.fieldIdentifierViaCdgIn)

  /** Traverse to FIELD_IDENTIFIER via CDG OUT edge.
    */
  def fieldIdentifierViaCdgOut: Iterator[nodes.FieldIdentifier] = traversal.flatMap(_.fieldIdentifierViaCdgOut)

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def fieldIdentifierViaDominateIn: Iterator[nodes.FieldIdentifier] = traversal.flatMap(_.fieldIdentifierViaDominateIn)

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def fieldIdentifierViaDominateOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_.fieldIdentifierViaDominateOut)

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def fieldIdentifierViaPostDominateIn: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_.fieldIdentifierViaPostDominateIn)

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def fieldIdentifierViaPostDominateOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_.fieldIdentifierViaPostDominateOut)

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def identifierViaCdgIn: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaCdgIn)

  /** Traverse to IDENTIFIER via CDG OUT edge.
    */
  def identifierViaCdgOut: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaCdgOut)

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def identifierViaDominateIn: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaDominateIn)

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def identifierViaDominateOut: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaDominateOut)

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def identifierViaPostDominateIn: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaPostDominateIn)

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def identifierViaPostDominateOut: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaPostDominateOut)

  /** Traverse to IDENTIFIER via REACHING_DEF IN edge.
    */
  def identifierViaReachingDefIn: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaReachingDefIn)

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def identifierViaReachingDefOut: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaReachingDefOut)

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def jumpTargetViaCdgIn: Iterator[nodes.JumpTarget] = traversal.flatMap(_.jumpTargetViaCdgIn)

  /** Traverse to JUMP_TARGET via CDG OUT edge.
    */
  def jumpTargetViaCdgOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_.jumpTargetViaCdgOut)

  /** Traverse to JUMP_TARGET via DOMINATE IN edge.
    */
  def jumpTargetViaDominateIn: Iterator[nodes.JumpTarget] = traversal.flatMap(_.jumpTargetViaDominateIn)

  /** Traverse to JUMP_TARGET via DOMINATE OUT edge.
    */
  def jumpTargetViaDominateOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_.jumpTargetViaDominateOut)

  /** Traverse to JUMP_TARGET via POST_DOMINATE IN edge.
    */
  def jumpTargetViaPostDominateIn: Iterator[nodes.JumpTarget] = traversal.flatMap(_.jumpTargetViaPostDominateIn)

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def jumpTargetViaPostDominateOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_.jumpTargetViaPostDominateOut)

  /** Traverse to LITERAL via CDG IN edge.
    */
  def literalViaCdgIn: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaCdgIn)

  /** Traverse to LITERAL via CDG OUT edge.
    */
  def literalViaCdgOut: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaCdgOut)

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def literalViaDominateIn: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaDominateIn)

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def literalViaDominateOut: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaDominateOut)

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def literalViaPostDominateIn: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaPostDominateIn)

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def literalViaPostDominateOut: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaPostDominateOut)

  /** Traverse to LITERAL via REACHING_DEF IN edge.
    */
  def literalViaReachingDefIn: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaReachingDefIn)

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def literalViaReachingDefOut: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaReachingDefOut)

  /** Traverse to LOCAL via REF OUT edge.
    */
  def localViaRefOut: Iterator[nodes.Local] = traversal.flatMap(_.localViaRefOut)

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def methodViaContainsIn: Iterator[nodes.Method] = traversal.flatMap(_.methodViaContainsIn)

  /** Traverse to METHOD via DOMINATE IN edge.
    */
  def methodViaDominateIn: Iterator[nodes.Method] = traversal.flatMap(_.methodViaDominateIn)

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def methodViaPostDominateOut: Iterator[nodes.Method] = traversal.flatMap(_.methodViaPostDominateOut)

  /** Traverse to METHOD via REACHING_DEF IN edge.
    */
  def methodViaReachingDefIn: Iterator[nodes.Method] = traversal.flatMap(_.methodViaReachingDefIn)

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF IN edge.
    */
  def methodParameterInViaReachingDefIn: Iterator[nodes.MethodParameterIn] =
    traversal.flatMap(_.methodParameterInViaReachingDefIn)

  /** Traverse to METHOD_PARAMETER_IN via REF OUT edge.
    */
  def methodParameterInViaRefOut: Iterator[nodes.MethodParameterIn] = traversal.flatMap(_.methodParameterInViaRefOut)

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF IN edge.
    */
  def methodParameterOutViaReachingDefIn: Iterator[nodes.MethodParameterOut] =
    traversal.flatMap(_.methodParameterOutViaReachingDefIn)

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def methodParameterOutViaReachingDefOut: Iterator[nodes.MethodParameterOut] =
    traversal.flatMap(_.methodParameterOutViaReachingDefOut)

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def methodRefViaCdgIn: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaCdgIn)

  /** Traverse to METHOD_REF via CDG OUT edge.
    */
  def methodRefViaCdgOut: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaCdgOut)

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def methodRefViaDominateIn: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaDominateIn)

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def methodRefViaDominateOut: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaDominateOut)

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def methodRefViaPostDominateIn: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaPostDominateIn)

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def methodRefViaPostDominateOut: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaPostDominateOut)

  /** Traverse to METHOD_REF via REACHING_DEF IN edge.
    */
  def methodRefViaReachingDefIn: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaReachingDefIn)

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def methodRefViaReachingDefOut: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaReachingDefOut)

  /** Traverse to METHOD_RETURN via CDG OUT edge.
    */
  def methodReturnViaCdgOut: Iterator[nodes.MethodReturn] = traversal.flatMap(_.methodReturnViaCdgOut)

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def methodReturnViaDominateOut: Iterator[nodes.MethodReturn] = traversal.flatMap(_.methodReturnViaDominateOut)

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def methodReturnViaPostDominateIn: Iterator[nodes.MethodReturn] = traversal.flatMap(_.methodReturnViaPostDominateIn)

  /** Traverse to RETURN via ARGUMENT IN edge.
    */
  def returnViaArgumentIn: Iterator[nodes.Return] = traversal.flatMap(_.returnViaArgumentIn)

  /** Traverse to RETURN via AST IN edge.
    */
  def returnViaAstIn: Iterator[nodes.Return] = traversal.flatMap(_.returnViaAstIn)

  /** Traverse to RETURN via CDG OUT edge.
    */
  def returnViaCdgOut: Iterator[nodes.Return] = traversal.flatMap(_.returnViaCdgOut)

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def returnViaDominateIn: Iterator[nodes.Return] = traversal.flatMap(_.returnViaDominateIn)

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def returnViaDominateOut: Iterator[nodes.Return] = traversal.flatMap(_.returnViaDominateOut)

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def returnViaPostDominateIn: Iterator[nodes.Return] = traversal.flatMap(_.returnViaPostDominateIn)

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def returnViaPostDominateOut: Iterator[nodes.Return] = traversal.flatMap(_.returnViaPostDominateOut)

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def returnViaReachingDefIn: Iterator[nodes.Return] = traversal.flatMap(_.returnViaReachingDefIn)

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def returnViaReachingDefOut: Iterator[nodes.Return] = traversal.flatMap(_.returnViaReachingDefOut)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.tagViaTaggedByOut)

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def typeViaEvalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_.typeViaEvalTypeOut)

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def typeRefViaCdgIn: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaCdgIn)

  /** Traverse to TYPE_REF via CDG OUT edge.
    */
  def typeRefViaCdgOut: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaCdgOut)

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def typeRefViaDominateIn: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaDominateIn)

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def typeRefViaDominateOut: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaDominateOut)

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def typeRefViaPostDominateIn: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaPostDominateIn)

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def typeRefViaPostDominateOut: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaPostDominateOut)

  /** Traverse to TYPE_REF via REACHING_DEF IN edge.
    */
  def typeRefViaReachingDefIn: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaReachingDefIn)

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def typeRefViaReachingDefOut: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaReachingDefOut)

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def unknownViaAstIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaAstIn)

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def unknownViaCdgIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaCdgIn)

  /** Traverse to UNKNOWN via CDG OUT edge.
    */
  def unknownViaCdgOut: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaCdgOut)

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def unknownViaDominateIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaDominateIn)

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def unknownViaDominateOut: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaDominateOut)

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def unknownViaPostDominateIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaPostDominateIn)

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def unknownViaPostDominateOut: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaPostDominateOut)

  /** Traverse to UNKNOWN via REACHING_DEF IN edge.
    */
  def unknownViaReachingDefIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaReachingDefIn)

  def argumentIn: Iterator[nodes.Expression] = traversal.flatMap(_.argumentIn)

  def astIn: Iterator[nodes.Expression] = traversal.flatMap(_.astIn)

  def astOut: Iterator[nodes.Annotation] = traversal.flatMap(_.astOut)

  def cdgIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.cdgIn)

  def cdgOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.cdgOut)

  def cfgOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.cfgOut)

  def conditionIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.conditionIn)

  def containsIn: Iterator[nodes.Method] = traversal.flatMap(_.containsIn)

  def dominateIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.dominateIn)

  def dominateOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.dominateOut)

  def evalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_.evalTypeOut)

  def postDominateIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.postDominateIn)

  def postDominateOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.postDominateOut)

  def reachingDefIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.reachingDefIn)

  def reachingDefOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.reachingDefOut)

  def receiverIn: Iterator[nodes.Call] = traversal.flatMap(_.receiverIn)

  def refOut: Iterator[nodes.AstNode] = traversal.flatMap(_.refOut)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)
}
