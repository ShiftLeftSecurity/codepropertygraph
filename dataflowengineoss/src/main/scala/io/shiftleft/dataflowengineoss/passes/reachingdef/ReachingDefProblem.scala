package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess.isGenericMemberAccessName
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.traversal._

import scala.collection.{Set, mutable}

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
  def fromNode(node: StoredNode): Definition = {
    new Definition(node)
  }
}

case class Definition(node: StoredNode) {}

object ReachingDefProblem {

  def create(method: Method): DataFlowProblem[mutable.Set[Definition]] = {
    val flowGraph = new ReachingDefFlowGraph(method)
    val transfer = new ReachingDefTransferFunction(method)
    val init = new ReachingDefInit(transfer.gen)
    def meet: (mutable.Set[Definition], mutable.Set[Definition]) => mutable.Set[Definition] =
      (x: mutable.Set[Definition], y: mutable.Set[Definition]) => { x.union(y) }

    new DataFlowProblem[mutable.Set[Definition]](flowGraph, transfer, meet, init, true, mutable.Set[Definition]())
  }

}

/**
  * The control flow graph as viewed by the data flow solver.
  * */
class ReachingDefFlowGraph(method: Method) extends FlowGraph {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val entryNode: StoredNode = method
  val exitNode: StoredNode = method.methodReturn
  val allNodes: List[StoredNode] = method.cfgNode.toList ++ List(entryNode, exitNode) ++ method.parameter.toList

  val succ: Map[StoredNode, List[StoredNode]] = initSucc(allNodes)
  val pred: Map[StoredNode, List[StoredNode]] = initPred(allNodes, method)

  /**
    * Create a map that allows CFG successors to be retrieved for each node
    * */
  private def initSucc(ns: List[StoredNode]): Map[StoredNode, List[StoredNode]] = {
    ns.map {
      case n @ (_: Return) => n -> List(exitNode)
      case n @ (param: MethodParameterIn) =>
        n -> {
          val nextParam = param.method.parameter.order(param.order + 1).headOption
          if (nextParam.isDefined) { nextParam.toList } else { param.method.cfgFirst.l }
        }
      case n @ (cfgNode: CfgNode) =>
        n ->
          // `.cfgNext` would be wrong here because it filters `METHOD_RETURN`
          cfgNode.out(EdgeTypes.CFG).map(_.asInstanceOf[StoredNode]).l
      case n =>
        logger.warn(s"Node type ${n.getClass.getSimpleName} should not be part of the CFG");
        n -> List()
    }.toMap
  }

  /**
    * Create a map that allows CFG predecessors to be retrieved for each node
    * */
  private def initPred(ns: List[StoredNode], method: Method): Map[StoredNode, List[StoredNode]] = {
    ns.map {
      case n @ (param: MethodParameterIn) =>
        n -> {
          val prevParam = param.method.parameter.order(param.order - 1).headOption
          if (prevParam.isDefined) { prevParam.toList } else { List(method) }
        }
      case n @ (_: CfgNode) if method.cfgFirst.headOption.contains(n) =>
        n -> method.parameter.l.sortBy(_.order).lastOption.toList

      case n @ (cfgNode: CfgNode) => n -> cfgNode.cfgPrev.l

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
class ReachingDefTransferFunction(method: Method) extends TransferFunction[mutable.Set[Definition]] {

  val generatedOnlyOnce: Map[Call, List[Definition]] = {
    val paramAndLocalNames = method.parameter.name.l ++ method.local.name.l

    val callArgPairs: List[(Call, Identifier)] = method.call.l
      .flatMap { call =>
        call.argument.isIdentifier
          .filterNot { i =>
            paramAndLocalNames.contains(i.name)
          }
          .l
          .map { a =>
            (call, a)
          }
      }

    callArgPairs
      .map { case (call, arg) => (arg.name, call, arg) }
      .groupBy(_._1)
      .collect { case (_, v) if v.size == 1 => v.map(x => (x._2, x._3)).head }
      .toList
      .groupBy(_._1)
      .map { case (k, v) => (k, v.map(_._2).map(Definition.fromNode)) }
  }

  def notInGenOnce(g: Map[StoredNode, mutable.Set[Definition]]): Map[StoredNode, mutable.Set[Definition]] = {
    g.map {
      case (k, defs) =>
        k match {
          case call: Call if (generatedOnlyOnce.contains(call)) =>
            (call, defs.filterNot(generatedOnlyOnce(call).contains(_)))
          case _ => (k, defs)
        }
    }
  }

  val gen: Map[StoredNode, mutable.Set[Definition]] = {
    notInGenOnce(
      initGen(method)
    ).withDefaultValue(mutable.Set.empty[Definition])
  }

  val kill: Map[StoredNode, Set[Definition]] =
    initKill(method, gen).withDefaultValue(Set.empty[Definition])

  /**
    * For a given flow graph node `n` and set of definitions, apply the transfer
    * function to obtain the updated set of definitions, considering `gen(n)`
    * and `kill(n)`.
    * */
  override def apply(n: StoredNode, x: mutable.Set[Definition]): mutable.Set[Definition] = {
    println(method.name)
    gen(n).union(x.diff(kill(n)))
  }

  /**
    * Initialize the map `gen`, a map that contains generated
    * definitions for each flow graph node.
    * */
  def initGen(method: Method): Map[StoredNode, mutable.Set[Definition]] = {

    val defsForParams = method.parameter.l.map { param =>
      param -> mutable.Set(Definition.fromNode(param.asInstanceOf[StoredNode]))
    }

    // We filter out field accesses to ensure that they propagate
    // taint unharmed.

    val defsForCalls = method.call
      .filterNot(x => isGenericMemberAccessName(x.name))
      .l
      .map { call =>
        call -> {
          val retVal = mutable.Set(call)
          val args = call.argument.filter(hasValidGenType)
          (retVal ++ args)
            .map(x => Definition.fromNode(x.asInstanceOf[StoredNode]))
        }
      }
    (defsForParams ++ defsForCalls).toMap
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
  private def initKill(method: Method, gen: Map[StoredNode, Set[Definition]]): Map[StoredNode, Set[Definition]] = {

    // We filter out field accesses to ensure that they propagate
    // taint unharmed.

    val allIdentifiers = method.ast.isIdentifier.l
      .map { x =>
        (x.name, x)
      }
      .groupBy(_._1)
      .map { case (k, v) => k -> v.map(_._2) }
      .withDefaultValue(List.empty[Identifier])

    val allCalls = method.call.l
      .map { x =>
        (x.code, x)
      }
      .groupBy(_._1)
      .map { case (k, v) => k -> v.map(_._2) }
      .withDefaultValue(List.empty[Call])

    method.call
      .filterNot(x => isGenericMemberAccessName(x.name))
      .map { call =>
        call -> killsForGens(gen(call), allIdentifiers, allCalls)
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
  private def killsForGens(genOfCall: Set[Definition],
                           allIdentifiers: Map[String, List[Identifier]],
                           allCalls: Map[String, List[Call]]): Set[Definition] = {
    genOfCall.flatMap { definition =>
      definitionsOfSameVariable(definition, allIdentifiers, allCalls)
    }
  }

  private def definitionsOfSameVariable(definition: Definition,
                                        allIdentifiers: Map[String, List[Identifier]],
                                        allCalls: Map[String, List[Call]]): Set[Definition] = {
    val definedNodes = definition.node match {
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
    definedNodes.map(Definition.fromNode).toSet
  }

}

class ReachingDefInit(gen: Map[StoredNode, mutable.Set[Definition]]) extends InOutInit[mutable.Set[Definition]] {
  override def initIn: Map[StoredNode, mutable.Set[Definition]] =
    Map
      .empty[StoredNode, mutable.Set[Definition]]
      .withDefaultValue(mutable.Set.empty[Definition])

  override def initOut: Map[StoredNode, mutable.Set[Definition]] = gen
}
