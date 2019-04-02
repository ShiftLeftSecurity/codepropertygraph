package io.shiftleft.passes.containsedges

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import io.shiftleft.passes.utils.Traversals

class ContainsEdgePass(graph: ScalaGraph) extends CpgPass(graph) {

  override def run(): Stream[DiffGraph] = {
    val dstGraph = new DiffGraph

    val sourceTypes = List(
      NodeTypes.METHOD,
      NodeTypes.TYPE_DECL,
      NodeTypes.FILE
    )
    val destinationTypes = List(
      NodeTypes.BLOCK,
      NodeTypes.IDENTIFIER,
      NodeTypes.RETURN,
      NodeTypes.METHOD,
      NodeTypes.TYPE_DECL,
      NodeTypes.CALL,
      NodeTypes.LITERAL,
      NodeTypes.METHOD_REF,
      NodeTypes.UNKNOWN
    )

    val sourceVertices = graph.V.hasLabel(sourceTypes.head, sourceTypes.tail: _*).toList()

    for (source <- sourceVertices) {
      Traversals
        .walkAST(
          source.start
            .out(EdgeTypes.AST)
            .until(v => v.hasLabel(sourceTypes.head, sourceTypes.tail: _*)))
        .sideEffect(destination =>
          if (destinationTypes.contains(destination.label())) {
            dstGraph.addEdgeInOriginal(source.asInstanceOf[StoredNode],
                                       destination.asInstanceOf[StoredNode],
                                       EdgeTypes.CONTAINS)
        })
        .iterate()
    }

    Stream(dstGraph)
  }
}
