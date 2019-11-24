package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{nodes => basenodes}
import io.shiftleft.semanticcpg.language.operatorextension.nodes.OpAstNode
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode
import io.shiftleft.semanticcpg.language._

class OpAstNodeTrav[NodeType <: basenodes.AstNode](raw: GremlinScala[NodeType]) {

  def inAssignment = new AssignmentTrav(astNode.flatMap(new OpAstNode(_).inAssignment).raw)

  private def astNode = new AstNode(raw.cast[basenodes.AstNode])

  def assignments: AssignmentTrav =
    new AssignmentTrav(
      new AstNode(raw.cast[basenodes.AstNode])
        .flatMap(new OpAstNode(_).assignments)
        .raw)

  def arithmetics: ArithmeticTrav =
    new ArithmeticTrav(
      new AstNode(raw.cast[basenodes.AstNode])
        .flatMap(new OpAstNode(_).arithmetics)
        .raw)

}
