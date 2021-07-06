package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.parser.CdtParser.ParseResult
import io.shiftleft.c2cpg.parser.{CdtParser, ParseConfig}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{DiffGraph, IntervalKeyPool, ParallelCpgPass}

import java.nio.file.Paths

class AstCreationPass(filenames: List[String],
                      cpg: Cpg,
                      keyPool: IntervalKeyPool,
                      parseConfig: ParseConfig = ParseConfig.empty)
    extends ParallelCpgPass[String](cpg, keyPools = Some(keyPool.split(filenames.size))) {

  override def partIterator: Iterator[String] = filenames.iterator

  override def runOnPart(filename: String): Iterator[DiffGraph] = {
    val parser = new CdtParser(parseConfig)
    val parseResult = parser.parse(Paths.get(filename))

    parseResult match {
      case ParseResult(Some(ast), _, _) =>
        new AstCreator(filename).createAst(ast)
      case _ =>
        Iterator()
    }
  }

}
