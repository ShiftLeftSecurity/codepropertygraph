package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.jdk.CollectionConverters._
import overflowdb.traversal._

object Definition {

  def fromNode(node: nodes.StoredNode): Definition = {
    new Definition(node)
  }

}

case class Definition private (node: nodes.StoredNode) {}

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

class ReachingDefFlowGraph(method: nodes.Method) extends FlowGraph {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val entryNode: nodes.StoredNode = method
  val exitNode: nodes.StoredNode = method.methodReturn
  val allNodes: List[nodes.StoredNode] = method.cfgNode.toList ++ List(entryNode, exitNode) ++ method.parameter

  val succ: Map[nodes.StoredNode, List[nodes.StoredNode]] = initSucc(allNodes)
  val pred: Map[nodes.StoredNode, List[nodes.StoredNode]] = initPred(allNodes, method)

  /**
    * Create a map that allows CFG successors to be retrieved for each node
    * */
  private def initSucc(ns: List[nodes.StoredNode]): Map[nodes.StoredNode, List[nodes.StoredNode]] = {
    ns.map {
      case n @ (ret: nodes.Return)              => n -> List(ret.method.methodReturn)
      case n @ (cfgNode: nodes.CfgNode)         => n -> cfgNode.start.cfgNext.l
      case n @ (param: nodes.MethodParameterIn) => n -> param.start.method.cfgFirst.l
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
      case n @ (_: nodes.CfgNode) if method.start.cfgFirst.headOption.contains(n) =>
        n -> method.parameter.l.sortBy(_.order).lastOption.toList
      case n @ (cfgNode: nodes.CfgNode)     => n -> cfgNode.start.cfgPrev.l
      case n @ (_: nodes.MethodParameterIn) => n -> List(method)
      case n =>
        logger.warn(s"Node type ${n.getClass.getSimpleName} should not be part of the CFG");
        n -> List()
    }.toMap
  }

}

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

    def defsMadeByCall(call: nodes.Call): Set[Definition] = {
      (Set(call) ++ call.start.argument.toSet
        .filterNot(_.isInstanceOf[nodes.Literal])
        .filterNot(_.isInstanceOf[nodes.FieldIdentifier]))
        .map(x => Definition.fromNode(x.asInstanceOf[nodes.StoredNode]))
    }

    val defsForParams = method.start.parameter.l.map { param =>
      param -> Set(Definition.fromNode(param.asInstanceOf[nodes.StoredNode]))
    }

    val defsForCalls = method.start.call.l.map { call =>
      call -> defsMadeByCall(call)
    }

    (defsForParams ++ defsForCalls).toMap
  }

  /**
    * Initialize the map `kill`, a map that contains killed
    * definitions for each flow graph node.
    * */
  def initKill(method: nodes.Method,
               gen: Map[nodes.StoredNode, Set[Definition]]): Map[nodes.StoredNode, Set[Definition]] = {

    def allOtherInstancesOf(node: nodes.StoredNode): Set[nodes.StoredNode] = {
      declaration(node).toList
        .flatMap(instances)
        .filter(_.id2 != node.id2)
        .toSet
    }

    // We are also adding nodes here that may not even be definitions, but that's
    // fine since `kill` is only subtracted
    method.start.call.map { call =>
      call -> gen(call)
        .map(d => allOtherInstancesOf(d.node).map(x => Definition.fromNode(x)))
        .fold(Set())((v1, v2) => v1.union(v2))
    }.toMap
  }

  private def instances(decl: nodes.StoredNode): List[nodes.StoredNode] = {
    decl._refIn().asScala.toList ++ {
      if (decl.isInstanceOf[nodes.MethodParameterIn]) {
        List(decl)
      } else {
        List()
      }
    }
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

}

class ReachingDefInit(gen: Map[nodes.StoredNode, Set[Definition]]) extends InOutInit[Set[Definition]] {
  override def initIn: Map[nodes.StoredNode, Set[Definition]] =
    Map
      .empty[nodes.StoredNode, Set[Definition]]
      .withDefaultValue(Set.empty[Definition])

  override def initOut: Map[nodes.StoredNode, Set[Definition]] = gen
}
