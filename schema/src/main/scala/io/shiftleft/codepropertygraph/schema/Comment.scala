package io.shiftleft.codepropertygraph.schema

import flatgraph.schema._

object Comment extends SchemaBase {
  def docIndex                    = 12
  override def providedByFrontend = true

  override def description = ""

  def apply(builder: SchemaBuilder, base: Base.Schema, ast: Ast.Schema, fs: FileSystem.Schema) =
    new Schema(builder, base, ast, fs)

  class Schema(builder: SchemaBuilder, baseSchema: Base.Schema, astSchema: Ast.Schema, fs: FileSystem.Schema) {
    import astSchema._
    import fs._
    import baseSchema._
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)

    val comment: NodeType = builder
      .addNodeType(name = "COMMENT", comment = "A source code comment")
      .protoId(ProtoIds.Comment)
      .addProperties(filename)
      .extendz(astNode)
      .primaryKey(code)

    comment.addOutEdge(edge = sourceFile, inNode = comment, stepNameOut = "file")
    file.addOutEdge(edge = ast, inNode = comment, stepNameOut = "comment")
  }

}
