package io.shiftleft.c2cpg.parser

import org.eclipse.cdt.core.index.IIndexFileLocation
import org.eclipse.cdt.core.parser.FileContent
import org.eclipse.cdt.internal.core.parser.IMacroDictionary
import org.eclipse.cdt.internal.core.parser.scanner.{InternalFileContent, InternalFileContentProvider}
import org.slf4j.LoggerFactory

object CustomFileContentProvider {

  private val logger = LoggerFactory.getLogger(classOf[CustomFileContentProvider])

}

class CustomFileContentProvider extends InternalFileContentProvider {

  import CustomFileContentProvider.logger

  private def loadContent(path: String): InternalFileContent =
    if (!getInclusionExists(path)) {
      logger.warn(s"Include file not found: '$path'")
      null
    } else {
      // TODO: we might want to disable this at the end
      logger.info(s"Loading include file $path")
      val content = FileContent.createForExternalFileLocation(path)
      content.asInstanceOf[InternalFileContent]
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
