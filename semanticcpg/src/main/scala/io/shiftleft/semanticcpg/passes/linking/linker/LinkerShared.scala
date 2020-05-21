package io.shiftleft.semanticcpg.passes.linking.linker

import org.apache.logging.log4j.{LogManager, Logger}

object LinkerShared {
  private val logger: Logger = LogManager.getLogger(this)

  @inline
  def logFailedDstLookup(edgeType: String,
                         srcNodeType: String,
                         srcNodeId: String,
                         dstNodeType: String,
                         dstFullName: String): Unit = {
    logger.error(
      "Could not create edge. Destination lookup failed. " +
        s"edgeType=$edgeType, srcNodeType=$srcNodeType, srcNodeId=$srcNodeId, " +
        s"dstNodeType=$dstNodeType, dstFullName=$dstFullName")
  }

  @inline
  def logFailedSrcLookup(edgeType: String,
                         srcNodeType: String,
                         srcFullName: String,
                         dstNodeType: String,
                         dstNodeId: String): Unit = {
    logger.error(
      "Could not create edge. Source lookup failed. " +
        s"edgeType=$edgeType, srcNodeType=$srcNodeType, srcFullName=$srcFullName, " +
        s"dstNodeType=$dstNodeType, dstNodeId=$dstNodeId")
  }
}
