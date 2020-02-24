package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.Call
import io.shiftleft.overflowdb.OdbGraph

object opnodes {
  class Assignment(call: Call) extends nodes.Call(call.graph.asInstanceOf[OdbGraph], call.id)
  class Arithmetic(call: Call) extends nodes.Call(call.graph.asInstanceOf[OdbGraph], call.id)
  class ArrayAccess(call: Call) extends nodes.Call(call.graph.asInstanceOf[OdbGraph], call.id)
}
