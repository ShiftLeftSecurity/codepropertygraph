package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._

object NodeTypeStarters {
  val assignmentPattern = "<operator>.(assignment.*)|(.*(increment|decrement))"
  val arithmeticPattern = "<operator>.(addition|subtraction|division|multiplication|exponentiation|modulo)"
}

class NodeTypeStarters(cpg: Cpg) {
  import NodeTypeStarters._

  def assignment: NodeSteps[Assignment] =
    cpg.call.name(assignmentPattern).map(new Assignment(_))

  def arithmetic: NodeSteps[Arithmetic] =
    cpg.call.name(arithmeticPattern).map(new Arithmetic(_))
}
