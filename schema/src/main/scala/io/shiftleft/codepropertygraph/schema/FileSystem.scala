package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{NodeType, SchemaBuilder, SchemaInfo}

object FileSystem extends SchemaBase {

  def index: Int = 3
  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            base: Base.Schema,
            namespaces: Namespace.Schema,
            methodSchema: Method.Schema,
            typeSchema: Type.Schema) =
    new Schema(builder, base, namespaces, methodSchema, typeDeclSchema)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               namespaces: Namespace.Schema,
               methodSchema: Method.Schema,
               typeSchema: Type.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import namespaces._
    import methodSchema._
    import typeDeclSchema._
    import base._

    val sourceFile = builder
      .addEdgeType(
        name = "SOURCE_FILE",
        comment = "Source file of a node, in which its LINE_NUMBER and COLUMN_NUMBER are valid"
      )
      .protoId(157)

    val file: NodeType = builder
      .addNodeType(
        name = "FILE",
        comment = """Node representing a source file - the root of the AST.
                    |Code property graphs are created from sets of files.
                    |Information about these files is stored in the graph to enable queries to map nodes
                    |of the graph back to the files that contain the code they represent.
                    |For each file, the graph must contain exactly one File.
                    |As file nodes are root nodes of abstract syntax tress, they are AstNodes and their
                    |order field is set to 0.
                    |
                    |Each code property graph must contain a special file node with name set to
                    |"<unknown>". This node is a placeholder used in cases where a file cannot be
                    |determined at compile time. As an example, consider the case where an external
                    |type is introduced only at link time.
                    |Conceptually file nodes serve as indices, e.g., they map all filenames to the
                    |list of methods they contain.
                    |
                    |File nodes MUST NOT be created by the language frontend. Instead, the language
                    |frontend is assumed to fill out the FILENAME field wherever possible,
                    |allowing File nodes to be created automatically when the semantic CPG layer is created.
                    |""".stripMargin
      )
      .protoId(38)
      .addProperties(name, hash)

    method
      .addOutEdge(edge = sourceFile, inNode = file)

    namespaceBlock
      .addOutEdge(edge = sourceFile, inNode = file)

    typeDecl
      .addOutEdge(edge = sourceFile, inNode = file)

  }

}
