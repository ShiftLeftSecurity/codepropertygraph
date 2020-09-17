package io.shiftleft.dataflowengineoss.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.TrackingPoint
import io.shiftleft.dataflowengineoss.queryengine.{Engine, PathElement}
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import io.shiftleft.semanticcpg.language.{NoResolve, toExpression}
import overflowdb.traversal.{NodeOps, Traversal}

class ExpressionMethods[NodeType <: nodes.Expression](val node: NodeType) extends AnyVal {

  def isUsed(implicit semantics: Semantics): Boolean = {
    val s = semanticsForCallByArg
    s.isEmpty || s.exists(_.mappings.exists { case (srcIndex, _) => srcIndex == node.order })
  }

  def isDefined(implicit semantics: Semantics): Boolean = {
    val s = semanticsForCallByArg
    s.isEmpty || s.exists(_.mappings.exists { case (_, dstIndex) => dstIndex == node.order })
  }

  def semanticsForCallByArg(implicit semantics: Semantics): List[FlowSemantic] = {
    argToMethods(node).flatMap { method =>
      semantics.forMethod(method.fullName)
    }
  }

  private def argToMethods(arg: nodes.Expression): List[nodes.Method] = {
    arg.start.inCall.l.flatMap { call =>
      NoResolve.getCalledMethods(call).toList
    }
  }

  def ddgIn(path: List[PathElement] = List())(implicit semantics: Semantics): Traversal[TrackingPoint] = {
    Engine.expandIn(node, path).map(_.node).to(Traversal)
  }

}
