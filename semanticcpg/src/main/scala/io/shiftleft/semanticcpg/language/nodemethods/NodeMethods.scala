package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class NodeMethods(val node: AbstractNode) extends AnyVal {

  def location: NewLocation =
    node match {
      case storedNode: StoredNode => LocationCreator(storedNode)
      case _                      => LocationCreator.emptyLocation("", None)
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
