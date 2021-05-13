package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._

object SourceSpecific extends SchemaBase {

  override def index: Int = 6

  override def description: String =
    """
      |
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

// node types
    val comment: NodeType = builder
      .addNodeType(
        name = "COMMENT",
        comment = "A comment"
      )
      .protoId(511)
      .addProperties(lineNumber, code, filename)

// node relations
    comment
      .addOutEdge(edge = sourceFile, inNode = comment)

    file
      .addOutEdge(edge = ast, inNode = comment)
// constants

  }

}
