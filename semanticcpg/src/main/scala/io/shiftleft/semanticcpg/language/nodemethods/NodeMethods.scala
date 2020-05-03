package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{Node, StoredNode}
import io.shiftleft.overflowdb.traversal.help
import io.shiftleft.overflowdb.traversal.help.Doc
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

@help.Traversal(elementType = classOf[nodes.Node])
class NodeMethods(val node: Node) extends AnyVal {

  @Doc("Human-readable location")
  def location: nodes.NewLocation =
    node match {
      case storedNode: StoredNode => LocationCreator(storedNode)
      case _                      => LocationCreator.emptyLocation("", None)
    }

  @Doc("Tags attached to this node")
  def tagList: List[nodes.TagBase] =
    node match {
      case storedNode: StoredNode =>
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
