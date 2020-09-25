package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._
import overflowdb.traversal._

class CallMethods(val node: nodes.Call) extends AnyVal {
  def arguments(index: Int): Traversal[nodes.Expression] =
    node._argumentOut.asScala
      .collect {
        case expr: nodes.Expression if expr.argumentIndex == index => expr
      }
      .to(Traversal)

  def argument(index: Int): nodes.Expression =
    arguments(index).head

  def argumentOption(index: Int): Option[nodes.Expression] =
    node._argumentOut.asScala
      .collectFirst {
        case expr: nodes.Expression if expr.argumentIndex == index => expr
      }
}
