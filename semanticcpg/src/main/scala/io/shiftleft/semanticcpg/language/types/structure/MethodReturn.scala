package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Call
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression
import io.shiftleft.semanticcpg.language.types.propertyaccessors.EvalTypeAccessors

class MethodReturn(raw: GremlinScala[nodes.MethodReturn])
    extends NodeSteps[nodes.MethodReturn](raw)
    with EvalTypeAccessors[nodes.MethodReturn] {

  def method: NodeSteps[nodes.Method] =
    new NodeSteps(raw.in(EdgeTypes.AST).cast[nodes.Method])

  def returnUser: NodeSteps[nodes.Call] =
    new NodeSteps(raw.in(EdgeTypes.AST).in(EdgeTypes.CALL).cast[nodes.Call])

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.in(EdgeTypes.CFG).cast[nodes.Expression])

  /**
    * Traverse to return type
    * */
  def typ: NodeSteps[nodes.Type] =
    new NodeSteps(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])

  def toReturn: NodeSteps[nodes.Return] =
    new NodeSteps(raw.flatMap { mr =>
      __(mr.toReturn: _*)
    })
}
