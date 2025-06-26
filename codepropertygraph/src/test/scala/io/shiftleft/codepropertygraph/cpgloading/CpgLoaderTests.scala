package io.shiftleft.codepropertygraph.cpgloading

import flatgraph.{Accessors, DiffGraphApplier}
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NewMethod
import io.shiftleft.utils.{ProjectRoot, TempFileCopy}
import io.shiftleft.codepropertygraph.generated.nodes.Type
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.FileNotFoundException
import java.nio.file.{Files, Path, Paths}
import scala.compiletime.uninitialized
import scala.util.Using

/** CpgLoader allows CPGs to be loaded from the CPG protobuf file format (based on Google protocol buffers), overflowdb
  * and flatgraph binaries.
  */
class CpgLoaderTests extends AnyWordSpec with Matchers with BeforeAndAfterAll {

  var cpgProtoFile: Path = uninitialized

  override def beforeAll(): Unit = {
    cpgProtoFile = TestProtoCpg.createTestProtoCpg
    Files.exists(cpgProtoFile) shouldBe true
  }

  override def afterAll(): Unit = {
    Files.delete(cpgProtoFile)
  }

  "allow loading of CPG in proto format (typically cpg.bin.zip)" in {
    Using.resource(CpgLoader.load(cpgProtoFile)) { cpg =>
      cpg.graph.nodes("METHOD").size shouldBe 1
    }
  }

  "allow loading of CPG in overflowdb format" in {
    val odbCpg = ProjectRoot.relativise("codepropertygraph/src/test/resources/cpg.odb")
    Using.resource(CpgLoader.load(odbCpg)) { cpg =>
      cpg.graph.nodes("METHOD").size shouldBe 7
    }
  }

  "allow loading of CPG in flatgraph format" in {
    Using.Manager { use =>
      val flatgraphCpg = use(temporaryFlatgraphCpg()).path
      val cpg          = use(CpgLoader.load(flatgraphCpg))
      cpg.graph.nodes("METHOD").size shouldBe 4
    }.get
  }

  "allow loading of CPG in flatgraph format and persist changes to separate file" in {
    val persistTo = Files.createTempFile(getClass.getSimpleName, "persistToTest")

    Using.resource(CpgLoader.load(flatgraphCpg, persistTo)) { cpg =>
      DiffGraphApplier.applyDiff(cpg.graph, Cpg.newDiffGraphBuilder.addNode(NewMethod()))
    }

    Using.resource(CpgLoader.load(persistTo)) { cpg =>
      cpg.graph.nodes("METHOD").size shouldBe 5
    }

    Using.Manager { use =>
      val flatgraphCpg = use(temporaryFlatgraphCpg()).path
      val cpg          = use(CpgLoader.load(flatgraphCpg))
      // original cpg should be unchanged
      cpg.graph.nodes("METHOD").size shouldBe 4
    }.get
  }

  "throw an appropriate exception if the provided filename that refers to a non-existing file" in {
    a[FileNotFoundException] should be thrownBy CpgLoader.load("invalid/path/cpg.bin.zip")
  }

  /** this test graph was created by c2cpg for https://github.com/joernio/joern/blob/master/tests/code/c/test.c */
  val flatgraphCpg = Paths.get(ProjectRoot.relativise("codepropertygraph/src/test/resources/cpg.fg"))

  /** flatgraph writes to this file on 'close', so we'll create a temporary copy */
  private def temporaryFlatgraphCpg(): TempFileCopy =
    TempFileCopy(flatgraphCpg)
}
