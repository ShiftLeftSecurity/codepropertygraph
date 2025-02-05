package io.shiftleft.codepropertygraph.schema

import flatgraph.schema.{NodeType, SchemaBuilder, SchemaInfo}

object Configuration extends SchemaBase {

  override def docIndex: Int = 18

  override def description: String =
    """
      |The code property graph specification currently does not contain
      |schema elements for the representation of configuration files in
      |a structured format, however, it does allow configuration files
      |to be included verbatim in the graph to enable language-/framework-
      |specific passes to access them. This layer provides the necessary
      |schema elements for this basic support of configuration files.
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)

  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    import base._

    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)

    val configFile: NodeType = builder
      .addNodeType(
        name = "CONFIG_FILE",
        comment = """This node type represent a configuration file, where `NAME` is the name
                    |of the file and `content` is its content. The exact representation of the
                    |name is left undefined and can be chosen as required by consumers of
                    |the corresponding configuration files.
                    |""".stripMargin
      )
      .protoId(ProtoIds.ConfigFile)
      .addProperties(name, content)
  }
}
