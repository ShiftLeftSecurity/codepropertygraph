package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.schema.Property.ValueType

object FileSystem extends SchemaBase {

  def index: Int = 3
  override def description: String =
    """
      |CPGs are created from sets of files and the File System Layer describes the layout of
      |these files, that is, it provides information about source files
      |and shared objects for source-based and machine-code-based frontends
      |respectively. The purpose of including this information in the CPG
      |is to allow nodes of the graph to be mapped back to file system locations.
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema) =
    new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import base._

    val filename = builder
      .addProperty(
        name = "FILENAME",
        valueType = ValueType.String,
        cardinality = Cardinality.One,
        comment = """The absolute path of the source file this node was generated from. This field
            |must be set but may be set to the value `<unknown>` to indicate that no source
            |file can be associated with the node, e.g., because the node represents an
            |entity known to exist because it is referenced, but for which the file that
            |is is declared in is unknown.
            |""".stripMargin
      )
      .protoId(106)

    val lineNumber = builder
      .addProperty(
        name = "LINE_NUMBER",
        valueType = ValueType.Int,
        comment = """This optional field provides the line number of the program construct
            |represented by the node.
            |""".stripMargin
      )
      .protoId(2)

    val columnNumber = builder
      .addProperty(
        name = "COLUMN_NUMBER",
        valueType = ValueType.Int,
        comment = """
            |This optional fields provides the column number of the program construct
            |represented by the node.
            |""".stripMargin
      )
      .protoId(11)

    val lineNumberEnd = builder
      .addProperty(
        name = "LINE_NUMBER_END",
        valueType = ValueType.Int,
        comment = """
        |This optional fields provides the line number at which the program construct
        |represented by the node ends.
        """.stripMargin
      )
      .protoId(12)

    val columnNumberEnd = builder
      .addProperty(
        name = "COLUMN_NUMBER_END",
        valueType = ValueType.Int,
        comment = """
            |This optional fields provides the column number at which the program construct
            |represented by the node ends.
        """.stripMargin
      )
      .protoId(16)

    val sourceFile = builder
      .addEdgeType(
        name = "SOURCE_FILE",
        comment = """This edge connects a node to the node that represents its source file. These
            |edges MUST not be created by the language frontend but are automatically
            |created based on `FILENAME` fields.
            |""".stripMargin
      )
      .protoId(157)

    val file: NodeType = builder
      .addNodeType(
        name = "FILE",
        comment = """File nodes represent source files or a shared objects from which the CPG
                    |was generated. File nodes serve as indices, that is, they allow looking up all
                    |elements of the code by file.
                    |
                    |For each file, the graph MUST contain exactly one File node.
                    |As file nodes are root nodes of abstract syntax tress, they are AstNodes and
                    |their order field is set to 0. This is because they have no sibling nodes,
                    |not because they are the first node of the AST.
                    |
                    |Each CPG MUST contain a special file node with name set to
                    |`<unknown>`. This node is a placeholder used in cases where a file cannot be
                    |determined at compile time. As an example, consider external library functions.
                    |As their code is not available on CPG construction, the file name is unknown.
                    |
                    |File nodes MUST NOT be created by the language frontend. Instead, the language
                    |frontend is assumed to fill out the `FILENAME` field wherever possible,
                    |allowing File nodes to be created automatically upon first loading the CPG.
                    |""".stripMargin
      )
      .protoId(38)
      .addProperties(name, hash)

  }

}
