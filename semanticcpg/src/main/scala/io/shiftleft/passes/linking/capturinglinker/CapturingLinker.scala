package io.shiftleft.passes.linking.capturinglinker

import gremlin.scala.ScalaGraph
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.logging.log4j.{LogManager, Logger}

/**
  *
  * This pass has no other pass as prerequisite.
  */
class CapturingLinker(graph: ScalaGraph) extends CpgPass(graph) {
  import CapturingLinker.logger

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = new DiffGraph

    val idToClosureBinding: Map[String, nodes.ClosureBinding] =
      graph.V
        .hasLabel(NodeTypes.CLOSURE_BINDING)
        .collect {
          case closureBinding: nodes.ClosureBinding =>
            (closureBinding.closureBindingId.get, closureBinding)
        }
        .toMap

    graph.V
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
