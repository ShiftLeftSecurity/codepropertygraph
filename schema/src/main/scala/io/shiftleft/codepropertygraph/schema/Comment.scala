package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._

object Comment extends SchemaBase {

  def index: Int = 12
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder, ast: Ast.Schema, fs: FileSystem.Schema) =
    new Schema(builder, ast, fs)

  class Schema(builder: SchemaBuilder, astSchema: Ast.Schema, fs: FileSystem.Schema) {
    import astSchema._
    import fs._
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)

// node types
    val comment: NodeType = builder
      .addNodeType(
        name = "COMMENT",
        comment = "A source code comment"
      )
      .protoId(511)
      .addProperties(filename)
      .extendz(astNode)

// node relations
    comment
      .addOutEdge(edge = sourceFile, inNode = comment)

    file
      .addOutEdge(edge = ast, inNode = comment)

  }

}
