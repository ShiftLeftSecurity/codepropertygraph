package io.shiftleft.c2cpg.utils

import org.eclipse.cdt.core.parser.FileContent

import java.io.{BufferedReader, FileInputStream, InputStreamReader}
import java.nio.file.{Path, Paths}

import scala.jdk.CollectionConverters._

object IOUtils {

  def readFile(file: Path): FileContent = {
    val reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.toString), "utf-8"))
    val fileLines =
      reader
        .lines()
        .iterator()
        .asScala
        .flatMap(_.toCharArray.appendedAll(System.lineSeparator().toCharArray))
        .toArray

    FileContent.create(file.toString, true, fileLines)
  }

  def readFile(filePathAsString: String): FileContent = readFile(Paths.get(filePathAsString))

  def linesInFile(fileContent: FileContent): Seq[String] = {
    val content = fileContent.toString
    content.split(System.lineSeparator()).toSeq
  }

}
