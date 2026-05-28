package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyColumnNumber[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasColumnNumberEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to columnNumber property */
  def columnNumber: Iterator[Int] =
    traversal.flatMap(_.columnNumber)

  /** Traverse to nodes where the columnNumber equals the given `value`
    */
  def columnNumber(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumber; tmp.isDefined && tmp.get == value
    }

  /** Traverse to nodes where the columnNumber equals at least one of the given `values`
    */
  def columnNumber(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.columnNumber; tmp.isDefined && vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the columnNumber is not equal to the given `value`
    */
  def columnNumberNot(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumber; tmp.isEmpty || tmp.get != value
    }

  /** Traverse to nodes where the columnNumber does not equal any one of the given `values`
    */
  def columnNumberNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.columnNumber; tmp.isEmpty || !vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the columnNumber is greater than the given `value`
    */
  def columnNumberGt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumber; tmp.isDefined && tmp.get > value
    }

  /** Traverse to nodes where the columnNumber is greater than or equal the given `value`
    */
  def columnNumberGte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumber; tmp.isDefined && tmp.get >= value
    }

  /** Traverse to nodes where the columnNumber is less than the given `value`
    */
  def columnNumberLt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumber; tmp.isDefined && tmp.get < value
    }

  /** Traverse to nodes where the columnNumber is less than or equal the given `value`
    */
  def columnNumberLte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.columnNumber; tmp.isDefined && tmp.get <= value
    }

}
