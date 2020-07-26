package io.shiftleft.semanticcpg.language

import gremlin.scala.{BranchCase, BranchOtherwise, GremlinScala, P, Vertex}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.codedumper.CodeDumper
import overflowdb.traversal.help
import overflowdb.traversal.help.Doc

/**
  * Steps for all node types
  *
  * This is the base class for all steps defined on nodes.
  * */
@help.Traversal(elementType = classOf[nodes.StoredNode])
class NodeSteps[NodeType <: nodes.StoredNode](raw: GremlinScala[NodeType]) extends Steps[NodeType](raw) {

  @Doc(
    "Label of the node (its type)",
    """
      |Each node has at least a unique id and a string label. The label
      |is a node type. Examples are `FILE`, `METHOD`, or `IDENTIFIER`.
      |""".stripMargin
  )
  def label: Steps[String] = new Steps(raw.label)

  @Doc("Traverse to ids of underlying nodes")
  def id: Steps[Long] = new Steps(raw.map(_.id2))

  @Doc("Filter: only keep node with given id")
  def id(value: Long): Steps[NodeType] =
    new Steps(raw.filterOnEnd(_.id2 == value))

  @Doc("Filter: only keep nodes with given ids")
  def id(values: Long*): Steps[NodeType] =
    id(Set(values: _*))

  @Doc("Filter: only keep nodes with given ids")
  def id(values: Set[Long]): Steps[NodeType] =
    new Steps(raw.filterOnEnd(node => values.contains(node.id2)))

  @Doc(
    "The source file this code is in",
    """
      |Not all but most node in the graph can be associated with
      |a specific source file they appear in. `file` provides
      |the file node that represents that source file.
      |""".stripMargin
  )
  def file: NodeSteps[nodes.File] =
    new NodeSteps(
      raw
        .choose(
          on = _.label,
          BranchCase(NodeTypes.NAMESPACE, _.in(EdgeTypes.REF).out(EdgeTypes.SOURCE_FILE)),
          BranchCase(NodeTypes.COMMENT, _.in(EdgeTypes.AST).hasLabel(NodeTypes.FILE)),
          BranchOtherwise(
            _.until(_.hasLabel(NodeTypes.FILE)).repeat(_.coalesce(_.out(EdgeTypes.SOURCE_FILE), _.in(EdgeTypes.AST)))),
        )
        .cast[nodes.File]
    )

  @Doc(
    "Location, including filename and line number",
    """
      |Most nodes of the graph can be associated with a specific
      |location in code, and `location` provides this location.
      |The return value is an object providing, e.g., filename,
      |line number, and method name, as opposed to being a flat
      |string. For example `.location.lineNumber` provides access
      |to the line number alone, without requiring any parsing
      |on the user's side.
      |""".stripMargin
  )
  def location: NewNodeSteps[nodes.NewLocation] =
    new NewNodeSteps(raw.map(_.location))

  @Doc(
    "Display code (with syntax highlighting)",
    """
      |For methods, dump the method code. For expressions,
      |dump the method code along with an arrow pointing
      |to the expression. Uses ansi-color highlighting.
      |This only works for source frontends.
      |""".stripMargin
  )
  def dump: List[String] =
    _dump(highlight = true)

  @Doc(
    "Display code (without syntax highlighting)",
    """
      |For methods, dump the method code. For expressions,
      |dump the method code along with an arrow pointing
      |to the expression. No color highlighting.
      |""".stripMargin
  )
  def dumpRaw: List[String] =
    _dump(highlight = false)

  private def _dump(highlight: Boolean): List[String] = {
    var language: Option[String] = null // initialized on first element - need the graph for this
    raw.l.map { node =>
      if (language == null) language = new Cpg(node.graph2()).metaData.language.headOption()
      CodeDumper.dump(node.location, language, highlight)
    }
  }

  /* follow the incoming edges of the given type as long as possible */
  protected def walkIn(edgeType: String): GremlinScala[Vertex] =
    raw
      .repeat(_.in(edgeType))
      .until(_.in(edgeType).count.is(P.eq(0)))

  @Doc(
    "Tag node with `tagName`",
    """
      |This method can be used to tag nodes in the graph such that
      |they can later be looked up easily via `cpg.tag`. Tags are
      |key value pairs, and they can be created with `newTagNodePair`.
      |Since in many cases, a key alone is sufficient, we provide the
      |utility method `newTagNode(key)`, which is equivalent to
      |`newTagNode(key, "")`.
      |""".stripMargin,
    """.newTagNode("foo")"""
  )
  def newTagNode(tagName: String): NewTagNodePair = newTagNodePair(tagName, "")

  @Doc("Tag node with (`tagName`, `tagValue`)", "", """.newTagNodePair("key","val")""")
  def newTagNodePair(tagName: String, tagValue: String): NewTagNodePair = {
    new NewTagNodePair(
      raw.map { node =>
        nodes.NewTagNodePair(nodes.NewTag(tagName, tagValue), node)
      }
    )
  }

  @Doc("Tags attached to this node")
  def tagList: List[List[nodes.TagBase]] =
    raw.map { taggedNode =>
      taggedNode.tagList
    }.l

}
