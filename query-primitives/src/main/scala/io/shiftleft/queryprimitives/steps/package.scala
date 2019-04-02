package io.shiftleft.queryprimitives

import io.shiftleft.codepropertygraph.generated.nodes
import scala.language.implicitConversions

/**
  Steps for traversing the code property graph

  All traversal start at io.shiftleft.queryprimitives.starters.Cpg.
  */
package object steps {
  implicit def trackingPointMethodsQp(node: nodes.TrackingPoint): TrackingPointMethods =
    new TrackingPointMethods(node)

  implicit def trackingPointBaseMethodsQp(node: nodes.TrackingPointBase): TrackingPointMethods =
    new TrackingPointMethods(node.asInstanceOf[nodes.TrackingPoint])

  implicit def withMethodMethodsQp(node: nodes.WithinMethod): WithinMethodMethods =
    new WithinMethodMethods(node)

  implicit def cfgNodeMethodsQp(node: nodes.CfgNode): CfgNodeMethods =
    new CfgNodeMethods(node)
}
