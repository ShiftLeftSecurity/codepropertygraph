package overflowdb.codegen

import overflowdb.schema._
import overflowdb.storage.ValueTypes

import scala.annotation.tailrec

// TODO drop
object DefaultNodeTypes {
  /** root type for all nodes */
  val AbstractNodeName = "ABSTRACT_NODE"
  val AbstractNodeClassname = "AbstractNode"
}

// TODO drop
object DefaultEdgeTypes {
  val ContainsNode = "CONTAINS_NODE"
}

object Helpers {

  /* surrounds input with `"` */
  def quoted(strings: Iterable[String]): Iterable[String] =
    strings.map(string => s""""$string"""")

  def stringToOption(s: String): Option[String] = s.trim match {
    case "" => None
    case nonEmptyString => Some(nonEmptyString)
  }

  def typeFor(valueType: ValueTypes): String = valueType match {
    case ValueTypes.BOOLEAN => "java.lang.Boolean"
    case ValueTypes.STRING => "String"
    case ValueTypes.BYTE => "java.lang.Byte"
    case ValueTypes.SHORT => "java.lang.Short"
    case ValueTypes.INTEGER => "java.lang.Integer"
    case ValueTypes.LONG => "java.lang.Long"
    case ValueTypes.FLOAT => "java.lang.Float"
    case ValueTypes.DOUBLE => "java.lang.Double"
    case ValueTypes.LIST => "java.lang.List[_]"
    case ValueTypes.NODE_REF => "overflowdb.NodeRef[_]"
    case ValueTypes.UNKNOWN => "java.lang.Object"
    case ValueTypes.CHARACTER => "java.lang.Character"
  }

  def isNodeBaseTrait(baseTraits: List[NodeBaseType], nodeName: String): Boolean =
    nodeName == DefaultNodeTypes.AbstractNodeName || baseTraits.map(_.name).contains(nodeName)

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

  /**
   * Converts from camelCase to snake_case
   * e.g.: camelCase => camel_case
   *
   * copy pasted from https://gist.github.com/sidharthkuruvila/3154845#gistcomment-2622928
   */
  def snakeCase(camelCase: String): String = {
    @tailrec
    def go(accDone: List[Char], acc: List[Char]): List[Char] = acc match {
      case Nil => accDone
      case a::b::c::tail if a.isUpper && b.isUpper && c.isLower => go(accDone ++ List(a, '_', b, c), tail)
      case a::b::tail if a.isLower && b.isUpper => go(accDone ++ List(a, '_', b), tail)
      case a::tail => go(accDone :+ a, tail)
    }
    go(Nil, camelCase.toList).mkString.toLowerCase
  }

  def singularize(str: String): String = {
    if (str.endsWith("ies")) {
      // e.g. Strategies -> Strategy
      s"${str.dropRight(3)}y"
    } else {
      // e.g. Types -> Type
      str.dropRight(1)
    }
  }

  def getHigherType(cardinality: Cardinality): HigherValueType.Value =
    cardinality match {
      case Cardinality.One       => HigherValueType.None
      case Cardinality.ZeroOrOne => HigherValueType.Option
      case Cardinality.List      => HigherValueType.List
      case  Cardinality.ISeq => ???
    }

  def getCompleteType(property: Property): String = {
    val valueType = typeFor(property.valueType)
    getHigherType(property.cardinality) match {
      case HigherValueType.None   => valueType
      case HigherValueType.Option => s"Option[$valueType]"
      case HigherValueType.List   => s"List[$valueType]"
    }
  }

  def getCompleteType(containedNode: ContainedNode): String = {
    val tpe = if (containedNode.nodeType.className != DefaultNodeTypes.AbstractNodeClassname) {
      containedNode.nodeType.className + "Base"
    } else {
      containedNode.nodeType.className
    }

    containedNode.cardinality match {
      case Cardinality.ZeroOrOne => s"Option[$tpe]"
      case Cardinality.One       => tpe
      case Cardinality.List      => s"Seq[$tpe]"
      case Cardinality.ISeq => s"IndexedSeq[$tpe]"
    }
  }

  def propertyBasedFields(properties: Seq[Property]): String =
    properties.map { property =>
      val name = camelCase(property.name)
      val tpe = getCompleteType(property)
      val unsetValue = propertyUnsetValue(property.cardinality)

      s"""private var _$name: $tpe = $unsetValue
         |def $name: $tpe = _$name""".stripMargin
    }.mkString("\n\n")

  def propertyUnsetValue(cardinality: Cardinality): String =
    getHigherType(cardinality) match {
      case HigherValueType.None => "null"
      case HigherValueType.Option => "None"
      case HigherValueType.List => "Nil"
    }

  def propertyKeyDef(name: String, baseType: String, cardinality: Cardinality) = {
    val completeType = cardinality match {
      case Cardinality.One       => baseType
      case Cardinality.ZeroOrOne => baseType
      case Cardinality.List      => s"Seq[$baseType]"
      case Cardinality.ISeq=> s"IndexedSeq[$baseType]"
    }
    s"""val ${camelCaseCaps(name)} = new PropertyKey[$completeType]("$name") """
  }

  val propertyErrorRegisterImpl =
    s"""object PropertyErrorRegister {
       |  private var errorMap = Set[(Class[_], String)]()
       |  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)
       |
       |  def logPropertyErrorIfFirst(clazz: Class[_], propertyName: String): Unit = {
       |    if (!errorMap.contains((clazz, propertyName))) {
       |      logger.warn("Property " + propertyName + " is deprecated for " + clazz.getName + ".")
       |      errorMap += ((clazz, propertyName))
       |    }
       |  }
       |}
       |""".stripMargin

  /** obtained from repl via
   * {{{
   * :power
   * nme.keywords
   * }}}
   */
  val scalaReservedKeywords = Set(
    "abstract", ">:", "if", ".", "catch", "protected", "final", "super", "while", "true", "val", "do", "throw",
    "<-", "package", "_", "macro", "@", "object", "false", "this", "then", "var", "trait", "with", "def", "else",
    "class", "type", "#", "lazy", "null", "=", "<:", "override", "=>", "private", "sealed", "finally", "new",
    "implicit", "extends", "for", "return", "case", "import", "forSome", ":", "yield", "try", "match", "<%")

  def escapeIfKeyword(value: String) =
    if (scalaReservedKeywords.contains(value)) s"`$value`"
    else value

  def deriveCommonSuperType(nodeTypes: Set[AbstractNodeType]): Option[AbstractNodeType] = {
    if (nodeTypes.size == 1) {
      Some(nodeTypes.head)
    } else if (nodeTypes.size > 1) {
      /** Trying to find common supertype. This is nontrivial and we're probably missing a few cases.
       * Trying to at least keep it deterministic...
       * Idea: take one nodeType and check if it's type or any of it's supertypes are declared in *all* other nodeTypes
       * */

      val sorted = nodeTypes.toSeq.sortBy(_.className)

      val (first, otherNodes) = (sorted.head, sorted.tail)
      allTypes(first).find { candidate =>
        otherNodes.forall { otherNode =>
          allTypes(otherNode).contains(candidate)
        }
      }
    } else {
      None
    }
  }

  def allTypes(node: AbstractNodeType): Seq[AbstractNodeType] =
    node +: node.extendzRecursively

  def fullScalaType(neighborNode: AbstractNodeType, cardinality: Cardinality): String = {
    val neighborNodeClass = neighborNode.className
    cardinality match {
      case Cardinality.List => s"Traversal[$neighborNodeClass]"
      case Cardinality.ZeroOrOne => s"Option[$neighborNodeClass]"
      case Cardinality.One => s"$neighborNodeClass"
      case Cardinality.ISeq => ???
    }
  }

}
