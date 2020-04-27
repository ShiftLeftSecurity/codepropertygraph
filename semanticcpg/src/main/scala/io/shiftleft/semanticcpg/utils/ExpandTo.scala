package io.shiftleft.semanticcpg.utils

import io.shiftleft.Implicits._
import io.shiftleft.codepropertygraph.generated._
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.structure.Vertex

import scala.annotation.tailrec
import scala.jdk.CollectionConverters._

// TODO This object is problematic. While it offers a few utility methods
// on nodes, these are all lumped together into one object, and they are
// unavailable in the query language. We should rather extend the query
// language appropriately.

object ExpandTo {

  private val logger = LogManager.getLogger(getClass)

  // For java, the call receiver is always an object instance.
  // For languages which make use of function pointers, this can also be the
  // pointer itself.
  // TODO move into traversal dsl - used in codescience
  def callReceiverOption(callNode: nodes.Call): Option[nodes.StoredNode] =
    callNode._receiverOut.nextOption

  def callReceiver(callNode: nodes.Call): nodes.StoredNode =
    callNode._receiverOut.onlyChecked

  // TODO move into traversal dsl - used in codescience
  def callArguments(callNode: nodes.CallRepr): Iterator[nodes.Expression] =
    callNode._argumentOut.asScala.map(_.asInstanceOf[nodes.Expression])

  def typeCarrierToType(parameterNode: nodes.StoredNode): nodes.TypeBase = {
    try {
      parameterNode._evalTypeOut.onlyChecked.asInstanceOf[nodes.Type]
    } catch {
      case exc: NoSuchElementException =>
        logger.warn("Invalid CPG `typeCarrierToType` unsuccessful: ", exc)
        nodes.NewType(fullName = "")
    }
  }

  def parameterInToMethod(parameterIn: nodes.MethodParameterIn): nodes.Method =
    parameterIn._methodViaAstIn.onlyChecked

  def parameterOutToMethod(parameterOut: nodes.MethodParameterOut): nodes.Method =
    parameterOut._methodViaAstIn.onlyChecked

  def methodReturnToMethod(formalReturnNode: nodes.MethodReturn): nodes.Method =
    formalReturnNode._methodViaAstIn.onlyChecked

  def returnToReturnedExpression(returnExpression: nodes.Return): Option[nodes.Expression] =
    returnExpression._returnViaAstOut.nextOption

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
    method._methodParameterOutViaAstOut

  def implicitCallToMethod(implicitCall: nodes.ImplicitCall): nodes.Method =
    implicitCall._methodViaAstIn.onlyChecked

}
