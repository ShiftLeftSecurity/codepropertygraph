package io.shiftleft.codepropertygraph.generated.edges

import overflowdb._
import scala.jdk.CollectionConverters._

object Contains {
  val Label = "CONTAINS"

  object PropertyNames {

    val all: Set[String]                 = Set()
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {}

  object PropertyDefaults {}

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[Contains] {
    override val forLabel = Contains.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new Contains(graph, outNode, inNode)
  }
}

class Contains(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
    extends Edge(_graph, Contains.Label, _outNode, _inNode, Contains.PropertyNames.allAsJava) {

  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {

      case _ => super.propertyDefaultValue(propertyKey)
    }
  }

}
