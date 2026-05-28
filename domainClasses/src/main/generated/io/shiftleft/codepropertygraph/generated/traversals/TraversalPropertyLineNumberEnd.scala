package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyLineNumberEnd[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasLineNumberEndEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to lineNumberEnd property */
  def lineNumberEnd: Iterator[Int] =
    traversal.flatMap(_.lineNumberEnd)

  /** Traverse to nodes where the lineNumberEnd equals the given `value`
    */
  def lineNumberEnd(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get == value
    }

  /** Traverse to nodes where the lineNumberEnd equals at least one of the given `values`
    */
  def lineNumberEnd(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.lineNumberEnd; tmp.isDefined && vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the lineNumberEnd is not equal to the given `value`
    */
  def lineNumberEndNot(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumberEnd; tmp.isEmpty || tmp.get != value
    }

  /** Traverse to nodes where the lineNumberEnd does not equal any one of the given `values`
    */
  def lineNumberEndNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.lineNumberEnd; tmp.isEmpty || !vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the lineNumberEnd is greater than the given `value`
    */
  def lineNumberEndGt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get > value
    }

  /** Traverse to nodes where the lineNumberEnd is greater than or equal the given `value`
    */
  def lineNumberEndGte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get >= value
    }

  /** Traverse to nodes where the lineNumberEnd is less than the given `value`
    */
  def lineNumberEndLt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get < value
    }

  /** Traverse to nodes where the lineNumberEnd is less than or equal the given `value`
    */
  def lineNumberEndLte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get <= value
    }

}
