package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.C2Cpg
import io.shiftleft.c2cpg.astcreation.{AstCreator, Defines}
import io.shiftleft.c2cpg.datastructures.Global
import io.shiftleft.c2cpg.parser.{CdtParser, HeaderFileFinder, ParserConfig}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{ConcurrentWriterCpgPass, DiffGraph, IntervalKeyPool}

import java.nio.file.Paths

class AstCreationPass(filenames: List[String],
                      cpg: Cpg,
                      keyPool: Option[IntervalKeyPool],
                      config: C2Cpg.Config,
                      parseConfig: ParserConfig = ParserConfig.empty,
                      headerFileFinder: HeaderFileFinder = null)
    extends ConcurrentWriterCpgPass[String](cpg, keyPool = keyPool) {

  private val global: Global = new Global()

  def usedTypes(): Seq[String] =
    global.usedTypes.keys().filterNot(_ == Defines.anyTypeName)

  def hasHeaderContentAndClear: Boolean = {
    val r = global.headerAsts.nonEmpty
    global.headerAsts.clear()
    r
  }

  override def generateParts(): Array[String] = filenames.toArray

  override def runOnPart(diffGraph: DiffGraph.Builder, filename: String): Unit =
    new CdtParser(parseConfig, headerFileFinder).parse(Paths.get(filename)).foreach { parserResult =>
      val localDiff = DiffGraph.newBuilder
      new AstCreator(filename, config, global, localDiff, parserResult).createAst()
      diffGraph.moveFrom(localDiff)
    }

}
