package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for KeyValuePair */
class KeyValuePairTraversalExtGen[NodeType <: KeyValuePair](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to key property */
  def key: Iterator[String] =
    traversal.map(_.key)

  /** Traverse to nodes where the key matches the regular expression `value`
    */
  def key(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      keyExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.key, pattern)
    }
  }

  /** Traverse to nodes where the key matches at least one of the regular expressions in `values`
    */
  def key(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.key, patterns)

  /** Traverse to nodes where key matches `value` exactly.
    */
  def keyExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("KEY", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.key == value }
  }

  /** Traverse to nodes where key matches one of the elements in `values` exactly.
    */
  def keyExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      keyExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.key), values, "KEY")
  }

  /** Traverse to nodes where key does not match the regular expression `value`.
    */
  def keyNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.key != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.key, pattern)
    }
  }

  /** Traverse to nodes where key does not match any of the regular expressions in `values`.
    */
  def keyNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.key, patterns)
  }

  /** Traverse to value property */
  def value: Iterator[String] =
    traversal.map(_.value)

  /** Traverse to nodes where the value matches the regular expression `value`
    */
  def value(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      valueExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.value, pattern)
    }
  }

  /** Traverse to nodes where the value matches at least one of the regular expressions in `values`
    */
  def value(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.value, patterns)

  /** Traverse to nodes where value matches `value` exactly.
    */
  def valueExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("VALUE", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.value == value }
  }

  /** Traverse to nodes where value matches one of the elements in `values` exactly.
    */
  def valueExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      valueExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.value), values, "VALUE")
  }

  /** Traverse to nodes where value does not match the regular expression `value`.
    */
  def valueNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.value != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.value, pattern)
    }
  }

  /** Traverse to nodes where value does not match any of the regular expressions in `values`.
    */
  def valueNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.value, patterns)
  }

}
