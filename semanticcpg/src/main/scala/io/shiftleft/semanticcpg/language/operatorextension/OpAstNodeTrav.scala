package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala

import io.shiftleft.codepropertygraph.generated.{nodes => basenodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode

class OpAstNodeTrav[NodeType <: basenodes.AstNode](raw: GremlinScala[NodeType]) {

  def inAssignment = new AssignmentTrav(astNode.flatMap(_.inAssignment).raw)

  private def astNode: AstNode =
    new AstNode(raw.cast[basenodes.AstNode])

  def assignments: AssignmentTrav =
    new AssignmentTrav(
      new AstNode(raw.cast[basenodes.AstNode])
        .flatMap(_.assignments)
        .raw)

}
