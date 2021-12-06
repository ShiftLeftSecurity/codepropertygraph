package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{CfgNode, Local, Method, NewLocation}
import io.shiftleft.semanticcpg.NodeExtension
import io.shiftleft.semanticcpg.language.{HasLocation, LocationCreator, NodeOrdering, toCfgNodeMethods}
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class MethodMethods(val method: Method) extends AnyVal with NodeExtension with HasLocation {

  def cfgFirst: Traversal[CfgNode] =
    method._cfgNodeViaCfgOut

  def local: Traversal[Local] =
    method._blockViaContainsOut.flatMap(_._localViaAstOut)

  def numberOfLines: Int = {
    if (method.lineNumber.isDefined && method.lineNumberEnd.isDefined) {
      method.lineNumberEnd.get - method.lineNumber.get + 1
    } else {
      0
    }
  }

  def isVariadic: Boolean = {
    method.parameter.exists(_.isVariadic)
  }

  def cfgNode: Traversal[CfgNode] =
    method._containsOut.asScala.collect { case cfgNode: CfgNode => cfgNode }

  /**
    * List of CFG nodes in reverse post order
    * */
  def reversePostOrder: Traversal[CfgNode] = {
    def expand(x: CfgNode) = { x.cfgNext.iterator }
    Traversal.from(
      NodeOrdering.reverseNodeList(
        NodeOrdering.postOrderNumbering(method, expand).toList
      )
    )
  }

  /**
    * List of CFG nodes in post order
    * */
  def postOrder: Traversal[CfgNode] = {
    def expand(x: CfgNode) = { x.cfgNext.iterator }
    Traversal.from(
      NodeOrdering.nodeList(
        NodeOrdering.postOrderNumbering(method, expand).toList
      )
    )
  }

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
