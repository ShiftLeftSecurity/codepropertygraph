package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.{NodeSteps, _}

class Modifier(val wrapped: NodeSteps[nodes.Modifier]) extends AnyVal {
  private def raw: GremlinScala[nodes.Modifier] = wrapped.raw

  def modifierType: Steps[String] =
    wrapped.map(_.modifierType)

}
