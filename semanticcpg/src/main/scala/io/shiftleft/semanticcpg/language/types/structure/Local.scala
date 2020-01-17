package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Identifier
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.DeclarationBase
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

/**
  * A local variable
  * */
class Local(raw: GremlinScala[nodes.Local])
    extends NodeSteps[nodes.Local](raw)
    with DeclarationBase[nodes.Local]
    with CodeAccessors[nodes.Local]
    with NameAccessors[nodes.Local]
    with OrderAccessors[nodes.Local]
    with EvalTypeAccessors[nodes.Local] {

  /**
    * The method hosting this local variable
    * */
  def method: Method = {
    // TODO The following line of code is here for backwards compatibility.
    // Use the lower commented out line once not required anymore.
    new Method(raw.repeat(_.in(EdgeTypes.AST)).until(_.hasLabel(NodeTypes.METHOD)).cast[nodes.Method])
    //definingBlock.method
  }

  /**
    * The block in which local is declared.
    */
  def definingBlock: Block =
    new Block(raw.in(EdgeTypes.AST).cast[nodes.Block])

  /**
    * Places (identifier) where this local is being referenced
    * */
  def referencingIdentifiers: Identifier =
    new Identifier(raw.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    * The type of the local.
    *
    * Unfortunately, `type` is a keyword, so we use `typ` here.
    * */
  def typ: Type =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
