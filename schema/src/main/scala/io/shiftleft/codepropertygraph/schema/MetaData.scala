package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, Constant, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object MetaData extends SchemaBase {

  def index: Int = 1
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      |The MetaData Layer is provided by the frontend and may be modified
      |modified by passes.
      |""".stripMargin

  def apply(builder: SchemaBuilder, common: CommonProperties.Schema) = new Schema(builder, common)

  class Schema(builder: SchemaBuilder, common: CommonProperties.Schema) {
    import common._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

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

    val languages = builder.addConstants(
      category = "Languages",
      Constant(name = "JAVA", value = "JAVA", valueType = ValueTypes.STRING, comment = "").protoId(1),
      Constant(name = "JAVASCRIPT", value = "JAVASCRIPT", valueType = ValueTypes.STRING, comment = "").protoId(2),
      Constant(name = "GOLANG", value = "GOLANG", valueType = ValueTypes.STRING, comment = "").protoId(3),
      Constant(name = "CSHARP", value = "CSHARP", valueType = ValueTypes.STRING, comment = "").protoId(4),
      Constant(name = "C", value = "C", valueType = ValueTypes.STRING, comment = "").protoId(5),
      Constant(name = "PYTHON", value = "PYTHON", valueType = ValueTypes.STRING, comment = "").protoId(6),
      Constant(name = "LLVM", value = "LLVM", valueType = ValueTypes.STRING, comment = "").protoId(7),
      Constant(name = "PHP", value = "PHP", valueType = ValueTypes.STRING, comment = "").protoId(8),
      Constant(name = "FUZZY_TEST_LANG", value = "FUZZY_TEST_LANG", valueType = ValueTypes.STRING, comment = "")
        .protoId(9),
    )

  }

}
