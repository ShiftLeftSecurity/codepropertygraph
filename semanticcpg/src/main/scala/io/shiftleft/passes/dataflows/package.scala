package io.shiftleft.passes

import gremlin.scala.Vertex
import gremlin.scala.dsl.Steps
import io.shiftleft.passes.dataflows.steps.DataFlowObject
import io.shiftleft.codepropertygraph.generated.nodes
import shapeless.HList

package object dataflows {
  implicit def toDataFlowObject[X <% Steps[NodeType, Vertex, Labels], NodeType <: nodes.DataFlowObject, Labels <: HList](
      steps: X): DataFlowObject[Labels] =
    new DataFlowObject[Labels](steps.raw)

}
