package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.Call

object opnodes {
  case class Assignment(val call: Call) // extends Call(call.graph, call.id)
  case class Arithmetic(val call: Call) // extends Call(call.graph, call.id)
  case class ArrayAccess(val call: Call) // extends Call(call.graph, call.id)
  implicit def unwrap(assignment: Assignment): Call = assignment.call
  implicit def unwrap(assignment: Arithmetic): Call = assignment.call
  implicit def unwrap(assignment: ArrayAccess): Call = assignment.call
}
