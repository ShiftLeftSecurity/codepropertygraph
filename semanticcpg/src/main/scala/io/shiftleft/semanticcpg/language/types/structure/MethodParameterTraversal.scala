package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.{Traversal, help}

import scala.jdk.CollectionConverters._

/**
  * Formal method input parameter
  * */
@help.Traversal(elementType = classOf[MethodParameterIn])
class MethodParameterTraversal(val traversal: Traversal[MethodParameterIn]) extends AnyVal {

  /**
    * Traverse to all `num`th parameters
    * */
  def index(num: Int): Traversal[MethodParameterIn] =
    traversal.order(num)

  /**
    * Traverse to all parameters with index greater or equal than `num`
    * */
  def indexFrom(num: Int): Traversal[MethodParameterIn] =
    traversal.filter(_.order >= num)

  /**
    * Traverse to all parameters with index smaller or equal than `num`
    * */
  def indexTo(num: Int): Traversal[MethodParameterIn] =
    traversal.filter(_.order <= num)

  /**
    * Traverse to method associated with this formal parameter
    * */
  def method: Traversal[Method] =
    traversal.in(EdgeTypes.AST).cast[Method]

  /**
    * Traverse to arguments (actual parameters) associated with this formal parameter
    * */
  def argument(implicit callResolver: ICallResolver): Traversal[Expression] =
    for {
      paramIn <- traversal
      call <- callResolver.getMethodCallsites(paramIn._methodViaAstIn)
      arg <- call._argumentOut.asScala.collect { case node: Expression with HasArgumentIndex => node }
      if arg.argumentIndex == paramIn.order
    } yield arg

  /**
    * Traverse to corresponding formal output parameter
    * */
  def asOutput: Traversal[MethodParameterOut] =
    traversal.out(EdgeTypes.PARAMETER_LINK).cast[MethodParameterOut]

  /**
    * Places (identifier) where this parameter is being referenced
    * */
  def referencingIdentifiers: Traversal[Identifier] =
    traversal.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[Identifier]

  /**
    * Traverse to parameter type
    * */
  def typ: Traversal[Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[Type]

}
