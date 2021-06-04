package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

object TagsAndLocation extends SchemaBase {

  override def index: Int = 17

  override def description: String =
    """
      |
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            base: Base.Schema,
            typeSchema: Type.Schema,
            methodSchema: Method.Schema,
            ast: Ast.Schema,
            fs: FileSystem.Schema) =
    new Schema(builder, base, typeSchema, methodSchema, ast, fs)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               typeSchema: Type.Schema,
               methodSchema: Method.Schema,
               ast: Ast.Schema,
               fs: FileSystem.Schema) {
    import base._
    import typeSchema._
    import methodSchema._
    import ast._
    import fs._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

// node properties
    val symbol = builder
      .addProperty(
        name = "SYMBOL",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(100)

    val methodShortName = builder
      .addProperty(
        name = "METHOD_SHORT_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(102)

    val packageName = builder
      .addProperty(
        name = "PACKAGE_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(103)

    val className = builder
      .addProperty(
        name = "CLASS_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(104)

    val classShortName = builder
      .addProperty(
        name = "CLASS_SHORT_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(132)

    val nodeLabel = builder
      .addProperty(
        name = "NODE_LABEL",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(105)

    val taggedBy = builder
      .addEdgeType(
        name = "TAGGED_BY",
        comment = "Edges from nodes to tags"
      )
      .protoId(11)

    // node types

    val tag: NodeType = builder
      .addNodeType(
        name = "TAG",
        comment = "A string tag"
      )
      .protoId(24)
      .addProperties(name, value)

    val location: NodeType = builder
      .addNodeType(
        name = "LOCATION",
        comment = ""
      )
      .protoId(25)
      .addProperties(symbol,
                     methodFullName,
                     methodShortName,
                     packageName,
                     lineNumber,
                     className,
                     classShortName,
                     nodeLabel,
                     filename)

    val tagNodePair: NodeType = builder
      .addNodeType(
        name = "TAG_NODE_PAIR",
        comment = ""
      )
      .protoId(208)
      .addProperties()

// node relations
    location
      .addContainedNode(builder.anyNode, "node", Cardinality.ZeroOrOne)

    tagNodePair
      .addContainedNode(tag, "tag", Cardinality.One)
      .addContainedNode(builder.anyNode, "node", Cardinality.One)

    method
      .addOutEdge(edge = taggedBy, inNode = tag)

    methodReturn
      .addOutEdge(edge = taggedBy, inNode = tag)

    literal
      .addOutEdge(edge = taggedBy, inNode = tag)

    local
      .addOutEdge(edge = taggedBy, inNode = tag)

    member
      .addOutEdge(edge = taggedBy, inNode = tag)

    callNode
      .addOutEdge(edge = taggedBy, inNode = tag)

    identifier
      .addOutEdge(edge = taggedBy, inNode = tag)

    fieldIdentifier
      .addOutEdge(edge = taggedBy, inNode = tag)

    methodParameterIn
      .addOutEdge(edge = taggedBy, inNode = tag)

    ret
      .addOutEdge(edge = taggedBy, inNode = tag)

    block
      .addOutEdge(edge = taggedBy, inNode = tag)

    unknown
      .addOutEdge(edge = taggedBy, inNode = tag)

    controlStructure
      .addOutEdge(edge = taggedBy, inNode = tag)

    methodRef
      .addOutEdge(edge = taggedBy, inNode = tag)

    typeRef
      .addOutEdge(edge = taggedBy, inNode = tag)

    jumpTarget
      .addOutEdge(edge = taggedBy, inNode = tag)

    file
      .addOutEdge(edge = taggedBy, inNode = tag)

    methodParameterOut
      .addOutEdge(edge = taggedBy, inNode = tag)

  }

}
