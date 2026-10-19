package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.proto.cpg.Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct

import java.nio.file.{Files, Path}
import java.util.zip.{ZipEntry, ZipOutputStream}

object TestProtoCpg {

  def createTestProtoCpg: Path = {
    val outDir    = Files.createTempDirectory("cpgloadertests")
    val protoFile = outDir.resolve("1.proto")
    val outStream = Files.newOutputStream(protoFile)
    try {
      CpgStruct
        .newBuilder()
        .addNode(
          CpgStruct.Node
            .newBuilder()
            .setKey(1)
            .setType(CpgStruct.Node.NodeType.valueOf("METHOD"))
            .addProperty(
              CpgStruct.Node.Property
                .newBuilder()
                .setName(Cpg.NodePropertyName.valueOf("FULL_NAME"))
                .setValue(
                  Cpg.PropertyValue
                    .newBuilder()
                    .setStringValue("foo")
                    .build()
                )
                .build
            )
            .build()
        )
        .build()
        .writeTo(outStream)
    } finally {
      outStream.close()
    }

    val zipFile   = Files.createTempFile("cpgloadertests", ".bin.zip")
    val zipStream = new ZipOutputStream(Files.newOutputStream(zipFile))
    try {
      zipStream.putNextEntry(new ZipEntry("1.proto"))
      Files.copy(protoFile, zipStream)
      zipStream.closeEntry()
    } finally {
      zipStream.close()
    }

    Files.delete(protoFile)
    Files.delete(outDir)
    zipFile
  }

}
