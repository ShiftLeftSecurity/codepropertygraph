package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Call
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.DeclarationBase
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{
  CodeAccessors,
  EvalTypeAccessors,
  ModifierAccessors,
  NameAccessors
}

/**
  * A member variable of a class/type.
  * */
class Member(raw: GremlinScala[nodes.Member])
    extends NodeSteps[nodes.Member](raw)
    with DeclarationBase[nodes.Member]
    with CodeAccessors[nodes.Member]
    with NameAccessors[nodes.Member]
    with EvalTypeAccessors[nodes.Member]
    with ModifierAccessors[nodes.Member] {

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
    isMemberWithModifier(ModifierTypes.PUBLIC)

  /**
    * Private members
    * */
  def isPrivate: Member =
    isMemberWithModifier(ModifierTypes.PRIVATE)

  /**
    * Protected members
    * */
  def isProtected: Member =
    isMemberWithModifier(ModifierTypes.PROTECTED)

  /**
    * Static members
    * */
  def isStatic: Member =
    isMemberWithModifier(ModifierTypes.STATIC)

  def isMemberWithModifier(modifier: String): Member =
    new Member(raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> modifier)))

  /**
    * Traverse to member type
    * */
  def typ: Type =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
