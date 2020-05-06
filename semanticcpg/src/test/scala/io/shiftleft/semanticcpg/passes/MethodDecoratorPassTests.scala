package io.shiftleft.semanticcpg.passes

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, EvaluationStrategies, NodeKeys, NodeTypes}
import io.shiftleft.semanticcpg.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.semanticcpg.testfixtures.EmptyGraphFixture
import org.apache.tinkerpop.gremlin.structure.Direction
import org.scalatest.{Matchers, WordSpec}

class MethodDecoratorPassTests extends WordSpec with Matchers {
  "MethodDecoratorTest" in EmptyGraphFixture { graph =>
    implicit val scalaGraph: ScalaGraph = graph.asScala
    val method = graph + NodeTypes.METHOD
    val parameterIn = graph + (NodeTypes.METHOD_PARAMETER_IN,
    NodeKeys.CODE -> "p1",
    NodeKeys.ORDER -> 1,
    NodeKeys.NAME -> "p1",
    NodeKeys.EVALUATION_STRATEGY -> EvaluationStrategies.BY_REFERENCE,
    NodeKeys.TYPE_FULL_NAME -> "some.Type",
    NodeKeys.LINE_NUMBER -> 10)
    val evalType = graph + NodeTypes.TYPE

    method --- EdgeTypes.AST --> parameterIn

    val methodDecorator = new MethodDecoratorPass(new Cpg(graph))
    methodDecorator.createAndApply()

    val parameterOut = parameterIn.vertices(Direction.OUT, EdgeTypes.PARAMETER_LINK).next
    parameterOut.value2(NodeKeys.CODE) shouldBe "p1"
    parameterOut.value2(NodeKeys.ORDER) shouldBe 1
    parameterOut.value2(NodeKeys.NAME) shouldBe "p1"
    parameterOut.value2(NodeKeys.EVALUATION_STRATEGY) shouldBe EvaluationStrategies.BY_REFERENCE
    parameterOut.value2(NodeKeys.TYPE_FULL_NAME) shouldBe "some.Type"
    parameterOut.value2(NodeKeys.LINE_NUMBER) shouldBe 10

    parameterOut.vertices(Direction.IN, EdgeTypes.AST).next shouldBe method
  }

}
