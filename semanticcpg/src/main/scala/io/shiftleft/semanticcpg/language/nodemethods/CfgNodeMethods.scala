package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes

object CfgNodeMethods {

  /**
    * Textual representation of CFG node
    * */
  def repr(cfgNode: nodes.CfgNode): String = {
    cfgNode match {
      case method: nodes.MethodBase             => method.name
      case methodReturn: nodes.MethodReturnBase => methodReturn.code
      case expr: nodes.Expression               => expr.code
      case call: nodes.ImplicitCallBase         => call.code
    }
  }

}
