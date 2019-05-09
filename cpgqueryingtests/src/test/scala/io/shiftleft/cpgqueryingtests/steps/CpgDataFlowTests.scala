package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps._
import io.shiftleft.queryprimitives.steps.types.expressions.Literal
import io.shiftleft.queryprimitives.steps.types.structure.{Member, Method}
import io.shiftleft.queryprimitives.steps.ext.dataflowengine._
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
    cpg.typeDecl.nameExact(typeName).method.literal.codeExact(literalName)
  }

  protected def flowToResultPairs(flow: List[nodes.TrackingPoint]): List[(String, Option[Integer])] = {
    flow.map(point => (point.cfgNode.code, point.cfgNode.lineNumber))
  }
}
