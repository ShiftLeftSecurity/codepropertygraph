package io.shiftleft.semanticcpg.utils

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
  // TODO move into traversal dsl - used in codescience
  def callReceiverOption(callNode: Vertex): Option[nodes.StoredNode] =
    callNode
      .asInstanceOf[nodes.StoredNode]
      ._receiverOut
      .nextOption

  def callReceiver(callNode: Vertex): nodes.StoredNode = callReceiverOption(callNode).get

  // TODO move into traversal dsl - used in codescience
  def callArguments(callNode: Vertex): Iterator[nodes.Expression] =
    callNode.asInstanceOf[nodes.StoredNode]._argumentOut.asScala.map(_.asInstanceOf[nodes.Expression])

  def typeCarrierToType(parameterNode: Vertex): nodes.StoredNode =
    parameterNode.asInstanceOf[nodes.StoredNode]._evalTypeOut.nextChecked

  def parameterInToMethod(parameterNode: Vertex): nodes.StoredNode =
    parameterNode.asInstanceOf[nodes.StoredNode]._astIn.nextChecked

  def methodReturnToMethod(formalReturnNode: Vertex): nodes.StoredNode =
    formalReturnNode.asInstanceOf[nodes.StoredNode]._astIn.nextChecked

  def returnToReturnedExpression(returnExpression: Vertex): Option[nodes.Expression] =
    returnExpression.asInstanceOf[nodes.StoredNode]._astOut.nextOption.map(_.asInstanceOf[nodes.Expression])

  def expressionToMethod(expression: nodes.Expression): nodes.Method =
    expression._containsIn.nextChecked match {
      case method: nodes.Method => method
      case _: nodes.TypeDecl    =>
        // TODO - there are csharp CPGs that have typedecls here, which is invalid.
        null
    }

  def hasModifier(methodNode: Vertex, modifierType: String): Boolean =
    methodNode
      .asInstanceOf[nodes.StoredNode]
      ._astOut
      .asScala
      .exists(astChild =>
        astChild.isInstanceOf[nodes.Modifier] &&
          astChild.asInstanceOf[nodes.Modifier].modifierType == modifierType)

  def callToCalledMethod(call: Vertex): Seq[nodes.Method] =
    call
      .asInstanceOf[nodes.StoredNode]
      ._callOut
      .asScala
      .map(_.asInstanceOf[nodes.Method])
      .toSeq

  def methodToTypeDecl(method: nodes.Method): Option[nodes.TypeDecl] =
    findVertex(method, _.isInstanceOf[nodes.TypeDecl]).map(_.asInstanceOf[nodes.TypeDecl])

  def methodToFile(method: nodes.Method): Option[nodes.File] =
    findVertex(method, _.isInstanceOf[nodes.File]).map(_.asInstanceOf[nodes.File])

  @tailrec
  private def findVertex(node: nodes.StoredNode, instanceCheck: nodes.StoredNode => Boolean): Option[nodes.StoredNode] =
    node._astIn.nextOption match {
      case Some(head) if instanceCheck(head) => Some(head)
      case Some(head)                        => findVertex(head, instanceCheck)
      case None                              => None
    }

  def methodToOutParameters(method: Vertex): Seq[nodes.StoredNode] = {
    method
      .asInstanceOf[nodes.StoredNode]
      ._astOut
      .asScala
      .filter(_.isInstanceOf[nodes.MethodParameterOut])
      .toSeq
  }

  def implicitCallToMethod(implicitCall: nodes.ImplicitCall): nodes.Method = {
    implicitCall.vertices(Direction.IN, EdgeTypes.AST).next.asInstanceOf[nodes.Method]
  }

}
