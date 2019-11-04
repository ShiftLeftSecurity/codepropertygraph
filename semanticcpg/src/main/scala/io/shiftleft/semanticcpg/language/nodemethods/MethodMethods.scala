package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.{Local, MethodParameter}

class MethodMethods(val node: nodes.Method) extends AnyVal {

  def parameter: MethodParameter = node.start.parameter

  def methodReturn: nodes.MethodReturn = node.start.methodReturn.head

  def local: Local = node.start.local

}
