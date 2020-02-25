package io.shiftleft.semanticcpg.language

import gremlin.scala.{BranchCase, BranchOtherwise, GremlinScala, P, Vertex}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.codedumper.CodeDumper
import io.shiftleft.semanticcpg.language.types.structure.File

/**
  * Steps for all node types
  *
  * This is the base class for all steps defined on nodes.
  * */
class NodeSteps[NodeType <: nodes.StoredNode](raw: GremlinScala[NodeType]) extends Steps[NodeType](raw) {

  /**
    * Traverse to node labels
    * */
  def label: Steps[String] = new Steps(raw.label)

  /**
    * Traverse to source file
    * */
  def file: NodeSteps[nodes.File] =
    new NodeSteps(
      raw
        .choose(
          on = _.label,
          BranchCase(NodeTypes.NAMESPACE, _.in(EdgeTypes.REF).in(EdgeTypes.AST)),
          BranchCase(NodeTypes.COMMENT, _.in(EdgeTypes.AST).hasLabel(NodeTypes.FILE)),
          BranchOtherwise(_.until(_.hasLabel(NodeTypes.FILE)).repeat(_.in(EdgeTypes.AST)))
        )
        .cast[nodes.File]
    )

  /**
    * Execute traversal and map each node to location.
    * */
  def location: NewLocation =
    new NewLocation(raw.map(x => x.location))

  /**
    * For methods, dump the method code. For expressions,
    * dump the method code along with an arrow pointing
    * to the expression. Uses ansi-color highlighting.
    * */
  def dump: List[String] = CodeDumper.dump(this, true)

  /**
    * For methods, dump the method code. For expressions,
    * dump the method code along with an arrow pointing
    * to the expression. No color highlighting.
    * */
  def dumpRaw: List[String] = CodeDumper.dump(this, false)

  /* follow the incoming edges of the given type as long as possible */
  protected def walkIn(edgeType: String): GremlinScala[Vertex] =
    raw
      .repeat(_.in(edgeType))
      .until(_.in(edgeType).count.is(P.eq(0)))

  def toMaps(): Steps[Map[String, Any]] =
    new Steps[Map[String, Any]](raw.map(_.toMap))

  /**
    * Execute traversal and create new (tag, "") pair.
    * */
  def newTagNode(tagName: String): NewTagNodePair[NodeType] = newTagNodePair(tagName, "")

  /**
    * Execute traversal and create new (tag, node) pair.
    * */
  def newTagNodePair(tagName: String, tagValue: String): NewTagNodePair[NodeType] = {
    new NewTagNodePair[NodeType](
      raw.map { node =>
        nodes.NewTagNodePair(nodes.NewTag(tagName, tagValue), node)
      }
    )
  }

  /**
  Execute traversal and map each node to the list of its associated tags.
    */
  def tagList: List[List[nodes.TagBase]] =
    raw.map { taggedNode =>
      taggedNode.tagList
    }.l

}
