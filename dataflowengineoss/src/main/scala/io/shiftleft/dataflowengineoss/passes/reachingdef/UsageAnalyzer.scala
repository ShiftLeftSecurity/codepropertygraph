package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.jdk.CollectionConverters._

class UsageAnalyzer(in: Map[nodes.StoredNode, Set[Definition]]) {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  private val allNodes = in.keys.toList

  val usedIncomingDefs: Map[StoredNode, Map[StoredNode, Set[Definition]]] = initUsedIncomingDefs()

  def initUsedIncomingDefs(): Map[StoredNode, Map[StoredNode, Set[Definition]]] = {
    allNodes.map { node =>
      node ->
        uses(node).map { use =>
          use -> in(node).filter { inElement =>
            declaration(use) == declaration(inElement.node)
          }
        }.toMap
    }.toMap
  }

  def uses(node: nodes.StoredNode): Set[nodes.StoredNode] = {
    val n = node match {
      case ret: nodes.Return =>
        ret.astChildren.map(_.asInstanceOf[nodes.StoredNode]).toSet()
      case call: nodes.Call =>
//        if (!hasAnnotation(call)) {
        call.start.argument.toSet.map(_.asInstanceOf[nodes.StoredNode])
//        } else {
//          val parameters = methodForCall(call).map(_.parameter.l).getOrElse(List())
//          call.start.argument
//            .where(arg => paramHasOutgoingPropagateEdge(arg, parameters))
//            .toSet
//            .map(_.asInstanceOf[nodes.StoredNode])
//        }
      case _ => Set()
    }
    n.filterNot(_.isInstanceOf[nodes.FieldIdentifier]).map(_.asInstanceOf[nodes.StoredNode])
  }

  private def hasAnnotation(call: nodes.Call): Boolean = {
    methodForCall(call).exists(method => method.parameter.l.exists(x => x._propagateOut().hasNext))
  }

  private def paramHasOutgoingPropagateEdge(arg: nodes.Expression, parameters: List[nodes.MethodParameterIn]) = {
    parameters.filter(_.order == arg.order).flatMap(_._propagateOut().asScala.toList).nonEmpty
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
