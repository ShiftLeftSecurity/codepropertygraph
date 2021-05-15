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

    val sourceFile = builder
      .addEdgeType(
        name = "SOURCE_FILE",
        comment = "Source file of a node, in which its LINE_NUMBER and COLUMN_NUMBER are valid"
      )
      .protoId(157)

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
