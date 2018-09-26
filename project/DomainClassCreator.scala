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

/** Parses cpg domain model and generates case classes to represent vertices of our domain
  * sbt is setup to invoke this class automatically on `compile`. */
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
    List(writeNodesFile(outputDir), writeNewNodesFile(outputDir))
  }

  def writeNodesFile(outputDir: JFile): JFile = {
    val propertyByName: Map[String, Property] =
      (Resources.cpgJson \ "nodeKeys")
        .as[List[Property]]
        .map { prop =>
          (prop.name -> prop)
        }
        .toMap

    def entries: List[String] = {
      val nodeTypes = (Resources.cpgJson \ "nodeTypes")
        .as[List[NodeType]]

      nodeTypes.map(node => generateNodeSource(node, node.keys.map(propertyByName)))
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
      import org.apache.tinkerpop.gremlin.util.iterator.MultiIterator
      import scala.collection.JavaConverters._
      import shapeless.HNil

      trait Node extends gremlin.scala.dsl.DomainRoot

      trait StoredNode extends Node {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        def underlying: Vertex
      }

      trait HasEvalType extends StoredNode {
        def evalType: Type = {
          // this.vertices(Direction.OUT, generated.EdgeTypes.EVAL_TYPE).next.asInstanceOf[Type]
          ???
        }
      }
      """

      val nodeBaseTraits =
        (Resources.cpgJson \ "nodeBaseTraits")
          .as[List[NodeBaseTrait]]
          .map {
            case NodeBaseTrait(name, hasKeys, extendz) =>
              val nameCC = camelCase(name).capitalize
              val mixins = hasKeys
                .map { key =>
                  s"with Has${camelCase(key).capitalize}"
                }
                .mkString(" ")
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
          }
          .mkString("\n")

      val keyBasedTraits =
        (Resources.cpgJson \ "nodeKeys")
          .as[List[Property]]
          .map { property =>
            val camelCaseName = camelCase(property.name)
            val camelCaseCapitalized = camelCaseName.capitalize
            val tpe = getCompleteType(property)
            s"trait Has$camelCaseCapitalized { def $camelCaseName: $tpe }"
          }
          .mkString("\n")

      staticHeader + nodeBaseTraits + keyBasedTraits
    }

    def generateNodeSource(nodeType: NodeType, keys: List[Property]) = {
      val nodeNameCamelCase = camelCase(nodeType.name).capitalize

      val additionalConstructorParams = keys.map { key =>
        getHigherType(key) match {
          case HigherValueType.None =>
            s"""${camelCase(key.name)} = properties.get("${key.name}").asInstanceOf[${getBaseType(key)}]"""
          case HigherValueType.Option =>
            s"""${camelCase(key.name)} = Option(properties.get("${key.name}").asInstanceOf[${getBaseType(key)}])"""
          case HigherValueType.List =>
            s"""${camelCase(key.name)} = properties.get("${key.name}").asInstanceOf[${getCompleteType(key)}]"""
        }
      } match {
        case Nil    => ""
        case params => ",\n" + params.mkString(",\n")
      }

      val mixinTraits: String =
        nodeType.is
          .getOrElse(List())
          .map { traitName =>
            s"with ${camelCase(traitName).capitalize}"
          }
          .mkString(" ")

      val additionalFields = keys match {
        case Nil => ""
        case keys =>
          ", " + keys
            .map { key =>
              s"${camelCase(key.name)}: ${getCompleteType(key)}"
            }
            .mkString(", ")
      }

      val propertyBasedTraits = keys.map(key => s"with Has${camelCase(key.name).capitalize}").mkString(" ")

      /* TODO: reimplement later? */
      // val containedNodesAsMembers =
      //   nodeType.containedNodes.map { _.map { containedNode =>
      //     val containedNodeType = camelCase(containedNode.nodeType).capitalize
      //     assert(Set(Cardinality.ZeroOrOne,  Cardinality.One, Cardinality.List).contains(containedNode.cardinality),
      //       s"cardinality must be one of `zeroOrOne`, `one`, `list`, but was ${containedNode.cardinality}")
      //     val completeType = containedNode.cardinality match {
      //       case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
      //       case Cardinality.One => containedNodeType
      //       case Cardinality.List => s"List[$containedNodeType]"
      //     }
      //     val traversalEnding = containedNode.cardinality match {
      //       case Cardinality.ZeroOrOne => s".headOption"
      //       case Cardinality.One => s".head"
      //       case Cardinality.List => s".toList.sortBy(_.valueOption[Integer](generated.EdgeKeys.INDEX))"
      //     }

      //     // TODO: contains is actually optional -> `localName.map(_ == annotationParameters.getOrElse(false)))`
      //     s"""
      //      /** link to 'contained' node of type $containedNodeType */
      //      lazy val ${containedNode.localName}: $completeType =
      //         containsOut.asScala.toIterable
      //           .filter(_.asInstanceOf[generated.edges.Contains].localName == ${containedNode.localName})
      //           .map(_.inVertex.asInstanceOf[$containedNodeType])
      //           $traversalEnding
      //     """
      //     }.mkString("\n")
      //   }.getOrElse("")

      /* TODO: reimplement later */
      // val containedNodesAsMembers =
      //   nodeType.containedNodes.map { _.map { containedNode =>
      //     val containedNodeType = camelCase(containedNode.nodeType).capitalize
      //     assert(Set(Cardinality.ZeroOrOne,  Cardinality.One, Cardinality.List).contains(containedNode.cardinality),
      //       s"cardinality must be one of `zeroOrOne`, `one`, `list`, but was ${containedNode.cardinality}")
      //     val completeType = containedNode.cardinality match {
      //       case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
      //       case Cardinality.One => containedNodeType
      //       case Cardinality.List => s"List[$containedNodeType]"
      //     }
      //     val traversalEnding = containedNode.cardinality match {
      //       case Cardinality.ZeroOrOne => s".headOption.asInstanceOf[$completeType]"
      //       case Cardinality.One => s".head.asInstanceOf[$completeType]"
      //       case Cardinality.List => s".toList.asInstanceOf[$completeType]"
      //     }

      //     // TODO: remove cast
      //     // TODO: contains is actually optional -> `localName.map(_ == annotationParameters.getOrElse(false)))`
      //     s"""
      //      /** link to 'contained' node of type $containedNodeType */
      //      lazy val ${containedNode.localName}: $completeType =
      //         containsOut.asScala.toIterable
      //           .filter(_.asInstanceOf[generated.edges.Contains].localName == ${containedNode.localName})
      //           .map(_.inVertex)
      //           $traversalEnding
      //     """
      //     }.mkString("\n")
      //   }.getOrElse("")

      // val abstractContainedNodeAccessors = nodeType.containedNodes
      //   .map {
      //     _.map { containedNode =>
      //       val containedNodeType = if (containedNode.nodeType != "NODE") {
      //         camelCase(containedNode.nodeType).capitalize + "Base"
      //       } else {
      //         camelCase(containedNode.nodeType).capitalize
      //       }
      //       val completeType = Cardinality.fromName(containedNode.cardinality) match {
      //         case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
      //         case Cardinality.One       => containedNodeType
      //         case Cardinality.List      => s"List[$containedNodeType]"
      //       }
      //       s"""def ${containedNode.localName}: $completeType"""
      //     }.mkString("\n")
      //   }
      //   .getOrElse("")

      val classImpl = s"""
      trait ${nodeNameCamelCase}Base extends Node {
        def asStored: StoredNode = this.asInstanceOf[StoredNode]
      }

      case class $nodeNameCamelCase(@underlying _underlying: Option[Vertex] $additionalFields)
          extends StoredNode $mixinTraits $propertyBasedTraits with ${nodeNameCamelCase}Base {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        override val underlying = _underlying.get
      }
      """

      classImpl
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
        .map { prop =>
          (prop.name -> prop)
        }
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
          s"${camelCase(key.name)}: ${getCompleteType(key)}"
        }

        val forContainedNodes: List[String] = nodeType.containedNodes
          .map {
            _.map { containedNode =>
              // TODO: remove duplication of handling of containedNodes
              val containedNodeType = if (containedNode.nodeType != "NODE") {
                camelCase(containedNode.nodeType).capitalize + "Base"
              } else {
                camelCase(containedNode.nodeType).capitalize
              }
              val completeType = Cardinality.fromName(containedNode.cardinality) match {
                case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
                case Cardinality.One       => containedNodeType
                case Cardinality.List      => s"List[$containedNodeType]"
              }
              s"${containedNode.localName}: $completeType"
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

      val containedNodesByLocalName: String = nodeType.containedNodes
        .map {
          _.map { containedNode =>
            val localName = containedNode.localName
            val value = Cardinality.fromName(containedNode.cardinality) match {
              case Cardinality.One       => s"List($localName)"
              case Cardinality.ZeroOrOne => s"List($localName).flatten"
              case Cardinality.List      => localName
            }
            s"""Map("$localName" -> $value)"""
          }
        }
        .getOrElse(List("Map.empty"))
        .mkString(" ++ ")

      s"""
      case class New$nodeNameCamelCase($fields) extends NewNode with ${nodeNameCamelCase}Base {
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

  /* TODO: reimplement later? */
//   def calculateNodeToInEdges(nodeTypes: List[NodeType]): mutable.MultiMap[String, String] = {
//     val nodeBaseTraitNames: List[String] =
//       (Resources.cpgJson \ "nodeBaseTraits").as[List[NodeBaseTrait]].map(_.name) :+ "NODE"

//     val nodeToInEdges = new mutable.HashMap[String, mutable.Set[String]] with mutable.MultiMap[String, String]
//     val nodeTypeNamesSet = nodeTypes.map(_.name).toSet ++ nodeBaseTraitNames

//     for (nodeType <- nodeTypes;
//          outEdge <- nodeType.outEdges;
//          inNode <- outEdge.inNodes) {
//       if (!nodeTypeNamesSet.contains(inNode)) {
//         throw new RuntimeException(s"Node with name $inNode is not defined.")
//       }
//       nodeToInEdges.addBinding(inNode, outEdge.edgeName)
//     }

//     nodeToInEdges
//   }
}

/* representation of NodeType in cpg.json */
case class NodeType(name: String,
                    keys: List[String],
                    outEdges: List[OutEdgeEntry],
                    is: Option[List[String]],
                    containedNodes: Option[List[ContainedNode]])

case class OutEdgeEntry(edgeName: String, inNodes: List[String])

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
case class Property(name: String, comment: String, valueType: String, cardinality: String)

/* representation of nodeBaseTrait in cpg.json */
case class NodeBaseTrait(name: String, hasKeys: List[String], `extends`: Option[String])

object HigherValueType extends Enumeration {
  type HigherValueType = Value
  val None, Option, List = Value
}

object Utils {

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
      case HigherValueType.List   => s"java.util.List[${getBaseType(property)}]"
    }
}
