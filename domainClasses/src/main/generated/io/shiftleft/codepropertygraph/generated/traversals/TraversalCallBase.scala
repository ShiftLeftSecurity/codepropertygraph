package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalCallBase[NodeType <: nodes.CallBase](val traversal: Iterator[NodeType]) extends AnyVal {

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

  /** Traverse to dynamicTypeHintFullName property */
  def dynamicTypeHintFullName: Iterator[String] =
    traversal.flatMap(_.dynamicTypeHintFullName)

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
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 53, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.typeFullName == value }
  }

  /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
    */
  def typeFullNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return typeFullNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 53, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
    }
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
