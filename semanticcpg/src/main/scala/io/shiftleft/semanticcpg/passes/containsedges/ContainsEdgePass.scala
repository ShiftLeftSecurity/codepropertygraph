package io.shiftleft.semanticcpg.passes.containsedges

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.AstNode
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.passes.{DiffGraph, NewStylePass}
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class ContainsEdgePass(cpg: Cpg) extends NewStylePass[AstNode](cpg) {
  import ContainsEdgePass.{destinationTypes, sourceTypes}

  override def generateParts(): Array[AstNode] =
    cpg.graph.nodes(sourceTypes: _*).asScala.map(_.asInstanceOf[AstNode]).toArray

  override def runOnPart(dstGraph: DiffGraph.Builder, source: AstNode): Unit = {
    source
      .walkAstUntilReaching(sourceTypes)
      .sideEffect { destination =>
        if (destinationTypes.contains(destination.label)) {
          dstGraph.addEdgeInOriginal(source, destination, EdgeTypes.CONTAINS)
        }
      }
      .iterate()
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
