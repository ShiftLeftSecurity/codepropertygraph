package overflowdb.codegen

import better.files._
import overflowdb.codegen.CodeGen.ConstantContext
import overflowdb.schema._
import overflowdb.storage.ValueTypes
import scala.collection.mutable

/** Generates a domain model for OverflowDb traversals for a given domain-specific schema. */
class CodeGen(schema: Schema) {
  import Helpers._
  val basePackage = schema.basePackage
  val nodesPackage = s"$basePackage.nodes"
  val edgesPackage = s"$basePackage.edges"

  def run(outputDir: java.io.File): Seq[java.io.File] = {
    val _outputDir = outputDir.toScala
    val results = writeConstants(_outputDir) ++ writeEdgeFiles(_outputDir) ++ writeNodeFiles(_outputDir) :+ writeNewNodeFile(_outputDir)
    println(s"generated ${results.size} files in ${_outputDir}")
    results.map(_.toJava)
  }

  protected def writeConstants(outputDir: File): Seq[File] = {
    val results = mutable.Buffer.empty[File]
    val baseDir = outputDir / basePackage.replaceAll("\\.", "/")
    baseDir.createDirectories()

    def writeConstantsFile(className: String, constants: Seq[ConstantContext]): Unit = {
      val constantsSource = constants.map { constant =>
        val documentation = constant.documentation.filter(_.nonEmpty).map(comment => s"""/** $comment */""").getOrElse("")
        s""" $documentation
           | ${constant.source}
           |""".stripMargin
      }.mkString("\n")
      val allConstantsSetType = if (constantsSource.contains("PropertyKey")) "PropertyKey" else "String"
      val allConstantsBody = constants.map { constant =>
        s"     add(${constant.name});"
      }.mkString("\n").stripSuffix("\n")
      val allConstantsSet =
        s"""
           | public static Set<$allConstantsSetType> ALL = new HashSet<$allConstantsSetType>() {{
           |$allConstantsBody
           | }};
           |""".stripMargin
      val file = baseDir.createChild(s"$className.java").write(
        s"""package $basePackage;
           |
           |import overflowdb.*;
           |
           |import java.util.Collection;
           |import java.util.HashSet;
           |import java.util.Set;
           |
           |public class $className {
           |
           |$constantsSource
           |$allConstantsSet
           |}""".stripMargin
      )
      results.append(file)
    }

    writeConstantsFile("PropertyNames", schema.properties.map { property =>
      ConstantContext(property.name, s"""public static final String ${property.name} = "${property.name}";""", property.comment)
    })
    writeConstantsFile("NodeTypes", schema.nodeTypes.map { nodeType =>
      ConstantContext(nodeType.name, s"""public static final String ${nodeType.name} = "${nodeType.name}";""", nodeType.comment)
    })
    writeConstantsFile("EdgeTypes", schema.edgeTypes.map { edgeType =>
      ConstantContext(edgeType.name, s"""public static final String ${edgeType.name} = "${edgeType.name}";""", edgeType.comment)
    })
    schema.constantsByCategory.foreach { case (category, constants) =>
      writeConstantsFile(category, constants.map { constant =>
        ConstantContext(constant.name, s"""public static final String ${constant.name} = "${constant.value}";""", constant.comment)
      })
    }

    writeConstantsFile("Properties", schema.properties.map { property =>
      val src = {
        val valueType = typeFor(property.valueType)
        val cardinality = property.cardinality
        val completeType = cardinality match {
          case Cardinality.One => valueType
          case Cardinality.ZeroOrOne => valueType
          case Cardinality.List => s"scala.collection.Seq<$valueType>"
          case Cardinality.ISeq => s"collection.immutable.IndexedSeq<$valueType>"
        }
        s"""public static final overflowdb.PropertyKey<$completeType> ${property.name} = new overflowdb.PropertyKey<>("${property.name}");"""
      }
      ConstantContext(property.name, src, property.comment)
    })

    results.toSeq
  }

  protected def writeEdgeFiles(outputDir: File): Seq[File] = {
    val staticHeader =
      s"""package $edgesPackage
         |
         |import overflowdb._
         |import scala.jdk.CollectionConverters._
         |""".stripMargin

    val packageObject = {
      val factories = {
        val edgeFactories = schema.edgeTypes.map(edgeType => edgeType.className + ".factory").mkString(", ")
        s"""object Factories {
           |  lazy val all: List[EdgeFactory[_]] = List($edgeFactories)
           |  lazy val allAsJava: java.util.List[EdgeFactory[_]] = all.asJava
           |}
           |""".stripMargin
      }

      s"""$staticHeader
         |$propertyErrorRegisterImpl
         |$factories
         |""".stripMargin
    }

    def generateEdgeSource(edgeType: EdgeType, properties: Seq[Property]) = {
      val edgeClassName = edgeType.className

      val propertyNames = properties.map(_.className)

      val propertyNameDefs = properties.map { p =>
        s"""val ${p.className} = "${p.name}" """
      }.mkString("\n|    ")

      val propertyDefs = properties.map { p =>
        propertyKeyDef(p.name, typeFor(p.valueType), p.cardinality)
      }.mkString("\n|    ")

      val companionObject =
        s"""object $edgeClassName {
           |  val Label = "${edgeType.name}"
           |
           |  object PropertyNames {
           |    $propertyNameDefs
           |    val all: Set[String] = Set(${propertyNames.mkString(", ")})
           |    val allAsJava: java.util.Set[String] = all.asJava
           |  }
           |
           |  object Properties {
           |    $propertyDefs
           |  }
           |
           |  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)
           |
           |  val factory = new EdgeFactory[$edgeClassName] {
           |    override val forLabel = $edgeClassName.Label
           |
           |    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
           |      new $edgeClassName(graph, outNode, inNode)
           |  }
           |}
           |""".stripMargin

      def propertyBasedFieldAccessors(properties: Seq[Property]): String =
        properties.map { property =>
          val name = camelCase(property.name)
          val tpe = getCompleteType(property)

          getHigherType(property.cardinality) match {
            case HigherValueType.None =>
              s"""def $name: $tpe = property("${property.name}").asInstanceOf[$tpe]"""
            case HigherValueType.Option =>
              s"""def $name: $tpe = Option(property("${property.name}")).asInstanceOf[$tpe]""".stripMargin
            case HigherValueType.List =>
              s"""private var _$name: $tpe = Nil
                 |def $name: $tpe = {
                 |  val p = property("${property.name}")
                 |  if (p != null) p.asInstanceOf[JList].asScala
                 |  else Nil
                 |}""".stripMargin
          }
        }.mkString("\n\n")

      val classImpl =
        s"""class $edgeClassName(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
           |extends Edge(_graph, $edgeClassName.Label, _outNode, _inNode, $edgeClassName.PropertyNames.allAsJava) {
           |${propertyBasedFieldAccessors(properties)}
           |}
           |""".stripMargin

      s"""$staticHeader
         |$companionObject
         |$classImpl
         |""".stripMargin
    }

    val baseDir = outputDir / edgesPackage.replaceAll("\\.", "/")
    if (baseDir.exists) baseDir.delete()
    baseDir.createDirectories()
    val pkgObjFile = baseDir.createChild("package.scala").write(packageObject)
    val edgeTypeFiles = schema.edgeTypes.map { edge =>
      val src = generateEdgeSource(edge, edge.properties)
      val srcFile = edge.className + ".scala"
      baseDir.createChild(srcFile).write(src)
    }
    pkgObjFile +: edgeTypeFiles
  }

  protected def neighborAccessorNameForEdge(edge: EdgeType, direction: Direction.Value): String =
    camelCase(edge.name + "_" + direction)

  protected def writeNodeFiles(outputDir: File): Seq[File] = {
    val rootTypeImpl = {
      val genericNeighborAccessors = for {
        direction <- Direction.all
        edgeType <- schema.edgeTypes
        accessor = neighborAccessorNameForEdge(edgeType, direction)
      } yield s"def _$accessor: java.util.Iterator[StoredNode] = { java.util.Collections.emptyIterator() }"

      val keyBasedTraits =
        schema.nodeProperties.map { property =>
          val camelCaseName = camelCase(property.name)
          val tpe = getCompleteType(property)
          s"trait Has${property.className} { def $camelCaseName: $tpe }"
        }.mkString("\n") + "\n"

      val factories = {
        val nodeFactories =
          schema.nodeTypes.map(nodeType => nodeType.className + ".factory").mkString(", ")
        s"""object Factories {
           |  lazy val all: Seq[NodeFactory[_]] = Seq($nodeFactories)
           |  lazy val allAsJava: java.util.List[NodeFactory[_]] = all.asJava
           |}
           |""".stripMargin
      }
      val reChars = "[](){}*+&|?.,\\\\$"
      s"""package $nodesPackage
         |
         |import overflowdb._
         |import scala.jdk.CollectionConverters._
         |
         |$propertyErrorRegisterImpl
         |
         |object Misc {
         |  val reChars = "$reChars"
         |  def isRegex(pattern: String): Boolean = pattern.exists(reChars.contains(_))
         |}
         |
         |/** Abstract supertype for overflowdb.Node and NewNode */
         |trait AbstractNode {
         |  def label: String
         |}
         |
         |/* A node that is stored inside an Graph (rather than e.g. DiffGraph) */
         |trait StoredNode extends Node with AbstractNode with Product {
         |  /* underlying Node in the graph.
         |   * since this is a StoredNode, this is always set */
         |  def underlying: Node = this
         |
         |  /** labels of product elements, used e.g. for pretty-printing */
         |  def productElementLabel(n: Int): String
         |
         |  /* all properties plus label and id */
         |  def toMap: Map[String, Any] = {
         |    val map = valueMap
         |    map.put("_label", label)
         |    map.put("_id", id: java.lang.Long)
         |    map.asScala.toMap
         |  }
         |
         |  /*Sets fields from newNode*/
         |  def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode):Unit = ???
         |
         |  /* all properties */
         |  def valueMap: java.util.Map[String, AnyRef]
         |
         |  ${genericNeighborAccessors.mkString("\n")}
         |}
         |
         |  $keyBasedTraits
         |  $factories
         |""".stripMargin
    }

    val staticHeader =
      s"""package $nodesPackage
         |
         |import overflowdb._
         |import overflowdb.traversal.Traversal
         |import scala.jdk.CollectionConverters._
         |""".stripMargin

    lazy val nodeTraversalImplicits = {
      def implicitForNodeType(name: String) = {
        val traversalName = s"${name}Traversal"
        s"implicit def to$traversalName[NodeType <: $name](trav: Traversal[NodeType]): ${traversalName}[NodeType] = new $traversalName(trav)"
      }

      def implicitForNewNodeBuilder(name : String) = {
        val newNodeName = s"New${name}"
        val newNodeBuilderName = s"${newNodeName}Builder"
        s"implicit def ${newNodeBuilderName}To${newNodeName}(x : $newNodeBuilderName) : $newNodeName = x.build"
      }

      val implicitsForNodeTraversals =
        schema.nodeTypes.map(_.className).sorted.map(implicitForNodeType).mkString("\n")

      val implicitsForNodeBaseTypeTraversals =
        schema.nodeBaseTypes.map(_.className).sorted.map(implicitForNodeType).mkString("\n")

      val nonBaseTypes = (schema.nodeTypes.map(_.className).toSet -- schema.nodeBaseTypes.map(_.className).toSet).toList.sorted
      val implicitsForNewNodeBuilders =
        nonBaseTypes.map(implicitForNewNodeBuilder).mkString("\n")

      s"""package $nodesPackage
         |
         |import overflowdb.traversal.Traversal
         |
         |trait NodeTraversalImplicits extends NodeBaseTypeTraversalImplicits {
         |  $implicitsForNodeTraversals
         |
         |  $implicitsForNewNodeBuilders
         |}
         |
         |// lower priority implicits for base types
         |trait NodeBaseTypeTraversalImplicits {
         |  $implicitsForNodeBaseTypeTraversals
         |}
         |""".stripMargin
    }

    def generatePropertyTraversals(className: String, properties: Seq[Property]): String = {
      val propertyTraversals = properties.map { property =>
        val nameCamelCase = camelCase(property.name)
        val baseType = typeFor(property.valueType)
        val cardinality = property.cardinality

        val mapOrFlatMap = cardinality match {
          case Cardinality.One => "map"
          case Cardinality.ZeroOrOne | Cardinality.List | Cardinality.ISeq => "flatMap"
        }

        val filterStepsForSingleString =
          s"""  /**
             |    * Traverse to nodes where the $nameCamelCase matches the regular expression `value`
             |    * */
             |  def $nameCamelCase(pattern: $baseType): Traversal[NodeType] = {
             |    if(!Misc.isRegex(pattern)){
             |      traversal.filter{node => node.${nameCamelCase} == pattern}
             |    } else {
             |    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
             |    traversal.filter{node =>  matcher.reset(node.$nameCamelCase); matcher.matches()}
             |    }
             |  }
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase matches at least one of the regular expressions in `values`
             |    * */
             |  def $nameCamelCase(patterns: $baseType*): Traversal[NodeType] = {
             |    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
             |    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.$nameCamelCase); matcher.matches()}}
             |   }
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase matches `value` exactly.
             |    * */
             |  def ${nameCamelCase}Exact(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => node.${nameCamelCase} == value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase matches one of the elements in `values` exactly.
             |    * */
             |  def ${nameCamelCase}Exact(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.to(Set)
             |    traversal.filter{node => vset.contains(node.${nameCamelCase})}
             |  }
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase does not match the regular expression `value`.
             |    * */
             |  def ${nameCamelCase}Not(pattern: $baseType): Traversal[NodeType] = {
             |    if(!Misc.isRegex(pattern)){
             |      traversal.filter{node => node.${nameCamelCase} != pattern}
             |    } else {
             |    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
             |    traversal.filter{node =>  matcher.reset(node.$nameCamelCase); !matcher.matches()}
             |    }
             |  }
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase does not match any of the regular expressions in `values`.
             |    * */
             |  def ${nameCamelCase}Not(patterns: $baseType*): Traversal[NodeType] = {
             |    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
             |    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.$nameCamelCase); matcher.matches()}}
             |   }
             |
             |""".stripMargin

        val filterStepsForOptionalString =
          s"""  /**
             |    * Traverse to nodes where the $nameCamelCase matches the regular expression `value`
             |    * */
             |  def $nameCamelCase(pattern: $baseType): Traversal[NodeType] = {
             |    if(!Misc.isRegex(pattern)){
             |      traversal.filter{node => node.$nameCamelCase.isDefined && node.${nameCamelCase}.get == pattern}
             |    } else {
             |    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
             |    traversal.filter{node => node.$nameCamelCase.isDefined && {matcher.reset(node.$nameCamelCase.get); matcher.matches()}}
             |    }
             |  }
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase matches at least one of the regular expressions in `values`
             |    * */
             |  def $nameCamelCase(patterns: $baseType*): Traversal[NodeType] = {
             |    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
             |    traversal.filter{node => node.$nameCamelCase.isDefined && matchers.exists{ matcher => matcher.reset(node.$nameCamelCase.get); matcher.matches()}}
             |   }
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase matches `value` exactly.
             |    * */
             |  def ${nameCamelCase}Exact(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => node.$nameCamelCase.isDefined && node.${nameCamelCase}.get == value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase matches one of the elements in `values` exactly.
             |    * */
             |  def ${nameCamelCase}Exact(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.to(Set)
             |    traversal.filter{node => node.$nameCamelCase.isDefined && vset.contains(node.${nameCamelCase}.get)}
             |  }
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase does not match the regular expression `value`.
             |    * */
             |  def ${nameCamelCase}Not(pattern: $baseType): Traversal[NodeType] = {
             |    if(!Misc.isRegex(pattern)){
             |      traversal.filter{node => node.$nameCamelCase.isEmpty || node.${nameCamelCase}.get != pattern}
             |    } else {
             |    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
             |    traversal.filter{node => node.$nameCamelCase.isEmpty || {matcher.reset(node.$nameCamelCase.get); !matcher.matches()}}
             |    }
             |  }
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase does not match any of the regular expressions in `values`.
             |    * */
             |  def ${nameCamelCase}Not(patterns: $baseType*): Traversal[NodeType] = {
             |    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
             |    traversal.filter{node => node.$nameCamelCase.isEmpty || !matchers.exists{ matcher => matcher.reset(node.$nameCamelCase.get); matcher.matches()}}
             |   }
             |
             |""".stripMargin

        val filterStepsForSingleBoolean =
          s"""  /**
             |    * Traverse to nodes where the $nameCamelCase equals the given `value`
             |    * */
             |  def $nameCamelCase(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase == value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to the given `value`.
             |    * */
             |  def ${nameCamelCase}Not(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase != value}
             |""".stripMargin

        val filterStepsForOptionalBoolean =
          s"""  /**
             |    * Traverse to nodes where the $nameCamelCase equals the given `value`
             |    * */
             |  def $nameCamelCase(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => node.${nameCamelCase}.isDefined && node.$nameCamelCase.get == value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to the given `value`.
             |    * */
             |  def ${nameCamelCase}Not(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => !node.${nameCamelCase}.isDefined || node.$nameCamelCase.get == value}
             |""".stripMargin

        val filterStepsForSingleInt =
          s"""  /**
             |    * Traverse to nodes where the $nameCamelCase equals the given `value`
             |    * */
             |  def $nameCamelCase(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase == value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase equals at least one of the given `values`
             |    * */
             |  def $nameCamelCase(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.toSet
             |    traversal.filter{node => vset.contains(node.$nameCamelCase)}
             |  }
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase is greater than the given `value`
             |    * */
             |  def ${nameCamelCase}Gt(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase > value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase is greater than or equal the given `value`
             |    * */
             |  def ${nameCamelCase}Gte(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase >= value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase is less than the given `value`
             |    * */
             |  def ${nameCamelCase}Lt(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase < value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase is less than or equal the given `value`
             |    * */
             |  def ${nameCamelCase}Lte(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase <= value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to the given `value`.
             |    * */
             |  def ${nameCamelCase}Not(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase != value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to any of the given `values`.
             |    * */
             |  def ${nameCamelCase}Not(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.toSet
             |    traversal.filter{node => !vset.contains(node.$nameCamelCase)}
             |  }
             |""".stripMargin

        val filterStepsForOptionalInt =
          s"""  /**
             |    * Traverse to nodes where the $nameCamelCase equals the given `value`
             |    * */
             |  def $nameCamelCase(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => node.$nameCamelCase.isDefined && node.$nameCamelCase.get == value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase equals at least one of the given `values`
             |    * */
             |  def $nameCamelCase(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.toSet
             |    traversal.filter{node => node.$nameCamelCase.isDefined && vset.contains(node.$nameCamelCase.get)}
             |  }
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase is greater than the given `value`
             |    * */
             |  def ${nameCamelCase}Gt(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => node.$nameCamelCase.isDefined && node.$nameCamelCase.get > value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase is greater than or equal the given `value`
             |    * */
             |  def ${nameCamelCase}Gte(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => node.$nameCamelCase.isDefined && node.$nameCamelCase.get >= value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase is less than the given `value`
             |    * */
             |  def ${nameCamelCase}Lt(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => node.$nameCamelCase.isDefined && node.$nameCamelCase.get < value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase is less than or equal the given `value`
             |    * */
             |  def ${nameCamelCase}Lte(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => node.$nameCamelCase.isDefined && node.$nameCamelCase.get <= value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to the given `value`.
             |    * */
             |  def ${nameCamelCase}Not(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => !node.$nameCamelCase.isDefined || node.$nameCamelCase.get != value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to any of the given `values`.
             |    * */
             |  def ${nameCamelCase}Not(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.toSet
             |    traversal.filter{node => !node.$nameCamelCase.isDefined || !vset.contains(node.$nameCamelCase.get)}
             |  }
             |""".stripMargin

        val filterStepsGenericSingle =
          s"""  /**
             |    * Traverse to nodes where the $nameCamelCase equals the given `value`
             |    * */
             |  def $nameCamelCase(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase == value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase equals at least one of the given `values`
             |    * */
             |  def $nameCamelCase(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.toSet
             |    traversal.filter{node => !vset.contains(node.$nameCamelCase)}
             |  }
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to the given `value`.
             |    * */
             |  def ${nameCamelCase}Not(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{_.$nameCamelCase != value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to any of the given `values`.
             |    * */
             |  def ${nameCamelCase}Not(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.toSet
             |    traversal.filter{node => !vset.contains(node.$nameCamelCase)}
             |  }
             |""".stripMargin

        val filterStepsGenericOption =
          s"""  /**
             |    * Traverse to nodes where the $nameCamelCase equals the given `value`
             |    * */
             |  def $nameCamelCase(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => node.$nameCamelCase.isDefined && node.$nameCamelCase.get == value}
             |
             |  /**
             |    * Traverse to nodes where the $nameCamelCase equals at least one of the given `values`
             |    * */
             |  def $nameCamelCase(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.toSet
             |    traversal.filter{node => node.$nameCamelCase.isDefined && !vset.contains(node.$nameCamelCase.get)}
             |  }
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to the given `value`.
             |    * */
             |  def ${nameCamelCase}Not(value: $baseType): Traversal[NodeType] =
             |    traversal.filter{node => !node.$nameCamelCase.isDefined || node.$nameCamelCase.get != value}
             |
             |  /**
             |    * Traverse to nodes where $nameCamelCase is not equal to any of the given `values`.
             |    * */
             |  def ${nameCamelCase}Not(values: $baseType*): Traversal[NodeType] = {
             |    val vset = values.toSet
             |    traversal.filter{node => !node.$nameCamelCase.isDefined || !vset.contains(node.$nameCamelCase.get)}
             |  }
             |""".stripMargin

        val filterSteps = (cardinality, property.valueType) match {
          case (Cardinality.List | Cardinality.ISeq, _) => ""
          case (Cardinality.One, ValueTypes.STRING) => filterStepsForSingleString
          case (Cardinality.ZeroOrOne, ValueTypes.STRING) => filterStepsForOptionalString
          case (Cardinality.One, ValueTypes.BOOLEAN) => filterStepsForSingleBoolean
          case (Cardinality.ZeroOrOne, ValueTypes.BOOLEAN) => filterStepsForOptionalBoolean
          case (Cardinality.One, ValueTypes.INTEGER) => filterStepsForSingleInt
          case (Cardinality.ZeroOrOne, ValueTypes.INTEGER) => filterStepsForOptionalInt
          case (Cardinality.One, _) => filterStepsGenericSingle
          case (Cardinality.ZeroOrOne, _) => filterStepsGenericOption
          case _ => ""

        }

        s"""  /** Traverse to $nameCamelCase property */
           |  def $nameCamelCase: Traversal[$baseType] =
           |    traversal.$mapOrFlatMap(_.$nameCamelCase)
           |
           |  $filterSteps
           |""".stripMargin
      }.mkString("\n")

      s"""
         |/** Traversal steps for $className */
         |class ${className}Traversal[NodeType <: $className](val traversal: Traversal[NodeType]) extends AnyVal {
         |
         |$propertyTraversals
         |
         |}""".stripMargin
    }

    def generateNodeBaseTypeSource(nodeBaseType: NodeBaseType): String = {
      val className = nodeBaseType.className
      val properties = nodeBaseType.properties

      val mixins = nodeBaseType.properties.map { property =>
        s"with Has${property.className}"
      }.mkString(" ")

      val mixinTraits = nodeBaseType.extendz.map { baseTrait =>
        s"with ${baseTrait.className}"
      }.mkString(" ")

      val mixinTraitsForBase = nodeBaseType.extendz.map { baseTrait =>
        s"with ${baseTrait.className}Base"
      }.mkString(" ")

      def abstractEdgeAccessors(neighbors: Seq[AdjacentNode], direction: Direction.Value) =
        neighbors.groupBy(_.viaEdge).map { case (edge, neighbors) =>
          val edgeAccessorName = neighborAccessorNameForEdge(edge, direction)
          val neighborNodesType = deriveCommonSuperType(neighbors.map(_.neighbor).toSet).map(_.className).getOrElse("StoredNode")
          val genericEdgeAccessor = s"def $edgeAccessorName: Traversal[$neighborNodesType]"

          val specificNodeAccessors = neighbors.flatMap { adjacentNode =>
            val neighbor = adjacentNode.neighbor
            val entireNodeHierarchy: Set[AbstractNodeType] = neighbor.subtypes(schema.allNodeTypes.toSet) ++ (neighbor.extendzRecursively :+ neighbor)
            entireNodeHierarchy.map { neighbor =>
              val accessorName = s"_${camelCase(neighbor.name)}Via${edge.className.capitalize}${camelCaseCaps(direction.toString)}"
              val cardinality = adjacentNode.cardinality
              val appendix = cardinality match {
                case Cardinality.One => ".next()"
                case Cardinality.ZeroOrOne => s".nextOption()"
                case _ => ""
              }
              s"def $accessorName: ${fullScalaType(neighbor, cardinality)} = $edgeAccessorName.collectAll[${neighbor.className}]$appendix"
            }
          }.distinct.mkString("\n\n")

          s"""$genericEdgeAccessor
             |
             |$specificNodeAccessors""".stripMargin
        }.mkString("\n")

      s"""package $nodesPackage
         |
         |import overflowdb.traversal.Traversal
         |
         |trait ${className}Base extends AbstractNode
         |$mixins
         |$mixinTraitsForBase
         |
         |trait $className extends StoredNode with ${className}Base
         |$mixinTraits {
         |${abstractEdgeAccessors(nodeBaseType.outEdges, Direction.OUT)}
         |${abstractEdgeAccessors(nodeBaseType.inEdges, Direction.IN)}
         |}
         |
         |${generatePropertyTraversals(className, properties)}
         |
         |""".stripMargin
    }

    def generateNodeSource(nodeType: NodeType) = {
      val properties = nodeType.properties

      val propertyNames = nodeType.properties.map(_.name) ++ nodeType.containedNodes.map(_.localName)
      val propertyNameDefs = propertyNames.map { name =>
        s"""val ${camelCaseCaps(name)} = "$name" """
      }.mkString("\n|    ")

      val propertyDefs = properties.map { p =>
        propertyKeyDef(p.name, typeFor(p.valueType), p.cardinality)
      }.mkString("\n|    ")

      val propertyDefsForContainedNodes = nodeType.containedNodes.map { containedNode =>
        propertyKeyDef(containedNode.localName, containedNode.nodeType.className, containedNode.cardinality)
      }.mkString("\n|    ")

      val (neighborOutInfos, neighborInInfos) = {
        /** the offsetPos determines the index into the adjacent nodes array of a given node type
         * assigning numbers here must follow the same way as in NodeLayoutInformation, i.e. starting at 0,
         * first assign ids to the outEdges based on their order in the list, and then the same for inEdges */
        var _currOffsetPos = -1
        def nextOffsetPos: Int = { _currOffsetPos += 1; _currOffsetPos }

        case class AjacentNodeWithInheritanceStatus(adjacentNode: AdjacentNode, isInherited: Boolean)

        /** For all adjacent nodes, figure out if they are inherited or not.
         *  Later on, we will create edge accessors for all inherited neighbors, but only create the node accessors
         *  on the base types (if they are defined there). Note: they may even be inherited with a different cardinality */
        def adjacentNodesWithInheritanceStatus(adjacentNodes: AbstractNodeType => Seq[AdjacentNode]): Seq[AjacentNodeWithInheritanceStatus] = {
          val inherited = nodeType.extendzRecursively
            .flatMap(adjacentNodes)
            .map(AjacentNodeWithInheritanceStatus(_, true))

          // only edge and neighbor node matter, not the cardinality
          val inheritedLookup: Set[(EdgeType, AbstractNodeType)] =
            inherited.map(_.adjacentNode).map { case AdjacentNode(viaEdge, neighbor, _) => (viaEdge, neighbor) }.toSet

          val direct = adjacentNodes(nodeType).map { adjacentNode =>
            val isInherited = inheritedLookup.contains((adjacentNode.viaEdge, adjacentNode.neighbor))
            AjacentNodeWithInheritanceStatus(adjacentNode, isInherited)
          }
          (direct ++ inherited).distinct
        }

        def createNeighborInfos(neighborContexts: Seq[AjacentNodeWithInheritanceStatus], direction: Direction.Value): Seq[NeighborInfoForEdge] = {
          neighborContexts.groupBy(_.adjacentNode.viaEdge).map { case (edge, neighborContexts) =>
            val neighborInfoForNodes = neighborContexts.map { case AjacentNodeWithInheritanceStatus(adjacentNode, isInherited) =>
              NeighborInfoForNode(adjacentNode.neighbor, edge, direction, adjacentNode.cardinality, isInherited)
            }
            NeighborInfoForEdge(edge, neighborInfoForNodes, nextOffsetPos)
          }.toSeq
        }

        val neighborOutInfos = createNeighborInfos(adjacentNodesWithInheritanceStatus(_.outEdges), Direction.OUT)
        val neighborInInfos = createNeighborInfos(adjacentNodesWithInheritanceStatus(_.inEdges), Direction.IN)
        (neighborOutInfos, neighborInInfos)
      }

      val neighborInfos: Seq[(NeighborInfoForEdge, Direction.Value)] =
        neighborOutInfos.map((_, Direction.OUT)) ++ neighborInInfos.map((_, Direction.IN))

      def toLayoutInformationEntry(neighborInfos: Seq[NeighborInfoForEdge]): String = {
        neighborInfos.sortBy(_.offsetPosition).map { neighborInfo =>
          val edgeClass = neighborInfo.edge.className
          s"$edgesPackage.$edgeClass.layoutInformation"
        }.mkString(",\n")
      }
      val List(outEdgeLayouts, inEdgeLayouts) = List(neighborOutInfos, neighborInInfos).map(toLayoutInformationEntry)

      val className = nodeType.className
      val classNameDb = nodeType.classNameDb

      val companionObject =
        s"""object $className {
           |  def apply(graph: Graph, id: Long) = new $className(graph, id)
           |
           |  val Label = "${nodeType.name}"
           |
           |  object PropertyNames {
           |    $propertyNameDefs
           |    val all: Set[String] = Set(${propertyNames.map(camelCaseCaps).mkString(", ")})
           |    val allAsJava: java.util.Set[String] = all.asJava
           |  }
           |
           |  object Properties {
           |    $propertyDefs
           |    $propertyDefsForContainedNodes
           |  }
           |
           |  val layoutInformation = new NodeLayoutInformation(
           |    Label,
           |    PropertyNames.allAsJava,
           |    List($outEdgeLayouts).asJava,
           |    List($inEdgeLayouts).asJava)
           |
           |
           |  object Edges {
           |    val Out: Array[String] = Array(${quoted(neighborOutInfos.map(_.edge.name).sorted).mkString(",")})
           |    val In: Array[String] = Array(${quoted(neighborInInfos.map(_.edge.name).sorted).mkString(",")})
           |  }
           |
           |  val factory = new NodeFactory[$classNameDb] {
           |    override val forLabel = $className.Label
           |
           |    override def createNode(ref: NodeRef[$classNameDb]) =
           |      new $classNameDb(ref.asInstanceOf[NodeRef[NodeDb]])
           |
           |    override def createNodeRef(graph: Graph, id: Long) = $className(graph, id)
           |  }
           |}
           |""".stripMargin

      val mixinTraits: String =
        nodeType.extendz
          .map { traitName =>
            s"with ${traitName.className}"
          }
          .mkString(" ")

      val mixinTraitsForBase: String =
        nodeType.extendz
          .map { traitName =>
            s"with ${traitName.className}Base"
          }
          .mkString(" ")

      val propertyBasedTraits = properties.map(p => s"with Has${p.className}").mkString(" ")

      val valueMapImpl = {
        val putKeysImpl = properties
          .map { key: Property =>
            val memberName = camelCase(key.name)
            key.cardinality match {
              case Cardinality.One =>
                s"""if ($memberName != null) { properties.put("${key.name}", $memberName) }"""
              case Cardinality.ZeroOrOne =>
                s"""$memberName.map { value => properties.put("${key.name}", value) }"""
              case Cardinality.List | Cardinality.ISeq => // need java list, e.g. for NodeSerializer
                s"""if (this._$memberName != null && this._$memberName.nonEmpty) { properties.put("${key.name}", $memberName.asJava) }"""
            }
          }
          .mkString("\n")
        val putRefsImpl = {
          nodeType.containedNodes.map { cnt =>
            val memberName = cnt.localName
            cnt.cardinality match {
              case Cardinality.One =>
                s"""   if (this._$memberName != null) { properties.put("${memberName}", this._$memberName) }"""
              case Cardinality.ZeroOrOne =>
                s"""   if (this._$memberName != null && this._$memberName.nonEmpty) { properties.put("${memberName}", this._$memberName.get) }"""
              case Cardinality.List | Cardinality.ISeq => // need java list, e.g. for NodeSerializer
                s"""  if (this._$memberName != null && this._$memberName.nonEmpty) { properties.put("${memberName}", this.$memberName.asJava) }"""
            }
          }
        }.mkString("\n")

        s""" {
        |  val properties = new java.util.HashMap[String, AnyRef]
        |$putKeysImpl
        |$putRefsImpl
        |  properties
        |}""".stripMargin
      }

      val fromNew = {
        val initKeysImpl = properties
          .map { key: Property =>
            val memberName = camelCase(key.name)
            key.cardinality match {
              case Cardinality.One =>
                s"""   this._$memberName = other.$memberName""".stripMargin
              case Cardinality.ZeroOrOne =>
                s"""   this._$memberName = if(other.$memberName != null) other.$memberName else None""".stripMargin
              case Cardinality.List =>
                s"""   this._$memberName = if(other.$memberName != null) other.$memberName else Nil""".stripMargin
              case Cardinality.ISeq => ???
            }
          }
          .mkString("\n")

        val initRefsImpl = {
          nodeType.containedNodes.map { containedNode =>
            val memberName = containedNode.localName
            val containedNodeType = containedNode.nodeType.className

            containedNode.cardinality match {
              case Cardinality.One =>
                s"""  this._$memberName = other.$memberName match {
                   |    case null => null
                   |    case newNode: NewNode => mapping(newNode).asInstanceOf[$containedNodeType]
                   |    case oldNode: StoredNode => oldNode.asInstanceOf[$containedNodeType]
                   |    case _ => throw new MatchError("unreachable")
                   |  }""".stripMargin
              case Cardinality.ZeroOrOne =>
                s"""  this._$memberName = other.$memberName match {
                   |    case null | None => None
                   |    case Some(newNode:NewNode) => Some(mapping(newNode).asInstanceOf[$containedNodeType])
                   |    case Some(oldNode:StoredNode) => Some(oldNode.asInstanceOf[$containedNodeType])
                   |    case _ => throw new MatchError("unreachable")
                   |  }""".stripMargin
              case Cardinality.List => // need java list, e.g. for NodeSerializer
                s"""  this._$memberName = if(other.$memberName == null) Nil else other.$memberName.map { nodeRef => nodeRef match {
                   |    case null => throw new NullPointerException("Nullpointers forbidden in contained nodes")
                   |    case newNode:NewNode => mapping(newNode).asInstanceOf[$containedNodeType]
                   |    case oldNode:StoredNode => oldNode.asInstanceOf[$containedNodeType]
                   |    case _ => throw new MatchError("unreachable")
                   |  }}""".stripMargin
              case Cardinality.ISeq =>
                s"""{
                   |  val arr = if(other.$memberName == null || other.$memberName.isEmpty) null
                   |    else other.$memberName.map { nodeRef => nodeRef match {
                   |      case null => throw new NullPointerException("Nullpointers forbidden in contained nodes")
                   |      case newNode:NewNode => mapping(newNode).asInstanceOf[$containedNodeType]
                   |      case oldNode:StoredNode => oldNode.asInstanceOf[$containedNodeType]
                   |      case _ => throw new MatchError("unreachable")
                   |    }}.toArray
                   |
                   |  this._$memberName = if(arr == null) collection.immutable.ArraySeq.empty
                   |    else collection.immutable.ArraySeq.unsafeWrapArray(arr)
                   |}""".stripMargin
            }
          }
        }.mkString("\n\n")

        val registerFullName = if(!properties.map{_.name}.contains("FULL_NAME")) "" else {
          s"""  graph.indexManager.putIfIndexed("FULL_NAME", other.fullName, this.ref)"""
        }

        s"""override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
           |  //this will throw for bad types -- no need to check by hand, we don't have a better error message
           |  val other = someNewNode.asInstanceOf[New${nodeType.className}]
           |$initKeysImpl
           |$initRefsImpl
           |$registerFullName
           |}""".stripMargin
      }

      val containedNodesAsMembers =
        nodeType.containedNodes
          .map { containedNode =>
            val containedNodeType = containedNode.nodeType.className
            containedNode.cardinality match {
              case Cardinality.One =>
                s"""
                   |private var _${containedNode.localName}: $containedNodeType = null
                   |def ${containedNode.localName}: $containedNodeType = this._${containedNode.localName}
                   |""".stripMargin
              case Cardinality.ZeroOrOne =>
                s"""
                   |private var _${containedNode.localName}: Option[$containedNodeType] = None
                   |def ${containedNode.localName}: Option[$containedNodeType] = this._${containedNode.localName}
                   |""".stripMargin
              case Cardinality.List =>
                s"""
                   |private var _${containedNode.localName}: Seq[$containedNodeType] = Seq.empty
                   |def ${containedNode.localName}: Seq[$containedNodeType] = this._${containedNode.localName}
                   |""".stripMargin
              case Cardinality.ISeq =>
                s"""
                   |private var _${containedNode.localName}: collection.immutable.ArraySeq[$containedNodeType] = collection.immutable.ArraySeq.empty
                   |def ${containedNode.localName}: collection.immutable.IndexedSeq[$containedNodeType] = this._${containedNode.localName}
                   |""".stripMargin
            }
          }
          .mkString("\n")

      val productElements: Seq[ProductElement] = {
        var currIndex = -1
        def nextIdx = { currIndex += 1; currIndex }
        val forId = ProductElement("id", "id", nextIdx)
        val forKeys = properties.map { key =>
          val name = camelCase(key.name)
          ProductElement(name, name, nextIdx)
        }
        val forContainedNodes = nodeType.containedNodes.map { containedNode =>
          ProductElement(
            containedNode.localName,
            containedNode.localName,
            nextIdx)
        }
        forId +: (forKeys ++ forContainedNodes)
      }

      val productElementLabels =
        productElements.map { case ProductElement(name, _, index) =>
          s"""case $index => "$name" """
        }.mkString("\n")

      val productElementAccessors =
        productElements.map { case ProductElement(_, accessorSrc, index) =>
          s"case $index => $accessorSrc"
        }.mkString("\n")

      val abstractContainedNodeAccessors = nodeType.containedNodes.map { containedNode =>
        s"""def ${containedNode.localName}: ${getCompleteType(containedNode)}"""
      }.mkString("\n")

      val delegatingContainedNodeAccessors = nodeType.containedNodes.map { containedNode =>
        containedNode.cardinality match {
          case Cardinality.One =>
            s"""  def ${containedNode.localName}: ${containedNode.nodeType.className} = get().${containedNode.localName}"""
          case Cardinality.ZeroOrOne =>
            s"""  def ${containedNode.localName}: Option[${containedNode.nodeType.className}] = get().${containedNode.localName}"""
          case Cardinality.List =>
            s"""  def ${containedNode.localName}: Seq[${containedNode.nodeType.className}] = get().${containedNode.localName}"""
          case Cardinality.ISeq =>
            s"""  def ${containedNode.localName}: collection.immutable.IndexedSeq[${containedNode.nodeType.className}] = get().${containedNode.localName}"""
        }
      }.mkString("\n")

      val nodeBaseImpl =
        s"""trait ${className}Base extends AbstractNode $mixinTraitsForBase $propertyBasedTraits {
           |  def asStored : StoredNode = this.asInstanceOf[StoredNode]
           |
           |  $abstractContainedNodeAccessors
           |}
           |""".stripMargin

      val neighborAccessorDelegators = neighborInfos.map { case (neighborInfo, direction) =>
        val edgeAccessorName = neighborAccessorNameForEdge(neighborInfo.edge, direction)
        val nodeDelegators = neighborInfo.nodeInfos.collect {
          case neighborNodeInfo if !neighborNodeInfo.isInherited =>
            val accessorNameForNode = neighborNodeInfo.accessorName
            s"def $accessorNameForNode: ${neighborNodeInfo.returnType} = get().$accessorNameForNode"
        }.mkString("\n")

        s"""def $edgeAccessorName = get().$edgeAccessorName
           |override def _$edgeAccessorName = get()._$edgeAccessorName
           |$nodeDelegators
           |""".stripMargin
      }.mkString("\n")

      val nodeRefImpl = {
        val propertyDelegators = properties.map { key =>
          val name = camelCase(key.name)
          s"""  override def $name: ${getCompleteType(key)} = get().$name"""
        }.mkString("\n")

        s"""class $className(graph: Graph, id: Long) extends NodeRef[$classNameDb](graph, id)
           |  with ${className}Base
           |  with StoredNode
           |  $mixinTraits {
           |  $propertyDelegators
           |  $delegatingContainedNodeAccessors
           |  $neighborAccessorDelegators
           |
           |  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
           |  override def valueMap: java.util.Map[String, AnyRef] = get.valueMap
           |  override def canEqual(that: Any): Boolean = get.canEqual(that)
           |  override def label: String = {
           |    $className.Label
           |  }
           |
           |  override def productElementLabel(n: Int): String =
           |    n match {
           |      $productElementLabels
           |    }
           |
           |  override def productElement(n: Int): Any =
           |    n match {
           |      $productElementAccessors
           |    }
           |
           |  override def productPrefix = "$className"
           |  override def productArity = ${productElements.size}
           |}
           |""".stripMargin
      }

      val neighborAccessors = neighborInfos.map { case (neighborInfo, direction) =>
        val edgeAccessorName = neighborAccessorNameForEdge(neighborInfo.edge, direction)
        val neighborType = neighborInfo.deriveNeighborNodeType
        val offsetPosition = neighborInfo.offsetPosition

        val nodeAccessors = neighborInfo.nodeInfos.collect {
          case neighborNodeInfo if !neighborNodeInfo.isInherited =>
            val appendix = neighborNodeInfo.consolidatedCardinality match {
              case Cardinality.One => ".next()"
              case Cardinality.ZeroOrOne => s".nextOption()"
              case _ => ""
            }
            s"def ${neighborNodeInfo.accessorName}: ${neighborNodeInfo.returnType} = $edgeAccessorName.collectAll[${neighborNodeInfo.neighborNode.className}]$appendix"
        }.mkString("\n")

        s"""def $edgeAccessorName: Traversal[$neighborType] = Traversal(createAdjacentNodeIteratorByOffSet[$neighborType]($offsetPosition))
           |override def _$edgeAccessorName = createAdjacentNodeIteratorByOffSet[StoredNode]($offsetPosition)
           |$nodeAccessors
           |""".stripMargin
      }.mkString("\n")

      val updateSpecificPropertyImpl: String = {
        def caseEntry(name: String, accessorName: String, cardinality: Cardinality, baseType: String) = {
          val setter = cardinality match {
            case Cardinality.One =>
              s"value.asInstanceOf[$baseType]"
            case Cardinality.ZeroOrOne =>
              s"""value match {
                 |        case null | None => None
                 |        case someVal: $baseType => Some(someVal)
                 |      }""".stripMargin
            case Cardinality.List =>
              s"""value match {
                 |        case singleValue: $baseType => List(singleValue)
                 |        case null | None | Nil => Nil
                 |        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[$baseType]].iterator.asScala.toList
                 |        case _: List[_] => value.asInstanceOf[List[$baseType]]
                 |      }""".stripMargin
            case Cardinality.ISeq =>
              s"""value match {
                 |        case null  => collection.immutable.ArraySeq.empty
                 |        case singleValue: $baseType => collection.immutable.ArraySeq(singleValue)
                 |        case arr: Array[$baseType] => if(arr.nonEmpty)  collection.immutable.ArraySeq.unsafeWrapArray(arr) else collection.immutable.ArraySeq.empty
                 |        case jCollection: java.lang.Iterable[_] => if(jCollection.iterator.hasNext()) collection.immutable.ArraySeq.unsafeWrapArray(jCollection.asInstanceOf[java.util.Collection[$baseType]].iterator.asScala.toArray) else collection.immutable.ArraySeq.empty
                 |        case iter: Iterable[_] => if(iter.nonEmpty) collection.immutable.ArraySeq.unsafeWrapArray(iter.asInstanceOf[Iterable[$baseType]].toArray) else collection.immutable.ArraySeq.empty
                 |      }""".stripMargin
          }
          s"""|      case "$name" => this._$accessorName = $setter"""
        }

        val forKeys = properties.map(p => caseEntry(p.name, camelCase(p.name), p.cardinality, typeFor(p.valueType))).mkString("\n")

        val forContaintedNodes = nodeType.containedNodes.map(containedNode =>
          caseEntry(containedNode.localName, containedNode.localName, containedNode.cardinality, containedNode.nodeType.className)
        ).mkString("\n")

        s"""  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
           |    key match {
           |    $forKeys
           |    $forContaintedNodes
           |      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
           |    }
           |  }""".stripMargin
      }

      val propertyImpl: String = {
        def caseEntry(name: String, accessorName: String, cardinality: Cardinality) = {
          cardinality match {
            case Cardinality.One | Cardinality.List =>
              s"""|      case "$name" => this._$accessorName"""
            case Cardinality.ZeroOrOne =>
              s"""|      case "$name" => this._$accessorName.orNull"""
            case Cardinality.ISeq =>
              s"""|      case "$name" => this._$accessorName"""

          }
        }

        val forKeys = properties.map(key =>
          caseEntry(key.name, camelCase(key.name), key.cardinality)
        ).mkString("\n")

        val forContainedKeys = nodeType.containedNodes.map(containedNode =>
          caseEntry(containedNode.localName ,containedNode.localName, containedNode.cardinality)
        ).mkString("\n")

        s"""override def property(key:String): AnyRef = {
           |    key match {
           |      $forKeys
           |      $forContainedKeys
           |      case _ => null
           |    }
           |  }""".stripMargin
      }

      val classImpl =
        s"""class $classNameDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
           |  $mixinTraits with ${className}Base {
           |
           |  override def layoutInformation: NodeLayoutInformation = $className.layoutInformation
           |
           |${propertyBasedFields(properties)}
           |$containedNodesAsMembers
           |
           |  /* all properties */
           |  override def valueMap: java.util.Map[String, AnyRef] = $valueMapImpl
           |
           |  $neighborAccessors
           |
           |  override def label: String = {
           |    $className.Label
           |  }
           |
           |  override def productElementLabel(n: Int): String =
           |    n match {
           |      $productElementLabels
           |    }
           |
           |  override def productElement(n: Int): Any =
           |    n match {
           |      $productElementAccessors
           |    }
           |
           |  override def productPrefix = "$className"
           |  override def productArity = ${productElements.size}
           |
           |  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[$classNameDb]
           |
           |  $propertyImpl
           |
           |$updateSpecificPropertyImpl
           |
           |override def removeSpecificProperty(key: String): Unit =
           |  this.updateSpecificProperty(key, null)
           |
           |$fromNew
           |
           |}""".stripMargin

      s"""$staticHeader
         |$companionObject
         |$nodeBaseImpl
         |$nodeRefImpl
         |$classImpl
         |${generatePropertyTraversals(className, properties)}
         |""".stripMargin
    }

    val packageObject =
      s"""package $basePackage
         |package object nodes extends NodeTraversalImplicits
         |""".stripMargin

    val results = mutable.Buffer.empty[File]
    val baseDir = outputDir / nodesPackage.replaceAll("\\.", "/")
    if (baseDir.exists) baseDir.delete()
    baseDir.createDirectories()
    results.append(baseDir.createChild("package.scala").write(packageObject))
    results.append(baseDir.createChild("NodeTraversalImplicits.scala").write(nodeTraversalImplicits))
    results.append(baseDir.createChild("RootTypes.scala").write(rootTypeImpl))
    schema.nodeBaseTypes.foreach { nodeBaseTrait =>
      val src = generateNodeBaseTypeSource(nodeBaseTrait)
      val srcFile = nodeBaseTrait.className + ".scala"
      results.append(baseDir.createChild(srcFile).write(src))
    }
    schema.nodeTypes.foreach { nodeType =>
      val src = generateNodeSource(nodeType)
      val srcFile = nodeType.className + ".scala"
      results.append(baseDir.createChild(srcFile).write(src))
    }
    results.toSeq
  }

  /** generates classes to easily add new nodes to the graph
    * this ability could have been added to the existing nodes, but it turned out as a different specialisation,
    * since e.g. `id` is not set before adding it to the graph */
  protected def writeNewNodeFile(outputDir: File): File = {
    val staticHeader =
      s"""package $nodesPackage
         |
         |/** base type for all nodes that can be added to a graph, e.g. the diffgraph */
         |trait NewNode extends AbstractNode {
         |  def properties: Map[String, Any]
         |}
         |
         |trait NewNodeBuilder {
         |  def id : Long
         |  def id(x: Long) : NewNodeBuilder
         |  def build : NewNode
         |}
         |""".stripMargin

    def generateNewNodeSource(nodeType: NodeType, keys: Seq[Property]) = {
      val fieldDescriptions = mutable.ArrayBuffer.empty[(String, String, Option[String])] // fieldName, type, default
      for (key <- keys) {
        val optionalDefault =
          if (getHigherType(key.cardinality) == HigherValueType.Option) Some("None")
          else if (key.valueType == ValueTypes.INTEGER) Some("-1")
          else if (getHigherType(key.cardinality) == HigherValueType.None && key.valueType == ValueTypes.STRING)
            Some("\"\"")
          else if (getHigherType(key.cardinality) == HigherValueType.None && key.valueType == ValueTypes.BOOLEAN)
            Some("false")
          else if (getHigherType(key.cardinality) == HigherValueType.List)
            Some("List()")
          else if (getHigherType(key.cardinality) == HigherValueType.None)
            Some("null")
          else None
        val typ = getCompleteType(key)
        fieldDescriptions += ((camelCase(key.name), typ, optionalDefault))
      }
      for (containedNode <- nodeType.containedNodes) {
        val optionalDefault = containedNode.cardinality match {
          case Cardinality.List      => Some("List()")
          case Cardinality.ZeroOrOne => Some("None")
          case Cardinality.ISeq      => Some("IndexedSeq.empty")
          case Cardinality.One       => Some("null")
          case _                     => None
        }
        val typ = getCompleteType(containedNode)
        fieldDescriptions += ((containedNode.localName, typ, optionalDefault))
      }
      val defaultsVal = fieldDescriptions.reverse
        .map {case (name, typ, Some(default)) => s"var $name: $typ = $default"
              case (name, typ, None)          => s"var $name: $typ"}
        .mkString(", ")

      val valueMapImpl = {
        val putKeysImpl = keys
          .map { key: Property =>
            val memberName = camelCase(key.name)
            key.cardinality match {
              case Cardinality.One =>
                s"""  if ($memberName != null) { res += "${key.name}" -> $memberName }"""
              case Cardinality.ZeroOrOne =>
                s"""  if ($memberName != null && $memberName.isDefined) { res += "${key.name}" -> $memberName.get }"""
              case Cardinality.List | Cardinality.ISeq=>
                s"""  if ($memberName != null && $memberName.nonEmpty) { res += "${key.name}" -> $memberName }"""
            }
          }
          .mkString("\n")
      val putRefsImpl = nodeType.containedNodes.map { key =>
          val memberName = key.localName
          key.cardinality match {
            case Cardinality.One =>
              s"""  if ($memberName != null) { res += "$memberName" -> $memberName }"""
            case Cardinality.ZeroOrOne =>
              s"""  if ($memberName != null && $memberName.isDefined) { res += "$memberName" -> $memberName.get }"""
            case Cardinality.List | Cardinality.ISeq =>
              s"""  if ($memberName != null && $memberName.nonEmpty) { res += "$memberName" -> $memberName }"""
          }
        }
        .mkString("\n")


        s"""override def properties: Map[String, Any] = {
           |  var res = Map[String, Any]()
           |$putKeysImpl
           |$putRefsImpl
           |  res
           |}""".stripMargin
      }

      val builderSetters = fieldDescriptions
        .map {case (name, typ, _) => s"def ${name}(x : $typ) : New${nodeType.className}Builder = { result = result.copy($name = x); this }" }
        .mkString("\n")

      s"""
         |object New${nodeType.className}Builder {
         |  def apply() : New${nodeType.className}Builder = new New${nodeType.className}Builder()
         |}
         |
         |class New${nodeType.className}Builder extends NewNodeBuilder {
         |   var result : New${nodeType.className} = new New${nodeType.className}()
         |   private var _id : Long = -1L
         |   def id: Long = _id
         |   def id(x: Long): New${nodeType.className}Builder = { _id = x; this }
         |
         |   $builderSetters
         |
         |   def build : New${nodeType.className} = result
         |
         |   def canEqual(other: Any): Boolean = other.isInstanceOf[New${nodeType.className}Builder]
         |
         |   override def equals(other: Any): Boolean = other match {
         |      case that: New${nodeType.className}Builder => (that canEqual this) && _id == that._id
         |      case _ => false
         |   }
         |
         |   override def hashCode(): Int = {
         |      val state = Seq(_id)
         |      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
         |   }
         |
         |   override def toString = s"New${nodeType.className}Builder($${_id})"
         |}
         |
         |object New${nodeType.className}{
         |  def apply() : New${nodeType.className}Builder = New${nodeType.className}Builder()
         |}
         |
         |case class New${nodeType.className} private[nodes] ($defaultsVal) extends NewNode with ${nodeType.className}Base {
         |  override def label:String = "${nodeType.name}"
         |
         |  $valueMapImpl
         |}
         |""".stripMargin
    }


    val outfile = outputDir / nodesPackage.replaceAll("\\.", "/") / "NewNodes.scala"
    if (outfile.exists) outfile.delete()
    outfile.createFile()
    val src = schema.nodeTypes.map { nodeType =>
      generateNewNodeSource(nodeType, nodeType.properties)
    }.mkString("\n")
    outfile.write(s"""$staticHeader
                     |$src
                     |""".stripMargin)
  }
}

object CodeGen {
  case class ConstantContext(name: String, source: String, documentation: Option[String])
}
