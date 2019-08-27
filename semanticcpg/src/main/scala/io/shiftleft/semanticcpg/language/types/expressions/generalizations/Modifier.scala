package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

/**
  * Modifier, e.g., "static", "public",
  * */
class Modifier(raw: GremlinScala[nodes.Modifier]) extends NodeSteps[nodes.Modifier](raw) {

  // TODO: This looks wrong. Modifier does not have a code field. It has a
  // ModifierType field.

  def code(): Steps[String] =
    new Steps[String](raw.value(NodeKeys.CODE))
}
