package io.shiftleft.cpgenhancements

import gremlin.scala.ScalaGraph
import io.shiftleft.cpgenhancements.generic.methodinstlinker.MethodInstLinker
import io.shiftleft.cpgenhancements.generic.capturinglinker.CapturingLinker
import io.shiftleft.cpgenhancements.generic.linker.Linker
import io.shiftleft.cpgenhancements.generic.memberaccesslinker.MemberAccessLinker
import io.shiftleft.cpgenhancements.generic.methoddecorator.MethodDecorator
import io.shiftleft.cpgenhancements.generic.namspacecreator.NamespaceCreator

/* singleton instance for convenience */
object CpgEnhancements extends CpgEnhancements

class CpgEnhancements {

  def run(graph: ScalaGraph) = {
    policyIndependentEnhancements(graph)
  }

  private def linkingEnhancements(graph: ScalaGraph): Unit = {
    val capturingLinker = new CapturingLinker(graph)
    capturingLinker.executeAndApply()

    val linker = new Linker(graph)
    linker.executeAndApply()

    val memberAccessLinker = new MemberAccessLinker(graph)
    memberAccessLinker.executeAndApply()
  }

  private def policyIndependentEnhancements(graph: ScalaGraph) = {
    val methodDecorator = new MethodDecorator(graph)
    methodDecorator.executeAndApply()

    linkingEnhancements(graph)

    val namespaceCreator = new NamespaceCreator(graph)
    namespaceCreator.executeAndApply()

    val methodInstLinker = new MethodInstLinker(graph)
    methodInstLinker.executeAndApply()
  }

}
