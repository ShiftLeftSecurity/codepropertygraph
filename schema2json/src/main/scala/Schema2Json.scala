import io.shiftleft.codepropertygraph.schema.CpgSchema

object Schema2Json extends App {

  val schema = CpgSchema.instance
  schema.nodeTypes.foreach(println)

}
