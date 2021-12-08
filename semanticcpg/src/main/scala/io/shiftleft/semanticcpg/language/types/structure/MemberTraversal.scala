package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes.{Call, Member, Type}
import overflowdb.traversal._

/**
  * A member variable of a class/type.
  * */
class MemberTraversal(val traversal: Traversal[Member]) extends AnyVal {

  /**
    * Places where
    * */
  def ref: Traversal[Call] =
    traversal.in(EdgeTypes.REF).cast[Call]

  /**
    * Traverse to member type
    * */
  def typ: Traversal[Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[Type]

}
