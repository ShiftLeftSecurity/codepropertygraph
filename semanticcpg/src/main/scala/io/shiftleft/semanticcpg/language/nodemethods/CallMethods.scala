package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._

class CallMethods(val node: nodes.Call) extends AnyVal {
  def arguments(index: Int): Iterable[nodes.Expression] =
    node._argumentOut.asScala.collect {
      case expr: nodes.Expression if expr.argumentIndex == index => expr
    }.toList

  def argument(index: Int): nodes.Expression = arguments(index).head
}
