package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalDependencyBase[NodeType <: nodes.DependencyBase](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to dependencyGroupId property */
  def dependencyGroupId: Iterator[String] =
    traversal.flatMap(_.dependencyGroupId)

  /** Traverse to nodes where the dependencyGroupId matches the regular expression `value`
    */
  def dependencyGroupId(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      dependencyGroupIdExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.dependencyGroupId; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the dependencyGroupId matches at least one of the regular expressions in `values`
    */
  def dependencyGroupId(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.dependencyGroupId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where dependencyGroupId matches `value` exactly.
    */
  def dependencyGroupIdExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 16, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.dependencyGroupId; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where dependencyGroupId matches one of the elements in `values` exactly.
    */
  def dependencyGroupIdExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) dependencyGroupIdExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.dependencyGroupId; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where dependencyGroupId does not match the regular expression `value`.
    */
  def dependencyGroupIdNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.dependencyGroupId.isEmpty || node.dependencyGroupId.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.dependencyGroupId; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where dependencyGroupId does not match any of the regular expressions in `values`.
    */
  def dependencyGroupIdNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.dependencyGroupId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to name property */
  def name: Iterator[String] =
    traversal.map(_.name)

  /** Traverse to nodes where the name matches the regular expression `value`
    */
  def name(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      nameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.name).matches }
    }

  /** Traverse to nodes where the name matches at least one of the regular expressions in `values`
    */
  def name(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.name).matches } }
  }

  /** Traverse to nodes where name matches `value` exactly.
    */
  def nameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 40, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.name == value }
  }

  /** Traverse to nodes where name matches one of the elements in `values` exactly.
    */
  def nameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return nameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 40, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
    }
  }

  /** Traverse to nodes where name does not match the regular expression `value`.
    */
  def nameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.name != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.name).matches }
    }
  }

  /** Traverse to nodes where name does not match any of the regular expressions in `values`.
    */
  def nameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.name).matches }.isEmpty }
  }

  /** Traverse to version property */
  def version: Iterator[String] =
    traversal.map(_.version)

  /** Traverse to nodes where the version matches the regular expression `value`
    */
  def version(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      versionExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.version).matches }
    }

  /** Traverse to nodes where the version matches at least one of the regular expressions in `values`
    */
  def version(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.version).matches } }
  }

  /** Traverse to nodes where version matches `value` exactly.
    */
  def versionExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 55, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.version == value }
  }

  /** Traverse to nodes where version matches one of the elements in `values` exactly.
    */
  def versionExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return versionExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 55, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.version) }
    }
  }

  /** Traverse to nodes where version does not match the regular expression `value`.
    */
  def versionNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.version != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.version).matches }
    }
  }

  /** Traverse to nodes where version does not match any of the regular expressions in `values`.
    */
  def versionNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.version).matches }.isEmpty }
  }

}
