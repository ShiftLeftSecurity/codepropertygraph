package io.shiftleft.dataflowengine

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengine.language.nodemethods.TrackingPointMethods
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.nodemethods.AstNodeMethods
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode

package object language {

  implicit def trackingPointBaseMethodsQp[NodeType <: nodes.TrackingPointBase](node: NodeType): TrackingPointMethods =
    new TrackingPointMethods(node.asInstanceOf[nodes.TrackingPoint])

  implicit def toTrackingPoint[NodeType <: nodes.TrackingPointBase](steps: Steps[NodeType]): TrackingPoint =
    new TrackingPoint(steps.raw.cast[nodes.TrackingPoint])

  implicit def trackingPointToAstNodeMethods(node: nodes.TrackingPoint) =
    new AstNodeMethods(trackingPointToAstNode(node))

  private def trackingPointToAstNode(node: nodes.TrackingPoint): nodes.AstNode = node match {
    case n: nodes.AstNode               => n
    case n: nodes.DetachedTrackingPoint => n.cfgNode
    case _                              => ??? //TODO markus/fabs?
  }

  implicit def trackingPointToAstBase(trav: TrackingPoint): AstNode = new AstNode(trav.map(trackingPointToAstNode).raw)

}
