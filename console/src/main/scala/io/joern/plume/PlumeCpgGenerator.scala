package io.joern.plume

import io.github.plume.oss.Extractor
import io.github.plume.oss.drivers.{DriverFactory, GraphDatabase, OverflowDbDriver}
import better.files._

import scala.util.{Failure, Success, Try, Using}

object PlumeCpgGenerator {

  def createCpgForJava(inputPaths: List[String], outputPath: String): Unit = {
    val (existing, nonExisting) = inputPaths.partition(inputPath => File(inputPath).exists)
    nonExisting.foreach(inputPath => println(s"Error: $inputPath does not exist"))
    if (existing.isEmpty) { Try { throw new RuntimeException("Not valid input paths for CPG generation") } }

    try {
      existing.foreach { inputPath =>
        val inFile = File(inputPath)
        if (inFile.isDirectory || inFile.isRegularFile) {
          createCpgForInputPath(inputPath, outputPath)
        } else {
          Try { throw new RuntimeException(s"$inputPath is neither a file nor a directory") }
        }
      }
    } catch {
      case exc: Exception =>
        exc.printStackTrace()
    }
  }

  private def createCpgForInputPath(inputPath: String, outputCpgFile: String): Unit = {
    println(s"Creating CPG for: $inputPath")
    Using(DriverFactory.invoke(GraphDatabase.OVERFLOWDB).asInstanceOf[OverflowDbDriver]) { driver =>
      deleteIfExists(outputCpgFile)
      driver.setStorageLocation(outputCpgFile)
      val extractor = new Extractor(driver)
      extractor.load(new java.io.File(inputPath))
      extractor.project()
    } match {
      case Success(_)   =>
      case Failure(exc) => throw exc
    }
  }

  private def deleteIfExists(fileName: String) = {
    val outFile = File(fileName)
    if (outFile.exists) {
      println(s"Output file ${fileName} exists. Removing first.")
      outFile.delete()
    }
  }

}
