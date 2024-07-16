package io.shiftleft.codepropertygraph.cpgloading

import flatgraph.*
import io.shiftleft.proto.cpg.Cpg.CpgStruct

import scala.jdk.CollectionConverters.*

/** Mutable datastructure to preserve mapping between proto and cpg nodes during ProtoToCpg import.
  *
  * Context: we need to run two passes: 1) add nodes and 2) set node properties and add edges (this is due to
  * flatgraph-specific implementation details)
  *
  * Because of that, we need to remember the mapping from proto node id to gnode. Typically that's just a plain mapping,
  * but there's one special case for TYPE nodes: some (parallel) frontends create duplicate TYPE nodes which we need to
  * deduplicate...
  */
class ProtoToGraphNodeMappings {
  private var protoNodeIdToGNode  = Map.empty[Long, DNode]
  private var typeFullNameToGNode = Map.empty[String, DNode]

  def addAll(other: ProtoToGraphNodeMappings): Unit = {
    val intersection1 = this.protoNodeIdToGNode.keySet.intersect(other.protoNodeIdToGNode.keySet)
    val intersection2 = this.typeFullNameToGNode.keySet.intersect(other.typeFullNameToGNode.keySet)
    assert(
      intersection1.isEmpty,
      s"unexpected duplicate entries in protoNodeIdToGNode mappings. protoNodeIds: $intersection1"
    )
    assert(
      intersection2.isEmpty,
      s"unexpected duplicate entries in typeFullNameToGNode mappings. FullNames: $intersection2"
    )

    this.protoNodeIdToGNode = this.protoNodeIdToGNode ++ other.protoNodeIdToGNode
    this.typeFullNameToGNode = this.typeFullNameToGNode ++ other.typeFullNameToGNode
  }

  def add(protoNode: CpgStruct.Node, node: DNode): Unit = {
    protoNodeIdToGNode += protoNode.getKey -> node
    typeFullNameMaybe(protoNode).foreach(typeFullName => typeFullNameToGNode += typeFullName -> node)
  }

  def contains(protoNode: CpgStruct.Node): Boolean =
    find(protoNode).isDefined

  def find(protoNode: CpgStruct.Node): Option[DNode] = {
    protoNodeIdToGNode.get(protoNode.getKey).orElse {
      typeFullNameMaybe(protoNode).flatMap(typeFullNameToGNode.get)
    }
  }

  /** This will fail hard if the DiffGraph hasn't been applied yet, which is the assumption for it's use case. In other
    * words, we specifically don't want to invoke `find(protoNode).flatMap(_.storedRef)` here
    */
  def findGNode(protoNode: CpgStruct.Node): Option[GNode] =
    find(protoNode).map(_.storedRef.get)

  /** This will fail hard if the DiffGraph hasn't been applied yet, which is the assumption for it's use case. In other
    * words, we specifically don't want to invoke `find(protoNode).flatMap(_.storedRef)` here
    *
    * Note that this doesn't (and cannot) check for duplicate nodes with the same TYPE.FULL_NAME, because we only have
    * the protoNodeId and not the node itself.
    */
  def findGNode(protoNodeId: Long): Option[GNode] =
    protoNodeIdToGNode.get(protoNodeId).map(_.storedRef.get)

  private def typeFullNameMaybe(protoNode: CpgStruct.Node): Option[String] = {
    if (protoNode.getType == CpgStruct.Node.NodeType.TYPE) {
      protoNode.getPropertyList.asScala.collectFirst {
        case prop if prop.getName == io.shiftleft.proto.cpg.Cpg.NodePropertyName.FULL_NAME =>
          prop.getValue.getStringValue
      }
    } else {
      None
    }
  }

}
