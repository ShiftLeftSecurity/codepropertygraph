package io.shiftleft.semanticcpg.passes.compat.argumentcompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import org.apache.logging.log4j.LogManager

import scala.jdk.CollectionConverters._

class ArgumentCompat(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val oldFormat = cpg.graph.traversal.E().hasLabel(EdgeTypes.ARGUMENT).range(0, 1).toList.isEmpty

    if (oldFormat) {
      ArgumentCompat.logger.info(s"Using old CPG format not containing ARGUMENT edges.")

      val diffGraph = new DiffGraph
      val callIterator = cpg.call.toIterator()
      callIterator.foreach(addArgumentEdges(_, diffGraph))
      val returnIterator = cpg.returnExpression.toIterator()
      returnIterator.foreach(addArgumentEdges(_, diffGraph))
      Iterator(diffGraph)
    } else {
      Iterator.empty
    }
  }

  private def addArgumentEdges(callOrReturn: nodes.AstNode, diffGraph: DiffGraph): Unit = {
    callOrReturn._astOut.asScala.foreach { argument =>
      if (!argument._argumentIn().hasNext) {
        diffGraph.addEdgeInOriginal(callOrReturn, argument, EdgeTypes.ARGUMENT)
      }
    }
  }
}

object ArgumentCompat {
  private val logger = LogManager.getLogger(getClass)
}
