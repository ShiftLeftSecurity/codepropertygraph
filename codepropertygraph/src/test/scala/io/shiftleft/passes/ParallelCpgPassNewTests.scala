package io.shiftleft.passes

import better.files.File
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.codepropertygraph.generated.Properties
import io.shiftleft.codepropertygraph.generated.language.*
import io.shiftleft.codepropertygraph.generated.nodes.{NewCall, NewFile}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.nio.file.Files
import scala.jdk.CollectionConverters._

class ForkJoinParallelCpgPassNewTests extends AnyWordSpec with Matchers {

  private object Fixture {
    def apply(timeout: Long = -1)(f: (Cpg, CpgPassBase) => Unit): Unit = {
      val cpg = Cpg.empty
      class MyPass(cpg: Cpg) extends ForkJoinParallelCpgPassWithTimeout[String](cpg, "MyPass", timeout = timeout) {
        override def generateParts(): Array[String] = Range(1, 101).map(_.toString).toArray

        override def runOnPart(diffGraph: DiffGraphBuilder, part: String): Unit = {
          Thread.sleep(1000)
          diffGraph.addNode(NewFile().name(part))
        }
      }
      val pass = new MyPass(cpg)
      f(cpg, pass)
    }
  }

  "ForkJoinParallelPassWithTimeout" should {
    "generate partial result in case of timeout" in Fixture(timeout = 2) { (cpg, pass) =>
      pass.createAndApply()
      assert(cpg.all.map(_.property(Properties.Name)).toList.size != 100)
    }

    "generate complete result without timeout" in Fixture() { (cpg, pass) =>
      pass.createAndApply()
      assert(cpg.all.map(_.property(Properties.Name)).toList.size == 100)
    }
  }

}
