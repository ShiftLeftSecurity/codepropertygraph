package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.help
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

/**
  * Formal method input parameter
  * */
@help.Traversal(elementType = classOf[nodes.MethodParameterIn])
class MethodParameter(val wrapped: NodeSteps[nodes.MethodParameterIn]) extends AnyVal {
  private def raw: GremlinScala[nodes.MethodParameterIn] = wrapped.raw

  /**
    * Traverse to all `num`th parameters
    * */
  def index(num: Int): NodeSteps[nodes.MethodParameterIn] =
    wrapped.order(num)

  /**
    * Traverse to all parameters with index greater or equal than `num`
    * */
  def indexFrom(num: Int): NodeSteps[nodes.MethodParameterIn] =
    wrapped.where(_.order >= num)

  /**
    * Traverse to all parameters with index smaller or equal than `num`
    * */
  def indexTo(num: Int): NodeSteps[nodes.MethodParameterIn] =
    wrapped.where(_.order <= num)

  /**
    * Traverse to method associated with this formal parameter
    * */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(raw.in(EdgeTypes.AST).cast[nodes.Method])

  /**
    * Traverse to arguments (actual parameters) associated with this formal parameter
    * */
  def argument: NodeSteps[nodes.Expression] = {
    val trav = for {
      paramIn <- raw.toIterator
      call <- paramIn._methodViaAstIn._callViaCallIn
      arg <- call._argumentOut.asScala.collect { case node: nodes.Expression with nodes.HasArgumentIndex => node }
      if arg.argumentIndex == paramIn.order
    } yield arg

    new NodeSteps(__(trav.toSeq: _*))
  }

  /**
    * Traverse to corresponding formal output parameter
    * */
  def asOutput: NodeSteps[nodes.MethodParameterOut] =
    new NodeSteps(raw.out(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterOut])

  /**
    * Places (identifier) where this parameter is being referenced
    * */
  def referencingIdentifiers: NodeSteps[nodes.Identifier] =
    new NodeSteps(raw.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    * Traverse to parameter type
    * */
  def typ: NodeSteps[nodes.Type] =
    new NodeSteps(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])

}
