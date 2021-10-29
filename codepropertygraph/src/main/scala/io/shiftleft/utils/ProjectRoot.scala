package io.shiftleft.utils

import better.files.File

/**
  * Finds the relative location of the project root.
  *
  * Used in tests which rely on the working directory - unfortunately Intellij and sbt have different default working
  * directories for executing tests from subprojects: while sbt defaults to the project root, intellij defaults to
  * the subproject.
  *
  * Previously a consistent behaviour was achieved by setting
  * `Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value`,
  * however that broke the bsp build within Intellij - it simply wouldn't recognise subprojects with this setting
  * any more.
  */
object ProjectRoot {

  private val SEARCH_DEPTH = 4
  object SearchDepthExceededError extends Error

  def relativise(path: String): String =
    s"$findRelativePath$path"

  def findRelativePath: String = {
    val fileThatOnlyExistsInRoot = ".git"

    for (depth <- 0 to SEARCH_DEPTH) {
      val pathPrefix = "./" + "../" * depth
      if (File(s"$pathPrefix$fileThatOnlyExistsInRoot").exists) {
        return pathPrefix
      }
    }

    throw SearchDepthExceededError
  }

  def find: File =
    File(findRelativePath)

}
