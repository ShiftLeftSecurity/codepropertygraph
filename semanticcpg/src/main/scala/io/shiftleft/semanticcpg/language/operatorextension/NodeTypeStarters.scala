package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

object NodeTypeStarters {
  val assignmentPattern = "<operator>.(assignment.*)|(.*(increment|decrement))"
  val arithmeticPattern = "<operator>.(addition|subtraction|division|multiplication|exponentiation|modulo)"
}

class NodeTypeStarters(cpg: Cpg) {
  import NodeTypeStarters._

  def assignment: NodeSteps[nodes.Call] =
    cpg.call.name(assignmentPattern)

  def arithmetic: NodeSteps[nodes.Call] =
    cpg.call.name(arithmeticPattern)
}
