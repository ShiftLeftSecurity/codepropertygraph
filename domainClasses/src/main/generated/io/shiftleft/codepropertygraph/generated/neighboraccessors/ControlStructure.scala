package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForControlStructure(val node: nodes.ControlStructure) extends AnyVal {

  /** Traverse to BLOCK via AST IN edge.
    */
  def _blockViaAstIn: Iterator[nodes.Block] = astIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via AST OUT edge.
    */
  def _blockViaAstOut: Iterator[nodes.Block] = astOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via CATCH_BODY OUT edge.
    */
  def _blockViaCatchBodyOut: Iterator[nodes.Block] = catchBodyOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via CDG IN edge.
    */
  def _blockViaCdgIn: Iterator[nodes.Block] = cdgIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via CDG OUT edge.
    */
  def _blockViaCdgOut: Iterator[nodes.Block] = cdgOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via CONDITION OUT edge.
    */
  def _blockViaConditionOut: Iterator[nodes.Block] = conditionOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def _blockViaDominateIn: Iterator[nodes.Block] = dominateIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def _blockViaDominateOut: Iterator[nodes.Block] = dominateOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via DO_BODY OUT edge.
    */
  def _blockViaDoBodyOut: Iterator[nodes.Block] = doBodyOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via FALSE_BODY OUT edge.
    */
  def _blockViaFalseBodyOut: Iterator[nodes.Block] = falseBodyOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via FINALLY_BODY OUT edge.
    */
  def _blockViaFinallyBodyOut: Iterator[nodes.Block] = finallyBodyOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via FOR_BODY OUT edge.
    */
  def _blockViaForBodyOut: Iterator[nodes.Block] = forBodyOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via FOR_INIT OUT edge.
    */
  def _blockViaForInitOut: Iterator[nodes.Block] = forInitOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via FOR_UPDATE OUT edge.
    */
  def _blockViaForUpdateOut: Iterator[nodes.Block] = forUpdateOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def _blockViaPostDominateIn: Iterator[nodes.Block] = postDominateIn.collectAll[nodes.Block]

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def _blockViaPostDominateOut: Iterator[nodes.Block] = postDominateOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via TRUE_BODY OUT edge.
    */
  def _blockViaTrueBodyOut: Iterator[nodes.Block] = trueBodyOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via TRY_BODY OUT edge.
    */
  def _blockViaTryBodyOut: Iterator[nodes.Block] = tryBodyOut.collectAll[nodes.Block]

  /** Traverse to CALL via ARGUMENT IN edge.
    */
  def _callViaArgumentIn: Iterator[nodes.Call] = argumentIn.collectAll[nodes.Call]

  /** Traverse to CALL via AST IN edge.
    */
  def _callViaAstIn: Iterator[nodes.Call] = astIn.collectAll[nodes.Call]

  /** Traverse to CALL via AST OUT edge.
    */
  def _callViaAstOut: Iterator[nodes.Call] = astOut.collectAll[nodes.Call]

  /** Traverse to CALL via CATCH_BODY OUT edge.
    */
  def _callViaCatchBodyOut: Iterator[nodes.Call] = catchBodyOut.collectAll[nodes.Call]

  /** Traverse to CALL via CDG IN edge.
    */
  def _callViaCdgIn: Iterator[nodes.Call] = cdgIn.collectAll[nodes.Call]

  /** Traverse to CALL via CDG OUT edge.
    */
  def _callViaCdgOut: Iterator[nodes.Call] = cdgOut.collectAll[nodes.Call]

  /** Traverse to CALL via CONDITION OUT edge.
    */
  def _callViaConditionOut: Iterator[nodes.Call] = conditionOut.collectAll[nodes.Call]

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def _callViaDominateIn: Iterator[nodes.Call] = dominateIn.collectAll[nodes.Call]

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def _callViaDominateOut: Iterator[nodes.Call] = dominateOut.collectAll[nodes.Call]

  /** Traverse to CALL via DO_BODY OUT edge.
    */
  def _callViaDoBodyOut: Iterator[nodes.Call] = doBodyOut.collectAll[nodes.Call]

  /** Traverse to CALL via FALSE_BODY OUT edge.
    */
  def _callViaFalseBodyOut: Iterator[nodes.Call] = falseBodyOut.collectAll[nodes.Call]

  /** Traverse to CALL via FINALLY_BODY OUT edge.
    */
  def _callViaFinallyBodyOut: Iterator[nodes.Call] = finallyBodyOut.collectAll[nodes.Call]

  /** Traverse to CALL via FOR_BODY OUT edge.
    */
  def _callViaForBodyOut: Iterator[nodes.Call] = forBodyOut.collectAll[nodes.Call]

  /** Traverse to CALL via FOR_INIT OUT edge.
    */
  def _callViaForInitOut: Iterator[nodes.Call] = forInitOut.collectAll[nodes.Call]

  /** Traverse to CALL via FOR_UPDATE OUT edge.
    */
  def _callViaForUpdateOut: Iterator[nodes.Call] = forUpdateOut.collectAll[nodes.Call]

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def _callViaPostDominateIn: Iterator[nodes.Call] = postDominateIn.collectAll[nodes.Call]

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def _callViaPostDominateOut: Iterator[nodes.Call] = postDominateOut.collectAll[nodes.Call]

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def _callViaReachingDefOut: Iterator[nodes.Call] = reachingDefOut.collectAll[nodes.Call]

  /** Traverse to CALL via RECEIVER IN edge.
    */
  def _callViaReceiverIn: Iterator[nodes.Call] = receiverIn.collectAll[nodes.Call]

  /** Traverse to CALL via TRUE_BODY OUT edge.
    */
  def _callViaTrueBodyOut: Iterator[nodes.Call] = trueBodyOut.collectAll[nodes.Call]

  /** Traverse to CALL via TRY_BODY OUT edge.
    */
  def _callViaTryBodyOut: Iterator[nodes.Call] = tryBodyOut.collectAll[nodes.Call]

  /** Traverse to CFG_NODE via CFG OUT edge.
    */
  def _cfgNodeViaCfgOut: Iterator[nodes.CfgNode] = cfgOut.collectAll[nodes.CfgNode]

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: Iterator[nodes.ControlStructure] = astIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via AST OUT edge.
    */
  def _controlStructureViaAstOut: Iterator[nodes.ControlStructure] = astOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via CATCH_BODY IN edge.
    */
  def _controlStructureViaCatchBodyIn: Iterator[nodes.ControlStructure] = catchBodyIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via CATCH_BODY OUT edge.
    */
  def _controlStructureViaCatchBodyOut: Iterator[nodes.ControlStructure] =
    catchBodyOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def _controlStructureViaCdgIn: Iterator[nodes.ControlStructure] = cdgIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via CDG OUT edge.
    */
  def _controlStructureViaCdgOut: Iterator[nodes.ControlStructure] = cdgOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via CONDITION IN edge.
    */
  def _controlStructureViaConditionIn: Iterator[nodes.ControlStructure] = conditionIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via CONDITION OUT edge.
    */
  def _controlStructureViaConditionOut: Iterator[nodes.ControlStructure] =
    conditionOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def _controlStructureViaDominateIn: Iterator[nodes.ControlStructure] = dominateIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via DOMINATE OUT edge.
    */
  def _controlStructureViaDominateOut: Iterator[nodes.ControlStructure] = dominateOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via DO_BODY IN edge.
    */
  def _controlStructureViaDoBodyIn: Iterator[nodes.ControlStructure] = doBodyIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via DO_BODY OUT edge.
    */
  def _controlStructureViaDoBodyOut: Iterator[nodes.ControlStructure] = doBodyOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FALSE_BODY IN edge.
    */
  def _controlStructureViaFalseBodyIn: Iterator[nodes.ControlStructure] = falseBodyIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FALSE_BODY OUT edge.
    */
  def _controlStructureViaFalseBodyOut: Iterator[nodes.ControlStructure] =
    falseBodyOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FINALLY_BODY IN edge.
    */
  def _controlStructureViaFinallyBodyIn: Iterator[nodes.ControlStructure] =
    finallyBodyIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FINALLY_BODY OUT edge.
    */
  def _controlStructureViaFinallyBodyOut: Iterator[nodes.ControlStructure] =
    finallyBodyOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FOR_BODY IN edge.
    */
  def _controlStructureViaForBodyIn: Iterator[nodes.ControlStructure] = forBodyIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FOR_BODY OUT edge.
    */
  def _controlStructureViaForBodyOut: Iterator[nodes.ControlStructure] = forBodyOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FOR_INIT IN edge.
    */
  def _controlStructureViaForInitIn: Iterator[nodes.ControlStructure] = forInitIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FOR_INIT OUT edge.
    */
  def _controlStructureViaForInitOut: Iterator[nodes.ControlStructure] = forInitOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FOR_UPDATE IN edge.
    */
  def _controlStructureViaForUpdateIn: Iterator[nodes.ControlStructure] = forUpdateIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via FOR_UPDATE OUT edge.
    */
  def _controlStructureViaForUpdateOut: Iterator[nodes.ControlStructure] =
    forUpdateOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def _controlStructureViaPostDominateIn: Iterator[nodes.ControlStructure] =
    postDominateIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def _controlStructureViaPostDominateOut: Iterator[nodes.ControlStructure] =
    postDominateOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via TRUE_BODY IN edge.
    */
  def _controlStructureViaTrueBodyIn: Iterator[nodes.ControlStructure] = trueBodyIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via TRUE_BODY OUT edge.
    */
  def _controlStructureViaTrueBodyOut: Iterator[nodes.ControlStructure] = trueBodyOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via TRY_BODY IN edge.
    */
  def _controlStructureViaTryBodyIn: Iterator[nodes.ControlStructure] = tryBodyIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via TRY_BODY OUT edge.
    */
  def _controlStructureViaTryBodyOut: Iterator[nodes.ControlStructure] = tryBodyOut.collectAll[nodes.ControlStructure]

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def _fieldIdentifierViaCdgIn: Iterator[nodes.FieldIdentifier] = cdgIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via CDG OUT edge.
    */
  def _fieldIdentifierViaCdgOut: Iterator[nodes.FieldIdentifier] = cdgOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def _fieldIdentifierViaDominateIn: Iterator[nodes.FieldIdentifier] = dominateIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def _fieldIdentifierViaDominateOut: Iterator[nodes.FieldIdentifier] = dominateOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _fieldIdentifierViaPostDominateIn: Iterator[nodes.FieldIdentifier] =
    postDominateIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _fieldIdentifierViaPostDominateOut: Iterator[nodes.FieldIdentifier] =
    postDominateOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to IDENTIFIER via AST OUT edge.
    */
  def _identifierViaAstOut: Iterator[nodes.Identifier] = astOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via CATCH_BODY OUT edge.
    */
  def _identifierViaCatchBodyOut: Iterator[nodes.Identifier] = catchBodyOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def _identifierViaCdgIn: Iterator[nodes.Identifier] = cdgIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via CDG OUT edge.
    */
  def _identifierViaCdgOut: Iterator[nodes.Identifier] = cdgOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via CONDITION OUT edge.
    */
  def _identifierViaConditionOut: Iterator[nodes.Identifier] = conditionOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def _identifierViaDominateIn: Iterator[nodes.Identifier] = dominateIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def _identifierViaDominateOut: Iterator[nodes.Identifier] = dominateOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via DO_BODY OUT edge.
    */
  def _identifierViaDoBodyOut: Iterator[nodes.Identifier] = doBodyOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via FALSE_BODY OUT edge.
    */
  def _identifierViaFalseBodyOut: Iterator[nodes.Identifier] = falseBodyOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via FINALLY_BODY OUT edge.
    */
  def _identifierViaFinallyBodyOut: Iterator[nodes.Identifier] = finallyBodyOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via FOR_BODY OUT edge.
    */
  def _identifierViaForBodyOut: Iterator[nodes.Identifier] = forBodyOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via FOR_INIT OUT edge.
    */
  def _identifierViaForInitOut: Iterator[nodes.Identifier] = forInitOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via FOR_UPDATE OUT edge.
    */
  def _identifierViaForUpdateOut: Iterator[nodes.Identifier] = forUpdateOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _identifierViaPostDominateIn: Iterator[nodes.Identifier] = postDominateIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _identifierViaPostDominateOut: Iterator[nodes.Identifier] = postDominateOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def _identifierViaReachingDefOut: Iterator[nodes.Identifier] = reachingDefOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via TRUE_BODY OUT edge.
    */
  def _identifierViaTrueBodyOut: Iterator[nodes.Identifier] = trueBodyOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via TRY_BODY OUT edge.
    */
  def _identifierViaTryBodyOut: Iterator[nodes.Identifier] = tryBodyOut.collectAll[nodes.Identifier]

  /** Traverse to JUMP_LABEL via AST OUT edge.
    */
  def _jumpLabelViaAstOut: Iterator[nodes.JumpLabel] = astOut.collectAll[nodes.JumpLabel]

  /** Traverse to JUMP_TARGET via AST OUT edge.
    */
  def _jumpTargetViaAstOut: Iterator[nodes.JumpTarget] = astOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via CATCH_BODY OUT edge.
    */
  def _jumpTargetViaCatchBodyOut: Iterator[nodes.JumpTarget] = catchBodyOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def _jumpTargetViaCdgIn: Iterator[nodes.JumpTarget] = cdgIn.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via CDG OUT edge.
    */
  def _jumpTargetViaCdgOut: Iterator[nodes.JumpTarget] = cdgOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via CONDITION OUT edge.
    */
  def _jumpTargetViaConditionOut: Iterator[nodes.JumpTarget] = conditionOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via DOMINATE IN edge.
    */
  def _jumpTargetViaDominateIn: Iterator[nodes.JumpTarget] = dominateIn.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via DOMINATE OUT edge.
    */
  def _jumpTargetViaDominateOut: Iterator[nodes.JumpTarget] = dominateOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via DO_BODY OUT edge.
    */
  def _jumpTargetViaDoBodyOut: Iterator[nodes.JumpTarget] = doBodyOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via FALSE_BODY OUT edge.
    */
  def _jumpTargetViaFalseBodyOut: Iterator[nodes.JumpTarget] = falseBodyOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via FINALLY_BODY OUT edge.
    */
  def _jumpTargetViaFinallyBodyOut: Iterator[nodes.JumpTarget] = finallyBodyOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via FOR_BODY OUT edge.
    */
  def _jumpTargetViaForBodyOut: Iterator[nodes.JumpTarget] = forBodyOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via FOR_INIT OUT edge.
    */
  def _jumpTargetViaForInitOut: Iterator[nodes.JumpTarget] = forInitOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via FOR_UPDATE OUT edge.
    */
  def _jumpTargetViaForUpdateOut: Iterator[nodes.JumpTarget] = forUpdateOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via POST_DOMINATE IN edge.
    */
  def _jumpTargetViaPostDominateIn: Iterator[nodes.JumpTarget] = postDominateIn.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def _jumpTargetViaPostDominateOut: Iterator[nodes.JumpTarget] = postDominateOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via TRUE_BODY OUT edge.
    */
  def _jumpTargetViaTrueBodyOut: Iterator[nodes.JumpTarget] = trueBodyOut.collectAll[nodes.JumpTarget]

  /** Traverse to JUMP_TARGET via TRY_BODY OUT edge.
    */
  def _jumpTargetViaTryBodyOut: Iterator[nodes.JumpTarget] = tryBodyOut.collectAll[nodes.JumpTarget]

  /** Traverse to LITERAL via AST OUT edge.
    */
  def _literalViaAstOut: Iterator[nodes.Literal] = astOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via CATCH_BODY OUT edge.
    */
  def _literalViaCatchBodyOut: Iterator[nodes.Literal] = catchBodyOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via CDG IN edge.
    */
  def _literalViaCdgIn: Iterator[nodes.Literal] = cdgIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via CDG OUT edge.
    */
  def _literalViaCdgOut: Iterator[nodes.Literal] = cdgOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via CONDITION OUT edge.
    */
  def _literalViaConditionOut: Iterator[nodes.Literal] = conditionOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def _literalViaDominateIn: Iterator[nodes.Literal] = dominateIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def _literalViaDominateOut: Iterator[nodes.Literal] = dominateOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via DO_BODY OUT edge.
    */
  def _literalViaDoBodyOut: Iterator[nodes.Literal] = doBodyOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via FALSE_BODY OUT edge.
    */
  def _literalViaFalseBodyOut: Iterator[nodes.Literal] = falseBodyOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via FINALLY_BODY OUT edge.
    */
  def _literalViaFinallyBodyOut: Iterator[nodes.Literal] = finallyBodyOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via FOR_BODY OUT edge.
    */
  def _literalViaForBodyOut: Iterator[nodes.Literal] = forBodyOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via FOR_INIT OUT edge.
    */
  def _literalViaForInitOut: Iterator[nodes.Literal] = forInitOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via FOR_UPDATE OUT edge.
    */
  def _literalViaForUpdateOut: Iterator[nodes.Literal] = forUpdateOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def _literalViaPostDominateIn: Iterator[nodes.Literal] = postDominateIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def _literalViaPostDominateOut: Iterator[nodes.Literal] = postDominateOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def _literalViaReachingDefOut: Iterator[nodes.Literal] = reachingDefOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via TRUE_BODY OUT edge.
    */
  def _literalViaTrueBodyOut: Iterator[nodes.Literal] = trueBodyOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via TRY_BODY OUT edge.
    */
  def _literalViaTryBodyOut: Iterator[nodes.Literal] = tryBodyOut.collectAll[nodes.Literal]

  /** Traverse to LOCAL via AST OUT edge.
    */
  def _localViaAstOut: Iterator[nodes.Local] = astOut.collectAll[nodes.Local]

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def _methodViaContainsIn: Iterator[nodes.Method] = containsIn.collectAll[nodes.Method]

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def _methodViaPostDominateOut: Iterator[nodes.Method] = postDominateOut.collectAll[nodes.Method]

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def _methodParameterOutViaReachingDefOut: Iterator[nodes.MethodParameterOut] =
    reachingDefOut.collectAll[nodes.MethodParameterOut]

  /** Traverse to METHOD_REF via AST OUT edge.
    */
  def _methodRefViaAstOut: Iterator[nodes.MethodRef] = astOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via CATCH_BODY OUT edge.
    */
  def _methodRefViaCatchBodyOut: Iterator[nodes.MethodRef] = catchBodyOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def _methodRefViaCdgIn: Iterator[nodes.MethodRef] = cdgIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via CDG OUT edge.
    */
  def _methodRefViaCdgOut: Iterator[nodes.MethodRef] = cdgOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via CONDITION OUT edge.
    */
  def _methodRefViaConditionOut: Iterator[nodes.MethodRef] = conditionOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def _methodRefViaDominateIn: Iterator[nodes.MethodRef] = dominateIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def _methodRefViaDominateOut: Iterator[nodes.MethodRef] = dominateOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via DO_BODY OUT edge.
    */
  def _methodRefViaDoBodyOut: Iterator[nodes.MethodRef] = doBodyOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via FALSE_BODY OUT edge.
    */
  def _methodRefViaFalseBodyOut: Iterator[nodes.MethodRef] = falseBodyOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via FINALLY_BODY OUT edge.
    */
  def _methodRefViaFinallyBodyOut: Iterator[nodes.MethodRef] = finallyBodyOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via FOR_BODY OUT edge.
    */
  def _methodRefViaForBodyOut: Iterator[nodes.MethodRef] = forBodyOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via FOR_INIT OUT edge.
    */
  def _methodRefViaForInitOut: Iterator[nodes.MethodRef] = forInitOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via FOR_UPDATE OUT edge.
    */
  def _methodRefViaForUpdateOut: Iterator[nodes.MethodRef] = forUpdateOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def _methodRefViaPostDominateIn: Iterator[nodes.MethodRef] = postDominateIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def _methodRefViaPostDominateOut: Iterator[nodes.MethodRef] = postDominateOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def _methodRefViaReachingDefOut: Iterator[nodes.MethodRef] = reachingDefOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via TRUE_BODY OUT edge.
    */
  def _methodRefViaTrueBodyOut: Iterator[nodes.MethodRef] = trueBodyOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via TRY_BODY OUT edge.
    */
  def _methodRefViaTryBodyOut: Iterator[nodes.MethodRef] = tryBodyOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_RETURN via CDG OUT edge.
    */
  def _methodReturnViaCdgOut: Iterator[nodes.MethodReturn] = cdgOut.collectAll[nodes.MethodReturn]

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def _methodReturnViaDominateOut: Iterator[nodes.MethodReturn] = dominateOut.collectAll[nodes.MethodReturn]

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def _methodReturnViaPostDominateIn: Iterator[nodes.MethodReturn] = postDominateIn.collectAll[nodes.MethodReturn]

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def _modifierViaAstOut: Iterator[nodes.Modifier] = astOut.collectAll[nodes.Modifier]

  /** Traverse to RETURN via ARGUMENT IN edge.
    */
  def _returnViaArgumentIn: Iterator[nodes.Return] = argumentIn.collectAll[nodes.Return]

  /** Traverse to RETURN via AST IN edge.
    */
  def _returnViaAstIn: Iterator[nodes.Return] = astIn.collectAll[nodes.Return]

  /** Traverse to RETURN via AST OUT edge.
    */
  def _returnViaAstOut: Iterator[nodes.Return] = astOut.collectAll[nodes.Return]

  /** Traverse to RETURN via CATCH_BODY OUT edge.
    */
  def _returnViaCatchBodyOut: Iterator[nodes.Return] = catchBodyOut.collectAll[nodes.Return]

  /** Traverse to RETURN via CDG OUT edge.
    */
  def _returnViaCdgOut: Iterator[nodes.Return] = cdgOut.collectAll[nodes.Return]

  /** Traverse to RETURN via CONDITION OUT edge.
    */
  def _returnViaConditionOut: Iterator[nodes.Return] = conditionOut.collectAll[nodes.Return]

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def _returnViaDominateIn: Iterator[nodes.Return] = dominateIn.collectAll[nodes.Return]

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def _returnViaDominateOut: Iterator[nodes.Return] = dominateOut.collectAll[nodes.Return]

  /** Traverse to RETURN via DO_BODY OUT edge.
    */
  def _returnViaDoBodyOut: Iterator[nodes.Return] = doBodyOut.collectAll[nodes.Return]

  /** Traverse to RETURN via FALSE_BODY OUT edge.
    */
  def _returnViaFalseBodyOut: Iterator[nodes.Return] = falseBodyOut.collectAll[nodes.Return]

  /** Traverse to RETURN via FINALLY_BODY OUT edge.
    */
  def _returnViaFinallyBodyOut: Iterator[nodes.Return] = finallyBodyOut.collectAll[nodes.Return]

  /** Traverse to RETURN via FOR_BODY OUT edge.
    */
  def _returnViaForBodyOut: Iterator[nodes.Return] = forBodyOut.collectAll[nodes.Return]

  /** Traverse to RETURN via FOR_INIT OUT edge.
    */
  def _returnViaForInitOut: Iterator[nodes.Return] = forInitOut.collectAll[nodes.Return]

  /** Traverse to RETURN via FOR_UPDATE OUT edge.
    */
  def _returnViaForUpdateOut: Iterator[nodes.Return] = forUpdateOut.collectAll[nodes.Return]

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def _returnViaPostDominateIn: Iterator[nodes.Return] = postDominateIn.collectAll[nodes.Return]

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def _returnViaPostDominateOut: Iterator[nodes.Return] = postDominateOut.collectAll[nodes.Return]

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def _returnViaReachingDefOut: Iterator[nodes.Return] = reachingDefOut.collectAll[nodes.Return]

  /** Traverse to RETURN via TRUE_BODY OUT edge.
    */
  def _returnViaTrueBodyOut: Iterator[nodes.Return] = trueBodyOut.collectAll[nodes.Return]

  /** Traverse to RETURN via TRY_BODY OUT edge.
    */
  def _returnViaTryBodyOut: Iterator[nodes.Return] = tryBodyOut.collectAll[nodes.Return]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def _typeViaEvalTypeOut: Iterator[nodes.Type] = evalTypeOut.collectAll[nodes.Type]

  /** Traverse to TYPE_REF via AST OUT edge.
    */
  def _typeRefViaAstOut: Iterator[nodes.TypeRef] = astOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via CATCH_BODY OUT edge.
    */
  def _typeRefViaCatchBodyOut: Iterator[nodes.TypeRef] = catchBodyOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def _typeRefViaCdgIn: Iterator[nodes.TypeRef] = cdgIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via CDG OUT edge.
    */
  def _typeRefViaCdgOut: Iterator[nodes.TypeRef] = cdgOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via CONDITION OUT edge.
    */
  def _typeRefViaConditionOut: Iterator[nodes.TypeRef] = conditionOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def _typeRefViaDominateIn: Iterator[nodes.TypeRef] = dominateIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def _typeRefViaDominateOut: Iterator[nodes.TypeRef] = dominateOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via DO_BODY OUT edge.
    */
  def _typeRefViaDoBodyOut: Iterator[nodes.TypeRef] = doBodyOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via FALSE_BODY OUT edge.
    */
  def _typeRefViaFalseBodyOut: Iterator[nodes.TypeRef] = falseBodyOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via FINALLY_BODY OUT edge.
    */
  def _typeRefViaFinallyBodyOut: Iterator[nodes.TypeRef] = finallyBodyOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via FOR_BODY OUT edge.
    */
  def _typeRefViaForBodyOut: Iterator[nodes.TypeRef] = forBodyOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via FOR_INIT OUT edge.
    */
  def _typeRefViaForInitOut: Iterator[nodes.TypeRef] = forInitOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via FOR_UPDATE OUT edge.
    */
  def _typeRefViaForUpdateOut: Iterator[nodes.TypeRef] = forUpdateOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def _typeRefViaPostDominateIn: Iterator[nodes.TypeRef] = postDominateIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def _typeRefViaPostDominateOut: Iterator[nodes.TypeRef] = postDominateOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def _typeRefViaReachingDefOut: Iterator[nodes.TypeRef] = reachingDefOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via TRUE_BODY OUT edge.
    */
  def _typeRefViaTrueBodyOut: Iterator[nodes.TypeRef] = trueBodyOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via TRY_BODY OUT edge.
    */
  def _typeRefViaTryBodyOut: Iterator[nodes.TypeRef] = tryBodyOut.collectAll[nodes.TypeRef]

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def _unknownViaAstIn: Iterator[nodes.Unknown] = astIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via AST OUT edge.
    */
  def _unknownViaAstOut: Iterator[nodes.Unknown] = astOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via CATCH_BODY OUT edge.
    */
  def _unknownViaCatchBodyOut: Iterator[nodes.Unknown] = catchBodyOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def _unknownViaCdgIn: Iterator[nodes.Unknown] = cdgIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via CDG OUT edge.
    */
  def _unknownViaCdgOut: Iterator[nodes.Unknown] = cdgOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via CONDITION OUT edge.
    */
  def _unknownViaConditionOut: Iterator[nodes.Unknown] = conditionOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def _unknownViaDominateIn: Iterator[nodes.Unknown] = dominateIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def _unknownViaDominateOut: Iterator[nodes.Unknown] = dominateOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via DO_BODY OUT edge.
    */
  def _unknownViaDoBodyOut: Iterator[nodes.Unknown] = doBodyOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via FALSE_BODY OUT edge.
    */
  def _unknownViaFalseBodyOut: Iterator[nodes.Unknown] = falseBodyOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via FINALLY_BODY OUT edge.
    */
  def _unknownViaFinallyBodyOut: Iterator[nodes.Unknown] = finallyBodyOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via FOR_BODY OUT edge.
    */
  def _unknownViaForBodyOut: Iterator[nodes.Unknown] = forBodyOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via FOR_INIT OUT edge.
    */
  def _unknownViaForInitOut: Iterator[nodes.Unknown] = forInitOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via FOR_UPDATE OUT edge.
    */
  def _unknownViaForUpdateOut: Iterator[nodes.Unknown] = forUpdateOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def _unknownViaPostDominateIn: Iterator[nodes.Unknown] = postDominateIn.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def _unknownViaPostDominateOut: Iterator[nodes.Unknown] = postDominateOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via TRUE_BODY OUT edge.
    */
  def _unknownViaTrueBodyOut: Iterator[nodes.Unknown] = trueBodyOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via TRY_BODY OUT edge.
    */
  def _unknownViaTryBodyOut: Iterator[nodes.Unknown] = tryBodyOut.collectAll[nodes.Unknown]

  def argumentIn: Iterator[nodes.Expression] = node._argumentIn.cast[nodes.Expression]

  def astIn: Iterator[nodes.Expression] = node._astIn.cast[nodes.Expression]

  def astOut: Iterator[nodes.AstNode] = node._astOut.cast[nodes.AstNode]

  def catchBodyIn: Iterator[nodes.ControlStructure] = node._catchBodyIn.cast[nodes.ControlStructure]

  def catchBodyOut: Iterator[nodes.CfgNode] = node._catchBodyOut.cast[nodes.CfgNode]

  def cdgIn: Iterator[nodes.CfgNode] = node._cdgIn.cast[nodes.CfgNode]

  def cdgOut: Iterator[nodes.CfgNode] = node._cdgOut.cast[nodes.CfgNode]

  def cfgOut: Iterator[nodes.CfgNode] = node._cfgOut.cast[nodes.CfgNode]

  def conditionIn: Iterator[nodes.ControlStructure] = node._conditionIn.cast[nodes.ControlStructure]

  def conditionOut: Iterator[nodes.CfgNode] = node._conditionOut.cast[nodes.CfgNode]

  def containsIn: Iterator[nodes.Method] = node._containsIn.cast[nodes.Method]

  def doBodyIn: Iterator[nodes.ControlStructure] = node._doBodyIn.cast[nodes.ControlStructure]

  def doBodyOut: Iterator[nodes.CfgNode] = node._doBodyOut.cast[nodes.CfgNode]

  def dominateIn: Iterator[nodes.CfgNode] = node._dominateIn.cast[nodes.CfgNode]

  def dominateOut: Iterator[nodes.CfgNode] = node._dominateOut.cast[nodes.CfgNode]

  def evalTypeOut: Iterator[nodes.Type] = node._evalTypeOut.cast[nodes.Type]

  def falseBodyIn: Iterator[nodes.ControlStructure] = node._falseBodyIn.cast[nodes.ControlStructure]

  def falseBodyOut: Iterator[nodes.CfgNode] = node._falseBodyOut.cast[nodes.CfgNode]

  def finallyBodyIn: Iterator[nodes.ControlStructure] = node._finallyBodyIn.cast[nodes.ControlStructure]

  def finallyBodyOut: Iterator[nodes.CfgNode] = node._finallyBodyOut.cast[nodes.CfgNode]

  def forBodyIn: Iterator[nodes.ControlStructure] = node._forBodyIn.cast[nodes.ControlStructure]

  def forBodyOut: Iterator[nodes.CfgNode] = node._forBodyOut.cast[nodes.CfgNode]

  def forInitIn: Iterator[nodes.ControlStructure] = node._forInitIn.cast[nodes.ControlStructure]

  def forInitOut: Iterator[nodes.CfgNode] = node._forInitOut.cast[nodes.CfgNode]

  def forUpdateIn: Iterator[nodes.ControlStructure] = node._forUpdateIn.cast[nodes.ControlStructure]

  def forUpdateOut: Iterator[nodes.CfgNode] = node._forUpdateOut.cast[nodes.CfgNode]

  def postDominateIn: Iterator[nodes.CfgNode] = node._postDominateIn.cast[nodes.CfgNode]

  def postDominateOut: Iterator[nodes.CfgNode] = node._postDominateOut.cast[nodes.CfgNode]

  def reachingDefOut: Iterator[nodes.CfgNode] = node._reachingDefOut.cast[nodes.CfgNode]

  def receiverIn: Iterator[nodes.Call] = node._receiverIn.cast[nodes.Call]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]

  def trueBodyIn: Iterator[nodes.ControlStructure] = node._trueBodyIn.cast[nodes.ControlStructure]

  def trueBodyOut: Iterator[nodes.CfgNode] = node._trueBodyOut.cast[nodes.CfgNode]

  def tryBodyIn: Iterator[nodes.ControlStructure] = node._tryBodyIn.cast[nodes.ControlStructure]

  def tryBodyOut: Iterator[nodes.CfgNode] = node._tryBodyOut.cast[nodes.CfgNode]
}

final class AccessNeighborsForControlStructureTraversal(val traversal: Iterator[nodes.ControlStructure])
    extends AnyVal {

  /** Traverse to BLOCK via AST IN edge.
    */
  def _blockViaAstIn: Iterator[nodes.Block] = traversal.flatMap(_._blockViaAstIn)

  /** Traverse to BLOCK via AST OUT edge.
    */
  def _blockViaAstOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaAstOut)

  /** Traverse to BLOCK via CATCH_BODY OUT edge.
    */
  def _blockViaCatchBodyOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaCatchBodyOut)

  /** Traverse to BLOCK via CDG IN edge.
    */
  def _blockViaCdgIn: Iterator[nodes.Block] = traversal.flatMap(_._blockViaCdgIn)

  /** Traverse to BLOCK via CDG OUT edge.
    */
  def _blockViaCdgOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaCdgOut)

  /** Traverse to BLOCK via CONDITION OUT edge.
    */
  def _blockViaConditionOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaConditionOut)

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def _blockViaDominateIn: Iterator[nodes.Block] = traversal.flatMap(_._blockViaDominateIn)

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def _blockViaDominateOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaDominateOut)

  /** Traverse to BLOCK via DO_BODY OUT edge.
    */
  def _blockViaDoBodyOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaDoBodyOut)

  /** Traverse to BLOCK via FALSE_BODY OUT edge.
    */
  def _blockViaFalseBodyOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaFalseBodyOut)

  /** Traverse to BLOCK via FINALLY_BODY OUT edge.
    */
  def _blockViaFinallyBodyOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaFinallyBodyOut)

  /** Traverse to BLOCK via FOR_BODY OUT edge.
    */
  def _blockViaForBodyOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaForBodyOut)

  /** Traverse to BLOCK via FOR_INIT OUT edge.
    */
  def _blockViaForInitOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaForInitOut)

  /** Traverse to BLOCK via FOR_UPDATE OUT edge.
    */
  def _blockViaForUpdateOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaForUpdateOut)

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def _blockViaPostDominateIn: Iterator[nodes.Block] = traversal.flatMap(_._blockViaPostDominateIn)

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def _blockViaPostDominateOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaPostDominateOut)

  /** Traverse to BLOCK via TRUE_BODY OUT edge.
    */
  def _blockViaTrueBodyOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaTrueBodyOut)

  /** Traverse to BLOCK via TRY_BODY OUT edge.
    */
  def _blockViaTryBodyOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaTryBodyOut)

  /** Traverse to CALL via ARGUMENT IN edge.
    */
  def _callViaArgumentIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaArgumentIn)

  /** Traverse to CALL via AST IN edge.
    */
  def _callViaAstIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaAstIn)

  /** Traverse to CALL via AST OUT edge.
    */
  def _callViaAstOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaAstOut)

  /** Traverse to CALL via CATCH_BODY OUT edge.
    */
  def _callViaCatchBodyOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaCatchBodyOut)

  /** Traverse to CALL via CDG IN edge.
    */
  def _callViaCdgIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaCdgIn)

  /** Traverse to CALL via CDG OUT edge.
    */
  def _callViaCdgOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaCdgOut)

  /** Traverse to CALL via CONDITION OUT edge.
    */
  def _callViaConditionOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaConditionOut)

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def _callViaDominateIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaDominateIn)

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def _callViaDominateOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaDominateOut)

  /** Traverse to CALL via DO_BODY OUT edge.
    */
  def _callViaDoBodyOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaDoBodyOut)

  /** Traverse to CALL via FALSE_BODY OUT edge.
    */
  def _callViaFalseBodyOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaFalseBodyOut)

  /** Traverse to CALL via FINALLY_BODY OUT edge.
    */
  def _callViaFinallyBodyOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaFinallyBodyOut)

  /** Traverse to CALL via FOR_BODY OUT edge.
    */
  def _callViaForBodyOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaForBodyOut)

  /** Traverse to CALL via FOR_INIT OUT edge.
    */
  def _callViaForInitOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaForInitOut)

  /** Traverse to CALL via FOR_UPDATE OUT edge.
    */
  def _callViaForUpdateOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaForUpdateOut)

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def _callViaPostDominateIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaPostDominateIn)

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def _callViaPostDominateOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaPostDominateOut)

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def _callViaReachingDefOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaReachingDefOut)

  /** Traverse to CALL via RECEIVER IN edge.
    */
  def _callViaReceiverIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaReceiverIn)

  /** Traverse to CALL via TRUE_BODY OUT edge.
    */
  def _callViaTrueBodyOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaTrueBodyOut)

  /** Traverse to CALL via TRY_BODY OUT edge.
    */
  def _callViaTryBodyOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaTryBodyOut)

  /** Traverse to CFG_NODE via CFG OUT edge.
    */
  def _cfgNodeViaCfgOut: Iterator[nodes.CfgNode] = traversal.flatMap(_._cfgNodeViaCfgOut)

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaAstIn)

  /** Traverse to CONTROL_STRUCTURE via AST OUT edge.
    */
  def _controlStructureViaAstOut: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaAstOut)

  /** Traverse to CONTROL_STRUCTURE via CATCH_BODY IN edge.
    */
  def _controlStructureViaCatchBodyIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaCatchBodyIn)

  /** Traverse to CONTROL_STRUCTURE via CATCH_BODY OUT edge.
    */
  def _controlStructureViaCatchBodyOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaCatchBodyOut)

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def _controlStructureViaCdgIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaCdgIn)

  /** Traverse to CONTROL_STRUCTURE via CDG OUT edge.
    */
  def _controlStructureViaCdgOut: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaCdgOut)

  /** Traverse to CONTROL_STRUCTURE via CONDITION IN edge.
    */
  def _controlStructureViaConditionIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaConditionIn)

  /** Traverse to CONTROL_STRUCTURE via CONDITION OUT edge.
    */
  def _controlStructureViaConditionOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaConditionOut)

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def _controlStructureViaDominateIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaDominateIn)

  /** Traverse to CONTROL_STRUCTURE via DOMINATE OUT edge.
    */
  def _controlStructureViaDominateOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaDominateOut)

  /** Traverse to CONTROL_STRUCTURE via DO_BODY IN edge.
    */
  def _controlStructureViaDoBodyIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaDoBodyIn)

  /** Traverse to CONTROL_STRUCTURE via DO_BODY OUT edge.
    */
  def _controlStructureViaDoBodyOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaDoBodyOut)

  /** Traverse to CONTROL_STRUCTURE via FALSE_BODY IN edge.
    */
  def _controlStructureViaFalseBodyIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaFalseBodyIn)

  /** Traverse to CONTROL_STRUCTURE via FALSE_BODY OUT edge.
    */
  def _controlStructureViaFalseBodyOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaFalseBodyOut)

  /** Traverse to CONTROL_STRUCTURE via FINALLY_BODY IN edge.
    */
  def _controlStructureViaFinallyBodyIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaFinallyBodyIn)

  /** Traverse to CONTROL_STRUCTURE via FINALLY_BODY OUT edge.
    */
  def _controlStructureViaFinallyBodyOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaFinallyBodyOut)

  /** Traverse to CONTROL_STRUCTURE via FOR_BODY IN edge.
    */
  def _controlStructureViaForBodyIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaForBodyIn)

  /** Traverse to CONTROL_STRUCTURE via FOR_BODY OUT edge.
    */
  def _controlStructureViaForBodyOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaForBodyOut)

  /** Traverse to CONTROL_STRUCTURE via FOR_INIT IN edge.
    */
  def _controlStructureViaForInitIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaForInitIn)

  /** Traverse to CONTROL_STRUCTURE via FOR_INIT OUT edge.
    */
  def _controlStructureViaForInitOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaForInitOut)

  /** Traverse to CONTROL_STRUCTURE via FOR_UPDATE IN edge.
    */
  def _controlStructureViaForUpdateIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaForUpdateIn)

  /** Traverse to CONTROL_STRUCTURE via FOR_UPDATE OUT edge.
    */
  def _controlStructureViaForUpdateOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaForUpdateOut)

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def _controlStructureViaPostDominateIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaPostDominateIn)

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def _controlStructureViaPostDominateOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaPostDominateOut)

  /** Traverse to CONTROL_STRUCTURE via TRUE_BODY IN edge.
    */
  def _controlStructureViaTrueBodyIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaTrueBodyIn)

  /** Traverse to CONTROL_STRUCTURE via TRUE_BODY OUT edge.
    */
  def _controlStructureViaTrueBodyOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaTrueBodyOut)

  /** Traverse to CONTROL_STRUCTURE via TRY_BODY IN edge.
    */
  def _controlStructureViaTryBodyIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaTryBodyIn)

  /** Traverse to CONTROL_STRUCTURE via TRY_BODY OUT edge.
    */
  def _controlStructureViaTryBodyOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaTryBodyOut)

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def _fieldIdentifierViaCdgIn: Iterator[nodes.FieldIdentifier] = traversal.flatMap(_._fieldIdentifierViaCdgIn)

  /** Traverse to FIELD_IDENTIFIER via CDG OUT edge.
    */
  def _fieldIdentifierViaCdgOut: Iterator[nodes.FieldIdentifier] = traversal.flatMap(_._fieldIdentifierViaCdgOut)

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def _fieldIdentifierViaDominateIn: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_._fieldIdentifierViaDominateIn)

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def _fieldIdentifierViaDominateOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_._fieldIdentifierViaDominateOut)

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _fieldIdentifierViaPostDominateIn: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_._fieldIdentifierViaPostDominateIn)

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _fieldIdentifierViaPostDominateOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_._fieldIdentifierViaPostDominateOut)

  /** Traverse to IDENTIFIER via AST OUT edge.
    */
  def _identifierViaAstOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaAstOut)

  /** Traverse to IDENTIFIER via CATCH_BODY OUT edge.
    */
  def _identifierViaCatchBodyOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaCatchBodyOut)

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def _identifierViaCdgIn: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaCdgIn)

  /** Traverse to IDENTIFIER via CDG OUT edge.
    */
  def _identifierViaCdgOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaCdgOut)

  /** Traverse to IDENTIFIER via CONDITION OUT edge.
    */
  def _identifierViaConditionOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaConditionOut)

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def _identifierViaDominateIn: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaDominateIn)

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def _identifierViaDominateOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaDominateOut)

  /** Traverse to IDENTIFIER via DO_BODY OUT edge.
    */
  def _identifierViaDoBodyOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaDoBodyOut)

  /** Traverse to IDENTIFIER via FALSE_BODY OUT edge.
    */
  def _identifierViaFalseBodyOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaFalseBodyOut)

  /** Traverse to IDENTIFIER via FINALLY_BODY OUT edge.
    */
  def _identifierViaFinallyBodyOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaFinallyBodyOut)

  /** Traverse to IDENTIFIER via FOR_BODY OUT edge.
    */
  def _identifierViaForBodyOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaForBodyOut)

  /** Traverse to IDENTIFIER via FOR_INIT OUT edge.
    */
  def _identifierViaForInitOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaForInitOut)

  /** Traverse to IDENTIFIER via FOR_UPDATE OUT edge.
    */
  def _identifierViaForUpdateOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaForUpdateOut)

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _identifierViaPostDominateIn: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaPostDominateIn)

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _identifierViaPostDominateOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaPostDominateOut)

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def _identifierViaReachingDefOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaReachingDefOut)

  /** Traverse to IDENTIFIER via TRUE_BODY OUT edge.
    */
  def _identifierViaTrueBodyOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaTrueBodyOut)

  /** Traverse to IDENTIFIER via TRY_BODY OUT edge.
    */
  def _identifierViaTryBodyOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaTryBodyOut)

  /** Traverse to JUMP_LABEL via AST OUT edge.
    */
  def _jumpLabelViaAstOut: Iterator[nodes.JumpLabel] = traversal.flatMap(_._jumpLabelViaAstOut)

  /** Traverse to JUMP_TARGET via AST OUT edge.
    */
  def _jumpTargetViaAstOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaAstOut)

  /** Traverse to JUMP_TARGET via CATCH_BODY OUT edge.
    */
  def _jumpTargetViaCatchBodyOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaCatchBodyOut)

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def _jumpTargetViaCdgIn: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaCdgIn)

  /** Traverse to JUMP_TARGET via CDG OUT edge.
    */
  def _jumpTargetViaCdgOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaCdgOut)

  /** Traverse to JUMP_TARGET via CONDITION OUT edge.
    */
  def _jumpTargetViaConditionOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaConditionOut)

  /** Traverse to JUMP_TARGET via DOMINATE IN edge.
    */
  def _jumpTargetViaDominateIn: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaDominateIn)

  /** Traverse to JUMP_TARGET via DOMINATE OUT edge.
    */
  def _jumpTargetViaDominateOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaDominateOut)

  /** Traverse to JUMP_TARGET via DO_BODY OUT edge.
    */
  def _jumpTargetViaDoBodyOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaDoBodyOut)

  /** Traverse to JUMP_TARGET via FALSE_BODY OUT edge.
    */
  def _jumpTargetViaFalseBodyOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaFalseBodyOut)

  /** Traverse to JUMP_TARGET via FINALLY_BODY OUT edge.
    */
  def _jumpTargetViaFinallyBodyOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaFinallyBodyOut)

  /** Traverse to JUMP_TARGET via FOR_BODY OUT edge.
    */
  def _jumpTargetViaForBodyOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaForBodyOut)

  /** Traverse to JUMP_TARGET via FOR_INIT OUT edge.
    */
  def _jumpTargetViaForInitOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaForInitOut)

  /** Traverse to JUMP_TARGET via FOR_UPDATE OUT edge.
    */
  def _jumpTargetViaForUpdateOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaForUpdateOut)

  /** Traverse to JUMP_TARGET via POST_DOMINATE IN edge.
    */
  def _jumpTargetViaPostDominateIn: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaPostDominateIn)

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def _jumpTargetViaPostDominateOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaPostDominateOut)

  /** Traverse to JUMP_TARGET via TRUE_BODY OUT edge.
    */
  def _jumpTargetViaTrueBodyOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaTrueBodyOut)

  /** Traverse to JUMP_TARGET via TRY_BODY OUT edge.
    */
  def _jumpTargetViaTryBodyOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaTryBodyOut)

  /** Traverse to LITERAL via AST OUT edge.
    */
  def _literalViaAstOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaAstOut)

  /** Traverse to LITERAL via CATCH_BODY OUT edge.
    */
  def _literalViaCatchBodyOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaCatchBodyOut)

  /** Traverse to LITERAL via CDG IN edge.
    */
  def _literalViaCdgIn: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaCdgIn)

  /** Traverse to LITERAL via CDG OUT edge.
    */
  def _literalViaCdgOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaCdgOut)

  /** Traverse to LITERAL via CONDITION OUT edge.
    */
  def _literalViaConditionOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaConditionOut)

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def _literalViaDominateIn: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaDominateIn)

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def _literalViaDominateOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaDominateOut)

  /** Traverse to LITERAL via DO_BODY OUT edge.
    */
  def _literalViaDoBodyOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaDoBodyOut)

  /** Traverse to LITERAL via FALSE_BODY OUT edge.
    */
  def _literalViaFalseBodyOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaFalseBodyOut)

  /** Traverse to LITERAL via FINALLY_BODY OUT edge.
    */
  def _literalViaFinallyBodyOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaFinallyBodyOut)

  /** Traverse to LITERAL via FOR_BODY OUT edge.
    */
  def _literalViaForBodyOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaForBodyOut)

  /** Traverse to LITERAL via FOR_INIT OUT edge.
    */
  def _literalViaForInitOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaForInitOut)

  /** Traverse to LITERAL via FOR_UPDATE OUT edge.
    */
  def _literalViaForUpdateOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaForUpdateOut)

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def _literalViaPostDominateIn: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaPostDominateIn)

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def _literalViaPostDominateOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaPostDominateOut)

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def _literalViaReachingDefOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaReachingDefOut)

  /** Traverse to LITERAL via TRUE_BODY OUT edge.
    */
  def _literalViaTrueBodyOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaTrueBodyOut)

  /** Traverse to LITERAL via TRY_BODY OUT edge.
    */
  def _literalViaTryBodyOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaTryBodyOut)

  /** Traverse to LOCAL via AST OUT edge.
    */
  def _localViaAstOut: Iterator[nodes.Local] = traversal.flatMap(_._localViaAstOut)

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def _methodViaContainsIn: Iterator[nodes.Method] = traversal.flatMap(_._methodViaContainsIn)

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def _methodViaPostDominateOut: Iterator[nodes.Method] = traversal.flatMap(_._methodViaPostDominateOut)

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def _methodParameterOutViaReachingDefOut: Iterator[nodes.MethodParameterOut] =
    traversal.flatMap(_._methodParameterOutViaReachingDefOut)

  /** Traverse to METHOD_REF via AST OUT edge.
    */
  def _methodRefViaAstOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaAstOut)

  /** Traverse to METHOD_REF via CATCH_BODY OUT edge.
    */
  def _methodRefViaCatchBodyOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaCatchBodyOut)

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def _methodRefViaCdgIn: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaCdgIn)

  /** Traverse to METHOD_REF via CDG OUT edge.
    */
  def _methodRefViaCdgOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaCdgOut)

  /** Traverse to METHOD_REF via CONDITION OUT edge.
    */
  def _methodRefViaConditionOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaConditionOut)

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def _methodRefViaDominateIn: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaDominateIn)

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def _methodRefViaDominateOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaDominateOut)

  /** Traverse to METHOD_REF via DO_BODY OUT edge.
    */
  def _methodRefViaDoBodyOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaDoBodyOut)

  /** Traverse to METHOD_REF via FALSE_BODY OUT edge.
    */
  def _methodRefViaFalseBodyOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaFalseBodyOut)

  /** Traverse to METHOD_REF via FINALLY_BODY OUT edge.
    */
  def _methodRefViaFinallyBodyOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaFinallyBodyOut)

  /** Traverse to METHOD_REF via FOR_BODY OUT edge.
    */
  def _methodRefViaForBodyOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaForBodyOut)

  /** Traverse to METHOD_REF via FOR_INIT OUT edge.
    */
  def _methodRefViaForInitOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaForInitOut)

  /** Traverse to METHOD_REF via FOR_UPDATE OUT edge.
    */
  def _methodRefViaForUpdateOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaForUpdateOut)

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def _methodRefViaPostDominateIn: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaPostDominateIn)

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def _methodRefViaPostDominateOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaPostDominateOut)

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def _methodRefViaReachingDefOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaReachingDefOut)

  /** Traverse to METHOD_REF via TRUE_BODY OUT edge.
    */
  def _methodRefViaTrueBodyOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaTrueBodyOut)

  /** Traverse to METHOD_REF via TRY_BODY OUT edge.
    */
  def _methodRefViaTryBodyOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaTryBodyOut)

  /** Traverse to METHOD_RETURN via CDG OUT edge.
    */
  def _methodReturnViaCdgOut: Iterator[nodes.MethodReturn] = traversal.flatMap(_._methodReturnViaCdgOut)

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def _methodReturnViaDominateOut: Iterator[nodes.MethodReturn] = traversal.flatMap(_._methodReturnViaDominateOut)

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def _methodReturnViaPostDominateIn: Iterator[nodes.MethodReturn] = traversal.flatMap(_._methodReturnViaPostDominateIn)

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def _modifierViaAstOut: Iterator[nodes.Modifier] = traversal.flatMap(_._modifierViaAstOut)

  /** Traverse to RETURN via ARGUMENT IN edge.
    */
  def _returnViaArgumentIn: Iterator[nodes.Return] = traversal.flatMap(_._returnViaArgumentIn)

  /** Traverse to RETURN via AST IN edge.
    */
  def _returnViaAstIn: Iterator[nodes.Return] = traversal.flatMap(_._returnViaAstIn)

  /** Traverse to RETURN via AST OUT edge.
    */
  def _returnViaAstOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaAstOut)

  /** Traverse to RETURN via CATCH_BODY OUT edge.
    */
  def _returnViaCatchBodyOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaCatchBodyOut)

  /** Traverse to RETURN via CDG OUT edge.
    */
  def _returnViaCdgOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaCdgOut)

  /** Traverse to RETURN via CONDITION OUT edge.
    */
  def _returnViaConditionOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaConditionOut)

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def _returnViaDominateIn: Iterator[nodes.Return] = traversal.flatMap(_._returnViaDominateIn)

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def _returnViaDominateOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaDominateOut)

  /** Traverse to RETURN via DO_BODY OUT edge.
    */
  def _returnViaDoBodyOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaDoBodyOut)

  /** Traverse to RETURN via FALSE_BODY OUT edge.
    */
  def _returnViaFalseBodyOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaFalseBodyOut)

  /** Traverse to RETURN via FINALLY_BODY OUT edge.
    */
  def _returnViaFinallyBodyOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaFinallyBodyOut)

  /** Traverse to RETURN via FOR_BODY OUT edge.
    */
  def _returnViaForBodyOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaForBodyOut)

  /** Traverse to RETURN via FOR_INIT OUT edge.
    */
  def _returnViaForInitOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaForInitOut)

  /** Traverse to RETURN via FOR_UPDATE OUT edge.
    */
  def _returnViaForUpdateOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaForUpdateOut)

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def _returnViaPostDominateIn: Iterator[nodes.Return] = traversal.flatMap(_._returnViaPostDominateIn)

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def _returnViaPostDominateOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaPostDominateOut)

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def _returnViaReachingDefOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaReachingDefOut)

  /** Traverse to RETURN via TRUE_BODY OUT edge.
    */
  def _returnViaTrueBodyOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaTrueBodyOut)

  /** Traverse to RETURN via TRY_BODY OUT edge.
    */
  def _returnViaTryBodyOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaTryBodyOut)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_._tagViaTaggedByOut)

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def _typeViaEvalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_._typeViaEvalTypeOut)

  /** Traverse to TYPE_REF via AST OUT edge.
    */
  def _typeRefViaAstOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaAstOut)

  /** Traverse to TYPE_REF via CATCH_BODY OUT edge.
    */
  def _typeRefViaCatchBodyOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaCatchBodyOut)

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def _typeRefViaCdgIn: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaCdgIn)

  /** Traverse to TYPE_REF via CDG OUT edge.
    */
  def _typeRefViaCdgOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaCdgOut)

  /** Traverse to TYPE_REF via CONDITION OUT edge.
    */
  def _typeRefViaConditionOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaConditionOut)

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def _typeRefViaDominateIn: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaDominateIn)

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def _typeRefViaDominateOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaDominateOut)

  /** Traverse to TYPE_REF via DO_BODY OUT edge.
    */
  def _typeRefViaDoBodyOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaDoBodyOut)

  /** Traverse to TYPE_REF via FALSE_BODY OUT edge.
    */
  def _typeRefViaFalseBodyOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaFalseBodyOut)

  /** Traverse to TYPE_REF via FINALLY_BODY OUT edge.
    */
  def _typeRefViaFinallyBodyOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaFinallyBodyOut)

  /** Traverse to TYPE_REF via FOR_BODY OUT edge.
    */
  def _typeRefViaForBodyOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaForBodyOut)

  /** Traverse to TYPE_REF via FOR_INIT OUT edge.
    */
  def _typeRefViaForInitOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaForInitOut)

  /** Traverse to TYPE_REF via FOR_UPDATE OUT edge.
    */
  def _typeRefViaForUpdateOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaForUpdateOut)

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def _typeRefViaPostDominateIn: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaPostDominateIn)

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def _typeRefViaPostDominateOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaPostDominateOut)

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def _typeRefViaReachingDefOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaReachingDefOut)

  /** Traverse to TYPE_REF via TRUE_BODY OUT edge.
    */
  def _typeRefViaTrueBodyOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaTrueBodyOut)

  /** Traverse to TYPE_REF via TRY_BODY OUT edge.
    */
  def _typeRefViaTryBodyOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaTryBodyOut)

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def _unknownViaAstIn: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaAstIn)

  /** Traverse to UNKNOWN via AST OUT edge.
    */
  def _unknownViaAstOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaAstOut)

  /** Traverse to UNKNOWN via CATCH_BODY OUT edge.
    */
  def _unknownViaCatchBodyOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaCatchBodyOut)

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def _unknownViaCdgIn: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaCdgIn)

  /** Traverse to UNKNOWN via CDG OUT edge.
    */
  def _unknownViaCdgOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaCdgOut)

  /** Traverse to UNKNOWN via CONDITION OUT edge.
    */
  def _unknownViaConditionOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaConditionOut)

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def _unknownViaDominateIn: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaDominateIn)

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def _unknownViaDominateOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaDominateOut)

  /** Traverse to UNKNOWN via DO_BODY OUT edge.
    */
  def _unknownViaDoBodyOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaDoBodyOut)

  /** Traverse to UNKNOWN via FALSE_BODY OUT edge.
    */
  def _unknownViaFalseBodyOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaFalseBodyOut)

  /** Traverse to UNKNOWN via FINALLY_BODY OUT edge.
    */
  def _unknownViaFinallyBodyOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaFinallyBodyOut)

  /** Traverse to UNKNOWN via FOR_BODY OUT edge.
    */
  def _unknownViaForBodyOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaForBodyOut)

  /** Traverse to UNKNOWN via FOR_INIT OUT edge.
    */
  def _unknownViaForInitOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaForInitOut)

  /** Traverse to UNKNOWN via FOR_UPDATE OUT edge.
    */
  def _unknownViaForUpdateOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaForUpdateOut)

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def _unknownViaPostDominateIn: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaPostDominateIn)

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def _unknownViaPostDominateOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaPostDominateOut)

  /** Traverse to UNKNOWN via TRUE_BODY OUT edge.
    */
  def _unknownViaTrueBodyOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaTrueBodyOut)

  /** Traverse to UNKNOWN via TRY_BODY OUT edge.
    */
  def _unknownViaTryBodyOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaTryBodyOut)

  def argumentIn: Iterator[nodes.Expression] = traversal.flatMap(_.argumentIn)

  def astIn: Iterator[nodes.Expression] = traversal.flatMap(_.astIn)

  def astOut: Iterator[nodes.AstNode] = traversal.flatMap(_.astOut)

  def catchBodyIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.catchBodyIn)

  def catchBodyOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.catchBodyOut)

  def cdgIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.cdgIn)

  def cdgOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.cdgOut)

  def cfgOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.cfgOut)

  def conditionIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.conditionIn)

  def conditionOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.conditionOut)

  def containsIn: Iterator[nodes.Method] = traversal.flatMap(_.containsIn)

  def doBodyIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.doBodyIn)

  def doBodyOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.doBodyOut)

  def dominateIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.dominateIn)

  def dominateOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.dominateOut)

  def evalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_.evalTypeOut)

  def falseBodyIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.falseBodyIn)

  def falseBodyOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.falseBodyOut)

  def finallyBodyIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.finallyBodyIn)

  def finallyBodyOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.finallyBodyOut)

  def forBodyIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.forBodyIn)

  def forBodyOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.forBodyOut)

  def forInitIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.forInitIn)

  def forInitOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.forInitOut)

  def forUpdateIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.forUpdateIn)

  def forUpdateOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.forUpdateOut)

  def postDominateIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.postDominateIn)

  def postDominateOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.postDominateOut)

  def reachingDefOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.reachingDefOut)

  def receiverIn: Iterator[nodes.Call] = traversal.flatMap(_.receiverIn)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)

  def trueBodyIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.trueBodyIn)

  def trueBodyOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.trueBodyOut)

  def tryBodyIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.tryBodyIn)

  def tryBodyOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.tryBodyOut)
}
