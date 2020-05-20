package io.shiftleft.semanticcpg.passes.linking.capturinglinker

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.Local
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import org.apache.logging.log4j.{LogManager, Logger}
import io.shiftleft.semanticcpg.language._

/**
  *
  * This pass has no other pass as prerequisite.
  */
class CapturingLinker(cpg: Cpg) extends ParallelCpgPass[nodes.Local](cpg) {

  private val logger: Logger = LogManager.getLogger(classOf[CapturingLinker])

  var idToClosureBinding: Map[String, nodes.ClosureBinding] = Map()

  override def init(): Unit = {
    idToClosureBinding = cpg.graph.V
      .hasLabel(NodeTypes.CLOSURE_BINDING)
      .collect {
        case closureBinding: nodes.ClosureBinding =>
          (closureBinding.closureBindingId.get, closureBinding)
      }
      .toMap
  }

  override def partIterator: Iterator[Local] = cpg.local.iterator

  override def runOnPart(local: nodes.Local): Option[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    local.closureBindingId.foreach { closureBindingId =>
      idToClosureBinding.get(closureBindingId) match {
        case Some(closureBindingNode) =>
          dstGraph.addEdgeInOriginal(local, closureBindingNode, EdgeTypes.CAPTURED_BY)
        case None =>
          logger.error(s"Missing CLOSURE_BINDING node or invalid closureBindingId=$closureBindingId")
      }
    }
    Some(dstGraph.build())
  }
}
