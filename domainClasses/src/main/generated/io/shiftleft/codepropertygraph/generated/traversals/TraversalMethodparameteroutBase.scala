package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalMethodparameteroutBase[NodeType <: nodes.MethodParameterOutBase](val traversal: Iterator[NodeType])
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

  /** Traverse to index property */
  def index: Iterator[Int] =
    traversal.map(_.index)

  /** Traverse to nodes where the index equals the given `value`
    */
  def index(value: Int): Iterator[NodeType] =
    traversal.filter { _.index == value }

  /** Traverse to nodes where the index equals at least one of the given `values`
    */
  def index(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => vset.contains(node.index) }
  }

  /** Traverse to nodes where the index is not equal to the given `value`
    */
  def indexNot(value: Int): Iterator[NodeType] =
    traversal.filter { _.index != value }

  /** Traverse to nodes where the index is not equal to any of the given `values`
    */
  def indexNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node => !vset.contains(node.index) }
  }

  /** Traverse to nodes where the index is greater than the given `value`
    */
  def indexGt(value: Int): Iterator[NodeType] =
    traversal.filter { _.index > value }

  /** Traverse to nodes where the index is greater than or equal the given `value`
    */
  def indexGte(value: Int): Iterator[NodeType] =
    traversal.filter { _.index >= value }

  /** Traverse to nodes where the index is less than the given `value`
    */
  def indexLt(value: Int): Iterator[NodeType] =
    traversal.filter { _.index < value }

  /** Traverse to nodes where the index is less than or equal the given `value`
    */
  def indexLte(value: Int): Iterator[NodeType] =
    traversal.filter { _.index <= value }

  /** Traverse to isVariadic property */
  def isVariadic: Iterator[Boolean] =
    traversal.map(_.isVariadic)

  /** Traverse to nodes where the isVariadic equals the given `value`
    */
  def isVariadic(value: Boolean): Iterator[NodeType] =
    traversal.filter { _.isVariadic == value }

  /** Traverse to typeFullName property */
  def typeFullName: Iterator[String] =
    traversal.map(_.typeFullName)

  /** Traverse to nodes where the typeFullName matches the regular expression `value`
    */
  def typeFullName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      typeFullNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.typeFullName).matches }
    }

  /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
    */
  def typeFullName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
  }

  /** Traverse to nodes where typeFullName matches `value` exactly.
    */
  def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.typeFullName == value }
  }

  /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
    */
  def typeFullNameExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) typeFullNameExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.typeFullName) }
    }

  /** Traverse to nodes where typeFullName does not match the regular expression `value`.
    */
  def typeFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.typeFullName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
    }
  }

  /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
    */
  def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
  }

}
