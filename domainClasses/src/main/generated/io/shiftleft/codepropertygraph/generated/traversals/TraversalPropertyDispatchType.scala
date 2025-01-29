package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyDispatchType[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasDispatchTypeEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to dispatchType property */
  def dispatchType: Iterator[String] =
    traversal.map(_.dispatchType)

  /** Traverse to nodes where the dispatchType matches the regular expression `value`
    */
  def dispatchType(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      dispatchTypeExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.dispatchType).matches }
    }

  /** Traverse to nodes where the dispatchType matches at least one of the regular expressions in `values`
    */
  def dispatchType(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.dispatchType).matches } }
  }

  /** Traverse to nodes where dispatchType matches `value` exactly.
    */
  def dispatchTypeExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 17, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.dispatchType == value }
  }

  /** Traverse to nodes where dispatchType matches one of the elements in `values` exactly.
    */
  def dispatchTypeExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return dispatchTypeExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 17, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.dispatchType) }
    }
  }

  /** Traverse to nodes where dispatchType does not match the regular expression `value`.
    */
  def dispatchTypeNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.dispatchType != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.dispatchType).matches }
    }
  }

  /** Traverse to nodes where dispatchType does not match any of the regular expressions in `values`.
    */
  def dispatchTypeNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.dispatchType).matches }.isEmpty }
  }

}
