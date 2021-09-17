package io.shiftleft.c2cpg.parser

import better.files._

import java.nio.file.{FileSystems, Path}

class HeaderFileFinder(roots: List[String]) {

  private val headerExtensions = List(".h", ".hpp", ".hh")
  private val nameToPathMap: Map[String, List[Path]] = {
    roots
      .flatMap { root =>
        File(root).listRecursively.filter(f => f.extension.isDefined && headerExtensions.contains(f.extension.get))
      }
      .map { file =>
        (file.name, file.path)
      }
      .groupBy(_._1)
      .map(x => (x._1, x._2.map(_._2)))
  }

  /**
    * Given an unresolved header file, given as a non-existing absolute path,
    * determine whether a header file with the same name can be found anywhere
    * in the code base.
    * */
  def find(path: String): String = {
    path
      .split(FileSystems.getDefault.getSeparator)
      .lastOption
      .flatMap { name =>
        val matches = nameToPathMap.getOrElse(name, List())
        matches.map(_.toString).sorted.headOption
      }
      .orNull
  }

}
