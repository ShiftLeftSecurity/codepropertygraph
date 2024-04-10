import io.shiftleft.codepropertygraph.schema.CpgSchema
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.{Formats, NoTypeHints}
import overflowdb.schema.Property.Cardinality
import overflowdb.schema.{AbstractNodeType, NodeBaseType, NodeType, Property, SchemaInfo}

object Schema2Json extends App {

  val schema = CpgSchema.instance

  implicit val formats: AnyRef & Formats =
    Serialization.formats(NoTypeHints)

  val json =
    ("schemas" -> schemaSummary) ~ ("nodes" -> nodeTypesAsJson) ~ ("edges" -> edgeTypesAsJson) ~ ("properties" -> propertiesAsJson)

  val outFileName = "/tmp/schema.json"
  better.files
    .File(outFileName)
    .write(compact(render(json)))
  println(s"Schema written to: $outFileName")

  private def schemaName(nodeType: AbstractNodeType): String =
    schemaName(nodeType.schemaInfo)

  private def schemaName(schemaInfo: SchemaInfo): String =
    schemaInfo.definedIn.map(_.getDeclaringClass.getSimpleName).getOrElse("unknown")

  private def schemaIndex(nodeType: AbstractNodeType): Int =
    schemaIndex(nodeType.schemaInfo)

  private def schemaIndex(schemaInfo: SchemaInfo) =
    schemaInfo.definedIn
      .map(_.getDeclaringClass.getDeclaredMethod("docIndex").invoke(null).asInstanceOf[Int])
      .getOrElse(Int.MaxValue)

  private def isSchemaHidden(schemaInfo: SchemaInfo) =
    schemaName(schemaInfo) == "Hidden"

  private def schemaSummary = {
    (schema.nodeTypes.map(_.schemaInfo) ++ schema.edgeTypes.map(_.schemaInfo))
      .filterNot(isSchemaHidden)
      .sortBy(schemaIndex)
      .flatMap(_.definedIn)
      .distinct
      .map { info =>
        val name        = info.getDeclaringClass.getSimpleName
        val description = info.getDeclaringClass.getDeclaredMethod("description").invoke(null).asInstanceOf[String]
        val providedByFrontend =
          info.getDeclaringClass.getDeclaredMethod("providedByFrontend").invoke(null).asInstanceOf[Boolean]
        ("name"                 -> name) ~
          ("description"        -> description) ~
          ("providedByFrontend" -> providedByFrontend)
      }
  }

  private def nodeTypesAsJson = {
    (schema.nodeTypes ++ schema.nodeBaseTypes)
      .filterNot(x => isSchemaHidden(x.schemaInfo))
      .sortBy(x => (schemaIndex(x), x.name))
      .flatMap { nodeType =>
        val baseTypeNames    = nodeType.extendz.map(_.name)
        val allPropertyNames = nodeType.properties.filterNot(x => isSchemaHidden(x.schemaInfo)).map(_.name)
        val cardinalities =
          nodeType.properties.filterNot(x => isSchemaHidden(x.schemaInfo)).map(p => name(p.cardinality))
        val schName = schemaName(nodeType)

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
          case nt: NodeType if !isSchemaHidden(nt.schemaInfo) =>
            nt.containedNodes.map { n =>
              Map("name" -> n.localName, "type" -> n.nodeType.name, "cardinality" -> name(n.cardinality))
            }
          case _ => List()
        }
        val json = ("name" -> nodeType.name) ~
          ("comment"             -> nodeType.comment) ~
          ("extends"             -> baseTypeNames) ~
          ("allProperties"       -> allPropertyNames) ~
          ("cardinalities"       -> cardinalities) ~
          ("inheritedProperties" -> inheritedProperties.map(x => x._1 ~ x._2)) ~
          ("properties"          -> nonInheritedProperties) ~
          ("schema"              -> schName) ~
          ("schemaIndex"         -> schemaIndex(nodeType)) ~
          ("isAbstract"          -> nodeType.isInstanceOf[NodeBaseType]) ~
          ("containedNodes"      -> containedNodes)
        Some(json)
      }
  }

  private def edgeTypesAsJson = {
    schema.edgeTypes
      .sortBy(_.name)
      .filterNot(x => isSchemaHidden(x.schemaInfo))
      .flatMap { edge =>
        val schName = schemaName(edge.schemaInfo)
        Some(
          ("name"      -> edge.name) ~
            ("comment" -> edge.comment) ~
            ("schema"  -> schName)
        )
      }
  }

  private def propertiesAsJson = {
    schema.properties
      .sortBy(_.name)
      .filterNot(x => isSchemaHidden(x.schemaInfo))
      .flatMap { prop =>
        val schName = schemaName(prop.schemaInfo)
        Some(
          ("name"      -> prop.name) ~
            ("comment" -> prop.comment) ~
            ("schema"  -> schName)
        )
      }
  }

  private def name(cardinality: Property.Cardinality): String =
    cardinality match {
      case Cardinality.ZeroOrOne => "zeroOrOne"
      case Cardinality.List      => "list"
      case Cardinality.One(_)    => "one"
    }

}
