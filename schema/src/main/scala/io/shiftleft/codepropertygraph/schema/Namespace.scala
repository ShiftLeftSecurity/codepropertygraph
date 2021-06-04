package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{NodeType, SchemaBuilder, SchemaInfo}

object Namespace extends SchemaBase {

  def index: Int = 4

  override def description: String =
    """
      |Many programming languages allow code to be structured into namespaces. The Namespace
      |Layer makes these namespaces explicit and associates program constructs with the
      |namespaces they are defined in.
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema, fs: FileSystem.Schema) =
    new Schema(builder, base, fs)

  class Schema(builder: SchemaBuilder, base: Base.Schema, fs: FileSystem.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import base._
    import fs._

    val namespaceBlock: NodeType = builder
      .addNodeType(
        name = "NAMESPACE_BLOCK",
        comment = """A reference to a namespace.
                    |We borrow the concept of a "namespace block" from C++, that is, a namespace block
                    |is a block of code that has been placed in the same namespace by a programmer.
                    |This block may be introduced via a `package` statement in Java or
                    |a `namespace{ }` statement in C++.
                    |
                    |The `FULL_NAME` field contains the namespace name in a human-readable format.
                    |The name should be given in dot-separated form where a dot indicates
                    |the the right hand side is a sub namespace of the left hand side, e.g.,
                    |`foo.bar` denotes the namespace `bar` contained in the namespace `foo`.
                    |
                    |The `NAME` field contains the name of the namespace without the names of
                    |its parent namespaces, e.g., for the namespace `foo.woo.bar`, the `NAME`
                    |field is `bar`.
                    |
                    |""".stripMargin
      )
      .protoId(41)
      .addProperties(name, fullName, filename)

    val namespace: NodeType = builder
      .addNodeType(
        name = "NAMESPACE",
        comment = """This node represents a namespace. Similar to FILE nodes, NAMESPACE nodes
                    |serve as indices that allow all definitions inside a namespace to be
                    |obtained by following outgoing edges from a NAMESPACE node.
                    |                    |
                    |NAMESPACE nodes MUST NOT be created by language frontends. Instead,
                    |they are generated from NAMESPACE_BLOCK nodes automatically upon
                    |first loading of the CPG.
                    |""".stripMargin
      )
      .protoId(40)
      .addProperties(name)

    namespaceBlock
      .addOutEdge(edge = sourceFile, inNode = file)

  }

}
