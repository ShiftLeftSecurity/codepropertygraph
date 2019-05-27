package io.shiftleft.codepropertygraph.cpgloading

object CpgLoaderConfig {

  def default: CpgLoaderConfig = CpgLoaderConfig(
    createIndices = true,
    onDiskOverflowConfig = None,
    ignoredProtoEntries = None
  )
}

/**
  * Configuration for the CPG loader
  * @param createIndices indicate whether to create indices or not
  * @param onDiskOverflowConfig configuration for the on-disk-overflow feature
  *  */
case class CpgLoaderConfig(var createIndices: Boolean,
                           var onDiskOverflowConfig: Option[OnDiskOverflowConfig],
                           var ignoredProtoEntries: Option[IgnoredProtoEntries])
