package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalModifierBase[NodeType <: nodes.ModifierBase](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to modifierType property */
  def modifierType: Iterator[String] =
    traversal.map(_.modifierType)

  /** Traverse to nodes where the modifierType matches the regular expression `value`
    */
  def modifierType(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      modifierTypeExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.modifierType).matches }
    }

  /** Traverse to nodes where the modifierType matches at least one of the regular expressions in `values`
    */
  def modifierType(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.modifierType).matches } }
  }

  /** Traverse to nodes where modifierType matches `value` exactly.
    */
  def modifierTypeExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 38, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.modifierType == value }
  }

  /** Traverse to nodes where modifierType matches one of the elements in `values` exactly.
    */
  def modifierTypeExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) modifierTypeExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.modifierType) }
    }

  /** Traverse to nodes where modifierType does not match the regular expression `value`.
    */
  def modifierTypeNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.modifierType != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.modifierType).matches }
    }
  }

  /** Traverse to nodes where modifierType does not match any of the regular expressions in `values`.
    */
  def modifierTypeNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.modifierType).matches }.isEmpty }
  }

}
