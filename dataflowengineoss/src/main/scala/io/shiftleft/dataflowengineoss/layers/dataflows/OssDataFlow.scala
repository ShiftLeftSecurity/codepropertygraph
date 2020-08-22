package io.shiftleft.dataflowengineoss.layers.dataflows

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.dataflowengineoss.passes.reachingdef.ReachingDefPass
import io.shiftleft.semanticcpg.layers.{LayerCreator, LayerCreatorContext, LayerCreatorOptions}

object OssDataFlow {
  val overlayName: String = "dataflowOss"
  val description: String = "Layer to support the OSS lightweight data flow tracker"

  def defaultOpts = new OssDataFlowOptions()
}

class OssDataFlowOptions() extends LayerCreatorOptions {}

class OssDataFlow(opts: OssDataFlowOptions) extends LayerCreator {

  override val overlayName: String = OssDataFlow.overlayName
  override val description: String = OssDataFlow.description

  override def create(context: LayerCreatorContext, serializeInverse: Boolean): Unit = {
    val cpg = context.cpg
    val enhancementExecList = Iterator(new ReachingDefPass(cpg))
    enhancementExecList.zipWithIndex.foreach {
      case (pass, index) =>
        val serializedCpg = initSerializedCpg(context.outputDir, pass.name, index)
        pass.createApplySerializeAndStore(serializedCpg)
        serializedCpg.close()
    }
  }

  override def probe(cpg: Cpg): Boolean = {
    false
  }
}
