package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{nodes, _}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.jdk.CollectionConverters._

/**
  * A pass that calculates reaching definitions ("data dependencies").
  * */
class ReachingDefPass(cpg: Cpg) extends ParallelCpgPass[nodes.Method](cpg) {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  override def partIterator: Iterator[nodes.Method] = cpg.method.toIterator()

  override def runOnPart(method: nodes.Method): Iterator[DiffGraph] = {
    val problem = ReachingDefProblem.create(method)
    val solution = new DataFlowSolver().calculateMopSolution(problem)
    val dstGraph = addReachingDefEdges(method, solution)
    Iterator(dstGraph.build())
  }

  /**
    * Once reaching definitions have been computed, we create a data dependence graph
    * by seeing which of these reaching definitions are relevant in the sense that
    * they are used.
    * */
  private def addReachingDefEdges(method: nodes.Method,
                                  solution: Solution[Set[nodes.StoredNode]]): DiffGraph.Builder = {

    val dstGraph = DiffGraph.newBuilder

    def addEdge(fromNode: nodes.StoredNode, toNode: nodes.StoredNode, variable: String = ""): Unit = {
      val properties = List((EdgeKeyNames.VARIABLE, variable))
      dstGraph.addEdgeInOriginal(fromNode, toNode, EdgeTypes.REACHING_DEF, properties)
    }

    val in = solution.in
    val gen = solution.problem.transferFunction.asInstanceOf[ReachingDefTransferFunction].gen
    val allNodes = in.keys.toList
    val usageAnalyzer = new UsageAnalyzer(in, gen)

    allNodes.foreach { node: nodes.StoredNode =>
      node match {
        case call: nodes.Call =>
          // Edges between arguments of call sites
          usageAnalyzer.usedIncomingDefs(call).foreach {
            case (use, ins) =>
              ins.foreach { in =>
                if (in != use) {
                  val edgeLabel = Some(in)
                    .filter(_.isInstanceOf[nodes.CfgNode])
                    .map(_.asInstanceOf[nodes.CfgNode].code)
                    .getOrElse("")
                  if (!use.isInstanceOf[nodes.FieldIdentifier])
                    addEdge(in, use, edgeLabel)
                }
              }
          }

          if (!hasAnnotation(call)) {
            usageAnalyzer.uses(call, gen).foreach { use =>
              gen(call).foreach { g =>
                if (g == use || g == call) {
                  addEdge(use, g)
                }
              }
            }
          } else {
            // Copy propagate edges from formal method to call site
            NoResolve
              .getCalledMethods(call)
              .flatMap { method =>
                method.parameter.map { param =>
                  (param.order, param._propagateOut().asScala.toList.map(_.asInstanceOf[nodes.HasOrder].order))
                }.l
              }
              .foreach {
                case (srcOrder, dstOrders) =>
                  val srcNode = call.argument(srcOrder)
                  val dstNodes = dstOrders.map {
                    case dstOrder if dstOrder == -1 => call
                    case dstOrder                   => call.argument(dstOrder)
                  }
                  dstNodes.foreach { dstNode =>
                    addEdge(srcNode, dstNode, srcNode.code)
                  }
              }
          }

        case ret: nodes.Return =>
          usageAnalyzer.usedIncomingDefs(ret).foreach {
            case (use, inElements) =>
              addEdge(use, ret, use.asInstanceOf[nodes.CfgNode].code)
              inElements.foreach { inElement =>
                addEdge(inElement, ret)
              }
              if (inElements.isEmpty) {
                addEdge(method, ret)
              }
          }

        case methodReturn: nodes.MethodReturn =>
          methodReturn.start.cfgPrev.isReturn.foreach { ret =>
            addEdge(ret, methodReturn, "<RET>")
          }

        case _ =>
      }
    }

    // Add edges from the entry node
    allNodes
      .filterNot(
        x =>
          x.isInstanceOf[nodes.MethodReturn] || x.isInstanceOf[nodes.Method] || x.isInstanceOf[nodes.Literal] || x
            .isInstanceOf[nodes.ControlStructure] || x.isInstanceOf[nodes.FieldIdentifier])
      .foreach { node =>
        if (in(node).size == in(node).count(_.isInstanceOf[nodes.MethodParameterIn])) {
          addEdge(method, node)
        }
      }

    dstGraph
  }

  private def hasAnnotation(call: nodes.Call): Boolean = {
    methodForCall(call).exists(method => method.parameter.l.exists(x => x._propagateOut().hasNext))
  }

  private def methodForCall(call: nodes.Call): Option[nodes.Method] = {
    NoResolve.getCalledMethods(call).toList match {
      case List(x) => Some(x)
      case List()  => None
      case list =>
        logger.warn(s"Multiple methods with name: ${call.name}, using first one")
        Some(list.head)
    }
  }

}
