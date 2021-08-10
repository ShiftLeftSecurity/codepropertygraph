package io.shiftleft.semanticcpg.passes.codepencegraph

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{
  Literal,
  Call,
  ControlStructure,
  Identifier,
  JumpTarget,
  Method,
  MethodRef,
  Unknown
}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.cfgdominator.{CfgDominatorFrontier, ReverseCpgCfgAdapter}
import org.slf4j.{Logger, LoggerFactory}

import scala.jdk.CollectionConverters._

/**
  * This pass has ContainsEdgePass and CfgDominatorPass as prerequisites.
  */
class CdgPass(cpg: Cpg) extends ParallelCpgPass[Method](cpg) {
  import CdgPass.logger

  override def partIterator: Iterator[Method] = cpg.method.iterator

  override def runOnPart(method: Method): Iterator[DiffGraph] = {

    val dominanceFrontier = new CfgDominatorFrontier(new ReverseCpgCfgAdapter, new CpgPostDomTreeAdapter)

    implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder

    val cfgNodes = method._containsOut.asScala.toList
    val postDomFrontiers = dominanceFrontier.calculate(method :: cfgNodes)

    postDomFrontiers.foreach {
      case (node, postDomFrontierNode) =>
        postDomFrontierNode match {
          case _: Literal | _: Identifier | _: Call | _: MethodRef | _: Unknown | _: ControlStructure |
              _: JumpTarget => {
            dstGraph.addEdgeInOriginal(postDomFrontierNode, node, EdgeTypes.CDG)
          }
          case _ =>
            val method = postDomFrontierNode._containsIn.next
            val nodeLabel = postDomFrontierNode.label
            logger.warn(
              s"Found CDG edge starting at $nodeLabel node. This is most likely caused by an invalid CFG." +
                s" Method: ${method match { case m: Method => m.fullName; case other => other.label }}" +
                s" number of outgoing CFG edges from $nodeLabel node: ${postDomFrontierNode._cfgOut.asScala.size}")
        }
    }
    Iterator(dstGraph.build())
  }
}

object CdgPass {
  private val logger: Logger = LoggerFactory.getLogger(classOf[CdgPass])
}
