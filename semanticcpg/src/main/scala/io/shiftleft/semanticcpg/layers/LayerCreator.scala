package io.shiftleft.semanticcpg.layers

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import org.apache.logging.log4j.LogManager
import io.shiftleft.semanticcpg.Overlays

abstract class LayerCreator {

  private val logger = LogManager.getLogger(this.getClass)

  val overlayName: String
  val description: String
  val dependsOn: List[String] = List()

  def run(context: LayerCreatorContext, serializeInverse: Boolean = false): Unit = {
    val appliedOverlays = Overlays.appliedOverlays(context.cpg).toSet
    if (!dependsOn.toSet.subsetOf(appliedOverlays)) {
      logger.warn(
        s"${this.getClass.getName} depends on $dependsOn but CPG only has $appliedOverlays - skipping creation")
    } else if (appliedOverlays.contains(overlayName)) {
      logger.warn(s"The overlay $overlayName already exists - skipping creation")
    } else {
      create(context, serializeInverse)
    }
  }

  def create(context: LayerCreatorContext, serializeInverse: Boolean = false): Unit

  /**
    * Heuristically determine if overlay has been
    * applied to the CPG already. Used as a fallback
    * when `metaData.overlays` is not present due
    * to conversion from legacy CPG.
    * */
  def probe(cpg: Cpg): Boolean

}

class LayerCreatorContext(val cpg: Cpg, val serializedCpg: SerializedCpg = new SerializedCpg()) {}
class LayerCreatorOptions()
