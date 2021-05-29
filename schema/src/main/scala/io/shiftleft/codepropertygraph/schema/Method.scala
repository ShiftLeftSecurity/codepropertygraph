package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object Method extends SchemaBase {

  def apply(builder: SchemaBuilder, base: Base.Schema, typeSchema: Type.Schema) =
    new Schema(builder, base, typeSchema)

  def index: Int = 5
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      | Structural layer (namespace blocks, method declarations, and type declarations).
      | This layer is provided by the frontend and may be modified by passes.
      |""".stripMargin

  class Schema(builder: SchemaBuilder, base: Base.Schema, typeSchema: Type.Schema) {
    import base._
    import typeSchema._
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

    val lineNumberEnd = builder
      .addProperty(
        name = "LINE_NUMBER_END",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Line where the code ends"
      )
      .protoId(12)

    val columnNumberEnd = builder
      .addProperty(
        name = "COLUMN_NUMBER_END",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Column where the code ends"
      )
      .protoId(16)

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
      .extendz(declaration, localLike, trackingPoint)

    val methodParameterOut: NodeType = builder
      .addNodeType(
        name = "METHOD_PARAMETER_OUT",
        comment = "This node represents a formal output parameter. It does not need to be created by the frontend."
      )
      .protoId(33)
      .addProperties(typeFullName)
      .extendz(declaration, trackingPoint)

    val local: NodeType = builder
      .addNodeType(
        name = "LOCAL",
        comment = "A local variable"
      )
      .protoId(23)
      .addProperties(typeFullName, lineNumber, columnNumber)
      .extendz(declaration, localLike)

    val methodReturn: NodeType = builder
      .addNodeType(
        name = "METHOD_RETURN",
        comment = "A formal method return"
      )
      .protoId(3)
      .addProperties(typeFullName)
      .extendz(trackingPoint)

    val binding: NodeType = builder
      .addNodeType(
        name = "BINDING",
        comment = "A binding of a METHOD into a TYPE_DECL"
      )
      .protoId(146)
      .addProperties(name, signature)

    val isMethodNeverOverridden = builder
      .addProperty(
        name = "IS_METHOD_NEVER_OVERRIDDEN",
        valueType = ValueTypes.BOOLEAN,
        cardinality = Cardinality.ZeroOrOne,
        comment =
          "True if the referenced method is never overridden by the subclasses and false otherwise. This can be left blank by the frontend."
      )
      .protoId(1002)

    binding
      .addProperties(isMethodNeverOverridden)

    binding
      .addOutEdge(edge = ref, inNode = method, cardinalityOut = Cardinality.One)

    val binds = builder
      .addEdgeType(
        name = "BINDS",
        comment = "Relation between TYPE_DECL and BINDING node"
      )
      .protoId(155)

    typeDecl
      .addOutEdge(edge = binds, inNode = binding, cardinalityIn = Cardinality.One)

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

  }

}
