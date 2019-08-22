package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import shapeless.HList

/**
  * Modifier, e.g., "static", "public",
  * */
class Modifier[Labels <: HList](raw: GremlinScala.Aux[nodes.Modifier, Labels])
    extends NodeSteps[nodes.Modifier, Labels](raw) {

  // TODO: This looks wrong. Modifier does not have a code field. It has a
  // ModifierType field.

  def code(): Steps[String, Labels] =
    new Steps[String, Labels](raw.value(NodeKeys.CODE))
}
