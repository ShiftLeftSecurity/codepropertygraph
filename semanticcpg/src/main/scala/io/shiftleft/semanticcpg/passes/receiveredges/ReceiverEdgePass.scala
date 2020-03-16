package io.shiftleft.semanticcpg.passes.receiveredges

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.logging.log4j.{LogManager, Logger}

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
class ReceiverEdgePass(cpg: Cpg) extends CpgPass(cpg) {
  import ReceiverEdgePass.logger

  override def run(): Iterator[DiffGraph] = {
    var loggedDeprecationWarning = false
    val dstGraph = DiffGraph.newBuilder

    cpg.call.sideEffect { call =>
      call._astOut.asScala.find(_.value2(NodeKeys.ARGUMENT_INDEX) == 0).foreach { instance =>
        if (!instance._receiverIn.hasNext) {
          dstGraph.addEdgeInOriginal(call, instance, EdgeTypes.RECEIVER)
          if (!loggedDeprecationWarning) {
            logger.warn("Using deprecated CPG format without RECEIVER edge between CALL and instance nodes.")
            loggedDeprecationWarning = true
          }
        }
      }
    }.iterate

    Iterator(dstGraph.build)
  }
}

object ReceiverEdgePass {
  private val logger: Logger = LogManager.getLogger(classOf[ReceiverEdgePass])
}
