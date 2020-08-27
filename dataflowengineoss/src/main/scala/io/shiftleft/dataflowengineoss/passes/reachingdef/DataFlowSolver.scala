package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes

import scala.collection.mutable

class DataFlowSolver {

  /**
    * Calculate fix point solution via a standard work list algorithm.
    * The result is given by two maps: `in` and `out`. These maps associate
    * all CFG nodes with the set of definitions at node entry and node
    * exit respectively.
    * */
  def calculateMopSolution[T <: Iterable[_]](problem: DataFlowProblem[T]): Solution[T] = {
    var out: Map[nodes.StoredNode, T] = problem.inOutInit.initOut
    var in = problem.inOutInit.initIn

    val worklist = mutable.Set.empty[nodes.StoredNode]
    worklist ++= problem.flowGraph.allNodes
    while (worklist.nonEmpty) {
      if (problem.forward) {
        val newEntries = worklist.flatMap { n =>
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
        }.toList
        worklist.clear
        worklist ++= newEntries
      } else {

        val newEntries = worklist.flatMap { n =>
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
        worklist.clear
        worklist ++= newEntries
      }
    }
    Solution(in, out, problem)
  }

}
