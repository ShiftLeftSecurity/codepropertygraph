package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps._
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.types.expressions.Identifier
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.DeclarationBase
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import shapeless.HList

/**
  * A local variable
  * */
class Local[Labels <: HList](raw: GremlinScala.Aux[nodes.Local, Labels])
    extends NodeSteps[nodes.Local, Labels](raw)
    with DeclarationBase[nodes.Local, Labels]
    with CodeAccessors[nodes.Local, Labels]
    with NameAccessors[nodes.Local, Labels]
    with OrderAccessors[nodes.Local, Labels]
    with LineNumberAccessors[nodes.Local, Labels]
    with EvalTypeAccessors[nodes.Local, Labels] {

  /**
    * The method hosting this local variable
    * */
  def method: Method[Labels] = {
    // TODO The following line of code is here for backwards compatibility.
    // Use the lower commented out line once not required anymore.
    new Method[Labels](raw.repeat(_.in(EdgeTypes.AST)).until(_.hasLabel(NodeTypes.METHOD)).cast[nodes.Method])
    //definingBlock.method
  }

  /**
    * The block in which local is declared.
    */
  def definingBlock: Block[Labels] =
    new Block[Labels](raw.in(EdgeTypes.AST).cast[nodes.Block])

  /**
    * Places (identifier) where this local is referenced
    * */
  def referencingIdentifiers: Identifier[Labels] =
    new Identifier[Labels](raw.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    * The type of the local.
    *
    * Unfortunately, `type` is a keyword, so we use `typ` here.
    * */
  def typ: Type[Labels] =
    new Type[Labels](raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
