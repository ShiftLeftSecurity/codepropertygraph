package io.shiftleft.cpgvalidator.validators.cfg

import gremlin.scala.Vertex
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.cpgvalidator.{CfgEdgeError, ValidationErrorRegistry}
import io.shiftleft.cpgvalidator.validators.Validator
import io.shiftleft.overflowdb.OdbGraph
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * This class checks whether all CFG edges do only point to CFG nodes in the same method.
  */
class NoLongJumpValidator(errorRegistry: ValidationErrorRegistry) extends Validator {
  override def validate(notEnhancedCpg: Cpg): Boolean = {
    notEnhancedCpg.graph.nodesByLabel(NodeTypes.METHOD).asScala.foreach(perMethod)
    errorRegistry.getErrorCount == 0
  }

  private def perMethod(method: Vertex): Unit = {
    val allAstNodesInMethod = mutable.Set.empty[Vertex]
    getTransitiveAstChildren(method, allAstNodesInMethod)

    allAstNodesInMethod.foreach { astNode =>
      val cfgSuccs = astNode.vertices(Direction.OUT, EdgeTypes.CFG).asScala
      cfgSuccs.foreach { cfgSucc =>
        if (!allAstNodesInMethod.contains(cfgSucc)) {
          errorRegistry.addError(new CfgEdgeError(astNode, cfgSucc, method, getMethodOfAstNode(cfgSucc)))
        }
      }

      val cfgPreds = astNode.vertices(Direction.IN, EdgeTypes.CFG).asScala
      cfgPreds.foreach { cfgPred =>
        if (!allAstNodesInMethod.contains(cfgPred)) {
          errorRegistry.addError(new CfgEdgeError(cfgPred, astNode, getMethodOfAstNode(cfgPred), method))
        }
      }

    }
  }

  private def getTransitiveAstChildren(node: Vertex, astChildren: mutable.Set[Vertex]): Unit = {
    astChildren.add(node)
    node.vertices(Direction.OUT, EdgeTypes.AST).forEachRemaining { astChild =>
      getTransitiveAstChildren(astChild, astChildren)
    }
  }

  private def getMethodOfAstNode(node: Vertex): Vertex = {
    if (node.label() == NodeTypes.METHOD) {
      node
    } else {
      getMethodOfAstNode(node.vertices(Direction.IN, EdgeTypes.AST).next)
    }
  }
}
