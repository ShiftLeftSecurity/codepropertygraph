import better.files.File
import java.io.{FileInputStream, File => JFile}
import play.api.libs.json._
import scala.collection.mutable

class Schema(schemaFile: String) {
  implicit val nodeBaseTraitRead = Json.reads[NodeBaseTrait]
  implicit val outEdgeEntryRead = Json.reads[OutEdgeEntry]
  implicit val containedNodeRead = Json.reads[ContainedNode]
  implicit val nodeTypesRead = Json.reads[NodeType]
  implicit val propertyRead = Json.reads[Property]
  implicit val edgeTypeRead = Json.reads[EdgeType]

  private lazy val jsonRoot = Json.parse(new FileInputStream(schemaFile))
  lazy val nodeBaseTraits = (jsonRoot \ "nodeBaseTraits").as[List[NodeBaseTrait]]
  lazy val nodeTypes = (jsonRoot \ "nodeTypes").as[List[NodeType]]
  lazy val edgeTypes = (jsonRoot \ "edgeTypes").as[List[EdgeType]]
  lazy val nodeKeys = (jsonRoot \ "nodeKeys").as[List[Property]]
  lazy val edgeKeys = (jsonRoot \ "edgeKeys").as[List[Property]]

  lazy val nodeTypeByName: Map[String, NodeType] =
    nodeTypes.map { node => (node.name, node)}.toMap

  /* nodes only specify their `outEdges` - this builds a reverse map (essentially `node.inEdges`) */
  lazy val nodeToInEdges: Map[String, Set[String]] = {
    val nodeToInEdges = new mutable.HashMap[String, mutable.Set[String]] with mutable.MultiMap[String, String]

    for {
      nodeType <- nodeTypes
      outEdge  <- nodeType.outEdges
      inNodeName   <- outEdge.inNodes
//      inNode = nodeTypeByName(inNodeName)
    } nodeToInEdges.addBinding(inNodeName, outEdge.edgeName)

    // all nodes can have incoming `CONTAINS_NODE` edges
    nodeTypes.foreach { nodeType =>
      nodeToInEdges.addBinding(nodeType.name, "CONTAINS_NODE")
    }

    nodeToInEdges.mapValues(_.toSet).toMap
  }

  /* nodes only specify their `outEdges.inNodes` - this builds a reverse map (essentially `node.inEdges.outNodes`) */
  lazy val nodeToInNodes: Map[NodeType, Set[String]] = {
    val nodeToInNodes = new mutable.HashMap[NodeType, mutable.Set[String]] with mutable.MultiMap[NodeType, String]

    for {
      nodeType <- nodeTypes
      outEdge  <- nodeType.outEdges
      inNodeName   <- outEdge.inNodes
      inNode = nodeTypeByName(inNodeName)
    } nodeToInNodes.addBinding(inNode, nodeType.name)

    // all nodes can have incoming `CONTAINS_NODE` edges
    nodeTypes.foreach { nodeType =>
      nodeToInNodes.addBinding(nodeType, "CONTAINS_NODE")
    }

    nodeToInNodes.mapValues(_.toSet).toMap
  }
}

/** Generates a domain model for OverflowDb traversals based on your domain-specific json schema.
  *
  * @param schemaFile: path to the schema (json file)
  * @param basePackage: specific for your domain, e.g. `com.example.mydomain`
  */
class DomainClassCreator(schemaFile: String, basePackage: String) {
  import Helpers._
  val nodesPackage = s"$basePackage.nodes"
  val edgesPackage = s"$basePackage.edges"
  val schema = new Schema(schemaFile)

  def run(outputDir: JFile): List[JFile] =
    List(writeEdgesFile(outputDir), writeNodesFile(outputDir), writeNewNodesFile(outputDir))

  def writeEdgesFile(outputDir: JFile): JFile = {
    val propertyByName: Map[String, Property] =
      schema.edgeKeys.map(key => key.name -> key).toMap

    def entries: List[String] =
      schema.edgeTypes.map(edge => generateEdgeSource(edge, edge.keys.map(propertyByName)))

    def edgeHeader = {
      val staticHeader =
        s"""package $edgesPackage
           | 
           |import java.lang.{Boolean => JBoolean, Long => JLong}
           |import java.util.{Set => JSet}
           |import java.util.{List => JList}
           |import org.apache.tinkerpop.gremlin.structure.Property
           |import org.apache.tinkerpop.gremlin.structure.{Vertex, VertexProperty}
           |import io.shiftleft.overflowdb.{EdgeLayoutInformation, EdgeFactory, NodeFactory, OdbEdge, OdbNode, OdbGraph, NodeRef}
           |import scala.jdk.CollectionConverters._
           |import org.slf4j.LoggerFactory
           |
           |object PropertyErrorRegister {
           |  private var errorMap = Set[(Class[_], String)]()
           |  private val logger = LoggerFactory.getLogger(getClass)
           |
           |  def logPropertyErrorIfFirst(clazz: Class[_], propertyName: String): Unit = {
           |    if (!errorMap.contains((clazz, propertyName))) {
           |      logger.warn("Property " + propertyName + " is deprecated for " + clazz.getName + ".")
           |      errorMap += ((clazz, propertyName))
           |    }
           |  }
           |}
           |""".stripMargin

      val factories = {
        val edgeFactories: List[String] =
          schema.edgeTypes.map(edgeType => edgeType.className + ".factory")
        s"""object Factories {
           |  lazy val all: List[EdgeFactory[_]] = $edgeFactories
           |  lazy val allAsJava: java.util.List[EdgeFactory[_]] = all.asJava
           |}
           |""".stripMargin
      }

      staticHeader + factories
    }

    def generateEdgeSource(edgeType: EdgeType, keys: List[Property]) = {
      val edgeClassName = edgeType.className
      val keysQuoted = keys.map('"' + _.name + '"')
      val keyToValueMap = keys
        .map { key =>
          s""" "${key.name}" -> { instance: $edgeClassName => instance.${camelCase(key.name)}()}"""
        }
        .mkString(",\n")

      val companionObject =
        s"""object $edgeClassName {
           |  val Label = "${edgeType.name}"
           |
           |  object PropertyNames {
           |    val all: Set[String] = Set(${keysQuoted.mkString(", ")})
           |    val allAsJava: JSet[String] = all.asJava
           |  }
           |
           |  object Properties {
           |    val keyToValue: Map[String, $edgeClassName => Any] = Map(
           |      $keyToValueMap
           |    )
           |  }
           |
           |  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)
           |
           |  val factory = new EdgeFactory[$edgeClassName] {
           |    override val forLabel = $edgeClassName.Label
           |
           |    override def createEdge(graph: OdbGraph, outNode: NodeRef[OdbNode], inNode: NodeRef[OdbNode]) =
           |      new $edgeClassName(graph, outNode, inNode)
           |  }
           |}
           |""".stripMargin

      def propertyBasedFieldAccessors(properties: List[Property]): String =
        properties.map { property =>
          val name = camelCase(property.name)
          val baseType = getBaseType(property)
          val tpe = getCompleteType(property)

          // TODO refactor so we don't need to wrap the property in a separate Property instance, only to unwrap it later
          getHigherType(property) match {
            case HigherValueType.None =>
              s"""def $name(): $tpe = property("${property.name}").value.asInstanceOf[$tpe]"""
            case HigherValueType.Option =>
              s"""def $name(): $tpe = {
                 |  val tp = property("${property.name}")
                 |  if (tp.isPresent) Option(tp.value.asInstanceOf[$baseType])
                 |  else None
                 |}""".stripMargin
            case HigherValueType.List =>
              s"""private var _$name: $tpe = Nil
                 |def $name(): $tpe = {
                 |  val tp = property("${property.name}")
                 |  if (tp.isPresent) tp.value.asInstanceOf[JList].asScala
                 |  else Nil
                 |}""".stripMargin
          }
        }.mkString("\n\n")

      val classImpl =
        s"""class $edgeClassName(_graph: OdbGraph, _outNode: NodeRef[OdbNode], _inNode: NodeRef[OdbNode])
           |extends OdbEdge(_graph, $edgeClassName.Label, _outNode, _inNode, $edgeClassName.PropertyNames.allAsJava) {
           |${propertyBasedFieldAccessors(keys)}
           |}
           |""".stripMargin

      companionObject + classImpl
    }

    val filename = outputDir.getPath + "/" + edgesPackage.replaceAll("\\.", "/") + "/Edges.scala"
    writeFile(filename, edgeHeader, entries)
  }

  def neighborAccessorName(edgeTypeName: String, direction: String): String =
    camelCase(edgeTypeName + "_" + direction)

  def writeNodesFile(outputDir: JFile): JFile = {
    val propertyByName: Map[String, Property] =
      schema.nodeKeys.map(prop => prop.name -> prop).toMap

    lazy val entries: List[String] =
      schema.nodeTypes.map { nodeType =>
        generateNodeSource(nodeType, nodeType.keys.map(propertyByName))
      }

    lazy val nodeHeader = {
      /* generic accessors for all potential neighbors. specific nodes override them, in case they really allow that edge type
       * TODO: resolve debate between Michael/Bernhard/Markus - these may not be needed. in the meantime, we also have
       * specific neighbor accessors driven by the schema, i.e. those are only available on the types that really allow the given edge type
       */
      val genericNeighborAccessors = for {
        direction <- List("IN", "OUT")
        edgeType <- schema.edgeTypes
        accessor = neighborAccessorName(edgeType.name, direction)
      } yield s"def _$accessor(): JIterator[StoredNode] = { JCollections.emptyIterator() }"

      val staticHeader =
        s"""package $nodesPackage
           |
           |import gremlin.scala._
           |import $basePackage.EdgeKeys
           |import $basePackage.edges
           |import $basePackage.edges._
           |import java.lang.{Boolean => JBoolean, Long => JLong}
           |import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
           |import org.apache.tinkerpop.gremlin.structure.{Direction, Vertex, VertexProperty}
           |import io.shiftleft.overflowdb.{EdgeFactory, NodeFactory, NodeLayoutInformation, OdbNode, OdbGraph, OdbNodeProperty, NodeRef}
           |import io.shiftleft.overflowdb.traversal.{NodeRefOps, PropertyKey, Traversal}
           |import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils
           |import scala.jdk.CollectionConverters._
           |import org.slf4j.LoggerFactory
           |
           |object PropertyErrorRegister {
           |  private var errorMap = Set[(Class[_], String)]()
           |  private val logger = LoggerFactory.getLogger(getClass)
           |
           |  def logPropertyErrorIfFirst(clazz: Class[_], propertyName: String): Unit = {
           |    if (!errorMap.contains((clazz, propertyName))) {
           |      logger.warn("Property " + propertyName + " is deprecated for " + clazz.getName + ".")
           |      errorMap += ((clazz, propertyName))
           |    }
           |  }
           |}
           |
           |trait Node extends Product {
           |  def label: String
           |  def getId: JLong
           |
           |  /** labels of product elements, used e.g. for pretty-printing */
           |  def productElementLabel(n: Int): String
           |}
           |
           |/* a node that stored inside an OdbGraph (rather than e.g. DiffGraph) */
           |trait StoredNode extends Vertex with Node {
           |  /* underlying vertex in the graph database. 
           |   * since this is a StoredNode, this is always set */
           |  def underlying: Vertex = this
           |
           |  // Java does not seem to be capable of calling methods from java classes if a scala trait is in the inheritance
           |  // chain.
           |  override def getId: JLong = underlying.id.asInstanceOf[JLong]
           |
           |  /* all properties plus label and id */
           |  def toMap: Map[String, Any] = {
           |    val map = valueMap
           |    map.put("_label", label)
           |    map.put("_id", getId)
           |    map.asScala.toMap
           |  }
           |
           |  /* all properties */
           |  def valueMap: JMap[String, AnyRef]
           |
           |  ${genericNeighborAccessors.mkString("\n")}
           |}
           |""".stripMargin

      val nodeBaseTraits = schema.nodeBaseTraits.map { nodeBaseTrait =>
        val mixins = nodeBaseTrait.hasKeys.map { key =>
          s"with Has${camelCaseCaps(key)}"
        }.mkString(" ")

        val mixinTraits = nodeBaseTrait
          .extendz
          .getOrElse(Nil)
          .map { traitName =>
            s"with ${camelCaseCaps(traitName)}"
          }.mkString(" ")

        val mixinTraitsForBase = nodeBaseTrait
          .extendz
          .getOrElse(List())
          .map { traitName =>
            s"with ${camelCaseCaps(traitName)}Base"
          }.mkString(" ")

        s"""trait ${nodeBaseTrait.className}Base extends Node $mixins $mixinTraitsForBase
           |trait ${nodeBaseTrait.className} extends StoredNode with ${nodeBaseTrait.className}Base $mixinTraits
           |""".stripMargin
      }.mkString("\n")

      val keyBasedTraits =
        schema.nodeKeys.map { property =>
          val camelCaseName = camelCase(property.name)
          val camelCaseCapitalized = camelCaseName.capitalize
          val tpe = getCompleteType(property)
          s"trait Has$camelCaseCapitalized { def $camelCaseName: $tpe }"
        }.mkString("\n") + "\n"

      val factories = {
        val nodeFactories: List[String] =
          schema.nodeTypes.map(nodeType => nodeType.className + ".factory")
        s"""object Factories {
           |  lazy val all: List[NodeFactory[_]] = $nodeFactories
           |  lazy val allAsJava: java.util.List[NodeFactory[_]] = all.asJava
           |}
           |""".stripMargin
      }

      staticHeader + nodeBaseTraits + keyBasedTraits + factories
    }

    def generateNodeSource(nodeType: NodeType, keys: List[Property]) = {
      val keyConstants = keys.map(key => s"""val ${camelCaseCaps(key.name)} = "${key.name}" """).mkString("\n")
      val keyToValueMap = keys
        .map { property: Property =>
          getHigherType(property) match {
            case HigherValueType.None | HigherValueType.List =>
              s""" "${property.name}" -> { instance: ${nodeType.classNameDb} => instance.${camelCase(property.name)}}"""
            case HigherValueType.Option =>
              s""" "${property.name}" -> { instance: ${nodeType.classNameDb} => instance.${camelCase(property.name)}.orNull}"""
          }
        }
        .mkString(",\n")

      val outEdgeNames: List[String] = nodeType.outEdges.map(_.edgeName)
      val inEdgeNames:  List[String] = schema.nodeToInEdges.getOrElse(nodeType.name, Set.empty).toList

      val outEdgeLayouts = outEdgeNames.map(edge => s"edges.${camelCaseCaps(edge)}.layoutInformation").mkString(", ")
      val inEdgeLayouts = inEdgeNames.map(edge => s"edges.${camelCaseCaps(edge)}.layoutInformation").mkString(", ")

      val className = nodeType.className
      val classNameDb = nodeType.classNameDb

      val companionObject =
        s"""object $className {
           |  def apply(graph: OdbGraph, id: Long) = new $className(graph, id)
           |
           |  val Label = "${nodeType.name}"
           |  val LabelId: Int = ${nodeType.id}
           |
           |  val layoutInformation = new NodeLayoutInformation(
           |    LabelId,
           |    PropertyNames.allAsJava,
           |    List($outEdgeLayouts).asJava,
           |    List($inEdgeLayouts).asJava)
           |
           |  object PropertyNames {
           |    $keyConstants
           |    val all: Set[String] = Set(${keys.map { key => camelCaseCaps(key.name) }.mkString(", ") })
           |    val allAsJava: JSet[String] = all.asJava
           |  }
           |
           |  object Properties {
           |    val keyToValue: Map[String, $classNameDb => Any] = Map(
           |      $keyToValueMap
           |    )
           |  }
           |
           |  object Edges {
           |    val In: Array[String] = Array(${inEdgeNames.map('"' + _ + '"').mkString(",")})
           |    val Out: Array[String] = Array(${outEdgeNames.map('"' + _ + '"').mkString(",")})
           |  }
           |
           |  val factory = new NodeFactory[$classNameDb] {
           |    override val forLabel = $className.Label
           |    override val forLabelId = $className.LabelId
           |
           |    override def createNode(ref: NodeRef[$classNameDb]) =
           |      new $classNameDb(ref.asInstanceOf[NodeRef[OdbNode]])
           |
           |    override def createNodeRef(graph: OdbGraph, id: Long) = $className(graph, id)
           |  }
           |}
           |""".stripMargin

      val mixinTraits: String =
        nodeType.is
          .getOrElse(List())
          .map { traitName =>
            s"with ${camelCaseCaps(traitName)}"
          }
          .mkString(" ")
      val mixinTraitsForBase: String =
        nodeType.is
          .getOrElse(List())
          .map { traitName =>
            s"with ${camelCaseCaps(traitName)}Base"
          }
          .mkString(" ")

      val propertyBasedTraits = keys.map(key => s"with Has${camelCaseCaps(key.name)}").mkString(" ")

      val valueMapImpl = {
        val putKeysImpl = keys
          .map { key: Property =>
            val memberName = camelCase(key.name)
            Cardinality.fromName(key.cardinality) match {
              case Cardinality.One =>
                s"""if ($memberName != null) { properties.put("${key.name}", $memberName) }"""
              case Cardinality.ZeroOrOne =>
                s"""$memberName.map { value => properties.put("${key.name}", value) }"""
              case Cardinality.List => // need java list, e.g. for NodeSerializer
                s"""if ($memberName.nonEmpty) { properties.put("${key.name}", $memberName.asJava) }"""
            }
          }
          .mkString("\n")

        s""" {
        |  val properties = new JHashMap[String, AnyRef]
        |  $putKeysImpl
        |  properties
        |}""".stripMargin
      }

      val containedNodesAsMembers =
        nodeType.containedNodes
          .map {
            _.map { containedNode =>
              val containedNodeType = containedNode.nodeTypeClassName
              val cardinality = Cardinality.fromName(containedNode.cardinality)
              val completeType = cardinality match {
                case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
                case Cardinality.One       => containedNodeType
                case Cardinality.List      => s"List[$containedNodeType]"
              }
              val traversalEnding = cardinality match {
                case Cardinality.ZeroOrOne => s".headOption"
                case Cardinality.One       => s".head"
                case Cardinality.List      => s".toList"
              }

              s"""/** link to 'contained' node of type $containedNodeType */
                 |def ${containedNode.localName}: $completeType =
                 |  edges(Direction.OUT, "CONTAINS_NODE").asScala.toList
                 |    .filter(_.valueOption(EdgeKeys.LOCAL_NAME).map(_  == "${containedNode.localName}").getOrElse(false))
                 |    .sortBy(_.valueOption(EdgeKeys.INDEX))
                 |    .map(_.inVertex.asInstanceOf[$containedNodeType])
                 |    $traversalEnding
                 |""".stripMargin
            }.mkString("\n")
          }
          .getOrElse("")

      val productElementLabels =
        keys.zipWithIndex
          .map { case (key, idx) =>
            s"""case ${idx + 1} => "${camelCase(key.name)}" """
          }
          .mkString("\n")

      val productElementAccessors =
        keys.zipWithIndex
          .map { case (key, idx) =>
            s"case ${idx + 1} => ${camelCase(key.name)}"
          }
          .mkString("\n")

      val abstractContainedNodeAccessors = nodeType.containedNodes
        .map {
          _.map { containedNode =>
            s"""def ${containedNode.localName}: ${getCompleteType(containedNode)}"""
          }.mkString("\n")
        }
        .getOrElse("")

      val delegatingContainedNodeAccessors = nodeType.containedNodes
        .map {
          _.map { containedNode =>
            s"""def ${containedNode.localName} = get().${containedNode.localName}"""
          }.mkString("\n")
        }
        .getOrElse("")

      val nodeBaseImpl =
        s"""trait ${className}Base extends Node $mixinTraitsForBase $propertyBasedTraits {
           |  def asStored : StoredNode = this.asInstanceOf[StoredNode]
           |  override def getId: JLong = -1L
           |
           |  $abstractContainedNodeAccessors
           |
           |  override def productElementLabel(n: Int): String =
           |      n match {
           |        case 0 => "id"
           |        $productElementLabels
           |      }
           |
           |  override def productElement(n: Int): Any =
           |      n match {
           |        case 0 => getId
           |        $productElementAccessors
           |      }
           |
           |  override def productPrefix = "$className"
           |  override def productArity = ${keys.size} + 1 // add one for id, leaving out `_graph`
           |}
           |""".stripMargin

      val neighborDelegators =
        (outEdgeNames.map(edge => neighborAccessorName(edge, "OUT")) ++
          inEdgeNames.map(edge => neighborAccessorName(edge, "IN")))
        .map { nbaName =>
          /* generic and specific neighbor accessors - as mentioned in comment above, we may not need generic ones (prefixed with `_`) in future */
          s"""def $nbaName: JIterator[StoredNode] = get().$nbaName
             |override def _$nbaName(): JIterator[StoredNode] = $nbaName
             |""".stripMargin
        }.mkString("\n")

      val nodeRefImpl = {
        val propertyDelegators = keys
          .map(_.name)
          .map(camelCase)
          .map { name =>
            s"""override def $name = get().$name"""
          }
          .mkString("\n")

        s"""class $className(graph: OdbGraph, id: Long)
           |  extends NodeRef[$classNameDb](graph, id)
           | // TODO use once we're on the new traversal dsl:
           | // with NodeRefOps[$className]
           |  with ${className}Base
           |  with StoredNode
           |  $mixinTraits {
           |  $propertyDelegators
           |  $delegatingContainedNodeAccessors
           |  $neighborDelegators
           |  override def valueMap: JMap[String, AnyRef] = get.valueMap
           |  override def canEqual(that: Any): Boolean = get.canEqual(that)
           |  override def label: String = {
           |    $className.Label
           |  }
           |}
           |""".stripMargin
      }
      val neighborAccessors =
        (outEdgeNames.map(edge => neighborAccessorName(edge, "OUT")) ++
          inEdgeNames.map(edge => neighborAccessorName(edge, "IN"))).zipWithIndex
        .map { case (nbaName: String, offsetPos: Int) =>
          /* generic and specific neighbor accessors - as mentioned in comment above, we may not need generic ones (prefixed with `_`) in future */
          s"""def $nbaName : JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet($offsetPos).asInstanceOf[JIterator[StoredNode]]
             |override def _$nbaName : JIterator[StoredNode] = $nbaName""".stripMargin
        }.mkString("\n")

      val classImpl =
        s"""class $classNameDb(ref: NodeRef[OdbNode]) extends OdbNode(ref) with StoredNode 
           |  $mixinTraits with ${className}Base {
           |
           |  override def layoutInformation: NodeLayoutInformation = $className.layoutInformation
           |  override def getId = ref.id
           |
           |  /* all properties */
           |  override def valueMap: JMap[String, AnyRef] = $valueMapImpl
           |
           |  ${propertyBasedFields(keys)}
           |  $neighborAccessors
           |
           |  override def label: String = {
           |    $className.Label
           |  }
           |
           |  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[$classNameDb]
           |
           |  /* performance optimisation to save instantiating an iterator for each property lookup */
           |  override protected def specificProperty[A](key: String): VertexProperty[A] = {
           |    $className.Properties.keyToValue.get(key) match {
           |      case None => VertexProperty.empty[A]
           |      case Some(fieldAccess) => 
           |        fieldAccess(this) match {
           |          case null | None => VertexProperty.empty[A]
           |          case values: List[_] => throw Vertex.Exceptions.multiplePropertiesExistForProvidedKey(key)
           |          case Some(value) => new OdbNodeProperty(-1, this, key, value.asInstanceOf[A])
           |          case value => new OdbNodeProperty(-1, this, key, value.asInstanceOf[A])
           |        }
           |    }
           |  }
           |
           |  override protected def specificProperties[A](key: String): JIterator[VertexProperty[A]] = {
           |    $className.Properties.keyToValue.get(key) match {
           |      case None => JCollections.emptyIterator[VertexProperty[A]]
           |      case Some(fieldAccess) => 
           |        fieldAccess(this) match {
           |          case null => JCollections.emptyIterator[VertexProperty[A]]
           |          case values: List[_] => 
           |            values.map { value => 
           |              new OdbNodeProperty(-1, this, key, value).asInstanceOf[VertexProperty[A]]
           |            }.iterator.asJava
           |          case value => IteratorUtils.of(new OdbNodeProperty(-1, this, key, value.asInstanceOf[A]))
           |        }
           |    }
           |  }
           |
           |  override protected def updateSpecificProperty[A](cardinality: VertexProperty.Cardinality, key: String, value: A): VertexProperty[A] = {
           |    ${updateSpecificPropertyBody(keys)}
           |    new OdbNodeProperty(-1, this, key, value)
           |  }
           |
           |  override protected def removeSpecificProperty(key: String): Unit =
           |    ${removeSpecificPropertyBody(keys)}
           |
           |  $containedNodesAsMembers
           |}
      |""".stripMargin

      s"\n//${nodeType.name} BEGIN\n" + companionObject + nodeBaseImpl + nodeRefImpl + classImpl + s"\n//${nodeType.name} END\n"
    }

    val filename = outputDir.getPath + "/" + nodesPackage.replaceAll("\\.", "/") + "/Nodes.scala"
    writeFile(filename, nodeHeader, entries)
  }

  /** generates classes to easily add new nodes to the graph
    * this ability could have been added to the existing nodes, but it turned out as a different specialisation,
    * since e.g. `id` is not set before adding it to the graph */
  def writeNewNodesFile(outputDir: JFile): JFile = {
    val staticHeader =
      s"""package $nodesPackage
         |
         |import java.lang.{Boolean => JBoolean, Long => JLong}
         |import java.util.{Map => JMap, Set => JSet}
         |
         |/** base type for all nodes that can be added to a graph, e.g. the diffgraph */
         |trait NewNode extends Node {
         |  override def label: String
         |  def properties: Map[String, Any]
         |  def containedNodesByLocalName: Map[String, List[Node]]
         |  def allContainedNodes: List[Node] = containedNodesByLocalName.values.flatten.toList
         |}
         |""".stripMargin

    val propertyByName: Map[String, Property] =
      schema.nodeKeys.map(prop => prop.name -> prop).toMap

    def entries: List[String] =
      schema.nodeTypes.map(node => generateNewNodeSource(node, node.keys.map(propertyByName)))

    def generateNewNodeSource(nodeType: NodeType, keys: List[Property]) = {
      val fields: String = {
        val forKeys = keys.map { key =>
          val optionalDefault =
            if (getHigherType(key) == HigherValueType.Option) " = None"
            else if (key.valueType == "int") " = -1"
            else if (getHigherType(key) == HigherValueType.None && key.valueType == "string") """ ="" """
            else if (getHigherType(key) == HigherValueType.List) "= List()"
            else ""
          s"${camelCase(key.name)}: ${getCompleteType(key)} $optionalDefault"
        }

        val forContainedNodes: List[String] = nodeType.containedNodes
          .map {
            _.map { containedNode =>
              val optionalDefault = Cardinality.fromName(containedNode.cardinality) match {
                case Cardinality.List      => "= List()"
                case Cardinality.ZeroOrOne => "= None"
                case _                     => ""
              }

              s"val ${containedNode.localName}: ${getCompleteType(containedNode)} $optionalDefault"
            }
          }
          .getOrElse(Nil)

        (forKeys ++ forContainedNodes).mkString(", ")
      }

      val propertiesImpl = keys match {
        case Nil => "Map.empty"
        case keys =>
          val containsOptionals = keys.exists { property =>
            Cardinality.fromName(property.cardinality) == Cardinality.ZeroOrOne
          }
          val forKeys = keys
            .map { key: Property =>
              s"""("${key.name}" -> ${camelCase(key.name)} )"""
            }
            .mkString(",\n")

          val baseCase = s"""Map($forKeys).asInstanceOf[Map[String, Any]].filterNot { case (k,v) => v == null || v == None } """
          if (!containsOptionals) baseCase
          else baseCase + s""".map {
                             |  case (k, v: Option[_]) => (k,v.get)
                             |  case other => other
                             |}
                             |""".stripMargin
      }

      val containedNodesByLocalName: String = {
        val mappedNodes = nodeType.containedNodes
          .getOrElse(Nil)
          .map { containedNode =>
            val localName = containedNode.localName
            val value = Cardinality.fromName(containedNode.cardinality) match {
              case Cardinality.One       => s"($localName :: Nil)"
              case Cardinality.ZeroOrOne => s"$localName.toList"
              case Cardinality.List      => localName
            }
            s"""("$localName" -> $value)"""
          }
        if (mappedNodes.isEmpty) {
          "Map.empty"
        } else {
          mappedNodes.mkString("Map.empty + ", " + ", "")
        }
      }

      s"""case class New${nodeType.className}($fields) extends NewNode with ${nodeType.className}Base {
         |  override val label = "${nodeType.name}"
         |  override val properties: Map[String, Any] = $propertiesImpl
         |  override def containedNodesByLocalName: Map[String, List[Node]] = $containedNodesByLocalName
         |}
         |""".stripMargin
    }

    val filename = outputDir.getPath + "/" + nodesPackage.replaceAll("\\.", "/") + "/NewNodes.scala"
    writeFile(filename, staticHeader, entries)
  }

  def writeFile(fileName: String, header: String, entries: List[String]): JFile = {
    val outputFile = File.newTemporaryFile()
    outputFile.appendLine(header)
    entries.map(outputFile.appendLine)

    val targetFile = File(fileName)
    targetFile.createIfNotExists(createParents = true)

    println(s"writing results to $targetFile")
    outputFile.moveTo(targetFile)(File.CopyOptions(overwrite = true)).toJava
  }
}

case class NodeType(
    name: String,
    id: Int,
    keys: List[String],
    outEdges: List[OutEdgeEntry],
    is: Option[List[String]],
    containedNodes: Option[List[ContainedNode]]) {
  lazy val className = Helpers.camelCaseCaps(name)
  lazy val classNameDb = s"${className}Db"
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

case class EdgeType(name: String, keys: List[String]) {
  lazy val className = Helpers.camelCaseCaps(name)
}

case class Property(name: String, comment: String, valueType: String, cardinality: String)

case class NodeBaseTrait(name: String, hasKeys: List[String], `extends`: Option[List[String]]) {
  lazy val extendz = `extends` //it's mapped from the key in json :(
  lazy val className = Helpers.camelCaseCaps(name)
}

object HigherValueType extends Enumeration {
  type HigherValueType = Value
  val None, Option, List = Value
}

object Helpers {

  def isNodeBaseTrait(baseTraits: List[NodeBaseTrait], nodeName: String): Boolean =
    nodeName == "NODE" || baseTraits.map(_.name).contains(nodeName)

  def camelCaseCaps(snakeCase: String): String = camelCase(snakeCase).capitalize

  def camelCase(snakeCase: String): String = {
    val corrected = // correcting for internal keys, like "_KEY" -> drop leading underscore
      if (snakeCase.startsWith("_")) snakeCase.drop(1)
      else snakeCase

    val elements: List[String] = corrected.split("_").map(_.toLowerCase).toList match {
      case head :: tail => head :: tail.map(_.capitalize)
      case Nil          => Nil
    }
    elements.mkString
  }

  def getHigherType(property: Property): HigherValueType.Value =
    Cardinality.fromName(property.cardinality) match {
      case Cardinality.One       => HigherValueType.None
      case Cardinality.ZeroOrOne => HigherValueType.Option
      case Cardinality.List      => HigherValueType.List
    }

  def getBaseType(property: Property): String = {
    property.valueType match {
      case "string"  => "String"
      case "int"     => "Integer"
      case "boolean" => "JBoolean"
      case _         => "UNKNOWN"
    }
  }

  def getCompleteType(property: Property): String =
    getHigherType(property) match {
      case HigherValueType.None   => getBaseType(property)
      case HigherValueType.Option => s"Option[${getBaseType(property)}]"
      case HigherValueType.List   => s"List[${getBaseType(property)}]"
    }

  def getCompleteType(containedNode: ContainedNode): String = {
    val tpe = if (containedNode.nodeType != "NODE") {
      containedNode.nodeTypeClassName + "Base"
    } else {
      containedNode.nodeTypeClassName
    }

    Cardinality.fromName(containedNode.cardinality) match {
      case Cardinality.ZeroOrOne => s"Option[$tpe]"
      case Cardinality.One       => tpe
      case Cardinality.List      => s"List[$tpe]"
    }
  }

  def propertyBasedFields(properties: List[Property]): String =
    properties.map { property =>
      val name = camelCase(property.name)
      val tpe = getCompleteType(property)

      getHigherType(property) match {
        case HigherValueType.None =>
          s"""private var _$name: $tpe = null
             |def $name(): $tpe = _$name""".stripMargin
        case HigherValueType.Option =>
          s"""private var _$name: $tpe = None
             |def $name(): $tpe = _$name""".stripMargin
        case HigherValueType.List =>
          s"""private var _$name: $tpe = Nil
             |def $name(): $tpe = _$name""".stripMargin
      }
    }.mkString("\n\n")

  def updateSpecificPropertyBody(properties: List[Property]): String = {
    val caseNotFound = "PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)"
    properties match {
      case Nil => caseNotFound
      case keys =>
        val casesForKeys: List[String] = keys.map { property =>
          getHigherType(property) match {
            case HigherValueType.None =>
              s""" if (key == "${property.name}") this._${camelCase(property.name)} = value.asInstanceOf[${getBaseType(property)}] """
            case HigherValueType.Option =>
              s""" if (key == "${property.name}") this._${camelCase(property.name)} = Option(value).asInstanceOf[${getCompleteType(property)}] """
            case HigherValueType.List =>
              val memberName = "_" + camelCase(property.name)
              s"""if (key == "${property.name}") {
                 |  if (cardinality == VertexProperty.Cardinality.list) {
                 |    if (this.$memberName == null) { this.$memberName = Nil }
                 |    this.$memberName = this.$memberName :+ value.asInstanceOf[${getBaseType(property)}]
                 |  } else {
                 |    this.$memberName = List(value.asInstanceOf[${getBaseType(property)}])
                 |  }
                 |}
                 |""".stripMargin
          }
        }
        (casesForKeys :+ caseNotFound).mkString("\n else ")
    }
  }

  def removeSpecificPropertyBody(properties: List[Property]): String = {
    val caseNotFound =
      """throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to the schema...")"""
    properties match {
      case Nil => caseNotFound
      case keys =>
        val casesForKeys: List[String] = keys.map { property =>
          s""" if (key == "${property.name}") this._${camelCase(property.name)} = null """
        }
        (casesForKeys :+ caseNotFound).mkString("\n else ")
    }
  }
}
