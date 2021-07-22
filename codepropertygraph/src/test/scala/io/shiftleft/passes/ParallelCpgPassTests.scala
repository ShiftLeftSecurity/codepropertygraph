package io.shiftleft.passes

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Properties
import io.shiftleft.codepropertygraph.generated.nodes.NewFile
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb.traversal._

import scala.jdk.CollectionConverters._

class ParallelCpgPassTests extends AnyWordSpec with Matchers {

  private object Fixture {
    def apply(keyPools: Option[Iterator[KeyPool]] = None)(f: (Cpg, CpgPassBase) => Unit): Unit = {
      val cpg = Cpg.emptyCpg
      class MyPass extends ParallelCpgPass[String](keyPools) {
        override def partIterator: Iterator[String] = {
          Iterator("foo", "bar")
        }

        override def runOnPart(part: String): Iterator[DiffGraph] = {
          val diffGraph = DiffGraph.newBuilder
          diffGraph.addNode(NewFile().name(part))
          Iterator(diffGraph.build())
        }
      }
      val pass = new MyPass()
      f(cpg, pass)
    }
  }

  "ParallelCpgPass" should {
    "allow creating and applying result of pass" in Fixture() { (cpg, pass) =>
      CpgPassRunner.apply(cpg, pass)
      cpg.graph.nodes.map(_.property(Properties.NAME)).toSet shouldBe Set("foo", "bar")
    }

    "produce a serialized inverse CPG" in Fixture() { (cpg, pass) =>
      File.usingTemporaryDirectory("cpgPassTests") { dir =>
        val file = dir / "0_io.shiftleft.passes.ParallelCpgPassTests$Fixture$MyPass$1"
        CpgPassRunner.applyAndStore(cpg, pass, dir.toString, false)
        file.exists shouldBe true
        file.size should not be 0
      }
    }

    val keyPools = Iterator(
      new IntervalKeyPool(10, 20),
      new IntervalKeyPool(30, 40)
    )

    "take into account KeyPools for createAndApply" in Fixture(Some(keyPools)) { (cpg, pass) =>
      CpgPassRunner.apply(cpg, pass)
      cpg.graph.V.asScala.map(_.id()).toSet shouldBe Set(10, 30)
    }

  }

}
