package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class Binding(val wrapped: NodeSteps[nodes.Binding]) extends AnyVal {
  private def raw: GremlinScala[nodes.Binding] = wrapped.raw

  /**
    * Traverse to the method bound by this method binding.
    */
  def boundMethod: NodeSteps[nodes.Method] =
    new NodeSteps(raw.out(EdgeTypes.REF).cast[nodes.Method])

  /**
    * Traverse to the method bound by this method binding.
    */
  def bindingTypeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(raw.in(EdgeTypes.BINDS).cast[nodes.TypeDecl])
}
