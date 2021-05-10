package io.shiftleft.dataflowengineoss

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.language.dotextension.DdgNodeDot
import io.shiftleft.dataflowengineoss.language.nodemethods.{ExpressionMethods, TrackingPointMethods}
import io.shiftleft.semanticcpg.language.nodemethods.AstNodeMethods
import overflowdb.traversal.Traversal

package object language {

  implicit def trackingPointBaseMethodsQp[NodeType <: nodes.TrackingPoint](
      node: NodeType): TrackingPointMethods[NodeType] =
    new TrackingPointMethods(node)

  implicit def expressionMethods[NodeType <: nodes.Expression](node: NodeType): ExpressionMethods[NodeType] =
    new ExpressionMethods(node)

  implicit def toTrackingPoint[A, NodeType <: nodes.TrackingPointBase](a: A)(
      implicit f: A => Traversal[NodeType]): TrackingPoint =
    new TrackingPoint(f(a).cast[nodes.TrackingPoint])

  implicit def trackingPointToAstNodeMethods(node: nodes.TrackingPoint) =
    new AstNodeMethods(trackingPointToAstNode(node))

  implicit def trackingPointToAstNode(node: nodes.TrackingPoint): nodes.AstNode = {
    node.astNode
  }

  implicit def toDdgNodeDot[A](a: A)(implicit f: A => Traversal[nodes.Method]): DdgNodeDot =
    new DdgNodeDot(f(a))

}
