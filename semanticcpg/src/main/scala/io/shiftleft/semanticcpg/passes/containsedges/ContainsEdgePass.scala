package io.shiftleft.semanticcpg.passes.containsedges

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.language.{NodeTypeDeco, Steps}

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class ContainsEdgePass(cpg: Cpg) extends CpgPass(cpg) {
  import ContainsEdgePass.{destinationTypes, sourceTypes}

  override def run(): Iterator[DiffGraph] = {
    val sourceVerticesTraversal = cpg.scalaGraph.V.hasLabel(sourceTypes.head, sourceTypes.tail: _*)
    val sourceVerticesIterator = new Steps(sourceVerticesTraversal).toIterator
    new ParallelIteratorExecutor(sourceVerticesIterator).map(source => perSource(source.asInstanceOf[nodes.AstNode]))
  }

  private def perSource(source: nodes.AstNode): DiffGraph = {
    val dstGraph = DiffGraph.newBuilder

    source.start
      .walkAstUntilReaching(sourceTypes)
      .sideEffect { destination =>
        if (destinationTypes.contains(destination.label))
          dstGraph.addEdgeInOriginal(source, destination, EdgeTypes.CONTAINS)
      }
      .iterate()

    dstGraph.build()
  }
}

object ContainsEdgePass {

  private val destinationTypes = List(
    NodeTypes.BLOCK,
    NodeTypes.IDENTIFIER,
    NodeTypes.RETURN,
    NodeTypes.METHOD,
    NodeTypes.TYPE_DECL,
    NodeTypes.CALL,
    NodeTypes.LITERAL,
    NodeTypes.METHOD_REF,
    NodeTypes.UNKNOWN
  )

  private val sourceTypes = List(
    NodeTypes.METHOD,
    NodeTypes.TYPE_DECL,
    NodeTypes.FILE
  )

}
