package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyArgumentIndex[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasArgumentIndexEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to argumentIndex property */
  def argumentIndex: Iterator[Int] =
    traversal.map(_.argumentIndex)

  /** Traverse to nodes where the argumentIndex equals the given `value`
    */
  def argumentIndex(value: Int): Iterator[NodeType] =
    traversal.filter { _.argumentIndex == value }

  /** Traverse to nodes where the argumentIndex equals at least one of the given `values`
    */
  def argumentIndex(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => vset.contains(node.argumentIndex) }
  }

  /** Traverse to nodes where the argumentIndex is not equal to the given `value`
    */
  def argumentIndexNot(value: Int): Iterator[NodeType] =
    traversal.filter { _.argumentIndex != value }

  /** Traverse to nodes where the argumentIndex is not equal to any of the given `values`
    */
  def argumentIndexNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => !vset.contains(node.argumentIndex) }
  }

  /** Traverse to nodes where the argumentIndex is greater than the given `value`
    */
  def argumentIndexGt(value: Int): Iterator[NodeType] =
    traversal.filter { _.argumentIndex > value }

  /** Traverse to nodes where the argumentIndex is greater than or equal the given `value`
    */
  def argumentIndexGte(value: Int): Iterator[NodeType] =
    traversal.filter { _.argumentIndex >= value }

  /** Traverse to nodes where the argumentIndex is less than the given `value`
    */
  def argumentIndexLt(value: Int): Iterator[NodeType] =
    traversal.filter { _.argumentIndex < value }

  /** Traverse to nodes where the argumentIndex is less than or equal the given `value`
    */
  def argumentIndexLte(value: Int): Iterator[NodeType] =
    traversal.filter { _.argumentIndex <= value }

}
