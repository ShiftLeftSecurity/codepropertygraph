package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyIndex[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIndexEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to index property */
  def index: Iterator[Int] =
    traversal.map(_.index)

  /** Traverse to nodes where the index equals the given `value`
    */
  def index(value: Int): Iterator[NodeType] =
    traversal.filter { _.index == value }

  /** Traverse to nodes where the index equals at least one of the given `values`
    */
  def index(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => vset.contains(node.index) }
  }

  /** Traverse to nodes where the index is not equal to the given `value`
    */
  def indexNot(value: Int): Iterator[NodeType] =
    traversal.filter { _.index != value }

  /** Traverse to nodes where the index is not equal to any of the given `values`
    */
  def indexNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => !vset.contains(node.index) }
  }

  /** Traverse to nodes where the index is greater than the given `value`
    */
  def indexGt(value: Int): Iterator[NodeType] =
    traversal.filter { _.index > value }

  /** Traverse to nodes where the index is greater than or equal the given `value`
    */
  def indexGte(value: Int): Iterator[NodeType] =
    traversal.filter { _.index >= value }

  /** Traverse to nodes where the index is less than the given `value`
    */
  def indexLt(value: Int): Iterator[NodeType] =
    traversal.filter { _.index < value }

  /** Traverse to nodes where the index is less than or equal the given `value`
    */
  def indexLte(value: Int): Iterator[NodeType] =
    traversal.filter { _.index <= value }

}
