package io.shiftleft.dataflowengine.language

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.nodemethods.CfgNodeMethods
import io.shiftleft.semanticcpg.language.types.expressions.Literal
import io.shiftleft.semanticcpg.language.types.structure.{Member, Method}
import org.scalatest.{Matchers, WordSpec}

class CpgDataFlowTests extends WordSpec with Matchers {

  protected implicit def int2IntegerOption(x: Int): Option[Integer] =
    Some(x)

  protected def getMemberOfType(cpg: Cpg, typeName: String, memberName: String): NodeSteps[nodes.Member] =
    cpg.typeDecl.nameExact(typeName).member.nameExact(memberName)

  protected def getMethodOfType(cpg: Cpg, typeName: String, methodName: String): NodeSteps[nodes.Method] =
    cpg.typeDecl.nameExact(typeName).method.nameExact(methodName)

  protected def getLiteralOfType(cpg: Cpg, typeName: String, literalName: String): NodeSteps[nodes.Literal] =
    cpg.typeDecl.nameExact(typeName).method.isLiteral.codeExact(literalName)

  protected def flowToResultPairs(path: Path): List[(String, Option[Integer])] = {
    path.elements.map { point =>
      point match {
        case methodParamIn: nodes.MethodParameterIn => {
          val method = point.start.method.head
          val method_name = method.name
          val code = s"$method_name(${method.start.parameter.l.sortBy(_.order).map(_.code).mkString(", ")})"
          (code, point.cfgNode.lineNumber)
        }
        case _ => (CfgNodeMethods.repr(point.cfgNode), point.cfgNode.lineNumber)
      }
    }
  }
}
