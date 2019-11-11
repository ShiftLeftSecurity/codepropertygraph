package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.ControlStructure
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode
import io.shiftleft.semanticcpg.language.types.structure.{Local, MethodParameter}

class MethodMethods(val node: nodes.Method) extends AnyVal {

  def parameter: MethodParameter = node.start.parameter

  def methodReturn: nodes.MethodReturn = node.start.methodReturn.head

  def local: Local = node.start.local

  def controlStructure : ControlStructure = node.start.controlStructure

  def ast : AstNode = node.start.ast

  def numberOfLines : Int = {
    if (node.lineNumber.isDefined && node.lineNumberEnd.isDefined) {
      node.lineNumberEnd.get - node.lineNumber.get + 1
    } else {
      0
    }
  }

}
