package io.shiftleft.semanticcpg.passes.containsedges

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.passes.{ConcurrentWriterCpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  *
  * The pass creates so called `CONTAINS` edges, which serve as shortcuts that
  * allow to traverse from an AST node to the METHOD, TYPE_DECL, or FILE
  * it is contained in.
  */
class ContainsEdgePass(cpg: Cpg) extends ConcurrentWriterCpgPass[AstNode](cpg) {

  private val sourceTypes = List(
    NodeTypes.METHOD,
    NodeTypes.TYPE_DECL,
    NodeTypes.FILE
  )

  def isSourceType(node: StoredNode): Boolean = node match {
    case _: Method | _: TypeDecl | _: File => true
    case _                                 => false
  }

  def isDestinationType(node: StoredNode): Boolean = node match {
    case _: Block | _: Identifier | _: FieldIdentifier | _: Return | _: Method | _: TypeDecl | _: Call | _: Literal |
        _: MethodRef | _: TypeRef | _: ControlStructure | _: JumpTarget | _: Unknown =>
      true
    case _ => false
  }

  override def generateParts(): Array[AstNode] =
    cpg.graph.nodes(sourceTypes: _*).asScala.map(_.asInstanceOf[AstNode]).toArray

  override def runOnPart(dstGraph: DiffGraph.Builder, source: AstNode): Unit = {
    // We do not check here whether the AST is free of cycles, that is, we assume
    // that it is indeed a tree. If this is not the case, then the loop will run
    // until an out of memory condition is reached, resulting in a crash.
    val queue = mutable.ArrayDeque[AstNode](source)
    while (queue.nonEmpty) {
      val parent = queue.removeHead()
      for (child <- parent.astChildren) {
        if (isDestinationType(child)) dstGraph.addEdge(source, child, EdgeTypes.CONTAINS)
        if (!isSourceType(child)) queue.append(child)
      }
    }
  }
}
