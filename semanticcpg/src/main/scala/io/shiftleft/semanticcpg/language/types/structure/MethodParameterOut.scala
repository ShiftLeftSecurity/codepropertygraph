package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class MethodParameterOut(val traversal: Traversal[nodes.MethodParameterOut]) extends AnyVal {

  /* method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def index(num: Int): Traversal[nodes.MethodParameterOut] =
    traversal.order(num)

  /* get all parameters from (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexFrom(num: Int): Traversal[nodes.MethodParameterOut] =
    traversal.filter(_.order >= num)

  /* get all parameters up to (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexTo(num: Int): Traversal[nodes.MethodParameterOut] =
    traversal.filter(_.order <= num)

  def method: Traversal[nodes.Method] =
    traversal.in(EdgeTypes.AST).cast[nodes.Method]

  def argument: Traversal[nodes.Expression] =
    for {
      paramOut <- raw.toIterator
      method <- paramOut._methodViaAstIn
      call <- method._callViaCallIn
      arg <- call._argumentOut.asScala.collect { case node: nodes.Expression with nodes.HasArgumentIndex => node }
      if arg.argumentIndex == paramOut.order
    } yield arg

  def asInput: Traversal[nodes.MethodParameterIn] =
    traversal.in(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterIn]

  /**
    * Traverse to parameter type
    * */
  def typ: Traversal[nodes.Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type]

}
