package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{nodes, _}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._

import scala.collection.mutable

/**
  * A pass that calculates reaching definitions ("data dependencies").
  * */
class ReachingDefPass(cpg: Cpg) extends ParallelCpgPass[nodes.Method](cpg) {

  override def partIterator: Iterator[nodes.Method] = cpg.method.iterator

  override def runOnPart(method: nodes.Method): Iterator[DiffGraph] = {
    val problem = ReachingDefProblem.create(method)
    val solution = new DataFlowSolver().calculateMopSolutionForwards(problem)
    val dstGraph = addReachingDefEdges(method, solution)
    Iterator(dstGraph.build())
  }

  /**
    * Once reaching definitions have been computed, we create a data dependence graph
    * by seeing which of these reaching definitions are relevant in the sense that
    * they are used.
    * */
  private def addReachingDefEdges(method: nodes.Method, solution: Solution[Set[Definition]]): DiffGraph.Builder = {

    val dstGraph = DiffGraph.newBuilder
    val nodesWithOutgoingEdges: mutable.Set[nodes.StoredNode] = mutable.Set()

    def addEdge(fromNode: nodes.StoredNode, toNode: nodes.StoredNode, variable: String = ""): Unit = {
      val properties = List((EdgeKeyNames.VARIABLE, variable))
      if (fromNode.isInstanceOf[nodes.Unknown] || toNode
            .isInstanceOf[nodes.Unknown])
        return
      nodesWithOutgoingEdges += fromNode
      dstGraph.addEdgeInOriginal(fromNode, toNode, EdgeTypes.REACHING_DEF, properties)
    }

    val in = solution.in
    val gen = solution.problem.transferFunction.asInstanceOf[ReachingDefTransferFunction].gen
    val allNodes = in.keys.toList
    val usageAnalyzer = new UsageAnalyzer(in)

    allNodes.foreach { node: nodes.StoredNode =>
      node match {
        case call: nodes.Call =>
          // Edges between arguments of call sites
          usageAnalyzer.usedIncomingDefs(call).foreach {
            case (use, ins) =>
              ins.foreach { in =>
                if (in.node != use) {
                  addEdge(in.node, use, nodeToEdgeLabel(in.node))
                }
              }
          }

          // For all calls, assume that input arguments
          // taint corresponding output arguments
          // and the return value
          usageAnalyzer.uses(call).foreach { use =>
            gen(call).foreach { g =>
              if (use != g.node) {
                addEdge(use, g.node, nodeToEdgeLabel(use))
              }
            }
          }

        case ret: nodes.Return =>
          usageAnalyzer.usedIncomingDefs(ret).foreach {
            case (use, inElements) =>
              addEdge(use, ret, use.asInstanceOf[nodes.CfgNode].code)
              inElements.foreach { inElement =>
                addEdge(inElement.node, ret, nodeToEdgeLabel(inElement.node))
              }
              if (inElements.isEmpty) {
                addEdge(method, ret)
              }
          }
          addEdge(ret, method.methodReturn, "<RET>")

        case _ =>
      }
    }

    // Add edges from the entry node
    allNodes
      .filterNot(
        x =>
          x.isInstanceOf[nodes.Method] || x
            .isInstanceOf[nodes.ControlStructure] || x.isInstanceOf[nodes.FieldIdentifier] || x
            .isInstanceOf[nodes.JumpTarget] || x.isInstanceOf[nodes.MethodReturn] || x.isInstanceOf[nodes.Block])
      .foreach { node =>
        if (usageAnalyzer.usedIncomingDefs(node).isEmpty) {
          addEdge(method, node)
        }
      }

    // Add edges to exit node
    val exitNode = method.methodReturn
    (allNodes.toSet -- nodesWithOutgoingEdges).toList
      .filter(_ != exitNode)
      .filter(_ != method)
      .filterNot(
        x =>
          x.isInstanceOf[nodes.Method] || x
            .isInstanceOf[nodes.ControlStructure] || x.isInstanceOf[nodes.FieldIdentifier] || x
            .isInstanceOf[nodes.JumpTarget] || x.isInstanceOf[nodes.MethodReturn])
      .foreach { from =>
        addEdge(from, exitNode)
      }
    dstGraph
  }

  private def nodeToEdgeLabel(node: nodes.StoredNode): String = {
    node match {
      case n: nodes.CfgNode           => n.code
      case n: nodes.MethodParameterIn => n.name
      case _                          => ""
    }
  }

}
