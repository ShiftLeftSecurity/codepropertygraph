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
  def callReceiverOption(callNode: nodes.Call): Option[nodes.StoredNode] =
    callNode.receiverOut.nextOption

  def callReceiver(callNode: nodes.Call): nodes.StoredNode =
    callNode.receiverOut.onlyChecked

  // TODO move into traversal dsl - used in codescience
  def callArguments(callNode: nodes.CallRepr): Iterator[nodes.Expression] =
    callNode._argumentOut.asScala.map(_.asInstanceOf[nodes.Expression])

  def typeCarrierToType(parameterNode: nodes.StoredNode): nodes.Type =
    parameterNode._evalTypeOut.onlyChecked.asInstanceOf[nodes.Type]

  def parameterInToMethod(parameterIn: nodes.MethodParameterIn): nodes.Method =
    parameterIn.astIn.onlyChecked

  def parameterOutToMethod(parameterOut: nodes.MethodParameterOut): nodes.Method =
    parameterOut.astIn.onlyChecked

  def methodReturnToMethod(formalReturnNode: nodes.MethodReturn): nodes.Method =
    formalReturnNode.astIn.onlyChecked

  def returnToReturnedExpression(returnExpression: nodes.Return): Option[nodes.Expression] =
    returnExpression.astOut.nextOption.asInstanceOf[Option[nodes.Expression]]

  def expressionToMethod(expression: nodes.Expression): nodes.Method =
    expression._containsIn.onlyChecked match {
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

  def methodToOutParameters(method: nodes.Method): Iterator[nodes.MethodParameterOut] =
    method.astOut.asScala.collect { case paramOut: nodes.MethodParameterOut => paramOut }

  def implicitCallToMethod(implicitCall: nodes.ImplicitCall): nodes.Method =
    implicitCall.astIn.onlyChecked

}
