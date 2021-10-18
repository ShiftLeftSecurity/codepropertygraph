package io.shiftleft.c2cpg.parser

import java.nio.file.Path

object ParseConfig {

  def empty: ParseConfig = ParseConfig(Set.empty, Map.empty, logProblems = false, logPreprocessor = false)

}

case class ParseConfig(includePaths: Set[Path],
                       definedSymbols: Map[String, String],
                       logProblems: Boolean,
                       logPreprocessor: Boolean)
