package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for Method */
class MethodTraversalExtGen[NodeType <: Method](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  /** Root of the abstract syntax tree */
  @overflowdb.traversal.help.Doc(info = """Root of the abstract syntax tree""")
  def block: Iterator[Block] =
    traversal.map(_.block)

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  /** First control flow graph node */
  @overflowdb.traversal.help.Doc(info = """First control flow graph node""")
  def cfgFirst: Iterator[CfgNode] =
    traversal.flatMap(_.cfgFirst)

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  /** Literals used in the method */
  @overflowdb.traversal.help.Doc(info = """Literals used in the method""")
  def literal: Iterator[Literal] =
    traversal.flatMap(_.literal)

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  /** Formal return parameters */
  @overflowdb.traversal.help.Doc(info = """Formal return parameters""")
  def methodReturn: Iterator[MethodReturn] =
    traversal.map(_.methodReturn)

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  /** Parameters of the method */
  @overflowdb.traversal.help.Doc(info = """Parameters of the method""")
  def parameter: Iterator[MethodParameterIn] =
    traversal.flatMap(_.parameter)

  /** Traverse to astParentFullName property */
  def astParentFullName: Iterator[String] =
    traversal.map(_.astParentFullName)

  /** Traverse to nodes where the astParentFullName matches the regular expression `value`
    */
  def astParentFullName(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      astParentFullNameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.astParentFullName, pattern)
    }
  }

  /** Traverse to nodes where the astParentFullName matches at least one of the regular expressions in `values`
    */
  def astParentFullName(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.astParentFullName, patterns)

  /** Traverse to nodes where astParentFullName matches `value` exactly.
    */
  def astParentFullNameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] =>
        init.getByIndex("AST_PARENT_FULL_NAME", value).getOrElse(null)
      case _ => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.astParentFullName == value }
  }

  /** Traverse to nodes where astParentFullName matches one of the elements in `values` exactly.
    */
  def astParentFullNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      astParentFullNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter.exactMultiple[NodeType, String](
        traversal,
        node => Some(node.astParentFullName),
        values,
        "AST_PARENT_FULL_NAME"
      )
  }

  /** Traverse to nodes where astParentFullName does not match the regular expression `value`.
    */
  def astParentFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.astParentFullName != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.astParentFullName, pattern)
    }
  }

  /** Traverse to nodes where astParentFullName does not match any of the regular expressions in `values`.
    */
  def astParentFullNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.astParentFullName, patterns)
  }

  /** Traverse to astParentType property */
  def astParentType: Iterator[String] =
    traversal.map(_.astParentType)

  /** Traverse to nodes where the astParentType matches the regular expression `value`
    */
  def astParentType(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      astParentTypeExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.astParentType, pattern)
    }
  }

  /** Traverse to nodes where the astParentType matches at least one of the regular expressions in `values`
    */
  def astParentType(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.astParentType, patterns)

  /** Traverse to nodes where astParentType matches `value` exactly.
    */
  def astParentTypeExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] =>
        init.getByIndex("AST_PARENT_TYPE", value).getOrElse(null)
      case _ => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.astParentType == value }
  }

  /** Traverse to nodes where astParentType matches one of the elements in `values` exactly.
    */
  def astParentTypeExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      astParentTypeExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.astParentType), values, "AST_PARENT_TYPE")
  }

  /** Traverse to nodes where astParentType does not match the regular expression `value`.
    */
  def astParentTypeNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.astParentType != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.astParentType, pattern)
    }
  }

  /** Traverse to nodes where astParentType does not match any of the regular expressions in `values`.
    */
  def astParentTypeNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.astParentType, patterns)
  }

  /** Traverse to code property */
  def code: Iterator[String] =
    traversal.map(_.code)

  /** Traverse to nodes where the code matches the regular expression `value`
    */
  def code(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      codeExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.code, pattern)
    }
  }

  /** Traverse to nodes where the code matches at least one of the regular expressions in `values`
    */
  def code(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.code, patterns)

  /** Traverse to nodes where code matches `value` exactly.
    */
  def codeExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("CODE", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.code == value }
  }

  /** Traverse to nodes where code matches one of the elements in `values` exactly.
    */
  def codeExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      codeExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.code), values, "CODE")
  }

  /** Traverse to nodes where code does not match the regular expression `value`.
    */
  def codeNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.code != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.code, pattern)
    }
  }

  /** Traverse to nodes where code does not match any of the regular expressions in `values`.
    */
  def codeNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.code, patterns)
  }

  /** Traverse to columnNumber property */
  def columnNumber: Iterator[Integer] =
    traversal.flatMap(_.columnNumber)

  /** Traverse to nodes where the columnNumber equals the given `value`
    */
  def columnNumber(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumber.isDefined && node.columnNumber.get == value }

  /** Traverse to nodes where the columnNumber equals at least one of the given `values`
    */
  def columnNumber(values: Integer*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => node.columnNumber.isDefined && vset.contains(node.columnNumber.get) }
  }

  /** Traverse to nodes where the columnNumber is greater than the given `value`
    */
  def columnNumberGt(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumber.isDefined && node.columnNumber.get > value }

  /** Traverse to nodes where the columnNumber is greater than or equal the given `value`
    */
  def columnNumberGte(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumber.isDefined && node.columnNumber.get >= value }

  /** Traverse to nodes where the columnNumber is less than the given `value`
    */
  def columnNumberLt(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumber.isDefined && node.columnNumber.get < value }

  /** Traverse to nodes where the columnNumber is less than or equal the given `value`
    */
  def columnNumberLte(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumber.isDefined && node.columnNumber.get <= value }

  /** Traverse to nodes where columnNumber is not equal to the given `value`.
    */
  def columnNumberNot(value: Integer): Iterator[NodeType] =
    traversal.filter { node => !node.columnNumber.isDefined || node.columnNumber.get != value }

  /** Traverse to nodes where columnNumber is not equal to any of the given `values`.
    */
  def columnNumberNot(values: Integer*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => !node.columnNumber.isDefined || !vset.contains(node.columnNumber.get) }
  }

  /** Traverse to columnNumberEnd property */
  def columnNumberEnd: Iterator[Integer] =
    traversal.flatMap(_.columnNumberEnd)

  /** Traverse to nodes where the columnNumberEnd equals the given `value`
    */
  def columnNumberEnd(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get == value }

  /** Traverse to nodes where the columnNumberEnd equals at least one of the given `values`
    */
  def columnNumberEnd(values: Integer*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => node.columnNumberEnd.isDefined && vset.contains(node.columnNumberEnd.get) }
  }

  /** Traverse to nodes where the columnNumberEnd is greater than the given `value`
    */
  def columnNumberEndGt(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get > value }

  /** Traverse to nodes where the columnNumberEnd is greater than or equal the given `value`
    */
  def columnNumberEndGte(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get >= value }

  /** Traverse to nodes where the columnNumberEnd is less than the given `value`
    */
  def columnNumberEndLt(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get < value }

  /** Traverse to nodes where the columnNumberEnd is less than or equal the given `value`
    */
  def columnNumberEndLte(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get <= value }

  /** Traverse to nodes where columnNumberEnd is not equal to the given `value`.
    */
  def columnNumberEndNot(value: Integer): Iterator[NodeType] =
    traversal.filter { node => !node.columnNumberEnd.isDefined || node.columnNumberEnd.get != value }

  /** Traverse to nodes where columnNumberEnd is not equal to any of the given `values`.
    */
  def columnNumberEndNot(values: Integer*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => !node.columnNumberEnd.isDefined || !vset.contains(node.columnNumberEnd.get) }
  }

  /** Traverse to filename property */
  def filename: Iterator[String] =
    traversal.map(_.filename)

  /** Traverse to nodes where the filename matches the regular expression `value`
    */
  def filename(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      filenameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.filename, pattern)
    }
  }

  /** Traverse to nodes where the filename matches at least one of the regular expressions in `values`
    */
  def filename(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.filename, patterns)

  /** Traverse to nodes where filename matches `value` exactly.
    */
  def filenameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("FILENAME", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.filename == value }
  }

  /** Traverse to nodes where filename matches one of the elements in `values` exactly.
    */
  def filenameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      filenameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.filename), values, "FILENAME")
  }

  /** Traverse to nodes where filename does not match the regular expression `value`.
    */
  def filenameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.filename != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.filename, pattern)
    }
  }

  /** Traverse to nodes where filename does not match any of the regular expressions in `values`.
    */
  def filenameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.filename, patterns)
  }

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

  /** Traverse to isExternal property */
  def isExternal: Iterator[Boolean] =
    traversal.map(_.isExternal)

  /** Traverse to nodes where the isExternal equals the given `value`
    */
  def isExternal(value: Boolean): Iterator[NodeType] =
    traversal.filter { _.isExternal == value }

  /** Traverse to nodes where isExternal is not equal to the given `value`.
    */
  def isExternalNot(value: Boolean): Iterator[NodeType] =
    traversal.filter { _.isExternal != value }

  /** Traverse to lineNumber property */
  def lineNumber: Iterator[Integer] =
    traversal.flatMap(_.lineNumber)

  /** Traverse to nodes where the lineNumber equals the given `value`
    */
  def lineNumber(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumber.isDefined && node.lineNumber.get == value }

  /** Traverse to nodes where the lineNumber equals at least one of the given `values`
    */
  def lineNumber(values: Integer*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => node.lineNumber.isDefined && vset.contains(node.lineNumber.get) }
  }

  /** Traverse to nodes where the lineNumber is greater than the given `value`
    */
  def lineNumberGt(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumber.isDefined && node.lineNumber.get > value }

  /** Traverse to nodes where the lineNumber is greater than or equal the given `value`
    */
  def lineNumberGte(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumber.isDefined && node.lineNumber.get >= value }

  /** Traverse to nodes where the lineNumber is less than the given `value`
    */
  def lineNumberLt(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumber.isDefined && node.lineNumber.get < value }

  /** Traverse to nodes where the lineNumber is less than or equal the given `value`
    */
  def lineNumberLte(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumber.isDefined && node.lineNumber.get <= value }

  /** Traverse to nodes where lineNumber is not equal to the given `value`.
    */
  def lineNumberNot(value: Integer): Iterator[NodeType] =
    traversal.filter { node => !node.lineNumber.isDefined || node.lineNumber.get != value }

  /** Traverse to nodes where lineNumber is not equal to any of the given `values`.
    */
  def lineNumberNot(values: Integer*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => !node.lineNumber.isDefined || !vset.contains(node.lineNumber.get) }
  }

  /** Traverse to lineNumberEnd property */
  def lineNumberEnd: Iterator[Integer] =
    traversal.flatMap(_.lineNumberEnd)

  /** Traverse to nodes where the lineNumberEnd equals the given `value`
    */
  def lineNumberEnd(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get == value }

  /** Traverse to nodes where the lineNumberEnd equals at least one of the given `values`
    */
  def lineNumberEnd(values: Integer*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => node.lineNumberEnd.isDefined && vset.contains(node.lineNumberEnd.get) }
  }

  /** Traverse to nodes where the lineNumberEnd is greater than the given `value`
    */
  def lineNumberEndGt(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get > value }

  /** Traverse to nodes where the lineNumberEnd is greater than or equal the given `value`
    */
  def lineNumberEndGte(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get >= value }

  /** Traverse to nodes where the lineNumberEnd is less than the given `value`
    */
  def lineNumberEndLt(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get < value }

  /** Traverse to nodes where the lineNumberEnd is less than or equal the given `value`
    */
  def lineNumberEndLte(value: Integer): Iterator[NodeType] =
    traversal.filter { node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get <= value }

  /** Traverse to nodes where lineNumberEnd is not equal to the given `value`.
    */
  def lineNumberEndNot(value: Integer): Iterator[NodeType] =
    traversal.filter { node => !node.lineNumberEnd.isDefined || node.lineNumberEnd.get != value }

  /** Traverse to nodes where lineNumberEnd is not equal to any of the given `values`.
    */
  def lineNumberEndNot(values: Integer*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => !node.lineNumberEnd.isDefined || !vset.contains(node.lineNumberEnd.get) }
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

  /** Traverse to order property */
  def order: Iterator[scala.Int] =
    traversal.map(_.order)

  /** Traverse to nodes where the order equals the given `value`
    */
  def order(value: scala.Int): Iterator[NodeType] =
    traversal.filter { _.order == value }

  /** Traverse to nodes where the order equals at least one of the given `values`
    */
  def order(values: scala.Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => vset.contains(node.order) }
  }

  /** Traverse to nodes where the order is greater than the given `value`
    */
  def orderGt(value: scala.Int): Iterator[NodeType] =
    traversal.filter { _.order > value }

  /** Traverse to nodes where the order is greater than or equal the given `value`
    */
  def orderGte(value: scala.Int): Iterator[NodeType] =
    traversal.filter { _.order >= value }

  /** Traverse to nodes where the order is less than the given `value`
    */
  def orderLt(value: scala.Int): Iterator[NodeType] =
    traversal.filter { _.order < value }

  /** Traverse to nodes where the order is less than or equal the given `value`
    */
  def orderLte(value: scala.Int): Iterator[NodeType] =
    traversal.filter { _.order <= value }

  /** Traverse to nodes where order is not equal to the given `value`.
    */
  def orderNot(value: scala.Int): Iterator[NodeType] =
    traversal.filter { _.order != value }

  /** Traverse to nodes where order is not equal to any of the given `values`.
    */
  def orderNot(values: scala.Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => !vset.contains(node.order) }
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
