package io.shiftleft.semanticcpg.passes.containsedges

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language.NodeTypeDeco

import scala.jdk.CollectionConverters._

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class ContainsEdgePass(cpg: Cpg) extends ParallelCpgPass[nodes.AstNode](cpg) {
  import ContainsEdgePass.{destinationTypes, sourceTypes}

  override def nodeIterator: Iterator[nodes.AstNode] =
    cpg.graph.nodesByLabel(sourceTypes: _*).asScala.map(_.asInstanceOf[nodes.AstNode])

  override def runOnNode(source: nodes.AstNode): DiffGraph = {
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
    NodeTypes.FIELD_IDENTIFIER,
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
