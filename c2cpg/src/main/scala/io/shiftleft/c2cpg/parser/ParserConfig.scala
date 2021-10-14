package io.shiftleft.c2cpg.parser

import java.nio.file.Path

object ParserConfig {

  def empty: ParserConfig = ParserConfig(Set.empty, Set.empty, Map.empty, logProblems = false, logPreprocessor = false)

}

case class ParserConfig(userIncludePaths: Set[Path],
                        systemIncludePaths: Set[Path],
                        definedSymbols: Map[String, String],
                        logProblems: Boolean,
                        logPreprocessor: Boolean) {

  def allIncludePaths: Set[Path] = userIncludePaths ++ systemIncludePaths

}
