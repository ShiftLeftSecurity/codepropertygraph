package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Call
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{EvalTypeAccessors, ModifierAccessors}

/**
  * A member variable of a class/type.
  * */
class Member[A <: nodes.Member](raw: GremlinScala[A])
    extends NodeSteps[A](raw)
    with EvalTypeAccessors[A]
    with ModifierAccessors[A] {

  /**
    * The type declaration this member is defined in
    * */
  def typeDecl: TypeDecl[nodes.TypeDecl] =
    new TypeDecl(raw.in(EdgeTypes.AST).cast[nodes.TypeDecl])

  /**
    * Places where
    * */
  def ref: Call[nodes.Call] =
    new Call(raw.in(EdgeTypes.REF).cast[nodes.Call])

  /**
    * Public members
    * */
  def isPublic: Member[A] =
    hasModifier(ModifierTypes.PUBLIC)

  /**
    * Private members
    * */
  def isPrivate: Member[A] =
    hasModifier(ModifierTypes.PRIVATE)

  /**
    * Protected members
    * */
  def isProtected: Member[A] =
    hasModifier(ModifierTypes.PROTECTED)

  /**
    * Static members
    * */
  def isStatic: Member[A] =
    hasModifier(ModifierTypes.STATIC)

  /**
    * Traverse to member type
    * */
  def typ: Type[nodes.Type] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
