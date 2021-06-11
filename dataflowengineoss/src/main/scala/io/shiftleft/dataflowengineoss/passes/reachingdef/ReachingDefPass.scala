package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, PropertyNames}
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._

/**
  * A pass that calculates reaching definitions ("data dependencies").
  * */
class ReachingDefPass(cpg: Cpg) extends ParallelCpgPass[Method](cpg) {

  override def partIterator: Iterator[Method] = cpg.method.iterator

  override def runOnPart(method: Method): Iterator[DiffGraph] = {
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
  private def addReachingDefEdges(method: Method, solution: Solution[Set[Definition]]): DiffGraph.Builder = {

    val dstGraph = DiffGraph.newBuilder

    def addEdge(fromNode: StoredNode, toNode: StoredNode, variable: String = ""): Unit = {
      val properties = List((PropertyNames.VARIABLE, variable))
      if (fromNode.isInstanceOf[Unknown] || toNode
            .isInstanceOf[Unknown])
        return
      dstGraph.addEdgeInOriginal(fromNode, toNode, EdgeTypes.REACHING_DEF, properties)
    }

    val in = solution.in
    val gen = solution.problem.transferFunction.asInstanceOf[ReachingDefTransferFunction].gen
    val allNodes = in.keys.toList
    val usageAnalyzer = new UsageAnalyzer(in)

    allNodes.foreach { node: StoredNode =>
      node match {
        case call: Call =>
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
          usageAnalyzer.uses(node).foreach { use =>
            gen(node).foreach { g =>
              if (use != g.node && nodeMayBeSource(use)) {
                addEdge(use, g.node, nodeToEdgeLabel(use))
              }
            }
          }

        case ret: Return =>
          usageAnalyzer.usedIncomingDefs(ret).foreach {
            case (use, inElements) =>
              addEdge(use, ret, use.asInstanceOf[CfgNode].code)
              inElements.filter(x => x.node != use).foreach { inElement =>
                addEdge(inElement.node, ret, nodeToEdgeLabel(inElement.node))
              }
              if (inElements.isEmpty) {
                addEdge(method, ret)
              }
          }
          addEdge(ret, method.methodReturn, "<RET>")

        case exitNode: MethodReturn =>
          in(exitNode).foreach { i =>
            addEdge(i.node, exitNode, nodeToEdgeLabel(i.node))
          }
        case _ =>
      }
    }

    // Add edges from the entry node
    allNodes
      .filter(nodeMayBeSource)
      .foreach { node =>
        if (usageAnalyzer.usedIncomingDefs(node).isEmpty) {
          addEdge(method, node)
        }
      }
    dstGraph
  }

  private def nodeMayBeSource(x: StoredNode): Boolean = {
    !(
      x.isInstanceOf[Method] || x
        .isInstanceOf[ControlStructure] || x.isInstanceOf[FieldIdentifier] || x
        .isInstanceOf[JumpTarget] || x.isInstanceOf[MethodReturn] || x.isInstanceOf[Block]
    )
  }

  private def nodeToEdgeLabel(node: StoredNode): String = {
    node match {
      case n: MethodParameterIn => n.name
      case n: CfgNode           => n.code
      case _                          => ""
    }
  }

}
