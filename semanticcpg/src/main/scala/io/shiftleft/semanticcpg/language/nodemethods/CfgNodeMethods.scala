package io.shiftleft.semanticcpg.language.nodemethods

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
  TrackedReturnValue,
  TrackedUnknown,
  VariableAccess,
  VariablePointerShift
}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
import org.slf4j.LoggerFactory
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class CfgNodeMethods(val node: nodes.CfgNode) extends AnyVal {

  /**
    * Textual representation of CFG node
    * */
  def repr: String =
    node match {
      case method: nodes.MethodBase             => method.name
      case methodReturn: nodes.MethodReturnBase => methodReturn.code
      case expr: nodes.Expression               => expr.code
      case call: nodes.ImplicitCallBase         => call.code
    }

  /**
    * Recursively determine all nodes on which this
    * CFG node is control-dependent.
    * */
  def controlledBy: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._cdgIn.asScala
    }
  }

  /**
    * Recursively determine all nodes which this
    * CFG node controls
    * */
  def controls: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._cdgOut.asScala
    }
  }

  /**
    * Recursively determine all nodes by which
    * this node is dominated
    * */
  def dominatedBy: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._dominateIn.asScala
    }
  }

  /**
    * Recursively determine all nodes which
    * are dominated by this node
    * */
  def dominates: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._dominateOut.asScala
    }
  }

  /**
    * Recursively determine all nodes by which
    * this node is post dominated
    * */
  def postDominatedBy: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._postDominateIn.asScala
    }
  }

  /**
    * Recursively determine all nodes which
    * are post dominated by this node
    * */
  def postDominates: Traversal[nodes.CfgNode] = {
    expandExhaustively { v =>
      v._postDominateOut.asScala
    }
  }

  private def expandExhaustively(expand: nodes.CfgNode => Iterator[nodes.StoredNode]): Traversal[nodes.CfgNode] = {
    var controllingNodes = List.empty[nodes.CfgNode]
    var visited = Set.empty + node
    var worklist = node :: Nil

    while (worklist.nonEmpty) {
      val vertex = worklist.head
      worklist = worklist.tail

      expand(vertex).foreach {
        case controllingNode: nodes.CfgNode =>
          if (!visited.contains(controllingNode)) {
            visited += controllingNode
            controllingNodes = controllingNode :: controllingNodes
            worklist = controllingNode :: worklist
          }
      }
    }
    controllingNodes
  }

  def statement: nodes.CfgNode =
    statementInternal(node, _.parentExpression.get)

  @scala.annotation.tailrec
  private def statementInternal(node: nodes.CfgNode,
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
        statementInternal(lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }

  private def lastExpressionInBlock(block: nodes.Block): Option[nodes.Expression] =
    block._astOut.asScala
      .collect {
        case node: nodes.Expression if !node.isInstanceOf[nodes.Local] && !node.isInstanceOf[nodes.Method] => node
      }
      .toVector
      .sortBy(_.order)
      .lastOption

  import CfgNodeMethods._

  // TODO this API is not amazing

  def trackedBaseAccessPath(
      recurseWith: nodes.CfgNode => (TrackedBase, List[AccessElement])): (TrackedBase, List[AccessElement]) = {
    node match {
      case block: nodes.Block =>
        TrackingPointMethodsBase
          .lastExpressionInBlock(block)
          .map { recurseWith }
          .getOrElse((TrackedUnknown, Nil))

      case call: nodes.Call               => toTrackedBaseAndAccessPathInternalForCall(call, recurseWith)
      case node: nodes.MethodParameterIn  => (TrackedNamedVariable(node.name), Nil)
      case node: nodes.MethodParameterOut => (TrackedNamedVariable(node.name), Nil)
      case node: nodes.Identifier         => (TrackedNamedVariable(node.name), Nil)
      case node: nodes.Literal            => (TrackedLiteral(node), Nil)
      case node: nodes.MethodRef          => (TrackedMethodOrTypeRef(node), Nil)
      case node: nodes.TypeRef            => (TrackedMethodOrTypeRef(node), Nil)
      case _: nodes.Return                => (TrackedFormalReturn, Nil)
      case _: nodes.MethodReturn          => (TrackedFormalReturn, Nil)
      case _: nodes.Unknown               => (TrackedUnknown, Nil)
      case _: nodes.ControlStructure      => (TrackedUnknown, Nil)
      // FieldIdentifiers are only fake arguments, hence should not be tracked
      case _: nodes.FieldIdentifier => (TrackedUnknown, Nil)
    }
  }

  private def toTrackedBaseAndAccessPathInternalForCall(
      node: nodes.Call,
      recurseWith: nodes.CfgNode => (TrackedBase, List[AccessElement])): (TrackedBase, List[AccessElement]) = {
    node match {
      case call: nodes.Call if !MemberAccess.isGenericMemberAccessName(call.name) => (TrackedReturnValue(call), Nil)
      case memberAccess: nodes.Call                                               =>
        //assume: MemberAccess.isGenericMemberAccessName(call.name)
        //FIXME: elevate debug to warn once csharp2cpg has managed to migrate.
        val argOne = memberAccess.argumentOption(1)
        if (argOne.isEmpty) {
          logger.warn(s"Missing first argument on call ${memberAccess}.")
          return (TrackedUnknown, Nil)
        }
        val (base, tail) = recurseWith(argOne.get)
        val path = memberAccess.name match {
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
        (base, path)
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

object CfgNodeMethods {
  private var hasWarnedDeprecations = false
  private val logger = LoggerFactory.getLogger(getClass)
}
