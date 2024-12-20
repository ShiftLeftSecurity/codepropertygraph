package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyOffset[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasOffsetEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to offset property */
  def offset: Iterator[Int] =
    traversal.flatMap(_.offset)

  /** Traverse to nodes where the offset equals the given `value`
    */
  def offset(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offset; tmp.isDefined && tmp.get == value
    }

  /** Traverse to nodes where the offset equals at least one of the given `values`
    */
  def offset(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.offset; tmp.isDefined && vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the offset is not equal to the given `value`
    */
  def offsetNot(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offset; tmp.isEmpty || tmp.get != value
    }

  /** Traverse to nodes where the offset does not equal any one of the given `values`
    */
  def offsetNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.offset; tmp.isEmpty || !vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the offset is greater than the given `value`
    */
  def offsetGt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offset; tmp.isDefined && tmp.get > value
    }

  /** Traverse to nodes where the offset is greater than or equal the given `value`
    */
  def offsetGte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offset; tmp.isDefined && tmp.get >= value
    }

  /** Traverse to nodes where the offset is less than the given `value`
    */
  def offsetLt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offset; tmp.isDefined && tmp.get < value
    }

  /** Traverse to nodes where the offset is less than or equal the given `value`
    */
  def offsetLte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.offset; tmp.isDefined && tmp.get <= value
    }

}
