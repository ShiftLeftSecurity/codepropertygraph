package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object Method extends SchemaBase {

  def apply(builder: SchemaBuilder, base: Base.Schema, typeSchema: Type.Schema, fs: FileSystem.Schema) =
    new Schema(builder, base, typeSchema, fs)

  def index: Int = 5
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      |The Method Layer contains declarations of methods, functions, and procedures.
      |""".stripMargin

  class Schema(builder: SchemaBuilder, base: Base.Schema, typeDeclSchema: Type.Schema, fs: FileSystem.Schema) {
    import base._
    import typeDeclSchema._
    import fs._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    val signature = builder
      .addProperty(
        name = "SIGNATURE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """Method signature. The format is defined by the language front-end, and the
                    |backend simply compares strings to resolve function overloading, i.e. match
                    |call-sites to METHODs. In theory, consecutive integers would be valid,
                    |but in practice this should be human readable
                    |""".stripMargin
      )
      .protoId(22)

    val method: NodeType = builder
      .addNodeType(
        name = "METHOD",
        comment = "A method/function/procedure"
      )
      .protoId(1)
      .addProperties(fullName, isExternal, signature, lineNumberEnd, columnNumberEnd, filename, hash)
      .extendz(declaration)

    method
      .addProperties(astParentType, astParentFullName)

    val methodParameterIn: NodeType = builder
      .addNodeType(
        name = "METHOD_PARAMETER_IN",
        comment = "This node represents a formal parameter going towards the callee side"
      )
      .protoId(34)
      .addProperties(typeFullName)
      .extendz(declaration, localLike)

    val methodParameterOut: NodeType = builder
      .addNodeType(
        name = "METHOD_PARAMETER_OUT",
        comment = "This node represents a formal output parameter. It does not need to be created by the frontend."
      )
      .protoId(33)
      .addProperties(typeFullName)
      .extendz(declaration)

    val local: NodeType = builder
      .addNodeType(
        name = "LOCAL",
        comment = "A local variable"
      )
      .protoId(23)
      .addProperties(typeFullName)
      .extendz(declaration, localLike)

    val methodReturn: NodeType = builder
      .addNodeType(
        name = "METHOD_RETURN",
        comment = "A formal method return"
      )
      .protoId(3)
      .addProperties(typeFullName)

    val parameterLink = builder
      .addEdgeType(
        name = "PARAMETER_LINK",
        comment = "Links together corresponding METHOD_PARAMETER_IN and METHOD_PARAMETER_OUT nodes. Created by backend."
      )
      .protoId(12)

    methodParameterIn
      .addOutEdge(edge = parameterLink, inNode = methodParameterOut)

    // To be removed

    val vtable = builder
      .addEdgeType(
        name = "VTABLE",
        comment = "Indicates that a method is part of the vtable of a certain type declaration"
      )
      .protoId(30)

    typeDecl.addOutEdge(edge = vtable, inNode = method)

    method
      .addOutEdge(edge = sourceFile, inNode = file)

  }

}
