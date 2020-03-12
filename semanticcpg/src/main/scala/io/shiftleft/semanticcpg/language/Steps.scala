package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes._
import java.util.{List => JList}

import org.apache.tinkerpop.gremlin.process.traversal.Scope
import org.json4s.CustomSerializer
import org.json4s.native.Serialization.{write, writePretty}
import org.json4s.Extraction

import scala.jdk.CollectionConverters._
import scala.collection.mutable

/** Base class for our DSL
  * These are the base steps available in all steps of the query language.
  * There are no constraints on the element types, unlike e.g. [[NodeSteps]]
  */
class Steps[A](val raw: GremlinScala[A]) {
  implicit lazy val graph: Graph = raw.traversal.asAdmin.getGraph.get

  def toIterator(): Iterator[A] = {
    val iter: java.util.Iterator[A] = raw.traversal
    iter.asScala
  }

  /**
    * Execute the traversal and convert the result to a list
    * `toList` (inspection) evaluates and returns raw case classes.
    * We use `toList` to allow the developer to view the raw CPG nodes
    * returned by a query - including containedNodes - in a format that
    * allows for easy inspection, but nonetheless, shows the data as-is.
    */
  def toList(): List[A] = raw.toList()

  /**
    Shorthand for `toList`
    */
  def l(): List[A] = toList()

  /**
    Execute the traversal and convert it to a mutable buffer
    */
  def toBuffer(): mutable.Buffer[A] = raw.toBuffer()

  /**
    Shorthand for `toBuffer`
    */
  def b(): mutable.Buffer[A] = toBuffer()

  /**
    Alias for `toList`
    @deprecated
    */
  def exec(): List[A] = toList()

  def iterate(): Unit = raw.iterate()

  /**
    Execute the travel and convert it to a Java stream.
    */
  def toStream(): java.util.stream.Stream[A] = raw.toStream()

  /**
    Alias for `toStream`
    */
  def s(): java.util.stream.Stream[A] = toStream()

  /**
    Execute the traversal and convert it into a Java list (as opposed
    to the Scala list obtained via `toList`)
    */
  def jl(): JList[A] = b.asJava

  /**
    Execute the traversal and convert it into a set
    */
  def toSet(): Set[A] = raw.toSet()

  def head(): A = raw.head()

  def headOption(): Option[A] = raw.headOption()

  def isDefined: Boolean = headOption().isDefined

  def isEmpty: Boolean = !isDefined

  /**
    * Print help/documentation about the current specific step - useful for REPL users
    * */
  def help(implicit helpProvider: Steps.Help[A] = Steps.defaultHelpInstance): String =
    helpProvider()

  /**
    * Print help/documentation about generic steps - useful for REPL users
    * */
  def helpGeneric: String = Steps.genericHelpMsg

  /**
    * Pretty print vertices
    * This may mean that not all properties of the node are displayed
    * or that some properties have undergone transformations to improve display.
    * A good example is flow pretty-printing. This is the only three of the
    * methods which we may modify on a per-node-type basis, typically via
    * implicits of type Show[NodeType].
    * */
  def p(implicit show: Show[A] = Show.default): List[String] =
    toList.map(show.apply)

  /** Execute traversal and convert the result to json.
    * `toJson` (export) contains the exact same information as `toList`,
    *  only in json format. Typically, the user will call this method upon
    *  inspection of the results of `toList` in order to export the data
    *  for processing with other tools.
    * */
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
        { case _ => ??? }, {
          case node: Node => {
            val elementMap = (0 until node.productArity).map { i =>
              val label = node.productElementLabel(i)
              val element = node.productElement(i)
              (label -> element)
            }.toMap + ("_label" -> node.label)
            Extraction.decompose(elementMap)
          }
        }
    ))

  /**
     Extend the traversal with a side-effect step, where `fun` is a
     function that performs a side effect. The function `fun` can
     access the current traversal element via the variable `_`.
    */
  def sideEffect(fun: A => Any): Steps[A] =
    new Steps[A](raw.sideEffect(fun))

  /** Aggregate all objects at this point into the given collection,
    * e.g. `mutable.ArrayBuffer.empty[A]`
    */
  def aggregate(into: mutable.Buffer[A]): Steps[A] =
    new Steps[A](raw.sideEffect { into += _ })

  /**
    Create a deep copy of the traversal.
    */
  override def clone: Steps[A] =
    new Steps[A](raw.clone)

  /**
    Extend the traversal with a deduplication step. This step ensures
    that duplicate elements are removed.
    */
  def dedup: Steps[A] =
    new Steps[A](raw.dedup())

  /**
    * Traverse to ids of underlying objects
    * */
  def id: Steps[AnyRef] = new Steps(raw.id)

  /**
    Step that selects only the node with the given id.
    */
  def id(key: AnyRef)(implicit isElement: A <:< Element): Steps[A] =
    new Steps[A](raw.hasId(key))

  /**
    Step that selects only nodes in the given id set `keys`.
    */
  def id(keys: Set[AnyRef])(implicit isElement: A <:< Element): Steps[A] =
    new Steps[A](raw.hasId(P.within(keys)))

  /**
    Repeat the given traversal. This step can be combined with the until and emit steps to
    provide a termination and emit criteria.
    */
  def repeat[NewNodeType >: A](repeatTraversal: Steps[A] => Steps[NewNodeType]): Steps[NewNodeType] =
    new Steps[NewNodeType](
      raw.repeat { rawTraversal =>
        repeatTraversal(
          new Steps[A](rawTraversal)
        ).raw
      }
    )

  /**
    Termination criteria for a repeat step.
    If used before the repeat step it as "while" characteristics.
    If used after the repeat step it as "do-while" characteristics
    */
  def until(untilTraversal: Steps[A] => Steps[_]): Steps[A] =
    new Steps[A](
      raw.until { rawTraversal =>
        untilTraversal(
          new Steps[A](rawTraversal)
        ).raw
      }
    )

  /**
    * Modifier for repeat steps. Configure the amount of times the repeat traversal is
    * executed.
    */
  def times(maxLoops: Int): Steps[A] = {
    new Steps[A](raw.times(maxLoops))
  }

  /**
    Emit is used with the repeat step to emit the elements of the repeatTraversal after each
    iteration of the repeat loop.
    */
  def emit(): Steps[A] =
    new Steps[A](raw.emit())

  /**
    Emit is used with the repeat step to emit the elements of the repeatTraversal after each
    iteration of the repeat loop.
    The emitTraversal defines under which condition the elements are emitted.
    */
  def emit(emitTraversal: Steps[A] => Steps[_]): Steps[A] =
    new Steps[A](
      raw.emit { rawTraversal =>
        emitTraversal(
          new Steps[A](rawTraversal)
        ).raw
      }
    )

  /**
    * Keep elements if the provided `predicate` traversal returns something
    */
  def filter(predicate: Steps[A] => Steps[_]): Steps[A] =
    new Steps[A](raw.filter(gs => predicate(new Steps[A](gs)).raw))

  /**
    * Keep elements if they do not match the predicate `predicate`
    * */
  def filterNot(predicate: Steps[A] => Steps[_]): Steps[A] =
    new Steps[A](raw.filterNot(gs => predicate(new Steps[A](gs)).raw))

  /**
    Same as filter, but operates with a lambda (will only work with local databases)
    */
  def where(predicate: A => Boolean): Steps[A] =
    new Steps[A](raw.filterOnEnd(predicate))

  def whereNonEmpty(predicate: A => Steps[_]): Steps[A] =
    new Steps[A](raw.filterOnEnd(predicate(_).size > 0))

  @deprecated("", "Nov. 2019")
  def filterOnEnd(predicate: A => Boolean): Steps[A] =
    where(predicate)

  /**
    * The or step is a filter with multiple or related filter traversals.
    */
  def or(orTraversals: (Steps[A] => Steps[_])*): Steps[A] = {
    val rawOrTraversals = rawTraversals(orTraversals: _*)
    new Steps[A](raw.or(rawOrTraversals: _*))
  }

  /**
    * The and step is a filter with multiple and related filter traversals.
    * */
  def and(andTraversals: (Steps[A] => Steps[_])*): Steps[A] = {
    val rawAndTraversals = rawTraversals(andTraversals: _*)
    new Steps[A](
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

  private def rawTraversals(traversals: (Steps[A] => Steps[_])*) =
    traversals.map { traversal => (rawTraversal: GremlinScala[A]) =>
      traversal(
        new Steps[A](rawTraversal)
      ).raw
    }

  def range(low: Long, high: Long): Steps[A] =
    new Steps[A](raw.range(low, high))

  def range(scope: Scope, low: Long, high: Long): Steps[A] =
    new Steps[A](raw.range(scope, low, high))

  /**
    * Step that applies the map `fun` to each element.
    */
  def map[B](fun: A => B): Steps[B] =
    new Steps[B](raw.map(fun))

  /**
    Step that applies the map `fun` to each element and flattens the result.
    */
  def flatMap[B](fun: A => Steps[B]): Steps[B] =
    new Steps[B](raw.flatMap { a: A =>
      fun(a).raw
    })

  /**
    * Step that orders nodes according to f.
    * */
  def orderBy[B](fun: A => B): Steps[A] =
    new Steps[A](raw.order(By(fun)))

  def size: Int = l.size

}

object Steps {

  /**
    * Typeclass for getting help/documentation about a type - useful for REPL users
    */
  trait Help[A] {
    def apply(): String
  }

  val genericHelpMsg =
    """.l : execute this traversal and return a List
      |.p : pretty print the results
      |.toJson[Pretty]
      |.map : transform the traversal
      |""".stripMargin

  def defaultHelpInstance[A]: Help[A] = () =>
    s"""no help text for this specific type available. generic help:
       |$genericHelpMsg""".stripMargin
}
