package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class Declaration(raw: GremlinScala[nodes.Declaration])
    extends NodeSteps[nodes.Declaration](raw)
    with DeclarationBase[nodes.Declaration]

trait DeclarationBase[NodeType <: nodes.Declaration] {
  this: NodeSteps[NodeType] =>
  // TODO: steps for Declarations go here

  def help: String = "a declaration. todo add more text"
}
