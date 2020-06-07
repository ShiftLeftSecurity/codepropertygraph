package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.help
import overflowdb.traversal.help.Doc
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._

@help.Traversal(elementType = classOf[nodes.Block])
class Block(val wrapped: NodeSteps[nodes.Block]) extends AnyVal {
  private def raw: GremlinScala[nodes.Block] = wrapped.raw

  /** Traverse to locals of this block. */
  @Doc("Traverse to locals of this block.")
  def local: NodeSteps[nodes.Local] =
    new NodeSteps(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.LOCAL).cast[nodes.Local])
}
