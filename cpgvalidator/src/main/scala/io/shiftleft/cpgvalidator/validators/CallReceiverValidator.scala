package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes, NodeKeyNames, NodeTypes}
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
    val dispatchType = call.property(NodeKeyNames.DISPATCH_TYPE).toString
    dispatchType match {
      case DispatchTypes.DYNAMIC_DISPATCH if numOfOutgoingReceiverEdges != 1 =>
        errorRegistry.addError(CallReceiverError(call, dispatchType))
      case DispatchTypes.STATIC_DISPATCH if numOfOutgoingReceiverEdges != 0 =>
        errorRegistry.addError(CallReceiverError(call, dispatchType))
      case _ => // this is fine
    }
  }
}
