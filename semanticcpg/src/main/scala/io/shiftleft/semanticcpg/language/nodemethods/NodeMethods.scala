package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.{Node, StoredNode}
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.semanticcpg.language._
import gremlin.scala._

class NodeMethods(node: Node) {

  def location: nodes.NewLocation = {
    node match {
      case storedNode: StoredNode =>
        LocationCreator(storedNode)
      case _ =>
        LocationCreator.emptyLocation("", None)

    }
  }

  def tagList: List[nodes.TagBase] =
    node match {
      case storedNode: StoredNode =>
        storedNode.start.raw
          .out(EdgeTypes.TAGGED_BY)
          .toList //TODO MP: use project step
          .map { tagNode =>
            nodes
              .NewTag(tagNode.value2(NodeKeys.NAME), tagNode.value2(NodeKeys.VALUE))
              .asInstanceOf[nodes.TagBase]
          }
          .distinct
      case _ =>
        Nil
    }

  def reference: Option[nodes.StoredNode] =
    node.asInstanceOf[nodes.StoredNode]._refOut.nextOption

}
