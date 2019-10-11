package io.shiftleft.semanticcpg.passes.containsedges

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.utils.ExpandTo

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class ContainsEdgePass(cpg: Cpg) extends CpgPass(cpg) {
  import ContainsEdgePass.{destinationTypes, sourceTypes}

  override def run(): Iterator[DiffGraph] = {
    val sourceVerticesTraversal = cpg.graph.V.hasLabel(sourceTypes.head, sourceTypes.tail: _*)
    val sourceVerticesIterator = new Steps(sourceVerticesTraversal).toIterator
    new ParallelIteratorExecutor(sourceVerticesIterator).map(perSource)
  }

  private def perSource(source: Vertex): DiffGraph = {
    val dstGraph = new DiffGraph()

    ExpandTo
      .walkAST(
        source.start
          .out(EdgeTypes.AST)
          .until(v => v.hasLabel(sourceTypes.head, sourceTypes.tail: _*)))
      .sideEffect(destination =>
        if (destinationTypes.contains(destination.label)) {
          dstGraph.addEdgeInOriginal(source.asInstanceOf[StoredNode],
                                     destination.asInstanceOf[StoredNode],
                                     EdgeTypes.CONTAINS)
      })
      .iterate()

    dstGraph
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
