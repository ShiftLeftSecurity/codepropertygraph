package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for Type */
class TypeTraversalExtGen[NodeType <: Type](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Direct alias type declarations. Traverse to TYPE_DECL via ALIAS_OF IN edge.
    */
  /** Direct alias type declarations. */
  @overflowdb.traversal.help.Doc(info = """Direct alias type declarations.""")
  def aliasTypeDecl: Iterator[TypeDecl] =
    traversal.flatMap(_.aliasTypeDecl)

  /** Type declaration which is referenced by this type. Traverse to TYPE_DECL via REF OUT edge.
    */
  /** Type declaration which is referenced by this type. */
  @overflowdb.traversal.help.Doc(info = """Type declaration which is referenced by this type.""")
  def referencedTypeDecl: Iterator[TypeDecl] =
    traversal.flatMap(_.referencedTypeDecl)

  /** Traverse to fullName property */
  def fullName: Iterator[String] =
    traversal.map(_.fullName)

  /** Traverse to nodes where the fullName matches the regular expression `value`
    */
  def fullName(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      fullNameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.fullName, pattern)
    }
  }

  /** Traverse to nodes where the fullName matches at least one of the regular expressions in `values`
    */
  def fullName(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.fullName, patterns)

  /** Traverse to nodes where fullName matches `value` exactly.
    */
  def fullNameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("FULL_NAME", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.fullName == value }
  }

  /** Traverse to nodes where fullName matches one of the elements in `values` exactly.
    */
  def fullNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      fullNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.fullName), values, "FULL_NAME")
  }

  /** Traverse to nodes where fullName does not match the regular expression `value`.
    */
  def fullNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.fullName != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.fullName, pattern)
    }
  }

  /** Traverse to nodes where fullName does not match any of the regular expressions in `values`.
    */
  def fullNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.fullName, patterns)
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

  /** Traverse to typeDeclFullName property */
  def typeDeclFullName: Iterator[String] =
    traversal.map(_.typeDeclFullName)

  /** Traverse to nodes where the typeDeclFullName matches the regular expression `value`
    */
  def typeDeclFullName(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      typeDeclFullNameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.typeDeclFullName, pattern)
    }
  }

  /** Traverse to nodes where the typeDeclFullName matches at least one of the regular expressions in `values`
    */
  def typeDeclFullName(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.typeDeclFullName, patterns)

  /** Traverse to nodes where typeDeclFullName matches `value` exactly.
    */
  def typeDeclFullNameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] =>
        init.getByIndex("TYPE_DECL_FULL_NAME", value).getOrElse(null)
      case _ => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.typeDeclFullName == value }
  }

  /** Traverse to nodes where typeDeclFullName matches one of the elements in `values` exactly.
    */
  def typeDeclFullNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      typeDeclFullNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.typeDeclFullName), values, "TYPE_DECL_FULL_NAME")
  }

  /** Traverse to nodes where typeDeclFullName does not match the regular expression `value`.
    */
  def typeDeclFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.typeDeclFullName != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.typeDeclFullName, pattern)
    }
  }

  /** Traverse to nodes where typeDeclFullName does not match any of the regular expressions in `values`.
    */
  def typeDeclFullNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.typeDeclFullName, patterns)
  }

}
