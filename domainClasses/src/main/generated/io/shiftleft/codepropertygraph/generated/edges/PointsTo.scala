package io.shiftleft.codepropertygraph.generated.edges

import overflowdb._
import scala.jdk.CollectionConverters._

object PointsTo {
  val Label = "POINTS_TO"

  object PropertyNames {

    val all: Set[String]                 = Set()
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {}

  object PropertyDefaults {}

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[PointsTo] {
    override val forLabel = PointsTo.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new PointsTo(graph, outNode, inNode)
  }
}

class PointsTo(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
    extends Edge(_graph, PointsTo.Label, _outNode, _inNode, PointsTo.PropertyNames.allAsJava) {

  override def propertyDefaultValue(propertyKey: String) =
    propertyKey match {

      case _ => super.propertyDefaultValue(propertyKey)
    }

}
