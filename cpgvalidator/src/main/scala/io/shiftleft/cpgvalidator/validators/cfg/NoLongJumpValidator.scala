package io.shiftleft.cpgvalidator.validators.cfg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.cpgvalidator.{CfgEdgeError, ValidationErrorRegistry}
import io.shiftleft.cpgvalidator.validators.Validator
import overflowdb.Node

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * This class checks whether all CFG edges do only point to CFG nodes in the same method.
  */
class NoLongJumpValidator(errorRegistry: ValidationErrorRegistry) extends Validator {
  override def validate(notEnhancedCpg: Cpg): Boolean = {
    notEnhancedCpg.graph.nodes(NodeTypes.METHOD).asScala.foreach(perMethod)
    errorRegistry.getErrorCount == 0
  }

  private def perMethod(method: Node): Unit = {
    val allAstNodesInMethod = mutable.Set.empty[Node]
    getTransitiveAstChildren(method, allAstNodesInMethod)

    allAstNodesInMethod.foreach { astNode =>
      val cfgSuccs = astNode.out(EdgeTypes.CFG).asScala
      cfgSuccs.foreach { cfgSucc =>
        if (!allAstNodesInMethod.contains(cfgSucc)) {
          errorRegistry.addError(CfgEdgeError(astNode, cfgSucc, method, getMethodOfAstNode(cfgSucc)))
        }
      }

      val cfgPreds = astNode.in(EdgeTypes.CFG).asScala
      cfgPreds.foreach { cfgPred =>
        if (!allAstNodesInMethod.contains(cfgPred)) {
          errorRegistry.addError(CfgEdgeError(cfgPred, astNode, getMethodOfAstNode(cfgPred), method))
        }
      }

    }
  }

  private def getTransitiveAstChildren(node: Node, astChildren: mutable.Set[Node]): Unit = {
    astChildren.add(node)
    node.out(EdgeTypes.AST).forEachRemaining { astChild =>
      getTransitiveAstChildren(astChild, astChildren)
    }
  }

  private def getMethodOfAstNode(node: Node): Node = {
    if (node.label == NodeTypes.METHOD) {
      node
    } else {
      getMethodOfAstNode(node.in(EdgeTypes.AST).next)
    }
  }
}
