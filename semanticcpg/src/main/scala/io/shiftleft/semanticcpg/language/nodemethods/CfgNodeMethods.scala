package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.semanticcpg.NodeExtension
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class CfgNodeMethods(val node: CfgNode) extends NodeExtension {

  /**
    * Textual representation of CFG node
    * */
  def repr: String =
    node match {
      case method: MethodBase                         => method.name
      case methodReturn: MethodReturnBase             => methodReturn.code
      case expr: Expression                           => expr.code
      case call: CallRepr if !call.isInstanceOf[Call] => call.code
    }

  /**
    * Recursively determine all nodes on which this
    * CFG node is control-dependent.
    * */
  def controlledBy: Traversal[CfgNode] = {
    expandExhaustively { v =>
      v._cdgIn.asScala
    }
  }

  /**
    * Recursively determine all nodes which this
    * CFG node controls
    * */
  def controls: Traversal[CfgNode] = {
    expandExhaustively { v =>
      v._cdgOut.asScala
    }
  }

  /**
    * Recursively determine all nodes by which
    * this node is dominated
    * */
  def dominatedBy: Traversal[CfgNode] = {
    expandExhaustively { v =>
      v._dominateIn.asScala
    }
  }

  /**
    * Recursively determine all nodes which
    * are dominated by this node
    * */
  def dominates: Traversal[CfgNode] = {
    expandExhaustively { v =>
      v._dominateOut.asScala
    }
  }

  /**
    * Recursively determine all nodes by which
    * this node is post dominated
    * */
  def postDominatedBy: Traversal[CfgNode] = {
    expandExhaustively { v =>
      v._postDominateIn.asScala
    }
  }

  /**
    * Recursively determine all nodes which
    * are post dominated by this node
    * */
  def postDominates: Traversal[CfgNode] = {
    expandExhaustively { v =>
      v._postDominateOut.asScala
    }
  }

  private def expandExhaustively(expand: CfgNode => Iterator[StoredNode]): Traversal[CfgNode] = {
    var controllingNodes = List.empty[CfgNode]
    var visited = Set.empty + node
    var worklist = node :: Nil

    while (worklist.nonEmpty) {
      val vertex = worklist.head
      worklist = worklist.tail

      expand(vertex).foreach {
        case controllingNode: CfgNode =>
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
