package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstTraversalNew
import io.shiftleft.semanticcpg.language.types.structure.LocalTraversalNew
import io.shiftleft.semanticcpg.langv2.Trav1
import io.shiftleft.semanticcpg.langv2.types.structure.MethodTraversal

object New {

  implicit def toAstTraversalNew1[I <: nodes.AstNode, FT[_]](trav: I): AstTraversalNew[I, Trav1, FT] = {
    new AstTraversalNew(trav: Trav1[I])
  }
  implicit def toAstTraversalNew2[I <: nodes.AstNode, IT[_], FT[_]](trav: IT[I]): AstTraversalNew[I, IT, FT] = {
    new AstTraversalNew(trav)
  }

  implicit def toMethodTraversalNew1[I <: nodes.Method, FT[_]](trav: I): MethodTraversal[I, Trav1, FT] = {
    new MethodTraversal(trav: Trav1[I])
  }
  implicit def toMethodTraversalNew2[I <: nodes.Method, IT[_], FT[_]](trav: IT[I]): MethodTraversal[I, IT, FT] = {
    new MethodTraversal(trav)
  }

  implicit def toLocalTraversalNew1[I <: nodes.Local, FT[_]](trav: I): LocalTraversalNew[I, Trav1, FT] = {
    new LocalTraversalNew(trav: Trav1[I])
  }

  implicit def toLocalTraversalNew2[I <: nodes.Local, IT[_], FT[_]](trav: IT[I]): LocalTraversalNew[I, IT, FT] = {
    new LocalTraversalNew(trav)
  }
}
