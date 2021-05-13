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

    // Properties used by more than one node type
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

    val signature = builder
      .addProperty(
        name = "SIGNATURE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """Method signature. The format is defined by the language front-end, and the
            |backend simply compares strings to resolve function overloading, i.e. match
            |call-sites to METHODs. In theory, consecutive integers would be valid,
            |but in practice this should be human readable
            |""".stripMargin
      )
      .protoId(22)

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

    val order = builder
      .addProperty(
        name = "ORDER",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.One,
        comment = """General ordering property, such that the children of each AST-node are
                    |typically numbered from 1, ..., N (this is not enforced).""".stripMargin
      )
      .protoId(4)

    // edge types
    val ast = builder
      .addEdgeType(
        name = "AST",
        comment = "Syntax tree edge"
      )
      .protoId(3)

    val cfg = builder
      .addEdgeType(
        name = "CFG",
        comment = "Control flow edge"
      )
      .protoId(19)

    val sourceFile = builder
      .addEdgeType(
        name = "SOURCE_FILE",
        comment = "Source file of a node, in which its LINE_NUMBER and COLUMN_NUMBER are valid"
      )
      .protoId(157)

    val ref = builder
      .addEdgeType(
        name = "REF",
        comment = "A reference to e.g. a LOCAL"
      )
      .protoId(10)

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

    val astNode = builder
      .addNodeBaseType(
        name = "AST_NODE",
        comment = """Base type for all nodes are (in particular) nodes of the abstract syntax tree.
                    |In a syntax tree, sibling nodes (nodes who share a parent node) are ordered.
                    |As some graph databases do not guarantee the order in which siblings are returned to
                    |be stable, we make the ordering explicit by storing the position of each node relative
                    |to its siblings in the order field. In the left most sibling, order is set to 0,
                    |while in the right-most sibling, it is set to n-1 where n is the number of siblings.
                    |""".stripMargin
      )
      .addProperties(order)

    val cfgNode = builder
      .addNodeBaseType(
        name = "CFG_NODE",
        comment = "Any node that can occur as part of a control flow graph"
      )
      .addProperties(lineNumber, columnNumber, code)
      .extendz(withinMethod, astNode)

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

    val lineNumberEnd = builder
      .addProperty(
        name = "LINE_NUMBER_END",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Line where the code ends"
      )
      .protoId(12)

    val columnNumberEnd = builder
      .addProperty(
        name = "COLUMN_NUMBER_END",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Column where the code ends"
      )
      .protoId(16)

    val modifierType = builder
      .addProperty(
        name = "MODIFIER_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Indicates the modifier which is represented by a MODIFIER node. See modifierTypes"
      )
      .protoId(26)

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
      .extendz(astNode)

    val modifier: NodeType = builder
      .addNodeType(
        name = "MODIFIER",
        comment = "A modifier, e.g., static, public, private"
      )
      .protoId(300)
      .addProperties(modifierType)
      .extendz(astNode)

// constants

    val modifierTypes = builder.addConstants(
      category = "ModifierTypes",
      Constant(name = "STATIC", value = "STATIC", valueType = ValueTypes.STRING, comment = "The static modifier")
        .protoId(1),
      Constant(name = "PUBLIC", value = "PUBLIC", valueType = ValueTypes.STRING, comment = "The public modifier")
        .protoId(2),
      Constant(name = "PROTECTED",
               value = "PROTECTED",
               valueType = ValueTypes.STRING,
               comment = "The protected modifier").protoId(3),
      Constant(name = "PRIVATE", value = "PRIVATE", valueType = ValueTypes.STRING, comment = "The private modifier")
        .protoId(4),
      Constant(name = "ABSTRACT", value = "ABSTRACT", valueType = ValueTypes.STRING, comment = "The abstract modifier")
        .protoId(5),
      Constant(name = "NATIVE", value = "NATIVE", valueType = ValueTypes.STRING, comment = "The native modifier")
        .protoId(6),
      Constant(name = "CONSTRUCTOR",
               value = "CONSTRUCTOR",
               valueType = ValueTypes.STRING,
               comment = "The constructor modifier").protoId(7),
      Constant(name = "VIRTUAL", value = "VIRTUAL", valueType = ValueTypes.STRING, comment = "The virtual modifier")
        .protoId(8),
    )

  }

}
