package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyPackageName[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasPackageNameEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to packageName property */
  def packageName: Iterator[String] =
    traversal.map(_.packageName)

  /** Traverse to nodes where the packageName matches the regular expression `value`
    */
  def packageName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      packageNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.packageName).matches }
    }

  /** Traverse to nodes where the packageName matches at least one of the regular expressions in `values`
    */
  def packageName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.packageName).matches } }
  }

  /** Traverse to nodes where packageName matches `value` exactly.
    */
  def packageNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.packageName == value }
  }

  /** Traverse to nodes where packageName matches one of the elements in `values` exactly.
    */
  def packageNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return packageNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.packageName) }
    }
  }

  /** Traverse to nodes where packageName does not match the regular expression `value`.
    */
  def packageNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.packageName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.packageName).matches }
    }
  }

  /** Traverse to nodes where packageName does not match any of the regular expressions in `values`.
    */
  def packageNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.packageName).matches }.isEmpty }
  }

}
