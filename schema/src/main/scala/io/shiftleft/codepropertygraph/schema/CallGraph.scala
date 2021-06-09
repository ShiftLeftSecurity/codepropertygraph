package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, Constant, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object CallGraph extends SchemaBase {

  def index: Int = 7

  override def description: String =
    """
      |This overlay represents call relations between methods.
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema, methodSchema: Method.Schema, typeSchema: Type.Schema) =
    new Schema(builder, base, methodSchema, typeSchema)

  class Schema(builder: SchemaBuilder, base: Base.Schema, methodSchema: Method.Schema, typeSchema: Type.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import methodSchema._
    import typeSchema._
    import base._

    val argumentIndex = builder
      .addProperty(
        name = "ARGUMENT_INDEX",
        valueType = ValueTypes.INTEGER,
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

    val parameterName = builder
      .addProperty(
        name = "PARAMETER_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = """
            |For calls involving named parameters, the `PARAMETER_NAME` field holds the
            |name of the parameter initialized by the expression. For all other calls,
            |this field is unset.
            |""".stripMargin
      )
      .protoId(130)

    val methodFullName = builder
      .addProperty(
        name = "METHOD_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required
                    |to have exactly one METHOD node for each METHOD_FULL_NAME""".stripMargin
      )
      .protoId(54)

    val expression = builder
      .addNodeBaseType(
        name = "EXPRESSION",
        comment = """An expression is a piece of code that can be evaluated to yield a value
            |of a fixed type.
            |
            | Expression may be arguments in method calls. For method calls that do
            | not involved named parameters, the `ARGUMENT_INDEX` field indicates at
            | which position in the argument list the expression occurs, e.g., an
            | `ARGUMENT_INDEX` of 1 indicates that the expression is the first argument
            | in a method call. For calls that employ named parameters, `ARGUMENT_INDEX`
            | is set to -1 and the `NAME` fields holds the name of the parameter.
            |""".stripMargin
      )
      .addProperties(argumentIndex, parameterName)

    val evaluationStrategy = builder
      .addProperty(
        name = "EVALUATION_STRATEGY",
        valueType = ValueTypes.STRING,
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

    methodParameterIn
      .addProperties(evaluationStrategy)

    methodReturn
      .addProperties(evaluationStrategy)

    methodParameterOut
      .addProperties(evaluationStrategy)

    val dispatchType = builder
      .addProperty(
        name = "DISPATCH_TYPE",
        valueType = ValueTypes.STRING,
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

    val callRepr = builder
      .addNodeBaseType(
        name = "CALL_REPR",
        comment = "A base class for nodes that represent different types of calls"
      )
      .addProperties(name, signature)

    val callNode: NodeType = builder
      .addNodeType(
        name = "CALL",
        comment = """A (function/method) call. The `METHOD_FULL_NAME` property is the name of the
                    |invoked method (the callee) while the `TYPE_FULL_NAME` is its return type, and
                    |therefore, the return type of the call when viewing it as an expression. For
                    |languages like Javascript, it is common that we may know the (short-) name
                    |of the invoked method, but we do not know at compile time which method
                    |will actually be invoked, e.g., because it depends on a dynamic import.
                    |In this case, we leave `METHOD_FULL_NAME` blank but at least fill out `NAME`,
                    |which contains the method's (short-) name and `SIGNATURE`, which contains
                    |any information we may have about the types of arguments and return value.
                    |""".stripMargin
      )
      .protoId(15)
      .extendz(callRepr)
      .addProperties(typeFullName)

    callNode
      .addProperties(dispatchType)

    callNode
      .addOutEdge(edge = call, inNode = method)

    callNode
      .addProperty(methodFullName)
      .extendz(expression)

  }

}
