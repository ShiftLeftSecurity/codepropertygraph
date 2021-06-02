package io.shiftleft.dataflowengineoss.queryengine

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.accesspath.{
  AccessElement,
  AccessPath,
  Elements,
  TrackedBase,
  TrackedReturnValue,
  TrackedUnknown
}
import io.shiftleft.semanticcpg.language.{AccessPathHandling, toCallMethods}
import io.shiftleft.semanticcpg.utils.MemberAccess
import org.slf4j.LoggerFactory

object AccessPathUsage {

  private val logger = LoggerFactory.getLogger(getClass)

  def toTrackedBaseAndAccessPathSimple(node: nodes.StoredNode): (TrackedBase, AccessPath) = {
    val (base, revPath) = toTrackedBaseAndAccessPathInternal(node)
    (base, AccessPath.apply(Elements.normalized(revPath.reverse), Nil))
  }

  private def toTrackedBaseAndAccessPathInternal(node: nodes.StoredNode): (TrackedBase, List[AccessElement]) = {
    val result = AccessPathHandling.leafToTrackedBaseAndAccessPathInternal(node)
    if (result.isDefined) {
      result.get
    } else {
      node match {

        case block: nodes.Block =>
          AccessPathHandling
            .lastExpressionInBlock(block)
            .map { toTrackedBaseAndAccessPathInternal }
            .getOrElse((TrackedUnknown, Nil))
        case call: nodes.Call if !MemberAccess.isGenericMemberAccessName(call.name) => (TrackedReturnValue(call), Nil)

        case memberAccess: nodes.Call =>
          //assume: MemberAccess.isGenericMemberAccessName(call.name)
          //FIXME: elevate debug to warn once csharp2cpg has managed to migrate.
          val argOne = memberAccess.argumentOption(1)
          if (argOne.isEmpty) {
            logger.warn(s"Missing first argument on call ${memberAccess}.")
            return (TrackedUnknown, Nil)
          }
          val (base, tail) = toTrackedBaseAndAccessPathInternal(argOne.get)
          val path = AccessPathHandling.memberAccessToPath(memberAccess, tail)
          (base, path)
      }
    }
  }
}
