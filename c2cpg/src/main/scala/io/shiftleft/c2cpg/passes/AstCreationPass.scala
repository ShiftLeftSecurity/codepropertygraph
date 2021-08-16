package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.C2Cpg
import io.shiftleft.c2cpg.astcreation.AstCreator
import io.shiftleft.c2cpg.datastructures.Global
import io.shiftleft.c2cpg.parser.{CdtParser, ParseConfig}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{ConcurrentWriterCpgPass, DiffGraph, IntervalKeyPool}

import java.nio.file.Paths
import scala.jdk.CollectionConverters._

class AstCreationPass(filenames: List[String],
                      cpg: Cpg,
                      keyPool: IntervalKeyPool,
                      config: C2Cpg.Config,
                      parseConfig: ParseConfig = ParseConfig.empty)
    extends ConcurrentWriterCpgPass[String](cpg, keyPool = Some(keyPool)) {

  private val global: Global = Global()

  def usedTypes(): List[String] = global.usedTypes.keys().asScala.toList

  override def generateParts(): Array[String] = filenames.toArray

  override def runOnPart(diffGraph: DiffGraph.Builder, filename: String): Unit =
    new CdtParser(parseConfig).parse(Paths.get(filename)).foreach { ast =>
      val localDiff = DiffGraph.newBuilder
      new AstCreator(filename, global, config, localDiff).createAst(ast)
      diffGraph.moveFrom(localDiff)
    }

}
