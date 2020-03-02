package io.shiftleft.dataflowengine.passes.propagateedges

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.dataflowengine.semanticsloader.Semantics
import org.apache.logging.log4j.{LogManager, Logger}

import scala.jdk.CollectionConverters._

/**
  * Create PROPAGATE edges which mark parameters defined by a method.
  * PROPAGATE edges can be picked up by the reachingdef pass to calculate
  * reaching definition edges.
  */
class PropagateEdgePass(cpg: Cpg, semantics: Semantics) extends CpgPass(cpg) {
  import PropagateEdgePass.logger

  var dstGraph: DiffGraph.Builder = _

  override def run(): Iterator[DiffGraph] = {
    dstGraph = DiffGraph.newBuilder

    semantics.elements.foreach { semantic =>
      val methodOption =
        cpg.graph.V().hasLabel(NodeTypes.METHOD).has(NodeKeys.FULL_NAME -> semantic.methodFullName).headOption()

      methodOption match {
        case Some(method) =>
          addSelfDefSemantic(method, semantic.parameterIndex)
        case None =>
      }
    }

    Iterator(dstGraph.build())
  }

  private def addSelfDefSemantic(method: Vertex, parameterIndex: Int): Unit = {
    // From where the PROPAGATE edge is coming does not matter for the open source reachable by.
    // Thus we let it start from the corresponding METHOD_PARAMETER_IN.
    val parameterInOption = method
      .asInstanceOf[nodes.StoredNode]
      ._astOut
      .asScala
      .find(node => node.label == NodeTypes.METHOD_PARAMETER_IN && node.value2(NodeKeys.ORDER) == parameterIndex)

    val parameterOutOption = method
      .asInstanceOf[nodes.StoredNode]
      ._astOut
      .asScala
      .find(node => node.label == NodeTypes.METHOD_PARAMETER_OUT && node.value2(NodeKeys.ORDER) == parameterIndex)

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

object PropagateEdgePass {
  private val logger: Logger = LogManager.getLogger(classOf[PropagateEdgePass])
}
