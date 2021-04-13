package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes, PropertyNames, NodeTypes}
import io.shiftleft.cpgvalidator.{CallReceiverError, ValidationErrorRegistry}
import overflowdb.Node

import scala.jdk.CollectionConverters._

/**
  * This class checks that
  * 1) all CALL nodes with dispatch type DYNAMIC_DISPATCH have an outgoing receiver edge
  * 2) all CALL nodes with dispatch type STATIC_DISPATCH have no outgoing receiver edge
  */
class CallReceiverValidator(errorRegistry: ValidationErrorRegistry) extends Validator {
  override def validate(notEnhancedCpg: Cpg): Boolean = {
    notEnhancedCpg.graph.nodes(NodeTypes.CALL).asScala.foreach(perCall)
    errorRegistry.getErrorCount == 0
  }

  private def perCall(call: Node): Unit = {
    val numOfOutgoingReceiverEdges = call.out(EdgeTypes.RECEIVER).asScala.size
    val dispatchType = call.property(PropertyNames.DISPATCH_TYPE).toString
    val arg0 = call.out(EdgeTypes.AST).asScala.find {
      case node if node.propertyOption(PropertyNames.ARGUMENT_INDEX).isPresent =>
        node.property(PropertyNames.ARGUMENT_INDEX) == 0
    }
    dispatchType match {
      case DispatchTypes.DYNAMIC_DISPATCH
          if numOfOutgoingReceiverEdges != 1
          // TODO: Remove this once the ReceiverEdgePass is gone
          // As we still have the ReceiverEdgePass in codepropertygraph this is valid as well
            && arg0.isEmpty =>
        errorRegistry.addError(CallReceiverError(call, dispatchType))
      case DispatchTypes.STATIC_DISPATCH if numOfOutgoingReceiverEdges != 0 =>
        errorRegistry.addError(CallReceiverError(call, dispatchType))
      case _ => // this is fine
    }
  }
}
