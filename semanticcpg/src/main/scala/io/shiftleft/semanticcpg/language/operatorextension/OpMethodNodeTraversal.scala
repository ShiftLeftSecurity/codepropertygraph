package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.{Call, Method}
import io.shiftleft.semanticcpg.language.toMethodForCallGraph
import overflowdb.traversal.Traversal
import overflowdb.traversal.help.Doc

class OpMethodNodeTraversal(methodTrav: Traversal[Method]) {

  @Doc(info = "All assignments, including shorthand assignments that perform arithmetic (e.g., '+=')")
  def assignment: Traversal[OpNodes.Assignment] =
    callsWithNameIn(allAssignmentTypes)
      .map(new OpNodes.Assignment(_))

  @Doc(info = "All arithmetic operations, including shorthand assignments that perform arithmetic (e.g., '+=')")
  def arithmetic: Traversal[OpNodes.Arithmetic] =
    callsWithNameIn(allArithmeticTypes)
      .map(new OpNodes.Arithmetic(_))

  @Doc(info = "All array accesses")
  def arrayAccess: Traversal[OpNodes.ArrayAccess] =
    callsWithNameIn(allArrayAccessTypes)
      .map(new OpNodes.ArrayAccess(_))

  @Doc(info = "Field accesses, both direct and indirect")
  def fieldAccess: Traversal[OpNodes.FieldAccess] =
    callsWithNameIn(allFieldAccessTypes)
      .map(new OpNodes.FieldAccess(_))

  private def callsWithNameIn(set: Set[String]): Traversal[Call] =
    methodTrav.call.filter(x => set.contains(x.name))

}
