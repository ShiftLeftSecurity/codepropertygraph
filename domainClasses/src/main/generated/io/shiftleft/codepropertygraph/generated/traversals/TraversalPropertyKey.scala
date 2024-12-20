package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyKey[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasKeyEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to key property */
  def key: Iterator[String] =
    traversal.map(_.key)

  /** Traverse to nodes where the key matches the regular expression `value`
    */
  def key(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      keyExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.key).matches }
    }

  /** Traverse to nodes where the key matches at least one of the regular expressions in `values`
    */
  def key(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.key).matches } }
  }

  /** Traverse to nodes where key matches `value` exactly.
    */
  def keyExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 32, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.key == value }
  }

  /** Traverse to nodes where key matches one of the elements in `values` exactly.
    */
  def keyExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) keyExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.key) }
    }

  /** Traverse to nodes where key does not match the regular expression `value`.
    */
  def keyNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.key != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.key).matches }
    }
  }

  /** Traverse to nodes where key does not match any of the regular expressions in `values`.
    */
  def keyNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.key).matches }.isEmpty }
  }

}
