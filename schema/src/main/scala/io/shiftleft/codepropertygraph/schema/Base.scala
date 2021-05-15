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

  def apply(builder: SchemaBuilder) = new Schema(builder)

  class Schema(builder: SchemaBuilder) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    val version = builder
      .addProperty(
        name = "VERSION",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """A version, given as a string. Used, for example, in the META_DATA node to
                    |indicate which version of the CPG spec this CPG conforms to
                    |""".stripMargin
      )
      .protoId(13)

    val hash = builder
      .addProperty(
        name = "HASH",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = """Hash value. Used, for example, to store the hash of the
                    |artifact that this CPG is built from""".stripMargin
      )
      .protoId(120)

    val code = builder
      .addProperty(
        name = "CODE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "The code snippet the node represents"
      )
      .protoId(21)

    val filename = builder
      .addProperty(
        name = "FILENAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """Full path of canonical file that contained this node; will be linked into
                    |corresponding FILE nodes. Possible for METHOD, TYPE_DECL and NAMESPACE_BLOCK""".stripMargin
      )
      .protoId(106)

    val lineNumber = builder
      .addProperty(
        name = "LINE_NUMBER",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Line where the code starts"
      )
      .protoId(2)

    val columnNumber = builder
      .addProperty(
        name = "COLUMN_NUMBER",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Column where the code starts"
      )
      .protoId(11)

    val isExternal = builder
      .addProperty(
        name = "IS_EXTERNAL",
        valueType = ValueTypes.BOOLEAN,
        cardinality = Cardinality.One,
        comment = """Indicates that the construct (METHOD or TYPE_DECL) is external, that is,
                    |it is referenced but not defined in the code (applies both to insular
                    |parsing and to library functions where we have header files only)
                    |""".stripMargin
      )
      .protoId(7)

    val name = builder
      .addProperty(
        name = "NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Name of represented object, e.g., method name (e.g. \"run\")"
      )
      .protoId(5)

    val fullName = builder
      .addProperty(
        name = "FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """Full name of an element, e.g., the class name along, including its package
                    |(e.g. \"io.shiftleft.dataflowenging.layers.dataflows.DataFlowRunner.run\").
                    |In theory, the FULL_NAME just needs to be unique and is used for linking references,
                    |so a consecutive integer would be valid. In practice, this should be human readable
                    |""".stripMargin
      )
      .protoId(6)

    val parserTypeName = builder
      .addProperty(
        name = "PARSER_TYPE_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "AST node type name emitted by parser."
      )
      .protoId(3)

    val order = builder
      .addProperty(
        name = "ORDER",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.One,
        comment = """General ordering property, such that the children of each AST-node are
                    |typically numbered from 1, ..., N (this is not enforced).""".stripMargin
      )
      .protoId(4)

    val value = builder
      .addProperty(
        name = "VALUE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Generic node property"
      )
      .protoId(8)

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

    val ref = builder
      .addEdgeType(
        name = "REF",
        comment = "A reference to e.g. a LOCAL"
      )
      .protoId(10)

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
