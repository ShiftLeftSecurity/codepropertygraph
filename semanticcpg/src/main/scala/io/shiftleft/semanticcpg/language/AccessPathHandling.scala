package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.{Operators, nodes}
import io.shiftleft.semanticcpg.accesspath.{
  AccessElement,
  AddressOf,
  ConstantAccess,
  IndirectionAccess,
  PointerShift,
  TrackedBase,
  TrackedFormalReturn,
  TrackedLiteral,
  TrackedMethodOrTypeRef,
  TrackedNamedVariable,
  TrackedUnknown,
  VariableAccess,
  VariablePointerShift
}
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters.IteratorHasAsScala

object AccessPathHandling {

  def leafToTrackedBaseAndAccessPathInternal(node: nodes.StoredNode): Option[(TrackedBase, List[AccessElement])] = {
    node match {
      case node: nodes.MethodParameterIn  => Some((TrackedNamedVariable(node.name), Nil))
      case node: nodes.MethodParameterOut => Some((TrackedNamedVariable(node.name), Nil))
      case node: nodes.Identifier         => Some((TrackedNamedVariable(node.name), Nil))
      case node: nodes.Literal            => Some((TrackedLiteral(node), Nil))
      case node: nodes.MethodRef          => Some((TrackedMethodOrTypeRef(node), Nil))
      case node: nodes.TypeRef            => Some((TrackedMethodOrTypeRef(node), Nil))
      case _: nodes.Return                => Some((TrackedFormalReturn, Nil))
      case _: nodes.MethodReturn          => Some((TrackedFormalReturn, Nil))
      case _: nodes.Unknown               => Some((TrackedUnknown, Nil))
      case _: nodes.ControlStructure      => Some((TrackedUnknown, Nil))
      // FieldIdentifiers are only fake arguments, hence should not be tracked
      case _: nodes.FieldIdentifier => Some((TrackedUnknown, Nil))
      case _                        => None
    }
  }

  private val logger = LoggerFactory.getLogger(getClass)
  private var hasWarnedDeprecations = false

  def memberAccessToPath(memberAccess: nodes.Call, tail: List[AccessElement]) = {
    memberAccess.name match {
      case Operators.memberAccess | Operators.indirectMemberAccess =>
        if (!hasWarnedDeprecations) {
          logger.info(s"Deprecated Operator ${memberAccess.name} on ${memberAccess}")
          hasWarnedDeprecations = true
        }
        memberAccess
          .argumentOption(2)
          .collect {
            case lit: nodes.Literal      => ConstantAccess(lit.code)
            case withName: nodes.HasName => ConstantAccess(withName.name)
          }
          .getOrElse(VariableAccess) :: tail

      case Operators.computedMemberAccess | Operators.indirectComputedMemberAccess =>
        if (!hasWarnedDeprecations) {
          logger.info(s"Deprecated Operator ${memberAccess.name} on ${memberAccess}")
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

  def lastExpressionInBlock(block: nodes.Block): Option[nodes.Expression] =
    block._astOut.asScala
      .collect {
        case node: nodes.Expression if !node.isInstanceOf[nodes.Local] && !node.isInstanceOf[nodes.Method] => node
      }
      .toVector
      .sortBy(_.order)
      .lastOption

}
