package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.NewLocation
import io.shiftleft.semanticcpg.language.{AddsMethodsToNode, HasLocation}

class MethodReturnMethods(val node: nodes.MethodReturn) extends AddsMethodsToNode with HasLocation {

  def toReturn: Option[nodes.Return] =
    node._returnViaCfgIn

  override def location: NewLocation = {
    ???
  }
}
