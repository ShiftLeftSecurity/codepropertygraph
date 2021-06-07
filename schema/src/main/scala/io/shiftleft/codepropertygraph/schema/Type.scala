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
      |types, and type instantiation and usage. In its current form, it allows moedling of
      |parametrized types, type hierarchies and aliases.
      |""".stripMargin

  class Schema(builder: SchemaBuilder, base: Base.Schema, fs: FileSystem.Schema) {
    import base._
    import fs._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    // Properties

    val typeFullName = builder
      .addProperty(
        name = "TYPE_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """This field contains the fully-qualified static type name of the program
                    |construct represented by a node. It is the name of an instantiated type, e.g.,
                    |`List<Integer>`, rather than `List[T]`.
                    |""".stripMargin
      )
      .protoId(51)

    val aliasTypeFullName = builder
      .addProperty(
        name = "ALIAS_TYPE_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = """This property holds the fully qualified name of the type that the node is
            |a type alias of.
            |""".stripMargin
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

    // Nodes

    val typeDecl: NodeType = builder
      .addNodeType(
        name = "TYPE_DECL",
        comment = """This node represents a type declaration as for example given by a class-, struct-,
            |or union declaration. In contrast to a `TYPE` node, this node does not represent a
            |concrete instantiation of a type, e.g., for the parametrized type `List[T]`, it represents
            |`List[T]`, but not `List[Integer]` where `Integer` is a concrete type.
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
        comment = """An (actual) type argument as used to instantiate a parametrized type, in the
                    |same way an (actual) arguments provides concrete values for a parameter
                    |at method call sites. As it true for arguments, the method is not expected
                    |to  interpret the type argument. It MUST however store its code in the
                    |`CODE` field.
                    |""".stripMargin
      )
      .protoId(48)

    val member: NodeType = builder
      .addNodeType(
        name = "MEMBER",
        comment = """This node represents a type member of a class, struct or union, e.g., for the
                    | type declaration `class Foo{ int i ; }`, it represents the declaration of the
                    | variable `i`.
                    |""".stripMargin
      )
      .protoId(9)
      .addProperties(typeFullName)
      .extendz(declaration)

    val tpe: NodeType = builder
      .addNodeType(
        name = "TYPE",
        comment = """This node represents a type instance, that is, a concrete instantiation
                    |of a type declaration.""".stripMargin
      )
      .protoId(45)
      .addProperties(name, fullName, typeDeclFullName)

    // edges

    val bindsTo = builder
      .addEdgeType(
        name = "BINDS_TO",
        comment = """This edge connects type arguments to type parameters to indicate
            |that the type argument is used to instantiate the type parameter.
            |""".stripMargin
      )
      .protoId(22)

    val aliasOf = builder
      .addEdgeType(
        name = "ALIAS_OF",
        comment = """This edge represents an alias relation between a type declaration and a type.
            |The language frontend MUST NOT create `ALIAS_OF` edges as they are created
            |automatically based on `ALIAS_TYPE_FULL_NAME` fields when the CPG is first loaded.
            |""".stripMargin
      )
      .protoId(138)

    val inheritsFrom = builder
      .addEdgeType(
        name = "INHERITS_FROM",
        comment = """Inheritance relation between a type declaration and a type. This edge MUST NOT
            | be created by the language frontend as it is automatically created from
            | `INHERITS_FROM_TYPE_FULL_NAME` fields then the CPG is first loaded.
            |""".stripMargin
      )
      .protoId(23)

    typeDecl
      .addOutEdge(edge = inheritsFrom, inNode = tpe)
      .addOutEdge(edge = aliasOf, inNode = tpe)
      .addOutEdge(edge = sourceFile, inNode = file)

    typeArgument
      .addOutEdge(edge = bindsTo, inNode = typeParameter)
      .addOutEdge(edge = ref, inNode = tpe)

  }

}
