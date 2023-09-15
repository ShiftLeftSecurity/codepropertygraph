package io.shiftleft.passes

import better.files.File
import flatgraph.SchemaViolationException
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NewFile
import io.shiftleft.codepropertygraph.generated.Language.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.nio.file.Files

class CpgPassNewTests extends AnyWordSpec with Matchers {

  private object Fixture {
    def apply(f: (Cpg, CpgPassBase) => Unit): Unit = {
      val cpg = Cpg.empty
      class MyPass(cpg: Cpg) extends SimpleCpgPass(cpg, "MyPass") {
        override def run(builder: DiffGraphBuilder): Unit = {
          val builder2 = Cpg.newDiffGraphBuilder
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
    "allow creating and applying result of pass" in Fixture { (cpg, pass) =>
      pass.createAndApply()
      cpg.all.label.toSet shouldBe Set("FILE")
    }

    "produce a serialized CPG file" in Fixture { (_, pass) =>
      File.usingTemporaryFile("pass", ".zip") { file =>
        file.delete()
        val filename      = file.path.toString
        val serializedCpg = new SerializedCpg(filename)
        pass.createApplySerializeAndStore(serializedCpg)
        serializedCpg.close()
        file.exists shouldBe true
        Files.size(file.path) should not be 0
      }
    }

    "fail for schema violations" in {
      val cpg = Cpg.empty
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
      intercept[SchemaViolationException] {
        pass.createAndApply()
      }
    }
  }

}
