package io.shiftleft.cpgsplitter

import io.shiftleft.codepropertygraph.cpgloading.{CpgLoaderConfig, ProtoCpgArchiveLoader}

class ProtoCpgStreamLoader {

  var archive: Option[ProtoCpgArchiveLoader] = None

  /**
    * Load code property graph stream. Code property graphs are bundled in archives.
    * This method consecutively loads the CPGs of the archive, returning them one by
    * one via an iterator.
    *
    * @param filename file of the CPG (archive)
    * @param config loader configuration
    *
    * */
  def loadStream(filename: String, config: CpgLoaderConfig = CpgLoaderConfig.default) = {
    archive = Some(new ProtoCpgArchiveLoader(filename, "stream"))
  }

  /**
    * Call to free resources
    * */
  def shutdown(): Unit = {
    archive.foreach(_.destroy)
  }

}
