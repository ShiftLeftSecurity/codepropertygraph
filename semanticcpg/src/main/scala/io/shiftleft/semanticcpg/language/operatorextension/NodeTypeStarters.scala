package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._
import overflowdb.traversal.help.{Doc, TraversalSource}

@TraversalSource
class NodeTypeStarters(cpg: Cpg) {

  @Doc(info = "All assignments, including shorthand assignments that perform arithmetic (e.g., '+=')")
  def assignment: Traversal[opnodes.Assignment] =
    callsWithNameIn(allAssignmentTypes)
      .map(new opnodes.Assignment(_))

  @Doc(info = "All arithmetic operations, including shorthand assignments that perform arithmetic (e.g., '+=')")
  def arithmetic: Traversal[opnodes.Arithmetic] =
    callsWithNameIn(allArithmeticTypes)
      .map(new opnodes.Arithmetic(_))

  @Doc(info = "All array accesses")
  def arrayAccess: Traversal[opnodes.ArrayAccess] =
    callsWithNameIn(allArrayAccessTypes)
      .map(new opnodes.ArrayAccess(_))

  private def callsWithNameIn(set: Set[String]) =
    cpg.call.filter(x => set.contains(x.name))

}
