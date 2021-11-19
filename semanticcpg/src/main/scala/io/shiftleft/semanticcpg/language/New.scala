package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstTraversalNew
import io.shiftleft.semanticcpg.language.types.structure.LocalTraversalNew
import io.shiftleft.semanticcpg.langv2.{IterableOnceOpsTravTypes, OptionTravTypes, SingleTravTypes, Single, SupportedTypes, TravTypesFor}
import io.shiftleft.semanticcpg.langv2.types.structure.MethodTraversal

import scala.collection.IterableOnceOps

object New {

  implicit def toAstTraversalNew1[I <: nodes.AstNode](trav: I) = {
    new AstTraversalNew[I, SingleTravTypes.type](trav: Single[I])
  }
  implicit def toAstTraversalNew2[I <: nodes.AstNode, T[_] <: SupportedTypes[_]](trav: TravTypesFor[T]#Collection[I]) = {
    new AstTraversalNew[I, TravTypesFor[T]](trav)
  }
  implicit def toAstTraversalNew3[I <: nodes.AstNode, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new AstTraversalNew[I, IterableOnceOpsTravTypes[CC, C]](trav)
  }

  implicit def toMethodTraversalNew1[I <: nodes.Method](trav: I) = {
    new MethodTraversal[I, SingleTravTypes.type](trav: Single[I])
  }
  implicit def toMethodTraversalNew2[I <: nodes.Method, T[_] <: SupportedTypes[_]](trav: TravTypesFor[T]#Collection[I]) = {
    new MethodTraversal[I, TravTypesFor[T]](trav)
  }
  implicit def toMethodTraversalNew3[I <: nodes.Method, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new MethodTraversal[I, IterableOnceOpsTravTypes[CC, C]](trav)
  }

  implicit def toLocalTraversalNew1[I <: nodes.Local](trav: I) = {
    new LocalTraversalNew[I, SingleTravTypes.type](trav: Single[I])
  }
  implicit def toLocalTraversalNew2[I <: nodes.Local, T[_] <: SupportedTypes[_]](trav: TravTypesFor[T]#Collection[I]) = {
    new LocalTraversalNew[I, TravTypesFor[T]](trav)
  }
  implicit def toLocalTraversalNew3[I <: nodes.Local, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new LocalTraversalNew[I, IterableOnceOpsTravTypes[CC, C]](trav)
  }
}
