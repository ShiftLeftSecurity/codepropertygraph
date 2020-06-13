package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

class NodeMethods(val node: nodes.CpgNode) extends AnyVal {

  def location: nodes.NewLocation =
    node match {
      case storedNode: nodes.StoredNode => LocationCreator(storedNode)
      case _                      => LocationCreator.emptyLocation("", None)
    }

  def tagList: List[nodes.TagBase] =
    node match {
      case storedNode: nodes.StoredNode =>
        storedNode._taggedByOut.asScala
          .map {
            case tagNode: nodes.HasName with nodes.HasValue =>
              (tagNode.name, Option(tagNode.value))
          }
          .distinct
          .collect {
            case (name, Some(value)) => nodes.NewTag(name, value).asInstanceOf[nodes.TagBase]
          }
          .toList
      case _ =>
        Nil
    }

}
