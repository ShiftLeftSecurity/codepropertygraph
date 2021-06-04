import io.shiftleft.codepropertygraph.schema.CpgSchema
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.{Formats, NoTypeHints}
import overflowdb.schema.{AbstractNodeType, NodeBaseType, NodeType, SchemaInfo}

object Schema2Json extends App {

  val schema = CpgSchema.instance
  val deprecatedSchemaName = "Deprecated"

  implicit val formats: AnyRef with Formats =
    Serialization.formats(NoTypeHints)

  val json = ("schemas" -> schemaSummary) ~ ("nodes" -> nodeTypesAsJson) ~ ("edges" -> edgeTypesAsJson) ~ ("properties" -> propertiesAsJson)

  val outFileName = "/tmp/schema.json"
  better.files
    .File(outFileName)
    .write(
      compact(render(json))
    )
  println(s"Schema written to: $outFileName")

  private def schemaName(nodeType: AbstractNodeType): String =
    schemaName(nodeType.schemaInfo)

  private def schemaName(schemaInfo: SchemaInfo) =
    schemaInfo.definedIn.map(_.getDeclaringClass.getSimpleName).getOrElse("unknown")

  private def schemaIndex(nodeType: AbstractNodeType): Int =
    schemaIndex(nodeType.schemaInfo)

  private def schemaIndex(schemaInfo: SchemaInfo) =
    schemaInfo.definedIn
      .map(_.getDeclaringClass.getDeclaredMethod("index").invoke(null).asInstanceOf[Int])
      .getOrElse(Int.MaxValue)

  private def schemaSummary = {
    (schema.nodeTypes.map(_.schemaInfo) ++ schema.edgeTypes.map(_.schemaInfo))
      .sortBy(schemaIndex)
      .flatMap(_.definedIn)
      .distinct
      .filter { _.getDeclaringClass.getSimpleName != deprecatedSchemaName }
      .map { info =>
        val name = info.getDeclaringClass.getSimpleName
        val description = info.getDeclaringClass.getDeclaredMethod("description").invoke(null).asInstanceOf[String]
        val providedByFrontend =
          info.getDeclaringClass.getDeclaredMethod("providedByFrontend").invoke(null).asInstanceOf[Boolean]
        ("name" -> name) ~
          ("description" -> description) ~
          ("providedByFrontend" -> providedByFrontend)
      }
  }

  private def nodeTypesAsJson = {
    (schema.nodeTypes ++ schema.nodeBaseTypes)
      .sortBy(x => (schemaIndex(x), x.name))
      .flatMap { nodeType =>
        val baseTypeNames = nodeType.extendz.map(_.name)
        val allPropertyNames = nodeType.properties.map(_.name)
        val cardinalities = nodeType.properties.map(_.cardinality.name)
        val schName = schemaName(nodeType)

        if (schName == deprecatedSchemaName) {
          None
        } else {
          val inheritedProperties = nodeType.extendz
            .flatMap { base =>
              base.properties.map(_.name).map(x => x -> base.name)
            }
            .toMap
            .toList
            .map(x => (("baseType" -> x._2), ("name" -> x._1)))

          val inheritedPropertyNames = inheritedProperties.map(_._2._2)
          val nonInheritedProperties = allPropertyNames.filterNot(x => inheritedPropertyNames.contains(x))

          val containedNodes = nodeType match {
            case nt: NodeType =>
              nt.containedNodes.map { n =>
                Map("name" -> n.localName, "type" -> n.nodeType.name, "cardinality" -> n.cardinality.name)
              }
            case _ => List()
          }
          val json = ("name" -> nodeType.name) ~
            ("comment" -> nodeType.comment) ~
            ("extends" -> baseTypeNames) ~
            ("allProperties" -> allPropertyNames) ~
            ("cardinalities" -> cardinalities) ~
            ("inheritedProperties" -> inheritedProperties.map(x => x._1 ~ x._2)) ~
            ("properties" -> nonInheritedProperties) ~
            ("schema" -> schName) ~
            ("schemaIndex" -> schemaIndex(nodeType)) ~
            ("isAbstract" -> nodeType.isInstanceOf[NodeBaseType]) ~
            ("containedNodes" -> containedNodes)
          Some(json)
        }
      }
  }

  private def edgeTypesAsJson = {
    schema.edgeTypes.sortBy(_.name).flatMap { edge =>
      val schName = schemaName(edge.schemaInfo)
      if (schName == deprecatedSchemaName) {
        None
      } else {
        Some(
          ("name" -> edge.name) ~
            ("comment" -> edge.comment) ~
            ("schema" -> schName)
        )
      }
    }
  }

  private def propertiesAsJson = {
    schema.properties
      .sortBy(_.name)
      .flatMap { prop =>
        val schName = schemaName(prop.schemaInfo)
        if (schName == deprecatedSchemaName) {
          None
        } else {
          Some(
            ("name" -> prop.name) ~
              ("comment" -> prop.comment) ~
              ("schema" -> schName)
          )
        }
      }
  }

}
