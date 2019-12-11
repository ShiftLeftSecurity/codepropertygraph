package io.shiftleft.semanticcpg.utils

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated._
import org.apache.tinkerpop.gremlin.structure.{Direction, Vertex}
import io.shiftleft.Implicits.JavaIteratorDeco

import scala.annotation.tailrec
import scala.jdk.CollectionConverters._

// TODO This object is problematic. While it offers a few utility methods
// on nodes, these are all lumped together into one object, and they are
// unavailable in the query language. We should rather extend the query
// language appropriately.

object ExpandTo {
  // For java, the call receiver is always an object instance.
  // For languages which make use of function pointers, this can also be the
  // pointer itself.
  def callReceiverOption(callNode: Vertex): Option[nodes.StoredNode] = {
    callNode
      .asInstanceOf[nodes.StoredNode]
      ._receiverOut
      .asScala
      .toList
      .headOption
  }

  def callReceiver(callNode: Vertex): nodes.StoredNode = {
    callReceiverOption(callNode).get
  }

  def callArguments(callNode: Vertex): Iterator[nodes.Expression] = {
    callNode.asInstanceOf[nodes.StoredNode]._argumentOut.asScala.map(_.asInstanceOf[nodes.Expression])
  }

  def argumentToCallOrReturn(argument: Vertex): nodes.Expression = {
    val parent = argument.asInstanceOf[nodes.StoredNode]._astIn.nextChecked
    parent match {
      case call: nodes.Call if MemberAccess.isGenericMemberAccessName(call.name) =>
        argumentToCallOrReturn(call)
      case expression: nodes.Expression =>
        expression
    }
  }

  def typeCarrierToType(parameterNode: Vertex): nodes.StoredNode = {
    parameterNode.asInstanceOf[nodes.StoredNode]._evalTypeOut.nextChecked
  }

  def parameterInToMethod(parameterNode: Vertex): nodes.StoredNode = {
    parameterNode.asInstanceOf[nodes.StoredNode]._astIn.nextChecked
  }

  def methodReturnToMethod(formalReturnNode: Vertex): nodes.StoredNode = {
    formalReturnNode.asInstanceOf[nodes.StoredNode]._astIn.nextChecked
  }

  def returnToReturnedExpression(returnExpression: Vertex): Option[nodes.Expression] = {
    returnExpression.asInstanceOf[nodes.StoredNode]._astOut.nextOption.map(_.asInstanceOf[nodes.Expression])
  }

  def methodToFormalReturn(method: Vertex): nodes.StoredNode = {
    method
      .asInstanceOf[nodes.StoredNode]
      ._astOut
      .asScala
      .filter(_.isInstanceOf[nodes.MethodReturn])
      .asJava
      .nextChecked
  }

  def formalReturnToReturn(methodReturn: Vertex): Seq[nodes.StoredNode] = {
    methodReturn
      .asInstanceOf[nodes.StoredNode]
      ._cfgIn
      .asScala
      .filter(_.isInstanceOf[nodes.Return])
      .toSeq
  }

  def expressionToMethod(expression: Vertex): nodes.StoredNode = {
    expression.asInstanceOf[nodes.StoredNode]._containsIn.nextChecked
  }

  def localToMethod(local: Vertex): nodes.StoredNode = {
    local.asInstanceOf[nodes.StoredNode]._astIn.nextChecked
  }

  def hasModifier(methodNode: Vertex, modifierType: String): Boolean = {
    methodNode
      .asInstanceOf[nodes.StoredNode]
      ._astOut
      .asScala
      .exists(astChild =>
        astChild.isInstanceOf[nodes.Modifier] &&
          astChild.asInstanceOf[nodes.Modifier].modifierType == modifierType)
  }

  def astParent(expression: Vertex): nodes.StoredNode = {
    expression.asInstanceOf[nodes.StoredNode]._astIn.nextChecked
  }

  def callToCalledMethod(call: Vertex): Seq[nodes.Method] = {
    call
      .asInstanceOf[nodes.StoredNode]
      ._callOut
      .asScala
      .map(_.asInstanceOf[nodes.Method])
      .toSeq
  }

  def methodToTypeDecl(vertex: Vertex): Option[nodes.StoredNode] = {
    findVertex(vertex, _.isInstanceOf[nodes.TypeDecl])
  }

  def methodToFile(vertex: Vertex): Option[Vertex] = {
    findVertex(vertex, _.isInstanceOf[nodes.File])
  }

  @tailrec
  private def findVertex(vertex: Vertex, instanceCheck: Vertex => Boolean): Option[nodes.StoredNode] = {
    val iterator = vertex.asInstanceOf[nodes.StoredNode]._astIn
    if (iterator.hasNext) {
      iterator.next() match {
        case head if instanceCheck(head) => Some(head)
        case head                        => findVertex(head, instanceCheck)
      }
    } else {
      None
    }
  }

  def methodToOutParameters(method: Vertex): Seq[nodes.StoredNode] = {
    method
      .asInstanceOf[nodes.StoredNode]
      ._astOut
      .asScala
      .filter(_.isInstanceOf[nodes.MethodParameterOut])
      .toSeq
  }

  def allCfgNodesOfMethod(method: Vertex): IterableOnce[nodes.StoredNode] = {
    method.asInstanceOf[nodes.StoredNode]._containsOut.asScala
  }

  def reference(node: Vertex): Option[nodes.StoredNode] = {
    node.asInstanceOf[nodes.StoredNode]._refOut.nextOption
  }

  def walkAST(expression: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    expression.emit.repeat(_.out(EdgeTypes.AST)).dedup()
  }

  def implicitCallToMethod(implicitCall: nodes.ImplicitCall): nodes.Method = {
    implicitCall.vertices(Direction.IN, EdgeTypes.AST).next.asInstanceOf[nodes.Method]
  }

}
