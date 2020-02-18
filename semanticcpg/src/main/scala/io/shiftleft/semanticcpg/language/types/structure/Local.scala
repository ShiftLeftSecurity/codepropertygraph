package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._

/**
  * A local variable
  * */
class Local(val wrapped: NodeSteps[nodes.Local]) extends AnyVal {
  def raw: GremlinScala[nodes.Local] = wrapped.raw

  /**
    * The method hosting this local variable
    * */
  def method: NodeSteps[nodes.Method] = {
    // TODO The following line of code is here for backwards compatibility.
    // Use the lower commented out line once not required anymore.
    new NodeSteps(raw.repeat(_.in(EdgeTypes.AST)).until(_.hasLabel(NodeTypes.METHOD)).cast[nodes.Method])
    //definingBlock.method
  }

  /**
    * The block in which local is declared.
    */
  def definingBlock: NodeSteps[nodes.Block] =
    new NodeSteps(raw.in(EdgeTypes.AST).cast[nodes.Block])

  /**
    * Places (identifier) where this local is being referenced
    * */
  def referencingIdentifiers: NodeSteps[nodes.Identifier] =
    new NodeSteps(raw.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    * The type of the local.
    *
    * Unfortunately, `type` is a keyword, so we use `typ` here.
    * */
  def typ: NodeSteps[nodes.Type] =
    new NodeSteps(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
