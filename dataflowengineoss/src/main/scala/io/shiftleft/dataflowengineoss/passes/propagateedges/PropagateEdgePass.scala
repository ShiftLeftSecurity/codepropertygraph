package io.shiftleft.dataflowengineoss.passes.propagateedges

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
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
      cpg.method.fullName(semantic.methodFullName).headOption.foreach { method =>
        addSelfDefSemantic(method, semantic.parameterIndex)
      }
    }

    Iterator(dstGraph.build())
  }

  private def addSelfDefSemantic(method: nodes.Method, parameterIndex: Int): Unit = {
    // From where the PROPAGATE edge is coming does not matter for the open source reachable by.
    // Thus we let it start from the corresponding METHOD_PARAMETER_IN.
    val astOut = method._astOut.asScala.toList
    val parameterInOption = astOut.find {
      case paramIn: nodes.MethodParameterIn if paramIn.order == parameterIndex => true
      case _                                                                   => false
    }
    val parameterOutOption = astOut.find {
      case paramOut: nodes.MethodParameterOut if paramOut.order == parameterIndex => true
      case _                                                                      => false
    }

    (parameterInOption, parameterOutOption) match {
      case (Some(parameterIn), Some(parameterOut)) =>
        addPropagateEdge(parameterIn, parameterOut, isAlias = false)
      case (None, _) =>
        logger.warn(s"Could not find parameter $parameterIndex of ${method.fullName}.")
      case _ =>
        logger.warn(s"Could not find output parameter $parameterIndex of ${method.fullName}.")
    }
  }

  private def addPropagateEdge(src: nodes.StoredNode, dst: nodes.StoredNode, isAlias: java.lang.Boolean): Unit = {
    dstGraph.addEdgeInOriginal(src, dst, EdgeTypes.PROPAGATE, (EdgeKeyNames.ALIAS, isAlias) :: Nil)
  }
}

object PropagateEdgePass {
  private val logger: Logger = LogManager.getLogger(classOf[PropagateEdgePass])
}
