package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.parser.FileDefaults
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{
  HasFilename,
  NewBlock,
  NewFile,
  NewMethod,
  NewMethodReturn,
  NewNamespaceBlock,
  StoredNode
}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, EvaluationStrategies, NodeTypes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.x2cpg.Ast
import overflowdb.traversal.Traversal

class HeaderContentPass(cpg: Cpg, hasHeaderContent: Boolean, projectPath: String) extends CpgPass(cpg) {

  override def run(): Iterator[DiffGraph] = {
    if (!hasHeaderContent) {
      Iterator.empty
    } else {
      val dstGraph = DiffGraph.newBuilder

      val absolutePath = new java.io.File(projectPath).toPath.toAbsolutePath.normalize().toString
      val filename = s"$absolutePath:<includes>"
      val globalName = NamespaceTraversal.globalNamespaceName

      val includesFile = NewFile().name(filename).order(0)

      val fullName = MetaDataPass.getGlobalNamespaceBlockFullName(Some(filename))
      val namespaceBlock = NewNamespaceBlock()
        .name(globalName)
        .fullName(fullName)
        .filename(filename)
        .order(1)

      val fakeGlobalIncludesMethod =
        NewMethod()
          .name(globalName)
          .code(globalName)
          .fullName(fullName)
          .filename(filename)
          .astParentType(NodeTypes.NAMESPACE_BLOCK)
          .astParentFullName(fullName)

      val blockNode = NewBlock()
        .order(1)
        .argumentIndex(1)
        .typeFullName("ANY")

      val methodReturn = NewMethodReturn()
        .code("RET")
        .evaluationStrategy(EvaluationStrategies.BY_VALUE)
        .typeFullName("ANY")
        .order(2)

      val ast = Ast(includesFile).withChild(
        Ast(namespaceBlock).withChild(
          Ast(fakeGlobalIncludesMethod).withChild(Ast(blockNode)).withChild(Ast(methodReturn))))

      Ast.storeInDiffGraph(ast, dstGraph)

      Traversal(cpg.graph.nodes()).whereNot(_.inE(EdgeTypes.AST)).foreach {
        case srcNode: HasFilename if FileDefaults.isHeaderFile(srcNode.filename) =>
          dstGraph.addEdgeToOriginal(blockNode, srcNode.asInstanceOf[StoredNode], EdgeTypes.AST)
        case _ =>
      }

      Iterator(dstGraph.build())
    }
  }

}
