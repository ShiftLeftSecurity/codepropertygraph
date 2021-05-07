import io.shiftleft.codepropertygraph.schema.CpgSchema
import org.json4s.{Formats, NoTypeHints}
import org.json4s.native.Serialization
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

object Schema2Json extends App {

  val schema = CpgSchema.instance

  implicit val formats: AnyRef with Formats =
      Serialization.formats(NoTypeHints)

  val json = schema.nodeTypes.map{ nodeType =>
    ("name" -> nodeType.name) ~
      ("comment" -> nodeType.comment) ~
      ("properties" -> nodeType.properties.map{ prop => prop.name } )
  }

  val outFileName = "/tmp/schema.json"
    better.files
      .File(outFileName)
      .write(
        compact(render(json))
      )
    println(s"Schema written to: $outFileName")
}
