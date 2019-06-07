package io.shiftleft.codepropertygraph.cpgloading

object CpgLoaderConfig {

  def default: CpgLoaderConfig = CpgLoaderConfig(
    createIndices = true,
    onDiskOverflowConfig = None,
    ignoredProtoEntries = None,
    patterns = List()
  )

}

/**
  * Configuration for the CPG loader
  * @param createIndices indicate whether to create indices or not
  * @param onDiskOverflowConfig configuration for the on-disk-overflow feature
  * @param ignoredProtoEntries no longer used
  * @param patterns only load CPG if the proto name matches at least one pattern
  */
case class CpgLoaderConfig(var createIndices: Boolean,
                           var onDiskOverflowConfig: Option[OnDiskOverflowConfig],
                           @deprecated("no longer used", "June 19")
                           var ignoredProtoEntries: Option[IgnoredProtoEntries],
                           var patterns: List[String])
