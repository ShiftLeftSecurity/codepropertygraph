package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess.isGenericMemberAccessName
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.traversal._

/**
  * The variables defined/used in the reaching def problem can
  * all be represented via nodes in the graph, however, that's
  * pretty confusing because it is then unclear that variables
  * and nodes are actually two separate domains. To make the
  * definition domain visible, we wrap nodes in `Definition`
  * classes. From a computational standpoint, this is not necessary,
  * but it greatly improves readability.
  * */
object Definition {
  def fromNode(node: nodes.StoredNode): Definition = {
    new Definition(node)
  }
}

case class Definition(node: nodes.StoredNode) {}

object ReachingDefProblem {

  def create(method: nodes.Method): DataFlowProblem[Set[Definition]] = {
    val flowGraph = new ReachingDefFlowGraph(method)
    val transfer = new ReachingDefTransferFunction(method)
    val init = new ReachingDefInit(transfer.gen)
    def meet: (Set[Definition], Set[Definition]) => Set[Definition] =
      (x: Set[Definition], y: Set[Definition]) => { x.union(y) }

    new DataFlowProblem[Set[Definition]](flowGraph, transfer, meet, init, true, Set[Definition]())
  }

}

/**
  * The control flow graph as viewed by the data flow solver.
  * */
class ReachingDefFlowGraph(method: nodes.Method) extends FlowGraph {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val entryNode: nodes.StoredNode = method
  val exitNode: nodes.StoredNode = method.methodReturn
  val allNodes: List[nodes.StoredNode] = method.cfgNode.toList ++ List(entryNode, exitNode) ++ method.parameter.toList

  val succ: Map[nodes.StoredNode, List[nodes.StoredNode]] = initSucc(allNodes)
  val pred: Map[nodes.StoredNode, List[nodes.StoredNode]] = initPred(allNodes, method)

  /**
    * Create a map that allows CFG successors to be retrieved for each node
    * */
  private def initSucc(ns: List[nodes.StoredNode]): Map[nodes.StoredNode, List[nodes.StoredNode]] = {
    ns.map {
      case n @ (_: nodes.Return) => n -> List(exitNode)
      case n @ (param: nodes.MethodParameterIn) =>
        n -> {
          val nextParam = param.method.parameter.order(param.order + 1).headOption
          if (nextParam.isDefined) { nextParam.toList } else { param.method.cfgFirst.l }
        }
      case n @ (cfgNode: nodes.CfgNode) =>
        n ->
          // `.cfgNext` would be wrong here because it filters `METHOD_RETURN`
          cfgNode.out(EdgeTypes.CFG).map(_.asInstanceOf[nodes.StoredNode]).l
      case n =>
        logger.warn(s"Node type ${n.getClass.getSimpleName} should not be part of the CFG");
        n -> List()
    }.toMap
  }

  /**
    * Create a map that allows CFG predecessors to be retrieved for each node
    * */
  private def initPred(ns: List[nodes.StoredNode],
                       method: nodes.Method): Map[nodes.StoredNode, List[nodes.StoredNode]] = {
    ns.map {
      case n @ (param: nodes.MethodParameterIn) =>
        n -> {
          val prevParam = param.method.parameter.order(param.order - 1).headOption
          if (prevParam.isDefined) { prevParam.toList } else { List(method) }
        }
      case n @ (_: nodes.CfgNode) if method.cfgFirst.headOption.contains(n) =>
        n -> method.parameter.l.sortBy(_.order).lastOption.toList

      case n @ (cfgNode: nodes.CfgNode) => n -> cfgNode.cfgPrev.l

      case n =>
        logger.warn(s"Node type ${n.getClass.getSimpleName} should not be part of the CFG");
        n -> List()
    }.toMap
  }

}

/**
  * For each node of the graph, this transfer function defines how it affects
  * the propagation of definitions.
  * */
class ReachingDefTransferFunction(method: nodes.Method) extends TransferFunction[Set[Definition]] {

  val gen: Map[nodes.StoredNode, Set[Definition]] = initGen(method).withDefaultValue(Set.empty[Definition])
  val kill: Map[nodes.StoredNode, Set[Definition]] =
    initKill(method, gen).withDefaultValue(Set.empty[Definition])

  /**
    * For a given flow graph node `n` and set of definitions, apply the transfer
    * function to obtain the updated set of definitions, considering `gen(n)`
    * and `kill(n)`.
    * */
  override def apply(n: nodes.StoredNode, x: Set[Definition]): Set[Definition] = {
    gen(n).union(x.diff(kill(n)))
  }

  /**
    * Initialize the map `gen`, a map that contains generated
    * definitions for each flow graph node.
    * */
  def initGen(method: nodes.Method): Map[nodes.StoredNode, Set[Definition]] = {

    val defsForParams = method.parameter.l.map { param =>
      param -> Set(Definition.fromNode(param.asInstanceOf[nodes.StoredNode]))
    }

    // We filter out field accesses to ensure that they propagate
    // taint unharmed.

    val defsForCalls = method.call
      .filterNot(x => isGenericMemberAccessName(x.name))
      .l
      .map { call =>
        call -> {
          val retVal = Set(call)
          val args = call.argument.filter(hasValidGenType)
          (retVal ++ args)
            .map(x => Definition.fromNode(x.asInstanceOf[nodes.StoredNode]))
        }
      }
    (defsForParams ++ defsForCalls).toMap
  }

  /**
    * Restricts the types of nodes that represent definitions.
    * */
  private def hasValidGenType(node: nodes.Expression): Boolean = {
    node match {
      case _: nodes.Call       => true
      case _: nodes.Identifier => true
      case _                   => false
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
  private def initKill(method: nodes.Method,
                       gen: Map[nodes.StoredNode, Set[Definition]]): Map[nodes.StoredNode, Set[Definition]] = {

    // We filter out field accesses to ensure that they propagate
    // taint unharmed.

    method.call
      .filterNot(x => isGenericMemberAccessName(x.name))
      .map { call =>
        call -> killsForGens(gen(call))
      }
      .toMap
  }

  /**
    * The only way in which a call can kill another definition is by
    * generating a new definition for the same variable. Given the
    * set of generated definitions `gens`, we calculate definitions
    * of the same variable for each, that is, we calculate kill(call)
    * based on gen(call).
    * */
  private def killsForGens(genOfCall: Set[Definition]): Set[Definition] = {
    genOfCall.flatMap { definition =>
      definitionsOfSameVariable(definition)
    }
  }

  private def definitionsOfSameVariable(definition: Definition): Set[Definition] = {
    val definedNodes = definition.node match {
      case param: nodes.MethodParameterIn =>
        method.cfgNode
          .filter(x => x.id != param.id)
          .isIdentifier
          .nameExact(param.name)
          .toSet
      case identifier: nodes.Identifier =>
        method.cfgNode
          .filter(x => x.id != identifier.id)
          .isIdentifier
          .nameExact(identifier.name)
          .toSet
      case call: nodes.Call =>
        method.cfgNode
          .filter(x => x.id != call.id)
          .isCall
          .codeExact(call.code)
          .toSet
      case _ => Set()
    }
    definedNodes.map(x => Definition.fromNode(x))
  }

}

class ReachingDefInit(gen: Map[nodes.StoredNode, Set[Definition]]) extends InOutInit[Set[Definition]] {
  override def initIn: Map[nodes.StoredNode, Set[Definition]] =
    Map
      .empty[nodes.StoredNode, Set[Definition]]
      .withDefaultValue(Set.empty[Definition])

  override def initOut: Map[nodes.StoredNode, Set[Definition]] = gen
}
