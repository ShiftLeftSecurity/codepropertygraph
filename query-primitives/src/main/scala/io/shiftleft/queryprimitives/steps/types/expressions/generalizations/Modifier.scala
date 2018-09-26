package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala._
import gremlin.scala.dsl.{Converter, Steps}
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import shapeless.HList

/**
  * Modifier, e.g., "static", "public",
  * */
class Modifier[Labels <: HList](raw: GremlinScala[Vertex]) extends CpgSteps[nodes.Modifier, Labels](raw) {

  def code(): Steps[String, String, Labels] =
    new Steps[String, String, Labels](raw.value(NodeKeys.CODE))
}
