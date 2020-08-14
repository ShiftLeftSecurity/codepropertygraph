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
        semantic.mappings.foreach {
          case (src, dst) =>
            addSelfDefSemantic(method, src, dst)
        }
      }
    }

    Iterator(dstGraph.build())
  }

  private def addSelfDefSemantic(method: nodes.Method, srcIndex: Int, dstIndex: Int): Unit = {
    // From where the PROPAGATE edge is coming does not matter for the open source reachable by.
    // Thus we let it start from the corresponding METHOD_PARAMETER_IN.
    val astOut = method._astOut.asScala.toList
    val parameterInOption = astOut.find {
      case paramIn: nodes.MethodParameterIn if paramIn.order == srcIndex => true
      case _                                                             => false
    }
    val parameterOutOption = astOut.find {
      case paramOut: nodes.MethodParameterOut if paramOut.order == dstIndex => true
      case _                                                                => false
    }

    (parameterInOption, parameterOutOption) match {
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
