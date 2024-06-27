package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForCfgNode(val node: nodes.CfgNode) extends AnyVal {

  /** Traverse to BLOCK via CFG IN edge.
    */
  def _blockViaCfgIn: Iterator[nodes.Block] = cfgIn.collectAll[nodes.Block]

  /** Traverse to CALL via CFG IN edge.
    */
  def _callViaCfgIn: Iterator[nodes.Call] = cfgIn.collectAll[nodes.Call]

  /** Traverse to CONTROL_STRUCTURE via CFG IN edge.
    */
  def _controlStructureViaCfgIn: Iterator[nodes.ControlStructure] = cfgIn.collectAll[nodes.ControlStructure]

  /** Traverse to FIELD_IDENTIFIER via CFG IN edge.
    */
  def _fieldIdentifierViaCfgIn: Iterator[nodes.FieldIdentifier] = cfgIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to IDENTIFIER via CFG IN edge.
    */
  def _identifierViaCfgIn: Iterator[nodes.Identifier] = cfgIn.collectAll[nodes.Identifier]

  /** Traverse to JUMP_TARGET via CFG IN edge.
    */
  def _jumpTargetViaCfgIn: Iterator[nodes.JumpTarget] = cfgIn.collectAll[nodes.JumpTarget]

  /** Traverse to LITERAL via CFG IN edge.
    */
  def _literalViaCfgIn: Iterator[nodes.Literal] = cfgIn.collectAll[nodes.Literal]

  /** Traverse to METHOD via CFG IN edge.
    */
  def _methodViaCfgIn: Iterator[nodes.Method] = cfgIn.collectAll[nodes.Method]

  /** Traverse to METHOD_REF via CFG IN edge.
    */
  def _methodRefViaCfgIn: Iterator[nodes.MethodRef] = cfgIn.collectAll[nodes.MethodRef]

  /** Traverse to TYPE_REF via CFG IN edge.
    */
  def _typeRefViaCfgIn: Iterator[nodes.TypeRef] = cfgIn.collectAll[nodes.TypeRef]

  /** Traverse to UNKNOWN via CFG IN edge.
    */
  def _unknownViaCfgIn: Iterator[nodes.Unknown] = cfgIn.collectAll[nodes.Unknown]

  def cfgIn: Iterator[nodes.CfgNode] = node._cfgIn.cast[nodes.CfgNode]
}

final class AccessNeighborsForCfgNodeTraversal(val traversal: Iterator[nodes.CfgNode]) extends AnyVal {

  /** Traverse to BLOCK via CFG IN edge.
    */
  def _blockViaCfgIn: Iterator[nodes.Block] = traversal.flatMap(_._blockViaCfgIn)

  /** Traverse to CALL via CFG IN edge.
    */
  def _callViaCfgIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaCfgIn)

  /** Traverse to CONTROL_STRUCTURE via CFG IN edge.
    */
  def _controlStructureViaCfgIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaCfgIn)

  /** Traverse to FIELD_IDENTIFIER via CFG IN edge.
    */
  def _fieldIdentifierViaCfgIn: Iterator[nodes.FieldIdentifier] = traversal.flatMap(_._fieldIdentifierViaCfgIn)

  /** Traverse to IDENTIFIER via CFG IN edge.
    */
  def _identifierViaCfgIn: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaCfgIn)

  /** Traverse to JUMP_TARGET via CFG IN edge.
    */
  def _jumpTargetViaCfgIn: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaCfgIn)

  /** Traverse to LITERAL via CFG IN edge.
    */
  def _literalViaCfgIn: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaCfgIn)

  /** Traverse to METHOD via CFG IN edge.
    */
  def _methodViaCfgIn: Iterator[nodes.Method] = traversal.flatMap(_._methodViaCfgIn)

  /** Traverse to METHOD_REF via CFG IN edge.
    */
  def _methodRefViaCfgIn: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaCfgIn)

  /** Traverse to TYPE_REF via CFG IN edge.
    */
  def _typeRefViaCfgIn: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaCfgIn)

  /** Traverse to UNKNOWN via CFG IN edge.
    */
  def _unknownViaCfgIn: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaCfgIn)

  def cfgIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.cfgIn)
}
