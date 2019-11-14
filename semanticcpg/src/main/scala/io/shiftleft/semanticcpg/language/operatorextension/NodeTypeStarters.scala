package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._

object NodeTypeStarters {
  val assignmentPattern = "<operator>.(assignment.*)|(.*(increment|decrement))"
  val arithmeticPattern = "<operator>.(addition|subtraction|division|multiplication|exponentiation|modulo)"
}

class NodeTypeStarters(cpg: Cpg) {

  import NodeTypeStarters._

  def assignment: AssignmentTrav = new AssignmentTrav(cpg.call.name(assignmentPattern).map(new nodes.Assignment(_)).raw)

  def arithmetic: ArithmeticTrav = new ArithmeticTrav(cpg.call.name(arithmeticPattern).map(new nodes.Arithmetic(_)).raw)

}
