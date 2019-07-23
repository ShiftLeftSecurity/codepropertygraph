package io.shiftleft.codepropertygraph.cpgloading

import scala.compat.java8.OptionConverters._

/** configure graphdb to use ondisk overflow.
  * if `graphLocation` is specified, graph will be saved there on close, and can be reloaded by just instantiating one with the same setting
  * otherwise, system tmp directory is used (e.g. `/tmp`) and graph won't be saved on close */
case class OnDiskOverflowConfig(enabled: Boolean = true,
                                graphLocation: Option[String] = None,
                                heapPercentageThreshold: Int = OnDiskOverflowConfig.defaultHeapPercentageThreshold) {
  lazy val graphLocationAsJava = graphLocation.asJava

  /**
    * Return this configuration but with disabled overflowdb
    * */
  def disabled: OnDiskOverflowConfig = this.copy(enabled = false)

  /**
    * Return this configuration but with overflowdb graph location set to `path`
    * */
  def withGraphLocation(path: String): OnDiskOverflowConfig = this.copy(graphLocation = Some(path))

  /**
    * Return this configuration with with heap percentage threshold set to `threshold`
    * */
  def withHeapPercentageThreshold(threshold: Int): OnDiskOverflowConfig = this.copy(heapPercentageThreshold = threshold)

}

object OnDiskOverflowConfig {

  def default: OnDiskOverflowConfig = OnDiskOverflowConfig()

  def disabled: OnDiskOverflowConfig = OnDiskOverflowConfig.default.disabled

  val defaultForJava = OnDiskOverflowConfig()
  val defaultHeapPercentageThreshold: Int = 80
}
