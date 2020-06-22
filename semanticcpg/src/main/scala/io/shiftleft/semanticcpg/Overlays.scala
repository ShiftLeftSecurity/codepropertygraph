package io.shiftleft.semanticcpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.semanticcpg.language._
import overflowdb._

object Overlays {

  def appendOverlayName(cpg: Cpg, name: String): Unit = {
    cpg.metaData.headOption match {
      case Some(metaData) =>
        val newValue = metaData.overlays :+ name
        metaData.setProperty2(NodeKeysOdb.OVERLAYS, newValue)
      case None =>
        System.err.println("Missing metaData block")
    }
  }

  def removeLastOverlayName(cpg: Cpg): Unit = {
    cpg.metaData.headOption match {
      case Some(metaData) =>
        val newValue = metaData.overlays.dropRight(1)
        metaData.setProperty2(NodeKeysOdb.OVERLAYS, newValue)
      case None =>
        System.err.println("Missing metaData block")
    }
  }

  def appliedOverlays(cpg: Cpg): List[String] = {
    cpg.metaData.headOption match {
      case Some(metaData) => Option(metaData.overlays).getOrElse(Nil)
      case None =>
        System.err.println("Missing metaData block")
        List()
    }
  }

}
