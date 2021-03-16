package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object Propagate {
  val Label = "PROPAGATE"

  object PropertyNames {
    val Alias = "ALIAS" 
    val all: Set[String] = Set(Alias)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Alias = new PropertyKey[java.lang.Boolean]("ALIAS") 
  }

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[Propagate] {
    override val forLabel = Propagate.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new Propagate(graph, outNode, inNode)
  }
}

class Propagate(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
extends Edge(_graph, Propagate.Label, _outNode, _inNode, Propagate.PropertyNames.allAsJava) {
def alias: java.lang.Boolean = property("ALIAS").asInstanceOf[java.lang.Boolean]
}

