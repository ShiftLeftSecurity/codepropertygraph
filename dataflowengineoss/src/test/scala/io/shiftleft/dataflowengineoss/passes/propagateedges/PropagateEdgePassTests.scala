package io.shiftleft.dataflowengineoss.passes.propagateedges

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import io.shiftleft.semanticcpg.layers.{LayerCreator, LayerCreatorContext, Scpg}
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._
import io.shiftleft.codepropertygraph.generated.nodes

class PropagateEdgePassTests extends PropagateEdgeTestSuite {

  val semantics: Semantics = Semantics(
    List(
      FlowSemantic("copy", List((2, 1))),
      FlowSemantic("escape", List((1, -1)))
    )
  )

  override val code =
    """
      |int foo(int x, int y) {
      |  int err = copy(x, y);
      |  char *foo = escape(x);
      |}
      |
      |""".stripMargin

  "PropagateEdgePass" should {

    "create an edge from second input to first output parameter for `copy`" in {
      cpg.method
        .name("copy")
        .parameter
        .order(2)
        .l
        .flatMap(_._propagateOut().asScala) match {
        case List(x: nodes.MethodParameterOut) => x.order shouldBe 1
        case _                                 => fail
      }
    }

    "create an edge from the first input parameter to return for `escape`" in {
      cpg.method.name("escape").parameter.order(1).l.flatMap(_._propagateOut().asScala) match {
        case List(x: nodes.MethodReturn) =>
          x.method.name shouldBe "escape"
        case _ =>
          fail
      }
    }

  }

}

abstract class PropagateEdgeTestSuite extends CodeToCpgSuite {

  val semantics: Semantics

  override def passes(cpg: Cpg): Unit = {
    val context = new LayerCreatorContext(cpg)
    new Scpg().run(context)
    new PropLayerCreator(semantics).run(context)
  }

  private class PropLayerCreator(semantics: Semantics) extends LayerCreator {
    override val overlayName: String = ""
    override val description: String = ""

    override def create(context: LayerCreatorContext, serializeInverse: Boolean): Unit = {
      val enhancementExecList = Iterator(new PropagateEdgePass(cpg, semantics))
      enhancementExecList.foreach(_.createAndApply())
    }

    override def probe(cpg: Cpg): Boolean = false
  }
}
