package io.shiftleft.passes.dataflows.steps

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.ext.dataflowengine._
import io.shiftleft.semanticcpg.language.nodemethods.CfgNodeMethods
import io.shiftleft.semanticcpg.language.types.expressions.Literal
import io.shiftleft.semanticcpg.language.types.structure.{Member, Method}
import org.scalatest.{Matchers, WordSpec}
import shapeless.HNil

class CpgDataFlowTests extends WordSpec with Matchers {

  protected implicit def int2IntegerOption(x: Int): Option[Integer] = {
    Some(x)
  }

  protected def getMemberOfType(cpg: Cpg, typeName: String, memberName: String): Member[HNil] = {
    cpg.typeDecl.nameExact(typeName).member.nameExact(memberName)
  }

  protected def getMethodOfType(cpg: Cpg, typeName: String, methodName: String): Method[HNil] = {
    cpg.typeDecl.nameExact(typeName).method.nameExact(methodName)
  }

  protected def getLiteralOfType(cpg: Cpg, typeName: String, literalName: String): Literal[HNil] = {
    cpg.typeDecl.nameExact(typeName).method.isLiteral.codeExact(literalName)
  }

  protected def flowToResultPairs(flow: List[nodes.TrackingPoint]): List[(String, Option[Integer])] = {
    flow.map { point =>
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
