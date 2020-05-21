package io.shiftleft.semanticcpg.passes.linking.linker

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeKeys, nodes}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.passes.linking.linker.Linker.logFailedDstLookup
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality

import scala.jdk.CollectionConverters._
import scala.collection.mutable

class LinkToMultiplePass[SRC_NODE_TYPE <: nodes.StoredNode](cpg: Cpg,
                                                            srcLabels: List[String],
                                                            dstNodeLabel: String,
                                                            edgeType: String,
                                                            dstNodeMap: mutable.Map[String, nodes.StoredNode],
                                                            getDstFullNames: SRC_NODE_TYPE => Iterable[String],
                                                            dstFullNameKey: String)
    extends ParallelCpgPass[SRC_NODE_TYPE](cpg) {

  private val logger: Logger = LogManager.getLogger(classOf[LinkToSinglePass])
  var loggedDeprecationWarning = false

  override def partIterator: Iterator[SRC_NODE_TYPE] =
    cpg.graph.V
      .hasLabel(srcLabels.head, srcLabels.tail: _*)
      .map(_.asInstanceOf[SRC_NODE_TYPE])
      .toIterator()

  override def runOnPart(srcNode: SRC_NODE_TYPE): Option[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
    if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
      getDstFullNames(srcNode).foreach { dstFullName =>
        dstNodeMap.get(dstFullName) match {
          case Some(dstNode) =>
            dstGraph.addEdgeInOriginal(srcNode, dstNode, edgeType)
          case None =>
            logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
        }
      }
    } else {
      val dstFullNames = srcNode
        .vertices(Direction.OUT, edgeType)
        .asScala
        .map(_.value2(NodeKeys.FULL_NAME))
        .iterator
        .to(Iterable)
      srcNode.removeProperty(Key(dstFullNameKey))
      dstFullNames.foreach { name =>
        srcNode.property(Cardinality.list, dstFullNameKey, name)
      }
      if (!loggedDeprecationWarning) {
        logger.warn(
          s"Using deprecated CPG format with already existing $edgeType edge between" +
            s" a source node of type $srcLabels and a $dstNodeLabel node.")
        loggedDeprecationWarning = true
      }
    }
    Some(dstGraph.build())
  }
}
