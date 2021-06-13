package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes._
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

trait ILocationCreator {
  def apply(node: StoredNode): NewLocation
  def emptyLocation(label: String, node: Option[AbstractNode]): NewLocation
}

class NodeMethods(val node: AbstractNode) extends AnyVal {

  def location(implicit locationCreator: ILocationCreator): NewLocation =
    node match {
      case storedNode: StoredNode => locationCreator(storedNode)
      case _                      => locationCreator.emptyLocation("", None)
    }

  def tagList: Traversal[TagBase] =
    node match {
      case storedNode: StoredNode =>
        storedNode._taggedByOut.asScala
          .map {
            case tagNode: HasName with HasValue =>
              (tagNode.name, Option(tagNode.value))
          }
          .distinct
          .collect {
            case (name, Some(value)) =>
              NewTag()
                .name(name)
                .value(value)
                .build
                .asInstanceOf[TagBase]
          }
      case _ =>
        Traversal.empty
    }

}
