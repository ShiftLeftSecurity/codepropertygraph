package io.shiftleft.passes

import better.files.File
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.codepropertygraph.generated.nodes
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.util.ConcurrentModificationException
import scala.jdk.CollectionConverters._

class CpgPassTests extends AnyWordSpec with Matchers {

  private object Fixture {
    def apply(keyPool: Option[KeyPool] = None)(f: (Cpg, CpgPassBase) => Unit): Unit = {
      val cpg = Cpg.emptyCpg
      class MyPass(cpg: Cpg) extends CpgPass(cpg, "MyPass", keyPool) {
        override def run(): Iterator[DiffGraph] = {
          val diffGraph1 = DiffGraph.newBuilder
          val diffGraph2 = DiffGraph.newBuilder
          diffGraph1.addNode(nodes.NewFile().name("foo"))
          diffGraph2.addNode(nodes.NewFile().name("bar"))
          Iterator(diffGraph1.build(), diffGraph2.build())
        }
      }
      val pass = new MyPass(cpg)
      f(cpg, pass)
    }
  }

  "CpgPass" should {
    "allow creating and applying result of pass" in Fixture() { (cpg, pass) =>
      pass.createAndApply()
      cpg.graph.V().asScala.map(_.label).toSet shouldBe Set("FILE")
    }

    "produce a serialized inverse CPG" in Fixture() { (_, pass) =>
      File.usingTemporaryFile("pass", ".zip") { file =>
        file.delete()
        val filename = file.path.toString
        val serializedCpg = new SerializedCpg(filename)
        pass.createApplySerializeAndStore(serializedCpg, true)
        serializedCpg.close()
        file.exists shouldBe true
        file.size should not be 0
      }
    }

    /* It is totally OK to stop interleaving diffgraph application and iterator draining.
     * If this tests breaks, just change its expectations -- it is simply intended to document current behavior.*/
    "have expected interleaving behavior between diffgraph application and iterator draining" in {
      class InitGraph(cpg: Cpg) extends CpgPass(cpg) {
        override def run(): Iterator[DiffGraph] = {
          val diffGraph = DiffGraph.newBuilder
          diffGraph.addNode(nodes.NewFile().name("foo").build)
          diffGraph.addNode(nodes.NewFile().name("bar").build)
          Iterator(diffGraph.build())
        }
      }
      class BadUpdate(cpg: Cpg) extends CpgPass(cpg) {
        override def run(): Iterator[DiffGraph] =
          cpg.graph.nodes(NodeTypes.FILE).asScala.asInstanceOf[Iterator[nodes.File]].map { file =>
            val diffGraph = DiffGraph.newBuilder
            diffGraph.addNode(nodes.NewFile().name(file.name + "2"))
            diffGraph.build()
          }
      }

      val cpg = Cpg.emptyCpg
      new InitGraph(cpg).createAndApply()
      //with interleaving:
      an[ConcurrentModificationException] should be thrownBy { new BadUpdate(cpg).createAndApply() }
      cpg.graph.nodes().asScala.size shouldBe 3
      //without interleaving:
      /*
      new BadUpdate(cpg).createAndApply()
      cpg.graph.nodes().asScala.size shouldBe 4
     */
    }

    "take into account KeyPool for createAndApply" in Fixture(Some(new IntervalKeyPool(100, 120))) { (cpg, pass) =>
      pass.createAndApply()
      cpg.graph.V.asScala.map(_.id()).toSet shouldBe Set(100, 101)
    }
  }

}
