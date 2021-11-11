package io.shiftleft.semanticcpg.langv2

import overflowdb.traversal.RepeatBehaviour
import overflowdb.traversal.RepeatBehaviour.SearchAlgorithm

class RepeatBehaviourBuilder[T] {
  private[this] var _shouldEmit: (T, Int) => Boolean = (_, _) => false
  private[this] var _untilCondition: Option[T => Iterator[_]] = None
  private[this] var _whileCondition: Option[T => Iterator[_]] = None
  private[this] var _times: Option[Int] = None
  private[this] var _dedupEnabled: Boolean = false
  private[this] var _searchAlgorithm: SearchAlgorithm.Value = SearchAlgorithm.DepthFirst

  /* configure search algorithm to go "breadth first", rather than the default "depth first" */
  def breadthFirstSearch: RepeatBehaviourBuilder[T] = {
    _searchAlgorithm = SearchAlgorithm.BreadthFirst
    this
  }

  def bfs: RepeatBehaviourBuilder[T] = breadthFirstSearch

  /* configure `repeat` step to emit everything along the way */
  def emit: RepeatBehaviourBuilder[T] = {
    _shouldEmit = (_, _) => true
    this
  }

  /* configure `repeat` step to emit everything along the way, apart from the _first_ element */
  def emitAllButFirst: RepeatBehaviourBuilder[T] = {
    _shouldEmit = (_, depth) => depth > 0
    this
  }

  /* configure `repeat` step to emit whatever meets the given condition */
  def emit(condition: T => Iterator[_]): RepeatBehaviourBuilder[T] = {
    _shouldEmit = (element, _) => condition(element).hasNext
    this
  }

  /* Configure `repeat` step to stop traversing when given condition-traversal has at least one result.
   * The condition-traversal is only evaluated _after_ the first iteration, for classic repeat/until behaviour */
  def until(condition: T => Iterator[_]): RepeatBehaviourBuilder[T] = {
    _untilCondition = Some(condition)
    this
  }

  /* Configure `repeat` step to stop traversing when given condition-traversal has at least one result.
   * The condition-traversal is already evaluated at the first iteration, for classic while/repeat behaviour.
   *
   * n.b. the only reason not to call this `while` is to avoid using scala keywords, which would need to be quoted. */
  def whilst(condition: T => Iterator[_]): RepeatBehaviourBuilder[T] = {
    _whileCondition = Some(condition)
    this
  }

  /* configure `repeat` step to perform the given amount of iterations */
  def times(value: Int): RepeatBehaviourBuilder[T] = {
    _times = Some(value)
    this
  }

  def dedup: RepeatBehaviourBuilder[T] = {
    _dedupEnabled = true
    this
  }

  def build: RepeatBehaviour[T] = {
    new RepeatBehaviour[T] {
      override val searchAlgorithm: SearchAlgorithm.Value = _searchAlgorithm
      override val untilCondition = _untilCondition
      override val whileCondition = _whileCondition
      final override val times: Option[Int] = _times
      final override val dedupEnabled = _dedupEnabled
      override def shouldEmit(element: T, currentDepth: Int): Boolean = _shouldEmit(element, currentDepth)
    }
  }
}
