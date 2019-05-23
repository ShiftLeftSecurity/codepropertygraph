package io.shiftleft.passes.propagateedges

import gremlin.scala._
import io.shiftleft.semanticsloader.Semantics
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

/**
  * Create PROPAGATE edges which mark parameters defined by a method.
  * PROPAGATE edges can be picked up by the reachingdef pass to calculate
  * reaching definition edges.
  */
class PropagateEdgePass(graph: ScalaGraph, semantics: Semantics) extends CpgPass(graph) {
  var dstGraph: DiffGraph = _

  override def run(): Iterator[DiffGraph] = {
    dstGraph = new DiffGraph()

    semantics.elements.foreach { semantic =>
      val methodOption =
        graph.V().hasLabel(NodeTypes.METHOD).has(NodeKeys.FULL_NAME -> semantic.methodFullName).headOption()

      methodOption match {
        case Some(method) =>
          addSelfDefSemantic(method, semantic.parameterIndex)
        case None =>
      }
    }

    Iterator(dstGraph)
  }

  private def addSelfDefSemantic(method: Vertex, parameterIndex: Int): Unit = {
    // From where the PROPAGATE edge is coming does not matter for the open source reachable by.
    // Thus we let it start from the corresponding METHOD_PARAMETER_IN.
    val parameterInOption = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(node => node.label() == NodeTypes.METHOD_PARAMETER_IN && node.value2(NodeKeys.ORDER) == parameterIndex)

    val parameterOutOption = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(node => node.label() == NodeTypes.METHOD_PARAMETER_OUT && node.value2(NodeKeys.ORDER) == parameterIndex)

    (parameterInOption, parameterOutOption) match {
      case (Some(parameterIn), Some(parameterOut)) =>
        addPropagateEdge(parameterIn, parameterOut, isAlias = false)
      case (None, _) =>
        logger.warn(s"Could not find parameter $parameterIndex of ${method.value2(NodeKeys.FULL_NAME)}.")
      case _ =>
        logger.warn(s"Could not find output parameter $parameterIndex of ${method.value2(NodeKeys.FULL_NAME)}.")
    }
  }

  private def addPropagateEdge(src: Vertex, dst: Vertex, isAlias: java.lang.Boolean): Unit = {
    dstGraph.addEdgeInOriginal(src.asInstanceOf[nodes.StoredNode],
                               dst.asInstanceOf[nodes.StoredNode],
                               EdgeTypes.PROPAGATE,
                               (EdgeKeyNames.ALIAS, isAlias) :: Nil)
  }
}
