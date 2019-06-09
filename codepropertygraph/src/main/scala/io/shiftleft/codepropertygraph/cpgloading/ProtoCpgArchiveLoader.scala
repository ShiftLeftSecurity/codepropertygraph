package io.shiftleft.codepropertygraph.cpgloading

import java.io.IOException
import java.nio.file.Files

import org.apache.commons.io.FileUtils
import org.apache.logging.log4j.LogManager

import scala.util.{Failure, Success, Try}

/**
  * Utility class to handle CPG archives, that is, archives
  * containing protobuf files, each of which represent a part of a code property graph.
  *
  * @param filename the filename of the archive
  * */
class ProtoCpgArchiveLoader(filename: String, prefix: String = "cpgarchive") {

  private val logger = LogManager.getLogger(classOf[ProtoCpgLoader])

  val tempDir = Try(Files.createTempDirectory(prefix).toFile) match {
    case Success(v) => v
    case Failure(e) => null
  }

  val tempDirPathName = tempDir.getAbsolutePath

  /**
    * Extract archive into temporary directory and return directory name
    * */
  def extract(): String = {
    extractIntoDirectory(filename, tempDir.toPath.toAbsolutePath.toString)
    tempDirPathName
  }

  /**
    * Extract the cpg archive at `filename` to the directory `dirName`
    * */
  private def extractIntoDirectory(filename: String, dirName: String): Unit = {
    val start = System.currentTimeMillis
    new ZipArchive(filename).unzip(dirName)
    logger.info("Unzipping completed in " + (System.currentTimeMillis - start) + "ms.")
  }

  /**
    * Call to free resources
    * */
  def destroy: Unit = removeTemporaryDirectory

  private def removeTemporaryDirectory: Unit = {
    try if (tempDir != null) FileUtils.deleteDirectory(tempDir)
    catch {
      case _: IOException =>
        logger.warn("Unable to remove temporary directory: " + tempDir)
    }
  }

}
