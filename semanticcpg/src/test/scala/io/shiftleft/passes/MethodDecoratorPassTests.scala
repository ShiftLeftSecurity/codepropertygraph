package io.shiftleft.passes.methoddecorations

import gremlin.scala._
import io.shiftleft.TinkerGraphTestInstance
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, EvaluationStrategies, NodeKeys, NodeTypes, nodes}
import org.apache.tinkerpop.gremlin.structure.Direction
import org.scalatest.{Matchers, WordSpec}

class MethodDecoratorPassTests extends WordSpec with Matchers {
  "MethodDecoratorTest" in {
    implicit val graph: ScalaGraph = TinkerGraphTestInstance.create

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

    val methodDecorator = new MethodDecoratorPass(graph)
    methodDecorator.executeAndApply()

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
