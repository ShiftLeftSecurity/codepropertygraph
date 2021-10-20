package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.datastructures.Global
import io.shiftleft.c2cpg.datastructures.Global.HeaderIncludeResult
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.passes.{DiffGraph, ForkJoinParallelCpgPass, IntervalKeyPool}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.x2cpg.Ast

class HeaderContentPass(cpg: Cpg, keyPool: IntervalKeyPool)
    extends ForkJoinParallelCpgPass[HeaderIncludeResult](cpg, keyPool = Some(keyPool)) {

  override def generateParts(): Array[HeaderIncludeResult] = Global.headerAstCache.values.toArray

  override def runOnPart(diffGraph: DiffGraph.Builder, headerIncludeResult: HeaderIncludeResult): Unit = {
    headerIncludeResult.ast
      .collect {
        case a if a.root.isDefined => a
      }
      .foreach { ast =>
        Ast.storeInDiffGraph(ast, diffGraph)
        cpg.method
          .name(NamespaceTraversal.globalNamespaceName)
          .filename(headerIncludeResult.includedInFilenames.toSeq: _*)
          .astChildren
          .isBlock
          .foreach(diffGraph.addEdgeFromOriginal(_, ast.root.get, EdgeTypes.AST))
      }
  }
}
