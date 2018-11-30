package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated.{nodes, EdgeTypes, NodeTypes}
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.expressions.Identifier
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.DeclarationBase
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import shapeless.HList

/**
  * A local variable
  * */
class Local[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Local, Labels](raw)
    with DeclarationBase[nodes.Local, Labels]
    with CodeAccessors[nodes.Local, Labels]
    with NameAccessors[nodes.Local, Labels]
    with OrderAccessors[nodes.Local, Labels]
    with LineNumberAccessors[nodes.Local, Labels]
    with EvalTypeAccessors[nodes.Local, Labels] {
  override val converter = Converter.forDomainNode[nodes.Local]

  /**
    * The method hosting this local variable
    * */
  def method: Method[Labels] = {
    // TODO The following line of code is here for backwards compatibility.
    // Use the lower commented out line once not required anymore.
    new Method[Labels](
      raw.repeat(_.in(EdgeTypes.AST)).until(_.hasLabel(NodeTypes.METHOD)))
    //definingBlock.method
  }

  /**
    * The block in which local is declared.
    */
  def definingBlock: Block[Labels] =
    new Block[Labels](raw.in(EdgeTypes.AST))

  /**
    * Places (identifier) where this local is referenced
    * */
  def referencingIdentifiers: Identifier[Labels] =
    new Identifier[Labels](raw.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER))

  /**
    * The type of the local.
    *
    * Unfortunately, `type` is a keyword, so we use `typ` here.
    * */
  def typ: Type[Labels] =
    new Type[Labels](raw.out(EdgeTypes.EVAL_TYPE))
}
