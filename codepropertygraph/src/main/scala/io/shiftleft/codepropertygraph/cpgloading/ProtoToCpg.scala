package io.shiftleft.codepropertygraph.cpgloading

import java.lang.{Boolean => JBoolean, Integer => JInt, Long => JLong}
import java.util.{NoSuchElementException, Collection => JCollection}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct.{Edge, Node}
import io.shiftleft.proto.cpg.Cpg.PropertyValue
import io.shiftleft.utils.StringInterner
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.T
import overflowdb._
import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase._

import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters._

object ProtoToCpg {
  val logger: Logger = LogManager.getLogger(classOf[ProtoToCpg])

  def addProperties(keyValues: ArrayBuffer[AnyRef],
                    name: String,
                    value: PropertyValue,
                    interner: StringInterner = StringInterner.noop): Unit = {
    import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase._
    value.getValueCase match {
      case INT_VALUE =>
        keyValues += interner.intern(name)
        keyValues += (value.getIntValue: JInt)
      case STRING_VALUE =>
        keyValues += interner.intern(name)
        keyValues += interner.intern(value.getStringValue)
      case BOOL_VALUE =>
        keyValues += interner.intern(name)
        keyValues += (value.getBoolValue: JBoolean)
      case STRING_LIST =>
        value.getStringList.getValuesList.asScala.foreach { elem: String =>
          keyValues += interner.intern(name)
          keyValues += interner.intern(elem)
        }
      case VALUE_NOT_SET => ()
      case _ =>
        throw new RuntimeException("Error: unsupported property case: " + value.getValueCase.name)
    }
  }

  def toRegularType(value: PropertyValue)(implicit interner: StringInterner): Any =
    value.getValueCase match {
      case INT_VALUE => value.getIntValue
      case BOOL_VALUE => value.getBoolValue
      case STRING_VALUE => interner.intern(value.getStringValue)
      case STRING_LIST => value.getStringList.getValuesList.asScala.map(interner.intern).toList
      case VALUE_NOT_SET => ()
      case _ => throw new RuntimeException("Error: unsupported property case: " + value.getValueCase.name)
    }
}

class ProtoToCpg(overflowConfig: OdbConfig = OdbConfig.withoutOverflow) {
  import ProtoToCpg._
  private val nodeFilter = new NodeFilter
  private val odbGraph =
    OdbGraph.open(overflowConfig,
                  io.shiftleft.codepropertygraph.generated.nodes.Factories.allAsJava,
                  io.shiftleft.codepropertygraph.generated.edges.Factories.allAsJava)
  implicit private val interner: StringInterner = StringInterner.makeStrongInterner()

  def addNodes(nodes: JCollection[Node]): Unit =
    addNodes(nodes.asScala)

  def addNodes(nodes: Iterable[Node]): Unit =
    nodes
      .filter(nodeFilter.filterNode)
      .foreach(addNodeToOdb)

  private def addNodeToOdb(node: Node) = {
    def nodeToArray(node: Node): Array[AnyRef] = {
      val props = node.getPropertyList
      val keyValues = new ArrayBuffer[AnyRef](4 + (2 * props.size()))
      keyValues += T.id
      keyValues += (node.getKey: JLong)
      keyValues += T.label
      keyValues += node.getType.name()
      for (prop <- props.asScala) {
        addProperties(keyValues, prop.getName.name, prop.getValue, interner)
      }
      keyValues.toArray
    }

    try {
//      println("XXX0=" + node.getKey)
      val res = odbGraph.addVertex(nodeToArray(node): _*)
//      println("XXX1=" + res.id)
      res
//          val properties: Seq[Property[Any]] = Nil
//      odbGraph + (node.getType.name, properties: _*)
    } catch {
      case e: Exception =>
        throw new RuntimeException("Failed to insert a vertex. proto:\n" + node, e)
    }
  }

  def addEdges(protoEdges: JCollection[Edge]): Unit =
    addEdges(protoEdges.asScala)

  def addEdges(protoEdges: Iterable[Edge]): Unit = {
    for (edgeProto <- protoEdges) {
      val srcNode = findNodeById(edgeProto, edgeProto.getSrc)
      val dstNode = findNodeById(edgeProto, edgeProto.getDst)
      val properties = edgeProto.getPropertyList.asScala.toSeq.map { edgeProperty =>
        Property(edgeProperty.getName.name, toRegularType(edgeProperty.getValue))
      }
      try {
        srcNode --- (edgeProto.getType.name, properties: _*) --> dstNode
      } catch {
        case e: IllegalArgumentException =>
          val context = "label=" + edgeProto.getType.name +
            ", srcNodeId=" + edgeProto.getSrc +
            ", dstNodeId=" + edgeProto.getDst +
            ", srcNode=" + srcNode +
            ", dstNode=" + dstNode
          logger.warn("Failed to insert an edge. context: " + context, e)
      }
    }
  }

  def build(): Cpg = new Cpg(odbGraph)

  private def findNodeById(edge: Edge, nodeId: Long): overflowdb.Node = {
    if (nodeId == -1)
      throw new IllegalArgumentException(
        "edge " + edge + " has illegal src|dst node. something seems wrong with the cpg")
    odbGraph
      .nodeOption(nodeId)
      .getOrElse(
        throw new NoSuchElementException(
          "Couldn't find src|dst node " + nodeId + " for edge " + edge + " of type " + edge.getType.name))
  }

}
