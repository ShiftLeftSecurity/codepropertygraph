package io.shiftleft.codepropertygraph.cpgloading

object CpgLoaderConfig {

  /**
    * Get a default object
    * */
  def apply(): CpgLoaderConfig = new CpgLoaderConfig()

  val withDefaults = CpgLoaderConfig()

  @deprecated("Use CpgLoaderConfig.withDefaults.withStorage instead", "")
  def withStorage(path: String) =
    new CpgLoaderConfig(
      overflowDbConfig = OverflowDbConfig().withGraphLocation(path),
    )

  @deprecated("Use CpgLoaderConfig.withDefaults.withoutStorage instead", "")
  def withoutOverflow =
    new CpgLoaderConfig(
      overflowDbConfig = OverflowDbConfig().disabled
    )

}

/**
  * Configuration for the CPG loader
  * @param createIndexes indicate whether to create indices or not
  * @param overflowDbConfig configuration for the on-disk-overflow feature
  */
class CpgLoaderConfig(var createIndexes: Boolean = true, var overflowDbConfig: OverflowDbConfig = OverflowDbConfig()) {

  /**
    * Existing configuration without indexing on load.
    * */
  def doNotCreateIndexesOnLoad: CpgLoaderConfig = {
    this.createIndexes = false
    this
  }

  /**
    * Existing configuration but with indexing on load.
    * */
  def createIndexesOnLoad: CpgLoaderConfig = {
    this.createIndexes = true
    this
  }

  /**
    * Return existing configuration but with overflowdb config set to `overflowConfig`.
    * */
  def withOverflowConfig(overflowConfig: OverflowDbConfig): CpgLoaderConfig = {
    this.overflowDbConfig = overflowConfig
    this
  }

}
