package io.shiftleft.dataflowengineoss

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.language.dotextension.DdgNodeDot
import io.shiftleft.dataflowengineoss.language.nodemethods.{ExpressionMethods, ExtendedCfgNodeMethods}
import overflowdb.traversal.Traversal

package object language {

  implicit def cfgNodeToMethodsQp[NodeType <: nodes.CfgNode](node: NodeType): ExtendedCfgNodeMethods[NodeType] =
    new ExtendedCfgNodeMethods(node)

  implicit def expressionMethods[NodeType <: nodes.Expression](node: NodeType): ExpressionMethods[NodeType] =
    new ExpressionMethods(node)

  implicit def toExtendedCfgNode[A, NodeType <: nodes.CfgNode](a: A)(
      implicit f: A => Traversal[NodeType]): ExtendedCfgNode =
    new ExtendedCfgNode(f(a).cast[nodes.CfgNode])

  implicit def toDdgNodeDot[A](a: A)(implicit f: A => Traversal[nodes.Method]): DdgNodeDot =
    new DdgNodeDot(f(a))

}
