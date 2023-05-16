package io.shiftleft.passes

import better.files.File
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Properties
import io.shiftleft.codepropertygraph.generated.nodes.{NewCall, NewFile}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb.traversal._

import java.nio.file.Files
import scala.jdk.CollectionConverters._

class ParallelCpgPassNewTests extends AnyWordSpec with Matchers {

  private object Fixture {
    def apply(keyPools: Option[Iterator[KeyPool]] = None)(f: (Cpg, CpgPassBase) => Unit): Unit = {
      val cpg  = Cpg.emptyCpg
      val pool = keyPools.flatMap(_.nextOption())
      class MyPass(cpg: Cpg) extends ConcurrentWriterCpgPass[String](cpg, "MyPass", pool) {
        override def generateParts(): Array[String] = Array("foo", "bar")

        override def runOnPart(diffGraph: DiffGraphBuilder, part: String): Unit = {
          diffGraph.addNode(NewFile().name(part))
        }
      }
      val pass = new MyPass(cpg)
      f(cpg, pass)
    }
  }

  "ConcurrentWriterCpgPass" should {
    "allow creating and applying result of pass" in Fixture() { (cpg, pass) =>
      pass.createAndApply()
      cpg.graph.nodes.map(_.property(Properties.NAME)).toSetMutable shouldBe Set("foo", "bar")
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

    val keyPools = Iterator(new IntervalKeyPool(10, 20), new IntervalKeyPool(30, 40))

    "use only the first KeyPool for createAndApply" in Fixture(Some(keyPools)) { (cpg, pass) =>
      pass.createAndApply()
      cpg.graph.V.asScala.map(_.id()).toSet shouldBe Set(10, 11)
    }

    "fail for schema violations" in {
      val cpg = Cpg.emptyCpg
      val pass = new ConcurrentWriterCpgPass[String](cpg, "pass2") {
        override def generateParts() = Array("a", "b")
        override def runOnPart(diffGraph: DiffGraphBuilder, part: String): Unit =
          part match {
            case "a" =>
              // this is fine
              diffGraph.addNode(NewFile().name(part))
            case "b" =>
              // schema violation
              val file1 = NewFile().name("foo")
              val file2 = NewFile().name("bar")
              diffGraph
                .addNode(file1)
                .addNode(file2)
                .addEdge(file1, file2, "illegal_edge_label")

          }
      }

      // the above DiffGraph (part "b") is not schema conform, applying it must throw an exception
      intercept[Exception] {
        pass.createAndApply()
      }
    }

    "add NewNodes that are referenced in different parts only once" in {
      val cpg = Cpg.emptyCpg
      val pass = new ConcurrentWriterCpgPass[String](cpg, "pass2") {
        val call1 = NewCall().name("call1")
        val call2 = NewCall().name("call2")
        val call3 = NewCall().name("call3")

        override def generateParts() = Array("a", "b")
        override def runOnPart(diffGraph: DiffGraphBuilder, part: String): Unit =
          part match {
            case "a" =>
              diffGraph.addEdge(call1, call2, "AST")
            case "b" =>
              diffGraph.addEdge(call2, call3, "AST")
          }
      }
      pass.createAndApply()
      cpg.graph.nodeCount() shouldBe 3
    }
  }

}
