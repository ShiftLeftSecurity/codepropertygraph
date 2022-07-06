package io.shiftleft.utils

import better.files.File

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

  private val SEARCH_DEPTH = 4
  object SearchDepthExceededError extends Error

  def relativise(path: String): String =
    s"$findRelativePath$path"

  def findRelativePath: String = {
    val fileThatOnlyExistsInRoot = ".git"

    @tailrec def loop(depth: Int): String = {
      val pathPrefix = "./" + "../" * depth
      if (File(s"$pathPrefix$fileThatOnlyExistsInRoot").exists) pathPrefix
      else if (depth < SEARCH_DEPTH) loop(depth + 1)
      else throw SearchDepthExceededError
    }

    loop(0)
  }

  def find: File =
    File(findRelativePath)

}
