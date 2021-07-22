package io.shiftleft.semanticcpg.layers

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{Languages, NodeTypes}
import io.shiftleft.passes.CpgPassV2
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.cfgdominator.CfgDominatorPass
import io.shiftleft.semanticcpg.passes.codepencegraph.CdgPass
import io.shiftleft.semanticcpg.passes.containsedges.ContainsEdgePass
import io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc.MethodStubCreator
import io.shiftleft.semanticcpg.passes.linking.calllinker.StaticCallLinker
import io.shiftleft.semanticcpg.passes.linking.filecompat.FileNameCompat
import io.shiftleft.semanticcpg.passes.linking.linker.Linker
import io.shiftleft.semanticcpg.passes.linking.memberaccesslinker.MemberAccessLinker
import io.shiftleft.semanticcpg.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.semanticcpg.passes.methodexternaldecorator.MethodExternalDecoratorPass
import io.shiftleft.semanticcpg.passes.namespacecreator.NamespaceCreator
import io.shiftleft.semanticcpg.passes.receiveredges.ReceiverEdgePass
import io.shiftleft.semanticcpg.passes.trim.TrimPass
import io.shiftleft.semanticcpg.passes.typenodes.TypeDeclStubCreator
import io.shiftleft.semanticcpg.passes.{CfgCreationPass, FileCreationPass}

import scala.annotation.nowarn

object Scpg {
  val overlayName: String = "semanticcpg"
  val description: String = "linked code property graph (OSS)"

  def defaultOpts = new LayerCreatorOptions()
}

@nowarn
class Scpg(optionsUnused: LayerCreatorOptions = null) extends LayerCreator {

  override val overlayName: String = Scpg.overlayName
  override val description: String = Scpg.description

  override def create(context: LayerCreatorContext, storeUndoInfo: Boolean): Unit = {
    val cpg = context.cpg
    val language = cpg.metaData.language.headOption
      .getOrElse(throw new Exception("Meta node missing."))

    val enhancementExecList = createEnhancementExecList(cpg, language)
    runPasses(enhancementExecList, context, storeUndoInfo)
  }

  private def createEnhancementExecList(cpg: Cpg, language: String): List[CpgPassV2[_]] = {
    language match {
      case Languages.JAVA =>
        List(
          new ReceiverEdgePass(cpg),
          new MethodDecoratorPass(cpg),
          new Linker(cpg),
          new FileNameCompat(cpg),
          new FileCreationPass(cpg),
          new StaticCallLinker(cpg),
          new MemberAccessLinker(cpg),
          new MethodExternalDecoratorPass(cpg),
          new ContainsEdgePass(cpg),
          new NamespaceCreator(cpg),
          new CfgDominatorPass(cpg),
          new CdgPass(cpg),
          new TrimPass(cpg),
        )
      case Languages.C =>
        List(
          new TypeDeclStubCreator(cpg),
          new MethodStubCreator(cpg),
          new MethodDecoratorPass(cpg),
          new Linker(cpg),
          new FileCreationPass(cpg),
          new StaticCallLinker(cpg),
          new MemberAccessLinker(cpg),
          new MethodExternalDecoratorPass(cpg),
          new ContainsEdgePass(cpg),
          new NamespaceCreator(cpg),
          new CfgDominatorPass(cpg),
          new CdgPass(cpg),
        )
      case Languages.LLVM =>
        List(
          new TypeDeclStubCreator(cpg),
          new MethodStubCreator(cpg),
          new MethodDecoratorPass(cpg),
          new Linker(cpg),
          new FileNameCompat(cpg),
          new FileCreationPass(cpg),
          new StaticCallLinker(cpg),
          new MemberAccessLinker(cpg),
          new MethodExternalDecoratorPass(cpg),
          new ContainsEdgePass(cpg),
          new NamespaceCreator(cpg),
          new CfgDominatorPass(cpg),
          new CdgPass(cpg),
        )
      case Languages.JAVASCRIPT =>
        List(
          new CfgCreationPass(cpg),
          new MethodStubCreator(cpg),
          new MethodDecoratorPass(cpg),
          new Linker(cpg),
          new FileNameCompat(cpg),
          new FileCreationPass(cpg),
          new ContainsEdgePass(cpg),
          new MethodExternalDecoratorPass(cpg),
          new CfgDominatorPass(cpg),
          new CdgPass(cpg),
          new NamespaceCreator(cpg),
        )
      case Languages.PYTHON =>
        List(
          new MethodStubCreator(cpg),
          new MethodDecoratorPass(cpg),
          new Linker(cpg),
          new FileNameCompat(cpg),
          new FileCreationPass(cpg),
          new ContainsEdgePass(cpg),
          new MethodExternalDecoratorPass(cpg),
          new CfgDominatorPass(cpg),
          new CdgPass(cpg),
          new NamespaceCreator(cpg),
        )
      case Languages.FUZZY_TEST_LANG =>
        List(
          new CfgCreationPass(cpg),
          new MethodStubCreator(cpg),
          new MethodDecoratorPass(cpg),
          new Linker(cpg),
          new FileNameCompat(cpg),
          new FileCreationPass(cpg),
          new ContainsEdgePass(cpg),
          new MethodExternalDecoratorPass(cpg),
          new CfgDominatorPass(cpg),
          new CdgPass(cpg),
          new NamespaceCreator(cpg),
        )
      case _ => List()
    }
  }

  override def probe(cpg: Cpg): Boolean = {
    cpg.graph.nodes(NodeTypes.METHOD_PARAMETER_OUT).hasNext
  }
}
