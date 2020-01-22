package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class Comment(raw: GremlinScala[nodes.Comment]) extends NodeSteps[nodes.Comment](raw) {

  override def file: File =
    new File(raw.in(EdgeTypes.AST).hasLabel(NodeTypes.FILE).cast[nodes.File])

}
