package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{Node, StoredNode}
import io.shiftleft.semanticcpg.language.LocationCreator

class NodeMethods(node: Node) {

  def location: nodes.NewLocation = {
    node match {
      case storedNode: StoredNode =>
        LocationCreator(storedNode)
      case _ =>
        LocationCreator.emptyLocation("", None)

    }
  }
}
