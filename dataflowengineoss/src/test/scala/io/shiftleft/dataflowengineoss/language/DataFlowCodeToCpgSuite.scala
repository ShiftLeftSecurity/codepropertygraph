package io.shiftleft.dataflowengineoss.language

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.layers.dataflows.{OssDataFlow, OssDataFlowOptions}
import io.shiftleft.dataflowengineoss.queryengine.EngineContext
import io.shiftleft.dataflowengineoss.semanticsloader.{Parser, Semantics}
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.layers.{LayerCreatorContext, Scpg}
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.dotextension.ImageViewer

import scala.sys.process.Process
import scala.util.Try

class DataFlowCodeToCpgSuite extends CodeToCpgSuite {

  val semanticsFilename = "dataflowengineoss/src/test/resources/default.semantics"
  val semantics: Semantics = Semantics.fromList(new Parser().parseFile(semanticsFilename))
  implicit val context = EngineContext(semantics)

  implicit val viewer: ImageViewer = (pathStr: String) =>
    Try {
      Process(Seq("xdg-open", pathStr)).!!
  }

  override def passes(cpg: Cpg): Unit = {
    val context = new LayerCreatorContext(cpg)
    new Scpg().run(context)
    val options = new OssDataFlowOptions()
    new OssDataFlow(options).run(context)
  }

  protected implicit def int2IntegerOption(x: Int): Option[Integer] =
    Some(x)

  protected def getMemberOfType(cpg: Cpg, typeName: String, memberName: String): NodeSteps[nodes.Member] =
    cpg.typeDecl.nameExact(typeName).member.nameExact(memberName)

  protected def getMethodOfType(cpg: Cpg, typeName: String, methodName: String): NodeSteps[nodes.Method] =
    cpg.typeDecl.nameExact(typeName).method.nameExact(methodName)

  protected def getLiteralOfType(cpg: Cpg, typeName: String, literalName: String): NodeSteps[nodes.Literal] =
    cpg.typeDecl.nameExact(typeName).method.isLiteral.codeExact(literalName)

  protected def flowToResultPairs(path: Path): List[(String, Option[Integer])] = {
    val pairs = path.elements.map { point =>
      point match {
        case _: nodes.MethodParameterIn => {
          val method = point.start.method.head
          val method_name = method.name
          val code = s"$method_name(${method.start.parameter.l.sortBy(_.order).map(_.code).mkString(", ")})"
          (code, point.cfgNode.lineNumber)
        }
        case _ => (point.cfgNode.repr, point.cfgNode.lineNumber)
      }
    }
    pairs.headOption.map(x => x :: pairs.sliding(2).collect { case Seq(a, b) if a != b => b }.toList).getOrElse(List())
  }

}
