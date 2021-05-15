package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._

object Comment extends SchemaBase {

  def index: Int = 6
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            common: CommonProperties.Schema,
            ast: Ast.Schema,
            fs : FileSystem.Schema) =
    new Schema(builder, common, ast, fs)

  class Schema(builder: SchemaBuilder,
               common: CommonProperties.Schema,
               astSchema: Ast.Schema,
               fs : FileSystem.Schema) {
    import common._
    import astSchema._
    import fs._
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

  }

}
