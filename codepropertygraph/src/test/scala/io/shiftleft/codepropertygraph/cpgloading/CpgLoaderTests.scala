package io.shiftleft.codepropertygraph.cpgloading

import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb.Config
import scala.compiletime.uninitialized

/** Specification of the CPGLoader. The loader allows CPGs to be loaded from the CPG protobuf file format (based on
  * Google protocol buffers). An optional `CpgLoaderConfig` can be passed to the loader to influence the loading
  * process.
  */
class CpgLoaderTests extends AnyWordSpec with Matchers with BeforeAndAfterAll {

  var zipFile: better.files.File = uninitialized

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
      cpg.graph.nodes.hasNext shouldBe true
    }

    "throw an appropriate exception if the provided filename that refers to a non-existing file" in {
      an[Exception] should be thrownBy CpgLoader.load("invalid/path/cpg.bin.zip")
    }

    /** By default, the CPG returned by the CpgLoader will NOT be backed by overflowdb
      *   - limiting usage to graphs that fit into RAM. Overflowdb can be enabled by passing a CpgLoaderConfig as
      *     follows.
      */
    "allow disabling the overflowdb backend" in {
      val config = new CpgLoaderConfig(overflowDbConfig = new Config())
      val cpg    = CpgLoader.load(zipFile.pathAsString, config)
      cpg.graph.nodes.hasNext shouldBe true
    }

    /** By default, indexes will be created for the CPG in order to increase performance of traversals. The downside is
      * that graph modifications become more expensive as indexes need to be updated on each modification. If a large
      * number of updates is to be performed on the CPG after loading it, it may make sense to defer creation of indexes
      * such that is is performed after modifications have been made.
      */
    "allow late creation of indexes" in {
      // Do not create indexes on load
      val config = new CpgLoaderConfig(createIndexes = false)
      val cpg    = CpgLoader.load(zipFile.pathAsString, config)

      // ... execute lots of operations on the graph
      cpg.graph.addNode("METHOD")
      // ...

      cpg.graph.indexManager.getIndexedNodeProperties.size shouldBe 0
      // Now create indexes
      CpgLoader.createIndexes(cpg)
      cpg.graph.indexManager.getIndexedNodeProperties.toArray shouldBe Array("FULL_NAME")
    }

  }

}
