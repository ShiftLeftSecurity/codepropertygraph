package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import overflowdb.schema.Property.ValueType
import overflowdb.schema._

object Base extends SchemaBase {

  def index: Int = Int.MaxValue
  override def providedByFrontend: Boolean = true
  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder) = new Schema(builder)

  class Schema(builder: SchemaBuilder) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    val version = builder
      .addProperty(
        name = "VERSION",
        valueType = ValueType.String,
        comment = """A version, given as a string. Used, for example, in the META_DATA node to
                    |indicate which version of the CPG spec this CPG conforms to
                    |""".stripMargin
      )
      .mandatory(PropertyDefaults.String)
      .protoId(13)

    val hash = builder
      .addProperty(
        name = "HASH",
        valueType = ValueType.String,
        comment = """This property contains a hash value in the form of a string.
                    |Hashes can be used to summarize data, e.g., to summarize the
                    |contents of source files or sub graphs. Such summaries are useful
                    |to determine whether code has already been analyzed in incremental
                    |analysis pipelines. This property is optional to allow its calculation
                    |to be deferred or skipped if the hash is not needed.
                    |""".stripMargin
      )
      .protoId(120)

    val code = builder
      .addProperty(
        name = "CODE",
        valueType = ValueType.String,
        comment = "This field holds the code snippet that the node represents."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(21)

    val isExternal = builder
      .addProperty(
        name = "IS_EXTERNAL",
        valueType = ValueType.Boolean,
        comment = """Indicates that the construct (METHOD or TYPE_DECL) is external, that is,
                    |it is referenced but not defined in the code (applies both to insular
                    |parsing and to library functions where we have header files only)
                    |""".stripMargin
      )
      .mandatory(false)
      .protoId(7)

    val name = builder
      .addProperty(
        name = "NAME",
        valueType = ValueType.String,
        comment = "Name of represented object, e.g., method name (e.g. \"run\")"
      )
      .mandatory(PropertyDefaults.String)
      .protoId(5)

    val fullName = builder
      .addProperty(
        name = "FULL_NAME",
        valueType = ValueType.String,
        comment = """This is the fully-qualified name of an entity, e.g., the fully-qualified
                    |name of a method or type. The details of what constitutes a fully-qualified
                    |name are language specific. This field SHOULD be human readable.
                    |""".stripMargin
      )
      .mandatory(PropertyDefaults.String)
      .protoId(6)

    val parserTypeName = builder
      .addProperty(
        name = "PARSER_TYPE_NAME",
        valueType = ValueType.String,
        comment = "AST node type name emitted by parser."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(3)

    val value = builder
      .addProperty(
        name = "VALUE",
        valueType = ValueType.String,
        comment = "This property denotes a string value as used in a key-value pair."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(8)

    // The following fields are used to create edges between nodes in later processing stages.

    val astParentType = builder
      .addProperty(
        name = "AST_PARENT_TYPE",
        valueType = ValueType.String,
        comment = """The type of the AST parent. Since this is only used in some parts of the graph,
            |the list does not include all possible parents by intention.
            |Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK.
            |""".stripMargin
      )
      .mandatory(PropertyDefaults.String)
      .protoId(56)

    val astParentFullName = builder
      .addProperty(
        name = "AST_PARENT_FULL_NAME",
        valueType = ValueType.String,
        comment = "This field holds the FULL_NAME of the AST parent of an entity."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(57)
    // node base types

    val declaration = builder
      .addNodeBaseType(
        name = "DECLARATION",
        comment = "This is the base node class for all declarations."
      )
      .addProperties(name)

    // Edge types

    val ref = builder
      .addEdgeType(
        name = "REF",
        comment = """This edge indicates that the source node is an identifier that denotes
            |access to the destination node. For example, an identifier may reference
            |a local variable.
            |""".stripMargin
      )
      .protoId(10)

  }

}
