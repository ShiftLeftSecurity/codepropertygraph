package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._

class NodeTypeStarters(cpg: Cpg) {

  def assign: Assignment = new Assignment(cpg.call.name("<operator>.assignment.*").raw)

}
