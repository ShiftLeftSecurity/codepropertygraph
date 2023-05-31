package io.shiftleft.codepropertygraph.generated.edges

import overflowdb._
import scala.jdk.CollectionConverters._

object Receiver {
  val Label = "RECEIVER"

  object PropertyNames {

    val all: Set[String]                 = Set()
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {}

  object PropertyDefaults {}

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[Receiver] {
    override val forLabel = Receiver.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new Receiver(graph, outNode, inNode)
  }
}

class Receiver(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
    extends Edge(_graph, Receiver.Label, _outNode, _inNode, Receiver.PropertyNames.allAsJava) {

  override def propertyDefaultValue(propertyKey: String) =
    propertyKey match {

      case _ => super.propertyDefaultValue(propertyKey)
    }

}
