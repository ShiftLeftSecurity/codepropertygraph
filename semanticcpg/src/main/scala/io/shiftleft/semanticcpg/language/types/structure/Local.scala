package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Identifier
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

/**
  * A local variable
  * */
class Local[A <: nodes.Local](raw: GremlinScala[A]) extends NodeSteps[A](raw) with EvalTypeAccessors[A] {

  /**
    * The method hosting this local variable
    * */
  def method: NodeSteps[nodes.Method] = {
    // TODO The following line of code is here for backwards compatibility.
    // Use the lower commented out line once not required anymore.
    new Method(raw.repeat(_.in(EdgeTypes.AST)).until(_.hasLabel(NodeTypes.METHOD)).cast[nodes.Method])
    //definingBlock.method
  }

  /**
    * The block in which local is declared.
    */
  def definingBlock: Block[nodes.Block] =
    new Block(raw.in(EdgeTypes.AST).cast[nodes.Block])

  /**
    * Places (identifier) where this local is being referenced
    * */
  def referencingIdentifiers: Identifier[nodes.Identifier] =
    new Identifier(raw.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    * The type of the local.
    *
    * Unfortunately, `type` is a keyword, so we use `typ` here.
    * */
  def typ: Type[nodes.Type] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
