package io.shiftleft.c2cpg.parser

import io.shiftleft.c2cpg.utils.{IOUtils, TimeUtils}
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage
import org.eclipse.cdt.core.model.ILanguage
import org.eclipse.cdt.core.parser.{DefaultLogService, ScannerInfo}
import org.eclipse.cdt.internal.core.dom.parser.cpp.semantics.CPPVisitor
import org.slf4j.LoggerFactory

import java.nio.file.Path
import java.util
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

object CdtParser {

  private val logger = LoggerFactory.getLogger(classOf[CdtParser])

  case class ParseResult(translationUnit: Option[IASTTranslationUnit],
                         preprocessorErrorCount: Int,
                         problems: Int,
                         failure: Option[Throwable] = None,
                         duration: Long = 0)

}

class CdtParser(private val parseConfig: ParseConfig) extends ParseProblemsLogger with PreprocessorStatementsLogger {

  import CdtParser._

  private val definedSymbols: util.Map[String, String] = parseConfig.definedSymbols.asJava

  private val includePaths: Seq[String] = parseConfig.includePaths.map(_.toAbsolutePath.toString)
  private val info: ScannerInfo = new ScannerInfo(definedSymbols, includePaths.toArray)
  private val log: DefaultLogService = new DefaultLogService

  // enables parsing of code behind disabled preprocessor defines:
  private val opts: Int = ILanguage.OPTION_PARSE_INACTIVE_CODE

  private def createParseLanguage(file: Path): ILanguage = {
    val fileAsString = file.toString
    if (fileAsString.endsWith(FileDefaults.CPP_EXT) || fileAsString.endsWith(FileDefaults.CPP_HEADER_EXT)) {
      new GPPLanguage()
    } else {
      new GCCLanguage()
    }
  }

  private def parseInternal(file: Path): ParseResult = {
    val (result, duration) = TimeUtils.time {
      val fileContent = IOUtils.readFile(file)
      val fileContentProvider = new CustomFileContentProvider()
      val lang = createParseLanguage(file)
      Try(
        lang.getASTTranslationUnit(fileContent, info, fileContentProvider, null, opts, log)
      ) match {
        case Failure(e) =>
          ParseResult(None, -1, -1, Some(e))
        case Success(translationUnit) =>
          val problems = CPPVisitor.getProblems(translationUnit)
          if (parseConfig.logProblems) logProblems(problems.toList)
          if (parseConfig.logPreprocessor) logPreprocessorStatements(translationUnit)
          ParseResult(
            Some(translationUnit),
            translationUnit.getPreprocessorProblemsCount,
            problems.length
          )
      }
    }
    result.copy(duration = duration)
  }

  def parse(file: Path): Option[IASTTranslationUnit] = {
    val parseResult = parseInternal(file)
    val duration = TimeUtils.pretty(parseResult.duration)
    parseResult match {
      case ParseResult(Some(t), c, p, _, _) =>
        logger.info(s"Parsed '${t.getFilePath}' in $duration ($c preprocessor error(s), $p problems)")
        Some(t)
      case ParseResult(_, _, _, maybeThrowable, _) =>
        logger.warn(s"Failed to parse '$file' (took: $duration) ${maybeThrowable.map(_.getMessage)}")
        None
    }
  }

}
