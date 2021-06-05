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
      |Input parameterss output parameters (including return parameters) and local variables are
      |represented, however, method contents is not present in this layer.
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
        comment = """
                    |The method signature encodes the types of parameters in a string.
                    |The string SHOULD be human readable and suitable for differentiating methods
                    |with different parameter types sufficiently to allow for resolving of
                    |function overloading. The present specification does not enforce a strict
                    |format for the signature, that is, it can be chosen by the frontend
                    |implementor to fit the source language.
                    |""".stripMargin
      )
      .protoId(22)

    val method: NodeType = builder
      .addNodeType(
        name = "METHOD",
        comment = """Programming languages offer many closely-related concepts for describing blocks
            |of code that can be executed with input parameters and return output parameters,
            |possibly causing side effects. In the CPG specification, we refer to all of these
            |concepts (procedures, functions, methods, etc.) as methods. A single METHOD node
            |must exist for each method found in the source program.
            |
            |The `FULL_NAME` field specifies the method's fully-qualified name, including
            |information about the namespace it is contained in if applicable, the name field
            |is the function's short name. The field `IS_EXTERNAL` indicates whether it was
            |possible to identify a method body for the method. This is true for methods that
            |are defined in the source program, and false for methods that are dynamically
            |linked to the program, that is, methods that exist in an external dependency.
            |
            |Line and column number information is specified in the optional fields
            |`LINE_NUMBER`, `COLUMN_NUMBER`, `LINE_NUMBER_END`, and `COLUMN_NUMBER_END` and
            |the name of the source file is specified in `FILENAME`. An optional hash value
            |MAY be calculated over the function contents and included in the `HASH` field.
            |""".stripMargin
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

    method
      .addOutEdge(edge = sourceFile, inNode = file)

  }

}
