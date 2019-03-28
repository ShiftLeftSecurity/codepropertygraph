package io.shiftleft.passes

import io.shiftleft.passes.dataflows.steps.TrackingPoint
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.Steps
import io.shiftleft.queryprimitives.steps.Implicits._
import scala.language.implicitConversions

import shapeless.HList

package object dataflows {
  implicit def toTrackingPoint[X <% Steps[NodeType, Labels],
  NodeType <: nodes.TrackingPoint,
  Labels <: HList](steps: X): TrackingPoint[Labels] =
    new TrackingPoint[Labels](steps.raw.cast[nodes.TrackingPoint])
}
