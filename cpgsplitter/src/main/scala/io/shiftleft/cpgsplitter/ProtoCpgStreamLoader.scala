package io.shiftleft.cpgsplitter

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoaderConfig, ProtoCpgArchiveLoader, ProtoCpgLoader}

class ProtoCpgStreamLoader {

  var archiveLoader: ProtoCpgArchiveLoader = new ProtoCpgArchiveLoader()

  /**
    * Iterate over all proto files in the CPG archive at `filename` and
    * return node-only CPGs.
    * @param filename file of the CPG (archive)
    * @param config loader configuration
    *
    * */
  def loadStreamOfNodeCpgs(filename: String, config: CpgLoaderConfig = CpgLoaderConfig.default) : Iterator[Cpg]  = {
    val tmpDirName = archiveLoader.extract(filename, "stream")
    if(tmpDirName.isEmpty) {
      Iterator()
    } else {
      val filenames = ProtoCpgLoader.filenamesForConfig(tmpDirName.get, config)
      filenames.iterator.map{ filename =>
        val builder = ProtoCpgLoader.builderForConfig(config)
        ProtoCpgLoader.addNodes(filename, builder)
        builder.build()
      }
    }
  }

  /**
    * Call to free resources
    * */
  def shutdown(): Unit = {
    archiveLoader.destroy
  }

}
