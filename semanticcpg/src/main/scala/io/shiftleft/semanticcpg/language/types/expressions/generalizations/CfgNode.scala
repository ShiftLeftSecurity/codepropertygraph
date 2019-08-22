package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors.CodeAccessors
import io.shiftleft.semanticcpg.language.types.structure.Method
import io.shiftleft.semanticcpg.utils.ExpandTo
import shapeless.HList

class CfgNode[Labels <: HList](raw: GremlinScala.Aux[nodes.CfgNode, Labels])
    extends NodeSteps[nodes.CfgNode, Labels](raw)
    with CodeAccessors[nodes.CfgNode, Labels]
    with AstNodeBase[nodes.CfgNode, Labels] {

  /**
  Traverse to enclosing method
    */
  def method: Method[Labels] = {
    new Method[Labels](
      raw
        .map {
          case method: nodes.Method =>
            method
          case methodReturn: nodes.MethodReturn =>
            ExpandTo.methodReturnToMethod(methodReturn)
          case expression =>
            ExpandTo.expressionToMethod(expression)
        }
        .cast[nodes.Method])
  }

}
