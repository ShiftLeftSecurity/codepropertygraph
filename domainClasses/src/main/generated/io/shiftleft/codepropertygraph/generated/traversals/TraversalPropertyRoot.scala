package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyRoot[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasRootEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to root property */
  def root: Iterator[String] =
    traversal.map(_.root)

  /** Traverse to nodes where the root matches the regular expression `value`
    */
  def root(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      rootExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.root).matches }
    }

  /** Traverse to nodes where the root matches at least one of the regular expressions in `values`
    */
  def root(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.root).matches } }
  }

  /** Traverse to nodes where root matches `value` exactly.
    */
  def rootExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 48, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.root == value }
  }

  /** Traverse to nodes where root matches one of the elements in `values` exactly.
    */
  def rootExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) rootExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.root) }
    }

  /** Traverse to nodes where root does not match the regular expression `value`.
    */
  def rootNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.root != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.root).matches }
    }
  }

  /** Traverse to nodes where root does not match any of the regular expressions in `values`.
    */
  def rootNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.root).matches }.isEmpty }
  }

}
