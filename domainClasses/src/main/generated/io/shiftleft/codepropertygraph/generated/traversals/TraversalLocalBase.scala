package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalLocalBase[NodeType <: nodes.LocalBase](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to closureBindingId property */
  def closureBindingId: Iterator[String] =
    traversal.flatMap(_.closureBindingId)

  /** Traverse to nodes where the closureBindingId matches the regular expression `value`
    */
  def closureBindingId(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      closureBindingIdExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the closureBindingId matches at least one of the regular expressions in `values`
    */
  def closureBindingId(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where closureBindingId matches `value` exactly.
    */
  def closureBindingIdExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 6, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.closureBindingId; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where closureBindingId matches one of the elements in `values` exactly.
    */
  def closureBindingIdExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) closureBindingIdExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.closureBindingId; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where closureBindingId does not match the regular expression `value`.
    */
  def closureBindingIdNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.closureBindingId.isEmpty || node.closureBindingId.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where closureBindingId does not match any of the regular expressions in `values`.
    */
  def closureBindingIdNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to dynamicTypeHintFullName property */
  def dynamicTypeHintFullName: Iterator[String] =
    traversal.flatMap(_.dynamicTypeHintFullName)

  /** Traverse to genericSignature property */
  def genericSignature: Iterator[String] =
    traversal.map(_.genericSignature)

  /** Traverse to nodes where the genericSignature matches the regular expression `value`
    */
  def genericSignature(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      genericSignatureExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.genericSignature).matches }
    }

  /** Traverse to nodes where the genericSignature matches at least one of the regular expressions in `values`
    */
  def genericSignature(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.genericSignature).matches } }
  }

  /** Traverse to nodes where genericSignature matches `value` exactly.
    */
  def genericSignatureExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 21, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.genericSignature == value }
  }

  /** Traverse to nodes where genericSignature matches one of the elements in `values` exactly.
    */
  def genericSignatureExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return genericSignatureExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 21, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.genericSignature) }
    }
  }

  /** Traverse to nodes where genericSignature does not match the regular expression `value`.
    */
  def genericSignatureNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.genericSignature != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.genericSignature).matches }
    }
  }

  /** Traverse to nodes where genericSignature does not match any of the regular expressions in `values`.
    */
  def genericSignatureNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.genericSignature).matches }.isEmpty }
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
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 48, value)
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
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 48, value)
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
