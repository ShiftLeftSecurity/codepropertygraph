package io.shiftleft.passes.receiveredges

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

/**
  This path only exists to assure backwards compatibility for java and c#
  cpgs which do not yet have the RECEIVER edge.
  For those cases the RECEIVER edge is always between a CALL and
  a possibly existing instance argument and can thus easily be calculated
  here.
  TODO remove once not needed anymore.
  */
class ReceiverEdgePass(graph: ScalaGraph) extends CpgPass(graph) {

  override def run(): Stream[DiffGraph] = {
    var loggedDeprecationWarning = false
    val dstGraph                 = new DiffGraph

    graph.V
      .hasLabel(NodeTypes.CALL)
      .sideEffect { call =>
        val instanceOption = call
          .vertices(Direction.OUT, EdgeTypes.AST)
          .asScala
          .find(_.value2(NodeKeys.ARGUMENT_INDEX) == 0)

        instanceOption match {
          case Some(instance) =>
            if (!instance.edges(Direction.IN, EdgeTypes.RECEIVER).hasNext) {
              dstGraph.addEdgeInOriginal(call.asInstanceOf[StoredNode],
                                         instance.asInstanceOf[StoredNode],
                                         EdgeTypes.RECEIVER)
              if (!loggedDeprecationWarning) {
                logger.warn(
                  s"Using deprecated CPG format without RECEIVER edge between " +
                    s"CALL and instance nodes.")
                loggedDeprecationWarning = true
              }
            }
          case None =>
        }
      }
      .iterate()
    Stream(dstGraph)
  }
}
