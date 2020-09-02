package io.shiftleft.semanticcpg.language

import java.util.{List => JList}

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes._
import org.json4s.native.Serialization.{write, writePretty}
import org.json4s.{CustomSerializer, Extraction}
import overflowdb.traversal.Traversal
import overflowdb.traversal.help.{Doc, TraversalHelp}

import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.reflect.ClassTag

/** Base class for our DSL
  * These are the base steps available in all steps of the query language.
  * There are no constraints on the element types, unlike e.g. [[NodeSteps]]
  */
class Steps[A](val traversal: Traversal[A]) extends AnyVal {

  /**
    Execute the traversal and convert it to a mutable buffer
    */
  def toBuffer(): mutable.Buffer[A] = traversal.to(mutable.Buffer)

  /**
    Shorthand for `toBuffer`
    */
  def b(): mutable.Buffer[A] = toBuffer()

  /**
    Alias for `toList`
    @deprecated
    */
  def exec(): List[A] = traversal.toList

  /**
    Execute the travel and convert it to a Java stream.
    */
  def toStream(): LazyList[A] = traversal.to(LazyList)

  /**
    Alias for `toStream`
    */
  def s(): LazyList[A] = toStream()

  /**
    Execute the traversal and convert it into a Java list (as opposed
    to the Scala list obtained via `toList`)
    */
  def jl(): JList[A] = b.asJava

  /**
    * Print help/documentation based on the current elementType `A`.
    * Relies on all step extensions being annotated with @TraversalExt / @Doc
    * Note that this works independently of tab completion and implicit conversions in scope - it will simply list
    * all documented steps in the classpath
    * */
  def help()(implicit elementType: ClassTag[A]): String =
    Steps.help.forElementSpecificSteps(elementType.runtimeClass, verbose = false)

  def helpVerbose()(implicit elementType: ClassTag[A]): String =
    Steps.help.forElementSpecificSteps(elementType.runtimeClass, verbose = true)

  /**
    * Execute this traversal and pretty print the results.
    * This may mean that not all properties of the node are displayed
    * or that some properties have undergone transformations to improve display.
    * A good example is flow pretty-printing. This is the only three of the
    * methods which we may modify on a per-node-type basis, typically via
    * implicits of type Show[NodeType].
    * */
  @Doc("execute this traversal and pretty print the results")
  def p(implicit show: Show[A] = Show.default): List[String] =
    traversal.toList.map(show.apply)

  /** Execute traversal and convert the result to json.
    * `toJson` (export) contains the exact same information as `toList`,
    *  only in json format. Typically, the user will call this method upon
    *  inspection of the results of `toList` in order to export the data
    *  for processing with other tools.
    * */
  @Doc("execute traversal and convert the result to json")
  def toJson: String = toJson(pretty = false)

  /** Execute traversal and convert the result to pretty json. */
  @Doc("execute traversal and convert the result to pretty json")
  def toJsonPretty: String = toJson(pretty = true)

  protected def toJson(pretty: Boolean): String = {
    implicit val formats = org.json4s.DefaultFormats + Steps.nodeSerializer

    val results = traversal.toList
    if (pretty) writePretty(results)
    else write(results)
  }

  /** Aggregate all objects at this point into the given collection,
    * e.g. `mutable.ArrayBuffer.empty[A]`
    */
  def aggregate(into: mutable.Buffer[A]): Steps[A] =
    new Steps[A](traversal.sideEffect { into += _ })

}

object Steps {
  private lazy val nodeSerializer = new CustomSerializer[nodes.StoredNode](
    implicit format =>
      (
        { case _ => ??? }, {
          case node: StoredNode =>
            val elementMap = (0 until node.productArity).map { i =>
              val label = node.productElementLabel(i)
              val element = node.productElement(i)
              label -> element
            }.toMap + ("_label" -> node.label)
            Extraction.decompose(elementMap)
        }
    ))

  val help = new TraversalHelp("io.shiftleft") {
    // TODO remove once we migrated to overflowdb-traversal
    override lazy val genericStepDocs: Iterable[StepDoc] =
      findStepDocs(classOf[Steps[_]])

    // TODO remove once we migrated to overflowdb-traversal
    override lazy val genericNodeStepDocs: Iterable[StepDoc] =
      findStepDocs(classOf[NodeSteps[_]])

  }
}
