package overflowdb.codegen

import better.files._
import java.io.{File => JFile}

/** Generates a domain model for OverflowDb traversals based on your domain-specific json schema.
  *
  * @param schemaFile: path to the schema (json file)
  * @param basePackage: specific for your domain, e.g. `com.example.mydomain`
  */
class CodeGen(schemaFile: String, basePackage: String) {
  import Helpers._
  val nodesPackage = s"$basePackage.nodes"
  val edgesPackage = s"$basePackage.edges"
  val schema = new Schema(schemaFile)

  def run(outputDir: JFile): List[JFile] =
    List(
      writeConstants(outputDir),
      writeEdgeFiles(outputDir),
      writeNodeFiles(outputDir),
      writeNewNodeFiles(outputDir))

/* TODO refactor: this was adapted directly from generateJava.py... */
def writeConstants(outputDir: JFile): JFile = {
  case class NameAndComment(name: String, comment: String)

  val baseDir = File(outputDir.getPath + "/" + basePackage.replaceAll("\\.", "/")).createDirectories

  def writeFile(className: String, entries: List[NameAndComment]): Unit = {
    val entriesSrc = entries.map { entry =>
      val javaDoc = Option(entry.comment).filter(_.nonEmpty).map(comment => s"""/** $comment */""").getOrElse("")
      s""" $javaDoc
         | public static final String ${entry.name} = "${entry.name}";
         |""".stripMargin
    }.mkString("\n")

    baseDir.createChild(s"$className.java").write(
      s"""package io.shiftleft.codepropertygraph.generated;
         |public class $className {
         |$entriesSrc
         |}""".stripMargin
    )
  }

  writeFile("NodeKeyNames", schema.nodeKeys.map { property => NameAndComment(property.name, property.comment)})
  writeFile("EdgeKeyNames", schema.edgeKeys.map { property => NameAndComment(property.name, property.comment)})
  writeFile("NodeTypes", schema.nodeTypes.map { tpe => NameAndComment(tpe.name, tpe.comment)})

  outputDir
}

  def writeEdgeFiles(outputDir: JFile): JFile = {
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
         |""".stripMargin

    val packageObject = {
      val factories = {
        val edgeFactories: List[String] = schema.edgeTypes.map(edgeType => edgeType.className + ".factory")
        s"""object Factories {
           |  lazy val all: List[EdgeFactory[_]] = $edgeFactories
           |  lazy val allAsJava: java.util.List[EdgeFactory[_]] = all.asJava
           |}
           |""".stripMargin
      }

      s"""$staticHeader
         |$propertyErrorRegisterImpl
         |$factories
         |""".stripMargin
    }

    def generateEdgeSource(edgeType: EdgeType, keys: List[Property]) = {
      val edgeClassName = edgeType.className

      val keysQuoted = quoted(keys.map(_.name))
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

      s"""$staticHeader
         |$companionObject
         |$classImpl
         |""".stripMargin
    }

    val baseDir = File(outputDir.getPath + "/" + edgesPackage.replaceAll("\\.", "/"))
    if (baseDir.exists) baseDir.delete()
    baseDir.createDirectories()
    baseDir.createChild("package.scala").write(packageObject)
    schema.edgeTypes.foreach { edge =>
      val src = generateEdgeSource(edge, edge.keys.map(schema.edgePropertyByName))
      val srcFile = edge.className + ".scala"
      baseDir.createChild(srcFile).write(src)
    }
    println(s"generated edge sources in $baseDir (${baseDir.list.size} files)")
    baseDir.toJava
  }

  def neighborAccessorName(edgeTypeName: String, direction: Direction.Value): String =
    camelCase(edgeTypeName + "_" + direction)

  def writeNodeFiles(outputDir: JFile): JFile = {
    val staticHeader =
      s"""package $nodesPackage
         |
         |import gremlin.scala._
         |import $basePackage.EdgeKeys
         |import $edgesPackage
         |import java.lang.{Boolean => JBoolean, Long => JLong}
         |import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
         |import org.apache.tinkerpop.gremlin.structure.{Direction, Vertex, VertexProperty}
         |import io.shiftleft.overflowdb.{EdgeFactory, NodeFactory, NodeLayoutInformation, OdbNode, OdbGraph, OdbNodeProperty, NodeRef}
         |import io.shiftleft.overflowdb.traversal.{NodeRefOps, PropertyKey, Traversal}
         |import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils
         |import scala.jdk.CollectionConverters._
         |""".stripMargin

    lazy val packageObject = {
      /* generic accessors for all potential neighbors. specific nodes override them, in case they really allow that edge type
       * TODO: resolve debate between Michael/Bernhard/Markus - these may not be needed. in the meantime, we also have
       * specific neighbor accessors driven by the schema, i.e. those are only available on the types that really allow the given edge type
       */
      val genericNeighborAccessors = for {
        direction <- Direction.all
        edgeType <- schema.edgeTypes
        accessor = neighborAccessorName(edgeType.name, direction)
      } yield s"def _$accessor(): JIterator[StoredNode] = { JCollections.emptyIterator() }"

      val rootTypes =
        s"""$propertyErrorRegisterImpl
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

      s"""$staticHeader
         |$rootTypes
         |$nodeBaseTraits
         |$keyBasedTraits
         |$factories
         |""".stripMargin
    }

    def generateNodeSource(nodeType: NodeType, keys: List[Property]) = {
      val keyConstants = keys.map(key => s"""val ${camelCaseCaps(key.name)} = "${key.name}" """).mkString("\n")
      val keyToValueMap = keys.map { property: Property =>
        getHigherType(property) match {
          case HigherValueType.None | HigherValueType.List =>
            s""" "${property.name}" -> { instance: ${nodeType.classNameDb} => instance.${camelCase(property.name)}}"""
          case HigherValueType.Option =>
            s""" "${property.name}" -> { instance: ${nodeType.classNameDb} => instance.${camelCase(property.name)}.orNull}"""
        }
      }.mkString(",\n")

      val outEdgeNames: Seq[String] = nodeType.outEdges.map(_.edgeName)
      val inEdgeNames:  Seq[String] = schema.nodeToInEdgeContexts.getOrElse(nodeType, Seq.empty).map(_.edgeName)

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
           |    val In: Array[String] = Array(${quoted(inEdgeNames).mkString(",")})
           |    val Out: Array[String] = Array(${quoted(outEdgeNames).mkString(",")})
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
        nodeType.containedNodesList.map { containedNode =>
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
             |  edges(Direction.OUT, "${DefaultEdgeTypes.ContainsNode}").asScala.toList
             |    .filter(_.valueOption(EdgeKeys.LOCAL_NAME).map(_  == "${containedNode.localName}").getOrElse(false))
             |    .sortBy(_.valueOption(EdgeKeys.INDEX))
             |    .map(_.inVertex.asInstanceOf[$containedNodeType])
             |    $traversalEnding
             |""".stripMargin
        }.mkString("\n")

      val productElements: List[ProductElement] = {
        var currIndex = -1
        def nextIdx = { currIndex += 1; currIndex }
        val forId = ProductElement("id", "getId", nextIdx)
        val forKeys = keys.map { key =>
          val name = camelCase(key.name)
          ProductElement(name, name, nextIdx)
        }
        val forContainedNodes = nodeType.containedNodesList.map { containedNode =>
          ProductElement(
            containedNode.localName,
            containedNode.localName,
            nextIdx)
        }
        forId +: (forKeys ++ forContainedNodes)
      }

      val productElementLabels =
        productElements.map { case ProductElement(name, accessorSrc, index) =>
          s"""case $index => "$name" """
        }.mkString("\n")

      val productElementAccessors =
        productElements.map { case ProductElement(name, accessorSrc, index) =>
          s"case $index => $accessorSrc"
        }.mkString("\n")

      val abstractContainedNodeAccessors = nodeType.containedNodesList.map { containedNode =>
        s"""def ${containedNode.localName}: ${getCompleteType(containedNode)}"""
      }.mkString("\n")

      val delegatingContainedNodeAccessors = nodeType.containedNodesList.map { containedNode =>
        s"""  def ${containedNode.localName} = get().${containedNode.localName}"""
      }.mkString("\n")

      val nodeBaseImpl =
        s"""trait ${className}Base extends Node $mixinTraitsForBase $propertyBasedTraits {
           |  def asStored : StoredNode = this.asInstanceOf[StoredNode]
           |  override def getId: JLong = -1L
           |
           |  $abstractContainedNodeAccessors
           |
           |  override def productElementLabel(n: Int): String =
           |      n match {
           |        $productElementLabels
           |      }
           |
           |  override def productElement(n: Int): Any =
           |      n match {
           |        $productElementAccessors
           |      }
           |
           |  override def productPrefix = "$className"
           |  override def productArity = ${productElements.size}
           |}
           |""".stripMargin

      def neighborOut(toCode: NeighborInfo => String): String = {
        nodeType.outEdges.map { case OutEdgeEntry(edgeName, inNodes) =>
          val nbaName = neighborAccessorName(edgeName, Direction.OUT)
          val neighborNodeType: String =
            if (inNodes.size == 1 && inNodes.head != DefaultNodeTypes.Node) {
              schema.nodeTypeByName(inNodes.head).className
            } else "StoredNode"
          toCode(NeighborInfo(nbaName, neighborNodeType))
        }
      }.mkString("\n")

      def neighborIn(toCode: NeighborInfo => String): String = {
        schema.nodeToInEdgeContexts.getOrElse(nodeType, Nil).map { case InEdgeContext(edgeName, outNodes) =>
          val nbaName = neighborAccessorName(edgeName, Direction.IN)
          val neighborNodeType: String =
            if (outNodes.size == 1) {
              outNodes.head.className
            } else "StoredNode"
          toCode(NeighborInfo(nbaName, neighborNodeType))
       }
      }.mkString("\n")

      val neighborDelegatorsOut = neighborOut { case NeighborInfo(nbaName, neighborNodeType) =>
        /* generic and specific neighbor accessors - as mentioned in comment above, we may not need generic ones (prefixed with `_`) in future */
        s"""def $nbaName: JIterator[$neighborNodeType] = get().$nbaName
           |override def _$nbaName(): JIterator[StoredNode] = get()._$nbaName
           |""".stripMargin
      }

      val neighborDelegatorsIn = neighborIn { case NeighborInfo(nbaName, neighborNodeType) =>
        /* generic and specific neighbor accessors - as mentioned in comment above, we may not need generic ones (prefixed with `_`) in future */
        s"""def $nbaName: JIterator[$neighborNodeType] = get().$nbaName
           |override def _$nbaName(): JIterator[StoredNode] = get()._$nbaName
           |""".stripMargin
      }

      val nodeRefImpl = {
        val propertyDelegators = keys
          .map(_.name)
          .map(camelCase)
          .map { name =>
            s"""  override def $name = get().$name"""
          }
          .mkString("\n")

        s"""class $className(graph: OdbGraph, id: Long) extends NodeRef[$classNameDb](graph, id)
           |  with ${className}Base
           |  with StoredNode
           |  $mixinTraits {
           |  $propertyDelegators
           |  $delegatingContainedNodeAccessors
           |  $neighborDelegatorsOut
           |  $neighborDelegatorsIn
           |  override def valueMap: JMap[String, AnyRef] = get.valueMap
           |  override def canEqual(that: Any): Boolean = get.canEqual(that)
           |  override def label: String = {
           |    $className.Label
           |  }
           |}
           |""".stripMargin
      }

      var offsetPos = -1
      val neighborAccessorsOut = neighborOut { case NeighborInfo(nbaName, neighborNodeType) =>
        offsetPos += 1
        /* generic and specific neighbor accessors - as mentioned in comment above, we may not need generic ones (prefixed with `_`) in future */
        s"""def $nbaName : JIterator[$neighborNodeType] = createAdjacentNodeIteratorByOffSet($offsetPos).asInstanceOf[JIterator[$neighborNodeType]]
           |override def _$nbaName : JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet($offsetPos).asInstanceOf[JIterator[StoredNode]]""".stripMargin
      }
      val neighborAccessorsIn = neighborIn { case NeighborInfo(nbaName, neighborNodeType) =>
        offsetPos += 1
        /* generic and specific neighbor accessors - as mentioned in comment above, we may not need generic ones (prefixed with `_`) in future */
        s"""def $nbaName : JIterator[$neighborNodeType] = createAdjacentNodeIteratorByOffSet($offsetPos).asInstanceOf[JIterator[$neighborNodeType]]
           |override def _$nbaName : JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet($offsetPos).asInstanceOf[JIterator[StoredNode]]""".stripMargin
      }

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
           |  $neighborAccessorsOut
           |  $neighborAccessorsIn
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

      s"""$staticHeader
         |$companionObject
         |$nodeBaseImpl
         |$nodeRefImpl
         |$classImpl
         |""".stripMargin
    }

    val baseDir = File(outputDir.getPath + "/" + nodesPackage.replaceAll("\\.", "/"))
    if (baseDir.exists) baseDir.delete()
    baseDir.createDirectories()
    baseDir.createChild("package.scala").write(packageObject)
    schema.nodeTypes.foreach { nodeType =>
      val src = generateNodeSource(nodeType, nodeType.keys.map(schema.nodePropertyByName))
      val srcFile = nodeType.className + ".scala"
      baseDir.createChild(srcFile).write(src)
    }
    println(s"generated node sources in $baseDir (${baseDir.list.size} files)")
    baseDir.toJava
  }

  /** generates classes to easily add new nodes to the graph
    * this ability could have been added to the existing nodes, but it turned out as a different specialisation,
    * since e.g. `id` is not set before adding it to the graph */
  def writeNewNodeFiles(outputDir: JFile): JFile = {
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

        val forContainedNodes: List[String] = nodeType.containedNodesList.map { containedNode =>
          val optionalDefault = Cardinality.fromName(containedNode.cardinality) match {
            case Cardinality.List      => "= List()"
            case Cardinality.ZeroOrOne => "= None"
            case _                     => ""
          }

          s"val ${containedNode.localName}: ${getCompleteType(containedNode)} $optionalDefault"
        }

        (forKeys ++ forContainedNodes).mkString(", ")
      }

      val propertiesImpl = keys match {
        case Nil => "Map.empty"
        case keys =>
          val containsOptionals = keys.exists { property =>
            Cardinality.fromName(property.cardinality) == Cardinality.ZeroOrOne
          }
          val forKeys = keys.map { key: Property =>
            s"""("${key.name}" -> ${camelCase(key.name)} )"""
          }.mkString(",\n")

          val baseCase = s"""Map($forKeys).asInstanceOf[Map[String, Any]].filterNot { case (k,v) => v == null || v == None } """
          if (!containsOptionals) baseCase
          else baseCase + s""".map {
                             |  case (k, v: Option[_]) => (k,v.get)
                             |  case other => other
                             |}
                             |""".stripMargin
      }

      val containedNodesByLocalName: String = {
        val mappedNodes = nodeType.containedNodesList.map { containedNode =>
          val localName = containedNode.localName
          val value = Cardinality.fromName(containedNode.cardinality) match {
            case Cardinality.One       => s"($localName :: Nil)"
            case Cardinality.ZeroOrOne => s"$localName.toList"
            case Cardinality.List      => localName
          }
          s"""("$localName" -> $value)"""
        }
        if (mappedNodes.isEmpty) "Map.empty"
        else mappedNodes.mkString("Map.empty + ", " + ", "")
      }

      s"""case class New${nodeType.className}($fields) extends NewNode with ${nodeType.className}Base {
         |  override val label = "${nodeType.name}"
         |  override val properties: Map[String, Any] = $propertiesImpl
         |  override def containedNodesByLocalName: Map[String, List[Node]] = $containedNodesByLocalName
         |}
         |""".stripMargin
    }

    val outfile = File(outputDir.getPath + "/" + nodesPackage.replaceAll("\\.", "/") + "/NewNodes.scala")
    if (outfile.exists) outfile.delete()
    outfile.createFile()
    val src = schema.nodeTypes.map { nodeType =>
      generateNewNodeSource(nodeType, nodeType.keys.map(schema.nodePropertyByName))
    }.mkString("\n")
    outfile.write(s"""$staticHeader
                     |$src
                     |""".stripMargin)
    println(s"generated NewNode sources in $outfile")
    outfile.toJava
  }

  /* surrounds input with `"` */
  def quoted(strings: Iterable[String]): Iterable[String] =
    strings.map(string => s""""$string"""")
}
