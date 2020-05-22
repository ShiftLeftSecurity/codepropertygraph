package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

class MethodParameterOut(val wrapped: NodeSteps[nodes.MethodParameterOut]) extends AnyVal {
  private def raw: GremlinScala[nodes.MethodParameterOut] = wrapped.raw

  /* method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def index(num: Int): NodeSteps[nodes.MethodParameterOut] =
    wrapped.order(num)

  /* get all parameters from (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexFrom(num: Int): NodeSteps[nodes.MethodParameterOut] =
    wrapped.where(_.order >= num)

  /* get all parameters up to (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexTo(num: Int): NodeSteps[nodes.MethodParameterOut] =
    wrapped.where(_.order <= num)

  def method: NodeSteps[nodes.Method] =
    new NodeSteps(raw.in(EdgeTypes.AST).cast[nodes.Method])

  def argument: NodeSteps[nodes.Expression] = {
    val trav = for {
      paramOut <- raw.toIterator
      method <- paramOut._methodViaAstIn
      call <- method._callViaCallIn
      arg <- call._argumentOut.asScala.collect { case node: nodes.Expression with nodes.HasArgumentIndex => node }
      if arg.argumentIndex == paramOut.order
    } yield arg

    new NodeSteps(__(trav.toSeq: _*))
  }

  def asInput: NodeSteps[nodes.MethodParameterIn] =
    new NodeSteps(raw.in(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterIn])

  /**
    * Traverse to parameter type
    * */
  def typ: NodeSteps[nodes.Type] =
    new NodeSteps(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])

}
