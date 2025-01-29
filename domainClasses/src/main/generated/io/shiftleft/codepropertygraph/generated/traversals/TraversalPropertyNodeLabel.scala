package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyNodeLabel[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasNodeLabelEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to nodeLabel property */
  def nodeLabel: Iterator[String] =
    traversal.map(_.nodeLabel)

  /** Traverse to nodes where the nodeLabel matches the regular expression `value`
    */
  def nodeLabel(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      nodeLabelExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.nodeLabel).matches }
    }

  /** Traverse to nodes where the nodeLabel matches at least one of the regular expressions in `values`
    */
  def nodeLabel(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.nodeLabel).matches } }
  }

  /** Traverse to nodes where nodeLabel matches `value` exactly.
    */
  def nodeLabelExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 41, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.nodeLabel == value }
  }

  /** Traverse to nodes where nodeLabel matches one of the elements in `values` exactly.
    */
  def nodeLabelExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return nodeLabelExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 41, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.nodeLabel) }
    }
  }

  /** Traverse to nodes where nodeLabel does not match the regular expression `value`.
    */
  def nodeLabelNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.nodeLabel != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.nodeLabel).matches }
    }
  }

  /** Traverse to nodes where nodeLabel does not match any of the regular expressions in `values`.
    */
  def nodeLabelNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.nodeLabel).matches }.isEmpty }
  }

}
