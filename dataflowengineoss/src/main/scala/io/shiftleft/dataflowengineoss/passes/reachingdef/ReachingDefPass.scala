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

  private case class Solution(in: Map[nodes.CfgNode, Set[nodes.StoredNode]],
                              out: Map[nodes.CfgNode, Set[nodes.StoredNode]],
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
    val allCfgNodes = method.cfgNode.toList ++ List(entryNode, exitNode)

    // Gen[n]: the definitions generated at node n
    // Kill[n]: the definitions killed at node n
    val gen = initGen(method).withDefaultValue(Set.empty[nodes.StoredNode])
    val kill = initKill(method, gen).withDefaultValue(Set.empty[nodes.StoredNode])

    // Out[n] = GEN[n] for all n in `allCfgNodes`
    var out: Map[nodes.CfgNode, Set[nodes.StoredNode]] =
      allCfgNodes
        .map(cfgNode => cfgNode -> gen(cfgNode))
        .toMap

    // In[n] = empty for all n in `allCfgNodes`
    var in = Map
      .empty[nodes.CfgNode, Set[nodes.StoredNode]]
      .withDefaultValue(Set.empty[nodes.StoredNode])

    val worklist = mutable.Set.empty[nodes.CfgNode]
    worklist ++= allCfgNodes
    while (worklist.nonEmpty) {
      val n = worklist.head
      worklist -= n

      // IN[n] = Union(OUT[i]) for all predecessors i

      val inSet = n.start.cfgPrev
        .map(out)
        .reduceOption((x, y) => x.union(y))
        .getOrElse(Set.empty[nodes.StoredNode])
      in += n -> inSet

      val oldSize = out(n).size

      // OUT[n] = GEN[n] + IN[n] \ KILL[N]
      out += n -> gen(n).union(inSet.diff(kill(n)))

      if (oldSize != out(n).size)
        worklist ++= n.start.cfgNext.l
    }
    Solution(in, out, gen)
  }

  def initGen(method: nodes.Method): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {

    def getGensOfCall(call: nodes.StoredNode): Set[nodes.StoredNode] = {
      val methodParamOutsOrder = callToMethodParamOut(call)
        .filter(methPO => methPO._propagateIn().hasNext)
        .map(_.asInstanceOf[nodes.HasOrder].order.toInt)
      filterArgumentIndex(call._argumentOut().asScala.toList, methodParamOutsOrder).toSet
    }

    method.start.call.map { call =>
      call -> getGensOfCall(call)
    }.toMap
  }

  def initKill(method: nodes.Method,
               gen: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    method.start.call.map { call =>
      val gens = gen(call)
      call -> kills(gens)
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

    // Add edges from formal input parameters to all nodes they
    // are used in. This assumes that formal parameters cannot
    // be redefined in the method body. If it's false, then this
    // is incorrect.
    for {
      methodParameterIn <- method.parameter.l
      parameterReferences <- methodParameterIn._refIn.asScala
      operationNode <- getOperation(parameterReferences)
    } addEdge(methodParameterIn, operationNode, methodParameterIn.name)

    // Add edges from return nodes to formal method returns
    val methodReturn = method.methodReturn
    methodReturn.toReturn.foreach(
      returnNode =>
        addEdge(returnNode,
                methodReturn,
                returnNode.astChildren.headOption().map(_.asInstanceOf[nodes.CfgNode].code).getOrElse("")))

    // Now look at `out` for each node
    out.foreach {
      case (call: nodes.Call, outDefs) =>
        handleCall(call, outDefs)
      case (ret: nodes.Return, _) =>
        ret.astChildren.foreach { returnExpr =>
          val localRef = reference(returnExpr)
          in(ret)
            .filter(inElement => localRef == reference(inElement))
            .toList
            .flatMap { filteredInElement =>
              getExpressionFromGen(filteredInElement).map(addEdge(_, ret))
            }
            .headOption
            .getOrElse(addEdge(method, ret))
        }
      case _ => // ignore
    }

    def handleCall(call: Call, outDefs: Set[StoredNode]): Unit = {
      val usesInExpression = getUsesOfCall(call, gen)
      val localRefsUses = usesInExpression.map(reference).filter(_.isDefined)

      // Create edge from entry point to all nodes that are not
      // reached by any other definitions.
      if (in(call).isEmpty) {
        addEdge(method, call)
      }

      /* if use is not an identifier, add edge, as we are going to visit the use separately */
      usesInExpression.foreach { use =>
        if (!use.isInstanceOf[Identifier] && !use.isInstanceOf[Literal] && !use
              .isInstanceOf[FieldIdentifier]) {
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
      }

      if (isOperationAndAssignment(call)) {
        val localRefGens = gen(call).map(reference)
        in(call)
          .filter(inElement => localRefGens.contains(reference(inElement)))
          .foreach { filteredInElement =>
            getExpressionFromGen(filteredInElement).foreach(x =>
              addEdge(x, call, filteredInElement.asInstanceOf[nodes.CfgNode].code))
          }
      }

      for (elem <- outDefs) {
        val localRefGen = reference(elem)

        getExpressionFromGen(elem).foreach { expressionOfElement =>
          if (expressionOfElement != call && localRefsUses.contains(localRefGen)) {
            addEdge(expressionOfElement, call, elem.asInstanceOf[nodes.CfgNode].code)
          }
        }
      }
    }
    dstGraph
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

  def callToMethodParamOut(call: nodes.StoredNode): Iterable[nodes.StoredNode] = {
    NoResolve
      .getCalledMethods(call.asInstanceOf[nodes.Call])
      .flatMap(method => method.parameter.asOutput)
  }

  def filterArgumentIndex(nodeList: List[nodes.StoredNode], orderSeq: Iterable[Int]): List[nodes.StoredNode] = {
    nodeList.filter(node => orderSeq.exists(_ == node.asInstanceOf[nodes.HasArgumentIndex].argumentIndex.toInt))
  }

  def getUsesOfCall(expr: nodes.StoredNode,
                    gen: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Set[nodes.StoredNode] = {
    expr
      ._argumentOut()
      .asScala
      .filter(!gen(expr).contains(_))
      .toSet
  }

  def getExpressionFromGen(genVertex: nodes.StoredNode): Option[nodes.StoredNode] = {
    getOperation(genVertex).filter(_.isInstanceOf[nodes.Call])
  }

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

  def kills(node: Set[nodes.StoredNode]): Set[nodes.StoredNode] = {
    node.map(v => killsVertices(v)).fold(Set())((v1, v2) => v1.union(v2))
  }

  def getOperation(node: nodes.StoredNode): Option[nodes.StoredNode] = {
    node match {
      case identifier: nodes.Identifier => identifier._argumentIn().nextOption.flatMap(getOperation)
      case _: nodes.Call                => Some(node)
      case _: nodes.Return              => Some(node)
      case _                            => None
    }
  }
}
