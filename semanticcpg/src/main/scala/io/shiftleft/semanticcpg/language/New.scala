package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstTraversalNew
import io.shiftleft.semanticcpg.language.types.structure.{LocalTraversalNew, MethodTraversalNew}
import io.shiftleft.semanticcpg.langv2.Trav1

object New {

  implicit def toAstTraversalNew1[I <: nodes.AstNode, FT[_]](trav: I): AstTraversalNew[I, Trav1, FT] = {
    new AstTraversalNew(trav: Trav1[I])
  }
  implicit def toAstTraversalNew2[I <: nodes.AstNode, IT[_], FT[_]](trav: IT[I]): AstTraversalNew[I, IT, FT] = {
    new AstTraversalNew(trav)
  }

  implicit def toMethodTraversalNew1[I <: nodes.Method, FT[_]](trav: I): MethodTraversalNew[I, Trav1, FT] = {
    new MethodTraversalNew(trav: Trav1[I])
  }
  implicit def toMethodTraversalNew2[I <: nodes.Method, IT[_], FT[_]](trav: IT[I]): MethodTraversalNew[I, IT, FT] = {
    new MethodTraversalNew(trav)
  }

  implicit def toLocalTraversalNew1[I <: nodes.Local, FT[_]](trav: I): LocalTraversalNew[I, Trav1, FT] = {
    new LocalTraversalNew(trav: Trav1[I])
  }

  implicit def toLocalTraversalNew2[I <: nodes.Local, IT[_], FT[_]](trav: IT[I]): LocalTraversalNew[I, IT, FT] = {
    new LocalTraversalNew(trav)
  }
}
