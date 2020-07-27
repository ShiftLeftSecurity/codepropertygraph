package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._
import io.shiftleft.Implicits.JavaIteratorDeco
import overflowdb.traversal.Traversal

class MethodMethods(val node: nodes.Method) extends AnyVal {

  def parameter: NodeSteps[nodes.MethodParameterIn] =
    // TODO once we use OdbTraversal, this will simply become `node._methodParameterInViaAstOut`
    node.start.parameter

  def methodReturn: nodes.MethodReturn =
    node._methodReturnViaAstOut

  def local: NodeSteps[nodes.Local] =
    // TODO once we use OdbTraversal, this will simply become `node._blockViaContainsOut.flatMap(_._localViaAstOut)`
    node.start.local

  def controlStructure: Traversal[nodes.ControlStructure] =
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
