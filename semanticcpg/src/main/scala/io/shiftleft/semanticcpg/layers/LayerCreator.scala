package io.shiftleft.semanticcpg.layers

import better.files.File
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.CpgPassBase
import io.shiftleft.semanticcpg.Overlays
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class LayerCreator {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val overlayName: String
  val description: String
  val dependsOn: List[String] = List()

  def run(context: LayerCreatorContext, storeUndoInfo: Boolean = false): Unit = {
    val appliedOverlays = Overlays.appliedOverlays(context.cpg).toSet
    if (!dependsOn.toSet.subsetOf(appliedOverlays)) {
      logger.warn(
        s"${this.getClass.getName} depends on $dependsOn but CPG only has $appliedOverlays - skipping creation")
    } else if (appliedOverlays.contains(overlayName)) {
      logger.warn(s"The overlay $overlayName already exists - skipping creation")
    } else {
      create(context, storeUndoInfo)
      Overlays.appendOverlayName(context.cpg, overlayName)
    }
  }

  protected def initSerializedCpg(outputDir: Option[String], passName: String, index: Int = 0): SerializedCpg = {
    outputDir match {
      case Some(dir) => new SerializedCpg((File(dir) / s"${index}_$passName").path.toAbsolutePath.toString)
      case None      => new SerializedCpg()
    }
  }

  protected def runPass(pass: CpgPassBase,
                        context: LayerCreatorContext,
                        storeUndoInfo: Boolean,
                        index: Int = 0): Unit = {
    val serializedCpg = initSerializedCpg(context.outputDir, pass.name, index)
    pass.createApplySerializeAndStore(serializedCpg, inverse = storeUndoInfo)
    serializedCpg.close()
  }

  def create(context: LayerCreatorContext, storeUndoInfo: Boolean = false): Unit

  /**
    * Heuristically determine if overlay has been
    * applied to the CPG already. Used as a fallback
    * when `metaData.overlays` is not present due
    * to conversion from legacy CPG.
    * */
  def probe(cpg: Cpg): Boolean = false

}

class LayerCreatorContext(val cpg: Cpg, val outputDir: Option[String] = None) {}
class LayerCreatorOptions()
