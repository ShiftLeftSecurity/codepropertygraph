package io.shiftleft.c2cpg.parser

import io.shiftleft.c2cpg.utils.TimeUtils
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.eclipse.cdt.core.model.ILanguage
import org.eclipse.cdt.core.parser.DefaultLogService
import org.eclipse.cdt.core.parser.ScannerInfo
import org.eclipse.cdt.internal.core.dom.parser.cpp.semantics.CPPVisitor
import org.eclipse.cdt.core.parser.FileContent
import org.slf4j.LoggerFactory

import java.nio.file.Path
import java.util
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import scala.jdk.CollectionConverters._

object CdtParser {

  private val logger = LoggerFactory.getLogger(classOf[CdtParser])

  case class ParseResult(translationUnit: Option[IASTTranslationUnit], preprocessorErrorCount: Int, problems: Int)

}

class CdtParser(private val parseConfig: ParseConfig) {

  import CdtParser._

  private val definedSymbols: util.Map[String, String] = parseConfig.definedSymbols.asJava

  private val includePaths: Seq[String] = parseConfig.includePaths.map(_.toAbsolutePath.toString)
  private val info: ScannerInfo = new ScannerInfo(definedSymbols, includePaths.toArray)
  private val log: DefaultLogService = new DefaultLogService

  // enables parsing of code behind disabled preprocessor defines:
  private val opts: Int = ILanguage.OPTION_PARSE_INACTIVE_CODE

  private def parseInternal(file: Path): (ParseResult, String) = {
    val (results, duration) = TimeUtils.time {
      val fileContent = FileContent.createForExternalFileLocation(file.toString)
      val fileContentProvider = new CustomFileContentProvider()
      val lang = new GPPLanguage()
      Try(
        lang.getASTTranslationUnit(fileContent, info, fileContentProvider, null, opts, log)
      ) match {
        case Failure(e) =>
          e.printStackTrace()
          ParseResult(None, -1, -1)
        case Success(translationUnit) =>
          val problems = CPPVisitor.getProblems(translationUnit)
          ParseResult(
            Some(translationUnit),
            translationUnit.getPreprocessorProblemsCount,
            problems.length
          )
      }
    }
    (results, TimeUtils.pretty(duration))
  }

  def parse(file: Path): ParseResult = parseInternal(file) match {
    case (r @ ParseResult(None, _, _), duration) =>
      logger.warn(s"Failed to parsed '$file' (took: $duration)")
      r
    case (r @ ParseResult(Some(t), c, p), duration) =>
      logger.info(s"Parsed '${t.getFilePath}' in $duration ($c preprocessor error(s), $p problems)")
      r
  }

}
