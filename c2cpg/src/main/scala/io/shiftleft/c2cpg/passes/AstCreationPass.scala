package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.C2Cpg
import io.shiftleft.c2cpg.astcreation.{AstCreator, Defines}
import io.shiftleft.c2cpg.datastructures.Global
import io.shiftleft.c2cpg.parser.{CdtParser, HeaderFileFinder, ParserConfig}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{DiffGraph, ForkJoinParallelCpgPass, IntervalKeyPool}

import java.nio.file.Paths
import scala.jdk.CollectionConverters._

class AstCreationPass(filenames: List[String],
                      cpg: Cpg,
                      keyPool: IntervalKeyPool,
                      config: C2Cpg.Config,
                      parseConfig: ParserConfig = ParserConfig.empty,
                      headerFileFinder: HeaderFileFinder = null)
    extends ForkJoinParallelCpgPass[String](cpg, keyPool = Some(keyPool)) {
  // We use the ForkJoinParallelCpgPass as this is the only parallel CPG pass at the moment
  // that gets node de-duplication right.

  private val global: Global = Global()

  def usedTypes(): List[String] =
    global.usedTypes.keys().asScala.filterNot(_ == Defines.anyTypeName).toList

  override def generateParts(): Array[String] = filenames.toArray

  override def runOnPart(diffGraph: DiffGraph.Builder, filename: String): Unit =
    new CdtParser(parseConfig, headerFileFinder).parse(Paths.get(filename)).foreach { parserResult =>
      new AstCreator(filename, global, config, diffGraph, parserResult).createAst()
    }

}
