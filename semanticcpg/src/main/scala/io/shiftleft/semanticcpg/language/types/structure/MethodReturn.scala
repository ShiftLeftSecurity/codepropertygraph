package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Call
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{CodeAccessors, EvalTypeAccessors, LineNumberAccessors}

class MethodReturn(raw: GremlinScala[nodes.MethodReturn])
    extends NodeSteps[nodes.MethodReturn](raw)
    with CodeAccessors[nodes.MethodReturn]
    with LineNumberAccessors[nodes.MethodReturn]
    with EvalTypeAccessors[nodes.MethodReturn] {

  def method: Method =
    new Method(raw.in(EdgeTypes.AST).cast[nodes.Method])

  def returnUser: Call = {
    new Call(
      raw.in(EdgeTypes.AST).in(EdgeTypes.REF).in(EdgeTypes.CALL).cast[nodes.Call]
    )
  }

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast: Expression =
    new Expression(
      raw.in(EdgeTypes.CFG).cast[nodes.Expression]
    )

  /**
    * Traverse to return type
    * */
  def typ: Type =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
