package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._

class NodeTypeStarters(cpg: Cpg) {

  def assignment: Assignment = new Assignment(cpg.call.name("<operator>.assignment.*").map(new nodes.Assignment(_)).raw)

}
