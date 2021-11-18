package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstTraversalNew
import io.shiftleft.semanticcpg.language.types.structure.LocalTraversalNew
import io.shiftleft.semanticcpg.langv2.{IterableTypes, OptionTypes, SingleTypes, Trav1}
import io.shiftleft.semanticcpg.langv2.types.structure.MethodTraversal

import scala.collection.IterableOnceOps

object New {

  implicit def toAstTraversalNew1[I <: nodes.AstNode](trav: I) = {
    new AstTraversalNew[I, Trav1, SingleTypes.type](trav: Trav1[I])
  }
  implicit def toAstTraversalNew2[I <: nodes.AstNode](trav: Option[I]) = {
    new AstTraversalNew[I, Option, OptionTypes.type](trav)
  }
  //implicit def toAstTraversalNew2[I <: nodes.AstNode, IT[_]](trav: IT[I]): AstTraversalNew[I, IT] = {
  //  new AstTraversalNew(trav)
  //}
  implicit def toAstTraversalNew3[I <: nodes.AstNode, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    //new AstTraversalNew[I, ({type X[B] = IterableOnceOps[B, CC, C]})#X, CC, CC, ({type X[B] = C})#X, CC](trav)
    new AstTraversalNew[I, ({type X[B] = IterableOnceOps[B, CC, C]})#X, IterableTypes[CC, C]](trav)
  }

  implicit def toMethodTraversalNew1[I <: nodes.Method](trav: I) = {
    new MethodTraversal[I, Trav1, SingleTypes.type](trav: Trav1[I])
  }
  implicit def toMethodTraversalNew2[I <: nodes.Method](trav: Option[I]) = {
    new MethodTraversal[I, Option, OptionTypes.type](trav)
  }
  //implicit def toMethodTraversalNew2[I <: nodes.Method, IT[_]](trav: IT[I]): MethodTraversal[I, IT] = {
  //  new MethodTraversal(trav)
  //}
  implicit def toMethodTraversalNew3[I <: nodes.Method, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new MethodTraversal[I, ({type X[B] = IterableOnceOps[B, CC, C]})#X, IterableTypes[CC, C]](trav)
  }

  implicit def toLocalTraversalNew1[I <: nodes.Local](trav: I) = {
    new LocalTraversalNew[I, Trav1, SingleTypes.type](trav: Trav1[I])
  }
  implicit def toLocalTraversalNew2[I <: nodes.Local](trav: Option[I]) = {
    new LocalTraversalNew[I, Option, OptionTypes.type](trav)
  }

  //implicit def toLocalTraversalNew2[I <: nodes.Local, IT[_]](trav: IT[I]): LocalTraversalNew[I, IT] = {
  //  new LocalTraversalNew(trav)
  //}
  implicit def toLocalTraversalNew3[I <: nodes.Local, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new LocalTraversalNew[I, ({type X[B] = IterableOnceOps[B, CC, C]})#X, IterableTypes[CC, C]](trav)
  }
}
