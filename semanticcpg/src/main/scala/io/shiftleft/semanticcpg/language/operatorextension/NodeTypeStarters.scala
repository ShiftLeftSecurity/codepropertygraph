package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import overflowdb.traversal.help.{Doc, TraversalSource}
import io.shiftleft.semanticcpg.language._

object NodeTypeStarters {
  val assignmentPattern = "<operator>.(assignment.*)|(.*(increment|decrement))"
  val arithmeticPattern = "<operator>.(addition|subtraction|division|multiplication|exponentiation|modulo)"
}

@TraversalSource
class NodeTypeStarters(cpg: Cpg) {
  import NodeTypeStarters._

  @Doc("All assignments")
  def assignment: NodeSteps[opnodes.Assignment] =
    cpg.call.name(assignmentPattern).map(new opnodes.Assignment(_))

  @Doc("All arithmetic operations")
  def arithmetic: NodeSteps[opnodes.Arithmetic] =
    cpg.call.name(arithmeticPattern).map(new opnodes.Arithmetic(_))
}
