package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.{Traversal, help}

import scala.jdk.CollectionConverters._

/**
  * Formal method input parameter
  * */
@help.Traversal(elementType = classOf[nodes.MethodParameterIn])
class MethodParameter(val traversal: Traversal[nodes.MethodParameterIn]) extends AnyVal {

  /**
    * Traverse to all `num`th parameters
    * */
  def index(num: Int): Traversal[nodes.MethodParameterIn] =
    traversal.order(num)

  /**
    * Traverse to all parameters with index greater or equal than `num`
    * */
  def indexFrom(num: Int): Traversal[nodes.MethodParameterIn] =
    traversal.filter(_.order >= num)

  /**
    * Traverse to all parameters with index smaller or equal than `num`
    * */
  def indexTo(num: Int): Traversal[nodes.MethodParameterIn] =
    traversal.filter(_.order <= num)

  /**
    * Traverse to method associated with this formal parameter
    * */
  def method: Traversal[nodes.Method] =
    traversal.in(EdgeTypes.AST).cast[nodes.Method])

  /**
    * Traverse to arguments (actual parameters) associated with this formal parameter
    * */
  def argument(implicit callResolver: ICallResolver): Traversal[nodes.Expression] =
    for {
      paramIn <- traversal
      call <- callResolver.getMethodCallsites(paramIn._methodViaAstIn)
      arg <- call._argumentOut.asScala.collect { case node: nodes.Expression with nodes.HasArgumentIndex => node }
      if arg.argumentIndex == paramIn.order
    } yield arg

  /**
    * Traverse to corresponding formal output parameter
    * */
  def asOutput: Traversal[nodes.MethodParameterOut] =
    traversal.out(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterOut]

  /**
    * Places (identifier) where this parameter is being referenced
    * */
  def referencingIdentifiers: Traversal[nodes.Identifier] =
    traversal.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier]

  /**
    * Traverse to parameter type
    * */
  def typ: Traversal[nodes.Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type]

}
