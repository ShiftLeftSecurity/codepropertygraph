package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyMethodShortName[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasMethodShortNameEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to methodShortName property */
  def methodShortName: Iterator[String] =
    traversal.map(_.methodShortName)

  /** Traverse to nodes where the methodShortName matches the regular expression `value`
    */
  def methodShortName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      methodShortNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.methodShortName).matches }
    }

  /** Traverse to nodes where the methodShortName matches at least one of the regular expressions in `values`
    */
  def methodShortName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.methodShortName).matches } }
  }

  /** Traverse to nodes where methodShortName matches `value` exactly.
    */
  def methodShortNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 37, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.methodShortName == value }
  }

  /** Traverse to nodes where methodShortName matches one of the elements in `values` exactly.
    */
  def methodShortNameExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) methodShortNameExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.methodShortName) }
    }

  /** Traverse to nodes where methodShortName does not match the regular expression `value`.
    */
  def methodShortNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.methodShortName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.methodShortName).matches }
    }
  }

  /** Traverse to nodes where methodShortName does not match any of the regular expressions in `values`.
    */
  def methodShortNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.methodShortName).matches }.isEmpty }
  }

}
