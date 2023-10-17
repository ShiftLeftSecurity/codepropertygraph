package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import overflowdb.schema.Property.ValueType
import overflowdb.schema.{NodeType, SchemaBuilder, SchemaInfo}

object FileSystem extends SchemaBase {

  def docIndex: Int = 3
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
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)
    import base._

    val filename = builder
      .addProperty(
        name = "FILENAME",
        valueType = ValueType.String,
        comment = """The path of the source file this node was generated from, relative to the root
            |path in the meta data node. This field must be set but may be set to the value `<unknown>` to
            |indicate that no source file can be associated with the node, e.g., because the node represents
            |an entity known to exist because it is referenced, but for which the file that is is declared in
            |is unknown.
            |""".stripMargin
      )
      .mandatory(PropertyDefaults.String)
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
      .addProperties(name, hash, fileContent)

    val fileContent = builder
      .addProperty(
        name = "FILE_CONTENT",
        valueType = ValueType.String,
        comment = """
          |This optional field is used to hold the content of text files like source code files.
        """.stripMargin
      )
      .protoId(3811)

    val offset = builder
      .addProperty(
        name = "OFFSET",
        valueType = ValueType.Int,
        comment = """
            |Start offset into the FILE_CONTENT property of the corresponding file.
            |E.g. for METHOD nodes this start offset points to the start of the methods
            |source code in the string holding the source code of the entire file.
        """.stripMargin
      )
      .protoId(3812)

    val offsetEnd = builder
      .addProperty(
        name = "OFFSET_END",
        valueType = ValueType.Int,
        comment = """
          |End offset (exclusive) into the FILE_CONTENT property of the corresponding file.
          |E.g. for METHOD nodes this end offset points to the first code position which is
          |not part of the method.
        """.stripMargin
      )
      .protoId(3813)

  }

}
