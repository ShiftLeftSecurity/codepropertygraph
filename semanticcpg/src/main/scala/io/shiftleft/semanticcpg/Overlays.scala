package io.shiftleft.semanticcpg

import gremlin.scala._
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

  def removeLastOverlayName(cpg: Cpg): Unit = {
    cpg.metaData.headOption match {
      case Some(metaData) => {
        // This is currently sub-optimal: we do not have
        // and operation to simply set a list as a property,
        // all we have is `removeProperty` and an append-
        //operation via `.property(Cardinality.list,...`).
        // Moreover, `remove` only works if the list has
        // only a single element, hence the call to
        // `property("OVERLAYS", "")` right before the remove.
        // It works, but it's not pretty.
        val newValue = metaData.overlays.dropRight(1)
        metaData.property(MetaData.PropertyNames.Overlays, "")
        metaData.removeProperty(Key(MetaData.PropertyNames.Overlays))
        newValue.foreach { e =>
          metaData.property(Cardinality.list, MetaData.PropertyNames.Overlays, e)
        }
      }
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
