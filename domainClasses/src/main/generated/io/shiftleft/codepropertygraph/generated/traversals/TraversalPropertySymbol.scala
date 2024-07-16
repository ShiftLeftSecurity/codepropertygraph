package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertySymbol[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasSymbolEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to symbol property */
  def symbol: Iterator[String] =
    traversal.map(_.symbol)

  /** Traverse to nodes where the symbol matches the regular expression `value`
    */
  def symbol(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      symbolExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.symbol).matches }
    }

  /** Traverse to nodes where the symbol matches at least one of the regular expressions in `values`
    */
  def symbol(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.symbol).matches } }
  }

  /** Traverse to nodes where symbol matches `value` exactly.
    */
  def symbolExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 50, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.symbol == value }
  }

  /** Traverse to nodes where symbol matches one of the elements in `values` exactly.
    */
  def symbolExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) symbolExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.symbol) }
    }

  /** Traverse to nodes where symbol does not match the regular expression `value`.
    */
  def symbolNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.symbol != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.symbol).matches }
    }
  }

  /** Traverse to nodes where symbol does not match any of the regular expressions in `values`.
    */
  def symbolNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.symbol).matches }.isEmpty }
  }

}
