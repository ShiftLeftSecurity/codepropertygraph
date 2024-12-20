package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyClassShortName[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasClassShortNameEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to classShortName property */
  def classShortName: Iterator[String] =
    traversal.map(_.classShortName)

  /** Traverse to nodes where the classShortName matches the regular expression `value`
    */
  def classShortName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      classShortNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.classShortName).matches }
    }

  /** Traverse to nodes where the classShortName matches at least one of the regular expressions in `values`
    */
  def classShortName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.classShortName).matches } }
  }

  /** Traverse to nodes where classShortName matches `value` exactly.
    */
  def classShortNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 7, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.classShortName == value }
  }

  /** Traverse to nodes where classShortName matches one of the elements in `values` exactly.
    */
  def classShortNameExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) classShortNameExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.classShortName) }
    }

  /** Traverse to nodes where classShortName does not match the regular expression `value`.
    */
  def classShortNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.classShortName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.classShortName).matches }
    }
  }

  /** Traverse to nodes where classShortName does not match any of the regular expressions in `values`.
    */
  def classShortNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.classShortName).matches }.isEmpty }
  }

}
