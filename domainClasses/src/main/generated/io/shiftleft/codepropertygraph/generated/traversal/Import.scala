package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for Import */
class ImportTraversalExtGen[NodeType <: Import](val traversal: Iterator[NodeType]) extends AnyVal {

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

  /** Traverse to explicitAs property */
  def explicitAs: Iterator[java.lang.Boolean] =
    traversal.flatMap(_.explicitAs)

  /** Traverse to nodes where the explicitAs equals the given `value`
    */
  def explicitAs(value: java.lang.Boolean): Iterator[NodeType] =
    traversal.filter { node => node.explicitAs.isDefined && node.explicitAs.get == value }

  /** Traverse to nodes where explicitAs is not equal to the given `value`.
    */
  def explicitAsNot(value: java.lang.Boolean): Iterator[NodeType] =
    traversal.filter { node => !node.explicitAs.isDefined || node.explicitAs.get == value }

  /** Traverse to importedAs property */
  def importedAs: Iterator[String] =
    traversal.flatMap(_.importedAs)

  /** Traverse to nodes where the importedAs matches the regular expression `value`
    */
  def importedAs(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.importedAs.isDefined && node.importedAs.get == pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexp(traversal.filter(_.importedAs.isDefined))(_.importedAs.get, pattern)
    }
  }

  /** Traverse to nodes where the importedAs matches at least one of the regular expressions in `values`
    */
  def importedAs(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpMultiple(traversal.filter(_.importedAs.isDefined))(_.importedAs.get, patterns)
  }

  /** Traverse to nodes where importedAs matches `value` exactly.
    */
  def importedAsExact(value: String): Iterator[NodeType] =
    traversal.filter { node => node.importedAs.contains(value) }

  /** Traverse to nodes where importedAs matches one of the elements in `values` exactly.
    */
  def importedAsExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      importedAsExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, _.importedAs, values, "IMPORTED_AS")
  }

  /** Traverse to nodes where importedAs does not match the regular expression `value`.
    */
  def importedAsNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.importedAs.isEmpty || node.importedAs.get != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexpNot(traversal.filter(_.importedAs.isDefined))(_.importedAs.get, pattern)
    }
  }

  /** Traverse to nodes where importedAs does not match any of the regular expressions in `values`.
    */
  def importedAsNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpNotMultiple(traversal.filter(_.importedAs.isDefined))(_.importedAs.get, patterns)
  }

  /** Traverse to importedEntity property */
  def importedEntity: Iterator[String] =
    traversal.flatMap(_.importedEntity)

  /** Traverse to nodes where the importedEntity matches the regular expression `value`
    */
  def importedEntity(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.importedEntity.isDefined && node.importedEntity.get == pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexp(traversal.filter(_.importedEntity.isDefined))(_.importedEntity.get, pattern)
    }
  }

  /** Traverse to nodes where the importedEntity matches at least one of the regular expressions in `values`
    */
  def importedEntity(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpMultiple(traversal.filter(_.importedEntity.isDefined))(_.importedEntity.get, patterns)
  }

  /** Traverse to nodes where importedEntity matches `value` exactly.
    */
  def importedEntityExact(value: String): Iterator[NodeType] =
    traversal.filter { node => node.importedEntity.contains(value) }

  /** Traverse to nodes where importedEntity matches one of the elements in `values` exactly.
    */
  def importedEntityExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      importedEntityExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, _.importedEntity, values, "IMPORTED_ENTITY")
  }

  /** Traverse to nodes where importedEntity does not match the regular expression `value`.
    */
  def importedEntityNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.importedEntity.isEmpty || node.importedEntity.get != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexpNot(traversal.filter(_.importedEntity.isDefined))(_.importedEntity.get, pattern)
    }
  }

  /** Traverse to nodes where importedEntity does not match any of the regular expressions in `values`.
    */
  def importedEntityNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpNotMultiple(traversal.filter(_.importedEntity.isDefined))(_.importedEntity.get, patterns)
  }

  /** Traverse to isExplicit property */
  def isExplicit: Iterator[java.lang.Boolean] =
    traversal.flatMap(_.isExplicit)

  /** Traverse to nodes where the isExplicit equals the given `value`
    */
  def isExplicit(value: java.lang.Boolean): Iterator[NodeType] =
    traversal.filter { node => node.isExplicit.isDefined && node.isExplicit.get == value }

  /** Traverse to nodes where isExplicit is not equal to the given `value`.
    */
  def isExplicitNot(value: java.lang.Boolean): Iterator[NodeType] =
    traversal.filter { node => !node.isExplicit.isDefined || node.isExplicit.get == value }

  /** Traverse to isWildcard property */
  def isWildcard: Iterator[java.lang.Boolean] =
    traversal.flatMap(_.isWildcard)

  /** Traverse to nodes where the isWildcard equals the given `value`
    */
  def isWildcard(value: java.lang.Boolean): Iterator[NodeType] =
    traversal.filter { node => node.isWildcard.isDefined && node.isWildcard.get == value }

  /** Traverse to nodes where isWildcard is not equal to the given `value`.
    */
  def isWildcardNot(value: java.lang.Boolean): Iterator[NodeType] =
    traversal.filter { node => !node.isWildcard.isDefined || node.isWildcard.get == value }

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

}
