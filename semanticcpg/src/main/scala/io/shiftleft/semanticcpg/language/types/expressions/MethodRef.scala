package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class MethodRef[A <: nodes.MethodRef](raw: GremlinScala[A]) extends NodeSteps[A](raw) {

  /**
    * Traverse to referenced method.
    * */
  def referencedMethod: NodeSteps[nodes.Method] =
    new NodeSteps(raw.out(EdgeTypes.REF).cast[nodes.Method])
}
