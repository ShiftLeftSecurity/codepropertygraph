package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.traversal._
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class MethodParameterOutTraversal(val traversal: Traversal[MethodParameterOut]) extends AnyVal {

  /* method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def index(num: Int): Traversal[MethodParameterOut] =
    traversal.order(num)

  /* get all parameters from (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexFrom(num: Int): Traversal[MethodParameterOut] =
    traversal.filter(_.order >= num)

  /* get all parameters up to (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexTo(num: Int): Traversal[MethodParameterOut] =
    traversal.filter(_.order <= num)

  def method: Traversal[Method] =
    traversal.in(EdgeTypes.AST).cast[Method]

  def argument: Traversal[Expression] =
    for {
      paramOut <- traversal
      method <- paramOut._methodViaAstIn
      call <- method._callViaCallIn
      arg <- call._argumentOut.asScala.collect { case node: Expression with HasArgumentIndex => node }
      if arg.argumentIndex == paramOut.order
    } yield arg

  def asInput: Traversal[MethodParameterIn] =
    traversal.in(EdgeTypes.PARAMETER_LINK).cast[MethodParameterIn]

  /**
    * Traverse to parameter type
    * */
  def typ: Traversal[Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[Type]

}
