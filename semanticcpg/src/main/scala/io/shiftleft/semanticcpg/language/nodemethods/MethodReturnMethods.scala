package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{MethodReturn, NewLocation, Return}
import io.shiftleft.semanticcpg.NodeExtension
import io.shiftleft.semanticcpg.language.{HasLocation, LocationCreator, _}

class MethodReturnMethods(val node: MethodReturn) extends AnyVal with NodeExtension with HasLocation {

  def toReturn: Option[Return] =
    node._returnViaCfgIn

  override def location: NewLocation = {
    LocationCreator(
      node,
      "$ret",
      node.label,
      node.lineNumber,
      node.method
    )
  }
}
