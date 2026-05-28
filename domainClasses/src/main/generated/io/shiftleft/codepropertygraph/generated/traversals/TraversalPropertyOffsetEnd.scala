package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyOffsetEnd[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasOffsetEndEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to offsetEnd property */
  def offsetEnd: Iterator[Int] =
    traversal.flatMap(_.offsetEnd)

  /** Traverse to nodes where the offsetEnd equals the given `value`
    */
  def offsetEnd(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offsetEnd; tmp.isDefined && tmp.get == value
    }

  /** Traverse to nodes where the offsetEnd equals at least one of the given `values`
    */
  def offsetEnd(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.offsetEnd; tmp.isDefined && vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the offsetEnd is not equal to the given `value`
    */
  def offsetEndNot(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offsetEnd; tmp.isEmpty || tmp.get != value
    }

  /** Traverse to nodes where the offsetEnd does not equal any one of the given `values`
    */
  def offsetEndNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.offsetEnd; tmp.isEmpty || !vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the offsetEnd is greater than the given `value`
    */
  def offsetEndGt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offsetEnd; tmp.isDefined && tmp.get > value
    }

  /** Traverse to nodes where the offsetEnd is greater than or equal the given `value`
    */
  def offsetEndGte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offsetEnd; tmp.isDefined && tmp.get >= value
    }

  /** Traverse to nodes where the offsetEnd is less than the given `value`
    */
  def offsetEndLt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offsetEnd; tmp.isDefined && tmp.get < value
    }

  /** Traverse to nodes where the offsetEnd is less than or equal the given `value`
    */
  def offsetEndLte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offsetEnd; tmp.isDefined && tmp.get <= value
    }

}
