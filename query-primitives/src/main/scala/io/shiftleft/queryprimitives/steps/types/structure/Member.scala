package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.expressions.Call
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.DeclarationBase
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{CodeAccessors, EvalTypeAccessors, NameAccessors}
import shapeless.HList

/**
  * A member variable of a class/type.
  * */
class Member[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Member, Labels](raw)
    with DeclarationBase[nodes.Member, Labels]
    with CodeAccessors[nodes.Member, Labels]
    with NameAccessors[nodes.Member, Labels]
    with EvalTypeAccessors[nodes.Member, Labels] {
  override val converter = Converter.forDomainNode[nodes.Member]

  /**
    * The type declaration this member is defined in
    * */
  def typeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](raw.in(EdgeTypes.AST))

  /**
    * Places where
    * */
  def ref: Call[Labels] =
    new Call[Labels](raw.in(EdgeTypes.REF))

  /**
    * Public members
    * */
  def isPublic: Member[Labels] =
    new Member[Labels](
      raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PUBLIC)))

  /**
    * Private members
    * */
  def isPrivate: Member[Labels] =
    new Member[Labels](
      raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PRIVATE)))

  /**
    * Protected members
    * */
  def isProtected: Member[Labels] =
    new Member[Labels](
      raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PROTECTED)))

  /**
    * Static members
    * */
  def isStatic: Member[Labels] =
    new Member[Labels](
      raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.STATIC)))
}
