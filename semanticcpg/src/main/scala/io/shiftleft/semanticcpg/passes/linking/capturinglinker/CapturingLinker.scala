package io.shiftleft.semanticcpg.passes.linking.capturinglinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

/**
  *
  * This pass has no other pass as prerequisite.
  */
class CapturingLinker(cpg: Cpg) extends CpgPass(cpg) {
  import CapturingLinker.logger

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    val idToClosureBinding: Map[String, nodes.ClosureBinding] =
      cpg.closureBinding.map { closureBinding =>
        (closureBinding.closureBindingId.get, closureBinding)
      }.toMap

    for {
      local <- cpg.local
      closureBindingId <- local.closureBindingId
    } idToClosureBinding.get(closureBindingId) match {
      case Some(closureBindingNode) =>
        dstGraph.addEdgeInOriginal(local, closureBindingNode, EdgeTypes.CAPTURED_BY)
      case None =>
        logger.warn(s"Missing CLOSURE_BINDING node or invalid closureBindingId=$closureBindingId")
    }

    Iterator(dstGraph.build())
  }
}

object CapturingLinker {
  private val logger: Logger = LoggerFactory.getLogger(classOf[CapturingLinker])
}
