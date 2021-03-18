package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._

object Dom {
  def apply(
      builder: SchemaBuilder,
      base: Base.Schema,
      enhancements: Enhancements.Schema,
      javaSpecific: JavaSpecific.Schema
  ) =
    new Schema(builder, base, enhancements, javaSpecific)

  class Schema(
      builder: SchemaBuilder,
      base: Base.Schema,
      enhancements: Enhancements.Schema,
      javaSpecific: JavaSpecific.Schema
  ) {
    import base._
    import javaSpecific._
    import enhancements._

// node types
val domNode: NodeType = builder.addNodeType(
  name = "DOM_NODE",
  comment = "A node in a Document Object Model (Tree) as obtained from, e.g., an HTML parser"
).protoId(600)

.addProperties(name)


val domAttribute: NodeType = builder.addNodeType(
  name = "DOM_ATTRIBUTE",
  comment = "Attribute of a DOM node"
).protoId(601)

.addProperties(name, value)


// node relations
domNode
.addOutEdge(edge = ast, inNode = domNode)
.addContainedNode(domAttribute, "attributes", Cardinality.List)

configFile
.addOutEdge(edge = contains, inNode = domNode)


// constants

  }

}
