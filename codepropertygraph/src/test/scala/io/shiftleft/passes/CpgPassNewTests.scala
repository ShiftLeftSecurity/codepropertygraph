package io.shiftleft.passes

import better.files.File
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NewFile
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.nio.file.Files
import scala.jdk.CollectionConverters._

class CpgPassNewTests extends AnyWordSpec with Matchers {

  private object Fixture {
    def apply(keyPool: Option[KeyPool] = None)(f: (Cpg, CpgPassBase) => Unit): Unit = {
      val cpg = Cpg.emptyCpg
      class MyPass(cpg: Cpg) extends SimpleCpgPass(cpg, "MyPass", keyPool) {
        override def run(builder: DiffGraphBuilder): Unit = {
          val builder2 = new DiffGraphBuilder
          builder.addNode(NewFile().name("foo"))
          builder2.addNode(NewFile().name("bar"))
          builder.absorb(builder2)
        }
      }
      val pass = new MyPass(cpg)
      f(cpg, pass)
    }
  }

  "SimpleCpgPass" should {
    "allow creating and applying result of pass" in Fixture() { (cpg, pass) =>
      pass.createAndApply()
      cpg.graph.V().asScala.map(_.label).toSet shouldBe Set("FILE")
    }

    "produce a serialized inverse CPG" in Fixture() { (_, pass) =>
      File.usingTemporaryFile("pass", ".zip") { file =>
        file.delete()
        val filename      = file.path.toString
        val serializedCpg = new SerializedCpg(filename)
        pass.createApplySerializeAndStore(serializedCpg, true)
        serializedCpg.close()
        file.exists shouldBe true
        Files.size(file.path) should not be 0
      }
    }

    "take into account KeyPool for createAndApply" in Fixture(Some(new IntervalKeyPool(100, 120))) { (cpg, pass) =>
      pass.createAndApply()
      cpg.graph.V.asScala.map(_.id()).toSet shouldBe Set(100, 101)
    }

    "fail for schema violations" in {
      val cpg = Cpg.emptyCpg
      val pass = new SimpleCpgPass(cpg, "pass1") {
        override def run(dst: DiffGraphBuilder): Unit = {
          val file1 = NewFile().name("foo")
          val file2 = NewFile().name("bar")
          dst
            .addNode(file1)
            .addNode(file2)
            .addEdge(file1, file2, "illegal_edge_label")
        }
      }

      // the above DiffGraph is not schema conform, applying it must throw an exception
      intercept[Exception] {
        pass.createAndApply()
      }
    }
  }

}
