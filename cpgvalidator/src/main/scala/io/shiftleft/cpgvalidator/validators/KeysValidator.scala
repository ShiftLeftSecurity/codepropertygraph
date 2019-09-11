package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator._
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.{Cardinality, KeysFact}
import io.shiftleft.cpgvalidator.facts.KeysFactsImporter
import gremlin.scala._
import org.apache.tinkerpop.gremlin.structure.VertexProperty

import scala.collection.JavaConverters._

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
    val keysFacts = new KeysFactsImporter().loadFacts
    keysFacts.foreach {
      case KeysFact(nodeType, nodeKeyType, cardinality) =>
        notEnhancedCpg.scalaGraph.V
          .hasLabel(nodeType)
          .sideEffectWithTraverser { traverser =>
            val dstNode = traverser.get
            validateNode(dstNode, nodeKeyType, cardinality)
          }
          .iterate()
    }
  }

  private def validateCardinalityOne(dstNode: Vertex, nodeKeyType: String): Unit = {
    val property = dstNode.property(nodeKeyType)
    if (null == property || !property.isPresent) {
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
