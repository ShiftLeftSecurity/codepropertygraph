package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyContainedRef[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasContainedRefEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to containedRef property */
  def containedRef: Iterator[String] =
    traversal.map(_.containedRef)

  /** Traverse to nodes where the containedRef matches the regular expression `value`
    */
  def containedRef(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      containedRefExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.containedRef).matches }
    }

  /** Traverse to nodes where the containedRef matches at least one of the regular expressions in `values`
    */
  def containedRef(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.containedRef).matches } }
  }

  /** Traverse to nodes where containedRef matches `value` exactly.
    */
  def containedRefExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 13, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.containedRef == value }
  }

  /** Traverse to nodes where containedRef matches one of the elements in `values` exactly.
    */
  def containedRefExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) containedRefExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.containedRef) }
    }

  /** Traverse to nodes where containedRef does not match the regular expression `value`.
    */
  def containedRefNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.containedRef != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.containedRef).matches }
    }
  }

  /** Traverse to nodes where containedRef does not match any of the regular expressions in `values`.
    */
  def containedRefNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.containedRef).matches }.isEmpty }
  }

}
