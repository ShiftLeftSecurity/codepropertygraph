package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import overflowdb.schema.Property.ValueType
import overflowdb.schema.{Constant, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object MetaData extends SchemaBase {

  def index: Int = 2
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      |The Meta Data Layer contains information about CPG creation. In particular,
      |it indicates which language frontend generated the CPG and which overlays
      |have been applied. The layer consists of a single node - the Meta Data node -
      |and language frontends MUST create this node. Overlay creators MUST edit
      |this node to indicate that a layer has been successfully applied in all
      |cases where applying the layer more than once is prohibitive.
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    val overlays = builder
      .addProperty(
        name = "OVERLAYS",
        valueType = ValueType.String,
        comment = """The field contains the names of the overlays applied to this CPG, in order of their
            |application. Names are free-form strings, that is, this specification does not
            |dictate them but rather requires tool producers and consumers to communicate them
            |between each other.
            |""".stripMargin
      )
      .asList()
      .protoId(118)

    val language = builder
      .addProperty(
        name = "LANGUAGE",
        valueType = ValueType.String,
        comment = """This field indicates which CPG language frontend generated the CPG.
            |Frontend developers may freely choose a value that describes their frontend
            |so long as it is not used by an existing frontend. Reserved values are to date:
            |C, LLVM, GHIDRA, PHP.
            |""".stripMargin
      )
      .mandatory(PropertyDefaults.String)
      .protoId(19)

    val metaData: NodeType = builder
      .addNodeType(
        name = "META_DATA",
        comment = """
                    |This node contains the CPG meta data. Exactly one node of this type
                    |MUST exist per CPG. The `HASH` property MAY contain a hash value calculated
                    |over the source files this CPG was generated from. The `VERSION` MUST be
                    |set to the version of the specification ("1.1"). The language field indicates
                    |which language frontend was used to generate the CPG and the list property
                    |`OVERLAYS` specifies which overlays have been applied to the CPG.
                    | """.stripMargin
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
      Constant(name = "GHIDRA",
               value = "GHIDRA",
               valueType = ValueTypes.STRING,
               comment = "generic reverse engineering framework").protoId(10),
    )

  }

}
