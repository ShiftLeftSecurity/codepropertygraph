package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object Type extends SchemaBase {

  def apply(builder: SchemaBuilder, base: Base.Schema) =
    new Schema(builder, base)

  def index: Int = 6
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      | Type layer (local).
      |""".stripMargin

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    val typeFullName = builder
      .addProperty(
        name = "TYPE_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """The static type of an entity. E.g. expressions, local, parameters etc.
                    |This property is matched against the FULL_NAME of TYPE nodes and thus it
                    |is required to have at least one TYPE node for each TYPE_FULL_NAME
                    |""".stripMargin
      )
      .protoId(51)

    val typeDeclFullName = builder
      .addProperty(
        name = "TYPE_DECL_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """The static type decl of a TYPE. This property is matched against the FULL_NAME
                    |of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each
                    |different TYPE_DECL_FULL_NAME""".stripMargin
      )
      .protoId(52)

    val aliasTypeFullName = builder
      .addProperty(
        name = "ALIAS_TYPE_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Type full name of which a TYPE_DECL is an alias of"
      )
      .protoId(158)

    val inheritsFromTypeFullName = builder
      .addProperty(
        name = "INHERITS_FROM_TYPE_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.List,
        comment = """The static types a TYPE_DECL inherits from. This property is matched against the
                    |FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node
                    |for each TYPE_FULL_NAME""".stripMargin
      )
      .protoId(53)

    val bindsTo = builder
      .addEdgeType(
        name = "BINDS_TO",
        comment = "Type argument binding to a type parameter"
      )
      .protoId(22)

    val typeDeclAlias = builder
      .addEdgeType(
        name = "TYPE_DECL_ALIAS",
        comment = "Alias relation between two TYPE_DECL"
      )
      .protoId(139)

    val aliasOf = builder
      .addEdgeType(
        name = "ALIAS_OF",
        comment = "Alias relation between types. Created by backend passes."
      )
      .protoId(138)

    val inheritsFrom = builder
      .addEdgeType(
        name = "INHERITS_FROM",
        comment = "Inheritance relation between types"
      )
      .protoId(23)

    val typeDecl: NodeType = builder
      .addNodeType(
        name = "TYPE_DECL",
        comment = "A type declaration"
      )
      .protoId(46)
      .addProperties(name, fullName, isExternal, inheritsFromTypeFullName, aliasTypeFullName, filename)

    typeDecl
      .addProperties(astParentType, astParentFullName)

    val member: NodeType = builder
      .addNodeType(
        name = "MEMBER",
        comment = "Member of a class struct or union"
      )
      .protoId(9)
      .addProperties(code, typeFullName)
      .extendz(declaration)

    val typeParameter: NodeType = builder
      .addNodeType(
        name = "TYPE_PARAMETER",
        comment = "Type parameter of TYPE_DECL or METHOD"
      )
      .protoId(47)
      .addProperties(name)

    val typeArgument: NodeType = builder
      .addNodeType(
        name = "TYPE_ARGUMENT",
        comment = """Argument for a TYPE_PARAMETER that belongs to a TYPE. It binds another
                    |TYPE to a TYPE_PARAMETER
                    |""".stripMargin
      )
      .protoId(48)

    val tpe: NodeType = builder
      .addNodeType(
        name = "TYPE",
        comment = """A type which always has to reference a type declaration and may have type
                    |argument children if the referred to type declaration is a template""".stripMargin
      )
      .protoId(45)
      .addProperties(name, fullName, typeDeclFullName)

    typeDecl
      .addOutEdge(edge = inheritsFrom, inNode = tpe)

    typeArgument
      .addOutEdge(edge = bindsTo, inNode = typeParameter)

    typeDecl
      .addOutEdge(edge = typeDeclAlias, inNode = typeDecl)

    typeDecl
      .addOutEdge(edge = aliasOf, inNode = tpe)

  }

}
