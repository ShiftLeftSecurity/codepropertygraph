package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.help
import overflowdb.traversal.help.Doc
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.ExpandTo

@help.Traversal(elementType = classOf[nodes.CfgNode])
class CfgNode[A <: nodes.CfgNode](val wrapped: NodeSteps[A]) extends AnyVal {
  private def raw: GremlinScala[A] = wrapped.raw

  /**
    * Textual representation of CFG node
    * */
  @Doc("Textual representation of CFG node")
  def repr: Steps[String] = {
    wrapped.map(_.repr)
  }

  /**
  Traverse to enclosing method
    */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(
      raw
        .map {
          case method: nodes.Method =>
            method
          case methodReturn: nodes.MethodReturn =>
            ExpandTo.methodReturnToMethod(methodReturn)
          case expression: nodes.Expression =>
            ExpandTo.expressionToMethod(expression)
        }
        .cast[nodes.Method])

}
