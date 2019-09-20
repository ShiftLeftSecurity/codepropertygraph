import better.files.File
import better.files.Dsl.SymbolicOperations
import java.io.{FileInputStream, File => JFile}
import java.nio.file.{Files, Paths}

import scala.collection.JavaConverters._
import play.api.libs.json._

import scala.Option
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
}

/** Parses schema file and generates a domain model for OverflowDb */
class DomainClassCreator(schemaFile: String) {
  import Helpers._
  val nodesPackage = "io.shiftleft.codepropertygraph.generated.nodes"
  val edgesPackage = "io.shiftleft.codepropertygraph.generated.edges"
  val schema = new Schema(schemaFile)

  def run(outputDir: JFile): List[JFile] =
    List(writeEdgesFile(outputDir), writeNodesFile(outputDir), writeNewNodesFile(outputDir))

  def writeEdgesFile(outputDir: JFile): JFile = {
    val propertyByName: Map[String, Property] =
      schema.edgeKeys.map(key => key.name -> key).toMap

    def entries: List[String] =
      schema.edgeTypes.map(edge => generateEdgeSource(edge, edge.keys.map(propertyByName)))

    def edgeHeader = {
      val staticHeader = s"""
      package $edgesPackage

      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Set => JSet}
      import java.util.{List => JList}
      import org.apache.tinkerpop.gremlin.structure.Property
      import org.apache.tinkerpop.gremlin.structure.{Vertex, VertexProperty}
      import io.shiftleft.overflowdb.{EdgeLayoutInformation, EdgeFactory, NodeFactory, OdbEdge, OdbNode, OdbGraph, NodeRef}
      import scala.collection.JavaConverters._
      import org.slf4j.LoggerFactory

      object PropertyErrorRegister {
        private var errorMap = Set[(Class[_], String)]()
        private val logger = LoggerFactory.getLogger(getClass)

        def logPropertyErrorIfFirst(clazz: Class[_], propertyName: String) {
          if (!errorMap.contains((clazz, propertyName))) {
            logger.warn("Property " + propertyName + " is deprecated for " + clazz.getName + ".")
            errorMap += ((clazz, propertyName))
          }
        }
      }
      """

      val factories = {
        val edgeFactories: List[String] =
          schema.edgeTypes.map(edgeType => edgeType.className + ".Factory")
        s"""
        object Factories {
          lazy val All: List[EdgeFactory[_]] = ${edgeFactories}
          lazy val AllAsJava: java.util.List[EdgeFactory[_]] = All.asJava
        }
        """
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

      val companionObject = s"""
      |object $edgeClassName {
      |  val Label = "${edgeType.name}"
      |  object Keys {
      |    val All: JSet[String] = Set(${keysQuoted.mkString(", ")}).asJava
      |    val KeyToValue: Map[String, $edgeClassName => Any] = Map(
      |      $keyToValueMap
      |    )
      |  }
      |
      |  val layoutInformation = new EdgeLayoutInformation(Label, Keys.All)
      |
      |  val Factory = new EdgeFactory[${edgeClassName}] {
      |    override val forLabel = $edgeClassName.Label
      |
      |    override def createEdge(graph: OdbGraph, outNode: NodeRef[OdbNode], inNode: NodeRef[OdbNode]) =
      |      new ${edgeClassName}(graph, outNode, inNode)
      |  }
      |}
      """.stripMargin

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

      val classImpl = s"""
      class ${edgeClassName}(_graph: OdbGraph, _outNode: NodeRef[OdbNode], _inNode: NodeRef[OdbNode])
          extends OdbEdge(_graph, $edgeClassName.Label, _outNode, _inNode, $edgeClassName.Keys.All) {

        ${propertyBasedFieldAccessors(keys)}
      }
      """

      companionObject  + classImpl
    }

    val filename = outputDir.getPath + "/" + edgesPackage.replaceAll("\\.", "/") + "/Edges.scala"
    writeFile(filename, edgeHeader, entries)
  }

  def writeNodesFile(outputDir: JFile): JFile = {
    val propertyByName: Map[String, Property] =
      schema.nodeKeys.map(prop => prop.name -> prop).toMap

    def entries: List[String] = {
      val nodeToInEdges = calculateNodeToInEdges(schema.nodeTypes)

      schema.nodeTypes.map(node =>
        generateNodeSource(node, node.keys.map(propertyByName), nodeToInEdges)
      )
    }

    def nodeHeader = {
      val staticHeader = s"""
      package $nodesPackage

      import gremlin.scala._
      import io.shiftleft.codepropertygraph.generated.EdgeKeys
      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
      import org.apache.tinkerpop.gremlin.structure.{Direction, Vertex, VertexProperty}
      import io.shiftleft.overflowdb.{EdgeFactory, NodeFactory, NodeLayoutInformation, OdbNode, OdbGraph, OdbNodeProperty, NodeRef}
      import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils
      import scala.collection.JavaConverters._
      import org.slf4j.LoggerFactory
      import io.shiftleft.codepropertygraph.generated.edges
      import io.shiftleft.codepropertygraph.generated.edges._

      object PropertyErrorRegister {
        private var errorMap = Set[(Class[_], String)]()
        private val logger = LoggerFactory.getLogger(getClass)

        def logPropertyErrorIfFirst(clazz: Class[_], propertyName: String) {
          if (!errorMap.contains((clazz, propertyName))) {
            logger.warn("Property " + propertyName + " is deprecated for " + clazz.getName + ".")
            errorMap += ((clazz, propertyName))
          }
        }
      }

      trait Node extends Product {
        def accept[T](visitor: NodeVisitor[T]): T = ???
      }

      /* a node that stored inside an OdbGraph (rather than e.g. DiffGraph) */
      trait StoredNode extends Vertex with Node {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        def underlying: Vertex = this

        // This is required for accessing the id from java code which only has a reference to StoredNode at hand.
        // Java does not seem to be capable of calling methods from java classes if a scala trait is in the inheritance
        // chain.
        def getId: JLong = underlying.id.asInstanceOf[JLong]

        /* all properties plus label and id */
        def toMap: Map[String, Any] = {
          val map = valueMap
          map.put("_label", label)
          map.put("_id", getId)
          map.asScala.toMap
        }

        /* all properties */
        def valueMap: JMap[String, AnyRef]
      }

      """

      val nodeVisitor = s""" 
        |/* Using pattern matching would be prettier than the visitor pattern, but because the matches would 
        | * contain type checks, scalac cannot compile them down to jvm `lookupswitch|tableswitch` instructions, 
        | * which would make them slower. 
        | * See https://github.com/ShiftLeftSecurity/codepropertygraph/pull/317 for more details. */
        |trait NodeVisitor[T] {
        |  ${generateNodeVisitorMethods}
        |  ${generateBaseTraitVisitorMethods}
        |}\n""".stripMargin

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
            trait ${nodeBaseTrait.className} extends StoredNode with ${nodeBaseTrait.className}Base $mixinTraits
        """
      }.mkString("\n")

      val keyBasedTraits =
        schema.nodeKeys.map { property =>
          val camelCaseName = camelCase(property.name)
          val camelCaseCapitalized = camelCaseName.capitalize
          val tpe = getCompleteType(property)
          s"trait Has$camelCaseCapitalized { def $camelCaseName: $tpe }"
        }.mkString("\n")

      val factories = {
        val nodeFactories: List[String] =
          schema.nodeTypes.map(nodeType => nodeType.className + ".Factory")
        s"""
        object Factories {
          lazy val All: List[NodeFactory[_]] = ${nodeFactories}
          lazy val AllAsJava: java.util.List[NodeFactory[_]] = All.asJava
        }
        """
      }

      staticHeader + nodeVisitor + nodeBaseTraits + keyBasedTraits + factories
    }

    def generateNodeVisitorMethods =
      schema.nodeTypes.map { nodeType =>
        s"def visit(node: ${nodeType.className}): T = ???"
      }.mkString("\n")

    def generateBaseTraitVisitorMethods() =
      schema.nodeBaseTraits.map { nodeBaseTrait: NodeBaseTrait =>
        s"def visit(node: ${nodeBaseTrait.className}): T = ???"
      }.mkString("\n")

    def edgeTypeByName: Map[String, EdgeType] =
      schema.edgeTypes.groupBy(_.name).mapValues(_.head)

    def generateNodeSource(nodeType: NodeType,
                           keys: List[Property],
                           nodeToInEdges: mutable.MultiMap[String, String]) = {
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

      val outEdges: List[String] = nodeType.outEdges.map(_.edgeName)

      val inEdges: List[String] = {
        val option = nodeToInEdges.get(nodeType.name)
        option.map(_.toList).getOrElse(Nil)
      }

      val outEdgeLayouts = outEdges.map(edge => s"edges.${camelCaseCaps(edge)}.layoutInformation").mkString(", ")
      val inEdgeLayouts = inEdges.map(edge => s"edges.${camelCaseCaps(edge)}.layoutInformation").mkString(", ")

      val companionObject = s"""
      object ${nodeType.className} {

        def apply(graph: OdbGraph, id: Long) = new ${nodeType.className}(graph, id)

        val layoutInformation = new NodeLayoutInformation(
          Keys.All,
          List($outEdgeLayouts).asJava,
          List($inEdgeLayouts).asJava)

        val Label = "${nodeType.name}"
        object Keys {
          $keyConstants
          val All: JSet[String] = Set(${keys.map { key =>
            camelCaseCaps(key.name)
          }
          .mkString(", ")}).asJava
            val KeyToValue: Map[String, ${nodeType.classNameDb} => Any] = Map(
              $keyToValueMap
            )
          }
        object Edges {
          val In: Array[String] = Array(${inEdges.map('"' + _ + '"').mkString(",")})
          val Out: Array[String] = Array(${outEdges.map('"' + _ + '"').mkString(",")})
        }

        val Factory = new NodeFactory[${nodeType.classNameDb}] {
          override val forLabel = ${nodeType.className}.Label

          override def createNode(ref: NodeRef[${nodeType.classNameDb}]) =
            new ${nodeType.classNameDb}(ref.asInstanceOf[NodeRef[OdbNode]])

          override def createNodeRef(graph: OdbGraph, id: Long) = ${nodeType.className}(graph, id)
        }
      }
      """

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
                s"""if (${memberName} != null) { properties.put("${key.name}", ${memberName}) }"""
              case Cardinality.ZeroOrOne =>
                s"""${memberName}.map { value => properties.put("${key.name}", value) }"""
              case Cardinality.List => // need java list, e.g. for NodeSerializer
                s"""if (${memberName}.nonEmpty) { properties.put("${key.name}", ${memberName}.asJava) }"""
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

              s"""
              /** link to 'contained' node of type $containedNodeType */
              lazy val ${containedNode.localName}: $completeType =
                edges(Direction.OUT, "CONTAINS_NODE").asScala.toList
                  .filter(_.valueOption(EdgeKeys.LOCAL_NAME).map(_  == "${containedNode.localName}").getOrElse(false))
                  .sortBy(_.valueOption(EdgeKeys.INDEX))
                  .map(_.inVertex.asInstanceOf[$containedNodeType])
                  $traversalEnding
              """
            }.mkString("\n")
          }
          .getOrElse("")

      val walkInEdgeCases = {
        inEdges.map { edge =>
          s"case ${edgesPackage}.${camelCaseCaps(edge)}.Label => walkIterators.add(${camelCase(edge)}In.iterator)"
        }
      }.mkString("\n")

      val walkOutEdgeCases = {
        outEdges.map { edge =>
          s"case ${edgesPackage}.${camelCaseCaps(edge)}.Label => walkIterators.add(${camelCase(edge)}Out.iterator)"
        }
      }.mkString("\n")

      val productElementAccessors =
        keys.zipWithIndex
          .map { case (key, idx) =>
            s"case ${idx + 1} => ${camelCase(key.name)}()"
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

      val nodeBaseImpl = s"""
      |trait ${nodeType.className}Base extends Node $mixinTraitsForBase $propertyBasedTraits {
      |  def asStored : StoredNode = this.asInstanceOf[StoredNode]
      |  $abstractContainedNodeAccessors
      |}""".stripMargin

      val nodeRefImpl = {
        val propertyDelegators = keys.map(_.name).map(camelCase).map { name =>
          s"""override def $name = get().$name"""
        }.mkString("\n")
        val containedNodesDelegators = nodeType.containedNodes
        s"""
          |class ${nodeType.className}(graph: OdbGraph, id: Long) extends NodeRef[${nodeType.classNameDb}](graph, id) with ${nodeType.className}Base with StoredNode $mixinTraits {
          |$propertyDelegators
          |$delegatingContainedNodeAccessors
          |  override def accept[T](visitor: NodeVisitor[T]): T = {
          |    visitor.visit(this)
          |  }
          |  override def valueMap: JMap[String, AnyRef] = get.valueMap
          |  override def productElement(n: Int): Any = get.productElement(n)
          |  override def productPrefix = "${nodeType.className}"
          |  override def productArity = ${keys.size} + 1 // add one for id, leaving out `_graph`
          |  override def canEqual(that: Any): Boolean = get.canEqual(that)
          |  override def label(): String = {
          |    ${nodeType.className}.Label
          |  }
          |}""".stripMargin
      }

      val classImpl = s"""
      class ${nodeType.classNameDb}(ref: NodeRef[OdbNode]) extends OdbNode(ref) with StoredNode 
        $mixinTraits with ${nodeType.className}Base {

        override def layoutInformation: NodeLayoutInformation = ${nodeType.className}.layoutInformation

        /* all properties */
        override def valueMap: JMap[String, AnyRef] = $valueMapImpl

        ${propertyBasedFields(keys)}
      
        override def label(): String = {
          ${nodeType.className}.Label
        }

        override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[${nodeType.classNameDb}]
        override def productElement(n: Int): Any =
            n match {
              case 0 => getId
              $productElementAccessors
            }

        override def productPrefix = "${nodeType.className}"
        override def productArity = ${keys.size} + 1 // add one for id, leaving out `_graph`
        
        /* performance optimisation to save instantiating an iterator for each property lookup */
        override protected def specificProperty[A](key: String): VertexProperty[A] = {
          ${nodeType.className}.Keys.KeyToValue.get(key) match {
            case None => VertexProperty.empty[A]
            case Some(fieldAccess) => 
              fieldAccess(this) match {
                case null | None => VertexProperty.empty[A]
                case values: List[_] => throw Vertex.Exceptions.multiplePropertiesExistForProvidedKey(key)
                case Some(value) => new OdbNodeProperty(-1, this, key, value.asInstanceOf[A])
                case value => new OdbNodeProperty(-1, this, key, value.asInstanceOf[A])
              }
          }
        }

        override protected def specificProperties[A](key: String): JIterator[VertexProperty[A]] = {
          ${nodeType.className}.Keys.KeyToValue.get(key) match {
            case None => JCollections.emptyIterator[VertexProperty[A]]
            case Some(fieldAccess) => 
              fieldAccess(this) match {
                case null => JCollections.emptyIterator[VertexProperty[A]]
                case values: List[_] => 
                  values.map { value => 
                    new OdbNodeProperty(-1, this, key, value).asInstanceOf[VertexProperty[A]]
                  }.toIterator.asJava
                case value => IteratorUtils.of(new OdbNodeProperty(-1, this, key, value.asInstanceOf[A]))
              }
          }
        }

        override protected def updateSpecificProperty[A](cardinality: VertexProperty.Cardinality, key: String, value: A): VertexProperty[A] = {
          ${updateSpecificPropertyBody(keys)}
          new OdbNodeProperty(-1, this, key, value)
        }

        override protected def removeSpecificProperty(key: String): Unit =
          ${removeSpecificPropertyBody(keys)}

        $containedNodesAsMembers
      }
      """

      companionObject + nodeBaseImpl + nodeRefImpl + classImpl
    }

    val filename = outputDir.getPath + "/" + nodesPackage.replaceAll("\\.", "/") + "/Nodes.scala"
    writeFile(filename, nodeHeader, entries)
  }

  /** generates classes to easily add new nodes to the graph
    * this ability could have been added to the existing nodes, but it turned out as a different specialisation,
    * since e.g. `id` is not set before adding it to the graph */
  def writeNewNodesFile(outputDir: JFile): JFile = {
    val staticHeader = s"""
    package $nodesPackage

    import java.lang.{Boolean => JBoolean, Long => JLong}
    import java.util.{Map => JMap, Set => JSet}

    /** base type for all nodes that can be added to a graph, e.g. the diffgraph */
    trait NewNode extends Node {
      def label: String
      def properties: Map[String, Any]
      def containedNodesByLocalName: Map[String, List[Node]]
      def allContainedNodes: List[Node] = containedNodesByLocalName.values.flatten.toList
    }
    """

    val propertyByName: Map[String, Property] =
      schema.nodeKeys.map(prop => prop.name -> prop).toMap

    def entries: List[String] =
      schema.nodeTypes.map(node => generateNodeSource(node, node.keys.map(propertyByName)))

    def generateNodeSource(nodeType: NodeType, keys: List[Property]) = {
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
                case _ => ""
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
          val containsOptionals = keys.find { property =>
            Cardinality.fromName(property.cardinality) == Cardinality.ZeroOrOne
          }.isDefined
          val forKeys = keys
            .map { key: Property =>
              s"""("${key.name}" -> ${camelCase(key.name)} )"""
            }
            .mkString(",\n")

          val baseCase = s"""
            Map($forKeys).asInstanceOf[Map[String, Any]].filterNot { case (k,v) =>
                v == null || v == None
              }
            """
          if (!containsOptionals) baseCase
          else baseCase + s""".map {
              case (k, v: Option[_]) => (k,v.get)
              case other => other
            }
          """
      }

      val containedNodesByLocalName: String = {
        val mappedNodes = nodeType.containedNodes.getOrElse(Nil)
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


      s"""
      case class New${nodeType.className}($fields) extends NewNode with ${nodeType.className}Base {
        override val label = "${nodeType.name}"
        override val properties: Map[String, Any] = $propertiesImpl
        override def containedNodesByLocalName: Map[String, List[Node]] = $containedNodesByLocalName

        override def accept[T](visitor: NodeVisitor[T]): T = ???
      }
      """
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
    outputFile.moveTo(targetFile, overwrite = true).toJava
  }

  def calculateNodeToInEdges(nodeTypes: List[NodeType]): mutable.MultiMap[String, String] = {
    val nodeBaseTraitNames: List[String] =
      schema.nodeBaseTraits.map(_.name) :+ "NODE"

    val nodeToInEdges = new mutable.HashMap[String, mutable.Set[String]] with mutable.MultiMap[String, String]
    val nodeTypeNamesSet = nodeTypes.map(_.name).toSet ++ nodeBaseTraitNames

    for {
      nodeType <- nodeTypes
      outEdge  <- nodeType.outEdges
      inNode   <- outEdge.inNodes
    } {
      assert(nodeTypeNamesSet.contains(inNode), s"Node with name $inNode is not defined.")
      nodeToInEdges.addBinding(inNode, outEdge.edgeName)
    }

    // all nodes can have incoming `CONTAINS_NODE` edges
    nodeTypes.foreach { nodeType =>
      nodeToInEdges.addBinding(nodeType.name, "CONTAINS_NODE")
    }

    nodeToInEdges
  }
}

case class NodeType(name: String,
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

  def getCompleteType(containedNode: ContainedNode) = {
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
          s"""|private var _$name: $tpe = null
              |def $name(): $tpe = _$name""".stripMargin
        case HigherValueType.Option =>
          s"""|private var _$name: $tpe = None
              |def $name(): $tpe = _$name""".stripMargin
        case HigherValueType.List =>
          s"""|private var _$name: $tpe = Nil
              |def $name(): $tpe = _$name""".stripMargin
      }
    }.mkString("\n\n")

  def updateSpecificPropertyBody(properties: List[Property]): String = {
    val caseNotFound =
      s"""PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)"""
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
                    if (cardinality == VertexProperty.Cardinality.list) {
                      if (this.$memberName == null) { this.$memberName = Nil }
                      this.$memberName = this.$memberName :+ value.asInstanceOf[${getBaseType(property)}]
                    } else {
                      this.$memberName = List(value.asInstanceOf[${getBaseType(property)}])
                    }
                  }
              """
          }
        }
        (casesForKeys :+ caseNotFound).mkString("\n else ")
    }
  }

  def removeSpecificPropertyBody(properties: List[Property]): String = {
    val caseNotFound =
      s"""throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to the schema...")"""
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
