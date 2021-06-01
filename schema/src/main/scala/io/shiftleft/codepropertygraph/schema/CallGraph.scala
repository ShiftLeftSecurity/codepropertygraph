package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, Constant, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object CallGraph extends SchemaBase {

  def index: Int = 10

  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder, methodSchema: Method.Schema, ast: Ast.Schema) =
    new Schema(builder, methodSchema, ast)

  class Schema(builder: SchemaBuilder, methodSchema: Method.Schema, ast: Ast.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import methodSchema._
    import ast._

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
                    |the last argument index determines the return-value of a BLOCK expression
                    |""".stripMargin
      )
      .protoId(40)

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

    val argument = builder
      .addEdgeType(
        name = "ARGUMENT",
        comment = "Relation between a CALL and its arguments and RETURN and the returned expression"
      )
      .protoId(156)

    val receiver = builder
      .addEdgeType(
        name = "RECEIVER",
        comment = "The receiver of a method call which is either an object or a pointer"
      )
      .protoId(55)

    jumpTarget
      .addProperty(argumentIndex)

    expression
      .addProperties(argumentIndex)

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
