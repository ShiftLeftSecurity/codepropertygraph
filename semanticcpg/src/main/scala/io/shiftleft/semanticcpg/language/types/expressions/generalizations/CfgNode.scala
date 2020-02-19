package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.ExpandTo

class CfgNode[A <: nodes.CfgNode](val wrapped: NodeSteps[A]) extends AnyVal {
  private def raw: GremlinScala[A] = wrapped.raw

  /**
  Traverse to enclosing method
    */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(
      raw
        .map {
          case method: nodes.Method =>
            method
          case methodReturn: nodes.MethodReturn =>
            ExpandTo.methodReturnToMethod(methodReturn)
          case expression =>
            ExpandTo.expressionToMethod(expression)
        }
        .cast[nodes.Method])

}
