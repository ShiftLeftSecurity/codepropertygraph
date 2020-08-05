package io.shiftleft.x2cpg

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import org.slf4j.LoggerFactory
import overflowdb.{OdbConfig, OdbGraph}

object X2Cpg {

  private val logger = LoggerFactory.getLogger(X2Cpg.getClass)

  /**
    * Create an empty CPG, backed by the file at `optionalOutputPath` or
    * in-memory if `optionalOutputPath` is empty.
    * */
  def initCpg(optionalOutputPath: Option[String]): Cpg = {
    val odbConfig = optionalOutputPath
      .map { outputPath =>
        val outFile = File(outputPath)
        if (outputPath != "" && outFile.exists) {
          logger.info("Output file exists, removing: " + outputPath)
          outFile.delete()
        }
        OdbConfig.withDefaults.withStorageLocation(outputPath)
      }
      .getOrElse {
        OdbConfig.withDefaults()
      }

    val graph = OdbGraph.open(odbConfig,
                              io.shiftleft.codepropertygraph.generated.nodes.Factories.allAsJava,
                              io.shiftleft.codepropertygraph.generated.edges.Factories.allAsJava)
    new Cpg(graph)
  }

}
