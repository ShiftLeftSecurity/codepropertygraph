package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyClassName[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasClassNameEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to className property */
  def className: Iterator[String] =
    traversal.map(_.className)

  /** Traverse to nodes where the className matches the regular expression `value`
    */
  def className(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      classNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.className).matches }
    }

  /** Traverse to nodes where the className matches at least one of the regular expressions in `values`
    */
  def className(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.className).matches } }
  }

  /** Traverse to nodes where className matches `value` exactly.
    */
  def classNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 6, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.className == value }
  }

  /** Traverse to nodes where className matches one of the elements in `values` exactly.
    */
  def classNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return classNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 6, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.className) }
    }
  }

  /** Traverse to nodes where className does not match the regular expression `value`.
    */
  def classNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.className != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.className).matches }
    }
  }

  /** Traverse to nodes where className does not match any of the regular expressions in `values`.
    */
  def classNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.className).matches }.isEmpty }
  }

}
