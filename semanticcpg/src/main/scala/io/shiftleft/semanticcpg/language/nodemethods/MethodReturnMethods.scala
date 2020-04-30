package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes

class MethodReturnMethods(val node: nodes.MethodReturn) extends AnyVal {

  def toReturn: Iterator[nodes.Return] =
    node._returnViaCfgIn

}
