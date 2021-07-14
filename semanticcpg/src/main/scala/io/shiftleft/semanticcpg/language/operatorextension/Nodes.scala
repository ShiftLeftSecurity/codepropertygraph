package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.Call

object opnodes {
  case class Assignment(val call: Call)
  case class Arithmetic(val call: Call)
  case class ArrayAccess(val call: Call)
}
