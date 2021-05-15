package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

object Base extends SchemaBase {

  def index: Int = 5
  override def providedByFrontend: Boolean = true
  override def description: String =
    """
      |The Base Layer of the Code Property Graph. This is the specification relevant
      |for implementers of language frontends.
      |""".stripMargin

  def apply(builder: SchemaBuilder, common: CommonProperties.Schema) = new Schema(builder, common)

  class Schema(builder: SchemaBuilder, common: CommonProperties.Schema) {

    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import common._

    // The following fields are used to create edges between nodes in later processing stages.

    val typeFullName = builder
      .addProperty(
        name = "TYPE_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """The static type of an entity. E.g. expressions, local, parameters etc.
            |This property is matched against the FULL_NAME of TYPE nodes and thus it
            |is required to have at least one TYPE node for each TYPE_FULL_NAME
            |""".stripMargin
      )
      .protoId(51)

    val typeDeclFullName = builder
      .addProperty(
        name = "TYPE_DECL_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """The static type decl of a TYPE. This property is matched against the FULL_NAME
            |of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each
            |different TYPE_DECL_FULL_NAME""".stripMargin
      )
      .protoId(52)

    val methodFullName = builder
      .addProperty(
        name = "METHOD_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required
            |to have exactly one METHOD node for each METHOD_FULL_NAME""".stripMargin
      )
      .protoId(54)

    val astParentType = builder
      .addProperty(
        name = "AST_PARENT_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """The type of the AST parent. Since this is only used in some parts of the graph,
            |the list does not include all possible parents by intention.
            |Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK.
            |""".stripMargin
      )
      .protoId(56)

    val astParentFullName = builder
      .addProperty(
        name = "AST_PARENT_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "The FULL_NAME of a the AST parent of an entity"
      )
      .protoId(57)

    // edge types

// node base types

    val withinMethod = builder.addNodeBaseType(
      name = "WITHIN_METHOD",
      comment = "Any node that can exist in a method"
    )

    val trackingPoint = builder
      .addNodeBaseType(
        name = "TRACKING_POINT",
        comment = "Any node that can occur in a data flow"
      )
      .extendz(withinMethod)

    val declaration = builder
      .addNodeBaseType(
        name = "DECLARATION",
        comment = ""
      )
      .addProperties(name)

    val localLike = builder
      .addNodeBaseType(
        name = "LOCAL_LIKE",
        comment = "Formal input parameters, locals, and identifiers"
      )
      .addProperties(name)


    // Node types

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
