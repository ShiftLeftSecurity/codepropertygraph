package io.shiftleft.dataflowengine.layers.dataflows

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.dataflowengine.passes.propagateedges.PropagateEdgePass
import io.shiftleft.dataflowengine.passes.reachingdef.ReachingDefPass
import io.shiftleft.dataflowengine.semanticsloader.Semantics
import io.shiftleft.semanticcpg.layers.{LayerCreator, LayerCreatorContext, LayerCreatorOptions}

class OssDataFlowOptions(val semantics: Semantics) extends LayerCreatorOptions {}

class OssDataFlow() extends LayerCreator {

  override val overlayName: String = "dataflowOSS"
  override val description: String = """Layer to support the OSS lightweight data flow tracker"""

  override def create(context: LayerCreatorContext,
                      options: Option[LayerCreatorOptions],
                      serializeInverse: Boolean): Unit = {
    val cpg = context.cpg
    val serializedCpg = context.serializedCpg
    val opts = options.asInstanceOf[Option[OssDataFlowOptions]]
    val semantics = opts.map(_.semantics).orNull
    val enhancementExecList = Iterator(new PropagateEdgePass(cpg, semantics), new ReachingDefPass(cpg))
    enhancementExecList.foreach(_.createApplySerializeAndStore(serializedCpg))
  }

  override def probe(cpg: Cpg): Boolean = {
    false
  }
}
