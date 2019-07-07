package io.shiftleft.codepropertygraph.cpgloading

import scala.compat.java8.OptionConverters._

/** configure graphdb to use ondisk overflow.
  * if the file specified by `graphLocation` already exists, we'll initialize the graph from there
  * if `graphLocation` is specified, graph will be saved there on close, and can be reloaded by just instantiating one with the same setting
  * otherwise, system tmp directory is used (e.g. `/tmp`) and graph won't be saved on close */
case class OnDiskOverflowConfig(graphLocation: Option[String] = None,
                                heapPercentageThreshold: Int = OnDiskOverflowConfig.defaultHeapPercentageThreshold) {
  lazy val graphLocationAsJava = graphLocation.asJava
}

object OnDiskOverflowConfig {
  val defaultForJava = OnDiskOverflowConfig()
  val defaultHeapPercentageThreshold: Int = 80
}
