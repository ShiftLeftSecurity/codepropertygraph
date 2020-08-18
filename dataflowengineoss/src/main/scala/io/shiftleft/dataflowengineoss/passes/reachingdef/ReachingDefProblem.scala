package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.jdk.CollectionConverters._
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode

object ReachingDefProblem {

  def create(method: nodes.Method): DataFlowProblem[Set[nodes.StoredNode]] = {
    val flowGraph = new ReachingDefFlowGraph(method)
    val transfer = new ReachingDefTransferFunction(method)
    val init = new ReachingDefInit(transfer.gen)
    def meet: (Set[StoredNode], Set[StoredNode]) => Set[StoredNode] =
      (x: Set[StoredNode], y: Set[StoredNode]) => { x.union(y) }
    new DataFlowProblem[Set[StoredNode]](flowGraph, transfer, meet, init, true, Set[nodes.StoredNode]())
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
      case n @ (_: nodes.CfgNode) if method.start.cfgFirst.headOption().contains(n) =>
        n -> method.parameter.l.sortBy(_.order).lastOption.toList
      case n @ (cfgNode: nodes.CfgNode)     => n -> cfgNode.start.cfgPrev.l
      case n @ (_: nodes.MethodParameterIn) => n -> List(method)
      case n =>
        logger.warn(s"Node type ${n.getClass.getSimpleName} should not be part of the CFG");
        n -> List()
    }.toMap
  }

}

class ReachingDefTransferFunction(method: nodes.Method) extends TransferFunction[Set[nodes.StoredNode]] {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val gen: Map[nodes.StoredNode, Set[nodes.StoredNode]] = initGen(method).withDefaultValue(Set.empty[nodes.StoredNode])
  val kill: Map[nodes.StoredNode, Set[nodes.StoredNode]] =
    initKill(method, gen).withDefaultValue(Set.empty[nodes.StoredNode])

  /**
    * For a given flow graph node `n` and set of definitions, apply the transfer
    * function to obtain the updated set of definitions, considering `gen(n)`
    * and `kill(n)`.
    * */
  override def apply(n: nodes.StoredNode, x: Set[nodes.StoredNode]): Set[nodes.StoredNode] = {
    gen(n).union(x.diff(kill(n)))
  }

  /**
    * Initialize the map `gen`, a map that contains generated
    * definitions for each flow graph node.
    * */
  def initGen(method: nodes.Method): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {

    def defsMadeByCall(call: nodes.Call): Set[nodes.StoredNode] = {

      val definedParams = methodsForCall(call).start.parameter.asOutput
        .where(outParam => outParam._propagateIn().hasNext)
        .order
        .l

      call.start.argument.l.filter(arg => definedParams.contains(arg.argumentIndex)).toSet ++ {
        if (methodForCall(call)
              .map(method => method.methodReturn)
              .exists(methodReturn => methodReturn._propagateIn().hasNext) || !hasAnnotation(call)) {
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

  /**
    * Initialize the map `kill`, a map that contains killed
    * definitions for each flow graph node.
    * */
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

  private def instances(decl: nodes.StoredNode): List[nodes.StoredNode] = {
    decl._refIn().asScala.toList
  }

  private def methodForCall(call: nodes.Call): Option[nodes.Method] = {
    methodsForCall(call) match {
      case List(x) => Some(x)
      case List()  => None
      case list =>
        logger.warn(s"Multiple methods with name: ${call.name}, using first one")
        Some(list.head)
    }
  }

  private def methodsForCall(call: nodes.Call): List[nodes.Method] = {
    NoResolve.getCalledMethods(call).toList
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

  private def hasAnnotation(call: nodes.Call): Boolean = {
    methodForCall(call).exists(method => method.parameter.l.exists(x => x._propagateOut().hasNext))
  }

}

class ReachingDefInit(gen: Map[nodes.StoredNode, Set[nodes.StoredNode]]) extends InOutInit[Set[nodes.StoredNode]] {
  override def initIn: Map[nodes.StoredNode, Set[nodes.StoredNode]] =
    Map
      .empty[nodes.StoredNode, Set[nodes.StoredNode]]
      .withDefaultValue(Set.empty[nodes.StoredNode])

  override def initOut: Map[nodes.StoredNode, Set[nodes.StoredNode]] = gen
}
