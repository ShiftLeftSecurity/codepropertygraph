package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstTraversalNew
import io.shiftleft.semanticcpg.language.types.structure.LocalTraversalNew
import io.shiftleft.semanticcpg.langv2.{IterableTypes, OptionTypes, SingleTypes, Trav1, TypeBound, TypesClassFor}
import io.shiftleft.semanticcpg.langv2.types.structure.MethodTraversal

import scala.collection.IterableOnceOps

object New {

  implicit def toAstTraversalNew1[I <: nodes.AstNode](trav: I) = {
    new AstTraversalNew[I, SingleTypes.type](trav: Trav1[I])
  }
  implicit def toAstTraversalNew2[I <: nodes.AstNode, IT[_] <: TypeBound[_]](trav: TypesClassFor[IT]#IT[I]) = {
    new AstTraversalNew[I, TypesClassFor[IT]](trav)
  }
  implicit def toAstTraversalNew3[I <: nodes.AstNode, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new AstTraversalNew[I, IterableTypes[CC, C]](trav)
  }

  implicit def toMethodTraversalNew1[I <: nodes.Method](trav: I) = {
    new MethodTraversal[I, SingleTypes.type](trav: Trav1[I])
  }
  implicit def toMethodTraversalNew2[I <: nodes.Method, IT[_] <: TypeBound[_]](trav: TypesClassFor[IT]#IT[I]) = {
    new MethodTraversal[I, TypesClassFor[IT]](trav)
  }
  implicit def toMethodTraversalNew3[I <: nodes.Method, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new MethodTraversal[I, IterableTypes[CC, C]](trav)
  }

  implicit def toLocalTraversalNew1[I <: nodes.Local](trav: I) = {
    new LocalTraversalNew[I, SingleTypes.type](trav: Trav1[I])
  }
  implicit def toLocalTraversalNew2[I <: nodes.Local, IT[_] <: TypeBound[_]](trav: TypesClassFor[IT]#IT[I]) = {
    new LocalTraversalNew[I, TypesClassFor[IT]](trav)
  }
  implicit def toLocalTraversalNew3[I <: nodes.Local, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new LocalTraversalNew[I, IterableTypes[CC, C]](trav)
  }
}
