package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import flatgraph.schema.Property.ValueType
import flatgraph.schema.{Constant, NodeType, SchemaBuilder, SchemaInfo}

object MetaData extends SchemaBase {

  def docIndex: Int                        = 2
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
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)

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

    val root = builder
      .addProperty(
        name = "ROOT",
        valueType = ValueType.String,
        comment = "The path to the root directory of the source/binary this CPG is generated from."
      )
      .protoId(1199)
      .mandatory(PropertyDefaults.String)

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
      .addProperties(language, version, overlays, hash, root)

    val languages = builder.addConstants(
      category = "Languages",
      Constant(name = "JAVA", value = "JAVA", valueType = ValueType.String, comment = "").protoId(1),
      Constant(name = "JAVASCRIPT", value = "JAVASCRIPT", valueType = ValueType.String, comment = "").protoId(2),
      Constant(name = "GOLANG", value = "GOLANG", valueType = ValueType.String, comment = "").protoId(3),
      Constant(name = "CSHARP", value = "CSHARP", valueType = ValueType.String, comment = "").protoId(4),
      Constant(name = "C", value = "C", valueType = ValueType.String, comment = "").protoId(5),
      Constant(name = "PYTHON", value = "PYTHON", valueType = ValueType.String, comment = "").protoId(6),
      Constant(name = "LLVM", value = "LLVM", valueType = ValueType.String, comment = "").protoId(7),
      Constant(name = "PHP", value = "PHP", valueType = ValueType.String, comment = "").protoId(8),
      Constant(name = "FUZZY_TEST_LANG", value = "FUZZY_TEST_LANG", valueType = ValueType.String, comment = "")
        .protoId(9),
      Constant(
        name = "GHIDRA",
        value = "GHIDRA",
        valueType = ValueType.String,
        comment = "generic reverse engineering framework"
      ).protoId(10),
      Constant(name = "KOTLIN", value = "KOTLIN", valueType = ValueType.String, comment = "").protoId(11),
      Constant(
        name = "NEWC",
        value = "NEWC",
        valueType = ValueType.String,
        comment = "Eclipse CDT based parser for C/C++"
      ).protoId(12),
      Constant(
        name = "JAVASRC",
        value = "JAVASRC",
        valueType = ValueType.String,
        comment = "Source-based front-end for Java"
      ).protoId(13),
      Constant(
        name = "PYTHONSRC",
        value = "PYTHONSRC",
        valueType = ValueType.String,
        comment = "Source-based front-end for Python"
      ).protoId(14),
      Constant(
        "JSSRC",
        value = "JSSRC",
        valueType = ValueType.String,
        comment = "Source-based JS frontend based on Babel"
      ).protoId(15),
      // Removed protoId 16. Used to be "Solidity".
      Constant(
        name = "RUBYSRC",
        value = "RUBYSRC",
        valueType = ValueType.String,
        comment = "Source-based frontend for Ruby"
      ).protoId(17),
      Constant(
        name = "SWIFTSRC",
        value = "SWIFTSRC",
        valueType = ValueType.String,
        comment = "Source-based frontend for Swift"
      ).protoId(18),
      Constant(
        name = "CSHARPSRC",
        value = "CSHARPSRC",
        valueType = ValueType.String,
        comment = "Source-based frontend for C# and .NET"
      ).protoId(19)
    )
  }
}
