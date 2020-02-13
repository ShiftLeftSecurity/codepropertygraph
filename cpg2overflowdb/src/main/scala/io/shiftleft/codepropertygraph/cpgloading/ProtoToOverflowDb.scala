 package io.shiftleft.codepropertygraph.cpgloading

 import java.io.{File}
 import io.shiftleft.overflowdb.{OdbConfig, OdbGraph}
 import org.apache.logging.log4j.LogManager

 /**
   * converts cpg.bin.zip proto to OverflowDb, so we don't need to first import into Tinkergraph and then serialize it out again
   * Useful especially for large cpgs. Uses scala parallel collections.
   *
   * Usage example: create the overflowdb and load it in ocular. This will load all references in memory, and accessed elements/properties will be lazily fetched from disk.
   * ./proto2overflowdb.sh --cpg cpg.bin.zip --out overflowdb.bin
   * ./ocular.sh
   * Cpg.withStorage("overflowdb.bin")
   */
 object ProtoToOverflowDb {
   type NodeId = java.lang.Long
   type EdgeLabel = String

   private lazy val logger = LogManager.getLogger(getClass)
 //  private lazy val edgeSerializer = new ProtoEdgeSerializer
   private lazy val nodeFilter = new NodeFilter

   def main(args: Array[String]): Unit = {
     parseConfig(args).map(run)
   }


   def run(config: Config): File = {
     val writeTo = config.writeTo.getOrElse(new File("overflowdb.bin"))
     logger.info(s"running ProtoToOverflowDb with cpg=${config.cpg}; writing results to $writeTo")
     if (writeTo.exists) writeTo.delete()
     val start = System.currentTimeMillis
     val odbConfig = OdbConfig.withDefaults()
       .withStorageLocation(writeTo.getAbsolutePath)
     if (!config.enableOverflow)
      odbConfig.disableOverflow()
     val cpg = CpgLoader.load(filename = config.cpg.getAbsolutePath, CpgLoaderConfig.withDefaults.withOverflowConfig(odbConfig).createIndexesOnLoad)
     cpg.graph.asInstanceOf[OdbGraph].close()
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
