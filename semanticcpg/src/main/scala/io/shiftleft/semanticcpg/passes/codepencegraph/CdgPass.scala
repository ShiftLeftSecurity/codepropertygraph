package io.shiftleft.semanticcpg.passes.codepencegraph

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.Method
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeysOdb, nodes}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.cfgdominator.{CfgDominatorFrontier, ReverseCpgCfgAdapter}
import org.apache.logging.log4j.LogManager
import overflowdb._

import scala.jdk.CollectionConverters._

/**
  * This pass has ContainsEdgePass and CfgDominatorPass as prerequisites.
  */
class CdgPass(cpg: Cpg) extends ParallelCpgPass[nodes.Method](cpg) {
  import CdgPass.logger

  override def partIterator: Iterator[Method] = cpg.method.toIterator()

  override def runOnPart(method: nodes.Method): Option[DiffGraph] = {

    val dominanceFrontier = new CfgDominatorFrontier(new ReverseCpgCfgAdapter, new CpgPostDomTreeAdapter)

    implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder

    val cfgNodes = method._containsOut.asScala.toList
    val postDomFrontiers = dominanceFrontier.calculate(method :: cfgNodes)

    postDomFrontiers.foreach {
      case (node, postDomFrontierNode) =>
        postDomFrontierNode match {
          case _: nodes.Literal | _: nodes.Identifier | _: nodes.Call | _: nodes.MethodRef | _: nodes.Unknown => {
            dstGraph.addEdgeInOriginal(postDomFrontierNode.asInstanceOf[nodes.StoredNode],
                                       node.asInstanceOf[nodes.StoredNode],
                                       EdgeTypes.CDG)
          }
          case _ =>
            val method = postDomFrontierNode.in(EdgeTypes.CONTAINS).next
            val nodeLabel = postDomFrontierNode.label
            logger.warn(s"Found CDG edge starting at $nodeLabel node. This is most likely caused by an invalid CFG." +
              s" Method: ${method.propertyOption(NodeKeysOdb.FULL_NAME)}" +
              s" number of outgoing CFG edges from $nodeLabel node: ${postDomFrontierNode.outE(EdgeTypes.CFG).asScala.size}")
        }
    }
    Some(dstGraph.build())
  }
}

object CdgPass {
  private val logger = LogManager.getLogger(classOf[CdgPass])
}
