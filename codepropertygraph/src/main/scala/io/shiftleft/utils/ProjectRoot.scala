package io.shiftleft.utils

import java.nio.file.{Files, Path, Paths}

import scala.annotation.tailrec

/** Finds the relative location of the project root.
  *
  * Used in tests which rely on the working directory - unfortunately Intellij and sbt have different default working
  * directories for executing tests from subprojects: while sbt defaults to the project root, intellij defaults to the
  * subproject.
  *
  * Previously a consistent behaviour was achieved by setting `Test / baseDirectory := (ThisBuild / Test / run /
  * baseDirectory).value`, however that broke the bsp build within Intellij - it simply wouldn't recognise subprojects
  * with this setting any more.
  */
object ProjectRoot {
  val FileNameToSearchForEnvVar = "FILE_IN_PROJECT_ROOT"

  object SearchDepthExceededError extends Error
  
  private val SEARCH_DEPTH = 4
  private lazy val filenameToSearchFor = 
    sys.env
      .get(FileNameToSearchForEnvVar)
      .getOrElse(".git")

  def relativise(path: String): String =
    s"$findRelativePath$path"

  def findRelativePath: String = {
    @tailrec def loop(depth: Int): String = {
      val pathPrefix = "./" + "../" * depth
      if (Files.exists(Paths.get(s"$pathPrefix$filenameToSearchFor"))) pathPrefix
      else if (depth < SEARCH_DEPTH) loop(depth + 1)
      else throw SearchDepthExceededError
    }

    loop(0)
  }

  def find: Path =
    Paths.get(findRelativePath)

}
