package io.shiftleft.semanticcpg.language.nodemethods

import gremlin.scala._
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.jdk.CollectionConverters._

// Adding a `private` here triggers an exception in the compiler.
object TrackingPointMethodsBase {
  def lastExpressionInBlock(block: nodes.Block): Option[nodes.Expression] = {
    block
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .filterNot(vertex => vertex.isInstanceOf[nodes.Local] || vertex.isInstanceOf[nodes.Method])
      .toVector
      .sortBy(_.value2(NodeKeys.ORDER))
      .lastOption
      .asInstanceOf[Option[nodes.Expression]]
  }
}

object TrackingPointToCfgNode {
  def apply(node: nodes.TrackingPointBase): nodes.CfgNode = {
    applyInternal(node, _.parentExpression)
  }

  private def applyInternal(node: nodes.TrackingPointBase,
                            parentExpansion: nodes.Expression => nodes.Expression): nodes.CfgNode = {

    node match {
      case node: nodes.Identifier => parentExpansion(node)
      case node: nodes.MethodRef  => parentExpansion(node)
      case node: nodes.Literal    => parentExpansion(node)

      case node: nodes.MethodParameterIn =>
        ExpandTo.parameterInToMethod(node)

      case node: nodes.MethodParameterOut =>
        ExpandTo.parameterOutToMethod(node).methodReturn

      case node: nodes.Call if MemberAccess.isGenericMemberAccessName(node.name) =>
        parentExpansion(node)

      case node: nodes.Call         => node
      case node: nodes.ImplicitCall => node
      case node: nodes.MethodReturn => node
      case block: nodes.Block       =>
        // Just taking the lastExpressionInBlock is not quite correct because a BLOCK could have
        // different return expressions. So we would need to expand via CFG.
        // But currently the frontends do not even put the BLOCK into the CFG so this is the best
        // we can do.
        applyInternal(TrackingPointMethodsBase.lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }
}
