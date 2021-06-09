package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object Deprecated extends SchemaBase {

  override def index: Int = Int.MaxValue

  override def description: String =
    """Elements that we need to keep around for now but that must not be used
      |in new code. These elements will also not be displayed in the spec.
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            base: Base.Schema,
            methodSchema: Method.Schema,
            typeSchema: Type.Schema,
            tagsAndLocation: TagsAndLocation.Schema,
            callGraph: CallGraph.Schema) =
    new Schema(builder, base, methodSchema, typeSchema, tagsAndLocation, callGraph)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               methodSchema: Method.Schema,
               typeSchema: Type.Schema,
               tagsAndLocation: TagsAndLocation.Schema,
               callGraph: CallGraph.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    import methodSchema._
    import base._
    import typeSchema._
    import tagsAndLocation._
    import callGraph._

    val trackingPoint = builder
      .addNodeBaseType(
        name = "TRACKING_POINT",
        comment = "Any node that can occur in a data flow"
      )
      .extendz(withinMethod)

    val sourceType = builder
      .addProperty(
        name = "SOURCE_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(115)

    val sinkType = builder
      .addProperty(
        name = "SINK_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(116)

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

  }

}
