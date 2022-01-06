package io.shiftleft.semanticcpg.passes.typerelations

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{Method, TypeDecl}
import io.shiftleft.passes.{ConcurrentWriterCpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

/**
  * This pass attempts to fill out empty `typeFullName` fields based on
  * existing `typeFullName` fields and type declarations on a per-method
  * basis.
  *
  * For now, we only implement semantics for direct and indirect field accesses,
  * that is, we attempt to determine the types of 'a.b' or 'a->b' given the
  * typeFullName of 'a' and the member name 'b'.
  * */
class TypePropagationPass(cpg: Cpg) extends ConcurrentWriterCpgPass[Method](cpg) {

  val typeFullNameToTypeDecl: Map[String, TypeDecl] = cpg.typeDecl.map { typeDecl =>
    typeDecl.fullName -> typeDecl
  }.toMap

  override def generateParts(): Array[Method] = cpg.method.toArray

  /** Algorithm:
    * (1) Determine all calls to operators for which we have propagation rules,
    *     along with their arguments
    * (2) For each of these calls, determine which ones can be resolved given the
    *     current state of typeFullName fields and add these two a queue.
    * (3) While the queue is not empty, take first element:
    *     - determine typeFullName and write it down
    *     - check whether the parent now represents an operation that we can resolve,
    *       and if so, add it to the queue.
    **/
  override def runOnPart(builder: DiffGraph.Builder, part: Method): Unit = {
    // TODO for the operator extension, we want steps from METHOD nodes
    // to all of the supported operators
    val fieldAccesses = cpg.method.fieldAccess.l
    println(fieldAccesses)
  }
}
