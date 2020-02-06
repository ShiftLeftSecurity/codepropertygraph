package io.shiftleft.codepropertygraph.cpgloading

import java.io.File
import java.nio.file.Files

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeKeys
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.Assertions._


class ProtoToOverflowDbTest extends WordSpec {

//  "imports cpg.bin.zip into overflowdb.bin" in {
//    val cpgBinZip = "resources/testcode/cpgs/hello-shiftleft-0.0.5/cpg.bin.zip"
//    val reference =
//      CpgLoader.load(cpgBinZip, CpgLoaderConfig.withoutOverflow).scalaGraph
//
//    val referenceNodeCount: Long = reference.V.count.head
//    val referenceEdgeCount: Long = reference.E.count.head
//    val referenceSpecificPropertyCount: Long = reference.V.has(NodeKeys.NAME, "java.lang").count.head
//
//    // sanity check to ensure cpg looks sane
//    assert(referenceNodeCount.toInt > 50)
//    assert(referenceEdgeCount.toInt > 50)
//    assert(referenceSpecificPropertyCount.toInt > 10)
//
//    val overflowdbFile: File =
//      ProtoToOverflowDb.run(Config(new File(cpgBinZip), Some(Files.createTempFile("overflowdb", "bin").toFile)))
//    overflowdbFile.deleteOnExit()
//    val fromStorage = Cpg.withStorage(overflowdbFile.getAbsolutePath).scalaGraph
//
//    assert(fromStorage.V.count.head == referenceNodeCount)
//
//    assert(fromStorage.E.count.head == referenceEdgeCount)
//
//    assert(fromStorage.V.has(NodeKeys.NAME, "java.lang").count.head == referenceSpecificPropertyCount)
//
//  }
}
