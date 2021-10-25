package io.shiftleft.c2cpg.astcreation

import io.shiftleft.c2cpg.C2Cpg
import io.shiftleft.c2cpg.datastructures.Stack._
import io.shiftleft.c2cpg.datastructures.{Cache, Global, Scope}
import io.shiftleft.c2cpg.parser.FileDefaults
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EvaluationStrategies, NodeTypes}
import io.shiftleft.passes.DiffGraph
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.x2cpg.Ast
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class AstCreator(val filename: String,
                 val config: C2Cpg.Config,
                 val global: Global,
                 val diffGraph: DiffGraph.Builder,
                 val parserResult: IASTTranslationUnit)
    extends AstForTypesCreator
    with AstForFunctionsCreator
    with AstForPrimitivesCreator
    with AstForStatementsCreator
    with AstForExpressionsCreator
    with AstNodeBuilder
    with AstCreatorHelper
    with MacroHandler {

  protected val logger: Logger = LoggerFactory.getLogger(classOf[AstCreator])

  protected val scope: Scope[String, (NewNode, String), NewNode] = new Scope()

  protected val usingDeclarationMappings: mutable.Map[String, String] = mutable.HashMap.empty

  // TypeDecls with their bindings (with their refs) for lambdas and methods are not put in the AST
  // where the respective nodes are defined. Instead we put them under the parent TYPE_DECL in which they are defined.
  // To achieve this we need this extra stack.
  protected val methodAstParentStack: Stack[NewNode] = new Stack()

  def createAst(): Unit =
    Ast.storeInDiffGraph(astForFile(parserResult), diffGraph)

  private def astForFile(parserResult: IASTTranslationUnit): Ast = {
    val cpgFile = Ast(NewFile().name(filename).order(0))
    val translationUnitAst = astForTranslationUnit(parserResult)

    val ast = cpgFile.withChild(translationUnitAst)
    if (config.includeComments) {
      val commentsAsts = parserResult.getComments.map(comment => astForComment(comment)).toIndexedSeq
      ast.withChildren(commentsAsts)
    } else {
      ast
    }
  }

  private def createFakeMethod(name: String,
                               fullName: String,
                               path: String,
                               iASTTranslationUnit: IASTTranslationUnit): Ast = {
    val fakeGlobalMethod =
      NewMethod()
        .name(name)
        .code(name)
        .fullName(fullName)
        .filename(path)
        .astParentType(NodeTypes.NAMESPACE_BLOCK)
        .astParentFullName(fullName)

    methodAstParentStack.push(fakeGlobalMethod)
    scope.pushNewScope(fakeGlobalMethod)

    val blockNode = NewBlock()
      .order(1)
      .argumentIndex(1)
      .typeFullName("ANY")

    var currOrder = 1
    val declsAsts = iASTTranslationUnit.getDeclarations.flatMap { stmt =>
      val linenumber = line(stmt)
      val columnnumber = column(stmt)
      val filename = fileName(stmt)

      val r =
        if (FileDefaults
              .isHeaderFile(filename) && filename != this.filename && linenumber.isDefined && columnnumber.isDefined) {
          global.headerAsts
            .getOrElseUpdate(filename, new Cache())
            .getOrElseUpdate((linenumber.get, columnnumber.get), {
              astsForDeclaration(stmt, currOrder).foreach(Ast.storeInDiffGraph(_, diffGraph))
              true
            })
          Seq.empty
        } else {
          astsForDeclaration(stmt, currOrder)
        }
      currOrder = currOrder + r.length
      r
    }.toSeq

    val methodReturn = NewMethodReturn()
      .code("RET")
      .evaluationStrategy(EvaluationStrategies.BY_VALUE)
      .typeFullName("ANY")
      .order(2)

    Ast(fakeGlobalMethod)
      .withChild(Ast(blockNode).withChildren(declsAsts))
      .withChild(Ast(methodReturn))
  }

  private def astForTranslationUnit(iASTTranslationUnit: IASTTranslationUnit): Ast = {
    val absolutePath = new java.io.File(iASTTranslationUnit.getFilePath).toPath.toAbsolutePath.normalize().toString
    val name = NamespaceTraversal.globalNamespaceName
    val fullName = MetaDataPass.getGlobalNamespaceBlockFullName(Some(absolutePath))
    val namespaceBlock = NewNamespaceBlock()
      .name(name)
      .fullName(fullName)
      .filename(absolutePath)
      .order(1)
    methodAstParentStack.push(namespaceBlock)
    Ast(namespaceBlock).withChild(createFakeMethod(name, fullName, absolutePath, iASTTranslationUnit))
  }

}
