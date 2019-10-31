package io.shiftleft.utils

import better.files.File
import io.shiftleft.codepropertygraph.generated.Languages
import org.apache.logging.log4j.LogManager
import scala.sys.process.Process

/** language must be one of io.shiftleft.codepropertygraph.generated.Languages
  * TODO: generate enums instead of Strings for the languages */
case class Source(code: String, language: String)

object SourceHighlighter {
  private val logger = LogManager.getLogger(this)

  def highlight(source: Source): Option[String] = {
    val langFlag = source.language match {
      case Languages.C => "-sC"
      case other       => throw new RuntimeException(s"Attempting to call highlighter on unsupported language: $other")
    }

    val tmpSrcFile = File.newTemporaryFile("dump")
    tmpSrcFile.writeText(source.code)
    try {
      val highlightedCode = Process(Seq("source-highlight-esc.sh", tmpSrcFile.path.toString, langFlag)).!!
      Some(highlightedCode)
    } catch {
      case exception: Exception =>
        logger.info("syntax highlighting not working. Is `source-highlight` installed?")
        logger.info(exception)
        None
    } finally {
      tmpSrcFile.delete()
    }
  }

}
