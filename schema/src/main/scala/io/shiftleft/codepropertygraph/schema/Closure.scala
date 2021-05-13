package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

object Closure extends SchemaBase {

  override def index: Int = 4

  def apply(builder: SchemaBuilder, base: Base.Schema, enhancements: Enhancements.Schema) =
    new Schema(builder, base, enhancements)

  class Schema(builder: SchemaBuilder, base: Base.Schema, enhancements: Enhancements.Schema) {
    import base._
    import enhancements._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    // node properties
    val closureBindingId = builder
      .addProperty(
        name = "CLOSURE_BINDING_ID",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment =
          "Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes"
      )
      .protoId(50)

    val closureOriginalName = builder
      .addProperty(
        name = "CLOSURE_ORIGINAL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = "The original name of the (potentially mangled) captured variable"
      )
      .protoId(159)

// edge types
    val capture = builder
      .addEdgeType(
        name = "CAPTURE",
        comment = "Represents the capturing of a variable into a closure"
      )
      .protoId(40)

// node types
    local.addProperties(closureBindingId)

    val closureBinding: NodeType = builder
      .addNodeType(
        name = "CLOSURE_BINDING",
        comment = "Represents the binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method"
      )
      .protoId(334)
      .addProperties(closureBindingId, evaluationStrategy, closureOriginalName)

    val capturedBy = builder
      .addEdgeType(
        name = "CAPTURED_BY",
        comment = "Connection between a captured LOCAL and the corresponding CLOSURE_BINDING"
      )
      .protoId(41)

    // node relations
    local.addOutEdge(edge = capturedBy, inNode = closureBinding)

    methodRef
      .addOutEdge(edge = capture, inNode = closureBinding)

    typeRef
      .addOutEdge(edge = capture, inNode = closureBinding)

    closureBinding
      .addOutEdge(edge = ref, inNode = local, cardinalityOut = Cardinality.One)
      .addOutEdge(edge = ref, inNode = methodParameterIn)
// constants

  }

}
