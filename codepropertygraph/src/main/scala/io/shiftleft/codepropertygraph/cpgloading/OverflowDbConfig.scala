package io.shiftleft.codepropertygraph.cpgloading

import scala.compat.java8.OptionConverters._

object OverflowDbConfig {

  def apply(): OverflowDbConfig = withDefaults

  def disabled: OverflowDbConfig = OverflowDbConfig.withDefaults.disabled

  val withDefaults = new OverflowDbConfig()
  val defaultHeapPercentageThreshold: Int = 80
}

/** configure graphdb to use ondisk overflow.
  * if `graphLocation` is specified, graph will be saved there on close, and can be reloaded by just instantiating one with the same setting
  * otherwise, system tmp directory is used (e.g. `/tmp`) and graph won't be saved on close */
class OverflowDbConfig(var enabled: Boolean = true,
                       var graphLocation: Option[String] = None,
                       var heapPercentageThreshold: Int = OverflowDbConfig.defaultHeapPercentageThreshold) {
  lazy val graphLocationAsJava = graphLocation.asJava

  /**
    * Return this configuration but with disabled overflowdb
    * */
  def disabled: OverflowDbConfig = { this.enabled = false; this }

  /**
    * Return this configuration but with overflowdb graph location set to `path`
    * */
  def withGraphLocation(path: String): OverflowDbConfig = { this.graphLocation = Some(path); this }

  /**
    * Return this configuration with with heap percentage threshold set to `threshold`
    * */
  def withHeapPercentageThreshold(threshold: Int): OverflowDbConfig = { this.heapPercentageThreshold = threshold; this }

}
