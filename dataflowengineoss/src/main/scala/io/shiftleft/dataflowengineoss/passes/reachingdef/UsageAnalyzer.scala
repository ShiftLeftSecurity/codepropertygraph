package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.generated.{Operators, nodes}
import io.shiftleft.semanticcpg.accesspath.MatchResult
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointMethodsBase.toTrackedBaseAndAccessPathSimple

/**
  * Upon calculating reaching definitions, we find ourselves with
  * a set of incoming definitions `in(n)` for each node `n` of the
  * flow graph. This component determines those of the incoming
  * definitions that are relevant as the value they define is
  * actually used by `n`.
  * */
class UsageAnalyzer(in: Map[nodes.StoredNode, Set[Definition]]) {

  private val allNodes = in.keys.toList
  val usedIncomingDefs: Map[StoredNode, Map[StoredNode, Set[Definition]]] = initUsedIncomingDefs()

  def initUsedIncomingDefs(): Map[StoredNode, Map[StoredNode, Set[Definition]]] = {
    allNodes.map { node =>
      node -> usedIncomingDefsForNode(node)
    }.toMap
  }

  private def usedIncomingDefsForNode(node: nodes.StoredNode): Map[nodes.StoredNode, Set[Definition]] = {
    uses(node).map { use =>
      use -> in(node).filter { inElement =>
        sameVariable(use, inElement.node) || isContainer(use, inElement.node) || isPart(use, inElement.node) || isAlias(
          use,
          inElement.node)
      }
    }.toMap
  }

  /**
    * Determine whether the node `use` describes a container for `inElement`, e.g.,
    * use = `ptr` while inElement = `ptr->foo`.
    * */
  private def isContainer(use: nodes.Expression, inElement: nodes.StoredNode): Boolean = {
    inElement match {
      case call: nodes.Call if Set(Operators.fieldAccess, Operators.indirectIndexAccess).contains(call.name) =>
        call.argument.headOption.exists { base =>
          base.code == use.code
        }
      case _ => false
    }
  }

  /**
    * Determine whether `use` is a part of `inElement`, e.g.,
    * use = `argv[0]` while inElement = `argv`
    * */
  private def isPart(use: nodes.Expression, inElement: nodes.StoredNode): Boolean = {
    use match {
      case call: nodes.Call if Set(Operators.indirectIndexAccess, Operators.fieldAccess).contains(call.name) =>
        inElement match {
          case param: nodes.MethodParameterIn =>
            call.argument.headOption.exists { base =>
              base.code == param.name
            }
          case identifier: nodes.Identifier =>
            call.argument.headOption.exists { base =>
              base.code == identifier.name
            }
          case _ => false
        }
      case _ => false
    }
  }

  private def isAlias(use: nodes.Expression, inElement: nodes.StoredNode): Boolean = {
    use match {
      case useCall: nodes.Call =>
        inElement match {
          case inCall: nodes.Call =>
            val (useBase, useAccessPath) = toTrackedBaseAndAccessPathSimple(useCall)
            val (inBase, inAccessPath) = toTrackedBaseAndAccessPathSimple(inCall)
            useBase == inBase && useAccessPath.matchAndDiff(inAccessPath.elements)._1 == MatchResult.EXACT_MATCH
          case _ => false
        }
      case _ => false
    }
  }

  def uses(node: nodes.StoredNode): Set[nodes.Expression] = {
    val n: Set[nodes.Expression] = node match {
      case ret: nodes.Return =>
        ret.astChildren.collect { case x: nodes.Expression => x }.toSet
      case call: nodes.Call =>
        call.argument.toSet
      case _ => Set()
    }
    n.filterNot(_.isInstanceOf[nodes.FieldIdentifier])
  }

  /**
    * Compares arguments of calls with incoming definitions
    * to see if they refer to the same variable
    * */
  def sameVariable(use: nodes.Expression, incoming: StoredNode): Boolean = {
    incoming match {
      case param: nodes.MethodParameterIn =>
        use.code == param.name
      case call: nodes.Call =>
        use.code == call.code
      case identifier: nodes.Identifier => use.code == identifier.code
      case _                            => false
    }
  }

}
