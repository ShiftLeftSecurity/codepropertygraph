package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.ExpandTo

class CfgNode(raw: GremlinScala[nodes.CfgNode]) extends NodeSteps[nodes.CfgNode](raw) {

  /**
  Traverse to enclosing method
    */
  def method: NodeSteps[nodes.Method] = {
    new NodeSteps(raw.map {
      case method: nodes.Method =>
        method
      case methodReturn: nodes.MethodReturn =>
        ExpandTo.methodReturnToMethod(methodReturn)
      case expression =>
        ExpandTo.expressionToMethod(expression)
    }.cast[nodes.Method])
  }

}
