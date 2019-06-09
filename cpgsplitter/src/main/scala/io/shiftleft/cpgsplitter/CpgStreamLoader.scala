package io.shiftleft.cpgsplitter

import java.nio.file.Files

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.CpgLoaderConfig

import scala.util.{Failure, Success, Try}

class CpgStreamLoader {

  private val tempDir = Try(Files.createTempDirectory("streamloader").toFile) match {
    case Success(v) => v
    case Failure(e) => throw new RuntimeException(e)
  }

  /**
    * Load code property graph stream. Code property graphs are bundled in archives.
    * This method consecutively loads the CPGs of the archive, returning them one by
    * one via an iterator.
    *
    * @param filename file of the CPG (archive)
    * @param config loader configuration
    *
    * */
  def loadStream(filename: String, config: CpgLoaderConfig = CpgLoaderConfig.default): Iterator[Cpg] = {
    ???
  }

  /**
    * Call to free resources
    * */
  def shutdown(): Unit = {}

}
