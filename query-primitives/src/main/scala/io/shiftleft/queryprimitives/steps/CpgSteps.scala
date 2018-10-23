package io.shiftleft.queryprimitives.steps

import gremlin.scala._
import gremlin.scala.dsl.{Converter, NodeSteps, Steps}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.types.structure.File
import java.util.{List => JList, Map => JMap}

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import org.apache.tinkerpop.gremlin.structure.T

import scala.collection.JavaConverters._
import scala.collection.mutable
import org.json4s.native.Serialization.{write, writePretty}
import shapeless.{HList, HNil}

/**
  * Steps for all node types
  *
  * This is the base class for all steps defined on nodes.
  * In `raw` we don't take the extra effort of carrying the graph labels, which will lead to some (safe) casting, but simplify our codebase.
  * */
class CpgSteps[NodeType <: nodes.StoredNode: Marshallable, Labels <: HList](override val raw: GremlinScala[Vertex])
    extends NodeSteps[NodeType, Labels](raw) {
  implicit val graph: Graph = raw.traversal.asAdmin.getGraph.get

  /**
    * shortcut for `toList`
    */
  def l: List[NodeType] = toList

  /**
    * shortcut for `iterate`
    */
  def exec: Unit = iterate

  /**
    * Traverse to source file
    * */
  def file: File[Labels] =
    new File[Labels](
      raw
        .until(_.hasLabel(NodeTypes.FILE))
        .repeat(_.in(EdgeTypes.AST)))

  def toMaps(): Steps[JMap[String, AnyRef], JMap[String, AnyRef], Labels] = {
    implicit val c = Converter.identityConverter[JMap[String, AnyRef]]
    new Steps[JMap[String, AnyRef], JMap[String, AnyRef], Labels](raw.valueMap())
  }

  /**
    Execute traversal and convert the result to json.
    */
  def toJson: String = _toJson(pretty = false)

  def toJsonPretty: String = _toJson(pretty = true)

  def toMaps(): Steps[Map[String, AnyRef], Map[String, AnyRef], Labels] = {
    implicit val c = Converter.identityConverter[Map[String, AnyRef]]
    new Steps[Map[String, AnyRef], Map[String, AnyRef], Labels](
      raw.map { vertex: Vertex =>
        val propertyEntries: List[(String, AnyRef)] =
          vertex.properties[AnyRef]().asScala.map(p => (p.key, p.value)).toList
        (propertyEntries :+ ("_id" -> vertex.id) :+ ("_label" -> vertex.label)).toMap
      }
    )
  }

  protected def _toJson(pretty: Boolean): String = {
    implicit val formats = org.json4s.DefaultFormats
    val maps: List[Map[String, AnyRef]] = toMaps.toList
    if (pretty) writePretty(maps)
    else write(maps)
  }

}
