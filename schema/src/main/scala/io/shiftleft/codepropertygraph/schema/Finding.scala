package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import overflowdb.schema.Property.{Cardinality, ValueType}
import overflowdb.schema._

object Finding extends SchemaBase {

  def docIndex: Int = 15

  override def description: String =
    """
      |We allow findings (e.g., potential vulnerabilities, notes on dangerous practices) to
      |be stored in the Findings Layer.
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema) =
    new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)
    import base._

// node properties
    val key = builder
      .addProperty(
        name = "KEY",
        valueType = ValueType.String,
        comment = "This property denotes a key of a key-value pair."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(131)

// node types
    val finding: NodeType = builder
      .addNodeType(
        name = "FINDING",
        comment = """Finding nodes may be used to store analysis results in the graph
            |that are to be exposed to an end-user, e.g., information about
            |potential vulnerabilities or dangerous programming practices.
            |A Finding node may contain an abitrary list of key value pairs
            |that characterize the finding, as well as a list of nodes that
            |serve as evidence for the finding.
            |""".stripMargin
      )
      .protoId(214)
      .addProperties()

    val keyValuePair: NodeType = builder
      .addNodeType(
        name = "KEY_VALUE_PAIR",
        comment = "This node represents a key value pair, where both the key and the value are strings."
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
