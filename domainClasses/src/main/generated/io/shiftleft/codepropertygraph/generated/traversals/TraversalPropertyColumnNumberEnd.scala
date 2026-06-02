package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyColumnNumberEnd[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasColumnNumberEndEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to columnNumberEnd property */
  def columnNumberEnd: Iterator[Int] =
    traversal.flatMap(_.columnNumberEnd)

  /** Traverse to nodes where the columnNumberEnd equals the given `value`
    */
  def columnNumberEnd(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get == value
    }

  /** Traverse to nodes where the columnNumberEnd equals the given `value`. If `value` is None, only nodes where
    * columnNumberEnd is not set are included.
    */
  def columnNumberEnd(value: Option[Int]): Iterator[NodeType] =
    value match {
      case Some(_val) => columnNumberEnd(_val); case None => traversal.filter { node => node.columnNumberEnd.isEmpty }
    }

  /** Traverse to nodes where the columnNumberEnd equals the given `value`, or no results if `value` is None.
    */
  def columnNumberEndIfPresent(value: Option[Int]): Iterator[NodeType] =
    value match { case Some(_val) => columnNumberEnd(_val); case None => Iterator.empty }

  /** Traverse to nodes where the columnNumberEnd equals at least one of the given `values`
    */
  def columnNumberEnd(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.columnNumberEnd; tmp.isDefined && vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the columnNumberEnd is not equal to the given `value`
    */
  def columnNumberEndNot(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumberEnd; tmp.isEmpty || tmp.get != value
    }

  /** Traverse to nodes where the columnNumberEnd does not equal any one of the given `values`
    */
  def columnNumberEndNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.columnNumberEnd; tmp.isEmpty || !vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the columnNumberEnd is greater than the given `value`
    */
  def columnNumberEndGt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get > value
    }

  /** Traverse to nodes where the columnNumberEnd is greater than or equal the given `value`
    */
  def columnNumberEndGte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get >= value
    }

  /** Traverse to nodes where the columnNumberEnd is less than the given `value`
    */
  def columnNumberEndLt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get < value
    }

  /** Traverse to nodes where the columnNumberEnd is less than or equal the given `value`
    */
  def columnNumberEndLte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get <= value
    }

}
