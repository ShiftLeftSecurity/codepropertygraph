package io.shiftleft.semanticcpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.MetaData
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality
import io.shiftleft.semanticcpg.language._

object Overlays {

  def appendOverlayName(cpg: Cpg, name: String): Unit = {
    cpg.metaData.l.headOption match {
      case Some(metaData) =>
        metaData.property(Cardinality.list, MetaData.PropertyNames.Overlays, name)
      case None =>
        System.err.println("Missing metaData block")
    }
  }

  def appliedOverlays(cpg: Cpg): List[String] = {
    cpg.metaData.l.headOption match {
      case Some(metaData) => metaData.overlays
      case None =>
        System.err.println("Missing metaData block")
        List()
    }
  }

}
