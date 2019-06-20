package io.shiftleft.codepropertygraph.cpgloading

object CpgLoaderConfig {

  def default =
    CpgLoaderConfig(
      createIndices = true,
      onDiskOverflowConfig = Some(OnDiskOverflowConfig()),
      ignoredProtoEntries = None
    )

  def withStorage(path: String) =
    CpgLoaderConfig(
      createIndices = true,
      onDiskOverflowConfig = Some(OnDiskOverflowConfig(graphLocation = Some(path))),
      ignoredProtoEntries = None
    )

  def withoutOverflow =
    CpgLoaderConfig(
      createIndices = true,
      onDiskOverflowConfig = None,
      ignoredProtoEntries = None
    )

}

/**
  * Configuration for the CPG loader
  * @param createIndices indicate whether to create indices or not
  * @param onDiskOverflowConfig configuration for the on-disk-overflow feature
  */
case class CpgLoaderConfig(createIndices: Boolean,
                           onDiskOverflowConfig: Option[OnDiskOverflowConfig],
                           ignoredProtoEntries: Option[IgnoredProtoEntries])
