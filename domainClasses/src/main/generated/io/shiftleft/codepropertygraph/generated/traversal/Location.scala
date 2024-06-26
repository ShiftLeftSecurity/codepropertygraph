package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for Location */
class LocationTraversalExtGen[NodeType <: Location](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to className property */
  def className: Iterator[String] =
    traversal.map(_.className)

  /** Traverse to nodes where the className matches the regular expression `value`
    */
  def className(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      classNameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.className, pattern)
    }
  }

  /** Traverse to nodes where the className matches at least one of the regular expressions in `values`
    */
  def className(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.className, patterns)

  /** Traverse to nodes where className matches `value` exactly.
    */
  def classNameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("CLASS_NAME", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.className == value }
  }

  /** Traverse to nodes where className matches one of the elements in `values` exactly.
    */
  def classNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      classNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.className), values, "CLASS_NAME")
  }

  /** Traverse to nodes where className does not match the regular expression `value`.
    */
  def classNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.className != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.className, pattern)
    }
  }

  /** Traverse to nodes where className does not match any of the regular expressions in `values`.
    */
  def classNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.className, patterns)
  }

  /** Traverse to classShortName property */
  def classShortName: Iterator[String] =
    traversal.map(_.classShortName)

  /** Traverse to nodes where the classShortName matches the regular expression `value`
    */
  def classShortName(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      classShortNameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.classShortName, pattern)
    }
  }

  /** Traverse to nodes where the classShortName matches at least one of the regular expressions in `values`
    */
  def classShortName(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.classShortName, patterns)

  /** Traverse to nodes where classShortName matches `value` exactly.
    */
  def classShortNameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] =>
        init.getByIndex("CLASS_SHORT_NAME", value).getOrElse(null)
      case _ => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.classShortName == value }
  }

  /** Traverse to nodes where classShortName matches one of the elements in `values` exactly.
    */
  def classShortNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      classShortNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.classShortName), values, "CLASS_SHORT_NAME")
  }

  /** Traverse to nodes where classShortName does not match the regular expression `value`.
    */
  def classShortNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.classShortName != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.classShortName, pattern)
    }
  }

  /** Traverse to nodes where classShortName does not match any of the regular expressions in `values`.
    */
  def classShortNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.classShortName, patterns)
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

  /** Traverse to methodShortName property */
  def methodShortName: Iterator[String] =
    traversal.map(_.methodShortName)

  /** Traverse to nodes where the methodShortName matches the regular expression `value`
    */
  def methodShortName(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      methodShortNameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.methodShortName, pattern)
    }
  }

  /** Traverse to nodes where the methodShortName matches at least one of the regular expressions in `values`
    */
  def methodShortName(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.methodShortName, patterns)

  /** Traverse to nodes where methodShortName matches `value` exactly.
    */
  def methodShortNameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] =>
        init.getByIndex("METHOD_SHORT_NAME", value).getOrElse(null)
      case _ => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.methodShortName == value }
  }

  /** Traverse to nodes where methodShortName matches one of the elements in `values` exactly.
    */
  def methodShortNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      methodShortNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.methodShortName), values, "METHOD_SHORT_NAME")
  }

  /** Traverse to nodes where methodShortName does not match the regular expression `value`.
    */
  def methodShortNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.methodShortName != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.methodShortName, pattern)
    }
  }

  /** Traverse to nodes where methodShortName does not match any of the regular expressions in `values`.
    */
  def methodShortNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.methodShortName, patterns)
  }

  /** Traverse to nodeLabel property */
  def nodeLabel: Iterator[String] =
    traversal.map(_.nodeLabel)

  /** Traverse to nodes where the nodeLabel matches the regular expression `value`
    */
  def nodeLabel(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      nodeLabelExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.nodeLabel, pattern)
    }
  }

  /** Traverse to nodes where the nodeLabel matches at least one of the regular expressions in `values`
    */
  def nodeLabel(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.nodeLabel, patterns)

  /** Traverse to nodes where nodeLabel matches `value` exactly.
    */
  def nodeLabelExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("NODE_LABEL", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.nodeLabel == value }
  }

  /** Traverse to nodes where nodeLabel matches one of the elements in `values` exactly.
    */
  def nodeLabelExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      nodeLabelExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.nodeLabel), values, "NODE_LABEL")
  }

  /** Traverse to nodes where nodeLabel does not match the regular expression `value`.
    */
  def nodeLabelNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.nodeLabel != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.nodeLabel, pattern)
    }
  }

  /** Traverse to nodes where nodeLabel does not match any of the regular expressions in `values`.
    */
  def nodeLabelNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.nodeLabel, patterns)
  }

  /** Traverse to packageName property */
  def packageName: Iterator[String] =
    traversal.map(_.packageName)

  /** Traverse to nodes where the packageName matches the regular expression `value`
    */
  def packageName(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      packageNameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.packageName, pattern)
    }
  }

  /** Traverse to nodes where the packageName matches at least one of the regular expressions in `values`
    */
  def packageName(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.packageName, patterns)

  /** Traverse to nodes where packageName matches `value` exactly.
    */
  def packageNameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] =>
        init.getByIndex("PACKAGE_NAME", value).getOrElse(null)
      case _ => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.packageName == value }
  }

  /** Traverse to nodes where packageName matches one of the elements in `values` exactly.
    */
  def packageNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      packageNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.packageName), values, "PACKAGE_NAME")
  }

  /** Traverse to nodes where packageName does not match the regular expression `value`.
    */
  def packageNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.packageName != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.packageName, pattern)
    }
  }

  /** Traverse to nodes where packageName does not match any of the regular expressions in `values`.
    */
  def packageNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.packageName, patterns)
  }

  /** Traverse to symbol property */
  def symbol: Iterator[String] =
    traversal.map(_.symbol)

  /** Traverse to nodes where the symbol matches the regular expression `value`
    */
  def symbol(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      symbolExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.symbol, pattern)
    }
  }

  /** Traverse to nodes where the symbol matches at least one of the regular expressions in `values`
    */
  def symbol(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.symbol, patterns)

  /** Traverse to nodes where symbol matches `value` exactly.
    */
  def symbolExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] => init.getByIndex("SYMBOL", value).getOrElse(null)
      case _                                                     => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.symbol == value }
  }

  /** Traverse to nodes where symbol matches one of the elements in `values` exactly.
    */
  def symbolExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      symbolExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.symbol), values, "SYMBOL")
  }

  /** Traverse to nodes where symbol does not match the regular expression `value`.
    */
  def symbolNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.symbol != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.symbol, pattern)
    }
  }

  /** Traverse to nodes where symbol does not match any of the regular expressions in `values`.
    */
  def symbolNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.symbol, patterns)
  }

}
