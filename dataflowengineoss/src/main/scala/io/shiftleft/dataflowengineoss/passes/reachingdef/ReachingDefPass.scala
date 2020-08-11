package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{nodes, _}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * A pass that calculates reaching definitions ("data dependencies").
  * */
class ReachingDefPass(cpg: Cpg) extends ParallelCpgPass[nodes.Method](cpg) {

  import DataFlowFrameworkHelper._

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

    // Gen[n]: the definitions generated at node n
    // Kill[n]: the definitions killed at node n
    val gen = initGen(method).withDefaultValue(Set.empty[nodes.StoredNode])
    val kill = initKill(method, gen).withDefaultValue(Set.empty[nodes.StoredNode])

    val entryNode = method
    val exitNode = method.methodReturn
    val allCfgNodes = method.cfgNode.toList ++ List(entryNode, exitNode) ++ method.parameter.l

    // Successors and predecessors in the CFG
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
        .map(out)
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

  /** For each node n in the CFG + parameters, initialize gen[n], that is,
    * the definitions generated at node `n`.
    *
    * For calls, we place arguments into gen[n]. These may be nodes
    * that represent entire expressions, e.g., `x->y`. For parameters,
    * we place the parameter node itself into gen[n].
    * */
  def initGen(method: nodes.Method): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    val gensForParams = method.parameter.l.map { param =>
      param -> Set(param.asInstanceOf[nodes.StoredNode])
    }
    val gensForCalls = method.start.call.map { call =>
      call -> definitionsInCall(call)
    }
    (gensForParams ++ gensForCalls).toMap
  }

  private def definitionsInCall(call: nodes.Call): Set[nodes.StoredNode] = {
    val methodParamOutsOrder = callToMethodParamOut(call)
      .filter(methPO => methPO._propagateIn().hasNext)
      .map(_.asInstanceOf[nodes.HasOrder].order.toInt)
    call.start.argument.l.filter(n => methodParamOutsOrder.toSet.contains(n.order)).toSet
  }

  def initKill(method: nodes.Method,
               gen: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    method.start.call.map { call =>
      call -> gen(call).map(v => killedVertices(v)).fold(Set())((v1, v2) => v1.union(v2))
    }.toMap
  }

  def initSucc(ns: List[nodes.StoredNode]): Map[nodes.StoredNode, List[nodes.StoredNode]] = {
    ns.map {
      case n @ (cfgNode: CfgNode)               => n -> cfgNode.start.cfgNext.l
      case n @ (param: nodes.MethodParameterIn) => n -> param.start.method.cfgFirst.l
      case n                                    => println("Shouldn't happen"); n -> List()
    }.toMap
  }

  def initPred(ns: List[nodes.StoredNode], method: nodes.Method): Map[nodes.StoredNode, List[nodes.StoredNode]] = {
    ns.map {
      case n @ (cfgNode: CfgNode) if method.start.cfgFirst.l.exists(_.id2 == n.id2) =>
        n -> (cfgNode.start.cfgPrev.l ++ method.parameter.l)
      case n @ (cfgNode: CfgNode)               => n -> cfgNode.start.cfgPrev.l
      case n @ (param: nodes.MethodParameterIn) => n -> param.start.method.l
      case n                                    => println("Shouldn't happen"); n -> List()
    }.toMap
  }

  /** Pruned DDG, i.e., two call assignment nodes are adjacent if a
    * reaching definition edge reaches a node where the definition is used.
    * The final representation makes it straightforward to build def-use/use-def chains */
  private def addReachingDefEdges(method: nodes.Method, solution: Solution): DiffGraph.Builder = {

    implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder

    val in = solution.in
    val out = solution.out
    val gen = solution.gen
    val allNodes = in.keys.toList

    allNodes.collect{ case ret : nodes.Return =>
      addEdge(ret, method.methodReturn, ret.astChildren.order(1).where(_.isInstanceOf[nodes.CfgNode]).map(_.asInstanceOf[nodes.CfgNode]).code().headOption().getOrElse(""))
    }

    allNodes.collect { case call : nodes.StoredNode if (call.isInstanceOf[nodes.Call] || call.isInstanceOf[nodes.Return])  =>
     createIntraCallReachingDefinitions(call)

      in(call).foreach {
        case param: nodes.MethodParameterIn =>
          // We create an edge to all arguments of the call
          // that mention the parameter AND "use" it, in the
          // sense of not just redefining it. We also checked
          // that it is in `in(call)` and thus reaches the call.
          val mentions = param._refIn().asScala.toSet
          mentions.intersect(use(call, gen)).foreach{ u =>
            addEdge(param, u, param.name)
          }
        case inIdentifier : nodes.Identifier =>
          // This identifier could be an instance of a local or a parameter
          val mentions = inIdentifier._refOut().asScala.flatMap { node =>
            node match {
              case  x : nodes.MethodParameterIn =>
                x.start.referencingIdentifiers.toSet
              case x : nodes.Local =>
                x.start.referencingIdentifiers.toSet
              case _ => Set[nodes.StoredNode]()
            }
          }.toSet

          mentions.intersect(use(call, gen)).foreach{ u =>
            addEdge(inIdentifier, u, inIdentifier.name)
          }
        case _ =>
      }
    }

    /**
      * For each call, create reachable_by edges from each use to each def and
      * from each use to the call itself (which represents the return value).
      * The latter is an important design choice: we are saying that the return
      * value is always influenced by the arguments of the function. This may not
      * always be true, but when it isn't we can filter out these flows once
      * they have been returned by the data flow tracker. This is in contrast
      * to an approach where we only return flows through methods which we
      * have specifically marked as methods that taint their return value
      * based on their arguments.
      * */
    def createIntraCallReachingDefinitions(callOrReturn : nodes.StoredNode) : Unit = {
      use(callOrReturn, gen).foreach{ useSymbol =>
        gen(callOrReturn).foreach{ genSymbol =>
          addEdge(useSymbol, genSymbol, useSymbol.asInstanceOf[nodes.CfgNode].code)
        }
        addEdge(useSymbol, callOrReturn, useSymbol.asInstanceOf[nodes.CfgNode].code)
      }
    }

    dstGraph
  }

  private def addEdge(fromNode: nodes.StoredNode, toNode: nodes.StoredNode, variable: String = "")(
      implicit dstGraph: DiffGraph.Builder): Unit = {
    val properties = List((EdgeKeyNames.VARIABLE, variable))
    dstGraph.addEdgeInOriginal(fromNode, toNode, EdgeTypes.REACHING_DEF, properties)
  }

  private def reference(node: nodes.StoredNode): Option[nodes.StoredNode] =
    node._refOut().nextOption

  private def isOperationAndAssignment(call: nodes.Call): Boolean = {
    call.name match {
      case Operators.assignmentAnd                  => true
      case Operators.assignmentArithmeticShiftRight => true
      case Operators.assignmentDivision             => true
      case Operators.assignmentExponentiation       => true
      case Operators.assignmentLogicalShiftRight    => true
      case Operators.assignmentMinus                => true
      case Operators.assignmentModulo               => true
      case Operators.assignmentMultiplication       => true
      case Operators.assignmentOr                   => true
      case Operators.assignmentPlus                 => true
      case Operators.assignmentShiftLeft            => true
      case Operators.assignmentXor                  => true
      case _                                        => false
    }
  }

  private def isIndirectAccess(node: nodes.StoredNode): Boolean =
    node match {
      case call: nodes.Call => MemberAccess.isGenericMemberAccessName(call.name)
      case _                => false
    }

}

/** Common functionalities needed for data flow frameworks */
object DataFlowFrameworkHelper {

  def callToMethodParamOut(call: nodes.Call): Iterable[nodes.StoredNode] = {
    NoResolve
      .getCalledMethods(call)
      .flatMap(method => method.parameter.asOutput)
  }

  def use(call: nodes.StoredNode, gen: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Set[nodes.StoredNode] = {
    call
      ._argumentOut()
      .asScala
      .filter(!gen(call).contains(_))
      .toSet
  }

  def parentCallForDefinition(genVertex: nodes.StoredNode): Option[nodes.StoredNode] = {
    parentCall(genVertex).filter(_.isInstanceOf[nodes.Call])
  }

  /**
    * Walks up argument edges until the parent `Call` or `Return`
    * is found
    **/
  def parentCall(node: nodes.StoredNode): Option[nodes.StoredNode] = {
    node match {
      case identifier: nodes.Identifier => identifier._argumentIn().nextOption.flatMap(parentCall)
      case _: nodes.Call                => Some(node)
      case _: nodes.Return              => Some(node)
      case _                            => None
    }
  }

  /** Returns a set of nodes that are killed by the passed nodes */
  def killedVertices(node: nodes.StoredNode): Set[nodes.StoredNode] = {
    // We kill anything that references the same local/param
    val localsAndParamsReferenced = node._refOut().asScala.toList
    localsAndParamsReferenced.flatMap { localRef =>
      localRef._refIn().asScala.filter(_.id2 != node.id2).toSet ++
        localsAndParamsReferenced.filter(_.isInstanceOf[nodes.MethodParameterIn]).toSet
    }.toSet
  }

}
