package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, NodeType, SchemaBuilder, SchemaInfo}

object Method extends SchemaBase {

  def apply(builder: SchemaBuilder, base: Base.Schema, typeDeclSchema: TypeDecl.Schema) =
    new Schema(builder, base, typeDeclSchema)

  def index: Int = 2
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      | Structural layer (namespace blocks, method declarations, and type declarations).
      | This layer is provided by the frontend and may be modified by passes.
      |""".stripMargin

  class Schema(builder: SchemaBuilder, base: Base.Schema, typeDeclSchema: TypeDecl.Schema) {
    import base._
    import typeDeclSchema._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    val method: NodeType = builder
      .addNodeType(
        name = "METHOD",
        comment = "A method/function/procedure"
      )
      .protoId(1)
      .addProperties(fullName, isExternal, signature, lineNumberEnd, columnNumberEnd, filename, hash)
      .extendz(declaration, cfgNode, astNode)

    val methodParameterIn: NodeType = builder
      .addNodeType(
        name = "METHOD_PARAMETER_IN",
        comment = "This node represents a formal parameter going towards the callee side"
      )
      .protoId(34)
      .addProperties(code, typeFullName, lineNumber, columnNumber)
      .extendz(declaration, localLike, trackingPoint, astNode)

    val methodReturn: NodeType = builder
      .addNodeType(
        name = "METHOD_RETURN",
        comment = "A formal method return"
      )
      .protoId(3)
      .addProperties(typeFullName)
      .extendz(cfgNode, trackingPoint)

    method
      .addOutEdge(edge = ast, inNode = methodReturn, cardinalityOut = Cardinality.One, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = methodParameterIn, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = modifier, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = typeParameter, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = cfg,
                  inNode = methodReturn,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)

    val vtable = builder
      .addEdgeType(
        name = "VTABLE",
        comment = "Indicates that a method is part of the vtable of a certain type declaration"
      )
      .protoId(30)

    typeDecl
      .addOutEdge(edge = vtable, inNode = method)

  }

}
