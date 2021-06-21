package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.traversal._
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal
import overflowdb.traversal.help.{Doc, TraversalSource}

object NodeTypeStarters {
  val assignmentPattern = "<operator>.(assignment.*)|(.*(increment|decrement))"
  val arithmeticPattern = "<operator>.(addition|subtraction|division|multiplication|exponentiation|modulo)"
}

@TraversalSource
class NodeTypeStarters(cpg: Cpg) {
  import NodeTypeStarters._

  @Doc("All assignments")
  def assignment: Traversal[opnodes.Assignment] =
    cpg.call.name(assignmentPattern).map(new opnodes.Assignment(_))

  @Doc("All arithmetic operations")
  def arithmetic: Traversal[opnodes.Arithmetic] =
    cpg.call.name(arithmeticPattern).map(new opnodes.Arithmetic(_))
}
