package io.shiftleft.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import org.apache.logging.log4j.{LogManager, Logger}

class OverlayNamePass(cpg : Cpg, name : String) extends CpgPass(cpg) {

  val logger: Logger = LogManager.getLogger(this.getClass)

  override def run(): Iterator[DiffGraph] = {
    implicit val diffGraph: DiffGraph.Builder = DiffGraph.newBuilder
    val metaDataList = cpg.scalaGraph.V.hasLabel(NodeTypes.META_DATA).l
    metaDataList match {
      case (metaData: nodes.MetaData) :: Nil => {
        diffGraph.addNodeProperty(metaData, NodeKeys.OVERLAYS.toString, metaData.overlays ++ List(name))
      }
      case _ =>
        // TODO: make this a warning once all frontends are caught up.
        logger.info("Invalid CPG: exactly 1 meta data block required")
    }
    Iterator(diffGraph.build)
  }

}
