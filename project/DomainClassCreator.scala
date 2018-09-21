import better.files.File
import better.files.Dsl.SymbolicOperations
import java.io.{FileInputStream, File => JFile}
import java.nio.file.{Files, Paths}

import scala.collection.JavaConverters._
import play.api.libs.json._
import Utils._

import scala.Option
import scala.collection.mutable

object Resources {
  lazy val cpgJson =
    Json.parse(new FileInputStream("codepropertygraph/src/main/resources/cpg.json"))
}

/** Parses cpg domain model and generates specialized TinkerVertices and TinkerEdges
  * sbt is setup to invoke this class automatically. */
object DomainClassCreator {
  implicit val outEdgeEntryRead = Json.reads[OutEdgeEntry]
  implicit val containedNodeRead = Json.reads[ContainedNode]
  implicit val nodeTypesRead = Json.reads[NodeType]
  implicit val nodeBaseTraitRead = Json.reads[NodeBaseTrait]
  implicit val propertyRead = Json.reads[Property]
  implicit val edgeTypeRead = Json.reads[EdgeType]
  val edgesPackage = "io.shiftleft.codepropertygraph.generated.edges"

  def run(outputDir: JFile): List[JFile] = {
    println(s"generating domain classes for nodes/edges based on cpg.json")
    List(writeEdgesFile(outputDir), writeNodesFile(outputDir), writeNewNodesFile(outputDir))
  }

  def writeEdgesFile(outputDir: JFile): JFile = {
    val propertyByName: Map[String, Property] = 
      (Resources.cpgJson \ "edgeKeys")
        .as[List[Property]]
        .map{ prop => (prop.name -> prop)}
        .toMap

    def entries: List[String] = 
      (Resources.cpgJson \ "edgeTypes")
        .as[List[EdgeType]]
        .map(edge => generateEdgeSource(edge, edge.keys.map(propertyByName)))

    def edgeHeader = {
      val staticHeader = s"""
      package $edgesPackage

      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Map => JMap, Set => JSet}
      import org.apache.tinkerpop.gremlin.structure.Vertex
      import org.apache.tinkerpop.gremlin.structure.Property
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.SpecializedElementFactory
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.SpecializedTinkerEdge
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerProperty
      import scala.collection.JavaConverters._
      """

      val factories = {
        val edgeFactories: List[String] =
          (Resources.cpgJson \ "edgeTypes")
            .as[List[EdgeType]]
            .map(edgeType => camelCase(edgeType.name).capitalize + ".Factory")
        s"""
        object Factories {
          lazy val All: List[SpecializedElementFactory.ForEdge[_, _]] = ${edgeFactories}
          lazy val AllAsJava: java.util.List[SpecializedElementFactory.ForEdge[_, _]] = All.asJava
        }
        """
      }

      staticHeader + factories
    }
 
    def generateEdgeSource(edgeType: EdgeType, keys: List[Property]) = {
      val edgeNameCamelCase = camelCase(edgeType.name).capitalize
      val keysQuoted = keys.map('"' + _.name + '"')
      val keyToValueMap = keys.map{ key =>
        s""" "${key.name}" -> { instance: $edgeNameCamelCase => instance.${camelCase(key.name)}}"""
      }.mkString(",\n")

      val additionalConstructorParams = keys.map{ key =>
        s"""${camelCase(key.name)} = properties.get("${key.name}").asInstanceOf[${getBaseType(key)}]"""
      } match {
        case Nil => ""
        case params => ",\n" + params.mkString(",\n")
      }

      val companionObject = s"""
      object $edgeNameCamelCase {
        val Label = "${edgeType.name}"
        object Keys {
          val All: JSet[String] = Set(${keysQuoted.mkString(", ")}).asJava
          val KeyToValue: Map[String, $edgeNameCamelCase => Any] = Map(
            $keyToValueMap
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[$edgeNameCamelCase, JLong] {
          override val forLabel = $edgeNameCamelCase.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex, properties: JMap[String, AnyRef]) = 
            new $edgeNameCamelCase(id, outVertex, inVertex $additionalConstructorParams)
        }
      }
      """

      val additionalFields = keys match {
        case Nil => ""
        case keys => ", " + keys.map{property =>
          s"var ${camelCase(property.name)}: ${getBaseType(property)}"
        }.mkString(", ")
      }

      val updateSpecificPropertyBody = {
        val caseNotFound = s"""throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")"""
        keys match {
          case Nil => caseNotFound
          case keys =>
            val casesForKeys: List[String] = keys.map { property =>
              s""" if (key == "${property.name}") this.${camelCase(property.name)} = value.asInstanceOf[${getBaseType(property)}] """
            }
            (casesForKeys :+ caseNotFound).mkString("\n else ")
        }
      }

      val classImpl = s"""
      class $edgeNameCamelCase(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex $additionalFields)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, $edgeNameCamelCase.Label, _inVertex, $edgeNameCamelCase.Keys.All) {

        override protected def specificProperty[A](key: String): Property[A] =
          $edgeNameCamelCase.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          $updateSpecificPropertyBody
          property(key)
        }
      }
      """

      companionObject + classImpl
    }

    writeFile(s"${outputDir.getPath}/Edges.scala", edgeHeader, entries)
  }

  def writeNodesFile(outputDir: JFile): JFile = {
    val propertyByName: Map[String, Property] =
      (Resources.cpgJson \ "nodeKeys")
        .as[List[Property]]
        .map{ prop => (prop.name -> prop)}
        .toMap

    def entries: List[String] = {
      val nodeTypes = (Resources.cpgJson \ "nodeTypes")
        .as[List[NodeType]]

      val nodeToInEdges = calculateNodeToInEdges(nodeTypes)

      nodeTypes.map(node => generateNodeSource(node, node.keys.map(propertyByName), nodeToInEdges))
    }

    def nodeHeader = {
      val staticHeader = s"""
      package io.shiftleft.codepropertygraph.generated.nodes

      import io.shiftleft.codepropertygraph.generated
      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Iterator => JIterator, LinkedList => JLinkedList, List => JList, Map => JMap, Set => JSet}
      import gremlin.scala._
      import org.apache.tinkerpop.gremlin.structure.Direction
      import org.apache.tinkerpop.gremlin.structure.Edge
      import org.apache.tinkerpop.gremlin.structure.VertexProperty
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.SpecializedElementFactory
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.SpecializedTinkerVertex
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerVertexProperty
      import org.apache.tinkerpop.gremlin.util.iterator.MultiIterator
      import scala.collection.JavaConverters._
      import shapeless.HNil

      trait Node

      trait StoredNode extends SpecializedTinkerVertex[JLong] with Node {
        // This is required for accessing the id from java code which only has a reference to StoredNode at hand.
        // Java does not seem to be capable of calling methods from java classes if a scala trait is in the inheritance
        // chain.
        def getId: JLong

        /* returns `Some(propertyValue)` if present, or `None` otherwise */
        def valueOption[A](key: Key[A]): Option[A] =
          this.property[A](key.name).toOption

        /* returns Map of all properties plus label and id */
        def toMap: Map[String, Any]
      }

      """

      val nodeBaseTraits =
        (Resources.cpgJson \ "nodeBaseTraits").as[List[NodeBaseTrait]]
          .map { case NodeBaseTrait(name, hasKeys, extendz) =>
            val nameCC = camelCase(name).capitalize
            val mixins = hasKeys.map { key =>
              s"with Has${camelCase(key).capitalize}"
            }.mkString(" ")
            val (baseNodeAdditionalMixins, storedNodeExtends) = extendz match {
              case Some(parent) =>
                val parentCC = camelCase(parent).capitalize
                (s"with ${parentCC}Base", parentCC)
              case None =>
                ("", "StoredNode")
            }
            s"""trait ${nameCC}Base extends Node $mixins $baseNodeAdditionalMixins
                trait ${nameCC} extends $storedNodeExtends with ${nameCC}Base
"""
          }.mkString("\n")

      val keyBasedTraits =
        (Resources.cpgJson \ "nodeKeys")
          .as[List[Property]]
          .map{ property =>
            val camelCaseName = camelCase(property.name)
            val camelCaseCapitalized = camelCaseName.capitalize
            val tpe = getCompleteType(property)
            s"trait Has$camelCaseCapitalized { def $camelCaseName: $tpe }"
          }.mkString("\n")

      val factories = {
        val vertexFactories: List[String] =
          (Resources.cpgJson \ "nodeTypes")
            .as[List[NodeType]]
            .map(nodeType => camelCase(nodeType.name).capitalize + ".Factory")
        s"""
        object Factories {
          lazy val All: List[SpecializedElementFactory.ForVertex[_, _]] = ${vertexFactories}
          lazy val AllAsJava: java.util.List[SpecializedElementFactory.ForVertex[_, _]] = All.asJava
        }
        """
      }

      staticHeader + nodeBaseTraits + keyBasedTraits + factories
    }

    def generateNodeSource(nodeType: NodeType, keys: List[Property], nodeToInEdges: mutable.MultiMap[String, String]) = {
      val nodeNameCamelCase = camelCase(nodeType.name).capitalize

      val keyConstants = keys.map(key => s"""val ${camelCase(key.name).capitalize} = "${key.name}" """).mkString("\n")
      val keyToValueMap = keys.map { key: Property =>
        getHigherType(key) match {
          case HigherValueType.NONE =>
            s""" "${key.name}" -> { instance: $nodeNameCamelCase => instance.${camelCase(key.name)}}"""
          case HigherValueType.OPTION =>
            s""" "${key.name}" -> { instance: $nodeNameCamelCase => instance.${camelCase(key.name)}.orNull}"""
        }
      }.mkString(",\n")

      val additionalConstructorParams = keys.map { key =>
        getHigherType(key) match {
          case HigherValueType.NONE =>
            s"""${camelCase(key.name)} = properties.get("${key.name}").asInstanceOf[${getBaseType(key)}]"""
          case HigherValueType.OPTION =>
            s"""${camelCase(key.name)} = Option(properties.get("${key.name}").asInstanceOf[${getBaseType(key)}])"""
        }
      } match {
        case Nil => ""
        case params => ",\n" + params.mkString(",\n")
      }

      def outEdges(nodeType: NodeType): List[String] = {
        nodeType.outEdges.map(_.edgeName)
      }

      def inEdges(nodeType: NodeType): List[String] = {
        val option = nodeToInEdges.get(nodeType.name)
        option.map(_.toList).getOrElse(Nil)
      }

      val companionObject = s"""
      object $nodeNameCamelCase {
        val Label = "${nodeType.name}"
        object Keys {
          $keyConstants
          val All: JSet[String] = Set(${keys.map{key => camelCase(key.name).capitalize}.mkString(", ")}).asJava
          val KeyToValue: Map[String, $nodeNameCamelCase => Any] = Map(
            $keyToValueMap
          )
        }
        object Edges {
          val In: Set[String] = Set(${inEdges(nodeType).map('"' + _ + '"').mkString(",")})
          val Out: Set[String] = Set(${outEdges(nodeType).map('"' + _+ '"').mkString(",")})
        }

        val Factory = new SpecializedElementFactory.ForVertex[$nodeNameCamelCase, JLong] {
          override val forLabel = $nodeNameCamelCase.Label

          override def createVertex(id: JLong, graph: TinkerGraph, properties: JMap[String, AnyRef]) =
            new $nodeNameCamelCase(id, graph $additionalConstructorParams)
        }
      }
      """

      val mixinTraits: String =
        nodeType.is.getOrElse(List())
          .map { traitName =>
            s"with ${camelCase(traitName).capitalize}"
          }.mkString(" ")

      val additionalFields = keys match {
        case Nil => ""
        case keys => ", " + keys.map{key =>
          s"var ${camelCase(key.name)}: ${getCompleteType(key)}"
        }.mkString(", ")
      }

      val propertyBasedTraits = keys.map(key => s"with Has${camelCase(key.name).capitalize}").mkString(" ")

      val updateSpecificPropertyBody = {
        val caseNotFound = s"""throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")"""
        keys match {
          case Nil => caseNotFound
          case keys =>
            val casesForKeys: List[String] = keys.map { property =>
              getHigherType(property) match {
                case HigherValueType.NONE =>
                  s""" if (key == "${property.name}") this.${camelCase(property.name)} = value.asInstanceOf[${getBaseType(property)}] """
                case HigherValueType.OPTION =>
                  s""" if (key == "${property.name}") this.${camelCase(property.name)} = Option(value).asInstanceOf[${getCompleteType(property)}] """
              }
            }
            (casesForKeys :+ caseNotFound).mkString("\n else ")
        }
      }

      val edgeSets = {
        val fullNames: List[String] =
          inEdges(nodeType).map { edge => s"${camelCase(edge)}In"} ++
          outEdges(nodeType).map { edge => s"${camelCase(edge)}Out"} ++
          nodeType.containedNodes.map(_ => List("containsOut")).getOrElse(Nil)
          // if there are any `contained` nodes, we also need to store the `contains` edges

        fullNames.distinct.map { name =>
          s"""
          private var _$name: JList[Edge] = null
          private def $name: JList[Edge] = {
            if (_$name == null) _$name = new JLinkedList
            _$name
          }"""
        }
      }.mkString("\n")

      val addInEdgeCases = {
        inEdges(nodeType).map { edge =>
          s"case edge: ${edgesPackage}.${camelCase(edge).capitalize} => ${camelCase(edge)}In.add(edge)"
        }
      }.mkString("\n")

      val addOutEdgeCases = {
        outEdges(nodeType).map { edge =>
          s"case edge: ${edgesPackage}.${camelCase(edge).capitalize} => ${camelCase(edge)}Out.add(edge)"
        }
      }.mkString("\n")

      val removeInEdgeCases = {
        inEdges(nodeType).map { edge =>
          s"case edge: ${edgesPackage}.${camelCase(edge).capitalize} => ${camelCase(edge)}In.remove(edge)"
        }
      }.mkString("\n")

      val removeOutEdgeCases = {
        outEdges(nodeType).map { edge =>
          s"case edge: ${edgesPackage}.${camelCase(edge).capitalize} => ${camelCase(edge)}Out.remove(edge)"
        }
      }.mkString("\n")

      val containedNodesAsMembers =
        nodeType.containedNodes.map { _.map { containedNode =>
          val containedNodeType = camelCase(containedNode.nodeType).capitalize
          val cardinality = Cardinality.fromName(containedNode.cardinality)
          val completeType = cardinality match {
            case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
            case Cardinality.One => containedNodeType
            case Cardinality.List => s"List[$containedNodeType]"
          }
          val traversalEnding = cardinality match {
            case Cardinality.ZeroOrOne => s".headOption"
            case Cardinality.One => s".head"
            case Cardinality.List => s".toList.sortBy(_.valueOption[Integer](generated.EdgeKeys.INDEX))"
          }

          // TODO: contains is actually optional -> `localName.map(_ == annotationParameters.getOrElse(false)))`
          s"""
           /** link to 'contained' node of type $containedNodeType */
           lazy val ${containedNode.localName}: $completeType =
              containsOut.asScala.toIterable
                .filter(_.asInstanceOf[generated.edges.Contains].localName == "${containedNode.localName}")
                .map(_.inVertex.asInstanceOf[$containedNodeType])
                $traversalEnding
          """
          }.mkString("\n")
        }.getOrElse("")

      val walkInEdgeCases = {
        inEdges(nodeType).map { edge =>
          s"case ${edgesPackage}.${camelCase(edge).capitalize}.Label => iterators.addIterator(${camelCase(edge)}In.iterator)"
        }
      }.mkString("\n")

      val walkOutEdgeCases = {
        outEdges(nodeType).map { edge =>
          s"case ${edgesPackage}.${camelCase(edge).capitalize}.Label => iterators.addIterator(${camelCase(edge)}Out.iterator)"
        }
      }.mkString("\n")

      val productElementAccessors =
        keys.zipWithIndex.map { case (key, idx) =>
          s"case ${idx + 1} => ${camelCase(key.name)}"
        }.mkString("\n")

      val toMap = {
        val forKeys = keys.map { key: Property =>
          s"""("${key.name}" -> ${camelCase(key.name)} )"""
        }.mkString(",\n")

        s"""
        Map("_label" -> "${nodeType.name}",
          "_id" -> _id,
          $forKeys
        ).filterNot { case (k,v) =>
            v == null || v == None
          }
         .map {
            case (k, v: Option[_]) => (k,v.get)
            case other => other
          }
        """
      }

      val abstractFieldAccessors = keys match {
        case Nil => ""
        case keys => "\n " + keys.map{key =>
          s"def ${camelCase(key.name)}: ${getCompleteType(key)}"
        }.mkString("\n ")
      }

      val abstractContainedNodeAccessors = nodeType.containedNodes.map { _.map { containedNode =>
          val containedNodeType = if (containedNode.nodeType != "NODE") {
            camelCase(containedNode.nodeType).capitalize + "Base"
          } else {
            camelCase(containedNode.nodeType).capitalize
          }
          val completeType = Cardinality.fromName(containedNode.cardinality) match {
            case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
            case Cardinality.One => containedNodeType
            case Cardinality.List => s"List[$containedNodeType]"
          }
          s"""def ${containedNode.localName}: $completeType"""
          }.mkString("\n")
        }.getOrElse("")

      val classImpl = s"""
      trait ${nodeNameCamelCase}Base extends Node {
        def asStored : StoredNode = this.asInstanceOf[StoredNode]

        $abstractFieldAccessors
        $abstractContainedNodeAccessors
      }

      class $nodeNameCamelCase(private val _id: JLong, private val _graph: TinkerGraph $additionalFields)
          extends SpecializedTinkerVertex[JLong](_id, $nodeNameCamelCase.Label, _graph, $nodeNameCamelCase.Keys.All) with StoredNode $mixinTraits $propertyBasedTraits with Product with ${nodeNameCamelCase}Base {

        override val productPrefix = "$nodeNameCamelCase"
        override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[$nodeNameCamelCase]
        override val productArity = ${keys.size} + 1 // add one for id, leaving out `_graph`
        override def productElement(n: Int): Any =
            n match {
              case 0 => _id
              $productElementAccessors
            }
        
        override def toMap: Map[String, Any] = $toMap

        $edgeSets

        def getId: JLong = {
          _id
        }

        override protected def specificProperty[A](key: String): VertexProperty[A] =
          $nodeNameCamelCase.Keys.KeyToValue.get(key) match {
            case None => VertexProperty.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) VertexProperty.empty[A]
              else new TinkerVertexProperty(-1, this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): VertexProperty[A] = {
          $updateSpecificPropertyBody
          property(key)
        }

        override protected def addSpecializedInEdge(edge: Edge): Unit =
          edge match {
            $addInEdgeCases
            case otherwise => throw new IllegalArgumentException("incoming edge of type " + edge.getClass + " not (yet) supported by $nodeNameCamelCase. You may want to add it to cpg.json")
          }

        override protected def addSpecializedOutEdge(edge: Edge): Unit =
          edge match {
            $addOutEdgeCases
            case otherwise => throw new IllegalArgumentException("outgoing edge of type " + edge.getClass + " not supported by $nodeNameCamelCase. You may want to add it to cpg.json")
          }

        override protected def specificEdges(direction: Direction, labels: String*): JIterator[Edge] = {
          val walkLabels: Set[String] = 
            if (labels.length > 0) labels.toSet
            else {
              // if no labels are specified, walk all
              direction match {
                case Direction.IN => $nodeNameCamelCase.Edges.In
                case Direction.OUT => $nodeNameCamelCase.Edges.Out
                case Direction.BOTH => $nodeNameCamelCase.Edges.In ++ $nodeNameCamelCase.Edges.Out
              }
            }

          val iterators = new MultiIterator[Edge]
          if (direction == Direction.IN || direction == Direction.BOTH) {
            walkLabels.collect {
              $walkInEdgeCases
              case _ => // ignore other labels
            }
          }
          if (direction == Direction.OUT || direction == Direction.BOTH) {
            walkLabels.collect {
              $walkOutEdgeCases
              case _ => // ignore other labels
            }
          }
          iterators
        }

        override protected def removeSpecificInEdge(edge: Edge): Unit = {
          edge match {
            $removeInEdgeCases
            case otherwise => throw new IllegalArgumentException("incoming edge of type " + edge.getClass + " not supported by $nodeNameCamelCase. You may want to add it to cpg.json")
          }
        }

        override protected def removeSpecificOutEdge(edge: Edge): Unit = {
          edge match {
            $removeOutEdgeCases
            case otherwise => throw new IllegalArgumentException("outgoing edge of type " + edge.getClass + " not supported by $nodeNameCamelCase. You may want to add it to cpg.json")
          }
        }

        $containedNodesAsMembers
      }
      """

      companionObject + classImpl
    }

    writeFile(s"${outputDir.getPath}/Nodes.scala", nodeHeader, entries)
  }

  /** generates classes to easily add new nodes to the graph
    * this ability could have been added to the existing nodes, but it turned out as a different specialisation,
    * since e.g. `id` is not set before adding it to the graph */
  def writeNewNodesFile(outputDir: JFile): JFile = {
    val staticHeader = s"""
    package io.shiftleft.codepropertygraph.generated.nodes

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
      (Resources.cpgJson \ "nodeKeys")
        .as[List[Property]]
        .map{ prop => (prop.name -> prop)}
        .toMap

    def entries: List[String] = {
      val nodeTypes = (Resources.cpgJson \ "nodeTypes")
        .as[List[NodeType]]

      nodeTypes.map(node => generateNodeSource(node, node.keys.map(propertyByName)))
    }

    def generateNodeSource(nodeType: NodeType, keys: List[Property]) = {
      val nodeNameCamelCase = camelCase(nodeType.name).capitalize

      val fields: String = {
        val forKeys = keys.map { key =>
          s"val ${camelCase(key.name)}: ${getCompleteType(key)}"
        }

        val forContainedNodes: List[String] = nodeType.containedNodes.map { _.map { containedNode =>
          // TODO: remove duplication of handling of containedNodes
          val containedNodeType = if (containedNode.nodeType != "NODE") {
            camelCase(containedNode.nodeType).capitalize + "Base"
          } else {
            camelCase(containedNode.nodeType).capitalize
          }
          val completeType = Cardinality.fromName(containedNode.cardinality) match {
            case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
            case Cardinality.One => containedNodeType
            case Cardinality.List => s"List[$containedNodeType]"
          }
          s"${containedNode.localName}: $completeType"
        }}.getOrElse(Nil)

        (forKeys ++ forContainedNodes).mkString(", ")
      }

      val propertiesImpl = keys match {
        case Nil => "Map.empty"
        case keys => 
          val containsOptionals = keys.find { property =>
            Cardinality.fromName(property.cardinality) == Cardinality.ZeroOrOne
          }.isDefined
          val forKeys = keys.map { key: Property =>
            s"""("${key.name}" -> ${camelCase(key.name)} )"""
          }.mkString(",\n")

          val baseCase = s"""
            Map($forKeys).filterNot { case (k,v) =>
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

      val containedNodesByLocalName: String = nodeType.containedNodes.map { _.map { containedNode =>
        val localName = containedNode.localName
        val value = Cardinality.fromName(containedNode.cardinality) match {
          case Cardinality.One => s"List($localName)"
          case Cardinality.ZeroOrOne => s"List($localName).flatten"
          case Cardinality.List => localName
        }
        s"""Map("$localName" -> $value)"""
      }}.getOrElse(List("Map.empty")).mkString(" ++ ")

      s"""
      class New$nodeNameCamelCase($fields) extends NewNode with ${nodeNameCamelCase}Base {
        override val label = "${nodeType.name}"
        override val properties: Map[String, Any] = $propertiesImpl
        override def containedNodesByLocalName: Map[String, List[Node]] = $containedNodesByLocalName
      }
      """
    }

    writeFile(s"${outputDir.getPath}/NewNodes.scala", staticHeader, entries)
  }

  def writeFile(fileName: String, header: String, entries: List[String]): JFile = {
    val outputFile = File.newTemporaryFile()
    outputFile.appendLine(header)
    entries.map(outputFile.appendLine)

    val targetFile = File(fileName)
    targetFile.createIfNotExists(createParents = true)

    println(s"writing results to $targetFile")
    outputFile.copyTo(targetFile, overwrite = true)
    targetFile.toJava
  }

  def calculateNodeToInEdges(nodeTypes: List[NodeType]): mutable.MultiMap[String, String] = {
    val nodeBaseTraitNames: List[String] =
      (Resources.cpgJson \ "nodeBaseTraits").as[List[NodeBaseTrait]].map(_.name) :+ "NODE"

    val nodeToInEdges = new mutable.HashMap[String, mutable.Set[String]] with mutable.MultiMap[String, String]
    val nodeTypeNamesSet = nodeTypes.map(_.name).toSet ++ nodeBaseTraitNames

    for (nodeType <- nodeTypes;
         outEdge <- nodeType.outEdges;
         inNode <- outEdge.inNodes) {
      if (!nodeTypeNamesSet.contains(inNode)) {
        throw new RuntimeException(s"Node with name $inNode is not defined.")
      }
      nodeToInEdges.addBinding(inNode, outEdge.edgeName)
    }

    nodeToInEdges
  }
}

/* representation of NodeType in cpg.json */
case class NodeType(name: String,
                    keys: List[String],
                    outEdges: List[OutEdgeEntry],
                    is: Option[List[String]],
                    containedNodes: Option[List[ContainedNode]])

case class OutEdgeEntry(edgeName: String,
                        inNodes: List[String])

/** nodeType links to the referenced NodeType
  * cardinality must be one of `zeroOrOne`, `one`, `list` */
case class ContainedNode(nodeType: String, localName: String, cardinality: String)

// TODO: use better json library which supports enums
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

/* representation of EdgeType in cpg.json */
case class EdgeType(name: String, keys: List[String])

/* representation of nodeKey/edgeKey in cpg.json */
case class Property(name: String, comment: String, valueType: String, cardinality: String, multipleValues: Option[String])

/* representation of nodeBaseTrait in cpg.json */
case class NodeBaseTrait(name: String, hasKeys: List[String], `extends`: Option[String])

object HigherValueType extends Enumeration {
  type HigherValueType = Value
  val NONE, OPTION, LIST = Value
}

object Utils {

  def camelCase(snakeCase: String): String = {
    val corrected = // correcting for internal keys, like "_KEY" -> drop leading underscore
      if (snakeCase.startsWith("_")) snakeCase.drop(1)
      else snakeCase

    val elements: List[String] = corrected.split("_").map(_.toLowerCase).toList match {
      case head :: tail => head :: tail.map(_.capitalize)
      case Nil => Nil
    }
    elements.mkString
  }

  def getHigherType(property: Property): HigherValueType.Value = 
    Cardinality.fromName(property.cardinality) match {
      case Cardinality.One => HigherValueType.NONE
      case Cardinality.ZeroOrOne => HigherValueType.OPTION
      case Cardinality.List => HigherValueType.LIST
    }

  def getBaseType(property: Property): String = {
    property.valueType match {
      case "string" => "String"
      case "int" => "Integer"
      case "boolean" => "JBoolean"
      case _ => "UNKNOWN"
    }
  }

  def getCompleteType(property: Property): String =
    getHigherType(property) match {
      case HigherValueType.NONE => getBaseType(property)
      case HigherValueType.OPTION => s"Option[${getBaseType(property)}]"
      case HigherValueType.LIST => s"List[${getBaseType(property)}]"
    }
}
