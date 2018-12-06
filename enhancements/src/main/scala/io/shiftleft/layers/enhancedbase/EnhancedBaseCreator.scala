package io.shiftleft.layers.enhancedbase

import gremlin.scala.ScalaGraph
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.passes.CpgPass
import io.shiftleft.passes.linking.linker.Linker
import io.shiftleft.passes.linking.memberaccesslinker.MemberAccessLinker
import io.shiftleft.passes.namespacecreator.NamespaceCreator
import io.shiftleft.passes.linking.callargumentlinker.CallArgumentLinker
import io.shiftleft.passes.containsedges.ContainsEdgePass
import io.shiftleft.passes.languagespecific.fuzzyc.MethodStubCreator
import io.shiftleft.passes.linking.capturinglinker.CapturingLinker
import io.shiftleft.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.passes.receiveredges.ReceiverEdgePass

class EnhancedBaseCreator(graph: ScalaGraph, language: String) {

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
          new CallArgumentLinker(graph),
          new ContainsEdgePass(graph),
          new NamespaceCreator(graph),
        )
      case Languages.FUZZYC =>
        List(
          new MethodStubCreator(graph),
          new ReceiverEdgePass(graph),
          new MethodDecoratorPass(graph),
          new CapturingLinker(graph),
          new Linker(graph),
          new MemberAccessLinker(graph),
          new CallArgumentLinker(graph),
          new ContainsEdgePass(graph),
          new NamespaceCreator(graph),
        )
      case _ =>
        List(
          new ReceiverEdgePass(graph),
          new MethodDecoratorPass(graph),
          new CapturingLinker(graph),
          new Linker(graph),
          new MemberAccessLinker(graph),
          new CallArgumentLinker(graph),
          new ContainsEdgePass(graph),
          new NamespaceCreator(graph),
        )
    }
  }

  def create(): Unit = {
    enhancementExecList.foreach(_.executeAndApply)
  }
}
