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

  def toMaps(): Steps[JMap[String, AnyRef], JMap[String, AnyRef], Labels] = {
    implicit val c = Converter.identityConverter[JMap[String, AnyRef]]
    new Steps[JMap[String, AnyRef], JMap[String, AnyRef], Labels](raw.valueMap())
  }

  /**
    * Traverse to source file
    * */
  def file: File[Labels] =
    new File[Labels](
      raw
        .until(_.hasLabel(NodeTypes.FILE))
        .repeat(_.in(EdgeTypes.AST)))

  /* follow the incoming edges of the given type as long as possible */
  protected def walkIn(edgeType: String): GremlinScala[Vertex] =
    raw
      .repeat(_.in(edgeType))
      .until(_.in(edgeType).count.is(P.eq(0)))

  /**
    Execute traversal and convert the result to json.
    */
  def toJson: String = _toJson(pretty = false)

  def toJsonPretty: String = _toJson(pretty = true)

  protected def _toJson(pretty: Boolean): String = {
    implicit val formats = org.json4s.DefaultFormats
    val maps: List[JMap[String, AnyRef]] = toMaps().toList
    if (pretty) writePretty(maps)
    else write(maps)
  }

  // TODO: move to gremlin.scala.dsl.Steps: everything below here

  /**
    Execute the traversal and convert it to a mutable buffer
    */
  def toBuffer(): mutable.Buffer[NodeType] = toList.toBuffer

  /** filter by id */
  def id(id: AnyRef): Steps[NodeType, Vertex, Labels] =
    new Steps[NodeType, Vertex, Labels](raw.hasId(id))

  /**
     Extend the traversal with a side-effect step, where `fun` is a
     function that performs a side effect. The function `fun` can
     access the current traversal element via the variable `_`.
    */
  def sideEffect(fun: NodeType => Any): Steps[NodeType, Vertex, Labels] =
    new Steps[NodeType, Vertex, Labels](raw.sideEffect { v: Vertex =>
      fun(v.toCC[NodeType])
    })

  /**
    Repeat the given traversal. This step can be combined with the until and emit steps to
    provide a termination and emit criteria.
    */
  def repeat[NewNodeType >: NodeType](repeatTraversal: Steps[NodeType, Vertex, HNil] => Steps[NewNodeType, Vertex, _])
    : Steps[NewNodeType, Vertex, Labels] =
    ???
  // new Steps[NewNodeType, Vertex, Labels](
  //   raw.repeat { rawTraversal =>
  //     repeatTraversal(
  //       new Steps[NodeType, HNil](rawTraversal)
  //     ).raw
  //   }
  // )

  /**
    Termination criteria for a repeat step.
    If used before the repeat step it as "while" characteristics.
    If used after the repeat step it as "do-while" characteristics
    */
  def until(untilTraversal: Steps[NodeType, Vertex, HNil] => Steps[_, _, _]): Steps[NodeType, Vertex, Labels] =
    ???
  // new Steps[NodeType, Vertex, Labels](
  //   raw.until { rawTraversal =>
  //     untilTraversal(
  //       new Steps[NodeType, Vertex, HNil](rawTraversal)
  //     ).raw
  //   }
  // )

  /**
    * Modifier for repeat steps. Configure the amount of times the repeat traversal is
    * executed.
    */
  def times(maxLoops: Int): Steps[NodeType, Vertex, Labels] =
    new Steps[NodeType, Vertex, Labels](raw.times(maxLoops))

  /**
    Emit is used with the repeat step to emit the elements of the repeatTraversal after each
    iteration of the repeat loop.
    */
  def emit(): Steps[NodeType, Vertex, Labels] =
    new Steps[NodeType, Vertex, Labels](raw.emit())

  /**
    Emit is used with the repeat step to emit the elements of the repeatTraversal after each
    iteration of the repeat loop.
    The emitTraversal defines under which condition the elements are emitted.
    */
  def emit(emitTraversal: Steps[NodeType, Vertex, HNil] => Steps[_, _, _]): Steps[NodeType, Vertex, Labels] =
    ???
  // new Steps[NodeType, Vertex, Labels](
  //   raw.emit { rawTraversal =>
  //     emitTraversal(
  //       new Steps[NodeType, Vertex, HNil](rawTraversal)
  //     ).raw
  //   }
  // )

}
