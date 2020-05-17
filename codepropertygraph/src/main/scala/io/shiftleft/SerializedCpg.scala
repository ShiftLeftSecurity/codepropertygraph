package io.shiftleft

import java.io.IOException
import com.google.protobuf.GeneratedMessageV3
import better.files._
import better.files.Dsl._

class SerializedCpg extends AutoCloseable {

  /**
    * We allow creating a dummy serialized CPG that does not do anything.
    */
  private[this] var counter = 0
  private[this] var fname: String = null

  /**
    * Create Serialized CPG from existing file. If the file does not exist,
    * an empty Serialized CPG is created.
    **/
  def this(filename: String) {
    this()
    fname = filename
    mkdirs(File(filename))
  }

  def isEmpty: Boolean = fname == null

  /**
    * Add overlay graph named `name` to the zip file
    **/
  @throws[IOException]
  def addOverlay(overlay: GeneratedMessageV3, name: String): Unit = {
    val outputStream = (File(fname) / s"${counter}_${name}").newOutputStream
    counter += 1
    overlay.writeTo(outputStream)
    outputStream.close()
  }

  @throws[IOException]
  def addOverlay(overlays: Iterator[GeneratedMessageV3], name: String): Unit = {
    overlays.zipWithIndex.foreach {
      case (overlay, i) => addOverlay(overlay, name + "_" + i)
    }
  }

  override def close(): Unit = {}
}
