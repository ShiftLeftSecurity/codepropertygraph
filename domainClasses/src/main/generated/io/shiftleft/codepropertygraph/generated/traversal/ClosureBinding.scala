package io.shiftleft.codepropertygraph.generated.traversal

import overflowdb.traversal._
import io.shiftleft.codepropertygraph.generated.nodes._

/** Traversal steps for ClosureBinding */
class ClosureBindingTraversalExtGen[NodeType <: ClosureBinding](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to closureBindingId property */
  def closureBindingId: Iterator[String] =
    traversal.flatMap(_.closureBindingId)

  /** Traverse to nodes where the closureBindingId matches the regular expression `value`
    */
  def closureBindingId(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.closureBindingId.isDefined && node.closureBindingId.get == pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexp(traversal.filter(_.closureBindingId.isDefined))(_.closureBindingId.get, pattern)
    }
  }

  /** Traverse to nodes where the closureBindingId matches at least one of the regular expressions in `values`
    */
  def closureBindingId(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpMultiple(traversal.filter(_.closureBindingId.isDefined))(_.closureBindingId.get, patterns)
  }

  /** Traverse to nodes where closureBindingId matches `value` exactly.
    */
  def closureBindingIdExact(value: String): Iterator[NodeType] =
    traversal.filter { node => node.closureBindingId.contains(value) }

  /** Traverse to nodes where closureBindingId matches one of the elements in `values` exactly.
    */
  def closureBindingIdExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      closureBindingIdExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, _.closureBindingId, values, "CLOSURE_BINDING_ID")
  }

  /** Traverse to nodes where closureBindingId does not match the regular expression `value`.
    */
  def closureBindingIdNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.closureBindingId.isEmpty || node.closureBindingId.get != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexpNot(traversal.filter(_.closureBindingId.isDefined))(_.closureBindingId.get, pattern)
    }
  }

  /** Traverse to nodes where closureBindingId does not match any of the regular expressions in `values`.
    */
  def closureBindingIdNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpNotMultiple(traversal.filter(_.closureBindingId.isDefined))(_.closureBindingId.get, patterns)
  }

  /** Traverse to closureOriginalName property */
  def closureOriginalName: Iterator[String] =
    traversal.flatMap(_.closureOriginalName)

  /** Traverse to nodes where the closureOriginalName matches the regular expression `value`
    */
  def closureOriginalName(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.closureOriginalName.isDefined && node.closureOriginalName.get == pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexp(traversal.filter(_.closureOriginalName.isDefined))(_.closureOriginalName.get, pattern)
    }
  }

  /** Traverse to nodes where the closureOriginalName matches at least one of the regular expressions in `values`
    */
  def closureOriginalName(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpMultiple(traversal.filter(_.closureOriginalName.isDefined))(_.closureOriginalName.get, patterns)
  }

  /** Traverse to nodes where closureOriginalName matches `value` exactly.
    */
  def closureOriginalNameExact(value: String): Iterator[NodeType] =
    traversal.filter { node => node.closureOriginalName.contains(value) }

  /** Traverse to nodes where closureOriginalName matches one of the elements in `values` exactly.
    */
  def closureOriginalNameExact(values: String*): Iterator[NodeType] = {
    if (values.size == 1)
      closureOriginalNameExact(values.head)
    else
      overflowdb.traversal.filter.StringPropertyFilter
        .exactMultiple[NodeType, String](traversal, _.closureOriginalName, values, "CLOSURE_ORIGINAL_NAME")
  }

  /** Traverse to nodes where closureOriginalName does not match the regular expression `value`.
    */
  def closureOriginalNameNot(pattern: String): Iterator[NodeType] = {
    if (!Misc.isRegex(pattern)) {
      traversal.filter { node => node.closureOriginalName.isEmpty || node.closureOriginalName.get != pattern }
    } else {
      overflowdb.traversal.filter.StringPropertyFilter
        .regexpNot(traversal.filter(_.closureOriginalName.isDefined))(_.closureOriginalName.get, pattern)
    }
  }

  /** Traverse to nodes where closureOriginalName does not match any of the regular expressions in `values`.
    */
  def closureOriginalNameNot(patterns: String*): Iterator[NodeType] = {
    overflowdb.traversal.filter.StringPropertyFilter
      .regexpNotMultiple(traversal.filter(_.closureOriginalName.isDefined))(_.closureOriginalName.get, patterns)
  }

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

}
