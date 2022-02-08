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

class ParallelCpgPassTests extends AnyWordSpec with Matchers {

  private object Fixture {
    def apply(keyPools: Option[Iterator[KeyPool]] = None)(f: (Cpg, CpgPassBase) => Unit): Unit = {
      val cpg = Cpg.emptyCpg
      class MyPass(cpg: Cpg) extends ParallelCpgPass[String](cpg, "MyPass", keyPools) {
        override def partIterator: Iterator[String] = {
          Iterator("foo", "bar")
        }

        override def runOnPart(part: String): Iterator[DiffGraph] = {
          val diffGraph = DiffGraph.newBuilder
          diffGraph.addNode(NewFile().name(part))
          Iterator(diffGraph.build())
        }
      }
      val pass = new MyPass(cpg)
      f(cpg, pass)
    }
  }

  "ParallelCpgPass" should {
    "allow creating and applying result of pass" in Fixture() { (cpg, pass) =>
      pass.createAndApply()
      cpg.graph.nodes.map(_.property(Properties.NAME)).toSetMutable shouldBe Set("foo", "bar")
    }

    "produce a serialized inverse CPG" in Fixture() { (_, pass) =>
      File.usingTemporaryFile("pass", ".zip") { file =>
        file.delete()
        val filename = file.path.toString
        val serializedCpg = new SerializedCpg(filename)
        pass.createApplySerializeAndStore(serializedCpg, true)
        serializedCpg.close()
        file.exists shouldBe true
        Files.size(file.path) should not be 0
      }
    }

    val keyPools = Iterator(
      new IntervalKeyPool(10, 20),
      new IntervalKeyPool(30, 40)
    )

    "take into account KeyPools for createAndApply" in Fixture(Some(keyPools)) { (cpg, pass) =>
      pass.createAndApply()
      cpg.graph.V.asScala.map(_.id()).toSet shouldBe Set(10, 30)
    }

    "fail for schema violations" in {
      val cpg = Cpg.emptyCpg
      val pass = new ParallelCpgPass[String](cpg, "pass2") {
        def partIterator = Iterator("a", "b")
        def runOnPart(part: String): Iterator[DiffGraph] =
          part match {
            case "a" =>
              // this is fine
              val diffGraph = DiffGraph.newBuilder
              diffGraph.addNode(NewFile().name(part))
              Iterator(diffGraph.build())
            case "b" =>
              // schema violation
              val file1 = NewFile().name("foo")
              val file2 = NewFile().name("bar")
              Iterator(
                DiffGraph.newBuilder
                  .addNode(file1)
                  .addNode(file2)
                  .addEdge(file1, file2, "illegal_edge_label")
                  .build()
              )
          }
      }

      // the above DiffGraph (part "b") is not schema conform, applying it must throw an exception
      intercept[Exception] {
        pass.createAndApply()
      }
    }

    "add NewNodes that are referenced in different parts multiple times" in {
      val cpg = Cpg.emptyCpg
      val pass = new ParallelCpgPass[String](cpg, "pass2") {
        val call1 = NewCall().name("call1")
        val call2 = NewCall().name("call2")
        val call3 = NewCall().name("call3")

        def partIterator = Iterator("a", "b")
        def runOnPart(part: String): Iterator[DiffGraph] =
          part match {
            case "a" =>
              Iterator(DiffGraph.newBuilder.addEdge(call1, call2, "AST").build())
            case "b" =>
              Iterator(DiffGraph.newBuilder.addEdge(call2, call3, "AST").build())
          }
      }
      pass.createAndApply()
      cpg.graph.nodeCount() shouldBe 4
    }
  }

}
