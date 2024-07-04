package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalUnknownBase[NodeType <: nodes.UnknownBase](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to containedRef property */
  def containedRef: Iterator[String] =
    traversal.map(_.containedRef)

  /** Traverse to nodes where the containedRef matches the regular expression `value`
    */
  def containedRef(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      containedRefExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.containedRef).matches }
    }

  /** Traverse to nodes where the containedRef matches at least one of the regular expressions in `values`
    */
  def containedRef(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.containedRef).matches } }
  }

  /** Traverse to nodes where containedRef matches `value` exactly.
    */
  def containedRefExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 13, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.containedRef == value }
  }

  /** Traverse to nodes where containedRef matches one of the elements in `values` exactly.
    */
  def containedRefExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) containedRefExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.containedRef) }
    }

  /** Traverse to nodes where containedRef does not match the regular expression `value`.
    */
  def containedRefNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.containedRef != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.containedRef).matches }
    }
  }

  /** Traverse to nodes where containedRef does not match any of the regular expressions in `values`.
    */
  def containedRefNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.containedRef).matches }.isEmpty }
  }

  /** Traverse to dynamicTypeHintFullName property */
  def dynamicTypeHintFullName: Iterator[String] =
    traversal.flatMap(_.dynamicTypeHintFullName)

  /** Traverse to parserTypeName property */
  def parserTypeName: Iterator[String] =
    traversal.map(_.parserTypeName)

  /** Traverse to nodes where the parserTypeName matches the regular expression `value`
    */
  def parserTypeName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      parserTypeNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.parserTypeName).matches }
    }

  /** Traverse to nodes where the parserTypeName matches at least one of the regular expressions in `values`
    */
  def parserTypeName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.parserTypeName).matches } }
  }

  /** Traverse to nodes where parserTypeName matches `value` exactly.
    */
  def parserTypeNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.parserTypeName == value }
  }

  /** Traverse to nodes where parserTypeName matches one of the elements in `values` exactly.
    */
  def parserTypeNameExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) parserTypeNameExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.parserTypeName) }
    }

  /** Traverse to nodes where parserTypeName does not match the regular expression `value`.
    */
  def parserTypeNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.parserTypeName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.parserTypeName).matches }
    }
  }

  /** Traverse to nodes where parserTypeName does not match any of the regular expressions in `values`.
    */
  def parserTypeNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.parserTypeName).matches }.isEmpty }
  }

  /** Traverse to possibleTypes property */
  def possibleTypes: Iterator[String] =
    traversal.flatMap(_.possibleTypes)

  /** Traverse to typeFullName property */
  def typeFullName: Iterator[String] =
    traversal.map(_.typeFullName)

  /** Traverse to nodes where the typeFullName matches the regular expression `value`
    */
  def typeFullName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      typeFullNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.typeFullName).matches }
    }

  /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
    */
  def typeFullName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
  }

  /** Traverse to nodes where typeFullName matches `value` exactly.
    */
  def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.typeFullName == value }
  }

  /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
    */
  def typeFullNameExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) typeFullNameExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.typeFullName) }
    }

  /** Traverse to nodes where typeFullName does not match the regular expression `value`.
    */
  def typeFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.typeFullName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
    }
  }

  /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
    */
  def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
  }

}
