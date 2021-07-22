package io.shiftleft.semanticcpg.layers

import better.files.File
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{CpgPassV2, CpgPassRunner}
import io.shiftleft.semanticcpg.Overlays
import org.slf4j.{Logger, LoggerFactory}

import scala.annotation.nowarn

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

  def run(context: LayerCreatorContext, storeUndoInfo: Boolean = false): Unit = {
    val appliedOverlays = Overlays.appliedOverlays(context.cpg).toSet
    if (!dependsOn.toSet.subsetOf(appliedOverlays)) {
      logger.warn(
        s"${this.getClass.getName} depends on $dependsOn but CPG only has $appliedOverlays - skipping creation")
    } else if (appliedOverlays.contains(overlayName)) {
      logger.warn(s"The overlay $overlayName already exists - skipping creation")
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

  protected def runPasses(passes: Iterable[CpgPassV2[_]],
                          context: LayerCreatorContext,
                          storeUndoInfo: Boolean,
                         ): Unit = {
    val passRunner = new CpgPassRunner(context.cpg, context.outputDir, inverse = storeUndoInfo)
    passes.foreach(passRunner.addPass)
    passRunner.run()
  }

  def create(context: LayerCreatorContext, storeUndoInfo: Boolean = false): Unit

  /**
    * Heuristically determine if overlay has been
    * applied to the CPG already. Used as a fallback
    * when `metaData.overlays` is not present due
    * to conversion from legacy CPG.
    * */
  @nowarn
  def probe(cpg: Cpg): Boolean = false

}

class LayerCreatorContext(val cpg: Cpg, val outputDir: Option[String] = None) {}
class LayerCreatorOptions()
