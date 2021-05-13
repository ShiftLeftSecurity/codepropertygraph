package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, NodeType, SchemaBuilder}

object TypeDecl extends SchemaBase {

  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)

  override def index: Int = 2

  override def description: String =
    """
      |
      |""".stripMargin

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._

    val typeDecl: NodeType = builder
      .addNodeType(
        name = "TYPE_DECL",
        comment = "A type declaration"
      )
      .protoId(46)
      .addProperties(name, fullName, isExternal, inheritsFromTypeFullName, aliasTypeFullName, filename)
      .extendz(astNode)

    val member: NodeType = builder
      .addNodeType(
        name = "MEMBER",
        comment = "Member of a class struct or union"
      )
      .protoId(9)
      .addProperties(code, typeFullName)
      .extendz(declaration, astNode)

    member.addOutEdge(edge = ast, inNode = modifier)

    val typeParameter: NodeType = builder
      .addNodeType(
        name = "TYPE_PARAMETER",
        comment = "Type parameter of TYPE_DECL or METHOD"
      )
      .protoId(47)
      .addProperties(name)
      .extendz(astNode)

    val typeArgument: NodeType = builder
      .addNodeType(
        name = "TYPE_ARGUMENT",
        comment = """Argument for a TYPE_PARAMETER that belongs to a TYPE. It binds another
                    |TYPE to a TYPE_PARAMETER
                    |""".stripMargin
      )
      .protoId(48)
      .extendz(astNode)

    val tpe: NodeType = builder
      .addNodeType(
        name = "TYPE",
        comment = """A type which always has to reference a type declaration and may have type
                    |argument children if the referred to type declaration is a template""".stripMargin
      )
      .protoId(45)
      .addProperties(name, fullName, typeDeclFullName)

    tpe
      .addOutEdge(edge = ast, inNode = typeArgument)

    typeArgument
      .addOutEdge(edge = ref, inNode = tpe)

    typeArgument
      .addOutEdge(edge = bindsTo, inNode = typeParameter)

    typeDecl
      .addOutEdge(edge = ast, inNode = typeParameter)
      .addOutEdge(edge = ast, inNode = member, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = modifier, cardinalityIn = Cardinality.One)

    methodInst
      .addOutEdge(edge = ast, inNode = typeArgument)

    unknown
      .addOutEdge(edge = ast, inNode = member)

  }

}
