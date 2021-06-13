package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{
  CfgNode,
  Local,
  Method,
  MethodParameterIn,
  MethodReturn,
  NewLocation
}
import io.shiftleft.semanticcpg.NodeExtension
import io.shiftleft.semanticcpg.language.{HasLocation, LocationCreator}
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class MethodMethods(val method: Method) extends AnyVal with NodeExtension with HasLocation {

  def parameter: Traversal[MethodParameterIn] =
    method._methodParameterInViaAstOut

  def methodReturn: MethodReturn =
    method._methodReturnViaAstOut

  def local: Traversal[Local] =
    method._blockViaContainsOut.flatMap(_._localViaAstOut)

  def numberOfLines: Int = {
    if (method.lineNumber.isDefined && method.lineNumberEnd.isDefined) {
      method.lineNumberEnd.get - method.lineNumber.get + 1
    } else {
      0
    }
  }

  def cfgNode: Traversal[CfgNode] =
    method._containsOut.asScala.collect { case cfgNode: CfgNode => cfgNode }

  override def location: NewLocation = {
    LocationCreator(
      method,
      method.name,
      method.label,
      method.lineNumber,
      method
    )
  }
}
