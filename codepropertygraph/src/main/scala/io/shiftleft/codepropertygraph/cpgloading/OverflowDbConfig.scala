package io.shiftleft.codepropertygraph.cpgloading

import scala.compat.java8.OptionConverters._

/** configure graphdb to use ondisk overflow.
  * if `graphLocation` is specified, graph will be saved there on close, and can be reloaded by just instantiating one with the same setting
  * otherwise, system tmp directory is used (e.g. `/tmp`) and graph won't be saved on close */
case class OverflowDbConfig(enabled: Boolean = true,
                            graphLocation: Option[String] = None,
                            heapPercentageThreshold: Int = OverflowDbConfig.defaultHeapPercentageThreshold) {
  lazy val graphLocationAsJava = graphLocation.asJava

  /**
    * Return this configuration but with disabled overflowdb
    * */
  def disabled: OverflowDbConfig = this.copy(enabled = false)

  /**
    * Return this configuration but with overflowdb graph location set to `path`
    * */
  def withGraphLocation(path: String): OverflowDbConfig = this.copy(graphLocation = Some(path))

  /**
    * Return this configuration with with heap percentage threshold set to `threshold`
    * */
  def withHeapPercentageThreshold(threshold: Int): OverflowDbConfig = this.copy(heapPercentageThreshold = threshold)

}

object OverflowDbConfig {

  def default: OverflowDbConfig = OverflowDbConfig()

  def disabled: OverflowDbConfig = OverflowDbConfig.default.disabled

  val withDefaults = OverflowDbConfig()
  val defaultHeapPercentageThreshold: Int = 80
}
