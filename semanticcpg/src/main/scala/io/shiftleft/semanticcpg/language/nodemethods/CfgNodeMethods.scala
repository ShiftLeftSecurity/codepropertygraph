package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.overflowdb.traversal.help
import io.shiftleft.overflowdb.traversal.help.Doc

@help.Traversal(elementType = classOf[nodes.CfgNode])
object CfgNodeMethods {

  /**
    * Textual representation of CFG node
    * */
  @Doc("Textual representation of CFG node")
  def repr(cfgNode: nodes.CfgNode): String = {
    cfgNode match {
      case method: nodes.MethodBase             => method.name
      case methodReturn: nodes.MethodReturnBase => methodReturn.code
      case expr: nodes.Expression               => expr.code
      case call: nodes.ImplicitCallBase         => call.code
    }
  }

}
