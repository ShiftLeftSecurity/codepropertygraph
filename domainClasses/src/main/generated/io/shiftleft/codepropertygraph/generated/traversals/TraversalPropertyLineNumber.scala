package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyLineNumber[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasLineNumberEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to lineNumber property */
  def lineNumber: Iterator[Int] =
    traversal.flatMap(_.lineNumber)

  /** Traverse to nodes where the lineNumber equals the given `value`
    */
  def lineNumber(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get == value
    }

  /** Traverse to nodes where the lineNumber equals at least one of the given `values`
    */
  def lineNumber(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the lineNumber is not equal to the given `value`
    */
  def lineNumberNot(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isEmpty || tmp.get != value
    }

  /** Traverse to nodes where the lineNumber does not equal any one of the given `values`
    */
  def lineNumberNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isEmpty || !vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the lineNumber is greater than the given `value`
    */
  def lineNumberGt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get > value
    }

  /** Traverse to nodes where the lineNumber is greater than or equal the given `value`
    */
  def lineNumberGte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get >= value
    }

  /** Traverse to nodes where the lineNumber is less than the given `value`
    */
  def lineNumberLt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get < value
    }

  /** Traverse to nodes where the lineNumber is less than or equal the given `value`
    */
  def lineNumberLte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get <= value
    }

}
