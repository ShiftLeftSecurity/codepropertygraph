package io.shiftleft.semanticcpg.langv2.types.structure

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.langv2._
import overflowdb.traversal.help
import overflowdb.traversal.help.Doc

import scala.collection.IterableOnceOps
import scala.jdk.CollectionConverters._

trait MethodTraversalImplicits {
  implicit def toMethodTraversalSingle[I <: nodes.Method](in: I): MethodTraversal[I, Single, Nothing] = {
    new MethodTraversal(in: Single[I])
  }
  implicit def toMethodTraversalOption[I <: nodes.Method](in: Option[I]): MethodTraversal[I, Option, Nothing] = {
    new MethodTraversal(in)
  }
  implicit def toMethodTraversalIter[I <: nodes.Method, CC[_], C](in: IterableOnceOps[I, CC, C])
  : MethodTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]] = {
    new MethodTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]](in)
  }
}

/**
  * A method, function, or procedure
  * */
@help.Traversal(elementType = classOf[nodes.Method])
class MethodTraversal[I <: nodes.Method, IT[_], Marker](val in: IT[I]) extends AnyVal {

  /**
    * Traverse to parameters of the method
    * */
  @Doc("All parameters")
  def parameter(implicit applier: ToMany[IT, Marker]) = {
    applier.apply(in)(_._astOut.asScala.collect { case par: nodes.MethodParameterIn => par })
  }

  /**
    * Traverse to formal return parameter
    * */
  @Doc("All formal return parameters")
  def methodReturn(implicit applier: ToOne[IT, Marker]) = {
    applier.apply(in)(_._astOut.asScala.collectFirst { case ret: nodes.MethodReturn => ret }.get)
  }

}
