package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes

object CfgNodeMethods {

  /**
    * Textual representation of CFG node
    * */
  def repr(cfgNode: nodes.CfgNode): String = {
    cfgNode match {
      case method: nodes.Method             => method.name
      case methodReturn: nodes.MethodReturn => methodReturn.code
      case expr: nodes.Expression           => expr.code
    }
  }

}
