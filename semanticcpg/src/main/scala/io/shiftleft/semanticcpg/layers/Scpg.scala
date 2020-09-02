package io.shiftleft.semanticcpg.layers

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{Languages, NodeTypes}
import io.shiftleft.passes.CpgPassBase
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.BindingMethodOverridesPass
import io.shiftleft.semanticcpg.passes.cfgdominator.CfgDominatorPass
import io.shiftleft.semanticcpg.passes.codepencegraph.CdgPass
import io.shiftleft.semanticcpg.passes.compat.argumentcompat.ArgumentCompat
import io.shiftleft.semanticcpg.passes.compat.bindingtablecompat.BindingTableCompat
import io.shiftleft.semanticcpg.passes.compat.methodinstcompat.MethodInstCompat
import io.shiftleft.semanticcpg.passes.containsedges.ContainsEdgePass
import io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc.{MethodStubCreator, TypeDeclStubCreator}
import io.shiftleft.semanticcpg.passes.linking.calllinker.CallLinker
import io.shiftleft.semanticcpg.passes.linking.capturinglinker.CapturingLinker
import io.shiftleft.semanticcpg.passes.linking.filecompat.{FileLinker, FileNameCompat}
import io.shiftleft.semanticcpg.passes.linking.linker.Linker
import io.shiftleft.semanticcpg.passes.linking.memberaccesslinker.MemberAccessLinker
import io.shiftleft.semanticcpg.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.semanticcpg.passes.methodexternaldecorator.MethodExternalDecoratorPass
import io.shiftleft.semanticcpg.passes.namespacecreator.NamespaceCreator
import io.shiftleft.semanticcpg.passes.receiveredges.ReceiverEdgePass
import io.shiftleft.semanticcpg.passes.trim.TrimPass

object Scpg {
  val overlayName: String = "semanticcpg"
  val description: String = "linked code property graph (OSS)"

  def defaultOpts = new LayerCreatorOptions()
}

class Scpg(optionsUnused: LayerCreatorOptions = null) extends LayerCreator {

  override val overlayName: String = Scpg.overlayName
  override val description: String = Scpg.description

  override def create(context: LayerCreatorContext, serializeInverse: Boolean): Unit = {
    val cpg = context.cpg
    val language = cpg.metaData.language.headOption
      .getOrElse(throw new Exception("Meta node missing."))

    val enhancementExecList = createEnhancementExecList(cpg, language)
    enhancementExecList.zipWithIndex.foreach {
      case (pass, index) =>
        val serializedCpg = initSerializedCpg(context.outputDir, pass.name, index)
        pass.createApplySerializeAndStore(serializedCpg, serializeInverse)
        serializedCpg.close()
    }
  }

  private def createEnhancementExecList(cpg: Cpg, language: String): Iterator[CpgPassBase] = {
    language match {
      case Languages.JAVA =>
        Iterator(
          new ArgumentCompat(cpg),
          new MethodInstCompat(cpg),
          new ReceiverEdgePass(cpg),
          new MethodDecoratorPass(cpg),
          new CapturingLinker(cpg),
          new Linker(cpg),
          new FileNameCompat(cpg),
          new FileLinker(cpg),
          new BindingTableCompat(cpg),
          new BindingMethodOverridesPass(cpg),
          new CallLinker(cpg),
          new MemberAccessLinker(cpg),
          new MethodExternalDecoratorPass(cpg),
          new ContainsEdgePass(cpg),
          new NamespaceCreator(cpg),
          new CfgDominatorPass(cpg),
          new CdgPass(cpg),
          new TrimPass(cpg),
        )
      case Languages.C =>
        Iterator(
          new TypeDeclStubCreator(cpg),
          new MethodStubCreator(cpg),
          new MethodDecoratorPass(cpg),
          new CapturingLinker(cpg),
          new Linker(cpg),
          new FileNameCompat(cpg),
          new FileLinker(cpg),
          new BindingTableCompat(cpg),
          new BindingMethodOverridesPass(cpg),
          new CallLinker(cpg),
          new MemberAccessLinker(cpg),
          new MethodExternalDecoratorPass(cpg),
          new ContainsEdgePass(cpg),
          new NamespaceCreator(cpg),
          new CfgDominatorPass(cpg),
          new CdgPass(cpg),
        )
      case Languages.LLVM =>
        Iterator(
          new TypeDeclStubCreator(cpg),
          new MethodStubCreator(cpg),
          new MethodDecoratorPass(cpg),
          new CapturingLinker(cpg),
          new Linker(cpg),
          new FileNameCompat(cpg),
          new FileLinker(cpg),
          new BindingTableCompat(cpg),
          new BindingMethodOverridesPass(cpg),
          new CallLinker(cpg),
          new MemberAccessLinker(cpg),
          new MethodExternalDecoratorPass(cpg),
          new ContainsEdgePass(cpg),
          new NamespaceCreator(cpg),
          new CfgDominatorPass(cpg),
          new CdgPass(cpg),
        )
    }
  }

  override def probe(cpg: Cpg): Boolean = {
    cpg.graph.nodes(NodeTypes.METHOD_PARAMETER_OUT).hasNext
  }
}
