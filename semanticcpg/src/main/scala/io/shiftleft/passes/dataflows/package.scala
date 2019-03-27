// TODO markus port to TrackingPoint
// package io.shiftleft.passes

// import gremlin.scala.Vertex
// import io.shiftleft.passes.dataflows.steps.DataFlowObject
// import io.shiftleft.codepropertygraph.generated.nodes
// import io.shiftleft.queryprimitives.steps.Steps
// import io.shiftleft.queryprimitives.steps.Implicits._
// import scala.language.implicitConversions
// import shapeless.HList

// package object dataflows {
//   implicit def toDataFlowObject[X <% Steps[NodeType, Labels], NodeType <: nodes.DataFlowObject, Labels <: HList](
//       steps: X): DataFlowObject[Labels] =
//     new DataFlowObject[Labels](steps.raw.cast[nodes.DataFlowObject])

// }
