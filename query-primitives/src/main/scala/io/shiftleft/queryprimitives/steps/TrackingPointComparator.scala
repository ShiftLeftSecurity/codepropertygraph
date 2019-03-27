package io.shiftleft.queryprimitives.steps

import java.util.Objects

import io.shiftleft.codepropertygraph.generated.nodes

class TrackingPointComparator(trackingPoint: nodes.TrackingPoint) {
  override def hashCode(): Int = {
    Objects.hashCode(trackingPoint.cfgNode, trackingPoint.trackedBase)
  }

  override def equals(other: Any): Boolean = {
    other match {
      case otherTrackingPoint: nodes.TrackingPoint =>
        trackingPoint.cfgNode == otherTrackingPoint.cfgNode &&
        trackingPoint.trackedBase == otherTrackingPoint.trackedBase
      case _ => false
    }
  }

}
