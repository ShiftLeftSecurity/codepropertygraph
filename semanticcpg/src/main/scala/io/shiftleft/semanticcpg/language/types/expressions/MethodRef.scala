package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.Method

class MethodRef(raw: GremlinScala[nodes.MethodRef]) extends NodeSteps[nodes.MethodRef](raw) {

  /**
    * Traverse to referenced method.
    * */
  def referencedMethod: NodeSteps[nodes.Method] =
    new NodeSteps(raw.out(EdgeTypes.REF).cast[nodes.Method])
}
