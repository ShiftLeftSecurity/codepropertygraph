package io.shiftleft.queryprimitives.utils

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.queryprimitives.steps.Implicits.JavaIteratorDeco
import org.apache.tinkerpop.gremlin.structure.{Direction, Vertex}

import scala.collection.JavaConverters._

object ExpandTo {
  // The call receiver is for java always an object instance.
  // For languages which make use of function pointer this can also be the
  // pointer itself.
  def callReceiverOption(callNode: Vertex): Option[Vertex] = {
    callNode
      .vertices(Direction.OUT, EdgeTypes.RECEIVER)
      .asScala
      .toList
      .headOption
  }

  def callReceiver(callNode: Vertex): Vertex = {
    callReceiverOption(callNode).get
  }

  def typeCarrierToType(parameterNode: Vertex): Vertex = {
    parameterNode.vertices(Direction.OUT, EdgeTypes.EVAL_TYPE).nextChecked
  }

  def parameterToMethod(parameterNode: Vertex): Vertex = {
    parameterNode.vertices(Direction.IN, EdgeTypes.AST).nextChecked
  }

  def formalReturnToMethod(formalReturnNode: Vertex): Vertex = {
    formalReturnNode.vertices(Direction.IN, EdgeTypes.AST).nextChecked
  }

  def methodToFormalReturn(method: Vertex): Vertex = {
    method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .filter(_.isInstanceOf[nodes.MethodReturn])
      .next()
  }

  def expressionToMethod(expression: Vertex): Vertex = {
    expression.vertices(Direction.IN, EdgeTypes.CONTAINS).nextChecked
  }

  def localToMethod(local: Vertex): Vertex = {
    local.vertices(Direction.IN, EdgeTypes.AST).nextChecked
  }

  def hasModifier(methodNode: Vertex, modifierType: String): Boolean = {
    methodNode
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .exists(astChild =>
        astChild.label == NodeTypes.MODIFIER &&
          astChild.asInstanceOf[nodes.Modifier].modifierType == modifierType)
  }

  def astParent(expression: Vertex): Vertex = {
    expression.vertices(Direction.IN, EdgeTypes.AST).nextChecked
  }

  def callToCalledMethod(call: Vertex): Seq[nodes.Method] = {
    call
      .vertices(Direction.OUT, EdgeTypes.CALL)
      .asScala
      .map(methodInst =>
        methodInst.vertices(Direction.OUT, EdgeTypes.REF).nextChecked.asInstanceOf[nodes.Method])
      .toSeq
  }

  def methodToTypeDecl(method: Vertex): Option[Vertex] = {
    var typeDeclOption = method.vertices(Direction.IN, EdgeTypes.AST).asScala.toList.headOption
    while (typeDeclOption.isDefined && !typeDeclOption.get.isInstanceOf[nodes.TypeDecl]) {
      typeDeclOption =
        typeDeclOption.get.vertices(Direction.IN, EdgeTypes.AST).asScala.toList.headOption
    }
    typeDeclOption
  }

  def methodToFile(method: Vertex): Option[Vertex] = {
    var fileOption = method.vertices(Direction.IN, EdgeTypes.AST).asScala.toList.headOption
    while (fileOption.isDefined && !fileOption.get.isInstanceOf[nodes.File]) {
      fileOption = fileOption.get.vertices(Direction.IN, EdgeTypes.AST).asScala.toList.headOption
    }
    fileOption
  }
}
