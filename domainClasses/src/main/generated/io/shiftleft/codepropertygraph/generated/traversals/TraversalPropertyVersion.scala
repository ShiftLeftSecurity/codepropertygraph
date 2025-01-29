package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyVersion[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasVersionEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to version property */
  def version: Iterator[String] =
    traversal.map(_.version)

  /** Traverse to nodes where the version matches the regular expression `value`
    */
  def version(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      versionExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.version).matches }
    }

  /** Traverse to nodes where the version matches at least one of the regular expressions in `values`
    */
  def version(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.version).matches } }
  }

  /** Traverse to nodes where version matches `value` exactly.
    */
  def versionExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 55, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.version == value }
  }

  /** Traverse to nodes where version matches one of the elements in `values` exactly.
    */
  def versionExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return versionExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 55, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.version) }
    }
  }

  /** Traverse to nodes where version does not match the regular expression `value`.
    */
  def versionNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.version != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.version).matches }
    }
  }

  /** Traverse to nodes where version does not match any of the regular expressions in `values`.
    */
  def versionNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.version).matches }.isEmpty }
  }

}
