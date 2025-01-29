package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalAstnodeBase[NodeType <: nodes.AstNodeBase](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to code property */
  def code: Iterator[String] =
    traversal.map(_.code)

  /** Traverse to nodes where the code matches the regular expression `value`
    */
  def code(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      codeExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.code).matches }
    }

  /** Traverse to nodes where the code matches at least one of the regular expressions in `values`
    */
  def code(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.code).matches } }
  }

  /** Traverse to nodes where code matches `value` exactly.
    */
  def codeExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 10, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.code == value }
  }

  /** Traverse to nodes where code matches one of the elements in `values` exactly.
    */
  def codeExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return codeExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 10, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.code) }
    }
  }

  /** Traverse to nodes where code does not match the regular expression `value`.
    */
  def codeNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.code != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.code).matches }
    }
  }

  /** Traverse to nodes where code does not match any of the regular expressions in `values`.
    */
  def codeNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.code).matches }.isEmpty }
  }

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

  /** Traverse to order property */
  def order: Iterator[Int] =
    traversal.map(_.order)

  /** Traverse to nodes where the order equals the given `value`
    */
  def order(value: Int): Iterator[NodeType] =
    traversal.filter { _.order == value }

  /** Traverse to nodes where the order equals at least one of the given `values`
    */
  def order(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => vset.contains(node.order) }
  }

  /** Traverse to nodes where the order is not equal to the given `value`
    */
  def orderNot(value: Int): Iterator[NodeType] =
    traversal.filter { _.order != value }

  /** Traverse to nodes where the order is not equal to any of the given `values`
    */
  def orderNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => !vset.contains(node.order) }
  }

  /** Traverse to nodes where the order is greater than the given `value`
    */
  def orderGt(value: Int): Iterator[NodeType] =
    traversal.filter { _.order > value }

  /** Traverse to nodes where the order is greater than or equal the given `value`
    */
  def orderGte(value: Int): Iterator[NodeType] =
    traversal.filter { _.order >= value }

  /** Traverse to nodes where the order is less than the given `value`
    */
  def orderLt(value: Int): Iterator[NodeType] =
    traversal.filter { _.order < value }

  /** Traverse to nodes where the order is less than or equal the given `value`
    */
  def orderLte(value: Int): Iterator[NodeType] =
    traversal.filter { _.order <= value }

}
