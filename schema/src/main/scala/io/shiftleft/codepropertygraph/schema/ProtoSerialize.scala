package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import flatgraph.schema.Property.ValueType
import flatgraph.schema.{SchemaBuilder, SchemaInfo}

object ProtoSerialize extends SchemaBase {

  override def docIndex: Int = Int.MaxValue

  override def description: String =
    """
      |
      |""".stripMargin

  def apply(builder: SchemaBuilder, ast: Ast.Schema) = new Schema(builder, ast)

  class Schema(builder: SchemaBuilder, ast: Ast.Schema) {

    import ast._
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)

    val containedRef = builder
      .addProperty(
        name = "CONTAINED_REF",
        valueType = ValueType.String,
        comment =
          "References to other nodes. This is not a real property; it exists here for the sake of proto serialization only. valueType and cardinality are meaningless."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(2007161)

    unknown.addProperty(containedRef)
  }

}
