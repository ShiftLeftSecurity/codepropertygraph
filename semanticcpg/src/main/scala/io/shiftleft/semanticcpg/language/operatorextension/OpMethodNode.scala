package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.{Call, Method}
import io.shiftleft.semanticcpg.language.toMethodForCallGraph
import overflowdb.traversal.Traversal

class OpMethodNode(methodTrav: Traversal[Method]) {

  def assignment: Traversal[OpNodes.Assignment] =
    callsWithNameIn(allAssignmentTypes)
      .map(new OpNodes.Assignment(_))

  def arithmetic: Traversal[OpNodes.Arithmetic] =
    callsWithNameIn(allArithmeticTypes)
      .map(new OpNodes.Arithmetic(_))

  def arrayAccess: Traversal[OpNodes.ArrayAccess] =
    callsWithNameIn(allArrayAccessTypes)
      .map(new OpNodes.ArrayAccess(_))

  def fieldAccess: Traversal[OpNodes.FieldAccess] =
    callsWithNameIn(allFieldAccessTypes)
      .map(new OpNodes.FieldAccess(_))

  private def callsWithNameIn(set: Set[String]): Traversal[Call] =
    methodTrav.call.filter(x => set.contains(x.name))

}
