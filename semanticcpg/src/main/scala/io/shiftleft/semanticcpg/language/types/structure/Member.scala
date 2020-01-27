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
class Member(raw: GremlinScala[nodes.Member])
    extends NodeSteps[nodes.Member](raw)
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
  def ref: NodeSteps[nodes.Call] =
    new NodeSteps(raw.in(EdgeTypes.REF).cast[nodes.Call])

  /**
    * Public members
    * */
  def isPublic: Member =
    hasModifier(ModifierTypes.PUBLIC)

  /**
    * Private members
    * */
  def isPrivate: Member =
    hasModifier(ModifierTypes.PRIVATE)

  /**
    * Protected members
    * */
  def isProtected: Member =
    hasModifier(ModifierTypes.PROTECTED)

  /**
    * Static members
    * */
  def isStatic: Member =
    hasModifier(ModifierTypes.STATIC)

  /**
    * Traverse to member type
    * */
  def typ: Type =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
