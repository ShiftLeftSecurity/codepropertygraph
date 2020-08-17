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
    * The result is given by two maps: `in`` and `out`. These maps associate
    * all CFG nodes with the set of definitions at node entry and node
    * exit respectively.
    * */
  private def calculateMopSolution(method: nodes.Method): Solution = {
    val entryNode = method
    val exitNode = method.methodReturn
    val allCfgNodes = method.cfgNode.toList ++ List(entryNode, exitNode) ++ method.parameter

    // Gen[n]: the definitions generated at node n
    // Kill[n]: the definitions killed at node n
    val gen = initGen(method).withDefaultValue(Set.empty[nodes.StoredNode])
    val kill = initKill(method, gen).withDefaultValue(Set.empty[nodes.StoredNode])

    val succ = initSucc(allCfgNodes)
    val pred = initPred(allCfgNodes, method)

    // Out[n] = GEN[n] for all n in `allCfgNodes`
    var out: Map[nodes.StoredNode, Set[nodes.StoredNode]] =
      allCfgNodes
        .map(cfgNode => cfgNode -> gen(cfgNode))
        .toMap

    // In[n] = empty for all n in `allCfgNodes`
    var in = Map
      .empty[nodes.StoredNode, Set[nodes.StoredNode]]
      .withDefaultValue(Set.empty[nodes.StoredNode])

    val worklist = mutable.Set.empty[nodes.StoredNode]
    worklist ++= allCfgNodes
    while (worklist.nonEmpty) {
      val n = worklist.head
      worklist -= n

      // IN[n] = Union(OUT[i]) for all predecessors i

      val inSet = pred(n)
        .map(x => out(x))
        .reduceOption((x, y) => x.union(y))
        .getOrElse(Set.empty[nodes.StoredNode])
      in += n -> inSet

      val oldSize = out(n).size

      // OUT[n] = GEN[n] + IN[n] \ KILL[N]
      out += n -> gen(n).union(inSet.diff(kill(n)))

      if (oldSize != out(n).size)
        worklist ++= succ(n)
    }
    Solution(in, out, gen)
  }

  def initGen(method: nodes.Method): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    def defsMadeByCall(call: nodes.Call): Set[nodes.StoredNode] = {
      val indicesOfDefinedOutputParams = NoResolve
        .getCalledMethods(call)
        .flatMap(method => method.parameter.asOutput)
        .filter(methPO => methPO._propagateIn().hasNext)
        .map(_.asInstanceOf[nodes.HasOrder].order.toInt)
      call
        ._argumentOut()
        .asScala
        .toList
        .filter(node =>
          indicesOfDefinedOutputParams.exists(_ == node.asInstanceOf[nodes.HasArgumentIndex].argumentIndex.toInt))
        .toSet ++ {
        if (methodForCall(call)
              .map(method => method.methodReturn)
              .exists(methodReturn => methodReturn._propagateIn().hasNext) ||
            !hasAnnotation(call)) {
          Set(call)
        } else {
          Set()
        }
      }
    }

    val defsForParams = method.start.parameter.l.map { param =>
      param -> Set(param.asInstanceOf[nodes.StoredNode])
    }

    val defsForCalls = method.start.call.l.map { call =>
      call -> defsMadeByCall(call)
    }

    (defsForParams ++ defsForCalls).toMap
  }

  def initKill(method: nodes.Method,
               gen: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {

    def allOtherInstancesOf(node: nodes.StoredNode): Set[nodes.StoredNode] = {
      declaration(node)
        .flatMap(instances(_).headOption)
        .filter(_.id2 != node.id2)
        .toSet
    }

    method.start.call.map { call =>
      call -> gen(call).map(v => allOtherInstancesOf(v)).fold(Set())((v1, v2) => v1.union(v2))
    }.toMap
  }

  def instances(decl: nodes.StoredNode): List[nodes.StoredNode] = {
    decl._refIn().asScala.toList
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

  /**
    * Create a map that allows CFG successors to be retrieved for each node
    * */
  def initSucc(ns: List[nodes.StoredNode]): Map[nodes.StoredNode, List[nodes.StoredNode]] = {
    ns.map {
      case n @ (ret: nodes.Return)              => n -> List(ret.method.methodReturn)
      case n @ (cfgNode: CfgNode)               => n -> cfgNode.start.cfgNext.l
      case n @ (param: nodes.MethodParameterIn) => n -> param.start.method.cfgFirst.l
      case n =>
        logger.warn(s"Node type ${n.getClass.getSimpleName} should not be part of the CFG");
        n -> List()
    }.toMap
  }

  /**
    * Create a map that allows CFG predecessors to be retrieved for each node
    * */
  def initPred(ns: List[nodes.StoredNode], method: nodes.Method): Map[nodes.StoredNode, List[nodes.StoredNode]] = {
    ns.map {
      case n @ (_: CfgNode) if method.start.cfgFirst.headOption().contains(n) =>
        n -> method.parameter.l.sortBy(_.order).lastOption.toList
      case n @ (cfgNode: CfgNode) => n -> cfgNode.start.cfgPrev.l
      case n @ (param: nodes.MethodParameterIn) =>
        if (param.order == 1) { n -> List(method) } else {
          n -> method.parameter.order(param.order - 1).headOption.toList
        }
      case n =>
        logger.warn(s"Node type ${n.getClass.getSimpleName} should not be part of the CFG");
        n -> List()
    }.toMap
  }

  /** Pruned DDG, i.e., two call assignment nodes are adjacent if a
    * reaching definition edge reaches a node where the definition is used.
    * The final representation makes it straightforward to build def-use/use-def chains */
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
