package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class CfgNodeMethods(val node: nodes.CfgNode) extends AnyVal {

  /**
    * Textual representation of CFG node
    * */
  def repr: String =
    node match {
      case method: nodes.MethodBase             => method.name
      case methodReturn: nodes.MethodReturnBase => methodReturn.code
      case expr: nodes.Expression               => expr.code
      case call: nodes.ImplicitCallBase         => call.code
    }

  /**
    * Recursively determine all nodes on which this
    * CFG node is control-dependent.
    * */
  def controlledBy: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._cdgIn.asScala
    }
  }

  /**
    * Recursively determine all nodes which this
    * CFG node controls
    * */
  def controls: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._cdgOut.asScala
    }
  }

  /**
    * Recursively determine all nodes by which
    * this node is dominated
    * */
  def dominatedBy: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._dominateIn.asScala
    }
  }

  /**
    * Recursively determine all nodes which
    * are dominated by this node
    * */
  def dominates: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._dominateOut.asScala
    }
  }

  /**
    * Recursively determine all nodes by which
    * this node is post dominated
    * */
  def postDominatedBy: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._postDominateIn.asScala
    }
  }

  /**
    * Recursively determine all nodes which
    * are post dominated by this node
    * */
  def postDominates: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._postDominateOut.asScala
    }
  }

  private def expandExhaustively(expand: nodes.CfgNode => Iterator[nodes.StoredNode]): Traversal[nodes.CfgNode] = {
    var controllingNodes = List.empty[nodes.CfgNode]
    var visited = Set.empty + node
    var worklist = node :: Nil

    while (worklist.nonEmpty) {
      val vertex = worklist.head
      worklist = worklist.tail

      expand(vertex).foreach {
        case controllingNode: nodes.CfgNode =>
          if (!visited.contains(controllingNode)) {
            visited += controllingNode
            controllingNodes = controllingNode :: controllingNodes
            worklist = controllingNode :: worklist
          }
      }
    }
    controllingNodes
  }

  def statement: nodes.CfgNode =
    statementInternal(node, _.parentExpression.get)

  @scala.annotation.tailrec
  private def statementInternal(node: nodes.CfgNode,
                                parentExpansion: nodes.Expression => nodes.Expression): nodes.CfgNode = {

    node match {
      case node: nodes.Identifier => parentExpansion(node)
      case node: nodes.MethodRef  => parentExpansion(node)
      case node: nodes.TypeRef    => parentExpansion(node)
      case node: nodes.Literal    => parentExpansion(node)

      case node: nodes.MethodParameterIn => node.method

      case node: nodes.MethodParameterOut =>
        node.method.methodReturn

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
        statementInternal(lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }

  private def lastExpressionInBlock(block: nodes.Block): Option[nodes.Expression] =
    block._astOut.asScala
      .collect {
        case node: nodes.Expression if !node.isInstanceOf[nodes.Local] && !node.isInstanceOf[nodes.Method] => node
      }
      .toVector
      .sortBy(_.order)
      .lastOption

}
