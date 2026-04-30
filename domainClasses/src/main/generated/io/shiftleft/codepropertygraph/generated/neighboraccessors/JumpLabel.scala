package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForJumpLabel(val node: nodes.JumpLabel) extends AnyVal {

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: Iterator[nodes.ControlStructure] = astIn.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via JUMP_ARGUMENT IN edge.
    */
  def _controlStructureViaJumpArgumentIn: Iterator[nodes.ControlStructure] =
    jumpArgumentIn.collectAll[nodes.ControlStructure]

  def astIn: Iterator[nodes.ControlStructure] = node._astIn.cast[nodes.ControlStructure]

  def jumpArgumentIn: Iterator[nodes.ControlStructure] = node._jumpArgumentIn.cast[nodes.ControlStructure]
}

final class AccessNeighborsForJumpLabelTraversal(val traversal: Iterator[nodes.JumpLabel]) extends AnyVal {

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaAstIn)

  /** Traverse to CONTROL_STRUCTURE via JUMP_ARGUMENT IN edge.
    */
  def _controlStructureViaJumpArgumentIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaJumpArgumentIn)

  def astIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.astIn)

  def jumpArgumentIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.jumpArgumentIn)
}
