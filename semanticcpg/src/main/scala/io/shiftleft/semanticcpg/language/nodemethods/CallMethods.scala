package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class CallMethods(val node: nodes.Call) extends AnyVal {

  def argument(index: Int): nodes.Expression = node.start.argument(index).head

}
