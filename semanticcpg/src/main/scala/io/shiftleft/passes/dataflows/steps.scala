package io.shiftleft.passes.dataflows

import gremlin.scala.Vertex
import gremlin.scala.dsl.Steps
import shapeless.HList
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.dataflows.steps.DataFlowObject


package object dataflows {

  implicit def toDataFlowObject[Labels <: HList](steps: Steps[nodes.DataFlowObject, Vertex, Labels]): DataFlowObject[Labels] =
    new DataFlowObject[Labels](steps.raw)
}
