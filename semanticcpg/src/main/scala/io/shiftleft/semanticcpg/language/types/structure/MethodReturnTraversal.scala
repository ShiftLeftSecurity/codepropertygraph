package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.help.Doc
import overflowdb.traversal.{Traversal, help}

@help.Traversal(elementType = classOf[MethodReturn])
class MethodReturnTraversal(val traversal: Traversal[MethodReturn]) extends AnyVal {

  @Doc("traverse to parent method")
  def method: Traversal[Method] =
    traversal.in(EdgeTypes.AST).cast[Method]

  def returnUser(implicit callResolver: ICallResolver): Traversal[Call] =
    traversal
      .in(EdgeTypes.AST)
      .flatMap { method =>
        val callsites = callResolver.getMethodCallsites(method.asInstanceOf[Method])
        // TODO for now we filter away all implicit calls because a change of the
        // return type to CallRepr would lead to a break in the API aka
        // the DSL steps which are subsequently allowed to be called. Before
        // we addressed this we can only return Call instances.
        callsites.filter(_.isInstanceOf[Call])
      }
      .cast[Call]

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  @Doc("traverse to last expressions in CFG (can be multiple)")
  def cfgLast: Traversal[Expression] =
    traversal.in(EdgeTypes.CFG).cast[Expression]

  /**
    * Traverse to return type
    * */
  @Doc("traverse to return type")
  def typ: Traversal[Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[Type]

  def toReturn: Traversal[Return] =
    traversal.flatMap(_.toReturn)
}
