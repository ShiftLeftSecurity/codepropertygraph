package io.shiftleft.console

import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.overflowdb.OdbConfig

object CpgConverter {

  def convertProtoCpgToOverflowDb(srcFilename: String, dstFilename: String): Unit = {
    val odbConfig = OdbConfig.withDefaults.withStorageLocation(dstFilename)
    val config    = CpgLoaderConfig.withDefaults.doNotCreateIndexesOnLoad.withOverflowConfig(odbConfig)
    CpgLoader.load(srcFilename, config).close
  }

}
