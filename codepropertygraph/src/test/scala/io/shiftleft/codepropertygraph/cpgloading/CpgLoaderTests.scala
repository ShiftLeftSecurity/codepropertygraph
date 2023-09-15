package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.utils.ProjectRoot
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.FileNotFoundException
import java.nio.file.{Files, Path, Paths}

/** Specification of the CPGLoader. The loader allows CPGs to be loaded from the CPG protobuf file format (based on
  * Google protocol buffers). An optional `CpgLoaderConfig` can be passed to the loader to influence the loading
  * process.
  */
class CpgLoaderTests extends AnyWordSpec with Matchers with BeforeAndAfterAll {

  var cpgProtoFile: Path = _

  override def beforeAll(): Unit = {
    cpgProtoFile = TestProtoCpg.createTestProtoCpg
    Files.exists(cpgProtoFile) shouldBe true
  }

  override def afterAll(): Unit = {
    Files.delete(cpgProtoFile)
  }

  "CpgLoader" should {

    "allow loading of CPG in proto format (typically cpg.bin.zip)" in {
      val cpg = CpgLoader.load(cpgProtoFile)
      cpg.graph.nodes("METHOD").size shouldBe 1
    }

    "allow loading of CPG in overflowdb format" in {
      val odbCpg = ProjectRoot.relativise("codepropertygraph/src/test/resources/cpg.odb")
      val cpg = CpgLoader.load(Paths.get(odbCpg))
      cpg.graph.nodes("METHOD").size shouldBe 7
    }

    "allow loading of CPG in flatgraph" in {
      val odbCpg = ProjectRoot.relativise("codepropertygraph/src/test/resources/cpg.fg")
      val cpg = CpgLoader.load(Paths.get(odbCpg))
      cpg.graph.nodes("METHOD").size shouldBe 7
    }

    "throw an appropriate exception if the provided filename that refers to a non-existing file" in {
      a[FileNotFoundException] should be thrownBy CpgLoader.load("invalid/path/cpg.bin.zip")
    }
  }

}