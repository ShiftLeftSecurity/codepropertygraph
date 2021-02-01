package io.shiftleft.fuzzyc2cpg

import io.shiftleft.codepropertygraph.generated._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._

class MethodHeaderTests extends AnyWordSpec with Matchers {
  val fixture = CpgTestFixture("methodheader")

  "Method header" should {

    "have correct METHOD node for method foo" in {
      val methods = fixture.traversalSource.label(NodeTypes.METHOD).has(NodeKeys.NAME -> "foo").l
      methods.size shouldBe 1
      val method = methods.head
      method.property(NodeKeys.IS_EXTERNAL) shouldBe false
      method.property(NodeKeys.FULL_NAME) shouldBe "foo"
      method.property(NodeKeys.SIGNATURE) shouldBe "int foo (int,int)"
      method.property(NodeKeys.LINE_NUMBER) shouldBe 1
      method.property(NodeKeys.COLUMN_NUMBER) shouldBe 0
      method.property(NodeKeys.LINE_NUMBER_END) shouldBe 3
      method.property(NodeKeys.COLUMN_NUMBER_END) shouldBe 0
      method.property(NodeKeys.CODE) shouldBe "foo (int x,int y)"
    }

    "have correct METHOD_PARAMETER_IN nodes for method foo" in {
      val parameters = fixture.traversalSource
        .label(NodeTypes.METHOD)
        .has(NodeKeys.NAME -> "foo")
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .l

      parameters.size shouldBe 2
      val param1Option = parameters.find(_.property(NodeKeys.ORDER) == 1)
      param1Option.isDefined shouldBe true
      param1Option.get.property(NodeKeys.CODE) shouldBe "int x"
      param1Option.get.property(NodeKeys.NAME) shouldBe "x"
      param1Option.get.property(NodeKeys.EVALUATION_STRATEGY) shouldBe EvaluationStrategies.BY_VALUE
      param1Option.get.property(NodeKeys.LINE_NUMBER) shouldBe 1
      param1Option.get.property(NodeKeys.COLUMN_NUMBER) shouldBe 8

      val param2Option = parameters.find(_.property(NodeKeyNames.ORDER) == 2)
      param2Option.isDefined shouldBe true
      param2Option.isDefined shouldBe true
      param2Option.get.property(NodeKeys.CODE) shouldBe "int y"
      param2Option.get.property(NodeKeys.NAME) shouldBe "y"
      param2Option.get.property(NodeKeys.EVALUATION_STRATEGY) shouldBe EvaluationStrategies.BY_VALUE
      param2Option.get.property(NodeKeys.LINE_NUMBER) shouldBe 1
      param2Option.get.property(NodeKeys.COLUMN_NUMBER) shouldBe 15
    }

    "have correct METHOD_RETURN node for method foo" in {
      val methodReturn = fixture.traversalSource
        .label(NodeTypes.METHOD)
        .has(NodeKeys.NAME -> "foo")
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_RETURN)
        .l

      methodReturn.size shouldBe 1
      methodReturn.head.property(NodeKeys.CODE) shouldBe "int"
      methodReturn.head.property(NodeKeys.EVALUATION_STRATEGY) shouldBe EvaluationStrategies.BY_VALUE
      methodReturn.head.property(NodeKeys.LINE_NUMBER) shouldBe 1
      methodReturn.head.property(NodeKeys.COLUMN_NUMBER) shouldBe 0
    }

  }

}
