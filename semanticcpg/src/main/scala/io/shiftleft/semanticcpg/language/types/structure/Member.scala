package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._
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
  def typeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(raw.in(EdgeTypes.AST).cast[nodes.TypeDecl])

  /**
    * Places where
    * */
  def ref: NodeSteps[nodes.Call] =
    new NodeSteps(raw.in(EdgeTypes.REF).cast[nodes.Call])

  /**
    * Public members
    * */
  def isPublic: NodeSteps[A] =
    hasModifier(ModifierTypes.PUBLIC)

  /**
    * Private members
    * */
  def isPrivate: NodeSteps[A] =
    hasModifier(ModifierTypes.PRIVATE)

  /**
    * Protected members
    * */
  def isProtected: NodeSteps[A] =
    hasModifier(ModifierTypes.PROTECTED)

  /**
    * Static members
    * */
  def isStatic: NodeSteps[A] =
    hasModifier(ModifierTypes.STATIC)

  /**
    * Traverse to member type
    * */
  def typ: NodeSteps[nodes.Type] =
    new NodeSteps(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
