package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import flatgraph.schema.Property.ValueType
import flatgraph.schema._

object Tags extends SchemaBase {

  override def docIndex: Int = 17

  override def description: String =
    """
      |The Code Property Graph specification allows for tags to be attached
      |to arbitrary nodes. Conceptually, this is similar to the creation of
      |Finding nodes, however, tags are to be used for intermediate results
      |rather than end-results that are to be reported to the user.
      |""".stripMargin

  def apply(
    builder: SchemaBuilder,
    base: Base.Schema,
    typeSchema: Type.Schema,
    methodSchema: Method.Schema,
    ast: Ast.Schema,
    fs: FileSystem.Schema,
    callGraph: CallGraph.Schema
  ) =
    new Schema(builder, base, typeSchema, methodSchema, ast, fs, callGraph)

  class Schema(
    builder: SchemaBuilder,
    base: Base.Schema,
    typeSchema: Type.Schema,
    methodSchema: Method.Schema,
    ast: Ast.Schema,
    fs: FileSystem.Schema,
    callGraph: CallGraph.Schema
  ) {
    import base._
    import typeSchema._
    import methodSchema._
    import ast._
    import fs._
    import callGraph._
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)

// node properties
    val symbol = builder
      .addProperty(name = "SYMBOL", valueType = ValueType.String, comment = "")
      .mandatory(PropertyDefaults.String)
      .protoId(ProtoIds.Symbol)

    val methodShortName = builder
      .addProperty(name = "METHOD_SHORT_NAME", valueType = ValueType.String, comment = "")
      .mandatory(PropertyDefaults.String)
      .protoId(ProtoIds.MethodShortName)

    val packageName = builder
      .addProperty(name = "PACKAGE_NAME", valueType = ValueType.String, comment = "")
      .mandatory(PropertyDefaults.String)
      .protoId(ProtoIds.PackageName)

    val className = builder
      .addProperty(name = "CLASS_NAME", valueType = ValueType.String, comment = "")
      .mandatory(PropertyDefaults.String)
      .protoId(ProtoIds.ClassName)

    val classShortName = builder
      .addProperty(name = "CLASS_SHORT_NAME", valueType = ValueType.String, comment = "")
      .mandatory(PropertyDefaults.String)
      .protoId(ProtoIds.ClassShortName)

    val nodeLabel = builder
      .addProperty(name = "NODE_LABEL", valueType = ValueType.String, comment = "")
      .mandatory(PropertyDefaults.String)
      .protoId(ProtoIds.NodeLabel)

    val taggedBy = builder
      .addEdgeType(name = "TAGGED_BY", comment = "Edges from nodes to the tags they are tagged by.")
      .protoId(ProtoIds.TaggedBy)

    // node types

    val tag: NodeType = builder
      .addNodeType(name = "TAG", comment = "This node represents a tag.")
      .protoId(ProtoIds.Tag)
      .addProperties(name, value)
      .primaryKey(name)

    val tagNodePair: NodeType = builder
      .addNodeType(name = "TAG_NODE_PAIR", comment = "This node contains an arbitrary node and an associated tag node.")
      .protoId(ProtoIds.TagNodePair)
      .addProperties()

    // TODO MP: provide dummy/empty node as default, to avoid null?
    tagNodePair
      .addContainedNode(tag, "tag", Property.Cardinality.One(Property.Default(null)))
      .addContainedNode(builder.anyNode, "node", Property.Cardinality.One(Property.Default(null)))

    Seq(
      method,
      methodReturn,
      literal,
      local,
      member,
      callNode,
      identifier,
      fieldIdentifier,
      methodParameterIn,
      ret,
      block,
      unknown,
      controlStructure,
      methodRef,
      typeRef,
      jumpTarget,
      file,
      methodParameterOut,
      tag,
      typeDecl
    ).foreach(_.addOutEdge(edge = taggedBy, inNode = tag))
  }

}
