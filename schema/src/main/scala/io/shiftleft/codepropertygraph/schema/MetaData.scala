package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object MetaData {

  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._
    implicit val schemaInfo = SchemaInfo.forClass(getClass)

    val overlays = builder
      .addProperty(
        name = "OVERLAYS",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.List,
        comment = "Names of overlays applied to this graph, in order of application"
      )
      .protoId(118)

    val language = builder
      .addProperty(
        name = "LANGUAGE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "the CPG language frontend that generated this CPG"
      )
      .protoId(19)

    val metaData: NodeType = builder
      .addNodeType(
        name = "META_DATA",
        comment = """Node to save meta data about the graph on its properties.
                    |Exactly one node of this type per graph""".stripMargin
      )
      .protoId(39)
      .addProperties(language, version, overlays, hash)

  }

}
