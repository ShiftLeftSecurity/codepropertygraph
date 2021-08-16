package io.shiftleft.c2cpg.astcreation

import io.shiftleft.c2cpg.C2Cpg
import io.shiftleft.c2cpg.datastructures.Stack._
import io.shiftleft.c2cpg.datastructures.{Global, Scope}
import io.shiftleft.c2cpg.utils.IOUtils
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.passes.DiffGraph
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.x2cpg.Ast
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class AstCreator(val filename: String, val global: Global, val config: C2Cpg.Config, val diffGraph: DiffGraph.Builder)
    extends AstForTypesCreator
    with AstForFunctionsCreator
    with AstForPrimitivesCreator
    with AstForStatementsCreator
    with AstForExpressionsCreator
    with AstNodeBuilder
    with AstCreatorHelper {

  protected val logger: Logger = LoggerFactory.getLogger(classOf[AstCreator])

  protected val fileLines: Seq[Int] = IOUtils.linesInFile(IOUtils.readFile(filename)).map(_.length)

  protected val scope: Scope[String, (NewNode, String), NewNode] = new Scope()

  protected val usingDeclarationMappings: mutable.Map[String, String] = mutable.HashMap.empty

  // Lambdas are not put in the AST where they are defined.
  // Instead we put them directly under the TYPE_DECL (or METHOD of not parent TYPE_DECL is available)
  // in which they are defined. To achieve this we need this extra stack.
  protected val methodAstParentStack: Stack[NewNode] = new Stack()

  def createAst(parserResult: IASTTranslationUnit): Unit =
    Ast.storeInDiffGraph(astForFile(parserResult), diffGraph)

  private def astForFile(parserResult: IASTTranslationUnit): Ast = {
    val cpgFile = Ast(NewFile(name = filename, order = 0))
    val translationUnitAst = astForTranslationUnit(parserResult)

    var currOrder = 1
    val declsAsts = parserResult.getDeclarations.flatMap { stmt =>
      val r = astsForDeclaration(stmt, currOrder)
      currOrder = currOrder + r.length
      r
    }.toIndexedSeq

    val ast = cpgFile.withChild(translationUnitAst.withChildren(declsAsts))
    if (config.includeComments) {
      val commentsAsts = parserResult.getComments.map(comment => astForComment(comment)).toIndexedSeq
      ast.withChildren(commentsAsts)
    } else {
      ast
    }
  }

  private def astForTranslationUnit(iASTTranslationUnit: IASTTranslationUnit): Ast = {
    val absolutePath = new java.io.File(iASTTranslationUnit.getFilePath).toPath.toAbsolutePath.normalize().toString
    val namespaceBlock = NewNamespaceBlock()
      .name(NamespaceTraversal.globalNamespaceName)
      .fullName(MetaDataPass.getGlobalNamespaceBlockFullName(Some(absolutePath)))
      .filename(absolutePath)
      .order(1)
    methodAstParentStack.push(namespaceBlock)
    Ast(namespaceBlock)
  }

}
