package io.shiftleft.cpgsplitter

import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}

class CpgSplitter {

  /**
    * Analyze the CPG at `cpgFilename` to determine its independent
    * sub graphs.
    * */
  def split(cpgFilename : String, outDirname : String) : Unit = {
    val config = CpgLoaderConfig.default
    config.patterns = List(".*filesandnamespaceblocks.*")
    val cpg = CpgLoader.load(cpgFilename, config)
    cpg.namespaceBlock.name.l.foreach(println(_))
  }

}
