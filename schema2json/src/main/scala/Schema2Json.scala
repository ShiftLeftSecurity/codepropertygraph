import io.shiftleft.codepropertygraph.schema.CpgSchema
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.{Formats, NoTypeHints}

object Schema2Json extends App {

  val schema = CpgSchema.instance

  implicit val formats: AnyRef with Formats =
    Serialization.formats(NoTypeHints)

  val json = schema.nodeTypes.map { nodeType =>
    val schemaName = nodeType.schemaInfo.definedIn.map(_.getDeclaringClass.getSimpleName).getOrElse("unknown")

    val baseTypeNames = nodeType.extendz.map(_.name)
    val allProperties = nodeType.properties.map { prop =>
      prop.name
    }
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
      ("schema" -> schemaName)
  }

  val outFileName = "/tmp/schema.json"
  better.files
    .File(outFileName)
    .write(
      compact(render(json))
    )
  println(s"Schema written to: $outFileName")
}
