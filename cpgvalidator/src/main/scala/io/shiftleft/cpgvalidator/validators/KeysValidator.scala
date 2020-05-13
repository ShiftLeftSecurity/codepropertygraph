package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator._
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.{Cardinality, KeysFact}
import io.shiftleft.cpgvalidator.facts.KeysFactsImporter
import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, NodeKeys}
import org.apache.tinkerpop.gremlin.structure.VertexProperty
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.jdk.CollectionConverters._

class KeysValidator(errorRegistry: ValidationErrorRegistry) extends Validator {

  override def validate(notEnhancedCpg: Cpg): Boolean = {
    validateKeysFacts(notEnhancedCpg)
    errorRegistry.getErrorCount == 0
  }

  private def validateNode(node: Vertex, nodeKeyType: String, cardinality: Cardinality): Unit =
    cardinality match {
      case Cardinality.One =>
        validateCardinalityOne(node, nodeKeyType)
      case Cardinality.ZeroOrOne =>
        validateCardinalityZeroOrOne(node, nodeKeyType)
      case Cardinality.List =>
        validateCardinalityList(node, nodeKeyType)
    }

  private def validateKeysFacts(notEnhancedCpg: Cpg): Unit = {
    new KeysFactsImporter().loadFacts.foreach {
      case KeysFact(nodeType, nodeKeyType, cardinality) =>
        notEnhancedCpg.graph.nodesByLabel(nodeType).asScala.foreach {
          case dstNode if dstNode.label != NodeTypes.UNKNOWN =>
            validateNode(dstNode, nodeKeyType, cardinality)
          case _ => // Do nothing. Hence, we skip UNKNOWN nodes
        }
    }
  }

  private def validateCardinalityOne(dstNode: Vertex, nodeKeyType: String): Unit = {
    val property = dstNode.property(nodeKeyType)
    if (null == property || !property.isPresent) {
      // AST_PARENT_FULL_NAME and AST_PARENT_TYPE have cardinality one in our base.json but are
      // in fact optional iff they are provided via an actual edge
      if (nodeKeyType == NodeKeys.AST_PARENT_FULL_NAME || nodeKeyType == NodeKeys.AST_PARENT_TYPE) {
        val inheritsFrom = dstNode.vertices(Direction.OUT, EdgeTypes.INHERITS_FROM)
        if (inheritsFrom.asScala.nonEmpty) return
      }
      errorRegistry.addError(KeyError(dstNode, nodeKeyType, Cardinality.One))
    }
  }

  private def validateCardinalityZeroOrOne(dstNode: Vertex, nodeKeyType: String): Unit = {
    val property = dstNode.property(nodeKeyType)
    if (null == property || !property.isInstanceOf[VertexProperty[_]]) {
      errorRegistry.addError(
        KeyError(dstNode, nodeKeyType, Cardinality.ZeroOrOne)
      )
    }
  }

  private def validateCardinalityList(dstNode: Vertex, nodeKeyType: String): Unit = {
    val properties = dstNode.properties(nodeKeyType).asScala.toList
    if (null == properties || !properties.isInstanceOf[List[_]]) {
      errorRegistry.addError(KeyError(dstNode, nodeKeyType, Cardinality.List))
    }
  }

}
