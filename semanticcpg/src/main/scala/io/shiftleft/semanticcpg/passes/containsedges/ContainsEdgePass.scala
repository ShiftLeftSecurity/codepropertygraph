package io.shiftleft.semanticcpg.passes.containsedges

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.AstNode
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.passes.{CpgPassV2, DiffGraph}
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class ContainsEdgePass(cpg: Cpg) extends CpgPassV2[AstNode] {
  import ContainsEdgePass.{destinationTypes, sourceTypes}

  override def partIterator: Iterator[AstNode] =
    cpg.graph.nodes(sourceTypes: _*).asScala.map(_.asInstanceOf[AstNode])

  override def runOnPart(source: AstNode): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    source
      .walkAstUntilReaching(sourceTypes)
      .sideEffect { destination =>
        if (destinationTypes.contains(destination.label)) {
          dstGraph.addEdgeInOriginal(source, destination, EdgeTypes.CONTAINS)
        }
      }
      .iterate()

    Iterator(dstGraph.build())
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
    NodeTypes.TYPE_REF,
    NodeTypes.CONTROL_STRUCTURE,
    NodeTypes.JUMP_TARGET,
    NodeTypes.UNKNOWN
  )

  private val sourceTypes = List(
    NodeTypes.METHOD,
    NodeTypes.TYPE_DECL,
    NodeTypes.FILE
  )

}
