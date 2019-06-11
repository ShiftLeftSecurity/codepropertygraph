package io.shiftleft.codepropertygraph.cpgloading

/** configure graphdb to use ondisk overflow. by default, system tmp directory is used (e.g. `/tmp`)  */
case class OnDiskOverflowConfig(
  alternativeParentDirectory: Option[String] = None,
  heapPercentageThreshold: Int = 90)
