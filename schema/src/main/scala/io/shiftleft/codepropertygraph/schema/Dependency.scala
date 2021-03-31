package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

object Dependency {
  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._

// node properties
    val dependencyGroupId = builder
      .addNodeProperty(
        name = "DEPENDENCY_GROUP_ID",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = "The group ID for a dependency"
      )
      .protoId(58)

// node types
    val dependency: NodeType = builder
      .addNodeType(
        name = "DEPENDENCY",
        comment = "This node represents a dependency"
      )
      .protoId(35)
      .addProperties(version, name, dependencyGroupId)

  }

}
