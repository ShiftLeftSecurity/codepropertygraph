package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyCanonicalName[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasCanonicalNameEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to canonicalName property */
  def canonicalName: Iterator[String] =
    traversal.map(_.canonicalName)

  /** Traverse to nodes where the canonicalName matches the regular expression `value`
    */
  def canonicalName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      canonicalNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.canonicalName).matches }
    }

  /** Traverse to nodes where the canonicalName matches at least one of the regular expressions in `values`
    */
  def canonicalName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.canonicalName).matches } }
  }

  /** Traverse to nodes where canonicalName matches `value` exactly.
    */
  def canonicalNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 5, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.canonicalName == value }
  }

  /** Traverse to nodes where canonicalName matches one of the elements in `values` exactly.
    */
  def canonicalNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return canonicalNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 5, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.canonicalName) }
    }
  }

  /** Traverse to nodes where canonicalName does not match the regular expression `value`.
    */
  def canonicalNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.canonicalName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.canonicalName).matches }
    }
  }

  /** Traverse to nodes where canonicalName does not match any of the regular expressions in `values`.
    */
  def canonicalNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.canonicalName).matches }.isEmpty }
  }

}
