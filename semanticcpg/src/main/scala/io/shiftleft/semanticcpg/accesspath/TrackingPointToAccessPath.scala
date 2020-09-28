package io.shiftleft.semanticcpg.accesspath

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, Operators, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.TrackingPoint
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointMethodsBase
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._

/* The closed source tracker sometimes needs to opt out of the default handling of accessPaths.
 * Instead of having two versions of the toTrackingPoint / toAccessPath functions, we define an interface to opt out.
 *
 * The open-sourced parts of the code currently have nothing that implements FakeTrackingPoint.
 *
 * If AccessPathOption or trackedBaseOption are set, then we use them; otherwise, we use trackedNode and the usual
 * algorithm. trackedCfgNode is always used. If trackedBase and accessPath are both set, then trackedNode is permitted
 *  to be null.
 * */
trait FakeTrackingPoint {
  def trackedBaseOption: Option[TrackedBase]
  def accessPathOption: Option[AccessPath]
  def trackedNode: TrackingPoint
  def trackedCfgNode: nodes.CfgNode
}

object TrackingPointMethods {
  private val logger = LoggerFactory.getLogger(getClass)
  private var hasWarnedDeprecations = false

  def toTrackedBaseAndAccessPath(node: nodes.StoredNode): (TrackedBase, AccessPath) = {
    // assume: node isa nodes.TrackingPoint
    if (node.isInstanceOf[FakeTrackingPoint]) {
      val ftp = node.asInstanceOf[FakeTrackingPoint]
      if (ftp.trackedBaseOption.isDefined && ftp.accessPathOption.isDefined) {
        return (ftp.trackedBaseOption.get, ftp.accessPathOption.get)
      } else {
        val (base, path) = toTrackedBaseAndAccessPath(ftp.trackedNode)
        return (ftp.trackedBaseOption.getOrElse(base), ftp.accessPathOption.getOrElse(path))
      }
    }
    val (base, revPath) = toTrackedBaseAndAccessPathInternal(node)
    (base, AccessPath.apply(Elements.normalized(revPath.reverse), Nil))
  }

  def toTrackedBase(node: nodes.StoredNode): TrackedBase = toTrackedBaseAndAccessPath(node)._1
  def toTrackedAccessPath(node: nodes.StoredNode): AccessPath = toTrackedBaseAndAccessPath(node)._2

  private def toTrackedBaseAndAccessPathInternal(node: nodes.StoredNode): (TrackedBase, List[AccessElement]) =
    node match {
      case node: nodes.MethodParameterIn  => (TrackedNamedVariable(node.name), Nil)
      case node: nodes.MethodParameterOut => (TrackedNamedVariable(node.name), Nil)
      case node: nodes.ImplicitCall       => (TrackedReturnValue(node), Nil)
      case node: nodes.Identifier         => (TrackedNamedVariable(node.name), Nil)
      case node: nodes.Literal            => (TrackedLiteral(node), Nil)
      case node: nodes.MethodRef          => (TrackedMethodOrTypeRef(node), Nil)
      case node: nodes.TypeRef            => (TrackedMethodOrTypeRef(node), Nil)
      case _: nodes.Return                => (TrackedFormalReturn, Nil)
      case _: nodes.MethodReturn          => (TrackedFormalReturn, Nil)
      case _: nodes.Unknown               => (TrackedUnknown, Nil)
      // FieldIdentifiers are only fake arguments, hence should not be tracked
      case _: nodes.FieldIdentifier => (TrackedUnknown, Nil)
      case block: nodes.Block =>
        TrackingPointMethodsBase
          .lastExpressionInBlock(block)
          .map { toTrackedBaseAndAccessPathInternal }
          .getOrElse((TrackedUnknown, Nil))
      case call: nodes.Call if !MemberAccess.isGenericMemberAccessName(call.name) => (TrackedReturnValue(call), Nil)

      case memberAccess: nodes.Call =>
        //assume: MemberAccess.isGenericMemberAccessName(call.name)
        val argOne = memberAccess.argumentOption(1)
        if (argOne.isEmpty) {
          logger.warn(s"Missing first argument on call ${memberAccess}.")
          return (TrackedUnknown, Nil)
        }
        val (base, tail) = toTrackedBaseAndAccessPathInternal(argOne.get)
        val path = memberAccess.name match {
          case Operators.memberAccess | Operators.indirectMemberAccess =>
            if (!hasWarnedDeprecations) {
              logger.warn(s"Deprecated Operator ${memberAccess.name} on ${memberAccess}")
              hasWarnedDeprecations = true
            }
            memberAccess
              .argumentOption(2)
              .collect {
                case lit: nodes.Literal   => ConstantAccess(lit.code)
                case id: nodes.Identifier => ConstantAccess(id.name)
              }
              .getOrElse(VariableAccess) :: tail

          case Operators.computedMemberAccess | Operators.indirectComputedMemberAccess =>
            if (!hasWarnedDeprecations) {
              logger.warn(s"Deprecated Operator ${memberAccess.name} on ${memberAccess}")
              hasWarnedDeprecations = true
            }
            memberAccess
              .argumentOption(2)
              .collect {
                case lit: nodes.Literal => ConstantAccess(lit.code)
              }
              .getOrElse(VariableAccess) :: tail
          case Operators.indirection =>
            IndirectionAccess :: tail
          case Operators.addressOf =>
            AddressOf :: tail
          case Operators.fieldAccess | Operators.indexAccess =>
            extractAccessStringToken(memberAccess) :: tail
          case Operators.indirectFieldAccess =>
            // we will reverse the list in the end
            extractAccessStringToken(memberAccess) :: IndirectionAccess :: tail
          case Operators.indirectIndexAccess =>
            // we will reverse the list in the end
            IndirectionAccess :: extractAccessIntToken(memberAccess) :: tail
          case Operators.pointerShift =>
            extractAccessIntToken(memberAccess) :: tail
          case Operators.getElementPtr =>
            // we will reverse the list in the end
            AddressOf :: extractAccessStringToken(memberAccess) :: IndirectionAccess :: tail
        }
        (base, path)
    }

  private def extractAccessStringToken(memberAccess: nodes.Call): AccessElement = {
    memberAccess.argumentOption(2) match {
      case None => {
        logger.warn(
          s"Invalid AST: Found member access without second argument." +
            s" Member access CODE: ${memberAccess.code}" +
            s" In method ${memberAccess.method.fullName}")
        VariableAccess
      }
      case Some(literal: nodes.Literal) => ConstantAccess(literal.code)
      case Some(fieldIdentifier: nodes.FieldIdentifier) =>
        ConstantAccess(fieldIdentifier.canonicalName)
      case _ => VariableAccess
    }
  }
  private def extractAccessIntToken(memberAccess: nodes.Call): AccessElement = {
    memberAccess.argumentOption(2) match {
      case None => {
        logger.warn(
          s"Invalid AST: Found member access without second argument." +
            s" Member access CODE: ${memberAccess.code}" +
            s" In method ${memberAccess.method.fullName}")
        VariablePointerShift
      }
      case Some(literal: nodes.Literal) =>
        literal.code.toIntOption.map(PointerShift).getOrElse(VariablePointerShift)
      case Some(fieldIdentifier: nodes.FieldIdentifier) =>
        fieldIdentifier.canonicalName.toIntOption
          .map(PointerShift)
          .getOrElse(VariablePointerShift)
      case _ => VariablePointerShift
    }
  }

}
