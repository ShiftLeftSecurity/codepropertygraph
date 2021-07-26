package io.shiftleft.fuzzyc2cpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.Method
import io.shiftleft.passes.{CpgPassV2, DiffGraph, DiffGraphHandler}
import io.shiftleft.semanticcpg.language._

import scala.collection.mutable

/**
  * A pass that ensures that for any method m for which a body exists,
  * there are no more method stubs for corresponding declarations.
  * */
class StubRemovalPass(cpg: Cpg) extends CpgPassV2[Method] {

  private val methodSignatureWithDef = mutable.Set.empty[String]

  override def init(): Unit = {
    cpg.method.isNotStub.foreach(m => methodSignatureWithDef.add(m.signature))
  }

  override def partIterator: Iterator[Method] =
    cpg.method.isStub.toList
      .filter(m => methodSignatureWithDef.contains(m.signature))
      .iterator

  override def runOnPart(diffGraphHandler: DiffGraphHandler, stub: Method): Unit = {
    val diffGraph = DiffGraph.newBuilder
    stub.ast.foreach(diffGraph.removeNode)
    diffGraphHandler.addDiffGraph(diffGraph.build())
  }
}
