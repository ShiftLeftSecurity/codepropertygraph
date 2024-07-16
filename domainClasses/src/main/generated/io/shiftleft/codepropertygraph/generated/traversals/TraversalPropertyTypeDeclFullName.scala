package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyTypeDeclFullName[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasTypeDeclFullNameEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to typeDeclFullName property */
  def typeDeclFullName: Iterator[String] =
    traversal.map(_.typeDeclFullName)

  /** Traverse to nodes where the typeDeclFullName matches the regular expression `value`
    */
  def typeDeclFullName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      typeDeclFullNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.typeDeclFullName).matches }
    }

  /** Traverse to nodes where the typeDeclFullName matches at least one of the regular expressions in `values`
    */
  def typeDeclFullName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.typeDeclFullName).matches } }
  }

  /** Traverse to nodes where typeDeclFullName matches `value` exactly.
    */
  def typeDeclFullNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 51, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.typeDeclFullName == value }
  }

  /** Traverse to nodes where typeDeclFullName matches one of the elements in `values` exactly.
    */
  def typeDeclFullNameExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) typeDeclFullNameExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.typeDeclFullName) }
    }

  /** Traverse to nodes where typeDeclFullName does not match the regular expression `value`.
    */
  def typeDeclFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.typeDeclFullName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.typeDeclFullName).matches }
    }
  }

  /** Traverse to nodes where typeDeclFullName does not match any of the regular expressions in `values`.
    */
  def typeDeclFullNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.typeDeclFullName).matches }.isEmpty }
  }

}
