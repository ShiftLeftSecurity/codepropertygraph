package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstTraversalNew
import io.shiftleft.semanticcpg.language.types.structure.LocalTraversalNew
import io.shiftleft.semanticcpg.langv2.Trav1
import io.shiftleft.semanticcpg.langv2.types.structure.MethodTraversal

object New {

  implicit def toAstTraversalNew1[I <: nodes.AstNode](trav: I): AstTraversalNew[I, Trav1] = {
    new AstTraversalNew(trav: Trav1[I])
  }
  implicit def toAstTraversalNew2[I <: nodes.AstNode, IT[_]](trav: IT[I]): AstTraversalNew[I, IT] = {
    new AstTraversalNew(trav)
  }

  implicit def toMethodTraversalNew1[I <: nodes.Method](trav: I): MethodTraversal[I, Trav1] = {
    new MethodTraversal(trav: Trav1[I])
  }
  implicit def toMethodTraversalNew2[I <: nodes.Method, IT[_]](trav: IT[I]): MethodTraversal[I, IT] = {
    new MethodTraversal(trav)
  }

  implicit def toLocalTraversalNew1[I <: nodes.Local](trav: I): LocalTraversalNew[I, Trav1] = {
    new LocalTraversalNew(trav: Trav1[I])
  }

  implicit def toLocalTraversalNew2[I <: nodes.Local, IT[_]](trav: IT[I]): LocalTraversalNew[I, IT] = {
    new LocalTraversalNew(trav)
  }
}
