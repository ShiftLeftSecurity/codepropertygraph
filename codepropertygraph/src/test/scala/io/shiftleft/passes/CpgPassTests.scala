package io.shiftleft.passes

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NewFile
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.jdk.CollectionConverters._

class CpgPassTests extends AnyWordSpec with Matchers {

  private object Fixture {
    def apply(keyPool: Option[KeyPool] = None)(f: (Cpg, CpgPassBase[_]) => Unit): Unit = {
      val cpg = Cpg.emptyCpg
      class MyPass extends CpgPass(keyPool) {
        override def run(): Iterator[DiffGraph] = {
          val diffGraph1 = DiffGraph.newBuilder
          val diffGraph2 = DiffGraph.newBuilder
          diffGraph1.addNode(NewFile().name("foo"))
          diffGraph2.addNode(NewFile().name("bar"))
          Iterator(diffGraph1.build(), diffGraph2.build())
        }
      }
      val pass = new MyPass()
      f(cpg, pass)
    }
  }

  "CpgPass" should {
    "allow creating and applying result of pass" in Fixture() { (cpg, pass) =>
      CpgPassRunner.apply(cpg, pass)
      cpg.graph.V().asScala.map(_.label).toSet shouldBe Set("FILE")
    }

    "produce a serialized inverse CPG" in Fixture() { (cpg, pass) =>
      File.usingTemporaryDirectory("cpgPassTests") { dir =>
        val file = dir / "0_io.shiftleft.passes.CpgPassTests$Fixture$MyPass$1"
        CpgPassRunner.applyAndStore(cpg, pass, dir.toString, false)
        file.exists shouldBe true
        file.size should not be 0
      }
    }

    "take into account KeyPool for createAndApply" in Fixture(Some(new IntervalKeyPool(100, 120))) { (cpg, pass) =>
      CpgPassRunner.apply(cpg, pass)
      cpg.graph.V.asScala.map(_.id()).toSet shouldBe Set(100, 101)
    }
  }

}
