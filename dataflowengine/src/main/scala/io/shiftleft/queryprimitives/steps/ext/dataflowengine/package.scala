package io.shiftleft.queryprimitives.steps.ext

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.dataflows.steps.{TrackingPoint, TrackingPointMethods}
import io.shiftleft.queryprimitives.steps.Steps
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import shapeless.HList

package object dataflowengine {

  // TODO MP: rather use `start` mechanism?
  // alternative: move to `nodes` package object?

  // TODO fabs: I'm wondering the same. I think the reason Markus is doing it
  // like this is performance of `.start`. We should get some more insights
  // into performance of `.start` vs this mechanism and then go for one or
  // the other.

  implicit def trackingPointBaseMethodsQp(node: nodes.TrackingPointBase): TrackingPointMethods =
    new TrackingPointMethods(node.asInstanceOf[nodes.TrackingPoint])

  implicit def toTrackingPoint[NodeType <: nodes.TrackingPointBase, Labels <: HList](
      steps: Steps[NodeType, Labels]): TrackingPoint[Labels] =
    new TrackingPoint[Labels](steps.raw.cast[nodes.TrackingPoint])
}
