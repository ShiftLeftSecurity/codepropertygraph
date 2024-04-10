package io.shiftleft.codepropertygraph.generated.edges

import overflowdb._
import scala.jdk.CollectionConverters._

object Binds {
  val Label = "BINDS"

  object PropertyNames {

    val all: Set[String]                 = Set()
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {}

  object PropertyDefaults {}

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[Binds] {
    override val forLabel = Binds.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new Binds(graph, outNode, inNode)
  }
}

class Binds(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
    extends Edge(_graph, Binds.Label, _outNode, _inNode, Binds.PropertyNames.allAsJava) {

  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {

      case _ => super.propertyDefaultValue(propertyKey)
    }
  }

}
