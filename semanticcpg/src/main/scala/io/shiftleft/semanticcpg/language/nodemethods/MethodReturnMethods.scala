package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{MethodReturn, Return}

class MethodReturnMethods(val node: MethodReturn) extends AnyVal {

  def toReturn: Option[Return] =
    node._returnViaCfgIn

}
