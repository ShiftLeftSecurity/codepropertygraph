package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.{Operators, nodes}
import io.shiftleft.semanticcpg.accesspath.{
  AccessElement,
  AccessPath,
  AddressOf,
  ConstantAccess,
  Elements,
  IndirectionAccess,
  PointerShift,
  TrackedBase,
  TrackedFormalReturn,
  TrackedLiteral,
  TrackedMethodOrTypeRef,
  TrackedNamedVariable,
  TrackedReturnValue,
  TrackedUnknown,
  VariableAccess,
  VariablePointerShift
}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._

/* The closed source tracker sometimes needs to opt out of the default handling of accessPaths.
 * Instead of having two versions of the toTrackingPoint / toAccessPath functions, we define an interface to opt out.
 *
 * The open-sourced parts of the code currently have nothing that implements FakeTrackingPoint.
 * */
trait FakeTrackingPoint {
  def trackedBaseAndAccessPathOverride: (TrackedBase, AccessPath)
  def trackedCfgNodeOverride: nodes.CfgNode
}

// Utility methods for dealing with tracking points
object TrackingPointMethodsBase {
  private val logger = LoggerFactory.getLogger(getClass)
  private var hasWarnedDeprecations = false
  //if this experimental flag is set, then we treat <operator>.cast like memberAccess
  //this has relevant effects for e.g. taintMe(x.asInstanceOf[typ]), i.e. aliasing.
  var experimentalCastAsMemberAccess = false

  //we don't want to expose this API everywhere, only when explicitly imported
  implicit class ImplicitsAPI(val node: nodes.TrackingPoint) extends AnyVal {
    def trackedBaseAndAccessPath: (TrackedBase, AccessPath) = toTrackedBaseAndAccessPath(node)
    def trackedBaseAndElements: (TrackedBase, Elements) = {
      val (base, path) = trackedBaseAndAccessPath
      (base, path.elements)
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
  def toCfgNode(node: nodes.TrackingPointBase): nodes.CfgNode = {
    node match {
      case ftp: FakeTrackingPoint => ftp.trackedCfgNodeOverride
      case _                      => toCfgNodeInternal(node, _.parentExpression.get)
    }
  }
  @scala.annotation.tailrec
  private def toCfgNodeInternal(node: nodes.TrackingPointBase,
                                parentExpansion: nodes.Expression => nodes.Expression): nodes.CfgNode = {

    node match {
      case node: nodes.Identifier => parentExpansion(node)
      case node: nodes.MethodRef  => parentExpansion(node)
      case node: nodes.TypeRef    => parentExpansion(node)
      case node: nodes.Literal    => parentExpansion(node)

      case node: nodes.MethodParameterIn => node.method

      case node: nodes.MethodParameterOut =>
        node.method.methodReturn

      case node: nodes.Call if MemberAccess.isGenericMemberAccessName(node.name) =>
        parentExpansion(node)

      case node: nodes.Call         => node
      case node: nodes.ImplicitCall => node
      case node: nodes.MethodReturn => node
      case block: nodes.Block       =>
        // Just taking the lastExpressionInBlock is not quite correct because a BLOCK could have
        // different return expressions. So we would need to expand via CFG.
        // But currently the frontends do not even put the BLOCK into the CFG so this is the best
        // we can do.
        toCfgNodeInternal(TrackingPointMethodsBase.lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }

  def toTrackedBaseAndAccessPath(node: nodes.StoredNode): (TrackedBase, AccessPath) = {
    // assume: node isa nodes.TrackingPoint
    node match {
      case ftp: FakeTrackingPoint =>
        ftp.trackedBaseAndAccessPathOverride
      case _ =>
        val (base, revPath) = toTrackedBaseAndAccessPathInternal(node)
        (base, AccessPath.apply(Elements.normalized(revPath.reverse), Nil))
    }
  }

  def toTrackedBase(node: nodes.StoredNode): TrackedBase = toTrackedBaseAndAccessPath(node)._1
  def toTrackedAccessPath(node: nodes.StoredNode): AccessPath = toTrackedBaseAndAccessPath(node)._2

  private def toTrackedBaseAndAccessPathInternal(node: nodes.StoredNode): (TrackedBase, List[AccessElement]) =
    node match {
      case node: nodes.MethodParameterIn  => (TrackedNamedVariable(node.name), Nil)
      case node: nodes.MethodParameterOut => (TrackedNamedVariable(node.name), Nil)
      case node: nodes.ImplicitCall       => (TrackedReturnValue(node), Nil)
      case node: nodes.Identifier if node._callViaMustAliasOut.isDefined =>
        toTrackedBaseAndAccessPathInternal(node._callViaMustAliasOut.get)
      case node: nodes.Identifier => (TrackedNamedVariable(node.name), Nil)
      case node: nodes.Literal    => (TrackedLiteral(node), Nil)
      case node: nodes.MethodRef  => (TrackedMethodOrTypeRef(node), Nil)
      case node: nodes.TypeRef    => (TrackedMethodOrTypeRef(node), Nil)
      case _: nodes.Return        => (TrackedFormalReturn, Nil)
      case _: nodes.MethodReturn  => (TrackedFormalReturn, Nil)
      case _: nodes.Unknown       => (TrackedUnknown, Nil)
      // FieldIdentifiers are only fake arguments, hence should not be tracked
      case _: nodes.FieldIdentifier => (TrackedUnknown, Nil)
      case block: nodes.Block =>
        TrackingPointMethodsBase
          .lastExpressionInBlock(block)
          .map { toTrackedBaseAndAccessPathInternal }
          .getOrElse((TrackedUnknown, Nil))
      case call: nodes.Call if !MemberAccess.isGenericMemberAccessName(call.name) => (TrackedReturnValue(call), Nil)
      case cast: nodes.Call if experimentalCastAsMemberAccess && cast.name == Operators.cast =>
        cast.argumentOption(2) match {
          case None =>
            logger.warn(s"Missing second argument on call ${cast}.")
            (TrackedUnknown, Nil)
          case Some(arg) => toTrackedBaseAndAccessPathInternal(arg)
        }
      case memberAccess: nodes.Call =>
        //assume: MemberAccess.isGenericMemberAccessName(call.name)
        //FIXME: elevate debug to warn once csharp2cpg has managed to migrate.
        val argOne = memberAccess.argumentOption(1)
        if (argOne.isEmpty) {
          logger.warn(s"Missing first argument on call ${memberAccess}.")
          return (TrackedUnknown, Nil)
        }
        val (base, tail) = toTrackedBaseAndAccessPathInternal(argOne.get)
        val path = memberAccess.name match {
          case Operators.memberAccess | Operators.indirectMemberAccess =>
            if (!hasWarnedDeprecations) {
              logger.info(s"deprecated Operator ${memberAccess.name} on ${memberAccess}")
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
              logger.info(s"deprecated Operator ${memberAccess.name} on ${memberAccess}")
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

object TrackingPointToCfgNode {
  def apply(node: nodes.TrackingPointBase): nodes.CfgNode = {
    node match {
      case ftp: FakeTrackingPoint => ftp.trackedCfgNodeOverride
      case _                      => applyInternal(node, _.parentExpression.get)
    }

  }

  @scala.annotation.tailrec
  private def applyInternal(node: nodes.TrackingPointBase,
                            parentExpansion: nodes.Expression => nodes.Expression): nodes.CfgNode = {

    node match {
      case node: nodes.Identifier => parentExpansion(node)
      case node: nodes.MethodRef  => parentExpansion(node)
      case node: nodes.TypeRef    => parentExpansion(node)
      case node: nodes.Literal    => parentExpansion(node)

      case node: nodes.MethodParameterIn => node.method

      case node: nodes.MethodParameterOut =>
        node.method.methodReturn

      case node: nodes.Call if MemberAccess.isGenericMemberAccessName(node.name) =>
        parentExpansion(node)

      case node: nodes.Call         => node
      case node: nodes.ImplicitCall => node
      case node: nodes.MethodReturn => node
      case block: nodes.Block       =>
        // Just taking the lastExpressionInBlock is not quite correct because a BLOCK could have
        // different return expressions. So we would need to expand via CFG.
        // But currently the frontends do not even put the BLOCK into the CFG so this is the best
        // we can do.
        applyInternal(TrackingPointMethodsBase.lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }
}
