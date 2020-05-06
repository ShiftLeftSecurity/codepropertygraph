package io.shiftleft.codepropertygraph.cpgloading

import java.io.File
import io.shiftleft.overflowdb.OdbConfig
import org.apache.logging.log4j.LogManager

/**
  * converts cpg.bin.zip proto to OverflowDb
  * ./cpg2overflowdb.sh --cpg cpg.bin.zip --out overflowdb.bin
  */
object ProtoToOverflowDb {
  private lazy val logger = LogManager.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    parseConfig(args).map(run)
  }

  def run(config: Config): File = {
    val writeTo = config.writeTo.getOrElse(new File("overflowdb.bin"))
    logger.info(s"running ProtoToOverflowDb with cpg=${config.cpg}; writing results to $writeTo")
    if (writeTo.exists) writeTo.delete()
    val start = System.currentTimeMillis
    val odbConfig = OdbConfig
      .withDefaults()
      .withStorageLocation(writeTo.getAbsolutePath)
    if (!config.enableOverflow)
      odbConfig.disableOverflow()
    val cpg = CpgLoader.load(filename = config.cpg.getAbsolutePath,
                             CpgLoaderConfig.withDefaults.withOverflowConfig(odbConfig).createIndexesOnLoad)
    cpg.graph.close()
    logger.info("OverflowDb construction finished in " + (System.currentTimeMillis - start) + "ms.")
    writeTo
  }

  private def parseConfig(args: Array[String]): Option[Config] = {
    new scopt.OptionParser[Config](getClass.getSimpleName) {
      opt[Boolean]("enable-overflow")
        .optional()
        .action((x, c) => c.copy(enableOverflow = x))
        .text("Enable disk overflow when there is not enough of memory")
      opt[File]("cpg").required
        .action((x, c) => c.copy(cpg = x))
        .text("path to cpg.bin.zip")
      opt[File]("out")
        .action((x, c) => c.copy(writeTo = Some(x)))
        .text("where to write overflowdb.bin output to (will delete if exists)")
      help("help").text("prints this usage text")
    }.parse(args, Config(cpg = null))
  }

}

case class Config(cpg: File, writeTo: Option[File] = None, enableOverflow: Boolean = false)
