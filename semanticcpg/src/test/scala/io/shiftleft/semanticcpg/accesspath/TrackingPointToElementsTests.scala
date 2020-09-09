package io.shiftleft.semanticcpg.accesspath

import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.generated._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._

class TrackingPointToElementsTests extends AnyWordSpec {

  def E(elements: AccessElement*): Elements = {
    Elements.normalized(elements)
  }

  private val V = VariableAccess
  private val I = IndirectionAccess
  private val C = ConstantAccess
  private val A = AddressOf
  private val VS = VariablePointerShift
  private val S = PointerShift

  private val g = OverflowDbTestInstance.create

  private def genCALL(graph: Graph, op: String, args: Node*): nodes.Call = {
    val ret = graph + NodeTypes.CALL //(NodeTypes.CALL, NodeKeys.NAME -> op)
    ret.setProperty(NodeKeys.NAME, op)
    args.reverse.zipWithIndex.foreach { argTup =>
      val arg = argTup._1
      val idx = argTup._2
      ret --- EdgeTypes.ARGUMENT --> arg
      arg.setProperty(NodeKeys.ARGUMENT_INDEX, new Integer(idx + 1))
    }
    ret.asInstanceOf[nodes.Call]
  }

  private def genLit(graph: Graph, payload: String): nodes.Literal = {
    val ret = graph + NodeTypes.LITERAL
    ret.setProperty(NodeKeys.CODE, payload)
    ret.asInstanceOf[nodes.Literal]
  }

  private def genID(graph: Graph, payload: String): nodes.Identifier = {
    val ret = graph + NodeTypes.IDENTIFIER
    ret.setProperty(NodeKeys.NAME, payload)
    ret.asInstanceOf[nodes.Identifier]
  }

  private def genFID(graph: Graph, payload: String): nodes.FieldIdentifier = {
    val ret = graph + NodeTypes.FIELD_IDENTIFIER
    ret.setProperty(NodeKeys.CANONICAL_NAME, payload)
    ret.asInstanceOf[nodes.FieldIdentifier]
  }

  "memberAccess" should {
    "work" in {
      val call =
        genCALL(g,
                Operators.memberAccess,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), C("a"))
    }
  }

  "indirectMemberAccess" should {
    "work" in {
      val call =
        genCALL(g,
                Operators.indirectMemberAccess,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), C("a"))
    }
  }

  "computedMemberAccess" should {
    "work with Literal" in {
      val call =
        genCALL(g,
                Operators.computedMemberAccess,
                genLit(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), C("a"))
    }
    "overtaint with others" in {
      val call =
        genCALL(g,
                Operators.computedMemberAccess,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), V)
    }
  }

  "indirectComputedMemberAccess" should {
    "work with Literal" in {
      val call =
        genCALL(g,
                Operators.indirectComputedMemberAccess,
                genLit(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), C("a"))
    }
    "overtaint with others" in {
      val call =
        genCALL(g,
                Operators.indirectComputedMemberAccess,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), V)
    }

  }

  "indirection" should {
    "work" in {
      val call =
        genCALL(g, Operators.indirection, genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), I)
    }
  }

  "addressOf" should {
    "work" in {
      val call =
        genCALL(g, Operators.addressOf, genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), A)
    }
  }
  // new style

  "FieldAccess" should {
    "work with Literal" in {
      val call =
        genCALL(g,
                Operators.fieldAccess,
                genLit(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), C("a"))
    }

    "work with FieldIdentifier" in {
      val call =
        genCALL(g,
                Operators.fieldAccess,
                genFID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), C("a"))
    }

    "overtaint with others" in {
      val call =
        genCALL(g,
                Operators.fieldAccess,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), V)
    }
  }

  "IndirectFieldAccess" should {
    "work with Literal" in {
      val call =
        genCALL(g,
                Operators.indirectFieldAccess,
                genLit(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), I, C("a"))
    }

    "work with FieldIdentifier" in {
      val call =
        genCALL(g,
                Operators.indirectFieldAccess,
                genFID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), I, C("a"))
    }

    "overtaint with others" in {
      val call =
        genCALL(g,
                Operators.indirectFieldAccess,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), I, V)
    }
  }

  "indexAccess" should {
    "work with Literal" in {
      val call =
        genCALL(g,
                Operators.indexAccess,
                genLit(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), C("a"))
    }

    "work with FieldIdentifier" in {
      val call =
        genCALL(g,
                Operators.indexAccess,
                genFID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), C("a"))
    }

    "overtaint with others" in {
      val call =
        genCALL(g,
                Operators.indexAccess,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), V)
    }
  }

  "indirectIndexAccess" should {
    "work with Literal" in {
      val call =
        genCALL(g,
                Operators.indirectIndexAccess,
                genLit(g, "12"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), S(12), I)
    }

    "work with FieldIdentifier" in {
      val call =
        genCALL(g,
                Operators.indirectIndexAccess,
                genFID(g, "12"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), S(12), I)
    }

    "overtaint with others" in {
      val call =
        genCALL(g,
                Operators.indirectIndexAccess,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), VS, I)
    }
    "overtaint on parsing failure" in {
      val call =
        genCALL(g,
                Operators.indirectIndexAccess,
                genLit(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), VS, I)
    }

  }

  "pointerShft" should {
    //fixme
    "work with Literal" in {
      val call =
        genCALL(g,
                Operators.pointerShift,
                genLit(g, "12"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), S(12))
    }

    "work with FieldIdentifier" in {
      val call =
        genCALL(g,
                Operators.pointerShift,
                genFID(g, "12"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), S(12))
    }

    "overtaint with others" in {
      val call =
        genCALL(g,
                Operators.pointerShift,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), VS)
    }
    "overtaint with parsing fails" in {
      val call =
        genCALL(g,
                Operators.pointerShift,
                genLit(g, "abc"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), VS)
    }

  }

  "getElementPointer" should {
    //fixme
    "work with Literal" in {
      val call =
        genCALL(g,
                Operators.getElementPtr,
                genLit(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), I, C("a"), A)
    }

    "work with FieldIdentifier" in {
      val call =
        genCALL(g,
                Operators.getElementPtr,
                genFID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), I, C("a"), A)
    }

    "overtaint with others" in {
      val call =
        genCALL(g,
                Operators.getElementPtr,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E(C("b"), I, V, A)
    }
  }

  "Others" should {
    "not expand through" in {
      val call =
        genCALL(g,
                Operators.addition,
                genID(g, "a"),
                genCALL(g, Operators.computedMemberAccess, genLit(g, "b"), genCALL(g, "foo")))

      TrackingPointToElements(call) shouldBe E()
    }
  }

}
