package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes

import scala.jdk.CollectionConverters._
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

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
      v._cdgIn().asScala
    }
  }

  /**
    * Recursively determine all nodes which this
    * CFG node controls
    * */
  def controls: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._cdgOut().asScala
    }
  }

  /**
    * Recursively determine all nodes by which
    * this node is dominated
    * */
  def dominatedBy: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._dominateIn().asScala
    }
  }

  /**
    * Recursively determine all nodes which
    * are dominated by this node
    * */
  def dominates: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._dominateOut().asScala
    }
  }

  /**
    * Recursively determine all nodes by which
    * this node is post dominated
    * */
  def postDominatedBy: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._postDominateIn().asScala
    }
  }

  /**
    * Recursively determine all nodes which
    * are post dominated by this node
    * */
  def postDominates: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._postDominateOut().asScala
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

}
