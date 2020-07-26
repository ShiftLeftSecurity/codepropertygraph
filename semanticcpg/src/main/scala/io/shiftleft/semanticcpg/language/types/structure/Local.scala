package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.Traversal

/**
  * A local variable
  * */
class Local(val traversal: Traversal[nodes.Local]) extends AnyVal {

  /**
    * The method hosting this local variable
    * */
  def method: Traversal[nodes.Method] = {
    // TODO The following line of code is here for backwards compatibility.
    // Use the lower commented out line once not required anymore.
    traversal.repeat(_.in(EdgeTypes.AST))(_.until(_.hasLabel(NodeTypes.METHOD))).cast[nodes.Method]
    //definingBlock.method
  }

  /**
    * The block in which local is declared.
    */
  def definingBlock: Traversal[nodes.Block] =
    traversal.in(EdgeTypes.AST).cast[nodes.Block]

  /**
    * Places (identifier) where this local is being referenced
    * */
  def referencingIdentifiers: Traversal[nodes.Identifier] =
    traversal.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier]

  /**
    * The type of the local.
    *
    * Unfortunately, `type` is a keyword, so we use `typ` here.
    * */
  def typ: Traversal[nodes.Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type]
}
