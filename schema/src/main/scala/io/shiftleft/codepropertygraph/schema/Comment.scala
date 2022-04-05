package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._

object Comment extends SchemaBase {
  def docIndex                    = 12
  override def providedByFrontend = true

  override def description = ""

  def apply(builder: SchemaBuilder, ast: Ast.Schema, fs: FileSystem.Schema) =
    new Schema(builder, ast, fs)

  class Schema(builder: SchemaBuilder, astSchema: Ast.Schema, fs: FileSystem.Schema) {
    import astSchema._
    import fs._
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)

    val comment: NodeType = builder
      .addNodeType(name = "COMMENT", comment = "A source code comment")
      .protoId(511)
      .addProperties(filename)
      .extendz(astNode)

    comment.addOutEdge(edge = sourceFile, inNode = comment, stepNameOut = "file")
    file.addOutEdge(edge = ast, inNode = comment, stepNameOut = "comment")
  }

}
