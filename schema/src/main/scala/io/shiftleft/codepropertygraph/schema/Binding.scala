package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object Binding extends SchemaBase {

  def apply(builder: SchemaBuilder, base: Base.Schema, typeSchema: Type.Schema, methodSchema: Method.Schema) =
    new Schema(builder, base, typeSchema, methodSchema)

  def index: Int = 13
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      | Structural layer (namespace blocks, method declarations, and type declarations).
      | This layer is provided by the frontend and may be modified by passes.
      |""".stripMargin

  class Schema(builder: SchemaBuilder, base: Base.Schema, typeDeclSchema: Type.Schema, methodSchema: Method.Schema) {

    import base._
    import typeDeclSchema._
    import methodSchema._

    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    val isMethodNeverOverridden = builder
      .addProperty(
        name = "IS_METHOD_NEVER_OVERRIDDEN",
        valueType = ValueTypes.BOOLEAN,
        cardinality = Cardinality.ZeroOrOne,
        comment =
          "True if the referenced method is never overridden by the subclasses and false otherwise. This can be left blank by the frontend."
      )
      .protoId(1002)

    val binding: NodeType = builder
      .addNodeType(
        name = "BINDING",
        comment = "A binding of a METHOD into a TYPE_DECL"
      )
      .protoId(146)
      .addProperties(name, signature)
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

  }

}
