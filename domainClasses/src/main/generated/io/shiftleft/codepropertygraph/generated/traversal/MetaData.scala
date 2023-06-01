package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for MetaData */
class MetaDataTraversalExtGen[NodeType <: MetaData](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to hash property */
  def hash: Iterator[String] =
    traversal.flatMap(_.hash)

  /** Traverse to nodes where the hash matches the regular expression `value`
    */
  def hash(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.hash.isDefined && node.hash.get == pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal.filter(_.hash.isDefined))(_.hash.get, pattern)
    }
  }

  /** Traverse to nodes where the hash matches at least one of the regular expressions in `values`
    */
  def hash(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpMultiple(traversal.filter(_.hash.isDefined))(_.hash.get, patterns)
  }

  /** Traverse to nodes where hash matches `value` exactly.
    */
  def hashExact(value: String): Iterator[NodeType] =
    traversal.filter { node => node.hash.contains(value) }

  /** Traverse to nodes where hash matches one of the elements in `values` exactly.
    */
  def hashExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      hashExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, _.hash, values, "HASH")
  }

  /** Traverse to nodes where hash does not match the regular expression `value`.
    */
  def hashNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.hash.isEmpty || node.hash.get != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexpNot(traversal.filter(_.hash.isDefined))(_.hash.get, pattern)
    }
  }

  /** Traverse to nodes where hash does not match any of the regular expressions in `values`.
    */
  def hashNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpNotMultiple(traversal.filter(_.hash.isDefined))(_.hash.get, patterns)
  }

  /** Traverse to language property */
  def language: Iterator[String] =
    traversal.map(_.language)

  /** Traverse to nodes where the language matches the regular expression `value`
    */
  def language(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      languageExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.language, pattern)
    }
  }

  /** Traverse to nodes where the language matches at least one of the regular expressions in `values`
    */
  def language(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.language, patterns)

  /** Traverse to nodes where language matches `value` exactly.
    */
  def languageExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("LANGUAGE", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.language == value }
  }

  /** Traverse to nodes where language matches one of the elements in `values` exactly.
    */
  def languageExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      languageExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.language), values, "LANGUAGE")
  }

  /** Traverse to nodes where language does not match the regular expression `value`.
    */
  def languageNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.language != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.language, pattern)
    }
  }

  /** Traverse to nodes where language does not match any of the regular expressions in `values`.
    */
  def languageNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.language, patterns)
  }

  /** Traverse to overlays property */
  def overlays: Iterator[String] =
    traversal.flatMap(_.overlays)

  /** Traverse to root property */
  def root: Iterator[String] =
    traversal.map(_.root)

  /** Traverse to nodes where the root matches the regular expression `value`
    */
  def root(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      rootExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.root, pattern)
    }
  }

  /** Traverse to nodes where the root matches at least one of the regular expressions in `values`
    */
  def root(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.root, patterns)

  /** Traverse to nodes where root matches `value` exactly.
    */
  def rootExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("ROOT", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.root == value }
  }

  /** Traverse to nodes where root matches one of the elements in `values` exactly.
    */
  def rootExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      rootExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.root), values, "ROOT")
  }

  /** Traverse to nodes where root does not match the regular expression `value`.
    */
  def rootNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.root != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.root, pattern)
    }
  }

  /** Traverse to nodes where root does not match any of the regular expressions in `values`.
    */
  def rootNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.root, patterns)
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
