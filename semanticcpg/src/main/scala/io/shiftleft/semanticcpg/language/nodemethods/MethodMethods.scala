package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

import scala.jdk.CollectionConverters._

class MethodMethods(val method: nodes.Method) extends AnyVal {

  def parameter: Traversal[nodes.MethodParameterIn] =
    method._methodParameterInViaAstOut

  def methodReturn: nodes.MethodReturn =
    method._methodReturnViaAstOut

  def local: Traversal[nodes.Local] =
    method._blockViaContainsOut.flatMap(_._localViaAstOut)

  def controlStructure: Traversal[nodes.ControlStructure] =
    method.start.controlStructure

  def ast: Traversal[nodes.AstNode] =
    method.start.ast

  def numberOfLines: Int = {
    if (method.lineNumber.isDefined && method.lineNumberEnd.isDefined) {
      method.lineNumberEnd.get - method.lineNumber.get + 1
    } else {
      0
    }
  }

  def cfgNode: Iterator[nodes.CfgNode] =
    method._containsOut.asScala.collect { case cfgNode: nodes.CfgNode => cfgNode }

}
