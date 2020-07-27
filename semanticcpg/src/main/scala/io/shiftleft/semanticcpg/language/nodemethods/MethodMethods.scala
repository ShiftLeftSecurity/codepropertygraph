package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class MethodMethods(val node: nodes.Method) extends AnyVal {

  def parameter: Traversal[nodes.MethodParameterIn] =
    node._methodParameterInViaAstOut

  def methodReturn: nodes.MethodReturn =
    node._methodReturnViaAstOut

  def local: Traversal[nodes.Local] =
    node._blockViaContainsOut.flatMap(_._localViaAstOut)

  def controlStructure: Traversal[nodes.ControlStructure] =
    Traversal.fromSingle(node).controlStructure

  def ast: Traversal[nodes.AstNode] =
    Traversal.fromSingle(node).ast

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
