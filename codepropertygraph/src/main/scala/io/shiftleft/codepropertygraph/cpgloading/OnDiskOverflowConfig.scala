package io.shiftleft.codepropertygraph.cpgloading

import scala.compat.java8.OptionConverters._

/** configure graphdb to use ondisk overflow. by default, system tmp directory is used (e.g. `/tmp`)  */
case class OnDiskOverflowConfig(alternativeParentDirectory: Option[String] = None,
                                heapPercentageThreshold: Int = OnDiskOverflowConfig.defaultHeapPercentageThreshold) {
  lazy val alternativeParentDirectoryAsJava = alternativeParentDirectory.asJava
}

object OnDiskOverflowConfig {
  val defaultForJava = OnDiskOverflowConfig()
  val defaultHeapPercentageThreshold: Int = 90
}
