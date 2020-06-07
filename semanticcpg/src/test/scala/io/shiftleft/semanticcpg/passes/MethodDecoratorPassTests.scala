package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import overflowdb._
import io.shiftleft.semanticcpg.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.semanticcpg.testfixtures.EmptyGraphFixture
import org.scalatest.{Matchers, WordSpec}

class MethodDecoratorPassTests extends WordSpec with Matchers {
  "MethodDecoratorTest" in EmptyGraphFixture { graph =>
    val method = graph + NodeTypes.METHOD
    val parameterIn = graph
      .+(
        NodeTypes.METHOD_PARAMETER_IN,
        NodeKeysOdb.CODE -> "p1",
        NodeKeysOdb.ORDER -> 1,
        NodeKeysOdb.NAME -> "p1",
        NodeKeysOdb.EVALUATION_STRATEGY -> EvaluationStrategies.BY_REFERENCE,
        NodeKeysOdb.TYPE_FULL_NAME -> "some.Type",
        NodeKeysOdb.LINE_NUMBER -> 10
      )
      .asInstanceOf[nodes.MethodParameterIn]
    val evalType = graph + NodeTypes.TYPE

    method --- EdgeTypes.AST --> parameterIn

    val methodDecorator = new MethodDecoratorPass(new Cpg(graph))
    methodDecorator.createAndApply()

    val parameterOut = parameterIn._methodParameterOutViaParameterLinkOut.next
    parameterOut.code shouldBe "p1"
    parameterOut.order shouldBe 1
    parameterOut.name shouldBe "p1"
    parameterOut.evaluationStrategy shouldBe EvaluationStrategies.BY_REFERENCE
    parameterOut.typeFullName shouldBe "some.Type"
    parameterOut.lineNumber.get shouldBe 10

    parameterOut._methodViaAstIn.next shouldBe method
  }

}
