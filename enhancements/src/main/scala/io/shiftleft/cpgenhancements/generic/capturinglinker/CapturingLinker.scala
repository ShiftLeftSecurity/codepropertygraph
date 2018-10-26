package io.shiftleft.cpgenhancements.generic.capturinglinker

import gremlin.scala._
import io.shiftleft.cpgenhancements.CpgEnhancement
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes, nodes}

class CapturingLinker(graph: ScalaGraph) extends CpgEnhancement(graph) {

  override def run(): Unit = {
    var idToClosureBinding = Map.empty[String, Vertex]

    graph.V
      .hasLabel(NodeTypes.CLOSURE_BINDING)
      .sideEffect { closureBinding =>
        idToClosureBinding += ((closureBinding.value2(NodeKeys.CLOSURE_BINDING_ID), closureBinding))
      }
      .iterate()

    graph.V
      .hasLabel(NodeTypes.LOCAL)
      .sideEffect { local =>
        local.valueOption(NodeKeys.CLOSURE_BINDING_ID).foreach { closureBindingId =>
          idToClosureBinding.get(closureBindingId) match {
            case Some(closureBindingNode) =>
              dstGraph.addEdgeInOriginal(local, closureBindingNode, EdgeTypes.CAPTURED_BY)
            case None =>
              logger.error("Missing CLOSURE_BINDING node or invalid closureBindingId=$closureBindingId")
          }
        }
      }
      .iterate()
  }
}
