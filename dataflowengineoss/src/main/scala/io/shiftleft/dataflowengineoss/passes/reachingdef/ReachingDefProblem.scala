package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess.isGenericMemberAccessName

import scala.collection.{Set, mutable}

/**
  * The variables defined/used in the reaching def problem can
  * all be represented via nodes in the graph, however, that's
  * pretty confusing because it is then unclear that variables
  * and nodes are actually two separate domains. To make the
  * definition domain visible, we use the type alias `Definition`.
  * From a computational standpoint, this is not necessary,
  * but it greatly improves readability.
  * */
object Definition {
  def fromNode(node: StoredNode, nodeToNumber: Map[StoredNode, Int]): Definition = {
    nodeToNumber(node)
  }
}

object ReachingDefProblem {
  def create(method: Method): DataFlowProblem[mutable.BitSet] = {
    val flowGraph = new FlowGraph(method)
    val transfer = new OptimizedReachingDefTransferFunction(flowGraph)
    val init = new ReachingDefInit(transfer.gen)
    def meet: (mutable.BitSet, mutable.BitSet) => mutable.BitSet =
      (x: mutable.BitSet, y: mutable.BitSet) => { x.union(y) }

    new DataFlowProblem[mutable.BitSet](flowGraph, transfer, meet, init, true, mutable.BitSet())
  }

}

class NodeNumbering(method: Method) {

  val entryNode: StoredNode = method
  val exitNode: StoredNode = method.methodReturn

  val allNodesReversePostOrder: List[StoredNode] =
    List(entryNode) ++ method.parameter.toList ++ method.reversePostOrder.toList ++ List(exitNode)

  val nodeToNumber: Map[StoredNode, Int] = allNodesReversePostOrder.zipWithIndex.map { case (x, i) => x -> i }.toMap
  val numberToNode: Map[Int, StoredNode] = allNodesReversePostOrder.zipWithIndex.map { case (x, i) => i -> x }.toMap

  lazy val allNodesPostOrder: List[StoredNode] =
    List(exitNode) ++ method.postOrder.toList ++ method.parameter.toList ++ List(entryNode)

}

/**
  * For each node of the graph, this transfer function defines how it affects
  * the propagation of definitions.
  * */
class ReachingDefTransferFunction(flowGraph: FlowGraph) extends TransferFunction[mutable.BitSet] {

  private val nodeToNumber = flowGraph.nodeToNumber

  val method = flowGraph.method

  val gen: Map[Int, mutable.BitSet] =
    initGen(method).withDefaultValue(mutable.BitSet())

  val kill: Map[Int, mutable.BitSet] =
    initKill(method, gen).withDefaultValue(mutable.BitSet())

  /**
    * For a given flow graph node `n` and set of definitions, apply the transfer
    * function to obtain the updated set of definitions, considering `gen(n)`
    * and `kill(n)`.
    * */
  override def apply(n: Int, x: mutable.BitSet): mutable.BitSet = {
    gen(n).union(x.diff(kill(n)))
  }

  /**
    * Initialize the map `gen`, a map that contains generated
    * definitions for each flow graph node.
    * */
  def initGen(method: Method): Map[Int, mutable.BitSet] = {

    val defsForParams = method.parameter.l.map { param =>
      param -> mutable.BitSet(Definition.fromNode(param.asInstanceOf[StoredNode], nodeToNumber))
    }

    // We filter out field accesses to ensure that they propagate
    // taint unharmed.

    val defsForCalls = method.call
      .filterNot(x => isGenericMemberAccessName(x.name))
      .l
      .map { call =>
        call -> {
          val retVal = List(call)
          val args = call.argument
            .filter(hasValidGenType)
            .l
          mutable.BitSet(
            (retVal ++ args)
              .collect {
                case x if nodeToNumber.contains(x) =>
                  Definition.fromNode(x.asInstanceOf[StoredNode], nodeToNumber)
              }: _*
          )
        }
      }
    val m = (defsForParams ++ defsForCalls).toMap
    m.map { case (k, xs) => nodeToNumber(k) -> xs }
  }

  /**
    * Restricts the types of nodes that represent definitions.
    * */
  private def hasValidGenType(node: Expression): Boolean = {
    node match {
      case _: Call       => true
      case _: Identifier => true
      case _             => false
    }
  }

  /**
    * Initialize the map `kill`, a map that contains killed
    * definitions for each flow graph node.
    *
    * All operations in our graph are represented by calls and non-operations
    * such as identifiers or field-identifiers have empty gen and kill sets,
    * meaning that they just pass on definitions unaltered.
    * */
  private def initKill(method: Method, gen: Map[Int, mutable.BitSet]): Map[Int, mutable.BitSet] = {

    val allIdentifiers: Map[String, List[Identifier]] = method.ast.isIdentifier.l
      .groupBy(_.name)
      .withDefaultValue(List.empty[Identifier])

    val allCalls: Map[String, List[Call]] = method.call.l
      .groupBy(_.code)
      .withDefaultValue(List.empty[Call])

    // We filter out field accesses to ensure that they propagate
    // taint unharmed.

    val m = method.call
      .filterNot(x => isGenericMemberAccessName(x.name))
      .map { call =>
        call -> killsForGens(gen(nodeToNumber(call)), allIdentifiers, allCalls)
      }
      .toMap
    m.map { case (k, xs) => nodeToNumber(k) -> xs }
  }

  /**
    * The only way in which a call can kill another definition is by
    * generating a new definition for the same variable. Given the
    * set of generated definitions `gens`, we calculate definitions
    * of the same variable for each, that is, we calculate kill(call)
    * based on gen(call).
    * */
  private def killsForGens(genOfCall: mutable.BitSet,
                           allIdentifiers: Map[String, List[Identifier]],
                           allCalls: Map[String, List[Call]]): mutable.BitSet = {

    def definitionsOfSameVariable(definition: Definition): mutable.BitSet = {
      val definedNodes = flowGraph.numberToNode(definition) match {
        case param: MethodParameterIn =>
          allIdentifiers(param.name)
            .filter(x => x.id != param.id)
        case identifier: Identifier =>
          allIdentifiers(identifier.name)
            .filter(x => x.id != identifier.id)
        case call: Call =>
          allCalls(call.code)
            .filter(x => x.id != call.id)
        case _ => Set()
      }
      mutable.BitSet(
        definedNodes
        // It can happen that the CFG is broken and contains isolated nodes,
        // in which case they are not in `nodeToNumber`. Let's filter those.
        .collect { case x if nodeToNumber.contains(x) => Definition.fromNode(x, nodeToNumber) }.toList: _*
      )
    }

    genOfCall.flatMap { definition =>
      definitionsOfSameVariable(definition)
    }
  }

}

/**
  * Lone Identifier Optimization: we first determine and store all identifiers
  * that neither refer to a local or parameter and that appear only once
  * as a call argument. For these identifiers, we know that they are
  * not used in any other location in the code, and so, we remove
  * them from `gen` sets so that they need not be propagated through
  * the entire graph only to determine that they reach the exit node. Instead,
  * when creating reaching definition edges, we simply create edges from the
  * identifier to the exit node.
  * */
class OptimizedReachingDefTransferFunction(flowGraph: FlowGraph) extends ReachingDefTransferFunction(flowGraph) {

  lazy val loneIdentifiers: Map[Call, List[Definition]] = {
    val paramAndLocalNames = method.parameter.name.l ++ method.local.name.l

    val callArgPairs = method.call.flatMap { call =>
      call.argument.isIdentifier
        .filterNot(i => paramAndLocalNames.contains(i.name))
        .map(arg => (arg.name, call, arg))
    }.l

    callArgPairs
      .groupBy(_._1)
      .collect { case (_, v) if v.size == 1 => v.map { case (_, call, arg) => (call, arg) }.head }
      .toList
      .groupBy(_._1)
      .map {
        case (k, v) =>
          (k,
           v.filter(x => flowGraph.nodeToNumber.contains(x._2))
             .map(x => Definition.fromNode(x._2, flowGraph.nodeToNumber)))
      }
  }

  override def initGen(method: Method): Map[Int, mutable.BitSet] =
    withoutLoneIdentifiers(super.initGen(method))

  private def withoutLoneIdentifiers(g: Map[Int, mutable.BitSet]): Map[Int, mutable.BitSet] = {
    g.map {
      case (k, defs) =>
        flowGraph.numberToNode(k) match {
          case call: Call if loneIdentifiers.contains(call) =>
            (k, defs.filterNot(loneIdentifiers(call).contains(_)))
          case _ => (k, defs)
        }
    }
  }
}

class ReachingDefInit(gen: Map[Int, mutable.BitSet]) extends InOutInit[mutable.BitSet] {
  override def initIn: Map[Int, mutable.BitSet] =
    Map
      .empty[Int, mutable.BitSet]
      .withDefaultValue(mutable.BitSet())

  override def initOut: Map[Int, mutable.BitSet] = gen
}
