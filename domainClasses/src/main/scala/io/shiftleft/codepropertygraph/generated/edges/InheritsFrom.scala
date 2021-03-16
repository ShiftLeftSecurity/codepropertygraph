package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object InheritsFrom {
  val Label = "INHERITS_FROM"

  object PropertyNames {
    
    val all: Set[String] = Set()
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
  }

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[InheritsFrom] {
    override val forLabel = InheritsFrom.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new InheritsFrom(graph, outNode, inNode)
  }
}

class InheritsFrom(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
extends Edge(_graph, InheritsFrom.Label, _outNode, _inNode, InheritsFrom.PropertyNames.allAsJava) {

}

