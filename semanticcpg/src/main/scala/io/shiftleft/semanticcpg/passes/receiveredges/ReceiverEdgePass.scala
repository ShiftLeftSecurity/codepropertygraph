package io.shiftleft.semanticcpg.passes.receiveredges

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.passes.{DiffGraph, SimpleCpgPass}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.jdk.CollectionConverters._

/**
  * This pass only exists to assure backwards compatibility for java and c#
  * cpgs which do not yet have the RECEIVER edge.
  * For those cases the RECEIVER edge is always between a CALL and
  * a possibly existing instance argument and can thus easily be calculated
  * here.
  *
  * This pass has no other pass as prerequisite.
  *
  * TODO remove once not needed anymore.
  */
class ReceiverEdgePass(cpg: Cpg) extends SimpleCpgPass(cpg) {
  import ReceiverEdgePass.logger

  override def run(dstGraph: DiffGraph.Builder): Unit = {
    var loggedDeprecationWarning = false

    cpg.call
      .sideEffect { call =>
        call._astOut.asScala.find { case node: nodes.HasArgumentIndex => node.argumentIndex == 0 }.foreach { instance =>
          // The check for !instance.isInstanceOf[nodes.Return] is only here to not crash and burn
          // do to invalid input of the python2cpg frontend which currently provides RETURN nodes
          // as argumentIndex 0 AST child to CALL nodes.
          if (!instance._receiverIn.hasNext && !instance.isInstanceOf[nodes.Return]) {
            dstGraph.addEdgeInOriginal(call, instance, EdgeTypes.RECEIVER)
            if (!loggedDeprecationWarning) {
              logger.warn("Using deprecated CPG format without RECEIVER edge between CALL and instance nodes.")
              loggedDeprecationWarning = true
            }
          }
        }
      }
      .iterate()
  }
}

object ReceiverEdgePass {
  private val logger: Logger = LoggerFactory.getLogger(classOf[ReceiverEdgePass])
}
