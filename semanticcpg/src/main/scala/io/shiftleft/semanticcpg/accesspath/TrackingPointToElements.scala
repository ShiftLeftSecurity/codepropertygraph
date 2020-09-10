package io.shiftleft.semanticcpg.accesspath

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, Operators, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.TrackingPoint
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointMethodsBase
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._

object TrackingPointToElements {

  def apply(node: nodes.TrackingPoint): Elements = {
    node match {
      case call: nodes.Call => convertCall(call)
      case block: nodes.Block =>
        val lastInBlock = TrackingPointMethodsBase.lastExpressionInBlock(block).get
        TrackingPointToElements.apply(lastInBlock)
      case _: nodes.MethodParameterIn  => Elements()
      case _: nodes.MethodParameterOut => Elements()
      case _: nodes.MethodReturn       => Elements()
      case _: nodes.ImplicitCall       => Elements()
      case _: nodes.Expression         => Elements()
      case _: nodes.TrackingPoint      => Elements()
    }
  }

  private def firstArgument(call: nodes.Call): TrackingPoint = {
    call
      .out(EdgeTypes.ARGUMENT)
      .asScala
      .find(_.property(NodeKeys.ARGUMENT_INDEX) == 1)
      .get
      .asInstanceOf[nodes.TrackingPoint]
  }

  private def convertCall(call: nodes.Call): Elements = {
    if (MemberAccess.isGenericMemberAccessName(call.name)) {
      val baseElements = TrackingPointToElements.apply(firstArgument(call))
      baseElements ++ MemberAccessToElement(call)
    } else {
      Elements()
    }
  }
}

private object MemberAccessToElement {

  private val logger = LoggerFactory.getLogger(getClass)

  def apply(memberAccess: nodes.Call): Elements = {
    memberAccess.name match {
      case Operators.memberAccess | Operators.indirectMemberAccess => {
        val memberOption = memberAccess
          .out(EdgeTypes.ARGUMENT)
          .asScala
          .find(_.property(NodeKeys.ARGUMENT_INDEX) == 2)
        Elements.normalized(List(ConstantAccess(memberOption.get.property(NodeKeys.NAME))))
      }
      case Operators.computedMemberAccess | Operators.indirectComputedMemberAccess => {
        val memberOption = memberAccess
          .out(EdgeTypes.ARGUMENT)
          .asScala
          .find(_.property(NodeKeys.ARGUMENT_INDEX) == 2)

        memberOption match {
          case Some(member) =>
            member match {
              case literal: nodes.Literal =>
                Elements.normalized(List(ConstantAccess(literal.code)))
              case _ =>
                Elements.normalized(List(VariableAccess))
            }
          case None =>
            logger.warn(
              s"Invalid AST: Found member access without second argument." +
                s" Member access CODE: ${memberAccess.code}" +
                s" In method ${ExpandTo.expressionToMethod(memberAccess).property(NodeKeys.FULL_NAME)}")
            Elements.normalized(List(VariableAccess))
        }
      }
      case Operators.indirection => Elements.normalized(IndirectionAccess)
      case Operators.addressOf   => Elements.normalized(AddressOf)
      case Operators.fieldAccess | Operators.indexAccess =>
        Elements.normalized(extractAccessStringToken(memberAccess))
      case Operators.indirectFieldAccess =>
        Elements.normalized(IndirectionAccess, extractAccessStringToken(memberAccess))
      case Operators.indirectIndexAccess =>
        Elements.normalized(extractAccessIntToken(memberAccess), IndirectionAccess)
      case Operators.pointerShift =>
        Elements.normalized(extractAccessIntToken(memberAccess))
      case Operators.getElementPtr =>
        Elements.normalized(IndirectionAccess, extractAccessStringToken(memberAccess), AddressOf)
    }
  }

  private def extractAccessStringToken(memberAccess: nodes.Call): AccessElement = {
    memberAccess
      .out(EdgeTypes.ARGUMENT)
      .asScala
      .find(_.property(NodeKeys.ARGUMENT_INDEX) == 2) match {
      case None => {
        logger.warn(
          s"Invalid AST: Found member access without second argument." +
            s" Member access CODE: ${memberAccess.code}" +
            s" In method ${ExpandTo.expressionToMethod(memberAccess).property(NodeKeys.FULL_NAME)}")
        VariableAccess
      }
      case Some(literal: nodes.Literal) => ConstantAccess(literal.code)
      case Some(fieldIdentifier: nodes.FieldIdentifier) =>
        ConstantAccess(fieldIdentifier.canonicalName)
      case _ => VariableAccess
    }
  }
  private def extractAccessIntToken(memberAccess: nodes.Call): AccessElement = {
    memberAccess
      .out(EdgeTypes.ARGUMENT)
      .asScala
      .find(_.property(NodeKeys.ARGUMENT_INDEX) == 2) match {
      case None => {
        logger.warn(
          s"Invalid AST: Found member access without second argument." +
            s" Member access CODE: ${memberAccess.code}" +
            s" In method ${ExpandTo.expressionToMethod(memberAccess).property(NodeKeys.FULL_NAME)}")
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



object TrackingPointToAccessPath {

  def apply(node: nodes.TrackingPoint, exclusions: List[Elements] = List()): AccessPath = {
    new AccessPath(TrackingPointToElements(node), exclusions)
  }

}
