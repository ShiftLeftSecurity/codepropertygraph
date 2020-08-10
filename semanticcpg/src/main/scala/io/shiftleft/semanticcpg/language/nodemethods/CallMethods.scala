package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._

class CallMethods(val node: nodes.Call) extends AnyVal {
  def argumentOption(index: Int): Option[nodes.Expression] =
    node._argumentOut.asScala
      .collectFirst { case expr: nodes.Expression if expr.argumentIndex == index => expr }

  def argument(index: Int): nodes.Expression = argumentOption(index).get
}
