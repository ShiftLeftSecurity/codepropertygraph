package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.expressions.Call
import shapeless.HList

class CfgNode[Labels <: HList](raw: GremlinScala.Aux[nodes.CfgNode, Labels])
  extends NodeSteps[nodes.CfgNode, Labels](raw) {

  /**
  Cast to call if applicable
    */
  def call: Call[Labels] =
    new Call[Labels](raw.hasLabel(NodeTypes.CALL).cast[nodes.Call])

}
