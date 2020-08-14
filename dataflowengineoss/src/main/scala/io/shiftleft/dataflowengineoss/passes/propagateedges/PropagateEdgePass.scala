package io.shiftleft.dataflowengineoss.passes.propagateedges

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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
      cpg.method.fullNameExact(semantic.methodFullName).headOption.foreach { method =>
        semantic.mappings.foreach { mapping =>
          addSelfDefSemantic(method, mapping._1, mapping._2)
        }
      }
    }

    Iterator(dstGraph.build())
  }

  private def addSelfDefSemantic(method: nodes.Method, srcIndex: Int, dstIndex: Int): Unit = {

    val srcNode: Option[nodes.StoredNode] = srcIndex match {
      case -1 =>
        logger.warn("Semantic with source index of -1 is invalid")
        None
      case _ => method.parameter.order(srcIndex).headOption
    }

    val dstNode: Option[nodes.StoredNode] = dstIndex match {
      case -1 =>
        Some(method.methodReturn)
      case _ => method.parameter.order(dstIndex).asOutput.headOption
    }

    (srcNode, dstNode) match {
      case (Some(parameterIn), Some(parameterOut)) =>
        addPropagateEdge(parameterIn, parameterOut)
      case (None, _) =>
        logger.warn(s"Could not find parameter $srcIndex of ${method.fullName}.")
      case _ =>
        logger.warn(s"Could not find output parameter $srcIndex of ${method.fullName}.")
    }
  }

  private def addPropagateEdge(src: nodes.StoredNode, dst: nodes.StoredNode): Unit = {
    dstGraph.addEdgeInOriginal(src, dst, EdgeTypes.PROPAGATE)
  }
}

object PropagateEdgePass {
  private val logger: Logger = LoggerFactory.getLogger(classOf[PropagateEdgePass])
}
