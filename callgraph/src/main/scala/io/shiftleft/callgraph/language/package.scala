package io.shiftleft.callgraph

import io.shiftleft.callgraph.language.extensions.{Call, Method, MethodInst}
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod, MethodInst => OriginalMethodInst}
import io.shiftleft.semanticcpg.language.types.expressions.{Call => OriginalCall}

package object language {

  implicit def toMethodForCallGraph[X <% OriginalMethod](original : X) : Method =
    new Method(original)

  implicit def toCallForCallGraph[X <% OriginalCall](original : X) : Call =
    new Call(original)

  implicit def toMethodInstForCallGraph[X <% OriginalMethodInst](original : X) : MethodInst =
    new MethodInst(original)

}
