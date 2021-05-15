package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object ProtoSerialize extends SchemaBase {

  override def index: Int = Int.MaxValue

  override def description: String =
    """
      |
      |""".stripMargin

  def apply(builder: SchemaBuilder, ast: Ast.Schema) = new Schema(builder, ast)

  class Schema(builder: SchemaBuilder, ast: Ast.Schema) {

    import ast._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    val containedRef = builder
      .addProperty(
        name = "CONTAINED_REF",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "References to other nodes. This is not a real property; it exists here for the sake of proto serialization only. valueType and cardinality are meaningless."
      )
      .protoId(2007161)

    unknown.addProperty(containedRef)
  }

}
