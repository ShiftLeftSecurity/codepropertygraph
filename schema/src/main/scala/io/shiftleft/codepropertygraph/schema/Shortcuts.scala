package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.EdgeType.Cardinality
import overflowdb.schema._

object Shortcuts extends SchemaBase {

  def index: Int = 16

  override def description: String =
    """
      |The Shortcuts Layer provides shortcut edges calculated to speed up
      |subsequent queries. Language frontends MUST NOT create shortcut edges.
      |""".stripMargin

  def apply(
    builder: SchemaBuilder,
    base: Base.Schema,
    methodSchema: Method.Schema,
    ast: Ast.Schema,
    typeSchema: Type.Schema,
    fs: FileSystem.Schema
  ) =
    new Schema(builder, base, methodSchema, ast, typeSchema, fs)

  class Schema(
    builder: SchemaBuilder,
    base: Base.Schema,
    methodSchema: Method.Schema,
    astSchema: Ast.Schema,
    typeSchema: Type.Schema,
    fs: FileSystem.Schema
  ) {

    import base._
    import methodSchema._
    import astSchema._
    import typeSchema._
    import fs._

    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)

    val evalType = builder
      .addEdgeType(name = "EVAL_TYPE", comment = "This edge connects a node to its evaluation type.")
      .protoId(21)

    val contains = builder
      .addEdgeType(name = "CONTAINS", comment = "This edge connects a node to the method that contains it.")
      .protoId(28)

    val parameterLink = builder
      .addEdgeType(
        name = "PARAMETER_LINK",
        comment = """This edge connects a method input parameter to the corresponding
            |method output parameter.
            |""".stripMargin
      )
      .protoId(12)

    methodParameterIn.addOutEdge(
      edge = parameterLink,
      inNode = methodParameterOut,
      stepNameIn = "asInput",
      stepNameOut = "asOutput",
      stepNameOutDoc = "Traverse to corresponding formal output parameter"
    )

    file
      .addOutEdge(edge = contains, inNode = typeDecl)
      .addOutEdge(edge = contains, inNode = method)

    method
      .addOutEdge(edge = contains, inNode = callNode)
      .addOutEdge(edge = contains, inNode = identifier)
      .addOutEdge(edge = contains, inNode = fieldIdentifier)
      .addOutEdge(
        edge = contains,
        inNode = literal,
        stepNameOut = "literal",
        stepNameOutDoc = "Literals used in the method"
      )
      .addOutEdge(edge = contains, inNode = ret)
      .addOutEdge(edge = contains, inNode = methodRef)
      .addOutEdge(edge = contains, inNode = typeRef)
      .addOutEdge(edge = contains, inNode = block)
      .addOutEdge(edge = contains, inNode = controlStructure)
      .addOutEdge(edge = contains, inNode = jumpTarget)
      .addOutEdge(edge = contains, inNode = unknown)

    methodParameterIn.addOutEdge(
      edge = evalType,
      inNode = tpe,
      cardinalityOut = Cardinality.One,
      stepNameOut = "typ",
      stepNameOutDoc = "Traverse to parameter type"
    )
    methodParameterOut.addOutEdge(
      edge = evalType,
      inNode = tpe,
      stepNameOut = "typ",
      stepNameOutDoc = "Traverse to parameter type"
    )
    methodReturn.addOutEdge(edge = evalType, inNode = tpe)

    methodRef
      .addOutEdge(
        edge = ref,
        inNode = method,
        cardinalityOut = Cardinality.One,
        stepNameOut = "referencedMethod",
        stepNameOutDoc = "Traverse to referenced method."
      )
      .addOutEdge(edge = evalType, inNode = tpe)

    typeRef.addOutEdge(edge = evalType, inNode = tpe)
    tpe.addOutEdge(
      edge = ref,
      inNode = typeDecl,
      stepNameOut = "referencedTypeDecl",
      stepNameOutDoc = "Type declaration which is referenced by this type."
    )
    typeDecl.addOutEdge(edge = contains, inNode = method)
    member.addOutEdge(edge = evalType, inNode = tpe, stepNameOut = "typ", stepNameOutDoc = "Traverse to member type")
    literal.addOutEdge(edge = evalType, inNode = tpe)

    callNode
      .addOutEdge(
        edge = ref,
        inNode = member,
        stepNameOut = "referencedMember",
        stepNameOutDoc = "Traverse to referenced members"
      )
      .addOutEdge(edge = evalType, inNode = tpe)

    local.addOutEdge(edge = evalType, inNode = tpe, stepNameOut = "typ", stepNameOutDoc = "The type of the local.")
    identifier.addOutEdge(edge = evalType, inNode = tpe)
    block.addOutEdge(edge = evalType, inNode = tpe)
    controlStructure.addOutEdge(edge = evalType, inNode = tpe)
    unknown.addOutEdge(edge = evalType, inNode = tpe)
  }

}
