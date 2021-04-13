package io.shiftleft.passes

import better.files.File
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{Properties, nodes}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb.traversal._

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
          diffGraph.addNode(nodes.NewFile().name(part))
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
      cpg.graph.nodes.map(_.property(Properties.NAME)).toSet shouldBe Set("foo", "bar")
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

    val keyPools = Iterator(
      new IntervalKeyPool(10, 20),
      new IntervalKeyPool(30, 40)
    )

    "take into account KeyPools for createAndApply" in Fixture(Some(keyPools)) { (cpg, pass) =>
      pass.createAndApply()
      cpg.graph.V.asScala.map(_.id()).toSet shouldBe Set(10, 30)
    }

  }

}
