package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._

object NodeTypeStarters {
  val assignmentPattern = "<operator>.(assignment.*)|(.*(increment|decrement))"
}

class NodeTypeStarters(cpg: Cpg) {

  import NodeTypeStarters._

  def assignment: AssignmentTrav =
    new AssignmentTrav(cpg.call.name(assignmentPattern).raw)

}
