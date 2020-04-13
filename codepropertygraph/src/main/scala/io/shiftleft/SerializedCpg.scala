package io.shiftleft

import java.io.{File, IOException}
import java.net.{URI, URISyntaxException}
import java.nio.file.{FileSystem, FileSystems, Files}
import java.util

import com.google.protobuf.GeneratedMessageV3
import io.shiftleft.proto.cpg.Cpg

class SerializedCpg extends AutoCloseable {

  /**
    * We allow creating a dummy serialized CPG that does not do anything.
    */
  private[this] var zipFileSystem: FileSystem = null
  private[this] var counter = 0

  /**
    * Create Serialized CPG from existing file. If the file does not exist,
    * an empty Serialized CPG is created.
    **/
  def this(filename: String) {
    this()
    initZipFilesystem(filename)
  }

  def isEmpty: Boolean = zipFileSystem == null

  @throws[URISyntaxException]
  @throws[IOException]
  private[this] def initZipFilesystem(filename: String): Unit = {
    val env = new util.HashMap[String, String]
    // This ensures that the file is created if it does not exist
    env.put("create", "true")
    val fileUri = new File(filename).toURI
    val outputUri = new URI("jar:" + fileUri.getScheme, null, fileUri.getPath, null)
    zipFileSystem = FileSystems.newFileSystem(outputUri, env)
  }

  /**
    * Add overlay graph named `name` to the zip file
    **/
  @throws[IOException]
  def addOverlay(overlay: GeneratedMessageV3, name: String): Unit = {
    if (!isEmpty) {
      val pathInZip = zipFileSystem.getPath(s"${counter}_${name}")
      counter += 1
      val outputStream = Files.newOutputStream(pathInZip)
      overlay.writeTo(outputStream)
      outputStream.close()
    }
  }

  @throws[IOException]
  def addOverlay(overlays: Iterator[GeneratedMessageV3], name: String): Unit = {
    if (!isEmpty) {
      overlays.zipWithIndex.foreach {
        case (overlay, i) => addOverlay(overlay, name + "_" + i)
      }
    }
  }

  @throws[IOException]
  override def close(): Unit = {
    if (!isEmpty) {
      zipFileSystem.close()
    }
  }
}
