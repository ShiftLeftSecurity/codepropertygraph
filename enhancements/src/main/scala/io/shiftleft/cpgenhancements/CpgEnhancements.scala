package io.shiftleft.cpgenhancements

import gremlin.scala.ScalaGraph
import io.shiftleft.cpgenhancements.generic.calldecorator.CallDecorator
import io.shiftleft.cpgenhancements.generic.methoddecorator.MethodDecorator
import io.shiftleft.cpgenhancements.generic.namspacecreator.NamespaceCreator

/* singleton instance for convenience */
object CpgEnhancements extends CpgEnhancements

class CpgEnhancements {

  def run(graph: ScalaGraph) = {
    val methodDecorator = new MethodDecorator(graph)
    methodDecorator.executeAndApply()

    val namespaceCreator = new NamespaceCreator(graph)
    namespaceCreator.executeAndApply()

    val callDecorator = new CallDecorator(graph)
    callDecorator.executeAndApply()
  }

}
