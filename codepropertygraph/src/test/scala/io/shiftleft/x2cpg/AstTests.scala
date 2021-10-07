package io.shiftleft.x2cpg

import io.shiftleft.codepropertygraph.generated.nodes.{AstNodeNew, NewCall, NewIdentifier}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class AstTests extends AnyWordSpec with Matchers {

  "subTreeCopy" should {

    val testTree = Ast(NewCall().name("foo"))
      .withChildren(
        List(
          Ast(NewCall().name("bar").order(1))
            .withChild(Ast(NewIdentifier().name("idname").order(1))),
          Ast(NewCall().name("moo").order(1))
            .withChild(
              Ast(NewCall().name("callincall").order(1))
                .withChild(Ast(NewIdentifier().name("leaf").order(1)))
            ),
        )
      )

    "copy sub tree correctly" in {
      val moo = testTree.nodes.filter(x => x.properties("NAME") == "moo").head.asInstanceOf[NewCall]
      val leaf = testTree.nodes.filter(x => x.properties("NAME") == "leaf").head.asInstanceOf[NewIdentifier]
      val copied = testTree.subTreeCopy(moo.asInstanceOf[AstNodeNew], 123)

      copied.root match {
        case Some(root: NewCall) =>
          root should not be Some(moo)
          root.properties("NAME") shouldBe "moo"
          root.order shouldBe 123
          root.argumentIndex shouldBe 123
        case _ => fail()
      }

      val List(_, callincallClone: NewCall, leafClone: NewIdentifier) = copied.nodes
      callincallClone.order shouldBe 1
      leafClone.order shouldBe 1
      copied.edges.filter(_.src == callincallClone).map(_.dst) match {
        case List(x: NewIdentifier) =>
          x shouldBe leafClone
          x should not be leaf
          x.name shouldBe "leaf"
        case _ => fail()
      }
    }
  }
}
