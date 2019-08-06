package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.expressions.Call
import io.shiftleft.queryprimitives.steps.types.structure.Method
import io.shiftleft.queryprimitives.utils.ExpandTo
import org.apache.tinkerpop.gremlin.structure.Direction
import shapeless.HList

class CfgNode[Labels <: HList](raw: GremlinScala.Aux[nodes.CfgNode, Labels])
    extends NodeSteps[nodes.CfgNode, Labels](raw)
    with AstNodeBase[nodes.CfgNode, Labels] {

  /**
  Traverse to enclosing method
    */
  def method: Method[Labels] = {
    new Method[Labels](
      raw
        .map {
          case method: nodes.Method =>
            method
          case methodReturn: nodes.MethodReturn =>
            ExpandTo.formalReturnToMethod(methodReturn)
          case expression =>
            ExpandTo.expressionToMethod(expression)
        }
        .cast[nodes.Method])
  }

}
