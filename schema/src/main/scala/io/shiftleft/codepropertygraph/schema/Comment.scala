package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._

object Comment extends SchemaBase {

  override def index: Int = 6
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema, enhancements: Enhancements.Schema) =
    new Schema(builder, base, enhancements)

  class Schema(builder: SchemaBuilder, base: Base.Schema, enhancements: Enhancements.Schema) {
    import base._
    import enhancements._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

// node types
    val comment: NodeType = builder
      .addNodeType(
        name = "COMMENT",
        comment = "A source code comment"
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
