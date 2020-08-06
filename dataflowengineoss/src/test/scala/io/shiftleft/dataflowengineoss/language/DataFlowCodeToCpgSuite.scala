package io.shiftleft.dataflowengineoss.language

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.layers.dataflows.{OssDataFlow, OssDataFlowOptions}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.layers.{LayerCreatorContext, Scpg}
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgSuite
import overflowdb.traversal._

class DataFlowCodeToCpgSuite extends CodeToCpgSuite {

  override def passes(cpg: Cpg): Unit = {
    val context = new LayerCreatorContext(cpg)
    new Scpg().run(context)
    val options = new OssDataFlowOptions("dataflowengineoss/src/test/resources/default.semantics")
    new OssDataFlow(options).run(context)
  }

  protected implicit def int2IntegerOption(x: Int): Option[Integer] =
    Some(x)

  protected def getMemberOfType(cpg: Cpg, typeName: String, memberName: String): Traversal[nodes.Member] =
    cpg.typeDecl.nameExact(typeName).member.nameExact(memberName)

  protected def getMethodOfType(cpg: Cpg, typeName: String, methodName: String): Traversal[nodes.Method] =
    cpg.typeDecl.nameExact(typeName).method.nameExact(methodName)

  protected def getLiteralOfType(cpg: Cpg, typeName: String, literalName: String): Traversal[nodes.Literal] =
    cpg.typeDecl.nameExact(typeName).method.isLiteral.codeExact(literalName)

  protected def flowToResultPairs(path: Path): List[(String, Option[Integer])] = {
    path.elements.map {
      case point: nodes.MethodParameterIn =>
        val method = point.start.method.head
        val method_name = method.name
        val code = s"$method_name(${method.start.parameter.l.sortBy(_.order).map(_.code).mkString(", ")})"
        (code, point.cfgNode.lineNumber)
      case point => (point.cfgNode.repr, point.cfgNode.lineNumber)
    }
  }

}
