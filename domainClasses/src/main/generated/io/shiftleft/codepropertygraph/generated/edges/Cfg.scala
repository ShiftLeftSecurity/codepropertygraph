package io.shiftleft.codepropertygraph.generated.edges

import overflowdb._
import scala.jdk.CollectionConverters._

object Cfg {
  val Label = "CFG"

  object PropertyNames {

    val all: Set[String]                 = Set()
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {}

  object PropertyDefaults {}

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[Cfg] {
    override val forLabel = Cfg.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new Cfg(graph, outNode, inNode)
  }
}

class Cfg(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
    extends Edge(_graph, Cfg.Label, _outNode, _inNode, Cfg.PropertyNames.allAsJava) {

  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {

      case _ => super.propertyDefaultValue(propertyKey)
    }
  }

}
