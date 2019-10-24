package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes._
import java.util.{List => JList}

import org.apache.tinkerpop.gremlin.process.traversal.Scope
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.json4s.CustomSerializer
import org.json4s.native.Serialization.{write, writePretty}
import org.json4s.Extraction

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  Base class for our DSL
  These are the base steps available in all steps of the query language.
  */
class Steps[NodeType](val raw: GremlinScala[NodeType]) {
  implicit lazy val graph: Graph = raw.traversal.asAdmin.getGraph.get

  def toIterator(): Iterator[NodeType] = {
    val iter: java.util.Iterator[NodeType] = raw.traversal
    iter.asScala
  }

  /**
    Execute the traversal and convert the result to a list
    */
  def toList(): List[NodeType] = raw.toList()

  /**
    Shorthand for `toList`
    */
  def l(): List[NodeType] = toList()

  /**
    Execute the traversal and convert it to a mutable buffer
    */
  def toBuffer(): mutable.Buffer[NodeType] = raw.toBuffer()

  /**
    Shorthand for `toBuffer`
    */
  def b(): mutable.Buffer[NodeType] = toBuffer()

  /**
    Alias for `toList`
    @deprecated
    */
  def exec(): List[NodeType] = toList()

  /**
    Execute the travel and convert it to a Java stream.
    */
  def toStream(): java.util.stream.Stream[NodeType] = raw.toStream()

  /**
    Alias for `toStream`
    */
  def s(): java.util.stream.Stream[NodeType] = toStream()

  /**
    Execute the traversal and convert it into a Java list (as opposed
    to the Scala list obtained via `toList`)
    */
  def jl(): JList[NodeType] = b.asJava

  /**
    Execute the traversal and convert it into a set
    */
  def toSet(): Set[NodeType] = raw.toSet()

  def head(): NodeType = raw.head()

  def headOption(): Option[NodeType] = raw.headOption()

  def isDefined: Boolean = headOption().isDefined

  def isEmpty: Boolean = !isDefined

  /**
    * Pretty print vertices
    * */
  def p(): List[String] = {
    l.map {
      case vertex: Vertex => {
        val label = vertex.label
        val id = vertex.id().toString
        val keyValPairs = vertex.valueMap.toList
          .filter(x => x._2.toString != "")
          .sortBy(_._1)
          .map(x => x._1 + ": " + x._2)
        s"($label,$id): " + keyValPairs.mkString(", ")
      }
      case elem => elem.toString
    }
  }

  /** Execute traversal and convert the result to json. */
  def toJson: String = toJson(pretty = false)

  /** Execute traversal and convert the result to pretty json. */
  def toJsonPretty: String = toJson(pretty = true)

  protected def toJson(pretty: Boolean): String = {
    implicit val formats = org.json4s.DefaultFormats + nodeSerializer

    val results = toList()
    if (pretty) writePretty(results)
    else write(results)
  }

  private lazy val nodeSerializer = new CustomSerializer[nodes.Node](
    implicit format =>
      (
        { case _                => ??? }, {
          case node: StoredNode => Extraction.decompose(node.toMap)
          case node: NewNode    => Extraction.decompose(node.properties)
        }
    ))

  /**
     Extend the traversal with a side-effect step, where `fun` is a
     function that performs a side effect. The function `fun` can
     access the current traversal element via the variable `_`.
    */
  def sideEffect(fun: NodeType => Any): Steps[NodeType] =
    new Steps[NodeType](raw.sideEffect(fun))

  /** Aggregate all objects at this point into the given collection,
    * e.g. `mutable.ArrayBuffer.empty[NodeType]`
    */
  def aggregate(into: mutable.Buffer[NodeType]): Steps[NodeType] =
    new Steps[NodeType](raw.sideEffect { into += _ })

  /**
    Create a deep copy of the traversal.
    */
  override def clone: Steps[NodeType] =
    new Steps[NodeType](raw.clone)

  /**
    Extend the traversal with a deduplication step. This step ensures
    that duplicate elements are removed.
    */
  def dedup: Steps[NodeType] =
    new Steps[NodeType](raw.dedup())

  /**
    Step that selects only the node with the given id.
    */
  def id(key: AnyRef)(implicit isElement: NodeType <:< Element): Steps[NodeType] =
    new Steps[NodeType](raw.hasId(key))

  /**
    Step that selects only nodes in the given id set `keys`.
    */
  def id(keys: Set[AnyRef])(implicit isElement: NodeType <:< Element): Steps[NodeType] =
    new Steps[NodeType](raw.hasId(P.within(keys)))

  /**
    Repeat the given traversal. This step can be combined with the until and emit steps to
    provide a termination and emit criteria.
    */
  def repeat[NewNodeType >: NodeType](repeatTraversal: Steps[NodeType] => Steps[NewNodeType]): Steps[NewNodeType] =
    new Steps[NewNodeType](
      raw.repeat { rawTraversal =>
        repeatTraversal(
          new Steps[NodeType](rawTraversal)
        ).raw
      }
    )

  /**
    Termination criteria for a repeat step.
    If used before the repeat step it as "while" characteristics.
    If used after the repeat step it as "do-while" characteristics
    */
  def until(untilTraversal: Steps[NodeType] => Steps[_]): Steps[NodeType] =
    new Steps[NodeType](
      raw.until { rawTraversal =>
        untilTraversal(
          new Steps[NodeType](rawTraversal)
        ).raw
      }
    )

  /**
    * Modifier for repeat steps. Configure the amount of times the repeat traversal is
    * executed.
    */
  def times(maxLoops: Int): Steps[NodeType] = {
    new Steps[NodeType](raw.times(maxLoops))
  }

  /**
    Emit is used with the repeat step to emit the elements of the repeatTraversal after each
    iteration of the repeat loop.
    */
  def emit(): Steps[NodeType] =
    new Steps[NodeType](raw.emit())

  /**
    Emit is used with the repeat step to emit the elements of the repeatTraversal after each
    iteration of the repeat loop.
    The emitTraversal defines under which condition the elements are emitted.
    */
  def emit(emitTraversal: Steps[NodeType] => Steps[_]): Steps[NodeType] =
    new Steps[NodeType](
      raw.emit { rawTraversal =>
        emitTraversal(
          new Steps[NodeType](rawTraversal)
        ).raw
      }
    )

  /**
    * Keep elements if the provided `predicate` traversal returns something
    */
  def filter(predicate: Steps[NodeType] => Steps[_]): Steps[NodeType] =
    new Steps[NodeType](
      raw.filter { gs =>
        predicate(
          new Steps[NodeType](gs.asInstanceOf[GremlinScala[NodeType]])
        ).raw
      }
    )

  /**
    * Keep elements if they do not match the predicate `predicate`
    * */
  def filterNot(predicate: Steps[NodeType] => Steps[_]): Steps[NodeType] =
    new Steps[NodeType](
      raw.filterNot { gs =>
        predicate(
          new Steps[NodeType](gs.asInstanceOf[GremlinScala[NodeType]])
        ).raw
      }
    )

  /**
    Same as filter, but operates with a lambda (will only work with local databases)
    */
  def filterOnEnd(predicate: NodeType => Boolean): Steps[NodeType] =
    new Steps[NodeType](
      raw.filterOnEnd(predicate)
    )

  /**
    * The or step is a filter with multiple or related filter traversals.
    */
  def or(orTraversals: (Steps[NodeType] => Steps[_])*): Steps[NodeType] = {
    val rawOrTraversals = rawTraversals(orTraversals: _*)
    new Steps[NodeType](raw.or(rawOrTraversals: _*))
  }

  /**
    * The and step is a filter with multiple and related filter traversals.
    * */
  def and(andTraversals: (Steps[NodeType] => Steps[_])*): Steps[NodeType] = {
    val rawAndTraversals = rawTraversals(andTraversals: _*)
    new Steps[NodeType](
      raw.and(rawAndTraversals: _*)
    )
  }

  /**
    * And step that receives another complete traversal as an argument
    * */
  def and[OtherNodeType <: Node](step: Steps[OtherNodeType]): Steps[OtherNodeType] =
    new Steps[OtherNodeType](
      raw.flatMap { node =>
        step.raw
      }
    )

  private def rawTraversals(traversals: (Steps[NodeType] => Steps[_])*) =
    traversals.map { traversal => (rawTraversal: GremlinScala[NodeType]) =>
      traversal(
        new Steps[NodeType](rawTraversal.asInstanceOf[GremlinScala[NodeType]])
      ).raw
    }

  def range(low: Long, high: Long): Steps[NodeType] =
    new Steps[NodeType](raw.range(low, high))

  def range(scope: Scope, low: Long, high: Long): Steps[NodeType] =
    new Steps[NodeType](raw.range(scope, low, high))

  /**
    * Step that applies the map `fun` to each element.
    */
  def map[NewNodeType](fun: NodeType => NewNodeType): Steps[NewNodeType] =
    new Steps[NewNodeType](raw.map(fun))

  /**
    Step that applies the map `fun` to each element and flattens the result.
    */
  def flatMap[NewNodeType](fun: NodeType => Steps[NewNodeType]): Steps[NewNodeType] =
    new Steps[NewNodeType](
      raw.flatMap { n: NodeType =>
        fun(n).raw
      }
    )

  /**
    * Step that orders nodes according to f.
    * */
  def orderBy[A](fun: NodeType => A): Steps[NodeType] =
    new Steps[NodeType](raw.order(By(fun)))

}
