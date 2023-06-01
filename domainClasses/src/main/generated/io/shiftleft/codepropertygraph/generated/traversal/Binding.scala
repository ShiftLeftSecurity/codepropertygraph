package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for Binding */
class BindingTraversalExtGen[NodeType <: Binding](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to TYPE_DECL via BINDS IN edge.
    */
  def bindingTypeDecl: Iterator[TypeDecl] =
    traversal.map(_.bindingTypeDecl)

  /** Traverse to METHOD via REF OUT edge.
    */
  def boundMethod: Iterator[Method] =
    traversal.map(_.boundMethod)

  /** Traverse to methodFullName property */
  def methodFullName: Iterator[String] =
    traversal.map(_.methodFullName)

  /** Traverse to nodes where the methodFullName matches the regular expression `value`
    */
  def methodFullName(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      methodFullNameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.methodFullName, pattern)
    }
  }

  /** Traverse to nodes where the methodFullName matches at least one of the regular expressions in `values`
    */
  def methodFullName(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.methodFullName, patterns)

  /** Traverse to nodes where methodFullName matches `value` exactly.
    */
  def methodFullNameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] =>
        init.getByIndex("METHOD_FULL_NAME", value).getOrElse(null)
      case _ => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.methodFullName == value }
  }

  /** Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
    */
  def methodFullNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      methodFullNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.methodFullName), values, "METHOD_FULL_NAME")
  }

  /** Traverse to nodes where methodFullName does not match the regular expression `value`.
    */
  def methodFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.methodFullName != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.methodFullName, pattern)
    }
  }

  /** Traverse to nodes where methodFullName does not match any of the regular expressions in `values`.
    */
  def methodFullNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.methodFullName, patterns)
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

  /** Traverse to signature property */
  def signature: Iterator[String] =
    traversal.map(_.signature)

  /** Traverse to nodes where the signature matches the regular expression `value`
    */
  def signature(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      signatureExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.signature, pattern)
    }
  }

  /** Traverse to nodes where the signature matches at least one of the regular expressions in `values`
    */
  def signature(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.signature, patterns)

  /** Traverse to nodes where signature matches `value` exactly.
    */
  def signatureExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("SIGNATURE", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.signature == value }
  }

  /** Traverse to nodes where signature matches one of the elements in `values` exactly.
    */
  def signatureExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      signatureExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.signature), values, "SIGNATURE")
  }

  /** Traverse to nodes where signature does not match the regular expression `value`.
    */
  def signatureNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.signature != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.signature, pattern)
    }
  }

  /** Traverse to nodes where signature does not match any of the regular expressions in `values`.
    */
  def signatureNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.signature, patterns)
  }

}
