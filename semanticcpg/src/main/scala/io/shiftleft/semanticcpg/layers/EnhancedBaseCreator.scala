package io.shiftleft.semanticcpg.layers

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.passes.CpgPass
import io.shiftleft.semanticcpg.passes.containsedges.ContainsEdgePass
import io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc.{MethodStubCreator, TypeDeclStubCreator}
import io.shiftleft.semanticcpg.passes.linking.calllinker.CallLinker
import io.shiftleft.semanticcpg.passes.linking.capturinglinker.CapturingLinker
import io.shiftleft.semanticcpg.passes.linking.linker.Linker
import io.shiftleft.semanticcpg.passes.linking.memberaccesslinker.MemberAccessLinker
import io.shiftleft.semanticcpg.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.semanticcpg.passes.methodexternaldecorator.MethodExternalDecoratorPass
import io.shiftleft.semanticcpg.passes.namespacecreator.NamespaceCreator
import io.shiftleft.semanticcpg.passes.receiveredges.ReceiverEdgePass

class EnhancedBaseCreator(cpg: Cpg, language: String, serializedCpg: SerializedCpg) {
  private val enhancementExecList = createEnhancementExecList(language)

  private def createEnhancementExecList(language: String): List[CpgPass] = {
    language match {
      case Languages.JAVA =>
        List(
          new ReceiverEdgePass(cpg),
          new MethodDecoratorPass(cpg),
          new CapturingLinker(cpg),
          new Linker(cpg),
          new CallLinker(cpg),
          new MemberAccessLinker(cpg),
          new MethodExternalDecoratorPass(cpg),
          new ContainsEdgePass(cpg),
          new NamespaceCreator(cpg)
        )
      case Languages.C =>
        List(
          new TypeDeclStubCreator(cpg),
          new MethodStubCreator(cpg),
          new MethodDecoratorPass(cpg),
          new CapturingLinker(cpg),
          new Linker(cpg),
          new CallLinker(cpg),
          new MemberAccessLinker(cpg),
          new MethodExternalDecoratorPass(cpg),
          new ContainsEdgePass(cpg),
          new NamespaceCreator(cpg)
        )
      case _ =>
        List(
          new ReceiverEdgePass(cpg),
          new MethodDecoratorPass(cpg),
          new CapturingLinker(cpg),
          new Linker(cpg),
          new CallLinker(cpg),
          new MemberAccessLinker(cpg),
          new MethodExternalDecoratorPass(cpg),
          new ContainsEdgePass(cpg),
          new NamespaceCreator(cpg)
        )
    }
  }

  def create(): Unit = {
    enhancementExecList.foreach(_.createApplySerializeAndStore(serializedCpg))
  }
}
