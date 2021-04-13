package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, SchemaBuilder}
import overflowdb.storage.ValueTypes

object ProtoSerialize {

  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._

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
