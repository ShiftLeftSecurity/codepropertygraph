package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForJumpLabel(val node: nodes.JumpLabel) extends AnyVal {

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: Iterator[nodes.ControlStructure] = astIn.collectAll[nodes.ControlStructure]

  def astIn: Iterator[nodes.ControlStructure] = node._astIn.cast[nodes.ControlStructure]
}

final class AccessNeighborsForJumpLabelTraversal(val traversal: Iterator[nodes.JumpLabel]) extends AnyVal {

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaAstIn)

  def astIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_.astIn)
}
