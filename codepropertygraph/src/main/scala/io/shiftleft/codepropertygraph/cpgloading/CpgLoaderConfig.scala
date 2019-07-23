package io.shiftleft.codepropertygraph.cpgloading

object CpgLoaderConfig {

  // To add a new option, please add a default value below and corresponding
  // methods in the case class.

  def default =
    CpgLoaderConfig(
      createIndexes = true,
      onDiskOverflowConfig = Some(OnDiskOverflowConfig()),
    )

  @deprecated("Use CpgLoaderConfig.default.withStorage instead", "")
  def withStorage(path: String) =
    CpgLoaderConfig(
      createIndexes = true,
      onDiskOverflowConfig = Some(OnDiskOverflowConfig(graphLocation = Some(path))),
    )

  @deprecated("Use CpgLoaderConfig.default.withoutStorage instead", "")
  def withoutOverflow =
    CpgLoaderConfig(
      createIndexes = true,
      onDiskOverflowConfig = None
    )

}

/**
  * Configuration for the CPG loader
  * @param createIndexes indicate whether to create indices or not
  * @param onDiskOverflowConfig configuration for the on-disk-overflow feature
  */
case class CpgLoaderConfig(createIndexes: Boolean, onDiskOverflowConfig: Option[OnDiskOverflowConfig]) {

  /**
    * Existing configuration with overflowdb path as specified
    * @param path the path of the on disk overflow database
    * */
  def withStorage(path: String): CpgLoaderConfig =
    this.copy(onDiskOverflowConfig = Some(OnDiskOverflowConfig(graphLocation = Some(path))))

  /**
    * Existing configuration with overflowdb turned off.
    * */
  def withoutOverflow: CpgLoaderConfig =
    this.copy(onDiskOverflowConfig = None)

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

}
