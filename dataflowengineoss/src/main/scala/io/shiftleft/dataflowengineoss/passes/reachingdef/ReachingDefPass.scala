package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{nodes, _}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * A pass that calculates reaching definitions ("data dependencies").
  * */
class ReachingDefPass(cpg: Cpg) extends ParallelCpgPass[nodes.Method](cpg) {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  private case class Solution(in: Map[nodes.StoredNode, Set[nodes.StoredNode]],
                              out: Map[nodes.StoredNode, Set[nodes.StoredNode]],
                              // gen is not really part of the solution but
                              // we also do not want to compute it again
                              gen: Map[nodes.StoredNode, Set[nodes.StoredNode]])

  override def partIterator: Iterator[nodes.Method] = cpg.method.toIterator()

  override def runOnPart(method: nodes.Method): Iterator[DiffGraph] = {
    val solution = calculateMopSolution(method)
    val dstGraph = addReachingDefEdges(method, solution)
    Iterator(dstGraph.build())
  }

  /**
    * Calculate fix point solution via a standard work list algorithm.
    * The result is given by two maps: `in` and `out`. These maps associate
    * all CFG nodes with the set of definitions at node entry and node
    * exit respectively.
    * */
  private def calculateMopSolution(method: nodes.Method): Solution = {

    val flowGraph = new ReachingDefFlowGraph(method)
    val transfer = new ReachingDefTransferFunction(method)
    val init = new ReachingDefInit(transfer.gen)
    val meet: (Set[StoredNode], Set[StoredNode]) => Set[StoredNode] = {
      case (x: Set[StoredNode], y: Set[StoredNode]) => x.union(y)
    }
    var out: Map[nodes.StoredNode, Set[nodes.StoredNode]] = init.initOut
    var in = init.initIn

    val worklist = mutable.Set.empty[nodes.StoredNode]
    worklist ++= flowGraph.allNodes
    while (worklist.nonEmpty) {
      val n = worklist.head
      worklist -= n

      // IN[n] = Union(OUT[i]) for all predecessors i

      val inSet = flowGraph
        .pred(n)
        .map(x => out(x))
        .reduceOption((x, y) => meet(x, y))
        .getOrElse(Set.empty[nodes.StoredNode])
      in += n -> inSet

      val oldSize = out(n).size

      out += n -> transfer(n, inSet)

      if (oldSize != out(n).size)
        worklist ++= flowGraph.succ(n)
    }
    Solution(in, out, transfer.gen)
  }

  def uses(node: nodes.StoredNode, gen: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Set[nodes.StoredNode] = {
    node match {
      case ret: nodes.Return =>
        ret.astChildren.map(_.asInstanceOf[nodes.StoredNode]).toSet()
      case call: nodes.Call =>
        val parameters = NoResolve.getCalledMethods(call).headOption.map(_.parameter.l).getOrElse(List())
        node
          ._argumentOut()
          .asScala
          .filter(
            arg =>
              parameters
                .filter(_.order == arg.asInstanceOf[HasOrder].order)
                .flatMap(_._propagateOut().asScala.toList)
                .nonEmpty || !gen(node).contains(arg))
          .toSet
      case _ => Set()
    }

  }

  def hasAnnotation(call: nodes.Call): Boolean = {
    methodForCall(call).exists(method => method.parameter.l.exists(x => x._propagateOut().hasNext))
  }

  private def addReachingDefEdges(method: nodes.Method, solution: Solution): DiffGraph.Builder = {

    val dstGraph = DiffGraph.newBuilder

    def addEdge(fromNode: nodes.StoredNode, toNode: nodes.StoredNode, variable: String = ""): Unit = {
      val properties = List((EdgeKeyNames.VARIABLE, variable))
      dstGraph.addEdgeInOriginal(fromNode, toNode, EdgeTypes.REACHING_DEF, properties)
    }

    val in = solution.in
    val gen = solution.gen
    val allNodes = in.keys.toList

    def useToIn(node: nodes.StoredNode) =
      uses(node, gen).map { use =>
        use -> in(node).filter { inElement =>
          declaration(use) == declaration(inElement)
        }
      }.toMap

    allNodes.foreach { node: nodes.StoredNode =>
      node match {
        case call: nodes.Call =>
          // Edges between arguments of call sites
          useToIn(call).foreach {
            case (use, ins) =>
              ins.foreach { in =>
                if (in != use) {
                  addEdge(in,
                          use,
                          Some(in)
                            .filter(_.isInstanceOf[nodes.CfgNode])
                            .map(_.asInstanceOf[nodes.CfgNode].code)
                            .getOrElse(""))
                }
              }
          }

          if (!hasAnnotation(call)) {
            uses(call, gen).foreach { use =>
              gen(call).foreach { g =>
                addEdge(use, g)
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
          useToIn(ret).foreach {
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

  private def declaration(node: nodes.StoredNode): Option[nodes.StoredNode] = {
    node match {
      case param: nodes.MethodParameterIn => Some(param)
      case _: nodes.Identifier            => node._refOut().nextOption
      case call: nodes.Call               =>
        // We map to the first call that has the exact same code. We use
        // this as a declaration
        call.method.start.call.codeExact(call.code).headOption
      case _ => None
    }
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
