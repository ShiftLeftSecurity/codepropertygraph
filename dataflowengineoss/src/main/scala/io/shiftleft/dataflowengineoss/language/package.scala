package io.shiftleft.dataflowengineoss

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.language.dotextension.DdgNodeDot
import io.shiftleft.dataflowengineoss.language.nodemethods.TrackingPointMethods
import io.shiftleft.semanticcpg.language.nodemethods.AstNodeMethods
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode
import overflowdb.traversal.Traversal

package object language {

  implicit def trackingPointBaseMethodsQp[NodeType <: nodes.TrackingPoint](
      node: NodeType): TrackingPointMethods[NodeType] =
    new TrackingPointMethods(node)

  implicit def toTrackingPoint[NodeType <: nodes.TrackingPointBase](traversal: Traversal[NodeType]): TrackingPoint =
    new TrackingPoint(traversal.cast[nodes.TrackingPoint])

  implicit def trackingPointToAstNodeMethods(node: nodes.TrackingPoint) =
    new AstNodeMethods(trackingPointToAstNode(node))

  private def trackingPointToAstNode(node: nodes.TrackingPoint): nodes.AstNode = node match {
    case n: nodes.AstNode               => n
    case n: nodes.DetachedTrackingPoint => n.cfgNode
    case _                              => ??? //TODO markus/fabs?
  }

  implicit def trackingPointToAstBase(trav: Traversal[nodes.TrackingPoint]): AstNode[nodes.AstNode] =
    new AstNode(trav.map(trackingPointToAstNode))

  implicit def toDdgNodeDot(trav: Traversal[nodes.Method]): DdgNodeDot =
    new DdgNodeDot(trav)

}
