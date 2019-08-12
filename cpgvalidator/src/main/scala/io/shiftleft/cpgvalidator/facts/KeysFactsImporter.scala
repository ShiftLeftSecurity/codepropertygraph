package io.shiftleft.cpgvalidator.facts

import play.api.libs.json.{Json, Reads}

import play.api.libs.json.Reads._

class KeysFactsImporter extends FactsImporter {

  import FactConstructionClasses._

  case class NodeKey(name: String, comment: String, valueType: String, cardinality: String)

  case class OutEdgeEntry(edgeName: String, inNodes: List[String])

  case class ContainedNode(nodeType: String, localName: String, cardinality: String)

  case class NodeType(name: String,
                      keys: List[String],
                      outEdges: List[OutEdgeEntry],
                      is: Option[List[String]],
                      containedNodes: Option[List[ContainedNode]])

  implicit val outEdgeEntryRead: Reads[OutEdgeEntry] =
    Json.reads[OutEdgeEntry]
  implicit val containedNodeRead: Reads[ContainedNode] =
    Json.reads[ContainedNode]
  implicit val nodeKeysRead: Reads[NodeKey] =
    Json.reads[NodeKey]
  implicit val nodeTypesRead: Reads[NodeType] =
    Json.reads[NodeType]

  override def loadFacts: List[KeysFact] = {
    val nodeTypes = allNodeTypesByNodeTypeName
    val nodeKeys = allNodeKeys
    nodeTypes.flatMap {
      case (nodeTypeName, keys) =>
        keys.map(keyName => {
          val key = findKey(nodeKeys, keyName)
          nodeTypeName withKey key.name hasCardinality Cardinality(
            key.cardinality
          )
        })
    }
  }.toList

  private def findKey(keys: List[NodeKey], keyName: String): NodeKey =
    keys
      .find(_.name == keyName)
      .getOrElse(
        throw new AssertionError(
          s"Key '$keyName' is used but this key is not defined in nodeKeys!"
        )
      )

  private def allNodeKeys: List[NodeKey] =
    (cpgJson \ "nodeKeys").as[List[NodeKey]]

  private def allNodeTypesByNodeTypeName: Map[String, List[String]] =
    (cpgJson \ "nodeTypes")
      .as[List[NodeType]]
      .map(nodeType => nodeType.name -> nodeType.keys)
      .toMap
}
