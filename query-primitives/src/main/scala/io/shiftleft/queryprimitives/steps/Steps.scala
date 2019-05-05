package io.shiftleft.queryprimitives.steps

import gremlin.scala._
import gremlin.scala.StepLabel.{GetLabelName, combineLabelWithValue}
import io.shiftleft.codepropertygraph.generated.nodes._
import java.util.{List => JList, Map => JMap}

import org.apache.tinkerpop.gremlin.process.traversal.Scope
import org.apache.tinkerpop.gremlin.structure.Vertex

import scala.collection.JavaConverters._
import scala.collection.mutable
import shapeless.{::, HList, HNil}
import shapeless.ops.hlist.{IsHCons, Mapper, Prepend, RightFolder, ToTraversable, Tupler}
import shapeless.ops.product.ToHList

// TODO: make Labels a type member rather than a type parameter to avoid all these casts

/**
  Base class for our DSL
  These are the base steps available in all steps of the query language.
  */
class Steps[NodeType, Labels <: HList](val raw: GremlinScala.Aux[NodeType, Labels])
    extends ext.Enrichable
    with ext.securityprofile.Enrichable
    with ext.semanticcpg.Enrichable
    with ext.dataflowengine.Enrichable {
  implicit lazy val graph: Graph = raw.traversal.asAdmin.getGraph.get

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

  /**
     Extend the traversal with a side-effect step, where `fun` is a
     function that performs a side effect. The function `fun` can
     access the current traversal element via the variable `_`.
    */
  def sideEffect(fun: NodeType => Any): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.sideEffect(fun))

  /** Aggregate all objects at this point into the given collection,
    * e.g. `mutable.ArrayBuffer.empty[NodeType]`
    */
  def aggregate(into: mutable.Buffer[NodeType]): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.sideEffect { into += _ })

  /**
    Create a deep copy of the traversal.
    */
  override def clone: Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.clone)

  /**
    Extend the traversal with a deduplication step. This step ensures
    that duplicate elements are removed.
    */
  def dedup: Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.dedup())

  /**
    Step that selects only the node with the given id.
    */
  def id(key: AnyRef)(implicit isElement: NodeType <:< Element): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.hasId(key))

  /**
    Step that selects only nodes in the given id set `keys`.
    */
  def id(keys: Set[AnyRef])(implicit isElement: NodeType <:< Element): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.hasId(P.within(keys)))

  /**
    Repeat the given traversal. This step can be combined with the until and emit steps to
    provide a termination and emit criteria.
    */
  def repeat[NewNodeType >: NodeType](
      repeatTraversal: Steps[NodeType, HNil] => Steps[NewNodeType, _]): Steps[NewNodeType, Labels] =
    new Steps[NewNodeType, Labels](
      raw.repeat { rawTraversal =>
        repeatTraversal(
          new Steps[NodeType, HNil](rawTraversal)
        ).raw
      }
    )

  /**
    Termination criteria for a repeat step.
    If used before the repeat step it as "while" characteristics.
    If used after the repeat step it as "do-while" characteristics
    */
  def until(untilTraversal: Steps[NodeType, HNil] => Steps[_, _]): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](
      raw.until { rawTraversal =>
        untilTraversal(
          new Steps[NodeType, HNil](rawTraversal)
        ).raw
      }
    )

  /**
    * Modifier for repeat steps. Configure the amount of times the repeat traversal is
    * executed.
    */
  def times(maxLoops: Int): Steps[NodeType, Labels] = {
    new Steps[NodeType, Labels](raw.times(maxLoops))
  }

  /**
    Emit is used with the repeat step to emit the elements of the repeatTraversal after each
    iteration of the repeat loop.
    */
  def emit(): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.emit())

  /**
    Emit is used with the repeat step to emit the elements of the repeatTraversal after each
    iteration of the repeat loop.
    The emitTraversal defines under which condition the elements are emitted.
    */
  def emit(emitTraversal: Steps[NodeType, HNil] => Steps[_, _]): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](
      raw.emit { rawTraversal =>
        emitTraversal(
          new Steps[NodeType, HNil](rawTraversal)
        ).raw
      }
    )

  /**
    * Keep elements if the provided `predicate` traversal returns something
    */
  def filter(predicate: Steps[NodeType, Labels] => Steps[_, _]): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](
      raw.filter { gs =>
        predicate(
          new Steps[NodeType, Labels](gs.asInstanceOf[GremlinScala.Aux[NodeType, Labels]])
        ).raw
      }
    )

  /**
    * Keep elements if they do not match the predicate `predicate`
    * */
  def filterNot(predicate: Steps[NodeType, Labels] => Steps[_, _]): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](
      raw.filterNot { gs =>
        predicate(
          new Steps[NodeType, Labels](gs.asInstanceOf[GremlinScala.Aux[NodeType, Labels]])
        ).raw
      }
    )

  /**
    Same as filter, but operates with a lambda (will only work with local databases)
    */
  def filterOnEnd(predicate: NodeType => Boolean): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](
      raw.filterOnEnd(predicate)
    )

  /**
    * The or step is a filter with multiple or related filter traversals.
    */
  def or(orTraversals: (Steps[NodeType, HNil] => Steps[_, _])*): Steps[NodeType, Labels] = {
    val rawOrTraversals = rawTraversals(orTraversals: _*)
    new Steps[NodeType, Labels](raw.or(rawOrTraversals: _*))
  }

  /**
    * The and step is a filter with multiple and related filter traversals.
    * */
  def and(andTraversals: (Steps[NodeType, HNil] => Steps[_, _])*): Steps[NodeType, Labels] = {
    val rawAndTraversals = rawTraversals(andTraversals: _*)
    new Steps[NodeType, Labels](
      raw.and(rawAndTraversals: _*)
    )
  }

  /**
    * And step that receives another complete traversal as an argument
    * */
  def and[X <: Node, L <: HNil](step: Steps[X, L]): Steps[X, Labels] = {
    new Steps[X, Labels](
      raw.flatMap { node =>
        step.raw
      }
    )
  }

  private def rawTraversals(traversals: (Steps[NodeType, HNil] => Steps[_, _])*) =
    traversals.map { traversal => (rawTraversal: GremlinScala[NodeType]) =>
      traversal(
        new Steps[NodeType, HNil](rawTraversal.asInstanceOf[GremlinScala.Aux[NodeType, HNil]])
      ).raw
    }

  def range(low: Long, high: Long): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.range(low, high))

  def range(scope: Scope, low: Long, high: Long): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.range(scope, low, high))

  /**
    * Step that applies the map `fun` to each element.
    */
  def map[NewNodeType](fun: NodeType => NewNodeType): Steps[NewNodeType, Labels] =
    new Steps[NewNodeType, Labels](raw.map(fun))

  /**
    Step that applies the map `fun` to each element and flattens the result.
    */
  def flatMap[NewNodeType](fun: NodeType => Steps[NewNodeType, _]): Steps[NewNodeType, Labels] =
    new Steps[NewNodeType, Labels](
      raw.flatMap { n: NodeType =>
        fun(n).raw
      }
    )

  /**
    * Step that orders nodes according to f.
    * */
  def orderBy[A](fun: NodeType => A): Steps[NodeType, Labels] =
    new Steps[NodeType, Labels](raw.order(By(fun)))

  /** Labels the current step and preserves the type - use together with `select` step
    */
  def as[NewLabels <: HList](stepLabel: String)(
      implicit prependDomain: Prepend.Aux[Labels, NodeType :: HNil, NewLabels]): Steps[NodeType, NewLabels] =
    new Steps[NodeType, NewLabels](raw.as(stepLabel))

  /**
    Labels the current step and preserves the type - use together with `select` step
    */
  def as[NewLabels <: HList](stepLabel: StepLabel[NodeType])(
      implicit prependDomain: Prepend.Aux[Labels, NodeType :: HNil, NewLabels]): Steps[NodeType, NewLabels] =
    new Steps[NodeType, NewLabels](raw.as(stepLabel))

  /**
    Select all labeled nodes
    */
  def select[LabelsTuple]()(implicit tupler: Tupler.Aux[Labels, LabelsTuple]): Steps[LabelsTuple, Labels] =
    new Steps[LabelsTuple, Labels](raw.select())

  /**
    Select node with label `label`
    */
  def select[LabelledType](label: StepLabel[LabelledType]) =
    new Steps[LabelledType, Labels](raw.select(label))

  /**
    Select nodes with labels `labels`
    */
  def select[StepLabelsTuple <: Product,
             StepLabels <: HList,
             H0,
             T0 <: HList,
             SelectedTypes <: HList,
             SelectedTypesTuple <: Product,
             LabelNames <: HList,
             Z](stepLabelsTuple: StepLabelsTuple)(
      implicit toHList: ToHList.Aux[StepLabelsTuple, StepLabels],
      hasOne: IsHCons.Aux[StepLabels, H0, T0],
      hasTwo: IsHCons[T0], // witnesses that labels has > 1 elements
      extractLabelType: StepLabel.ExtractLabelType.Aux[StepLabels, SelectedTypes],
      tupler: Tupler.Aux[SelectedTypes, SelectedTypesTuple],
      stepLabelToString: Mapper.Aux[GetLabelName.type, StepLabels, LabelNames],
      trav: ToTraversable.Aux[LabelNames, List, String],
      folder: RightFolder.Aux[StepLabels, (HNil, JMap[String, Any]), combineLabelWithValue.type, (SelectedTypes, Z)])
    : Steps[SelectedTypesTuple, Labels] =
    new Steps[SelectedTypesTuple, Labels](raw.select(stepLabelsTuple))

}
