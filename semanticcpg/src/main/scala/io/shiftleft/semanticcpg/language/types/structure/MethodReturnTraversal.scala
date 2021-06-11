package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.help.Doc
import overflowdb.traversal.{Traversal, help}

@help.Traversal(elementType = classOf[nodes.MethodReturn])
class MethodReturnTraversal(val traversal: Traversal[nodes.MethodReturn]) extends AnyVal {

  @Doc("traverse to parent method")
  def method: Traversal[nodes.Method] =
    traversal.in(EdgeTypes.AST).cast[nodes.Method]

  def returnUser(implicit callResolver: ICallResolver): Traversal[nodes.Call] =
    traversal
      .in(EdgeTypes.AST)
      .flatMap { method =>
        val callsites = callResolver.getMethodCallsites(method.asInstanceOf[nodes.Method])
        // TODO for now we filter away all implicit calls because a change of the
        // return type to nodes.CallRepr would lead to a break in the API aka
        // the DSL steps which are subsequently allowed to be called. Before
        // we addressed this we can only return nodes.Call instances.
        callsites.filter(_.isInstanceOf[nodes.Call])
      }
      .cast[nodes.Call]

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  @Doc("traverse to last expressions in CFG (can be multiple)")
  def cfgLast: Traversal[nodes.Expression] =
    traversal.in(EdgeTypes.CFG).cast[nodes.Expression]

  /**
    * Traverse to return type
    * */
  @Doc("traverse to return type")
  def typ: Traversal[nodes.Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type]

  def toReturn: Traversal[nodes.Return] =
    traversal.flatMap(_.toReturn)
}
