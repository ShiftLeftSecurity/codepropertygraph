package io.shiftleft.layers.enhancedbase

import gremlin.scala.ScalaGraph
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.passes.{CpgPass, CpgPassRunner}
import io.shiftleft.passes.linking.linker.Linker
import io.shiftleft.passes.linking.memberaccesslinker.MemberAccessLinker
import io.shiftleft.passes.namespacecreator.NamespaceCreator
import io.shiftleft.SerializedCpg
import io.shiftleft.semanticsloader.ArgumentDefs
import io.shiftleft.passes.containsedges.ContainsEdgePass
import io.shiftleft.passes.languagespecific.fuzzyc.{MethodStubCreator, TypeDeclStubCreator}
import io.shiftleft.passes.linking.capturinglinker.CapturingLinker
import io.shiftleft.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.passes.propagateedges.PropagateEdgePass
import io.shiftleft.passes.reachingdef.ReachingDefPass
import io.shiftleft.passes.receiveredges.ReceiverEdgePass

class EnhancedBaseCreator(graph: ScalaGraph,
                          language: String,
                          serializedCpg: SerializedCpg,
                          argumentDefs: ArgumentDefs) {

  private val runner = new CpgPassRunner(serializedCpg)

  private val enhancementExecList = createEnhancementExecList(language)

  private def createEnhancementExecList(language: String): List[CpgPass] = {
    language match {
      case Languages.JAVA =>
        List(
          new ReceiverEdgePass(graph),
          new MethodDecoratorPass(graph),
          new PropagateEdgePass(graph, argumentDefs),
          new CapturingLinker(graph),
          new Linker(graph),
          new MemberAccessLinker(graph),
          new ContainsEdgePass(graph),
          new NamespaceCreator(graph),
          new ReachingDefPass(graph),
        )
      case Languages.C =>
        List(
          new TypeDeclStubCreator(graph),
          new MethodStubCreator(graph),
          new ReceiverEdgePass(graph),
          new MethodDecoratorPass(graph),
          new PropagateEdgePass(graph, argumentDefs),
          new CapturingLinker(graph),
          new Linker(graph),
          new MemberAccessLinker(graph),
          new ContainsEdgePass(graph),
          new NamespaceCreator(graph),
          new ReachingDefPass(graph),
        )
      case _ =>
        List(
          new ReceiverEdgePass(graph),
          new MethodDecoratorPass(graph),
          new PropagateEdgePass(graph, argumentDefs),
          new CapturingLinker(graph),
          new Linker(graph),
          new MemberAccessLinker(graph),
          new ContainsEdgePass(graph),
          new NamespaceCreator(graph),
          new ReachingDefPass(graph),
        )
    }
  }

  def create(): Unit = {
    enhancementExecList.foreach(runner.createStoreAndApplyOverlay(_))
  }
}
