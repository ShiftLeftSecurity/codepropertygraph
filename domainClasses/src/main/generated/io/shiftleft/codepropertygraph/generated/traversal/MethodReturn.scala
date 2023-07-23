package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for MethodReturn */
class MethodReturnTraversalExtGen[NodeType <: MethodReturn](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to RETURN via CFG IN edge.
    */
  def toReturn: Iterator[Return] =
    traversal.flatMap(_.toReturn)

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

  /** Traverse to dynamicTypeHintFullName property */
  def dynamicTypeHintFullName: Iterator[String] =
    traversal.flatMap(_.dynamicTypeHintFullName)

  /** Traverse to evaluationStrategy property */
  def evaluationStrategy: Iterator[String] =
    traversal.map(_.evaluationStrategy)

  /** Traverse to nodes where the evaluationStrategy matches the regular expression `value`
    */
  def evaluationStrategy(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      evaluationStrategyExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.evaluationStrategy, pattern)
    }
  }

  /** Traverse to nodes where the evaluationStrategy matches at least one of the regular expressions in `values`
    */
  def evaluationStrategy(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.evaluationStrategy, patterns)

  /** Traverse to nodes where evaluationStrategy matches `value` exactly.
    */
  def evaluationStrategyExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] =>
        init.getByIndex("EVALUATION_STRATEGY", value).getOrElse(null)
      case _ => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.evaluationStrategy == value }
  }

  /** Traverse to nodes where evaluationStrategy matches one of the elements in `values` exactly.
    */
  def evaluationStrategyExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      evaluationStrategyExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter.exactMultiple[NodeType, String](
        traversal,
        node => Some(node.evaluationStrategy),
        values,
        "EVALUATION_STRATEGY"
      )
  }

  /** Traverse to nodes where evaluationStrategy does not match the regular expression `value`.
    */
  def evaluationStrategyNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.evaluationStrategy != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.evaluationStrategy, pattern)
    }
  }

  /** Traverse to nodes where evaluationStrategy does not match any of the regular expressions in `values`.
    */
  def evaluationStrategyNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.evaluationStrategy, patterns)
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

  /** Traverse to possibleTypes property */
  def possibleTypes: Iterator[String] =
    traversal.flatMap(_.possibleTypes)

  /** Traverse to typeFullName property */
  def typeFullName: Iterator[String] =
    traversal.map(_.typeFullName)

  /** Traverse to nodes where the typeFullName matches the regular expression `value`
    */
  def typeFullName(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      typeFullNameExact(pattern)
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexp(traversal)(_.typeFullName, pattern)
    }
  }

  /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
    */
  def typeFullName(patterns: String*): Iterator[NodeType] =
    overflowdb.traversal.filter.StringPropertyFilter.regexpMultiple(traversal)(_.typeFullName, patterns)

  /** Traverse to nodes where typeFullName matches `value` exactly.
    */
  def typeFullNameExact(value: String): Iterator[NodeType] = {
    val fastResult = traversal match {
      case init: overflowdb.traversal.InitialTraversal[NodeType] =>
        init.getByIndex("TYPE_FULL_NAME", value).getOrElse(null)
      case _ => null
    }
    if (fastResult != null) fastResult
    else traversal.filter { node => node.typeFullName == value }
  }

  /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
    */
  def typeFullNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      typeFullNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, node => Some(node.typeFullName), values, "TYPE_FULL_NAME")
  }

  /** Traverse to nodes where typeFullName does not match the regular expression `value`.
    */
  def typeFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.typeFullName != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter.regexpNot(traversal)(_.typeFullName, pattern)
    }
  }

  /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
    */
  def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter.regexpNotMultiple(traversal)(_.typeFullName, patterns)
  }

}
