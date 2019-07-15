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
  val nodesPackage = "io.shiftleft.codepropertygraph.generated.nodes"
  val edgesPackage = "io.shiftleft.codepropertygraph.generated.edges"

  def run(outputDir: JFile): List[JFile] = {
    println(s"generating domain classes for nodes/edges based on cpg.json")
    List(writeEdgesFile(outputDir), writeNodesFile(outputDir), writeNewNodesFile(outputDir))
  }

  def writeEdgesFile(outputDir: JFile): JFile = {
    val propertyByName: Map[String, Property] =
      (Resources.cpgJson \ "edgeKeys")
        .as[List[Property]]
        .map { prop =>
          (prop.name -> prop)
        }
        .toMap

    def entries: List[String] =
      (Resources.cpgJson \ "edgeTypes")
        .as[List[EdgeType]]
        .map(edge => generateEdgeSource(edge, edge.keys.map(propertyByName)))

    def edgeHeader = {
      val staticHeader = s"""
      package $edgesPackage

      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Set => JSet}
      import java.util.{List => JList}
      import org.apache.tinkerpop.gremlin.structure.Property
      import org.apache.tinkerpop.gremlin.structure.{Vertex, VertexProperty}
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.{EdgeRef, OverflowDbEdge, OverflowDbNode, SpecializedElementFactory, SpecializedTinkerEdge, TinkerGraph, TinkerProperty, VertexRef}
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
          (Resources.cpgJson \ "edgeTypes")
            .as[List[EdgeType]]
            .map(edgeType => edgeType.className + ".Factory")
        s"""
        object Factories {
          lazy val All: List[SpecializedElementFactory.ForEdge[_]] = ${edgeFactories}
          lazy val AllAsJava: java.util.List[SpecializedElementFactory.ForEdge[_]] = All.asJava
        }
        """
      }

      staticHeader + factories
    }

    def generateEdgeSource(edgeType: EdgeType, keys: List[Property]) = {
      val edgeClassName = edgeType.className
      val edgeClassNameDb = s"${edgeClassName}Db"
      val keysQuoted = keys.map('"' + _.name + '"')
      val keyToValueMap = keys
        .map { key =>
          s""" "${key.name}" -> { instance: $edgeClassNameDb => instance.${camelCase(key.name)}()}"""
        }
        .mkString(",\n")

      val companionObject = s"""
      |object ${edgeClassName}NoClash {
      |  object Keys {
      |    val AllList: JList[String] = List(${keysQuoted.mkString(", ")}).asJava
      |  }
      |}
      |object $edgeClassName {
      |  val Label = "${edgeType.name}"
      |  object Keys {
      |    val All: JSet[String] = Set(${keysQuoted.mkString(", ")}).asJava
      |    val KeyToValue: Map[String, $edgeClassNameDb => Any] = Map(
      |      $keyToValueMap
      |    )
      |  }
      |
      |  val Factory = new SpecializedElementFactory.ForEdge[${edgeClassNameDb}] {
      |    override val forLabel = $edgeClassName.Label
      |
      |    override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: Vertex], inVertex: VertexRef[_ <: Vertex]) =
      |      new ${edgeClassNameDb}(graph, outVertex.asInstanceOf[VertexRef[OverflowDbNode]], inVertex.asInstanceOf[VertexRef[OverflowDbNode]])
      |
      |    override def createEdgeRef(edge: ${edgeClassNameDb}) = ${edgeClassName}(edge)
      |
      |    override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: Vertex], inVertex: VertexRef[_ <: Vertex]) = 
      |      ${edgeClassName}(id, graph)
      |  }
      |
      |  def apply(wrapped: $edgeClassNameDb) =
      |   new $edgeClassName(wrapped.id.asInstanceOf[JLong], wrapped.graph.asInstanceOf[TinkerGraph], wrapped)
      |  def apply(id: Long, graph: TinkerGraph) = new $edgeClassName(id, graph, null)
      |}
      """.stripMargin

      val edgeRefImpl =
        s"""
           |class ${edgeClassName}(_id: JLong, _graph: TinkerGraph, dbNode: ${edgeClassNameDb}) extends EdgeRef[${edgeClassNameDb}](_id, _graph, dbNode) {
           |  override def label(): String = {
           |    ${edgeClassName}.Label
           |  }
           |}
           """.stripMargin

      def propertyBasedFieldAccessors(properties: List[Property]): String =
        properties.map { property =>
          val name = camelCase(property.name)
          val baseType = getBaseType(property)
          val tpe = getCompleteType(property)

          // TODO refactor so we don't need to wrap the property in a TinkerProperty instance, only to unwrap it later
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
      class ${edgeClassNameDb}(_graph: TinkerGraph, _outVertex: VertexRef[OverflowDbNode], _inVertex: VertexRef[OverflowDbNode])
          extends OverflowDbEdge(_graph, $edgeClassName.Label, _outVertex, _inVertex, $edgeClassName.Keys.All) {

        ${propertyBasedFieldAccessors(keys)}
      }
      """

      companionObject + edgeRefImpl + classImpl
    }

    val filename = outputDir.getPath + "/" + edgesPackage.replaceAll("\\.", "/") + "/Edges.scala"
    writeFile(filename, edgeHeader, entries)
  }

  def writeNodesFile(outputDir: JFile): JFile = {
    val baseTraits = (Resources.cpgJson \ "nodeBaseTraits").as[List[NodeBaseTrait]]

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

      val nodeToInEdges = calculateNodeToInEdges(nodeTypes)

      nodeTypes.map(node => generateNodeSource(node, node.keys.map(propertyByName), nodeToInEdges))
    }

    def nodeHeader = {
      val staticHeader = s"""
      package $nodesPackage

      import gremlin.scala._
      import io.shiftleft.codepropertygraph.generated.EdgeKeys
      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
      import org.apache.tinkerpop.gremlin.structure.{Direction, Vertex, VertexProperty}
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.{OverflowDbNode, SpecializedElementFactory, SpecializedTinkerVertex, TinkerGraph, SpecializedVertexProperty, VertexRef}
      import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils
      import scala.collection.JavaConverters._
      import org.slf4j.LoggerFactory
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

      /* making use of the fact that SpecializedVertex is also our domain node */
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

      val nodeVisitor = 
        s"""trait NodeVisitor[T] {
          ${generateNodeVisitorMethods}
          ${generateBaseTraitVisitorMethods}
        }\n"""

      val nodeBaseTraits =
        (Resources.cpgJson \ "nodeBaseTraits")
          .as[List[NodeBaseTrait]]
          .map {
            case nodeBaseTrait: NodeBaseTrait =>
              val mixins = nodeBaseTrait.hasKeys.map { key =>
                s"with Has${camelCase(key).capitalize}"
              }.mkString(" ")

              val mixinTraits = nodeBaseTrait
                .extendz
                .getOrElse(Nil)
                .map { traitName =>
                  s"with ${camelCase(traitName).capitalize}"
                }.mkString(" ")

              val mixinTraitsForBase = nodeBaseTrait
                .extendz
                .getOrElse(List())
                .map { traitName =>
                  s"with ${camelCase(traitName).capitalize}Base"
                }.mkString(" ")

              s"""trait ${nodeBaseTrait.className}Base extends Node $mixins $mixinTraitsForBase
                  trait ${nodeBaseTrait.className} extends StoredNode with ${nodeBaseTrait.className}Base $mixinTraits
              """
          }.mkString("\n")

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

      val factories = {
        val vertexFactories: List[String] =
          (Resources.cpgJson \ "nodeTypes")
            .as[List[NodeType]]
            .map(nodeType => nodeType.className + ".Factory")
        s"""
        object Factories {
          lazy val All: List[SpecializedElementFactory.ForVertex[_]] = ${vertexFactories}
          lazy val AllAsJava: java.util.List[SpecializedElementFactory.ForVertex[_]] = All.asJava
        }
        """
      }

      staticHeader + nodeVisitor + nodeBaseTraits + keyBasedTraits + factories
    }

    def generateNodeVisitorMethods() = {
      val nodeTypes = (Resources.cpgJson \ "nodeTypes").as[List[NodeType]]

      nodeTypes.map { nodeType =>
        s"def visit(node: ${nodeType.className}): T = ???"
      }.mkString("\n")
    }

    def generateBaseTraitVisitorMethods() = {
      baseTraits.map { nodeBaseTrait: NodeBaseTrait =>
        s"def visit(node: ${nodeBaseTrait.className}): T = ???"
      }.mkString("\n")
    }

    def edgeTypeByName: Map[String, EdgeType] =
      (Resources.cpgJson \ "edgeTypes").as[List[EdgeType]].groupBy(_.name).mapValues(_.head)

    def generateNodeSource(nodeType: NodeType,
                           keys: List[Property],
                           nodeToInEdges: mutable.MultiMap[String, String]) = {
      val keyConstants = keys.map(key => s"""val ${camelCase(key.name).capitalize} = "${key.name}" """).mkString("\n")
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

      def outEdges(nodeType: NodeType): List[String] = {
        nodeType.outEdges.map(_.edgeName)
      }

      def inEdges(nodeType: NodeType): List[String] = {
        val option = nodeToInEdges.get(nodeType.name)
        option.map(_.toList).getOrElse(Nil)
      }

      def allEdges(nodeType: NodeType): List[String] =
        (outEdges(nodeType) ++ inEdges(nodeType)).distinct

      val keyCountByLabelEntries =
        allEdges(nodeType).map { edgeType =>
          val propertyCount = edgeTypeByName(edgeType).keys.size
          s""" "$edgeType" -> $propertyCount"""
        }.mkString(",\n")

      val positionInEdgeOffsetsEntries = {
        var position = -1 // starts with `0`
        val forOutEdges = outEdges(nodeType).map { edgeType =>
          position += 1
          s""" (Direction.OUT, "$edgeType") -> $position"""
        }
        val forInEdges = inEdges(nodeType).map { edgeType =>
          position += 1
          s""" (Direction.IN, "$edgeType") -> $position"""
        }
        (forOutEdges ++ forInEdges).mkString(",\n")
      }

      val offsetRelativeToAdjacentVertexRefEntries =
        allEdges(nodeType).flatMap { edgeType =>
          var offset = 0 // must start with `1`, because position `0` is the vertexRef itself
          edgeTypeByName(edgeType).keys.map { key =>
            offset = offset + 1
            s""" ("$edgeType", "$key") -> $offset """
          }
        }.mkString(",\n")

      val companionObject = s"""
      object ${nodeType.className} {
        implicit val marshaller: Marshallable[${nodeType.classNameDb}] = new Marshallable[${nodeType.classNameDb}] {
          override def fromCC(cc: ${nodeType.classNameDb}): FromCC = ???
          override def toCC(element: Element): ${nodeType.classNameDb} = element.asInstanceOf[${nodeType.classNameDb}]
        }
        val Label = "${nodeType.name}"
        object Keys {
          $keyConstants
          val All: JSet[String] = Set(${keys
        .map { key =>
          camelCase(key.name).capitalize
        }
        .mkString(", ")}).asJava
          val KeyToValue: Map[String, ${nodeType.classNameDb} => Any] = Map(
            $keyToValueMap
          )
        }
        object Edges {
          val In: Set[String] = Set(${inEdges(nodeType).map('"' + _ + '"').mkString(",")})
          val Out: Set[String] = Set(${outEdges(nodeType).map('"' + _ + '"').mkString(",")})
          val keyCountByLabel: Map[String, Int] = Map(
            $keyCountByLabelEntries
          )

          val positionInEdgeOffsets: Map[(Direction, String), Int] = Map(
            $positionInEdgeOffsetsEntries
          )

          val offsetRelativeToAdjacentVertexRef: Map[(String, String), Int] = Map(
            $offsetRelativeToAdjacentVertexRefEntries
          )
        }

        val Factory = new SpecializedElementFactory.ForVertex[${nodeType.classNameDb}] {
          override val forLabel = ${nodeType.className}.Label

          override def createVertex(id: JLong, graph: TinkerGraph) =
            new ${nodeType.classNameDb}(createVertexRef(id, graph).asInstanceOf[VertexRef[Vertex]])

          override def createVertex(ref: VertexRef[${nodeType.classNameDb}]) =
            new ${nodeType.classNameDb}(ref.asInstanceOf[VertexRef[Vertex]])

          override def createVertexRef(id: JLong, graph: TinkerGraph) = ${nodeType.className}(id, graph)
        }

        def apply(wrapped: ${nodeType.classNameDb}) =
         new ${nodeType.className}(wrapped.id.asInstanceOf[JLong], wrapped.graph.asInstanceOf[TinkerGraph], wrapped)
        def apply(id: Long, graph: TinkerGraph) = new ${nodeType.className}(id, graph, null)
      }
      """

      val mixinTraits: String =
        nodeType.is
          .getOrElse(List())
          .map { traitName =>
            s"with ${camelCase(traitName).capitalize}"
          }
          .mkString(" ")
      val mixinTraitsForBase: String =
        nodeType.is
          .getOrElse(List())
          .map { traitName =>
            s"with ${camelCase(traitName).capitalize}Base"
          }
          .mkString(" ")

      val propertyBasedTraits = keys.map(key => s"with Has${camelCase(key.name).capitalize}").mkString(" ")

      val valueMapImpl = {
        val putKeysImpl = keys
          .map { key: Property =>
            val memberName = camelCase(key.name)
            Cardinality.fromName(key.cardinality) match {
              case Cardinality.One =>
                s"""if (${memberName} != null) { properties.put("${key.name}", ${memberName}) }"""
              case Cardinality.ZeroOrOne =>
                s"""${memberName}.map { value => properties.put("${key.name}", value) }"""
              case Cardinality.List => // need java list, e.g. for VertexSerializer
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
        inEdges(nodeType).map { edge =>
          s"case ${edgesPackage}.${camelCase(edge).capitalize}.Label => walkIterators.add(${camelCase(edge)}In.iterator)"
        }
      }.mkString("\n")

      val walkOutEdgeCases = {
        outEdges(nodeType).map { edge =>
          s"case ${edgesPackage}.${camelCase(edge).capitalize}.Label => walkIterators.add(${camelCase(edge)}Out.iterator)"
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
              // TODO: remove duplication of handling of containedNodes
            val containedNodeType = if (containedNode.nodeType != "NODE") {
              containedNode.nodeTypeClassName + "Base"
            } else {
              containedNode.nodeTypeClassName
            }
            val completeType = Cardinality.fromName(containedNode.cardinality) match {
              case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
              case Cardinality.One       => containedNodeType
              case Cardinality.List      => s"List[$containedNodeType]"
            }
            s"""def ${containedNode.localName}: $completeType"""
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

      val nodeRefImpl = {
        val propertyDelegators = keys.map(_.name).map(camelCase).map { name =>
          s"""override def $name = get().$name"""
        }.mkString("\n")
        val containedNodesDelegators = nodeType.containedNodes
        s"""
          |class ${nodeType.className}(_id: JLong, _graph: TinkerGraph, dbNode: ${nodeType.classNameDb}) extends VertexRef[${nodeType.classNameDb}](_id, _graph, dbNode) with ${nodeType.className}Base with StoredNode $mixinTraits {
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

      val numberOfDifferentAdjacentTypes = outEdges(nodeType).size + inEdges(nodeType).size

      val allowedEdgeKeysBody =
        s"""
           | edgeLabel match {
           | ${outEdges(nodeType).map { outEdge => s"""case "$outEdge" => ${Utils.camelCase(outEdge).capitalize}NoClash.Keys.AllList"""}.mkString("\n")}
           |   case _ => Nil.asJava
           | }
         """.stripMargin

      val classImpl = s"""
      trait ${nodeType.className}Base extends Node $mixinTraitsForBase $propertyBasedTraits {
        def asStored : StoredNode = this.asInstanceOf[StoredNode]

        $abstractContainedNodeAccessors
      }

      class ${nodeType.classNameDb}(ref: VertexRef[Vertex])
          extends OverflowDbNode($numberOfDifferentAdjacentTypes, ref) with StoredNode $mixinTraits with ${nodeType.className}Base {

        override def allowedInEdgeLabels() = ${nodeType.className}.Edges.In.asJava
        override def allowedOutEdgeLabels() = ${nodeType.className}.Edges.Out.asJava
        override def specificKeys() = ${nodeType.className}.Keys.All

        override def allowedEdgeKeys(edgeLabel: String) = {
          $allowedEdgeKeysBody
        }

        override def getEdgeKeyCount(edgeLabel: String): Int =
          ${nodeType.className}.Edges.keyCountByLabel.getOrElse(edgeLabel, -1)

        override def getPositionInEdgeOffsets(direction: Direction, edgeLabel: String): Int =
          ${nodeType.className}.Edges.positionInEdgeOffsets.getOrElse((direction, edgeLabel), -1)

        override def getOffsetRelativeToAdjacentVertexRef(edgeLabel: String, key: String): Int = 
          ${nodeType.className}.Edges.offsetRelativeToAdjacentVertexRef.getOrElse((edgeLabel, key), -1)

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
                case Some(value) => new SpecializedVertexProperty(-1, this, key, value.asInstanceOf[A])
                case value => new SpecializedVertexProperty(-1, this, key, value.asInstanceOf[A])
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
                    new SpecializedVertexProperty(-1, this, key, value).asInstanceOf[VertexProperty[A]]
                  }.toIterator.asJava
                case value => IteratorUtils.of(new SpecializedVertexProperty(-1, this, key, value.asInstanceOf[A]))
              }
          }
        }

        override protected def updateSpecificProperty[A](cardinality: VertexProperty.Cardinality, key: String, value: A): VertexProperty[A] = {
          ${updateSpecificPropertyBody(keys)}
          new SpecializedVertexProperty(-1, this, key, value)
        }

        override protected def removeSpecificProperty(key: String): Unit =
          ${removeSpecificPropertyBody(keys)}

        $containedNodesAsMembers
      }
      """

      companionObject + nodeRefImpl + classImpl
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
      val fields: String = {
        val forKeys = keys.map { key =>
          val optionalDefault =
            /* a little hack to allow us to have different DomainClassCreator while using the same cpg traversals */
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
              // TODO: remove duplication of handling of containedNodes
              val containedNodeType = if (containedNode.nodeType != "NODE") {
                containedNode.nodeTypeClassName + "Base"
              } else {
                containedNode.nodeTypeClassName
              }
              val completeType = Cardinality.fromName(containedNode.cardinality) match {
                case Cardinality.ZeroOrOne => s"Option[$containedNodeType]"
                case Cardinality.One       => containedNodeType
                case Cardinality.List      => s"List[$containedNodeType]"
              }
              val optionalDefault = Cardinality.fromName(containedNode.cardinality) match {
                case Cardinality.List      => "= List()"
                case Cardinality.ZeroOrOne => "= None"
                case _ => ""
              }

              s"val ${containedNode.localName}: $completeType $optionalDefault"
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
    outputFile.copyTo(targetFile, overwrite = true)
    targetFile.toJava
  }

  def calculateNodeToInEdges(nodeTypes: List[NodeType]): mutable.MultiMap[String, String] = {
    val nodeBaseTraitNames: List[String] =
      (Resources.cpgJson \ "nodeBaseTraits").as[List[NodeBaseTrait]].map(_.name) :+ "NODE"

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

/* representation of NodeType in cpg.json */
case class NodeType(name: String,
                    keys: List[String],
                    outEdges: List[OutEdgeEntry],
                    is: Option[List[String]],
                    containedNodes: Option[List[ContainedNode]]) {
  lazy val className = Utils.camelCase(name).capitalize
  lazy val classNameDb = s"${className}Db"
}

case class OutEdgeEntry(edgeName: String, inNodes: List[String])

/** nodeType links to the referenced NodeType
  * cardinality must be one of `zeroOrOne`, `one`, `list` */
case class ContainedNode(nodeType: String, localName: String, cardinality: String) {
  lazy val nodeTypeClassName = Utils.camelCase(nodeType).capitalize
}

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
case class EdgeType(name: String, keys: List[String]) {
  lazy val className = Utils.camelCase(name).capitalize
}

/* representation of nodeKey/edgeKey in cpg.json */
case class Property(name: String, comment: String, valueType: String, cardinality: String)

/* representation of nodeBaseTrait in cpg.json */
case class NodeBaseTrait(name: String, hasKeys: List[String], `extends`: Option[List[String]]) {
  lazy val extendz = `extends` //it's mapped from the key in json :(
  lazy val className = Utils.camelCase(name).capitalize
}

object HigherValueType extends Enumeration {
  type HigherValueType = Value
  val None, Option, List = Value
}

object Utils {

  def isNodeBaseTrait(baseTraits: List[NodeBaseTrait], nodeName: String): Boolean = 
    nodeName == "NODE" || baseTraits.map(_.name).contains(nodeName)

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

  def propertyBasedFields(properties: List[Property]): String =
    properties.map { property =>
      val name = camelCase(property.name)
      val tpe = getCompleteType(property)

      getHigherType(property) match {
        case HigherValueType.None =>
        /** TODO: rather than returning `null`, throw an exception, since this is a schema violation:
          s"""|var _$name: $tpe = null
              |def $name: $tpe =
              |  if (_$name == null) {
              |    throw new AssertionError("property $name is mandatory but hasn't been initialised yet")
              |} else { _$name } """.stripMargin
          */
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
      s"""throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")"""
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
