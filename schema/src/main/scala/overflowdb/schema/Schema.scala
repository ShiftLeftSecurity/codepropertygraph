package overflowdb.schema

import overflowdb.codegen.Helpers._
import overflowdb.storage.ValueTypes
import scala.collection.mutable

/**
 * @param basePackage: specific for your domain, e.g. `com.example.mydomain`
 */
class Schema(val basePackage: String,
             val properties: Seq[Property],
             val nodeBaseTypes: Seq[NodeBaseType],
             val nodeTypes: Seq[NodeType],
             val edgeTypes: Seq[EdgeType],
             val constantsByCategory: Map[String, Seq[Constant]],
             val protoOptions: Option[ProtoOptions]) {

  /** nodeTypes and nodeBaseTypes combined */
  lazy val allNodeTypes: Seq[AbstractNodeType] =
    nodeTypes ++ nodeBaseTypes

  /** properties that are used in node types */
  def nodeProperties: Seq[Property] =
    properties.filter(property =>
      (nodeTypes ++ nodeBaseTypes).exists(_.properties.contains(property))
    )

  /** properties that are used in edge types */
  def edgeProperties: Seq[Property] =
    properties.filter(property =>
      edgeTypes.exists(_.properties.contains(property))
    )
}

abstract class AbstractNodeType(val name: String, val comment: Option[String], val schemaInfo: SchemaInfo)
  extends HasClassName with HasProperties with HasSchemaInfo {
  protected val _extendz: mutable.Set[NodeBaseType] = mutable.Set.empty
  protected val _outEdges: mutable.Set[AdjacentNode] = mutable.Set.empty
  protected val _inEdges: mutable.Set[AdjacentNode] = mutable.Set.empty

  /** all node types that extend this node */
  def subtypes(allNodes: Set[AbstractNodeType]): Set[AbstractNodeType]

  override def properties: Seq[Property] = {
    /* only to provide feedback for potential schema optimisation: no need to redefine properties if they are already
     * defined in one of the parents */
    for {
      property <- _properties
      baseType <- _extendz
      if baseType.properties.contains(property)
    } println(s"[info]: $this wouldn't need to have $property added explicitly - $baseType already brings it in")

    (_properties ++ _extendz.flatMap(_.properties)).toSeq.sortBy(_.name.toLowerCase)
  }

  def extendz(additional: NodeBaseType*): this.type = {
    additional.foreach(_extendz.add)
    this
  }

  def extendz: Seq[NodeBaseType] =
    _extendz.toSeq

  def extendzRecursively: Seq[NodeBaseType] = {
    val extendsLevel1 = extendz
    (extendsLevel1 ++ extendsLevel1.flatMap(_.extendzRecursively)).distinct
  }

  /**
   * note: allowing to define one outEdge for ONE inNode only - if you are looking for Union Types, please use NodeBaseTypes
   */
  def addOutEdge(edge: EdgeType,
                 inNode: AbstractNodeType,
                 cardinalityOut: Cardinality = Cardinality.List,
                 cardinalityIn: Cardinality = Cardinality.List): this.type = {
    _outEdges.add(AdjacentNode(edge, inNode, cardinalityOut))
    inNode._inEdges.add(AdjacentNode(edge, this, cardinalityIn))
    this
  }

  def addInEdge(edge: EdgeType,
                outNode: AbstractNodeType,
                cardinalityIn: Cardinality = Cardinality.List,
                cardinalityOut: Cardinality = Cardinality.List): this.type = {
    _inEdges.add(AdjacentNode(edge, outNode, cardinalityIn))
    outNode._outEdges.add(AdjacentNode(edge, this, cardinalityOut))
    this
  }

  def outEdges: Seq[AdjacentNode] =
    _outEdges.toSeq

  def inEdges: Seq[AdjacentNode] =
    _inEdges.toSeq
}

class NodeType(name: String, comment: Option[String], schemaInfo: SchemaInfo)
  extends AbstractNodeType(name, comment, schemaInfo) with HasOptionalProtoId  {
  protected val _containedNodes: mutable.Set[ContainedNode] = mutable.Set.empty

  lazy val classNameDb = s"${className}Db"

  /** all node types that extend this node */
  override def subtypes(allNodes: Set[AbstractNodeType]) = Set.empty

  def containedNodes: Seq[ContainedNode] =
    _containedNodes.toSeq.sortBy(_.localName.toLowerCase)

  def addContainedNode(node: AbstractNodeType, localName: String, cardinality: Cardinality): NodeType = {
    _containedNodes.add(ContainedNode(node, localName, cardinality))
    this
  }

  override def toString = s"NodeType($name)"
}

class NodeBaseType(name: String, comment: Option[String], schemaInfo: SchemaInfo)
  extends AbstractNodeType(name, comment, schemaInfo) {

  /** all node types that extend this node */
  override def subtypes(allNodes: Set[AbstractNodeType]) =
    allNodes.filter { candidate =>
      candidate.extendzRecursively.contains(this)
    }

  override def toString = s"NodeBaseType($name)"
}

case class AdjacentNode(viaEdge: EdgeType, neighbor: AbstractNodeType, cardinality: Cardinality)

case class ContainedNode(nodeType: AbstractNodeType, localName: String, cardinality: Cardinality)

sealed abstract class Cardinality(val name: String)
object Cardinality {
  case object ZeroOrOne extends Cardinality("zeroOrOne")
  case object One extends Cardinality("one")
  case object List extends Cardinality("list")
  case object ISeq extends Cardinality("array")

  def fromName(name: String): Cardinality =
    Seq(ZeroOrOne, One, List, ISeq)
      .find(_.name == name)
      .getOrElse(throw new AssertionError(s"cardinality must be one of `zeroOrOne`, `one`, `list`, `iseq`, but was $name"))
}

class EdgeType(val name: String, val comment: Option[String], val schemaInfo: SchemaInfo)
  extends HasClassName with HasProperties with HasOptionalProtoId with HasSchemaInfo {
  override def toString = s"EdgeType($name)"
}

class Property(val name: String,
               val comment: Option[String],
               val valueType: ValueTypes,
               val cardinality: Cardinality,
               val schemaInfo: SchemaInfo) extends HasClassName with HasOptionalProtoId with HasSchemaInfo {
  override def toString = s"Property($name)"
}

class Constant(val name: String,
               val value: String,
               val valueType: ValueTypes,
               val comment: Option[String],
               val schemaInfo: SchemaInfo) extends HasOptionalProtoId with HasSchemaInfo {
  override def toString = s"Constant($name)"
}

object Constant {
  def apply(name: String, value: String, valueType: ValueTypes, comment: String = "")(
    implicit schemaInfo: SchemaInfo = SchemaInfo.Unknown): Constant =
    new Constant(name, value, valueType, stringToOption(comment), schemaInfo)
}

case class NeighborInfoForEdge(edge: EdgeType, nodeInfos: Seq[NeighborInfoForNode], offsetPosition: Int) {
  lazy val deriveNeighborNodeType: String = {
    deriveCommonSuperType(nodeInfos.map(_.neighborNode).toSet)
      .map(_.className)
      .getOrElse("StoredNode")
  }
}

case class NeighborInfoForNode(
  neighborNode: AbstractNodeType,
  edge: EdgeType,
  direction: Direction.Value,
  cardinality: Cardinality,
  isInherited: Boolean) {

  lazy val accessorName = s"_${camelCase(neighborNode.name)}Via${edge.className}${camelCaseCaps(direction.toString)}"

  /** handling some accidental complexity within the schema: if a relationship is defined on a base node and
   * separately on a concrete node, with different cardinalities, we need to use the highest cardinality  */
  lazy val consolidatedCardinality: Cardinality = {
    val inheritedCardinalities = neighborNode.extendzRecursively.flatMap(_.inEdges).collect {
      case AdjacentNode(viaEdge, neighbor, cardinality)
        if viaEdge == edge && neighbor == neighborNode => cardinality
    }
    val allCardinalities = cardinality +: inheritedCardinalities
    allCardinalities.distinct.sortBy {
      case Cardinality.List => 0
      case Cardinality.ISeq => 1
      case Cardinality.ZeroOrOne => 2
      case Cardinality.One => 3
    }.head
  }

  lazy val returnType: String =
    fullScalaType(neighborNode, consolidatedCardinality)

}

object HigherValueType extends Enumeration {
  type HigherValueType = Value
  val None, Option, List, ISeq = Value
}

object Direction extends Enumeration {
  val IN, OUT = Value
  val all = List(IN, OUT)
}

object DefaultEdgeTypes {
  // TODO define this in actual schema, not here
  val ContainsNode = new EdgeType("CONTAINS_NODE", None, SchemaInfo.forClass(getClass)).protoId(9)
}

case class ProductElement(name: String, accessorSrc: String, index: Int)

case class ProtoOptions(pkg: String,
                        javaOuterClassname: String,
                        javaPackage: String,
                        goPackage: String,
                        csharpNamespace: String,
                        uncommonProtoEnumNameMappings: Map[String, String] = Map.empty)

trait HasClassName {
  def name: String
  lazy val className = camelCaseCaps(name)
}

trait HasProperties {
  protected val _properties: mutable.Set[Property] = mutable.Set.empty

  def addProperty(additional: Property): this.type = {
    _properties.add(additional)
    this
  }

  def addProperties(additional: Property*): this.type = {
    additional.foreach(addProperty)
    this
  }

  def properties: Seq[Property] = _properties.toSeq.sortBy(_.name)
}

trait HasOptionalProtoId {
  protected var _protoId: Option[Int] = None

  def protoId(id: Int): this.type = {
    _protoId = Some(id)
    this
  }

  def protoId: Option[Int] = _protoId
}

trait HasSchemaInfo {
  def schemaInfo: SchemaInfo
}

/** carry extra information on where a schema element is being defined, e.g. when we want to be able to
 * refer back that `node XYZ` was defined in `BaseSchema`, e.g. for documentation */
case class SchemaInfo(definedIn: Option[Class[_]])
object SchemaInfo {
  val Unknown = SchemaInfo(None)

  def forClass(schemaClass: Class[_]): SchemaInfo =
    SchemaInfo(Option(schemaClass))
}
