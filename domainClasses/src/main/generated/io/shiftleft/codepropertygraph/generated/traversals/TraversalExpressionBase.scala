package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalExpressionBase[NodeType <: nodes.ExpressionBase](val traversal: Iterator[NodeType])
    extends AnyVal {

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

  /** Traverse to argumentName property */
  def argumentName: Iterator[String] =
    traversal.flatMap(_.argumentName)

  /** Traverse to nodes where the argumentName matches the regular expression `value`
    */
  def argumentName(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      argumentNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.argumentName; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the argumentName matches at least one of the regular expressions in `values`
    */
  def argumentName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.argumentName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where argumentName matches `value` exactly.
    */
  def argumentNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 2, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.argumentName; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where argumentName matches one of the elements in `values` exactly.
    */
  def argumentNameExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) argumentNameExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.argumentName; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where argumentName does not match the regular expression `value`.
    */
  def argumentNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.argumentName.isEmpty || node.argumentName.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.argumentName; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where argumentName does not match any of the regular expressions in `values`.
    */
  def argumentNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.argumentName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
