package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, SchemaBuilder}
import overflowdb.storage.ValueTypes

object ProtoSerialize {

  def apply(builder: SchemaBuilder) = new Schema(builder)

  class Schema(builder: SchemaBuilder) {

    val containedRef = builder
      .addProperty(
        name = "CONTAINED_REF",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "References to other nodes. This is not a real property; it exists here for the sake of proto serialization only. valueType and cardinality are meaningless."
      )
      .protoId(2007161)
  }

}
