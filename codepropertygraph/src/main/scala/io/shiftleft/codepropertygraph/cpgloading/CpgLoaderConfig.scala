package io.shiftleft.codepropertygraph.cpgloading
//import overflowdb.Config

object CpgLoaderConfig {

  /** Get a default object
    */
  def apply(): CpgLoaderConfig = new CpgLoaderConfig()

  val withDefaults = CpgLoaderConfig()

}

/** Configuration for the CPG loader
  * @param createIndexes
  *   indicate whether to create indices or not
  * @param overflowDbConfig
  *   configuration for the on-disk-overflow feature
  */
class CpgLoaderConfig(var createIndexes: Boolean = true/*, var overflowDbConfig: Config = Config.withoutOverflow*/) {

  /** Existing configuration without indexing on load.
    */
  def doNotCreateIndexesOnLoad: CpgLoaderConfig = {
    this.createIndexes = false
    this
  }

  /** Existing configuration but with indexing on load.
    */
  def createIndexesOnLoad: CpgLoaderConfig = {
    this.createIndexes = true
    this
  }

  /** Return existing configuration but with overflowdb config set to `overflowConfig`.
    */
//  def withOverflowConfig(overflowConfig: Config): CpgLoaderConfig = {
//    this.overflowDbConfig = overflowConfig
//    this
//  }

}
