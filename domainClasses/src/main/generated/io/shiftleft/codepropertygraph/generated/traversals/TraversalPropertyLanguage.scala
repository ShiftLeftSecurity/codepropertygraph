package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyLanguage[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasLanguageEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to language property */
  def language: Iterator[String] =
    traversal.map(_.language)

  /** Traverse to nodes where the language matches the regular expression `value`
    */
  def language(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      languageExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.language).matches }
    }

  /** Traverse to nodes where the language matches at least one of the regular expressions in `values`
    */
  def language(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.language).matches } }
  }

  /** Traverse to nodes where language matches `value` exactly.
    */
  def languageExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 34, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.language == value }
  }

  /** Traverse to nodes where language matches one of the elements in `values` exactly.
    */
  def languageExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return languageExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 34, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.language) }
    }
  }

  /** Traverse to nodes where language does not match the regular expression `value`.
    */
  def languageNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.language != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.language).matches }
    }
  }

  /** Traverse to nodes where language does not match any of the regular expressions in `values`.
    */
  def languageNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.language).matches }.isEmpty }
  }

}
