package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._
import io.shiftleft.Implicits.JavaIteratorDeco

class MethodMethods(val node: nodes.Method) extends AnyVal {

  def parameter: NodeSteps[nodes.MethodParameterIn] =
    node.start.parameter

  def methodReturn: nodes.MethodReturn =
    node._astOut.asScala
      .collect { case mr: nodes.MethodReturn => mr }
      .asJava
      .onlyChecked

  def local: NodeSteps[nodes.Local] =
    node.start.local

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
    node.containsOut.asScala.collect { case cfgNode: nodes.CfgNode => cfgNode }

}
