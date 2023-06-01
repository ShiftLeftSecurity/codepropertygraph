package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for Dependency */
class DependencyTraversalExtGen[NodeType <: Dependency](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to dependencyGroupId property */
  def dependencyGroupId: Iterator[String] =
    traversal.flatMap(_.dependencyGroupId)

  /** Traverse to nodes where the dependencyGroupId matches the regular expression `value`
    */
  def dependencyGroupId(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.dependencyGroupId.isDefined && node.dependencyGroupId.get == pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexp(traversal.filter(_.dependencyGroupId.isDefined))(_.dependencyGroupId.get, pattern)
    }
  }

  /** Traverse to nodes where the dependencyGroupId matches at least one of the regular expressions in `values`
    */
  def dependencyGroupId(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpMultiple(traversal.filter(_.dependencyGroupId.isDefined))(_.dependencyGroupId.get, patterns)
  }

  /** Traverse to nodes where dependencyGroupId matches `value` exactly.
    */
  def dependencyGroupIdExact(value: String): Iterator[NodeType] =
    traversal.filter { node => node.dependencyGroupId.contains(value) }

  /** Traverse to nodes where dependencyGroupId matches one of the elements in `values` exactly.
    */
  def dependencyGroupIdExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      dependencyGroupIdExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, _.dependencyGroupId, values, "DEPENDENCY_GROUP_ID")
  }

  /** Traverse to nodes where dependencyGroupId does not match the regular expression `value`.
    */
  def dependencyGroupIdNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.dependencyGroupId.isEmpty || node.dependencyGroupId.get != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexpNot(traversal.filter(_.dependencyGroupId.isDefined))(_.dependencyGroupId.get, pattern)
    }
  }

  /** Traverse to nodes where dependencyGroupId does not match any of the regular expressions in `values`.
    */
  def dependencyGroupIdNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpNotMultiple(traversal.filter(_.dependencyGroupId.isDefined))(_.dependencyGroupId.get, patterns)
  }

  /** Traverse to name property */
  def name: Iterator[String] =
    traversal.map(_.name)

  /** Traverse to nodes where the name matches the regular expression `value`
    */
  def name(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      nameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.name, pattern)
    }
  }

  /** Traverse to nodes where the name matches at least one of the regular expressions in `values`
    */
  def name(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.name, patterns)

  /** Traverse to nodes where name matches `value` exactly.
    */
  def nameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("NAME", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.name == value }
  }

  /** Traverse to nodes where name matches one of the elements in `values` exactly.
    */
  def nameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      nameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.name), values, "NAME")
  }

  /** Traverse to nodes where name does not match the regular expression `value`.
    */
  def nameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.name != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.name, pattern)
    }
  }

  /** Traverse to nodes where name does not match any of the regular expressions in `values`.
    */
  def nameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.name, patterns)
  }

  /** Traverse to version property */
  def version: Iterator[String] =
    traversal.map(_.version)

  /** Traverse to nodes where the version matches the regular expression `value`
    */
  def version(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      versionExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.version, pattern)
    }
  }

  /** Traverse to nodes where the version matches at least one of the regular expressions in `values`
    */
  def version(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.version, patterns)

  /** Traverse to nodes where version matches `value` exactly.
    */
  def versionExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("VERSION", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.version == value }
  }

  /** Traverse to nodes where version matches one of the elements in `values` exactly.
    */
  def versionExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      versionExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.version), values, "VERSION")
  }

  /** Traverse to nodes where version does not match the regular expression `value`.
    */
  def versionNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.version != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.version, pattern)
    }
  }

  /** Traverse to nodes where version does not match any of the regular expressions in `values`.
    */
  def versionNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.version, patterns)
  }

}
