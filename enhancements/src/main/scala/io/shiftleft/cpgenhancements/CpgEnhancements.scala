package io.shiftleft.cpgenhancements

import gremlin.scala.ScalaGraph
import io.shiftleft.cpgenhancements.generic.linker.Linker
import io.shiftleft.cpgenhancements.generic.methoddecorator.MethodDecorator
import io.shiftleft.cpgenhancements.generic.methodinstlinker.MethodInstLinker
import io.shiftleft.cpgenhancements.generic.namspacecreator.NamespaceCreator

/* singleton instance for convenience */
object CpgEnhancements extends CpgEnhancements

class CpgEnhancements {

  def run(graph: ScalaGraph) = {
    val methodDecorator = new MethodDecorator(graph)
    methodDecorator.executeAndApply()

    val linker = new Linker(graph)
    linker.executeAndApply()

    val namespaceCreator = new NamespaceCreator(graph)
    namespaceCreator.executeAndApply()

    val methodInstLinker = new MethodInstLinker(graph)
    methodInstLinker.executeAndApply()
  }

}
