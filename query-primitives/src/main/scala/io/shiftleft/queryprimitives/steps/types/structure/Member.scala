package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.expressions.Call
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.{
  DeclarationBase,
  Modifier
}
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{
  CodeAccessors,
  EvalTypeAccessors,
  NameAccessors
}
import shapeless.HList

/**
  * A member variable of a class/type.
  * */
class Member[Labels <: HList](raw: GremlinScala.Aux[nodes.Member, Labels])
    extends NodeSteps[nodes.Member, Labels](raw)
    with DeclarationBase[nodes.Member, Labels]
    with CodeAccessors[nodes.Member, Labels]
    with NameAccessors[nodes.Member, Labels]
    with EvalTypeAccessors[nodes.Member, Labels] {

  /**
    * The type declaration this member is defined in
    * */
  def typeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](raw.in(EdgeTypes.AST).cast[nodes.TypeDecl])

  /**
    * Places where
    * */
  def ref: Call[Labels] =
    new Call[Labels](raw.in(EdgeTypes.REF).cast[nodes.Call])

  /**
    * Public members
    * */
  def isPublic: Member[Labels] =
    new Member[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PUBLIC)))

  /**
    * Private members
    * */
  def isPrivate: Member[Labels] =
    new Member[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PRIVATE)))

  /**
    * Protected members
    * */
  def isProtected: Member[Labels] =
    new Member[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PROTECTED)))

  /**
    * Static members
    * */
  def isStatic: Member[Labels] =
    new Member[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.STATIC)))

  /**
    * Traverse to method modifiers, e.g., "static", "public".
    * */
  def modifier: Modifier[Labels] =
    new Modifier[Labels](
      raw.out
        .hasLabel(NodeTypes.MODIFIER)
        .cast[nodes.Modifier]
    )

  /**
    * Traverse to member type
    * */
  def typ: Type[Labels] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
