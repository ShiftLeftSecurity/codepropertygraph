package io.shiftleft.semanticcpg.layers

import better.files.File
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.CpgPassBase
import io.shiftleft.semanticcpg.Overlays
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext

abstract class LayerCreator {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val overlayName: String
  val description: String
  val dependsOn: List[String] = List()

  /**
    * If the LayerCreator modifies the CPG, then we store its name
    * in the CPGs metadata and disallow rerunning the creator,
    * that is, applying the layer twice.
    * */
  protected val modifiesCpg: Boolean = true

  def run(context: LayerCreatorContext, storeUndoInfo: Boolean = false)(implicit ec: ExecutionContext): Unit = {
    val appliedOverlays = Overlays.appliedOverlays(context.cpg).toSet
    if (!dependsOn.toSet.subsetOf(appliedOverlays)) {
      logger.warn(
        "{} depends on {} but CPG only has $appliedOverlays - skipping creation", this.getClass.getName, dependsOn)
    } else if (appliedOverlays.contains(overlayName)) {
      logger.warn("The overlay {} already exists - skipping creation", overlayName)
    } else {
      create(context, storeUndoInfo)
      if (modifiesCpg) {
        Overlays.appendOverlayName(context.cpg, overlayName)
      }
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
                        index: Int = 0)(implicit ec: ExecutionContext): Unit = {
    val serializedCpg = initSerializedCpg(context.outputDir, pass.name, index)
    pass.createApplySerializeAndStore(serializedCpg, inverse = storeUndoInfo)
    serializedCpg.close()
  }

  def create(context: LayerCreatorContext, storeUndoInfo: Boolean = false)(implicit ec: ExecutionContext): Unit

}

class LayerCreatorContext(val cpg: Cpg, val outputDir: Option[String] = None) {}
class LayerCreatorOptions()
