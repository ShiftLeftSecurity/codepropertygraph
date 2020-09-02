package io.shiftleft.x2cpg

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import org.slf4j.LoggerFactory
import overflowdb.{Config, Graph}

object X2Cpg {

  private val logger = LoggerFactory.getLogger(X2Cpg.getClass)

  /**
    * Create an empty CPG, backed by the file at `optionalOutputPath` or
    * in-memory if `optionalOutputPath` is empty.
    * */
  def newEmptyCpg(optionalOutputPath: Option[String] = None): Cpg = {
    val odbConfig = optionalOutputPath
      .map { outputPath =>
        val outFile = File(outputPath)
        if (outputPath != "" && outFile.exists) {
          logger.info("Output file exists, removing: " + outputPath)
          outFile.delete()
        }
        Config.withDefaults.withStorageLocation(outputPath)
      }
      .getOrElse {
        Config.withDefaults()
      }

    val graph = Graph.open(odbConfig,
                           io.shiftleft.codepropertygraph.generated.nodes.Factories.allAsJava,
                           io.shiftleft.codepropertygraph.generated.edges.Factories.allAsJava)
    new Cpg(graph)
  }

}
