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

  def find(path: String): String = {
    path
      .split(FileSystems.getDefault.getSeparator)
      .lastOption
      .flatMap { name =>
        nameToPathMap.get(name).flatMap(_.headOption).map(_.toString)
      }
      .orNull
  }

}
