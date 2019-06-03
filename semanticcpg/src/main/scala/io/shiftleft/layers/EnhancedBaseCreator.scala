package io.shiftleft.layers

import gremlin.scala.ScalaGraph
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.passes.containsedges.ContainsEdgePass
import io.shiftleft.passes.languagespecific.fuzzyc.{MethodStubCreator, TypeDeclStubCreator}
import io.shiftleft.passes.linking.capturinglinker.CapturingLinker
import io.shiftleft.passes.linking.linker.Linker
import io.shiftleft.passes.linking.memberaccesslinker.MemberAccessLinker
import io.shiftleft.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.passes.namespacecreator.NamespaceCreator
import io.shiftleft.passes.receiveredges.ReceiverEdgePass
import io.shiftleft.passes.{CpgPass, CpgPassRunner}

class EnhancedBaseCreator(graph: ScalaGraph, language: String, serializedCpg: SerializedCpg) {
  private val runner = new CpgPassRunner(serializedCpg)
  private val enhancementExecList = createEnhancementExecList(language)

  private def createEnhancementExecList(language: String): List[CpgPass] = {
    language match {
      case Languages.JAVA =>
        List(
          new ReceiverEdgePass(graph),
          new MethodDecoratorPass(graph),
          new CapturingLinker(graph),
          new Linker(graph),
          new MemberAccessLinker(graph),
          new ContainsEdgePass(graph),
          new NamespaceCreator(graph)
        )
      case Languages.C =>
        List(
          new TypeDeclStubCreator(graph),
          new MethodStubCreator(graph),
          new MethodDecoratorPass(graph),
          new CapturingLinker(graph),
          new Linker(graph),
          new MemberAccessLinker(graph),
          new ContainsEdgePass(graph),
          new NamespaceCreator(graph)
        )
      case _ =>
        List(
          new ReceiverEdgePass(graph),
          new MethodDecoratorPass(graph),
          new CapturingLinker(graph),
          new Linker(graph),
          new MemberAccessLinker(graph),
          new ContainsEdgePass(graph),
          new NamespaceCreator(graph)
        )
    }
  }

  def create(): Unit = {
    enhancementExecList.foreach(runner.createStoreAndApplyOverlay(_))
  }
}
