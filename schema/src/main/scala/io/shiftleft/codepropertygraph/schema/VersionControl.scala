package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import overflowdb.schema.Property.ValueType
import overflowdb.schema.{Constant, NodeType, SchemaBuilder, SchemaInfo}

object VersionControl extends SchemaBase {

  override def docIndex: Int = 21

  override def description: String =
    """
      |Version control systems (VCS) are a natural part of software development and, as such, the code property graph
      |will encode some key details about the VCS in use, if any. These details can help correlate the code version
      |corresponding to the generated CPG. One can choose to explore the development evolution of a versioned codebase
      |using the code property graph.
      |""".stripMargin

  def apply(builder: SchemaBuilder) = new Schema(builder)

  class Schema(builder: SchemaBuilder) {

    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)

    val vcsSystem = builder
      .addProperty(
        name = "VCS_SYSTEM",
        valueType = ValueType.String,
        comment =
          """This field holds the name of the version control system used. This corresponds to the systems that are
            |supported for being detected e.g. `GIT` or `SVN`.
            |""".stripMargin
      )
      .mandatory(PropertyDefaults.String)
      .protoId(2151)

    val versionControlSystems = builder.addConstants(
      category = "VersionControlSystems",
      Constant(
        name = "GIT",
        value = "GIT",
        valueType = ValueType.String,
        comment = "Git is a distributed version control system."
      ).protoId(2152),
      Constant(
        name = "SVN",
        value = "SVN",
        valueType = ValueType.String,
        comment = "Apache Subversion, a centralized version control system."
      ).protoId(2153)
    )

    val remote = builder
      .addProperty(
        name = "REMOTE",
        valueType = ValueType.String,
        comment = "This field holds the URI of the remote host of the repository if one is present."
      )
      .protoId(2154)

    val branch = builder
      .addProperty(
        name = "BRANCH",
        valueType = ValueType.String,
        comment =
          """This field holds the name of the current branch this version of the code is developed on. This is usually a
            |branch from a more stable branch.
            |""".stripMargin
      )
      .mandatory(PropertyDefaults.String)
      .protoId(2155)

    val author = builder
      .addProperty(
        name = "AUTHOR",
        valueType = ValueType.String,
        comment = "The name or alias of the developer or authored the code change."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(2156)

    val revisionId = builder
      .addProperty(
        name = "REVISION_ID",
        valueType = ValueType.String,
        comment = "The unique identifier of the code change revision object."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(2157)

    val parentRevisionId = builder
      .addProperty(
        name = "PARENT_REVISION_ID",
        valueType = ValueType.String,
        comment = "The unique identifier of the parent code change revision object."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(2158)

    val revisionMessage = builder
      .addProperty(
        name = "REVISION_MESSAGE",
        valueType = ValueType.String,
        comment = "The author's message describing the code change for this revision."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(2159)

    val modifiedFiles = builder
      .addProperty(
        name = "MODIFIED_FILES",
        valueType = ValueType.String,
        comment = "Full paths relative to the project root directory of files that were modified during the revision."
      )
      .asList()
      .protoId(2160)

    val tags = builder
      .addProperty(
        name = "REVISION_TAGS",
        valueType = ValueType.String,
        comment = "The tags associated with this revision. This does not include tags put on by forward commits."
      )
      .asList()
      .protoId(2161)

    val vcsNode: NodeType = builder
      .addNodeType(
        name = "VERSION_CONTROL",
        comment = """This node type represents version control system agnostic details about what version of a code base
            |generated this code property graph.
            |""".stripMargin
      )
      .protoId(2150)
      .addProperties(
        vcsSystem,
        remote,
        branch,
        author,
        revisionId,
        parentRevisionId,
        revisionMessage,
        modifiedFiles,
        tags
      )

  }
}
