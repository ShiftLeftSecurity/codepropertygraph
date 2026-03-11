package io.shiftleft.passes

import flatgraph.SchemaViolationException
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.codepropertygraph.generated.language.*
import io.shiftleft.codepropertygraph.generated.nodes.NewFile
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ArrayBuffer

class CpgPassNewTests extends AnyWordSpec with Matchers {

  private object Fixture {
    def apply(f: (Cpg, CpgPassBase) => Unit): Unit = {
      val cpg = Cpg.empty
      class MyPass(cpg: Cpg) extends CpgPass(cpg, "MyPass") {
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

    "fail for schema violations" in {
      val cpg = Cpg.empty
      val pass = new CpgPass(cpg, "pass1") {
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

    "call init and finish once around run" in {
      val cpg    = Cpg.empty
      val events = ArrayBuffer.empty[String]
      val pass: ForkJoinParallelCpgPass[String] = new ForkJoinParallelCpgPass[String](cpg, "lifecycle-pass") {
        override def init(): Unit                                             = events += "init"
        override def generateParts(): Array[String]                           = Array("p1")
        override def runOnPart(builder: DiffGraphBuilder, part: String): Unit = events += "run"
        override def finish(): Unit                                           = events += "finish"
      }

      pass.createAndApply()

      // all events should be in the expected order and should only occur once
      events.toSeq shouldBe Seq("init", "run", "finish")
    }

    "call finish once when run fails" in {
      val cpg    = Cpg.empty
      val events = ArrayBuffer.empty[String]
      val pass: ForkJoinParallelCpgPass[String] = new ForkJoinParallelCpgPass[String](cpg, "failing-lifecycle-pass") {
        override def init(): Unit                   = events += "init"
        override def generateParts(): Array[String] = Array("p1")
        override def runOnPart(builder: DiffGraphBuilder, part: String): Unit = {
          events += "run"
          throw new RuntimeException("run failed")
        }
        override def finish(): Unit = events += "finish"
      }

      intercept[RuntimeException] {
        pass.createAndApply()
      }

      // all events should be in the expected order and should only occur once even if run fails
      events.toSeq shouldBe Seq("init", "run", "finish")
    }
  }

}
