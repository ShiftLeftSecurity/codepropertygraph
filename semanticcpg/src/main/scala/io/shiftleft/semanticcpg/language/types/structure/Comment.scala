package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class Comment[A <: nodes.Comment](raw: GremlinScala[A]) extends NodeSteps[A](raw) {

  override def file: NodeSteps[nodes.File] =
    new NodeSteps(raw.in(EdgeTypes.AST).hasLabel(NodeTypes.FILE).cast[nodes.File])

}
