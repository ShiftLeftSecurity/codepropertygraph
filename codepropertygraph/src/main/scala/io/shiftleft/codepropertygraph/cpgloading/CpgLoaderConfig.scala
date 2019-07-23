package io.shiftleft.codepropertygraph.cpgloading

object CpgLoaderConfig {

  // To add a new option, please add a default value below and corresponding
  // methods in the case class.

  def default =
    CpgLoaderConfig(
      createIndexes = true,
      onDiskOverflowConfig = OnDiskOverflowConfig.default,
    )

  // This is required because `default` is a keyword in Java
  def defaultForJava: CpgLoaderConfig = default

  @deprecated("Use CpgLoaderConfig.default.withStorage instead", "")
  def withStorage(path: String) =
    CpgLoaderConfig(
      createIndexes = true,
      onDiskOverflowConfig = OnDiskOverflowConfig.default.withGraphLocation(path),
    )

  @deprecated("Use CpgLoaderConfig.default.withoutStorage instead", "")
  def withoutOverflow =
    CpgLoaderConfig(
      createIndexes = true,
      onDiskOverflowConfig = OnDiskOverflowConfig.default.disabled
    )

}

/**
  * Configuration for the CPG loader
  * @param createIndexes indicate whether to create indices or not
  * @param onDiskOverflowConfig configuration for the on-disk-overflow feature
  */
case class CpgLoaderConfig(createIndexes: Boolean, onDiskOverflowConfig: OnDiskOverflowConfig) {

  /**
    * Existing configuration without indexing on load.
    * */
  def doNotCreateIndexesOnLoad: CpgLoaderConfig =
    this.copy(createIndexes = false)

  /**
    * Existing configuration but with indexing on load.
    * */
  def createIndexesOnLoad: CpgLoaderConfig =
    this.copy(createIndexes = true)

  /**
    * Return existing configuration but with overflowdb config set to `overflowConfig`.
    * */
  def withOverflowConfig(overflowConfig: OnDiskOverflowConfig): CpgLoaderConfig =
    this.copy(onDiskOverflowConfig = overflowConfig)

}
