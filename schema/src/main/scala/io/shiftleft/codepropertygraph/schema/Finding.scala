package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

object Finding extends SchemaBase {

  def index: Int = 14

  override def description: String =
    """
      |
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema) =
    new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import base._

// node properties
    val key = builder
      .addProperty(
        name = "KEY",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(131)

// node types
    val finding: NodeType = builder
      .addNodeType(
        name = "FINDING",
        comment = ""
      )
      .protoId(214)
      .addProperties()

    val keyValuePair: NodeType = builder
      .addNodeType(
        name = "KEY_VALUE_PAIR",
        comment = ""
      )
      .protoId(217)
      .addProperties(key, value)

// node relations
    finding
      .addContainedNode(builder.anyNode, "evidence", Cardinality.List)
      .addContainedNode(keyValuePair, "keyValuePairs", Cardinality.List)

// constants

  }

}
