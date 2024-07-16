package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyEvaluationStrategy[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasEvaluationStrategyEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to evaluationStrategy property */
  def evaluationStrategy: Iterator[String] =
    traversal.map(_.evaluationStrategy)

  /** Traverse to nodes where the evaluationStrategy matches the regular expression `value`
    */
  def evaluationStrategy(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      evaluationStrategyExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.evaluationStrategy).matches }
    }

  /** Traverse to nodes where the evaluationStrategy matches at least one of the regular expressions in `values`
    */
  def evaluationStrategy(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.evaluationStrategy).matches } }
  }

  /** Traverse to nodes where evaluationStrategy matches `value` exactly.
    */
  def evaluationStrategyExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 19, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.evaluationStrategy == value }
  }

  /** Traverse to nodes where evaluationStrategy matches one of the elements in `values` exactly.
    */
  def evaluationStrategyExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) evaluationStrategyExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.evaluationStrategy) }
    }

  /** Traverse to nodes where evaluationStrategy does not match the regular expression `value`.
    */
  def evaluationStrategyNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.evaluationStrategy != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.evaluationStrategy).matches }
    }
  }

  /** Traverse to nodes where evaluationStrategy does not match any of the regular expressions in `values`.
    */
  def evaluationStrategyNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.evaluationStrategy).matches }.isEmpty }
  }

}
