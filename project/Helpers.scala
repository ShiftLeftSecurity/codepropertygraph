package overflowdb.codegen

object Helpers {

  def isNodeBaseTrait(baseTraits: List[NodeBaseTrait], nodeName: String): Boolean =
    nodeName == DefaultNodeTypes.Node || baseTraits.map(_.name).contains(nodeName)

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
    val tpe = if (containedNode.nodeType != DefaultNodeTypes.Node) {
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
}
