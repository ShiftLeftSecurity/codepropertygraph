package io.shiftleft.c2cpg.parser

import java.nio.file.Path

object ParseConfig {

  def empty: ParseConfig = ParseConfig(List.empty, Map.empty, logProblems = false, logPreprocessor = false)

}

case class ParseConfig(includePaths: List[Path],
                       definedSymbols: Map[String, String],
                       logProblems: Boolean,
                       logPreprocessor: Boolean)
