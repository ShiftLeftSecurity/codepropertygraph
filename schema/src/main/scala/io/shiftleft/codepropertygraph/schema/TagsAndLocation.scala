package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import overflowdb.schema.Property.ValueType
import overflowdb.schema._

object TagsAndLocation extends SchemaBase {

  override def index: Int = 17

  override def description: String =
    """
      |The Code Property Graph specification allows for tags to be attached
      |to arbitrary nodes. Conceptually, this is similar to the creation of
      |Finding nodes, however, tags are to be used for intermediate results
      |rather than end-results that are to be reported to the user.
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            base: Base.Schema,
            typeSchema: Type.Schema,
            methodSchema: Method.Schema,
            ast: Ast.Schema,
            fs: FileSystem.Schema,
            callGraph: CallGraph.Schema) =
    new Schema(builder, base, typeSchema, methodSchema, ast, fs, callGraph)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               typeSchema: Type.Schema,
               methodSchema: Method.Schema,
               ast: Ast.Schema,
               fs: FileSystem.Schema,
               callGraph: CallGraph.Schema) {
    import base._
    import typeSchema._
    import methodSchema._
    import ast._
    import fs._
    import callGraph._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

// node properties
    val symbol = builder
      .addProperty(
        name = "SYMBOL",
        valueType = ValueType.String,
        comment = ""
      )
      .mandatory(PropertyDefaults.String)
      .protoId(100)

    val methodShortName = builder
      .addProperty(
        name = "METHOD_SHORT_NAME",
        valueType = ValueType.String,
        comment = ""
      )
      .mandatory(PropertyDefaults.String)
      .protoId(102)

    val packageName = builder
      .addProperty(
        name = "PACKAGE_NAME",
        valueType = ValueType.String,
        comment = ""
      )
      .mandatory(PropertyDefaults.String)
      .protoId(103)

    val className = builder
      .addProperty(
        name = "CLASS_NAME",
        valueType = ValueType.String,
        comment = ""
      )
      .mandatory(PropertyDefaults.String)
      .protoId(104)

    val classShortName = builder
      .addProperty(
        name = "CLASS_SHORT_NAME",
        valueType = ValueType.String,
        comment = ""
      )
      .mandatory(PropertyDefaults.String)
      .protoId(132)

    val nodeLabel = builder
      .addProperty(
        name = "NODE_LABEL",
        valueType = ValueType.String,
        comment = ""
      )
      .mandatory(PropertyDefaults.String)
      .protoId(105)

    val taggedBy = builder
      .addEdgeType(
        name = "TAGGED_BY",
        comment = "Edges from nodes to the tags they are tagged by."
      )
      .protoId(11)

    // node types

    val tag: NodeType = builder
      .addNodeType(
        name = "TAG",
        comment = "This node represents a tag."
      )
      .protoId(24)
      .addProperties(name, value)

    val location: NodeType = builder
      .addNodeType(
        name = "LOCATION",
        comment = "A location node summarizes a source code location."
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
        comment = "This node contains an arbitrary node and an associated tag node."
      )
      .protoId(208)
      .addProperties()

// node relations
    location
      .addContainedNode(builder.anyNode, "node", Property.Cardinality.ZeroOrOne)

    // TODO MP: provide dummy/empty node as default, to avoid null?
    tagNodePair
      .addContainedNode(tag, "tag", Property.Cardinality.One(Property.Default(null)))
      .addContainedNode(builder.anyNode, "node", Property.Cardinality.One(Property.Default(null)))

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
