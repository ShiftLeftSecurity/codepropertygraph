package io.shiftleft.c2cpg.parser

object FileDefaults {

  val C_EXT = ".c"
  val CC_EXT = ".cc"
  val CPP_EXT = ".cpp"
  val C_HEADER_EXT = ".h"
  val CPP_HEADER_EXT = ".hpp"

  val SOURCE_FILE_EXTENSIONS = Set(C_EXT, CC_EXT, CPP_EXT, C_HEADER_EXT, CPP_HEADER_EXT)

  private val HEADER_FILE_EXTENSIONS = Set(C_HEADER_EXT, CPP_HEADER_EXT)

  private val CPP_FILE_EXTENSIONS = Set(CPP_EXT, CPP_HEADER_EXT)

  def isHeaderFile(filePath: String): Boolean =
    HEADER_FILE_EXTENSIONS.exists(filePath.endsWith)

  def isCPPFile(filePath: String): Boolean =
    CPP_FILE_EXTENSIONS.exists(filePath.endsWith)
}
