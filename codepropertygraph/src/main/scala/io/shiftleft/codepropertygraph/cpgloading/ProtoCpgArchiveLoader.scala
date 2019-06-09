package io.shiftleft.codepropertygraph.cpgloading

import java.io.{File, IOException}
import java.nio.file.Files

import org.apache.commons.io.FileUtils
import org.apache.logging.log4j.LogManager

import scala.util.{Failure, Success, Try}

class ProtoCpgArchiveLoader() {

  private val logger = LogManager.getLogger(classOf[ProtoCpgLoader])

  var tempDir: Option[File] = None
  def tempDirPathName: Option[String] = tempDir.map(_.getAbsolutePath)

  /**
    * Extract archive into temporary directory and return directory name
    * */
  def extract(filename: String, prefix: String = "cpgarchive"): Option[String] = {
    tempDir = Try(Files.createTempDirectory(prefix).toFile) match {
      case Success(v) => Some(v)
      case Failure(e) => None
    }
    tempDir.foreach { t =>
      extractIntoDirectory(filename, t.toPath.toAbsolutePath.toString)
    }
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
    try {
      tempDir.foreach(FileUtils.deleteDirectory)
    } catch {
      case _: IOException =>
        logger.warn("Unable to remove temporary directory: " + tempDir)
    }
  }

}
