package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Call
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.{DeclarationBase, Modifier}
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{CodeAccessors, EvalTypeAccessors, NameAccessors}

/**
  * A member variable of a class/type.
  * */
class Member(raw: GremlinScala[nodes.Member])
    extends NodeSteps[nodes.Member](raw)
    with DeclarationBase[nodes.Member]
    with CodeAccessors[nodes.Member]
    with NameAccessors[nodes.Member]
    with EvalTypeAccessors[nodes.Member] {

  /**
    * The type declaration this member is defined in
    * */
  def typeDecl: TypeDecl =
    new TypeDecl(raw.in(EdgeTypes.AST).cast[nodes.TypeDecl])

  /**
    * Places where
    * */
  def ref: Call =
    new Call(raw.in(EdgeTypes.REF).cast[nodes.Call])

  /**
    * Public members
    * */
  def isPublic: Member =
    new Member(raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PUBLIC)))

  /**
    * Private members
    * */
  def isPrivate: Member =
    new Member(raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PRIVATE)))

  /**
    * Protected members
    * */
  def isProtected: Member =
    new Member(raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PROTECTED)))

  /**
    * Static members
    * */
  def isStatic: Member =
    new Member(raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.STATIC)))

  /**
    * Traverse to method modifiers, e.g., "static", "public".
    * */
  def modifier: Modifier =
    new Modifier(
      raw.out
        .hasLabel(NodeTypes.MODIFIER)
        .cast[nodes.Modifier]
    )

  /**
    * Traverse to member type
    * */
  def typ: Type =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
