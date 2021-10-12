package io.shiftleft.c2cpg.utils

import java.io

import org.slf4j.LoggerFactory

import scala.collection.mutable
import scala.sys.process.{Process, ProcessLogger}
import scala.util.{Failure, Success, Try}

object ExternalCommand {
  private val logger = LoggerFactory.getLogger(ExternalCommand.getClass)
  private val windowsSystemPrefix = "Windows"
  private val osNameProperty = "os.name"

  def run(command: String,
          inDir: String = ".",
          extraEnv: Map[String, String] = Map.empty,
          debugLogging: Boolean = false): Try[Seq[String]] = {
    val result = mutable.ArrayBuffer.empty[String]
    val lineHandler: String => Unit = line => {
      if (debugLogging) {
        logger.debug(s"\t\t$line")
      }
      result.addOne(line)
    }

    val systemString = System.getProperty(osNameProperty)
    val shellPrefix =
      if (systemString != null && systemString.startsWith(windowsSystemPrefix)) {
        "cmd" :: "/c" :: Nil
      } else {
        "sh" :: "-c" :: Nil
      }

    Process(shellPrefix :+ command, new io.File(inDir), extraEnv.toList: _*)
      .!(ProcessLogger(lineHandler, lineHandler)) match {
      case 0 =>
        Success(result.toSeq)
      case code if debugLogging =>
        logger.debug(s"\t- '$command' failed with exit code '$code': ${result.mkString(System.lineSeparator())}")
        Failure(new RuntimeException(result.mkString(System.lineSeparator())))
      case _ =>
        Failure(new RuntimeException(result.mkString(System.lineSeparator())))
    }
  }
}
