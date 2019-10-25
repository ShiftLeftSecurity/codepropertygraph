package io.shiftleft.semanticcpg.language

import gremlin.scala.{GremlinScala, P, Vertex}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
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
  def file: File =
    new File(
      raw
        .choose(
          _.label.is(NodeTypes.NAMESPACE),
          onTrue = _.in(EdgeTypes.REF).in(EdgeTypes.AST),
          onFalse = _.until(_.hasLabel(NodeTypes.FILE)).repeat(_.in(EdgeTypes.AST))
        )
        .cast[nodes.File]
    )

  /**
    * Execute traversal and map each node to location.
    * */
  def location: NewLocation =
    new NewLocation(raw.map(x => x.location))

  /* follow the incoming edges of the given type as long as possible */
  protected def walkIn(edgeType: String): GremlinScala[Vertex] =
    raw
      .repeat(_.in(edgeType))
      .until(_.in(edgeType).count.is(P.eq(0)))

  def toMaps(): Steps[Map[String, Any]] =
    new Steps[Map[String, Any]](raw.map(_.toMap))

}
