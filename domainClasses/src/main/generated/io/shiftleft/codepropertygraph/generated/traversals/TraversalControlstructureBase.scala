package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalControlstructureBase[NodeType <: nodes.ControlStructureBase](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to controlStructureType property */
  def controlStructureType: Iterator[String] =
    traversal.map(_.controlStructureType)

  /** Traverse to nodes where the controlStructureType matches the regular expression `value`
    */
  def controlStructureType(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      controlStructureTypeExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.controlStructureType).matches }
    }

  /** Traverse to nodes where the controlStructureType matches at least one of the regular expressions in `values`
    */
  def controlStructureType(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.controlStructureType).matches } }
  }

  /** Traverse to nodes where controlStructureType matches `value` exactly.
    */
  def controlStructureTypeExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 15, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.controlStructureType == value }
  }

  /** Traverse to nodes where controlStructureType matches one of the elements in `values` exactly.
    */
  def controlStructureTypeExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return controlStructureTypeExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 15, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.controlStructureType) }
    }
  }

  /** Traverse to nodes where controlStructureType does not match the regular expression `value`.
    */
  def controlStructureTypeNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.controlStructureType != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.controlStructureType).matches }
    }
  }

  /** Traverse to nodes where controlStructureType does not match any of the regular expressions in `values`.
    */
  def controlStructureTypeNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.controlStructureType).matches }.isEmpty }
  }

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
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 47, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.parserTypeName == value }
  }

  /** Traverse to nodes where parserTypeName matches one of the elements in `values` exactly.
    */
  def parserTypeNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return parserTypeNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 47, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.parserTypeName) }
    }
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

}
