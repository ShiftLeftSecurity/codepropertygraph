package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

/**
  * This is only intended for Java.
  */
object JavaSpecific {
  def apply(builder: SchemaBuilder, base: Base.Schema, enhancements: Enhancements.Schema) =
    new Schema(builder, base, enhancements)

  class Schema(builder: SchemaBuilder, base: Base.Schema, enhancements: Enhancements.Schema) {
    import base._
    import enhancements._

// node properties

    val content = builder
      .addProperty(
        name = "CONTENT",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Content of CONFIG_FILE node"
      )
      .protoId(20)

// node types
    val annotation: NodeType = builder
      .addNodeType(
        name = "ANNOTATION",
        comment = "A method annotation"
      )
      .protoId(5)
      .addProperties(code, name, fullName)
      .extendz(astNode)

    val annotationParameterAssign: NodeType = builder
      .addNodeType(
        name = "ANNOTATION_PARAMETER_ASSIGN",
        comment = "Assignment of annotation argument to annotation parameter"
      )
      .protoId(6)
      .addProperties(code)
      .extendz(astNode)

    val annotationParameter: NodeType = builder
      .addNodeType(
        name = "ANNOTATION_PARAMETER",
        comment = "Formal annotation parameter"
      )
      .protoId(7)
      .addProperties(code)
      .extendz(astNode)

    val annotationLiteral: NodeType = builder
      .addNodeType(
        name = "ANNOTATION_LITERAL",
        comment = "A literal value assigned to an ANNOTATION_PARAMETER"
      )
      .protoId(49)
      .addProperties(name)
      .extendz(expression)

    arrayInitializer
      .extendz(expression)

    methodParameterIn
      .addProperties()

    typeDecl
      .addProperties()

    member
      .addProperties()

    val configFile: NodeType = builder
      .addNodeType(
        name = "CONFIG_FILE",
        comment = "Configuration file contents. Might be in use by a framework"
      )
      .protoId(50)
      .addProperties(name, content)
      .extendz(trackingPoint)

// node relations
    annotation
      .addOutEdge(edge = ast, inNode = annotationParameterAssign)

    annotationParameterAssign
      .addOutEdge(edge = ast, inNode = annotationParameter)
      .addOutEdge(edge = ast, inNode = arrayInitializer)
      .addOutEdge(edge = ast, inNode = annotationLiteral)
      .addOutEdge(edge = ast, inNode = annotation)

    arrayInitializer
      .addOutEdge(edge = ast, inNode = literal)
      .addOutEdge(edge = evalType, inNode = tpe)

    method
      .addOutEdge(edge = ast, inNode = annotation)

    methodParameterIn
      .addOutEdge(edge = ast, inNode = annotation)

    typeDecl
      .addOutEdge(edge = ast, inNode = annotation)

    member
      .addOutEdge(edge = ast, inNode = annotation)
// constants

  }

}
