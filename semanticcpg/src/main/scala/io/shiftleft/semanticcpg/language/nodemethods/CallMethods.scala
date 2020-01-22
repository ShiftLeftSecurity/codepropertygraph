package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{HasArgumentIndex, StoredNode}
import io.shiftleft.semanticcpg.language._

class CallMethods(val node: nodes.Call) extends AnyVal {

  def argument(index: Int): StoredNode with HasArgumentIndex =
    node.start.argument(index).head

}
