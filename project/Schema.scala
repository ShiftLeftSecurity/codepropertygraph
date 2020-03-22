package overflowdb.codegen

import java.io.FileInputStream
import play.api.libs.json._
import play.api.libs.functional.syntax._

class Schema(schemaFile: String) {
  implicit private val nodeBaseTraitRead = Json.reads[NodeBaseTrait]
  implicit private val outEdgeEntryRead = Json.reads[OutEdgeEntry]
  implicit private val containedNodeRead = Json.reads[ContainedNode]
  implicit private val nodeTypesRead = Json.reads[NodeType]
  implicit private val propertyRead = Json.reads[Property]
  implicit private val edgeTypeRead = Json.reads[EdgeType]

  lazy val jsonRoot = Json.parse(new FileInputStream(schemaFile))
  lazy val nodeBaseTraits = (jsonRoot \ "nodeBaseTraits").as[List[NodeBaseTrait]]
  lazy val nodeTypes = (jsonRoot \ "nodeTypes").as[List[NodeType]]
  lazy val edgeTypes = (jsonRoot \ "edgeTypes").as[List[EdgeType]]
  lazy val nodeKeys = (jsonRoot \ "nodeKeys").as[List[Property]]
  lazy val edgeKeys = (jsonRoot \ "edgeKeys").as[List[Property]]

  lazy val nodeTypeByName: Map[String, NodeType] =
    nodeTypes.map(node => node.name -> node).toMap

  lazy val nodePropertyByName: Map[String, Property] =
    nodeKeys.map(property => property.name -> property).toMap

  lazy val edgePropertyByName: Map[String, Property] =
    edgeKeys.map(property => property.name -> property).toMap

  /* schema only specifies `node.outEdges` - this builds a reverse map (essentially `node.inEdges`) along with the outNodes */
  lazy val nodeToInEdgeContexts: Map[NodeType, Seq[InEdgeContext]] = {
    val tuples: Seq[(NodeType, String, NodeType)] =
      for {
        nodeType <- nodeTypes
        outEdge <- nodeType.outEdges
        inNodeName <- outEdge.inNodes
        inNode = nodeTypeByName.get(inNodeName) if inNode.isDefined
      } yield (inNode.get, outEdge.edgeName, nodeType)

    /* grouping above (node, inEdge, adjacentNode) tuples by `node` and `inEdge`
     * TODO use scala 2.13's `groupMap` */
    val grouped: Map[NodeType, Map[String, Seq[NodeType]]] =
      tuples.groupBy(_._1).mapValues(_.groupBy(_._2).mapValues(_.map(_._3)).toMap)

    grouped.mapValues { inEdgesWithAdjacentNodes =>
      // all nodes can have incoming `CONTAINS_NODE` edges
      val adjustedInEdgesWithAdjacentNodes =
        if (inEdgesWithAdjacentNodes.contains(DefaultEdgeTypes.ContainsNode)) inEdgesWithAdjacentNodes
        else inEdgesWithAdjacentNodes + (DefaultEdgeTypes.ContainsNode -> Set.empty)

      adjustedInEdgesWithAdjacentNodes.map { case (edge, adjacentNodes) =>
        InEdgeContext(edge, adjacentNodes.toSet)
      }.toSeq
    }
  }

  lazy val defaultConstantReads: Reads[Constant] = constantReads("name", "name")

  def constantReads(nameField: String, valueField: String): Reads[Constant] = (
    (JsPath \ nameField).read[String] and
      (JsPath \ valueField).read[String] and
      (JsPath \ "comment").readNullable[String] and
      (JsPath \ "valueType").readNullable[String]
    )(Constant.apply _)

  def constantsFromElement(rootElementName: String)(implicit reads: Reads[Constant] = defaultConstantReads): List[Constant] =
    (jsonRoot \ rootElementName).get.validate[List[Constant]].get
}

case class NodeType(
    name: String,
    comment: Option[String],
    id: Int,
    keys: List[String],
    outEdges: List[OutEdgeEntry],
    is: Option[List[String]],
    containedNodes: Option[List[ContainedNode]]) {
  lazy val className = Helpers.camelCaseCaps(name)
  lazy val classNameDb = s"${className}Db"
  lazy val containedNodesList = containedNodes.getOrElse(Nil)
}

case class OutEdgeEntry(edgeName: String, inNodes: List[String])

case class ContainedNode(nodeType: String, localName: String, cardinality: String) {
  lazy val nodeTypeClassName = Helpers.camelCaseCaps(nodeType)
}

sealed abstract class Cardinality(val name: String)
object Cardinality {
  case object ZeroOrOne extends Cardinality("zeroOrOne")
  case object One extends Cardinality("one")
  case object List extends Cardinality("list")

  def fromName(name: String): Cardinality =
    Seq(ZeroOrOne, One, List)
      .find(_.name == name)
      .getOrElse(throw new AssertionError(s"cardinality must be one of `zeroOrOne`, `one`, `list`, but was $name"))
}

case class EdgeType(name: String, keys: List[String], comment: Option[String]) {
  lazy val className = Helpers.camelCaseCaps(name)
}

case class Property(name: String, comment: Option[String], valueType: String, cardinality: String)

case class NodeBaseTrait(name: String, hasKeys: List[String], `extends`: Option[List[String]]) {
  lazy val extendz = `extends` //it's mapped from the key in json :(
  lazy val className = Helpers.camelCaseCaps(name)
}

case class InEdgeContext(edgeName: String, outNodes: Set[NodeType])
case class NeighborInfo(neighborAccessorName: String, neighborNodeType: String)

object HigherValueType extends Enumeration {
  type HigherValueType = Value
  val None, Option, List = Value
}

object Direction extends Enumeration {
  val IN, OUT = Value
  val all = List(IN, OUT)
}

object DefaultNodeTypes {
  /** root type for all nodes */
  val Node = "NODE"
}

object DefaultEdgeTypes {
  val ContainsNode = "CONTAINS_NODE"
}

case class ProductElement(name: String, accessorSrc: String, index: Int)

case class Constant(name: String, value: String, comment: Option[String], tpe: Option[String] = None)
