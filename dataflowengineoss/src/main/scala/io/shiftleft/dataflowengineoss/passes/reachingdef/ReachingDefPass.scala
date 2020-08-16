package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{nodes, _}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
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
      val methodParamOutsOrder = NoResolve
        .getCalledMethods(call)
        .flatMap(method => method.parameter.asOutput)
        .filter(methPO => methPO._propagateIn().hasNext)
        .map(_.asInstanceOf[nodes.HasOrder].order.toInt)

      val nodeList = call._argumentOut().asScala.toList
      val orderSeq = methodParamOutsOrder
      nodeList.filter(node => orderSeq.exists(_ == node.asInstanceOf[nodes.HasArgumentIndex].argumentIndex.toInt)).toSet
    }

    val defsForParams = method.start.parameter.l.map { param =>
      param -> Set(param.asInstanceOf[nodes.StoredNode])
    }.toMap

    val defsForCalls = method.start.call.map { call =>
      call -> defsMadeByCall(call)
    }

    (defsForParams ++ defsForCalls).toMap
  }

  def initKill(method: nodes.Method,
               gen: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {

    /** Returns a set of nodes that are killed by the passed nodes */
    def killsVertices(node: nodes.StoredNode): Set[nodes.StoredNode] = {

      val localRefIt = node._refOut().asScala

      if (!localRefIt.hasNext) {
        Set()
      } else {
        val localRef = localRefIt.next
        localRef._refIn().asScala.filter(_.id2 != node.id2).toSet
      }
    }

    method.start.call.map { call =>
      call -> gen(call).map(v => killsVertices(v)).fold(Set())((v1, v2) => v1.union(v2))
    }.toMap
  }

  def uses(call: nodes.StoredNode, gen: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Set[nodes.StoredNode] = {
    call
      ._argumentOut()
      .asScala
      .filter(arg => arg._propagateOut().hasNext || !gen(call).contains(arg))
      .toSet
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
    val out = solution.out
    val gen = solution.gen

    val allNodes = in.keys.toList

    allNodes.foreach { node: nodes.StoredNode =>
      node match {
        case methodReturn: nodes.MethodReturn =>
          methodReturn.start.cfgPrev.isReturn.foreach { ret =>
            addEdge(ret,
                    methodReturn,
                    ret.astChildren.headOption().map(_.asInstanceOf[nodes.CfgNode].code).getOrElse(""))
          }
        case ret: nodes.Return =>
          ret.astChildren.foreach { returnExpr =>
            val localRef = reference(returnExpr)
            in(ret)
              .filter(inElement => localRef == reference(inElement))
              .toList
              .flatMap { inElement =>
                getParentCallFromGen(inElement).map(addEdge(_, ret))
              }
              .headOption
              .getOrElse(addEdge(method, ret))
          }
        case _ =>
          in(node).foreach {
            case (inNode: nodes.MethodParameterIn) =>
              val localsAndParamsUsed = uses(node, gen).flatMap(_._refOut().asScala)
              if (localsAndParamsUsed.contains(inNode)) {
                addEdge(inNode, node, inNode.name)
              }
            case _ =>
          }
      }
    }

    // Now look at `out` for each node
    allNodes.collect {
      case call: nodes.Call =>
        val usesInExpression = uses(call, gen)
        val localRefsUsed = usesInExpression.map(reference).filter(_.isDefined)
        val outDefs = out(call)

        /* if use is not an identifier, add edge, as we are going to visit the use separately */
        usesInExpression
          .filter(
            use =>
              !use.isInstanceOf[Identifier] && !use.isInstanceOf[Literal] && !use
                .isInstanceOf[FieldIdentifier])
          .foreach { use =>
            addEdge(use, call, use.asInstanceOf[nodes.CfgNode].code)

            /* handle indirect access uses: check if we have it in our out set and get
             * the corresponding def expression from which the definition reaches the use
             */
            if (isIndirectAccess(use)) {
              outDefs.filter(out => isIndirectAccess(out)).foreach { indirectOutDef =>
                val indirectOutCall = indirectOutDef.asInstanceOf[Call]
                if (indirectOutCall.code == use.asInstanceOf[Call].code) {
                  val expandedToCall = indirectOutCall.parentExpression
                  addEdge(expandedToCall, use, use.asInstanceOf[nodes.CfgNode].code)
                }
              }
            }
          }

        // Create edge from entry point to all nodes that are not
        // reached by any other definitions.
        val incomingParams = in(call).filter(_.isInstanceOf[nodes.MethodParameterIn])
        if (in(call).isEmpty || incomingParams == in(call) && localRefsUsed.flatten
              .intersect(incomingParams)
              .isEmpty) {
          addEdge(method, call)
        }

        if (isOperationAndAssignment(call)) {
          val localsGenerated = gen(call).map(reference)
          in(call).collect {
            case elem if localsGenerated.contains(reference(elem)) =>
              getParentCallFromGen(elem)
                .foreach { inParent =>
                  addEdge(inParent, call, elem.asInstanceOf[nodes.CfgNode].code)
                }
          }
        }

        // Adds reaching def edges from any args generated
        // by the call to the call itself, that is, we indicate
        // that there is a flow from the output definition to
        // the return value of the call. Strange.
        outDefs.foreach { elem =>
          val localRefGen = reference(elem)
          getParentCallFromGen(elem).foreach { parent =>
            if (parent != call && localRefsUsed.contains(localRefGen)) {
              addEdge(parent, call, elem.asInstanceOf[nodes.CfgNode].code)
            }
          }
        }
    }
    dstGraph
  }

  private def reference(node: nodes.StoredNode): Option[nodes.StoredNode] =
    node._refOut().nextOption

  private def isOperationAndAssignment(call: nodes.Call): Boolean = {
    implicit val resolver: NoResolve.type = NoResolve
    call.start.callee.parameter.asOutput.l.flatMap(_._propagateIn().asScala.toList).nonEmpty &&
    call.start.callee.parameter.l
      .flatMap(_._propagateOut().asScala.toList)
      .nonEmpty && call.name != Operators.assignment
  }

  private def isIndirectAccess(node: nodes.StoredNode): Boolean =
    node match {
      case call: nodes.Call => MemberAccess.isGenericMemberAccessName(call.name)
      case _                => false
    }

  private def getParentCallFromGen(genVertex: nodes.StoredNode): Option[nodes.StoredNode] = {
    def getOperation(node: nodes.StoredNode): Option[nodes.StoredNode] = {
      node match {
        case identifier: nodes.Identifier => identifier._argumentIn().nextOption.flatMap(getOperation)
        case _: nodes.Call                => Some(node)
        case _: nodes.Return              => Some(node)
        case _                            => None
      }
    }
    getOperation(genVertex).filter(_.isInstanceOf[nodes.Call])
  }

}
