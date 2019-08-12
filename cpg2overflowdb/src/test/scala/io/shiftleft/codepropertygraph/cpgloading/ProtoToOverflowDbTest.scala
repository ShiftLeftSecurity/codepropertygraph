package io.shiftleft.codepropertygraph.cpgloading

import java.io.File
import java.nio.file.Files

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeKeys
import org.scalatest.{Matchers, WordSpec}

class ProtoToOverflowDbTest extends WordSpec with Matchers {

  "imports cpg.bin.zip into overflowdb.bin" ignore {
    val cpgBinZip = "resources/cpgs/namespace/cpg.bin.zip"
    val reference =
      CpgLoader.load(cpgBinZip, CpgLoaderConfig().withOverflowConfig(OverflowDbConfig.disabled)).scalaGraph

    val referenceNodeCount = reference.V.count.head
    val referenceEdgeCount = reference.E.count.head
    val referenceSpecificPropertyCount = reference.V.has(NodeKeys.NAME, "java.lang").count.head

    // sanity check to ensure cpg looks sane
    referenceNodeCount.toInt should be > 50
    referenceEdgeCount.toInt should be > 50
    referenceSpecificPropertyCount.toInt should be > 10

    val overflowdbFile =
      ProtoToOverflowDb.run(Config(new File(cpgBinZip), Some(Files.createTempFile("overflowdb", "bin").toFile)))
    val fromStorage = Cpg.withStorage(overflowdbFile.getAbsolutePath).scalaGraph
    fromStorage.V.count.head shouldBe referenceNodeCount
    fromStorage.E.count.head shouldBe referenceEdgeCount
    fromStorage.V.has(NodeKeys.NAME, "java.lang").count.head shouldBe referenceSpecificPropertyCount

    overflowdbFile.deleteOnExit
  }
}
