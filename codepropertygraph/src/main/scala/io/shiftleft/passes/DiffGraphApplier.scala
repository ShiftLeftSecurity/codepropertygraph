package io.shiftleft.passes

import java.util

import gremlin.scala.ScalaGraph
import io.shiftleft.codepropertygraph.generated.nodes.NewNode
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality

/**
  * Component to merge diff graphs into existing (loaded) OdbGraph
  * */
private class DiffGraphApplier {
  import DiffGraphApplier.InternalProperty

  private val overlayNodeToTinkerNode = new util.HashMap[IdentityHashWrapper[NewNode], Vertex]()

  /**
    * Applies diff to existing (loaded) OdbGraph
    **/
  def applyDiff(diffGraph: DiffGraph, graph: ScalaGraph): AppliedDiffGraph = {
    addNodes(diffGraph, graph)
    addEdges(diffGraph, graph)
    addNodeProperties(diffGraph, graph)
    addEdgeProperties(diffGraph, graph)
    AppliedDiffGraph(diffGraph, overlayNodeToTinkerNode)
  }

  // We are in luck: OdbGraph will assign ids to new nodes for us
  private def addNodes(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    val nodeTinkerNodePairs = diffGraph.nodes.map { node =>
      val newNode = graph.graph.addVertex(node.label)

      node.properties.filter { case (key, _) => !key.startsWith(InternalProperty) }.foreach {
        case (key, value: Traversable[_]) =>
          value.foreach { value =>
            newNode.property(Cardinality.list, key, value)
          }
        case (key, value) =>
          newNode.property(key, value)
      }
      (node, newNode)
    }
    nodeTinkerNodePairs.foreach {
      case (node, tinkerNode) =>
        overlayNodeToTinkerNode.put(IdentityHashWrapper(node), tinkerNode)
    }
  }

  private def addEdges(diffGraph: DiffGraph, graph: ScalaGraph) = {
    diffGraph.edges.foreach { edge =>
      val srcTinkerNode = overlayNodeToTinkerNode.get(IdentityHashWrapper(edge.src))
      val dstTinkerNode = overlayNodeToTinkerNode.get(IdentityHashWrapper(edge.dst))
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesFromOriginal.foreach { edge =>
      val srcTinkerNode = edge.src
      val dstTinkerNode = overlayNodeToTinkerNode.get(IdentityHashWrapper(edge.dst))
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesToOriginal.foreach { edge =>
      val srcTinkerNode = overlayNodeToTinkerNode.get(IdentityHashWrapper(edge.src))
      val dstTinkerNode = edge.dst
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesInOriginal.foreach { edge =>
      val srcTinkerNode = edge.src
      val dstTinkerNode = edge.dst
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    def tinkerAddEdge(src: Vertex, dst: Vertex, edge: DiffGraph.DiffEdge) = {
      val tinkerEdge = src.addEdge(edge.label, dst)

      edge.properties.foreach {
        case (key, value) =>
          tinkerEdge.property(key, value)
      }
    }
  }

  private def addNodeProperties(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    diffGraph.nodeProperties.foreach { property =>
      val node = property.node
      node.property(property.propertyKey, property.propertyValue)
    }
  }

  private def addEdgeProperties(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    diffGraph.edgeProperties.foreach { property =>
      val edge = property.edge
      edge.property(property.propertyKey, property.propertyValue)
    }
  }

}

private object DiffGraphApplier {
  private val InternalProperty = "_"
}
