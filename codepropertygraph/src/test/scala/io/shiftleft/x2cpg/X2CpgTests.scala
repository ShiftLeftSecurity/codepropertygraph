package io.shiftleft.x2cpg

import better.files.File
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class X2CpgTests extends AnyWordSpec with Matchers {

  "initCpg" should {

    "create an empty in-memory CPG when no output path is given" in {
      val cpg = X2Cpg.newEmptyCpg(None)
      cpg.graph.V.hasNext shouldBe false
      cpg.graph.E.hasNext shouldBe false
      cpg.close()
    }

    "create file if it does not exist" in {
      val file = File.newTemporaryFile("x2cpg")
      file.delete()
      file.exists shouldBe false
      val cpg = X2Cpg.newEmptyCpg(Some(file.path.toString))
      file.exists shouldBe true
      file.size should not be 0
      cpg.close()
    }

    "overwrite existing file to create empty CPG" in {
      File.usingTemporaryFile("x2cpg") { file =>
        file.exists shouldBe true
        file.size shouldBe 0
        val cpg = X2Cpg.newEmptyCpg(Some(file.path.toString))
        cpg.graph.V.hasNext shouldBe false
        cpg.graph.E.hasNext shouldBe false
        file.exists shouldBe true
        file.size should not be 0
        cpg.close()
      }
    }
  }
}
