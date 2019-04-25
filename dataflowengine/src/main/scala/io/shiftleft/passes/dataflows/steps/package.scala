package io.shiftleft.passes.dataflows

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.dataflows.steps._
import io.shiftleft.queryprimitives.steps.Steps
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import shapeless.HList

/** extend steps without the need for additional implicit imports */
package object steps {

  // TODO MP: rather use `start` mechanism?
  // alternative: move to `nodes` package object?
  implicit def trackingPointBaseMethodsQp(node: nodes.TrackingPointBase): TrackingPointMethods =
    new TrackingPointMethods(node.asInstanceOf[nodes.TrackingPoint])

  implicit def toTrackingPoint[NodeType <: nodes.TrackingPoint, Labels <: HList](
      steps: Steps[NodeType, Labels]): TrackingPoint[Labels] =
    new TrackingPoint[Labels](steps.raw.cast[nodes.TrackingPoint])
}
