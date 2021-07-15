package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Constant, SchemaBuilder, SchemaInfo}
import overflowdb.schema.Property.ValueType

object CallGraph extends SchemaBase {

  def index: Int = 8

  override def description: String =
    """
      |The Call Graph Layer represents call relations between methods.
      |""".stripMargin

  def apply(builder: SchemaBuilder, methodSchema: Method.Schema, astSchema: Ast.Schema) =
    new Schema(builder, methodSchema, astSchema: Ast.Schema)

  class Schema(builder: SchemaBuilder, methodSchema: Method.Schema, astSchema: Ast.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import methodSchema._
    import astSchema._

    val argumentIndex = builder
      .addProperty(
        name = "ARGUMENT_INDEX",
        valueType = ValueType.Int,
        cardinality = Cardinality.One,
        comment = """AST-children of CALL nodes have an argument index, that is used to match
                    |call-site arguments with callee parameters. Explicit parameters are numbered
                    |from 1 to N, while index 0 is reserved for implicit self / this parameter.
                    |CALLs without implicit parameter therefore have arguments starting with index 1.
                    |AST-children of BLOCK nodes may have an argument index as well; in this case,
                    |the last argument index determines the return expression of a BLOCK expression.
                    |If the `PARAMETER_NAME` field is set, then the `ARGUMENT_INDEX` field is
                    |ignored. It is suggested to set it to -1.
                    |""".stripMargin
      )
      .protoId(40)

    val argumentName = builder
      .addProperty(
        name = "ARGUMENT_NAME",
        valueType = ValueType.String,
        comment = """
            |For calls involving named parameters, the `ARGUMENT_NAME` field holds the
            |name of the parameter initialized by the expression. For all other calls,
            |this field is unset.
            |""".stripMargin
      )
      .protoId(130)

    val methodFullName = builder
      .addProperty(
        name = "METHOD_FULL_NAME",
        valueType = ValueType.String,
        cardinality = Cardinality.One,
        comment = """The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required
                    |to have exactly one METHOD node for each METHOD_FULL_NAME""".stripMargin
      )
      .protoId(54)

    expression
      .addProperties(argumentIndex, argumentName)

    val evaluationStrategy = builder
      .addProperty(
        name = "EVALUATION_STRATEGY",
        valueType = ValueType.String,
        cardinality = Cardinality.One,
        comment = """For formal method input parameters, output parameters, and return parameters,
            |this field holds the evaluation strategy, which is one of the following:
            |1) `BY_REFERENCE` indicates that the parameter is passed by reference, 2)
            |`BY_VALUE` indicates that it is passed by value, that is, a copy is made,
            |3) `BY_SHARING` the parameter is a pointer/reference and it is shared with
            |the caller/callee. While a copy of the pointer is made, a copy of the object
            |that it points to is not made.
            |""".stripMargin
      )
      .protoId(15)

    val evaluationStrategies = builder.addConstants(
      category = "EvaluationStrategies",
      Constant(
        name = "BY_REFERENCE",
        value = "BY_REFERENCE",
        valueType = ValueType.String,
        comment =
          "A parameter or return of a function is passed by reference which means an address is used behind the scenes"
      ).protoId(1),
      Constant(
        name = "BY_SHARING",
        value = "BY_SHARING",
        valueType = ValueType.String,
        comment =
          "Only applicable to object parameter or return values. The pointer to the object is passed by value but the object itself is not copied and changes to it are thus propagated out of the method context"
      ).protoId(2),
      Constant(
        name = "BY_VALUE",
        value = "BY_VALUE",
        valueType = ValueType.String,
        comment = "A parameter or return of a function passed by value which means a flat copy is used"
      ).protoId(3),
    )

    methodParameterIn
      .addProperties(evaluationStrategy)

    methodReturn
      .addProperties(evaluationStrategy)

    methodParameterOut
      .addProperties(evaluationStrategy)

    val dispatchType = builder
      .addProperty(
        name = "DISPATCH_TYPE",
        valueType = ValueType.String,
        cardinality = Cardinality.One,
        comment = """This field holds the dispatch type of a call, which is either `STATIC_DISPATCH` or
            |`DYNAMIC_DISPATCH`. For statically dispatched method calls, the call target is known
            |at compile time while for dynamically dispatched calls, it can only be determined at
            |runtime as it may depend on the type of an object (as is the case for virtual method
            |calls) or calculation of an offset.
            |""".stripMargin
      )
      .protoId(25)

    val call = builder
      .addEdgeType(
        name = "CALL",
        comment = """This edge connects call sites, i.e., nodes with the type `CALL`, to the
            |method node that represent the method they invoke. The frontend MAY create
            |`CALL` edges but is not required to do so. Instead, of the `METHOD_FULL_NAME`
            |field of the `CALL` node is set correctly, `CALL` edges are created
            |automatically as the CPG is first loaded.
            |""".stripMargin
      )
      .protoId(6)

    val argument = builder
      .addEdgeType(
        name = "ARGUMENT",
        comment = """Argument edges connect call sites (node type `CALL`) to their arguments
            |(node type `EXPRESSION`) as well as `RETURN` nodes to the expressions
            |that return.
            |""".stripMargin
      )
      .protoId(156)

    val receiver = builder
      .addEdgeType(
        name = "RECEIVER",
        comment = """Similar to `ARGUMENT` edges, `RECEIVER` edges connect call sites
            |to their receiver arguments. A receiver argument is the object on
            |which a method operates, that is, it is the expression that is
            |assigned to the `this` pointer as control is transferred to the method.
            |""".stripMargin
      )
      .protoId(55)

    val dispatchTypes = builder.addConstants(
      category = "DispatchTypes",
      Constant(
        name = "STATIC_DISPATCH",
        value = "STATIC_DISPATCH",
        valueType = ValueType.String,
        comment = "For statically dispatched calls the call target is known before program execution"
      ).protoId(1),
      Constant(
        name = "DYNAMIC_DISPATCH",
        value = "DYNAMIC_DISPATCH",
        valueType = ValueType.String,
        comment = "For dynamically dispatched calls the target is determined during runtime"
      ).protoId(2),
    )

    callNode
      .addProperties(dispatchType)

    callNode
      .addOutEdge(edge = call, inNode = method)

    callNode
      .addProperty(methodFullName)
      .extendz(expression)

    jumpTarget
      .addProperty(argumentIndex)

    methodRef.addProperty(methodFullName)

    callNode
      .addOutEdge(edge = receiver,
                  inNode = callNode,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver,
                  inNode = identifier,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver,
                  inNode = literal,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver,
                  inNode = methodRef,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver, inNode = typeRef)
      .addOutEdge(edge = receiver,
                  inNode = block,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver, inNode = controlStructure)
      .addOutEdge(edge = receiver, inNode = unknown)
      .addOutEdge(edge = argument, inNode = callNode, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = identifier, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = fieldIdentifier, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = argument, inNode = literal, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = methodRef, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = typeRef)
      .addOutEdge(edge = argument, inNode = block, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = jumpTarget)
      .addOutEdge(edge = argument, inNode = controlStructure)
      .addOutEdge(edge = argument, inNode = unknown)

    ret
      .addOutEdge(edge = argument,
                  inNode = callNode,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument,
                  inNode = identifier,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument,
                  inNode = literal,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument,
                  inNode = methodRef,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = typeRef)
      .addOutEdge(edge = argument,
                  inNode = ret,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument,
                  inNode = block,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = jumpTarget)
      .addOutEdge(edge = argument, inNode = controlStructure)
      .addOutEdge(edge = argument, inNode = unknown)

  }

}
