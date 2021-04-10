package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, SchemaBuilder}
import overflowdb.storage.ValueTypes

object Deprecated {
  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._

    val methodInstFullName = builder
      .addNodeProperty(
        name = "METHOD_INST_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Deprecated"
      )
      .protoId(55)

    // node types
    callNode
      .addProperties(methodInstFullName, typeFullName)

    methodRef.addProperties(methodInstFullName)

  }

}
