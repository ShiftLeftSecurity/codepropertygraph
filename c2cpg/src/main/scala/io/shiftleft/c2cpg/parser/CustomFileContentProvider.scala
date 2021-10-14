package io.shiftleft.c2cpg.parser

import org.eclipse.cdt.core.index.IIndexFileLocation
import org.eclipse.cdt.core.parser.FileContent
import org.eclipse.cdt.internal.core.parser.IMacroDictionary
import org.eclipse.cdt.internal.core.parser.scanner.{InternalFileContent, InternalFileContentProvider}
import org.slf4j.LoggerFactory

class CustomFileContentProvider(headerFileFinder: HeaderFileFinder) extends InternalFileContentProvider {

  private val logger = LoggerFactory.getLogger(classOf[CustomFileContentProvider])

  private def loadContent(path: String): InternalFileContent = {
    val headerFilePath = if (!getInclusionExists(path)) {
      Some(headerFileFinder).map(finder => finder.find(path)).orNull
    } else {
      path
    }

    if (headerFilePath != null) {
      logger.debug(s"Loading header file '$headerFilePath'")
      val content = FileContent.createForExternalFileLocation(headerFilePath)
      content.asInstanceOf[InternalFileContent]
    } else {
      logger.debug(s"Cannot find header file '$path'")
      null
    }

  }

  override def getContentForInclusion(
      path: String,
      macroDictionary: IMacroDictionary
  ): InternalFileContent = loadContent(path)

  override def getContentForInclusion(
      ifl: IIndexFileLocation,
      astPath: String
  ): InternalFileContent = loadContent(astPath)

}
