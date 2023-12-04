package io.shiftleft.codepropertygraph.cpgloading

import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

/** Specification of the CPGLoader. The loader allows CPGs to be loaded from the CPG protobuf file format (based on
  * Google protocol buffers). An optional `CpgLoaderConfig` can be passed to the loader to influence the loading
  * process.
  */
class CpgLoaderTests extends AnyWordSpec with Matchers with BeforeAndAfterAll {

  var zipFile: better.files.File = _

  override def beforeAll(): Unit = {
    zipFile = TestProtoCpg.createTestProtoCpg
  }

  override def afterAll(): Unit = {
    zipFile.delete()
  }

  "CpgLoader" should {

    /** CpgLoader receives the filename of the CPG that is to be loaded.
      */
    "allow loading of CPG from bin.zip file" in {
      zipFile.exists shouldBe true
      val cpg = CpgLoader.load(zipFile.pathAsString)
      cpg.graph.allNodes.hasNext shouldBe true
    }

    "throw an appropriate exception if the provided filename that refers to a non-existing file" in {
      an[Exception] should be thrownBy CpgLoader.load("invalid/path/cpg.bin.zip")
    }
  }

}
