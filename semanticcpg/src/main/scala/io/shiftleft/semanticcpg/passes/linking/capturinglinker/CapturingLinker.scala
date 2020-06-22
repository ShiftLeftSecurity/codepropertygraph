package io.shiftleft.semanticcpg.passes.linking.capturinglinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.logging.log4j.{LogManager, Logger}
import overflowdb.traversal.Traversal

/**
  *
  * This pass has no other pass as prerequisite.
  */
class CapturingLinker(cpg: Cpg) extends CpgPass(cpg) {
  import CapturingLinker.logger

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    // TODO MP use `cpg.typ` once that's defined in odb api
    val idToClosureBinding: Map[String, nodes.ClosureBinding] =
      Traversal(cpg.graph.nodesByLabel(NodeTypes.CLOSURE_BINDING))
        .cast[nodes.ClosureBinding]
        .map { closureBinding =>
          (closureBinding.closureBindingId.get, closureBinding)
        }
        .toMap

    // TODO MP use `cpg.local` once that's defined in odb api
    Traversal(cpg.graph.nodesByLabel(NodeTypes.LOCAL)).cast[nodes.Local].foreach { local =>
      local.closureBindingId.foreach { closureBindingId =>
        idToClosureBinding.get(closureBindingId) match {
          case Some(closureBindingNode) =>
            dstGraph.addEdgeInOriginal(local, closureBindingNode, EdgeTypes.CAPTURED_BY)
          case None =>
            logger.error(s"Missing CLOSURE_BINDING node or invalid closureBindingId=$closureBindingId")
        }
      }
    }

    Iterator(dstGraph.build())
  }
}

object CapturingLinker {
  private val logger: Logger = LogManager.getLogger(classOf[CapturingLinker])
}
