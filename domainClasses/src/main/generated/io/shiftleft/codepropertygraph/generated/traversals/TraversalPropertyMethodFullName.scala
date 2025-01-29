package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyMethodFullName[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasMethodFullNameEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to methodFullName property */
  def methodFullName: Iterator[String] =
    traversal.map(_.methodFullName)

  /** Traverse to nodes where the methodFullName matches the regular expression `value`
    */
  def methodFullName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      methodFullNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.methodFullName).matches }
    }

  /** Traverse to nodes where the methodFullName matches at least one of the regular expressions in `values`
    */
  def methodFullName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.methodFullName).matches } }
  }

  /** Traverse to nodes where methodFullName matches `value` exactly.
    */
  def methodFullNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 37, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.methodFullName == value }
  }

  /** Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
    */
  def methodFullNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return methodFullNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 37, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodFullName) }
    }
  }

  /** Traverse to nodes where methodFullName does not match the regular expression `value`.
    */
  def methodFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.methodFullName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.methodFullName).matches }
    }
  }

  /** Traverse to nodes where methodFullName does not match any of the regular expressions in `values`.
    */
  def methodFullNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.methodFullName).matches }.isEmpty }
  }

}
