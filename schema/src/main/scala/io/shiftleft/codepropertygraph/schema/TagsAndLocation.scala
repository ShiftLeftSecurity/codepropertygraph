package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

object TagsAndLocation {
  def apply(builder: SchemaBuilder, base: Base.Schema, enhancements: Enhancements.Schema) =
    new Schema(builder, base, enhancements)

  class Schema(builder: SchemaBuilder, base: Base.Schema, enhancements: Enhancements.Schema) {
    import base._
    import enhancements._

// node properties
    val symbol = builder
      .addNodeProperty(
        name = "SYMBOL",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(100)

    val methodShortName = builder
      .addNodeProperty(
        name = "METHOD_SHORT_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(102)

    val packageName = builder
      .addNodeProperty(
        name = "PACKAGE_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(103)

    val className = builder
      .addNodeProperty(
        name = "CLASS_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(104)

    val classShortName = builder
      .addNodeProperty(
        name = "CLASS_SHORT_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(132)

    val nodeLabel = builder
      .addNodeProperty(
        name = "NODE_LABEL",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(105)

    val sourceType = builder
      .addNodeProperty(
        name = "SOURCE_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(115)

    val sinkType = builder
      .addNodeProperty(
        name = "SINK_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(116)

// node tpes
    val location: NodeType = builder
      .addNodeType(
        name = "LOCATION",
        comment = ""
      )
      .protoId(25)
      .addProperties(symbol,
                     methodFullName,
                     methodShortName,
                     packageName,
                     lineNumber,
                     className,
                     classShortName,
                     nodeLabel,
                     filename)

    val tagNodePair: NodeType = builder
      .addNodeType(
        name = "TAG_NODE_PAIR",
        comment = ""
      )
      .protoId(208)
      .addProperties()

    val source: NodeType = builder
      .addNodeType(
        name = "SOURCE",
        comment = ""
      )
      .protoId(202)
      .addProperties(sourceType)

    val sink: NodeType = builder
      .addNodeType(
        name = "SINK",
        comment = ""
      )
      .protoId(203)
      .addProperties(sinkType)

// node relations
    location
      .addContainedNode(builder.anyNode, "node", Cardinality.ZeroOrOne)

    tagNodePair
      .addContainedNode(tag, "tag", Cardinality.One)
      .addContainedNode(builder.anyNode, "node", Cardinality.One)

    source
      .addContainedNode(trackingPoint, "node", Cardinality.One)
      .addContainedNode(method, "method", Cardinality.One)
      .addContainedNode(tag, "methodTags", Cardinality.List)
      .addContainedNode(method, "callingMethod", Cardinality.ZeroOrOne)
      .addContainedNode(callNode, "callsite", Cardinality.ZeroOrOne)
      .addContainedNode(tag, "tags", Cardinality.List)
      .addContainedNode(tpe, "nodeType", Cardinality.One)

    sink
      .addContainedNode(trackingPoint, "node", Cardinality.One)
      .addContainedNode(tpe, "nodeType", Cardinality.One)
      .addContainedNode(method, "method", Cardinality.One)
      .addContainedNode(tag, "methodTags", Cardinality.List)
      .addContainedNode(method, "callingMethod", Cardinality.ZeroOrOne)
      .addContainedNode(callNode, "callsite", Cardinality.ZeroOrOne)
      .addContainedNode(methodParameterIn, "parameterIn", Cardinality.ZeroOrOne)
      .addContainedNode(tag, "parameterInTags", Cardinality.List)

// constants

  }

}
