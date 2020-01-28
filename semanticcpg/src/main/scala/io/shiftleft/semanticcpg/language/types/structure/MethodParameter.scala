package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Identifier
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

/**
  * Formal method input parameter
  * */
class MethodParameter[A <: nodes.MethodParameterIn](raw: GremlinScala[A])
    extends NodeSteps[A](raw)
    with EvalTypeAccessors[A] {

  /**
    * Traverse to all `num`th parameters
    * */
  def index(num: Int): MethodParameter[A] =
    this.order(num)

  /**
    * Traverse to all parameters with index greater or equal than `num`
    * */
  def indexFrom(num: Int): MethodParameter[A] =
    new MethodParameter(raw.has(NodeKeys.METHOD_PARAMETER_IN.ORDER, P.gte(num: Integer)))

  /**
    * Traverse to all parameters with index smaller or equal than `num`
    * */
  def indexTo(num: Int): MethodParameter[A] =
    new MethodParameter(raw.has(NodeKeys.METHOD_PARAMETER_IN.ORDER, P.lte(num: Integer)))

  /**
    * Traverse to method associated with this formal parameter
    * */
  def method: Method[nodes.Method] =
    new Method(raw.in(EdgeTypes.AST).cast[nodes.Method])

  /**
    * Traverse to arguments (actual parameters) associated with this formal parameter
    * */
  def argument(): Expression[nodes.Expression] = {
    new Expression(
      raw
        .sack((_: Integer, node: nodes.MethodParameterIn) => node.value2(NodeKeys.ORDER))
        .in(EdgeTypes.AST)
        .in(EdgeTypes.CALL)
        .out(EdgeTypes.ARGUMENT)
        .filterWithTraverser { traverser =>
          val argumentIndex = traverser.sack[Integer]
          traverser.get.value2(NodeKeys.ARGUMENT_INDEX) == argumentIndex
        }
        .cast[nodes.Expression]
    )
  }

  /**
    * Traverse to corresponding formal output parameter
    * */
  def asOutput: MethodParameterOut[nodes.MethodParameterOut] =
    new MethodParameterOut(raw.out(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterOut])

  /**
    * Places (identifier) where this parameter is being referenced
    * */
  def referencingIdentifiers: Identifier[nodes.Identifier] =
    new Identifier(raw.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    * Traverse to parameter type
    * */
  def typ: Type[nodes.Type] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])

}
