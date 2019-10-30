package io.shiftleft.utils

import better.files.File
import io.shiftleft.codepropertygraph.generated.Languages
import org.apache.logging.log4j.LogManager
import scala.sys.process.Process

/** language must be one of io.shiftleft.codepropertygraph.generated.Languages
  * TODO: generate enums instead of Strings for the languages */
case class Source(code: String, language: String)

case class HighlightedSource(value: String) {
  lazy val fixedForFansi = value
    .replaceAll("""\u001b\[m""", """\u001b[39m""") //encoding ends differently for fansi
    .replaceAll("""\u001b\[0(\d)m""", """\u001b[$1m""") // `[01m` is encoded as `[1m` in fansi for all single digit numbers
    .replaceAll("""\u001b\[[0?](\d);(\d+)m""", """\u001b[$1m\u001b[$2m""") // `[01;34m` is encoded as `[1m[34m` in fansi
}

object SourceHighlighter {
  private val logger = LogManager.getLogger(this)

  def highlight(source: Source): Option[String] = {
//  def highlight(source: Source): Option[HighlightedSource] = {
    val langFlag = source.language match {
      case Languages.C => "-sC"
      case other       => throw new RuntimeException(s"Attempting to call highlighter on unsupported language: $other")
    }

    val tmpSrcFile = File.newTemporaryFile("dump")
    tmpSrcFile.writeText(source.code)
    try {
      val highlightedCode = Process(Seq("source-highlight-esc.sh", tmpSrcFile.path.toString, langFlag)).!!
      // fixup for fansi.Str parsing
//      val fixedForFansi = highlightedCode.replaceAll("\\[m", "[39m")
//      Some(HighlightedSource(fixedForFansi))
//      Some(fixedForFansi)
      Some(highlightedCode)
    } catch {
      case exception: Exception =>
        logger.info("syntax highlighting now working. Is `source-highlight` installed?")
        logger.info(exception)
        None
    } finally {
      tmpSrcFile.delete()
    }
  }

}
