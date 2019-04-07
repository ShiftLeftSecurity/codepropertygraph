package io.shiftleft.passes.dataflows

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.dataflows.steps.TrackingPoint
import io.shiftleft.queryprimitives.steps.Steps
import shapeless.HList

package object steps {

  implicit def toTrackingPoint[Labels <: HList](steps: Steps[nodes.TrackingPoint, Labels]): TrackingPoint[Labels] =
    new TrackingPoint[Labels](steps.raw)

}
