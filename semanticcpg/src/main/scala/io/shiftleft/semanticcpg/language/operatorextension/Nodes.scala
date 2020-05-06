package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.Call

object opnodes {
  class Assignment(call: Call) extends nodes.Call(call.graph, call.id)
  class Arithmetic(call: Call) extends nodes.Call(call.graph, call.id)
  class ArrayAccess(call: Call) extends nodes.Call(call.graph, call.id)
}
