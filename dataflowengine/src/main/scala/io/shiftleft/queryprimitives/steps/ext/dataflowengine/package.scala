package io.shiftleft.queryprimitives.steps.ext

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.dataflows.steps.{TrackingPoint, TrackingPointMethods}
import io.shiftleft.queryprimitives.steps.Steps
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import shapeless.HList

package object dataflowengine {
  // TODO MP: rather use `start` mechanism?
  // alternative: move to `nodes` package object?
  implicit def trackingPointBaseMethodsQp(node: nodes.TrackingPointBase): TrackingPointMethods =
    new TrackingPointMethods(node.asInstanceOf[nodes.TrackingPoint])

  implicit def toTrackingPoint[NodeType <: nodes.TrackingPointBase, Labels <: HList](
      steps: Steps[NodeType, Labels]): TrackingPoint[Labels] =
    new TrackingPoint[Labels](steps.raw.cast[nodes.TrackingPoint])
}
