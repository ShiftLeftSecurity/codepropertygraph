package io.shiftleft.semanticcpg.passes.compat.argumentcompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.AstNode
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.traversal._

import scala.jdk.CollectionConverters._

class ArgumentCompat(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val oldFormat = cpg.graph.edges(EdgeTypes.ARGUMENT).isEmpty

    if (oldFormat) {
      ArgumentCompat.logger.info(s"Using old CPG format not containing ARGUMENT edges.")

      val diffGraph = DiffGraph.newBuilder
      cpg.call.foreach(addArgumentEdges(_, diffGraph))
      cpg.ret.foreach(addArgumentEdges(_, diffGraph))
      Iterator(diffGraph.build())
    } else {
      Iterator.empty
    }
  }

  private def addArgumentEdges(callOrReturn: AstNode, diffGraph: DiffGraph.Builder): Unit = {
    callOrReturn._astOut.asScala.foreach { argument =>
      if (!argument._argumentIn.hasNext) {
        diffGraph.addEdgeInOriginal(callOrReturn, argument, EdgeTypes.ARGUMENT)
      }
    }
  }
}

object ArgumentCompat {
  private val logger: Logger = LoggerFactory.getLogger(classOf[ArgumentCompat])
}
