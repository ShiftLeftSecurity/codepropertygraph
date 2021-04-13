package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, PropertyNames}
import io.shiftleft.cpgvalidator._
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.{Cardinality, KeysFact}
import io.shiftleft.cpgvalidator.facts.KeysFactsImporter
import overflowdb._

import scala.jdk.CollectionConverters._

class KeysValidator(errorRegistry: ValidationErrorRegistry) extends Validator {

  override def validate(notEnhancedCpg: Cpg): Boolean = {
    validateKeysFacts(notEnhancedCpg)
    errorRegistry.getErrorCount == 0
  }

  private def validateNode(node: Node, nodeKeyType: String, cardinality: Cardinality): Unit =
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
        notEnhancedCpg.graph.nodes(nodeType).asScala.foreach {
          case dstNode if dstNode.label != NodeTypes.UNKNOWN =>
            validateNode(dstNode, nodeKeyType, cardinality)
          case _ => // Do nothing. Hence, we skip UNKNOWN nodes
        }
    }
  }

  private def validateCardinalityOne(dstNode: Node, nodeKeyType: String): Unit = {
    val property = dstNode.propertyOption(new PropertyKey[Any](nodeKeyType))
    if (!property.isPresent) {
      // AST_PARENT_FULL_NAME and AST_PARENT_TYPE have cardinality one in our base.json but are
      // in fact optional iff the related information is provided via an AST edge
      if (nodeKeyType == PropertyNames.AST_PARENT_FULL_NAME || nodeKeyType == PropertyNames.AST_PARENT_TYPE) {
        val incomingAstVertices = dstNode.in(EdgeTypes.AST)
        if (incomingAstVertices.asScala.exists(
              v =>
                v.label() == NodeTypes.TYPE_DECL
                  || v.label() == NodeTypes.METHOD
                  || v.label() == NodeTypes.NAMESPACE_BLOCK)) return
      }
      errorRegistry.addError(KeyError(dstNode, nodeKeyType, Cardinality.One))
    }
  }

  private def validateCardinalityZeroOrOne(dstNode: Node, nodeKeyType: String): Unit = {
    val property = dstNode.propertyOption(new PropertyKey[Any](nodeKeyType))
    if (null == property) {
      errorRegistry.addError(
        KeyError(dstNode, nodeKeyType, Cardinality.ZeroOrOne)
      )
    }
  }

  private def validateCardinalityList(dstNode: Node, nodeKeyType: String): Unit = {
    val property = dstNode.property(new PropertyKey[List[_]](nodeKeyType))
    if (null == property) {
      errorRegistry.addError(KeyError(dstNode, nodeKeyType, Cardinality.List))
    }
  }

}
