package io.shiftleft.dataflowengine

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengine.language.nodemethods.TrackingPointMethods
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.nodemethods.AstNodeMethods

package object language {

  implicit def trackingPointBaseMethodsQp[NodeType <: nodes.TrackingPointBase](node: NodeType): TrackingPointMethods =
    new TrackingPointMethods(node.asInstanceOf[nodes.TrackingPoint])

  implicit def toTrackingPoint[NodeType <: nodes.TrackingPointBase](steps: Steps[NodeType]): TrackingPoint =
    new TrackingPoint(steps.raw.cast[nodes.TrackingPoint])

}
