package io.shiftleft.semanticcpg.passes.linking.filecompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.linking.linker.LinkerShared.logFailedDstLookup
import org.apache.tinkerpop.gremlin.structure.Direction
import gremlin.scala._
import io.shiftleft.Implicits.JavaIteratorDeco
import org.apache.logging.log4j.{LogManager, Logger}

import scala.collection.mutable

/**
  * Links NAMESPACE_BLOCKs, TYPE_DECLs and METHODs to their FILEs via
  * SOURCE_FILE edges.
  *
  * This pass should come after FILENAME properties have been recovered,
  * that is, after FileNameCompat.
  */
class FileLinker(cpg: Cpg) extends CpgPass(cpg) {
  private val logger: Logger = LogManager.getLogger(this)

  override def run(): Iterator[DiffGraph] = {

    val originalFileNameToNode = mutable.Map.empty[String, nodes.StoredNode]
    val newFileNameToNode = mutable.Map.empty[String, nodes.NewFile]
    var maxFileOrder = -1

    cpg.file.toIterator().foreach { node =>
      originalFileNameToNode += node.name -> node
      maxFileOrder = Math.max(maxFileOrder, node.order)
    }

    def createFileIfDoesNotExist(srcNode: nodes.StoredNode, dstFullName: String, dstGraph: DiffGraph.Builder): Unit = {
      val newFile = newFileNameToNode.getOrElseUpdate(dstFullName, {
        maxFileOrder += 1
        val file = nodes.NewFile(name = dstFullName, order = maxFileOrder)
        dstGraph.addNode(file)
        file
      })
      dstGraph.addEdgeFromOriginal(srcNode, newFile, EdgeTypes.SOURCE_FILE)
    }

    // Create SOURCE_FILE edges from nodes of various types
    // to FILE nodes.

    val it = linkToSingle(
      cpg,
      srcLabels = List(
        NodeTypes.NAMESPACE_BLOCK,
        NodeTypes.TYPE_DECL,
        NodeTypes.METHOD
      ),
      dstNodeLabel = NodeTypes.FILE,
      edgeType = EdgeTypes.SOURCE_FILE,
      dstNodeMap = originalFileNameToNode,
      dstFullNameKey = "FILENAME",
      Some(createFileIfDoesNotExist)
    )
    it
  }

  /**
    * For all nodes `n` with a label in `srcLabels`, determine
    * the value of `n.\$dstFullNameKey`, use that to lookup the
    * destination node in `dstNodeMap`, and create an edge of type
    * `edgeType` between `n` and the destination node.
    * */
  private def linkToSingle(
      cpg: Cpg,
      srcLabels: List[String],
      dstNodeLabel: String,
      edgeType: String,
      dstNodeMap: mutable.Map[String, nodes.StoredNode],
      dstFullNameKey: String,
      dstNotExistsHandler: Option[(nodes.StoredNode, String, DiffGraph.Builder) => Unit]): Iterator[DiffGraph] = {
    var loggedDeprecationWarning = false

    val dstGraph = DiffGraph.newBuilder

    def sourceIterator = {
      val sourceTraversal = cpg.graph.V.hasLabel(srcLabels.head, srcLabels.tail: _*)
      new Steps(sourceTraversal).toIterator()
    }

    sourceIterator.foreach { srcNode =>
      // If the source node does not have any outgoing edges of this type
      // This check is just required for backward compatibility
      if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
        srcNode.valueOption[String](dstFullNameKey).foreach { dstFullName =>
          // for `UNKNOWN` this is not always set, so we're using an Option here
          val srcStoredNode = srcNode.asInstanceOf[nodes.StoredNode]
          val dstNode: Option[nodes.StoredNode] = dstNodeMap.get(dstFullName)
          dstNode match {
            case Some(dstNodeInner) =>
              dstGraph
                .addEdgeInOriginal(srcStoredNode, dstNodeInner, edgeType)
            case None =>
              if (dstNotExistsHandler.isDefined) {
                dstNotExistsHandler.get(srcStoredNode, dstFullName, dstGraph)
              } else {
                logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
              }
          }
        }
      } else {
        val maybeDstFullName = srcNode.vertices(Direction.OUT, edgeType).nextOption.map(_.value2(NodeKeys.FULL_NAME))
        maybeDstFullName match {
          case Some(dstFullName) => srcNode.property(dstFullNameKey, dstFullName)
          case None              => logger.error(s"Missing outgoing edge of type ${edgeType} from node ${srcNode}")
        }
        if (!loggedDeprecationWarning) {
          logger.warn(
            s"Using deprecated CPG format with already existing $edgeType edge between" +
              s" a source node of type $srcLabels and a $dstNodeLabel node.")
          loggedDeprecationWarning = true
        }
      }
    }
    Iterator(dstGraph.build())
  }

}
