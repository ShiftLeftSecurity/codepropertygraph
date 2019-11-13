package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{nodes => basenodes}
import io.shiftleft.semanticcpg.language.operatorextension.nodes.OpAstNode
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode

class OpAstNodeTrav(raw: GremlinScala[basenodes.AstNode]) {

  def assignments: AssignmentTrav =
    new AssignmentTrav(
      new AstNode(raw)
        .flatMap(new OpAstNode(_).assignments)
        .raw)

}
