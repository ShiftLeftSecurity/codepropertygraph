package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyContent[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasContentEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to content property */
  def content: Iterator[String] =
    traversal.map(_.content)

  /** Traverse to nodes where the content matches the regular expression `value`
    */
  def content(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      contentExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.content).matches }
    }

  /** Traverse to nodes where the content matches at least one of the regular expressions in `values`
    */
  def content(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.content).matches } }
  }

  /** Traverse to nodes where content matches `value` exactly.
    */
  def contentExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 14, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.content == value }
  }

  /** Traverse to nodes where content matches one of the elements in `values` exactly.
    */
  def contentExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return contentExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 14, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.content) }
    }
  }

  /** Traverse to nodes where content does not match the regular expression `value`.
    */
  def contentNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.content != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.content).matches }
    }
  }

  /** Traverse to nodes where content does not match any of the regular expressions in `values`.
    */
  def contentNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.content).matches }.isEmpty }
  }

}
