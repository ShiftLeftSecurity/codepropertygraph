package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, Constant, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object CallGraph extends SchemaBase {

  def index: Int = 6

  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder, methodSchema: Method.Schema, methodBody: MethodBody.Schema) =
    new Schema(builder, methodSchema, methodBody)

  class Schema(builder: SchemaBuilder, methodSchema: Method.Schema, methodBody: MethodBody.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import methodSchema._
    import methodBody._

    val evaluationStrategy = builder
      .addProperty(
        name = "EVALUATION_STRATEGY",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "Evaluation strategy for function parameters and return values. One of the values in \"evaluationStrategies\""
      )
      .protoId(15)

    val dispatchType = builder
      .addProperty(
        name = "DISPATCH_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "The dispatch type of a call, which is either static or dynamic. See dispatchTypes"
      )
      .protoId(25)

    val call = builder
      .addEdgeType(
        name = "CALL",
        comment = ""
      )
      .protoId(6)

    callNode
      .addProperties(dispatchType)

    methodParameterIn
      .addProperties(evaluationStrategy)

    methodReturn
      .addProperties(evaluationStrategy)

    methodParameterOut
      .addProperties(evaluationStrategy)

    callNode
      .addOutEdge(edge = call, inNode = method)

    val dispatchTypes = builder.addConstants(
      category = "DispatchTypes",
      Constant(
        name = "STATIC_DISPATCH",
        value = "STATIC_DISPATCH",
        valueType = ValueTypes.STRING,
        comment = "For statically dispatched calls the call target is known before program execution"
      ).protoId(1),
      Constant(
        name = "DYNAMIC_DISPATCH",
        value = "DYNAMIC_DISPATCH",
        valueType = ValueTypes.STRING,
        comment = "For dynamically dispatched calls the target is determined during runtime"
      ).protoId(2),
    )

    val evaluationStrategies = builder.addConstants(
      category = "EvaluationStrategies",
      Constant(
        name = "BY_REFERENCE",
        value = "BY_REFERENCE",
        valueType = ValueTypes.STRING,
        comment =
          "A parameter or return of a function is passed by reference which means an address is used behind the scenes"
      ).protoId(1),
      Constant(
        name = "BY_SHARING",
        value = "BY_SHARING",
        valueType = ValueTypes.STRING,
        comment =
          "Only applicable to object parameter or return values. The pointer to the object is passed by value but the object itself is not copied and changes to it are thus propagated out of the method context"
      ).protoId(2),
      Constant(
        name = "BY_VALUE",
        value = "BY_VALUE",
        valueType = ValueTypes.STRING,
        comment = "A parameter or return of a function passed by value which means a flat copy is used"
      ).protoId(3),
    )

  }

}
