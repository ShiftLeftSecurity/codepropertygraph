package io.shiftleft.passes.linking.capturinglinker

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.logging.log4j.{LogManager, Logger}

/**
  *
  * This pass has no other pass as prerequisite.
  */
class CapturingLinker(cpg: Cpg) extends CpgPass(cpg) {
  import CapturingLinker.logger

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = new DiffGraph

    val idToClosureBinding: Map[String, nodes.ClosureBinding] =
      cpg.graph.V
        .hasLabel(NodeTypes.CLOSURE_BINDING)
        .collect {
          case closureBinding: nodes.ClosureBinding =>
            (closureBinding.closureBindingId.get, closureBinding)
        }
        .toMap

    cpg.graph.V
      .hasLabel(NodeTypes.LOCAL)
      .sideEffect {
        case local: nodes.Local =>
          local.closureBindingId.foreach { closureBindingId =>
            idToClosureBinding.get(closureBindingId) match {
              case Some(closureBindingNode) =>
                dstGraph.addEdgeInOriginal(local, closureBindingNode, EdgeTypes.CAPTURED_BY)
              case None =>
                logger.error(s"Missing CLOSURE_BINDING node or invalid closureBindingId=$closureBindingId")
            }
          }
        case _ =>
      }
      .iterate()
    Iterator(dstGraph)
  }
}

object CapturingLinker {
  private val logger: Logger = LogManager.getLogger(classOf[CapturingLinker])
}
