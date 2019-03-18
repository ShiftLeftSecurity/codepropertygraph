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
      .filter(_.label == NodeTypes.METHOD_RETURN)
      .next()
  }

  def methodToOutParameters(method: Vertex): Seq[Vertex] = {
    method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .filter(_.label == NodeTypes.METHOD_PARAMETER_OUT)
      .toSeq
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
          astChild.value2(NodeKeys.MODIFIER_TYPE) == modifierType)
  }

  def astParent(expression: Vertex): Vertex = {
    expression.vertices(Direction.IN, EdgeTypes.AST).nextChecked
  }

  def callToCalledMethod(call: Vertex): Seq[Vertex] = {
    call
      .vertices(Direction.OUT, EdgeTypes.CALL)
      .asScala
      .map(methodInst => methodInst.vertices(Direction.OUT, EdgeTypes.REF).nextChecked)
      .toSeq
  }

  def methodToTypeDecl(method: Vertex): Option[Vertex] = {
    var typeDeclOption = method.vertices(Direction.IN, EdgeTypes.AST).asScala.toList.headOption
    while (typeDeclOption.isDefined && typeDeclOption.get.label != NodeTypes.TYPE_DECL) {
      typeDeclOption = typeDeclOption.get.vertices(Direction.IN, EdgeTypes.AST).asScala.toList.headOption
    }
    typeDeclOption
  }

  def methodToFile(method: Vertex): Option[Vertex] = {
    var fileOption = method.vertices(Direction.IN, EdgeTypes.AST).asScala.toList.headOption
    while (fileOption.isDefined && fileOption.get.label != NodeTypes.FILE) {
      fileOption = fileOption.get.vertices(Direction.IN, EdgeTypes.AST).asScala.toList.headOption
    }
    fileOption
  }

  def allCfgNodesOfMethod(method: Vertex): Set[Vertex] = {
    var worklist = method :: Nil

    var cfgNodes = Set[Vertex]()

    while (worklist.nonEmpty) {
      val cfgNode = worklist.head
      worklist = worklist.tail
      cfgNodes += cfgNode

      val newCfgSuccessors = cfgNode
        .vertices(Direction.OUT, EdgeTypes.CFG)
        .asScala
        .filter(cfgSuccessor => !cfgNodes.contains(cfgSuccessor))
        .toList

      worklist ++= newCfgSuccessors
    }
    cfgNodes
  }

  def reference(node: Vertex): Option[Vertex] = {
    val iterator = node.vertices(Direction.OUT, EdgeTypes.REF).asScala
    if (iterator.hasNext) {
      Some(iterator.next)
    } else {
      None
    }
  }

}
