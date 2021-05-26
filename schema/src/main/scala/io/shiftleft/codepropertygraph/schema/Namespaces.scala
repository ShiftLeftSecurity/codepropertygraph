package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{NodeType, SchemaBuilder, SchemaInfo}

object Namespaces extends SchemaBase {

  def index: Int = 6

  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema) =
    new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import base._

    val namespaceBlock: NodeType = builder
      .addNodeType(
        name = "NAMESPACE_BLOCK",
        comment = """A reference to a namespace.
                    |We borrow the concept of a "namespace block" from C++, that is, a namespace block
                    |is a block of code that has been placed in the same namespace by a programmer.
                    |This block may be introduced via a `package` statement in Java or
                    |a `namespace{ }` statement in C++.
                    |""".stripMargin
      )
      .protoId(41)
      .addProperties(name, fullName, filename)

    val namespace: NodeType = builder
      .addNodeType(
        name = "NAMESPACE",
        comment = """This node represents a namespace as a whole whereas the NAMESPACE_BLOCK is used
                    |for each grouping occurrence of a namespace in code.
                    |Single representing NAMESPACE node is required for easier navigation in
                    |the query language.
                    |""".stripMargin
      )
      .protoId(40)
      .addProperties(name)

  }

}
