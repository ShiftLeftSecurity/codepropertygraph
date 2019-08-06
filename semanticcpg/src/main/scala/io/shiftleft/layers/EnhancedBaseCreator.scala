package io.shiftleft.layers

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.passes.containsedges.ContainsEdgePass
import io.shiftleft.passes.languagespecific.fuzzyc.{MethodStubCreator, TypeDeclStubCreator}
import io.shiftleft.passes.linking.capturinglinker.CapturingLinker
import io.shiftleft.passes.linking.linker.Linker
import io.shiftleft.passes.linking.memberaccesslinker.MemberAccessLinker
import io.shiftleft.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.passes.namespacecreator.NamespaceCreator
import io.shiftleft.passes.receiveredges.ReceiverEdgePass
import io.shiftleft.passes.CpgPass
import io.shiftleft.passes.methodexternaldecorator.MethodExternalDecoratorPass

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
