package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes

import scala.collection.mutable

class DataFlowSolver {

  /**
    * Calculate fix point solution via a standard work list algorithm (Forwards).
    * The result is given by two maps: `in` and `out`. These maps associate
    * all CFG nodes with the set of definitions at node entry and node
    * exit respectively.
    * */
  def calculateMopSolutionForwards[T <: Iterable[_]](problem: DataFlowProblem[T]): Solution[T] = {
    var out: Map[nodes.StoredNode, T] = problem.inOutInit.initOut
    var in = problem.inOutInit.initIn
    val workList = mutable.Set.empty[nodes.StoredNode]
    workList ++= problem.flowGraph.allNodes

    while (workList.nonEmpty) {
      val newEntries = workList.flatMap { n =>
        val inSet = problem.flowGraph
          .pred(n)
          .map(x => out(x))
          .reduceOption((x, y) => problem.meet(x, y))
          .getOrElse(problem.empty)
        in += n -> inSet
        val oldSize = out(n).size
        out += n -> problem.transferFunction(n, inSet)
        if (oldSize != out(n).size)
          problem.flowGraph.succ(n)
        else
          List()
      }
      workList.clear()
      workList ++= newEntries
    }
    Solution(in, out, problem)
  }

  /**
    * Calculate fix point solution via a standard work list algorithm (Backwards).
    * The result is given by two maps: `in` and `out`. These maps associate
    * all CFG nodes with the set of definitions at node entry and node
    * exit respectively.
    * */
  def calculateMopSolutionBackwards[T <: Iterable[_]](problem: DataFlowProblem[T]): Solution[T] = {
    var out: Map[nodes.StoredNode, T] = problem.inOutInit.initOut
    var in = problem.inOutInit.initIn
    val workList = mutable.Set.empty[nodes.StoredNode]
    workList ++= problem.flowGraph.allNodes

    while (workList.nonEmpty) {
      val newEntries = workList.flatMap { n =>
        val outSet = problem.flowGraph
          .succ(n)
          .map(x => in(x))
          .reduceOption((x, y) => problem.meet(x, y))
          .getOrElse(problem.empty)
        out += n -> outSet
        val oldSize = in(n).size
        in += n -> problem.transferFunction(n, outSet)
        if (oldSize != in(n).size)
          problem.flowGraph.pred(n)
        else
          List()
      }
      workList.clear()
      workList ++= newEntries
    }
    Solution(in, out, problem)
  }

}
