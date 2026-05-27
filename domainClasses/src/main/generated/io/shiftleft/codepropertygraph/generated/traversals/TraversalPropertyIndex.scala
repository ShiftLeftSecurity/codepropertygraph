package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyIndex[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIndexEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to index property */
  def index: Iterator[Int] =
    traversal.flatMap(_.index)

  /** Traverse to nodes where the index equals the given `value`
    */
  def index(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.index; tmp.isDefined && tmp.get == value
    }

  /** Traverse to nodes where the index equals the given `value`. If `value` is None, only nodes where index is not set
    * are included.
    */
  def index(value: Option[Int]): Iterator[NodeType] =
    value match { case Some(_val) => index(_val); case None => traversal.filter { node => node.index.isEmpty } }

  /** Traverse to nodes where the index equals the given `value`, or no results if `value` is None.
    */
  def indexIfPresent(value: Option[Int]): Iterator[NodeType] =
    value match { case Some(_val) => index(_val); case None => Iterator.empty }

  /** Traverse to nodes where the index equals at least one of the given `values`
    */
  def index(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.index; tmp.isDefined && vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the index is not equal to the given `value`
    */
  def indexNot(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.index; tmp.isEmpty || tmp.get != value
    }

  /** Traverse to nodes where the index does not equal any one of the given `values`
    */
  def indexNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.index; tmp.isEmpty || !vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the index is greater than the given `value`
    */
  def indexGt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.index; tmp.isDefined && tmp.get > value
    }

  /** Traverse to nodes where the index is greater than or equal the given `value`
    */
  def indexGte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.index; tmp.isDefined && tmp.get >= value
    }

  /** Traverse to nodes where the index is less than the given `value`
    */
  def indexLt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.index; tmp.isDefined && tmp.get < value
    }

  /** Traverse to nodes where the index is less than or equal the given `value`
    */
  def indexLte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.index; tmp.isDefined && tmp.get <= value
    }

}
