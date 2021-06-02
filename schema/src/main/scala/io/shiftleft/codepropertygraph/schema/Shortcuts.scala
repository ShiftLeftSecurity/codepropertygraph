package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._

object Shortcuts extends SchemaBase {

  def index: Int = 15

  override def description: String =
    """
      |Shortcut edges calculated by the backend.
      |These edges should NOT be created by the language frontend.
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            base: Base.Schema,
            methodSchema: Method.Schema,
            ast: Ast.Schema,
            typeSchema: Type.Schema,
            fs: FileSystem.Schema) =
    new Schema(builder, base, methodSchema, ast, typeSchema, fs)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               methodSchema: Method.Schema,
               astSchema: Ast.Schema,
               typeSchema: Type.Schema,
               fs: FileSystem.Schema) {

    import base._
    import methodSchema._
    import astSchema._
    import typeSchema._
    import fs._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

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

    file
      .addOutEdge(edge = contains, inNode = typeDecl)
      .addOutEdge(edge = contains, inNode = method)

    method
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
