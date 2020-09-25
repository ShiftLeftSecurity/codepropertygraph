package io.shiftleft.semanticcpg.accesspath

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, Operators, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.TrackingPoint
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointMethodsBase
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._

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

  private def toTrackedBaseAndAccessPathInternal(node: nodes.StoredNode): (TrackedBase, List[AccessElement]) = {
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
        memberAccess.name match {
          case Operators.memberAccess | Operators.indirectMemberAccess =>
            if (!hasWarnedDeprecations) {
              logger.warn(s"Deprecated Operator ${memberAccess.name} on ${memberAccess}")
              hasWarnedDeprecations = true
            }
            memberAccess
              .argumentOption(1)
              .map { toTrackedBaseAndAccessPathInternal }
              .map {
                case (base, tail) =>
                  (base,
                   memberAccess
                     .argumentOption(2)
                     .collect {
                       case lit: nodes.Literal   => ConstantAccess(lit.code)
                       case id: nodes.Identifier => ConstantAccess(id.name)
                     }
                     .getOrElse(VariableAccess) :: tail)
              }
              .getOrElse {
                logger.warn(s"Missing argument on call ${memberAccess}.")
                (TrackedUnknown, Nil)
              }

          case Operators.computedMemberAccess | Operators.indirectComputedMemberAccess =>
            if (!hasWarnedDeprecations) {
              logger.warn(s"Deprecated Operator ${memberAccess.name} on ${memberAccess}")
              hasWarnedDeprecations = true
            }
            memberAccess
              .argumentOption(1)
              .map { toTrackedBaseAndAccessPathInternal }
              .map {
                case (base, tail) =>
                  (base,
                   memberAccess
                     .argumentOption(2)
                     .collect {
                       case lit: nodes.Literal => ConstantAccess(lit.code)
                     }
                     .getOrElse(VariableAccess) :: tail)
              }
              .getOrElse {
                logger.warn(s"Missing argument on call ${memberAccess}.")
                (TrackedUnknown, Nil)
              }
          case Operators.indirection =>
            memberAccess
              .argumentOption(1)
              .map { toTrackedBaseAndAccessPathInternal }
              .map { case (base, tail) => (base, IndirectionAccess :: tail) }
              .getOrElse {
                logger.warn(s"Missing argument on call ${memberAccess}.")
                (TrackedUnknown, Nil)
              }
          case Operators.addressOf =>
            memberAccess
              .argumentOption(1)
              .map { toTrackedBaseAndAccessPathInternal }
              .map { case (base, tail) => (base, AddressOf :: tail) }
              .getOrElse {
                logger.warn(s"Missing argument on call ${memberAccess}.")
                (TrackedUnknown, Nil)
              }
          case Operators.fieldAccess | Operators.indexAccess =>
            memberAccess
              .argumentOption(1)
              .map { toTrackedBaseAndAccessPathInternal }
              .map { case (base, tail) => (base, extractAccessStringToken(memberAccess) :: tail) }
              .getOrElse {
                logger.warn(s"Missing argument on call ${memberAccess}.")
                (TrackedUnknown, Nil)
              }
          case Operators.indirectFieldAccess =>
            memberAccess
              .argumentOption(1)
              .map { toTrackedBaseAndAccessPathInternal }
              // we will reverse the list in the end
              .map { case (base, tail) => (base, extractAccessStringToken(memberAccess) :: IndirectionAccess :: tail) }
              .getOrElse {
                logger.warn(s"Missing argument on call ${memberAccess}.")
                (TrackedUnknown, Nil)
              }
          case Operators.indirectIndexAccess =>
            memberAccess
              .argumentOption(1)
              .map { toTrackedBaseAndAccessPathInternal }
              // we will reverse the list in the end
              .map { case (base, tail) => (base, IndirectionAccess :: extractAccessIntToken(memberAccess) :: tail) }
              .getOrElse {
                logger.warn(s"Missing argument on call ${memberAccess}.")
                (TrackedUnknown, Nil)
              }
          case Operators.pointerShift =>
            memberAccess
              .argumentOption(1)
              .map { toTrackedBaseAndAccessPathInternal }
              .map { case (base, tail) => (base, extractAccessIntToken(memberAccess) :: tail) }
              .getOrElse {
                logger.warn(s"Missing argument on call ${memberAccess}.")
                (TrackedUnknown, Nil)
              }
          case Operators.getElementPtr =>
            memberAccess
              .argumentOption(1)
              .map { toTrackedBaseAndAccessPathInternal }
              // we will reverse the list in the end
              .map {
                case (base, tail) =>
                  (base, AddressOf :: extractAccessStringToken(memberAccess) :: IndirectionAccess :: tail)
              }
              .getOrElse {
                logger.warn(s"Missing argument on call ${memberAccess}.")
                (TrackedUnknown, Nil)
              }
        }
    }
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
