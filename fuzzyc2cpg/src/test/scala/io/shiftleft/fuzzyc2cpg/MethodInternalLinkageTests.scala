package io.shiftleft.fuzzyc2cpg

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._
import overflowdb.traversal._


class MethodInternalLinkageTests extends AnyWordSpec with Matchers with TraversalUtils {
  val fixture = CpgTestFixture("methodinternallinkage")

  implicit class VertexListWrapper(vertexList: List[Node]) {
    def expandAst(filterLabels: String*): List[Node] = {
      if (filterLabels.nonEmpty) {
        vertexList.flatMap(_.start.out(EdgeTypes.AST).hasLabel(filterLabels: _*).l)
      } else {
        vertexList.flatMap(_.start.out(EdgeTypes.AST).l)
      }
    }

    def expandRef(): List[Node] = {
      vertexList.flatMap(_.start.out(EdgeTypes.REF).l)
    }

    def filterOrder(order: Int): List[Node] = {
        vertexList.to(Traversal).has(NodeKeys.ORDER -> order).l
    }

    def filterName(name: String): List[Node] = {
      vertexList.to(Traversal).has(NodeKeys.NAME -> name).l
    }

    def checkForSingle[T](label: String, propertyKey: PropertyKey[T], value: T): Unit = {
      vertexList.size shouldBe 1
      vertexList.head.label() shouldBe label
      vertexList.head.property(propertyKey) shouldBe value
    }

    def checkForSingle[T](propertyKey: PropertyKey[T], value: T): Unit = {
      vertexList.size shouldBe 1
      vertexList.head.property(propertyKey) shouldBe value
    }
  }

  "REF edges" should {
    "be correct for local x in method1" in {
      val method = getMethod("method1")
      val indentifierX = method.expandAst().expandAst().expandAst(NodeTypes.IDENTIFIER)
      indentifierX.checkForSingle(NodeKeys.NAME, "x")

      val localX = indentifierX.expandRef()
      localX.checkForSingle(NodeTypes.LOCAL, NodeKeys.NAME, "x")
    }

    "be correct for parameter x in method2" in {
      val method = getMethod("method2")
      val indentifierX = method.expandAst().expandAst().expandAst(NodeTypes.IDENTIFIER)
      indentifierX.checkForSingle(NodeKeys.NAME, "x")

      val parameterX = indentifierX.expandRef()
      parameterX.checkForSingle(NodeTypes.METHOD_PARAMETER_IN, NodeKeys.NAME, "x")
    }

    "be correct for all identifiers x, y in method3" in {
      val method = getMethod("method3")
      val outerIdentifierX = method.expandAst().expandAst().filterOrder(3).expandAst(NodeTypes.IDENTIFIER)
      outerIdentifierX.checkForSingle(NodeKeys.NAME, "x")
      val parameterX = outerIdentifierX.expandRef()
      parameterX.checkForSingle(NodeTypes.METHOD_PARAMETER_IN, NodeKeys.NAME, "x")
      val expectedParameterX = method.expandAst(NodeTypes.METHOD_PARAMETER_IN)
      expectedParameterX.checkForSingle(NodeKeys.NAME, "x")
      parameterX shouldBe expectedParameterX

      val outerIdentifierY = method.expandAst().expandAst().filterOrder(4).expandAst(NodeTypes.IDENTIFIER)
      outerIdentifierY.checkForSingle(NodeKeys.NAME, "y")
      val outerLocalY = outerIdentifierY.expandRef()
      outerLocalY.checkForSingle(NodeTypes.LOCAL, NodeKeys.NAME, "y")
      val expectedOuterLocalY = method.expandAst().expandAst(NodeTypes.LOCAL)
      expectedOuterLocalY.checkForSingle(NodeKeys.NAME, "y")
      outerLocalY shouldBe expectedOuterLocalY

      val nestedBlock = method.expandAst().expandAst(NodeTypes.BLOCK)

      val nestedIdentifierX = nestedBlock.expandAst().filterOrder(3).expandAst(NodeTypes.IDENTIFIER)
      nestedIdentifierX.checkForSingle(NodeKeys.NAME, "x")
      val nestedLocalX = nestedIdentifierX.expandRef()
      nestedLocalX.checkForSingle(NodeTypes.LOCAL, NodeKeys.NAME, "x")
      val expectedNestedLocalX = nestedBlock.expandAst(NodeTypes.LOCAL).filterName("x")
      expectedNestedLocalX.checkForSingle(NodeKeys.NAME, "x")
      nestedLocalX shouldBe expectedNestedLocalX

      val nestedIdentifierY = nestedBlock.expandAst().filterOrder(4).expandAst(NodeTypes.IDENTIFIER)
      nestedIdentifierY.checkForSingle(NodeKeys.NAME, "y")
      val nestedLocalY = nestedIdentifierY.expandRef()
      nestedLocalY.checkForSingle(NodeTypes.LOCAL, NodeKeys.NAME, "y")
      val expectedNestedLocalY = nestedBlock.expandAst(NodeTypes.LOCAL).filterName("y")
      expectedNestedLocalY.checkForSingle(NodeKeys.NAME, "y")
      nestedLocalY shouldBe expectedNestedLocalY
    }
  }

}
