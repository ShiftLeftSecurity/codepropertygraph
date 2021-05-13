import io.shiftleft.codepropertygraph.schema.CpgSchema
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.{Formats, JsonAST, NoTypeHints}
import overflowdb.schema.{AbstractNodeType, NodeBaseType}

object Schema2Json extends App {

  val schema = CpgSchema.instance

  implicit val formats: AnyRef with Formats =
    Serialization.formats(NoTypeHints)

  val json = nodeTypesAsJson

  val outFileName = "/tmp/schema.json"
  better.files
    .File(outFileName)
    .write(
      compact(render(json))
    )
  println(s"Schema written to: $outFileName")

  private def schemaName(nodeType: AbstractNodeType): String =
    nodeType.schemaInfo.definedIn.map(_.getDeclaringClass.getSimpleName).getOrElse("unknown")

  private def schemaIndex(nodeType: AbstractNodeType): Int =
    nodeType.schemaInfo.definedIn
      .map(_.getDeclaringClass.getDeclaredMethod("index").invoke(null).asInstanceOf[Int])
      .getOrElse(Int.MaxValue)

  private def nodeTypesAsJson: Seq[JsonAST.JObject] =
    (schema.nodeTypes ++ schema.nodeBaseTypes)
      .sortBy(x => (schemaIndex(x), x.name))
      .map { nodeType =>
        val baseTypeNames = nodeType.extendz.map(_.name)
        val allProperties = nodeType.properties.map(_.name)
        val inheritedProperties = nodeType.extendz.flatMap { base =>
          base.properties.map(_.name).map(x => (("baseType" -> base.name), ("name" -> x)))
        }

        val inheritedPropertyNames = inheritedProperties.map(_._2._2)
        val nonInheritedProperties = allProperties.filterNot(x => inheritedPropertyNames.contains(x))
        ("name" -> nodeType.name) ~
          ("comment" -> nodeType.comment) ~
          ("extends" -> baseTypeNames) ~
          ("inheritedProperties" -> inheritedProperties.map(x => x._1 ~ x._2)) ~
          ("properties" -> nonInheritedProperties) ~
          ("schema" -> schemaName(nodeType)) ~
          ("isAbstract" -> nodeType.isInstanceOf[NodeBaseType])
      }

}
