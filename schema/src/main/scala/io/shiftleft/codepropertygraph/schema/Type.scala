package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object Type extends SchemaBase {

  def apply(builder: SchemaBuilder, base: Base.Schema, fs: FileSystem.Schema) =
    new Schema(builder, base, fs)

  def index: Int = 6
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      |The Type Layer contains information about type declarations, relations between
      |types and type usage.
      |""".stripMargin

  class Schema(builder: SchemaBuilder, base: Base.Schema, fs: FileSystem.Schema) {
    import base._
    import fs._
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

    val typeDecl: NodeType = builder
      .addNodeType(
        name = "TYPE_DECL",
        comment = """This node represents a type declaration as for example given by a class-, struct-,
            |or union declaration.
            |
            |The language frontend MUST create type declarations for all types declared in the
            |source program and MAY provide type declarations for types that are not declared
            |but referenced by the source program. If a declaration is present in the source
            |program, the field `IS_EXTERNAL` is set to `false`. Otherwise, it is set to `true`.
            |
            |The `FULL_NAME` field specifies the type's fully-qualified name, including
            |information about the namespace it is contained in if applicable, the name field
            |is the type's short name. Line and column number information is specified in the
            |optional fields `LINE_NUMBER`, `COLUMN_NUMBER`, `LINE_NUMBER_END`, and
            |`COLUMN_NUMBER_END` and the name of the source file is specified in `FILENAME`.
            |
            |Base types can be specified via the `INHERITS_FROM_TYPE_FULL_NAME` list, where
            |each entry contains the fully-qualified name of a base type. If the type is
            |known to be an alias of another type (as for example introduced via the C
            |`typedef` statement), the name of the alias is stored in `ALIAS_TYPE_FULL_NAME`.
            |
            |Finally, the fully qualified name of the program constructs that the type declaration
            |is immediately contained in is stored in the `AST_PARENT_FULL_NAME` field
            |and its type is indicated in the `AST_PARENT_TYPE` field to be one of
            |`METHOD`, `TYPE_DECL` or `NAMESPACE_BLOCK`.
            |
            |""".stripMargin
      )
      .protoId(46)
      .addProperties(name, fullName, isExternal, inheritsFromTypeFullName, aliasTypeFullName, filename)
      .addProperties(astParentType, astParentFullName)

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

    val member: NodeType = builder
      .addNodeType(
        name = "MEMBER",
        comment = "Member of a class struct or union"
      )
      .protoId(9)
      .addProperties(typeFullName)
      .extendz(declaration)

    val typeParameter: NodeType = builder
      .addNodeType(
        name = "TYPE_PARAMETER",
        comment = """This node represents a formal type parameter, that is, the type parameter
            |as given in a type-parametrized method or type declaration. Examples for
            |languages that support type parameters are Java (via Generics) and C++
            |(via templates). Apart from the standard fields of AST nodes, the type
            |parameter carries only a `NAME` field that holds the parameters name.
            |""".stripMargin
      )
      .protoId(47)
      .addProperties(name)

    val typeArgument: NodeType = builder
      .addNodeType(
        name = "TYPE_ARGUMENT",
        comment = """An (actual) type argument assigns a concrete type to a type parameter in the
                    |same way an (actual) argument provides a concrete value to a parameter.
                    |The frontend is not expected to interpret the type argument but instead, it
                    |merely places the code that corresponds to the type argument into the
                    |`CODE` field.
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

    typeDecl
      .addOutEdge(edge = sourceFile, inNode = file)

  }

}
