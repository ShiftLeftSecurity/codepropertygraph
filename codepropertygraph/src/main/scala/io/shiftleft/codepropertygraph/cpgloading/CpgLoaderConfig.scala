package io.shiftleft.codepropertygraph.cpgloading

object CpgLoaderConfig {

  // To add a new option, please add a default value below and corresponding
  // methods in the case class.

  def default =
    CpgLoaderConfig(
      createIndices = true,
      onDiskOverflowConfig = Some(OnDiskOverflowConfig()),
    )

  @deprecated("Use CpgLoaderConfig.default.withStorage instead", "Jul 19")
  def withStorage(path: String) =
    CpgLoaderConfig(
      createIndices = true,
      onDiskOverflowConfig = Some(OnDiskOverflowConfig(graphLocation = Some(path))),
    )

  @deprecated("Use CpgLoaderConfig.default.withoutStorage instead", "Jul 19")
  def withoutOverflow =
    CpgLoaderConfig(
      createIndices = true,
      onDiskOverflowConfig = None
    )

}

/**
  * Configuration for the CPG loader
  * @param createIndices indicate whether to create indices or not
  * @param onDiskOverflowConfig configuration for the on-disk-overflow feature
  */
case class CpgLoaderConfig(createIndices: Boolean, onDiskOverflowConfig: Option[OnDiskOverflowConfig]) {

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
  def doNotCreateIndicesOnLoad: CpgLoaderConfig =
    this.copy(createIndices = false)

  /**
    * Existing configuration but with indexing on load.
    * */
  def createIndicesOnLoad: CpgLoaderConfig =
    this.copy(createIndices = true)

}
