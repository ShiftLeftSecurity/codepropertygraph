package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._

object Enhancements extends SchemaBase {

  def index: Int = 3

  override def description: String =
    """
      |Node and edges introduced in the linking stage. These are edges that can only
      |be drawn once all compilation units have gone through local analysis as done
      |by the language frontend.
      |
      |These nodes/edges should NOT be created by the language frontend.
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            methodSchema: Method.Schema,
            ast: Ast.Schema,
            typeDeclSchema: TypeDecl.Schema,
            fs : FileSystem.Schema) =
    new Schema(builder, methodSchema, ast, typeDeclSchema, fs)

  class Schema(builder: SchemaBuilder,
               methodSchema: Method.Schema,
               astSchema: Ast.Schema,
               typeDeclSchema: TypeDecl.Schema,
               fs : FileSystem.Schema) {

    import methodSchema._
    import astSchema._
    import typeDeclSchema._
    import fs._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

// node properties

    val parameterLink = builder
      .addEdgeType(
        name = "PARAMETER_LINK",
        comment = "Links together corresponding METHOD_PARAMETER_IN and METHOD_PARAMETER_OUT nodes"
      )
      .protoId(12)

    val evalType = builder
      .addEdgeType(
        name = "EVAL_TYPE",
        comment = "Link to evaluation type"
      )
      .protoId(21)

    val contains = builder
      .addEdgeType(
        name = "CONTAINS",
        comment = "Shortcut over multiple AST edges"
      )
      .protoId(28)

    val inheritsFrom = builder
      .addEdgeType(
        name = "INHERITS_FROM",
        comment = "Inheritance relation between types"
      )
      .protoId(23)

    val aliasOf = builder
      .addEdgeType(
        name = "ALIAS_OF",
        comment = "Alias relation between types"
      )
      .protoId(138)

    // node types

    file
      .addOutEdge(edge = contains, inNode = typeDecl)
      .addOutEdge(edge = contains, inNode = method)

// node relations

    binding
      .addOutEdge(edge = ref, inNode = method, cardinalityOut = Cardinality.One)

    method
      .addOutEdge(edge = ast, inNode = methodParameterOut)
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = contains, inNode = callNode)
      .addOutEdge(edge = contains, inNode = identifier)
      .addOutEdge(edge = contains, inNode = fieldIdentifier)
      .addOutEdge(edge = contains, inNode = literal)
      .addOutEdge(edge = contains, inNode = ret)
      .addOutEdge(edge = contains, inNode = methodRef)
      .addOutEdge(edge = contains, inNode = typeRef)
      .addOutEdge(edge = contains, inNode = block)
      .addOutEdge(edge = contains, inNode = controlStructure)
      .addOutEdge(edge = contains, inNode = jumpTarget)
      .addOutEdge(edge = contains, inNode = unknown)


    methodParameterIn
      .addOutEdge(edge = evalType, inNode = tpe, cardinalityOut = Cardinality.One)
      .addOutEdge(edge = parameterLink, inNode = methodParameterOut)

    methodParameterOut
      .addOutEdge(edge = evalType, inNode = tpe)

    methodReturn
      .addOutEdge(edge = evalType, inNode = tpe)

    methodRef
      .addOutEdge(edge = ref, inNode = method, cardinalityOut = Cardinality.One)
      .addOutEdge(edge = evalType, inNode = tpe)

    typeRef
      .addOutEdge(edge = evalType, inNode = tpe)

    tpe
      .addOutEdge(edge = ref, inNode = typeDecl)

    typeDecl
      .addOutEdge(edge = inheritsFrom, inNode = tpe)
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = aliasOf, inNode = tpe)
      .addOutEdge(edge = contains, inNode = method)

    member
      .addOutEdge(edge = evalType, inNode = tpe)

    literal
      .addOutEdge(edge = evalType, inNode = tpe)

    callNode
      .addOutEdge(edge = ref, inNode = member)
      .addOutEdge(edge = evalType, inNode = tpe)

    local
      .addOutEdge(edge = evalType, inNode = tpe)

    identifier
      .addOutEdge(edge = evalType, inNode = tpe)

    block
      .addOutEdge(edge = evalType, inNode = tpe)

    controlStructure
      .addOutEdge(edge = evalType, inNode = tpe)

    unknown
      .addOutEdge(edge = evalType, inNode = tpe)

  }

}
