package io.shiftleft.passes.dataflows

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.dataflows.steps.TrackingPoint
import io.shiftleft.queryprimitives.steps.Steps
import io.shiftleft.queryprimitives.steps.Implicits._
import shapeless.HList

object Implicits {

  implicit def toTrackingPoint[X <% Steps[NodeType, Labels], NodeType <: nodes.TrackingPoint, Labels <: HList](
      steps: X): TrackingPoint[Labels] =
    new TrackingPoint[Labels](steps.raw.cast[nodes.TrackingPoint])
}
