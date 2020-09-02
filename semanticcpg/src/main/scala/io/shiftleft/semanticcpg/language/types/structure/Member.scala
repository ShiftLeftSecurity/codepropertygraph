package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated._
import overflowdb.traversal.Traversal

/**
  * A member variable of a class/type.
  * */
class Member(val traversal: Traversal[nodes.Member]) extends AnyVal {

  /**
    * The type declaration this member is defined in
    * */
  def typeDecl: Traversal[nodes.TypeDecl] =
    traversal.in(EdgeTypes.AST).cast[nodes.TypeDecl]

  /**
    * Places where
    * */
  def ref: Traversal[nodes.Call] =
    traversal.in(EdgeTypes.REF).cast[nodes.Call]

  /**
    * Traverse to member type
    * */
  def typ: Traversal[nodes.Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type]

}
