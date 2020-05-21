package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._
import io.shiftleft.Implicits.JavaIteratorDeco

class MethodMethods(val node: nodes.Method) extends AnyVal {

  def parameter: Iterator[nodes.MethodParameterIn] =
    node._methodParameterInViaAstOut

  def methodReturn: nodes.MethodReturn =
    node._methodReturnViaAstOut

  def local: Iterator[nodes.Local] =
    node._blockViaContainsOut.flatMap(_._localViaAstOut)

  def controlStructure: NodeSteps[nodes.ControlStructure] =
    node.start.controlStructure

  def ast: NodeSteps[nodes.AstNode] =
    node.start.ast

  def numberOfLines: Int = {
    if (node.lineNumber.isDefined && node.lineNumberEnd.isDefined) {
      node.lineNumberEnd.get - node.lineNumber.get + 1
    } else {
      0
    }
  }

  def cfgNode: Iterator[nodes.CfgNode] =
    node._containsOut.asScala.collect { case cfgNode: nodes.CfgNode => cfgNode }

}
