package io.shiftleft.queryprimitives.steps

import gremlin.scala.{GremlinScala, P, Vertex}
import io.shiftleft.codepropertygraph.generated.{nodes, NodeTypes}
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.structure.File
import java.util.{List => JList}

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import org.apache.tinkerpop.gremlin.structure.T

import scala.collection.JavaConverters._
import org.json4s.native.Serialization.{write, writePretty}
import shapeless.{HList, HNil}

/**
  * Steps for all node types
  *
  * This is the base class for all steps defined on nodes.
  * */
class NodeSteps[NodeType <: nodes.StoredNode, Labels <: HList](raw: GremlinScala.Aux[NodeType, Labels])
    extends Steps[NodeType, Labels](raw) {

  /**
    * Traverse to source file
    * */
  def file: File[Labels] =
    new File[Labels](
      raw.choose(
        _.label.is(NodeTypes.NAMESPACE),
        onTrue = _.in(EdgeTypes.REF).in(EdgeTypes.AST),
        onFalse = _.until(_.hasLabel(NodeTypes.FILE)).repeat(_.in(EdgeTypes.AST))
      ).cast[nodes.File]
    )

  /* follow the incoming edges of the given type as long as possible */
  protected def walkIn(edgeType: String): GremlinScala[Vertex] =
    raw
      .repeat(_.in(edgeType))
      .until(_.in(edgeType).count.is(P.eq(0)))

  def toMaps(): Steps[Map[String, Any], Labels] =
    new Steps[Map[String, Any], Labels](raw.map(_.toMap))

  /**
    Execute traversal and convert the result to json.
    */
  def toJson: String = _toJson(pretty = false)

  def toJsonPretty: String = _toJson(pretty = true)

  protected def _toJson(pretty: Boolean): String = {
    implicit val formats = org.json4s.DefaultFormats
    val maps: List[Map[String, Any]] = toList.map(_.toMap)
    if (pretty) writePretty(maps)
    else write(maps)
  }

}
