package io.shiftleft.codepropertygraph.cpgloading

import flatgraph.{Accessors, DiffGraphApplier}
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NewMethod
import io.shiftleft.utils.ProjectRoot
import io.shiftleft.codepropertygraph.generated.nodes.Type
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.FileNotFoundException
import java.nio.file.{Files, Path, Paths}
import scala.util.Using

/** CpgLoader allows CPGs to be loaded from the CPG protobuf file format (based on Google protocol buffers),
 * overflowdb and flatgraph binaries.
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

  "allow loading of CPG in proto format (typically cpg.bin.zip)" in {
    Using.resource(CpgLoader.load(cpgProtoFile)) { cpg =>
      cpg.graph.nodes("METHOD").size shouldBe 1
    }
  }
  
  "allow loading from proto.bin.zip file created by java2cpg" in {
    val cpgPath = ProjectRoot.relativise("codepropertygraph/src/test/resources/java2cpg.bin.zip")
    val cpg = CpgLoader.load(cpgPath)
    // cpg.graph.nodeCount() shouldBe 209
    cpg.graph.nodeCount() shouldBe 208 // ignoring one packagePrefix node
    cpg.graph.nodeCount(Type.Label) shouldBe 4

    import io.shiftleft.codepropertygraph.generated.Language.*
    import io.shiftleft.codepropertygraph.generated.nodes
//    cpg.graph.nodes("TYPE").map(_.property(Properties.FULL_NAME)).toSeq.sorted shouldBe Seq("TODO")
    cpg.graph.nodes("TYPE").map(node => node.asInstanceOf[nodes.Type].fullName).toSeq.sorted shouldBe Seq("TODO")

    ??? // TODO cleanup/remove - this test is using the internal schema...
  }

  "allow loading of CPG in overflowdb format" in {
    val odbCpg = ProjectRoot.relativise("codepropertygraph/src/test/resources/cpg.odb")
    Using.resource(CpgLoader.load(odbCpg)) { cpg =>
      cpg.graph.nodes("METHOD").size shouldBe 7
    }
  }

  "allow loading of CPG in flatgraph format" in {
    val flatgraphCpg = ProjectRoot.relativise("codepropertygraph/src/test/resources/cpg.fg")
    Using.resource(CpgLoader.load(flatgraphCpg)) { cpg =>
      cpg.graph.nodes("METHOD").size shouldBe 7
    }
  }

  "allow loading of CPG in flatgraph format and persist changes to separate file" in {
    val flatgraphCpg = Paths.get(ProjectRoot.relativise("codepropertygraph/src/test/resources/cpg.fg"))
    val persistTo = Files.createTempFile(getClass.getSimpleName, "persistToTest")

    Using.resource(CpgLoader.load(flatgraphCpg, persistTo)) { cpg =>
      DiffGraphApplier.applyDiff(cpg.graph, Cpg.newDiffGraphBuilder.addNode(NewMethod()))
    }

    Using.resource(CpgLoader.load(persistTo)) { cpg =>
      cpg.graph.nodes("METHOD").size shouldBe 8
    }

    Using.resource(CpgLoader.load(flatgraphCpg)) { cpg =>
      // original cpg should be unchanged
      cpg.graph.nodes("METHOD").size shouldBe 7
    }
  }

  "throw an appropriate exception if the provided filename that refers to a non-existing file" in {
    a[FileNotFoundException] should be thrownBy CpgLoader.load("invalid/path/cpg.bin.zip")
  }

}
