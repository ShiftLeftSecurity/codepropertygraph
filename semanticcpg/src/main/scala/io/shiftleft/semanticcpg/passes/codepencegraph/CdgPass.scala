package io.shiftleft.semanticcpg.passes.codepencegraph

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import org.apache.logging.log4j.LogManager
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.passes.cfgdominator.{CfgDominatorFrontier, ReverseCpgCfgAdapter}
import org.apache.tinkerpop.gremlin.structure.Direction
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

/**
  * This pass has ContainsEdgePass and CfgDominatorPass as prerequisites.
  */
class CdgPass(cpg: Cpg) extends CpgPass(cpg) {
  import CdgPass.logger

  override def run(): Iterator[DiffGraph] = {

    val dominanceFrontier =
      new CfgDominatorFrontier(new ReverseCpgCfgAdapter(), new CpgPostDomTreeAdapter())

    new ParallelIteratorExecutor[nodes.Method](cpg.method.toIterator()).map { method =>
      implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder

      val cfgNodes = method.vertices(Direction.OUT, EdgeTypes.CONTAINS).asScala.toList
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
              val method = postDomFrontierNode.vertices(Direction.IN, EdgeTypes.CONTAINS).next
              val nodeLabel = postDomFrontierNode.label
              logger.warn(s"Found CDG edge starting at $nodeLabel node. This is most likely caused by an invalid CFG." +
                s" Method: ${method.valueOption(NodeKeys.FULL_NAME)}" +
                s" number of outgoing CFG edges from $nodeLabel node: ${postDomFrontierNode.edges(Direction.OUT, EdgeTypes.CFG).asScala.size}")
          }
      }
      dstGraph.build()
    }
  }
}

object CdgPass {
  private val logger = LogManager.getLogger(classOf[CdgPass])
}
