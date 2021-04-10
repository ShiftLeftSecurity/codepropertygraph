package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, SchemaBuilder}
import overflowdb.storage.ValueTypes

object Deprecated {
  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._

    val localName = builder
      .addEdgeProperty(
        name = "LOCAL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Local name of referenced CONTAINED node. This key is deprecated."
      )
      .protoId(6)

    val index = builder
      .addEdgeProperty(
        name = "INDEX",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment =
          "Index of referenced CONTAINED node (0 based) - used together with cardinality=list. This key is deprecated."
      )
      .protoId(8)

    val containsNode = builder
      .addEdgeType(
        name = "CONTAINS_NODE",
        comment = "Membership relation for a compound object. This edge is deprecated."
      )
      .protoId(9)
      .addProperties(localName, index)

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
